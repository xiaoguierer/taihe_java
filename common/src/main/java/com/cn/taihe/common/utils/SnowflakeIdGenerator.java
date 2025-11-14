package com.cn.taihe.common.utils;

import java.lang.management.ManagementFactory;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * 雪花算法ID生成器
 * 结构：1位符号位 + 41位时间戳 + 10位工作机器ID + 12位序列号
 */
public class SnowflakeIdGenerator {
    // ==============================Fields===========================================
    /** 开始时间截 (2023-01-01) */
    private static final long START_TIMESTAMP = 1672531200000L;

    /** 机器id所占的位数 */
    private static final long WORKER_ID_BITS = 5L;

    /** 数据标识id所占的位数 */
    private static final long DATACENTER_ID_BITS = 5L;

    /** 支持的最大机器id，结果是31 (这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数) */
    private static final long MAX_WORKER_ID = ~(-1L << WORKER_ID_BITS);

    /** 支持的最大数据标识id，结果是31 */
    private static final long MAX_DATACENTER_ID = ~(-1L << DATACENTER_ID_BITS);

    /** 序列在id中占的位数 */
    private static final long SEQUENCE_BITS = 12L;

    /** 机器ID向左移12位 */
    private static final long WORKER_ID_SHIFT = SEQUENCE_BITS;

    /** 数据标识id向左移17位(12+5) */
    private static final long DATACENTER_ID_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS;

    /** 时间截向左移22位(5+5+12) */
    private static final long TIMESTAMP_LEFT_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS + DATACENTER_ID_BITS;

    /** 生成序列的掩码，这里为4095 (0b111111111111=0xfff=4095) */
    private static final long SEQUENCE_MASK = ~(-1L << SEQUENCE_BITS);

    /** 工作机器ID(0~31) */
    private static long workerId;

    /** 数据中心ID(0~31) */
    private static long datacenterId;

    /** 毫秒内序列(0~4095) */
    private static long sequence = 0L;

    /** 上次生成ID的时间截 */
    private static long lastTimestamp = -1L;

    // ==============================Static Block=====================================
    static {
        // 自动获取机器ID和数据中心ID
        try {
            long[] ids = getMachineIdAndDatacenterId();
            workerId = ids[0];
            datacenterId = ids[1];
        } catch (Exception e) {
            // 默认值
            workerId = 0L;
            datacenterId = 0L;
        }

        if (workerId > MAX_WORKER_ID || workerId < 0) {
            throw new IllegalArgumentException(
                    String.format("worker Id can't be greater than %d or less than 0", MAX_WORKER_ID));
        }
        if (datacenterId > MAX_DATACENTER_ID || datacenterId < 0) {
            throw new IllegalArgumentException(
                    String.format("datacenter Id can't be greater than %d or less than 0", MAX_DATACENTER_ID));
        }
    }

    // ==============================Methods==========================================
    /**
     * 获得下一个ID (线程安全)
     * @return SnowflakeId
     */
    public static synchronized Long nextId() {
        long timestamp = timeGen();

        // 如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(
                    String.format("Clock moved backwards. Refusing to generate id for %d milliseconds",
                            lastTimestamp - timestamp));
        }

        // 如果是同一时间生成的，则进行毫秒内序列
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & SEQUENCE_MASK;
            // 毫秒内序列溢出
            if (sequence == 0) {
                // 阻塞到下一个毫秒，获得新的时间戳
                timestamp = tilNextMillis(lastTimestamp);
            }
        }
        // 时间戳改变，毫秒内序列重置
        else {
            sequence = 0L;
        }

        // 上次生成ID的时间截
        lastTimestamp = timestamp;

        // 移位并通过或运算拼到一起组成64位的ID
        return ((timestamp - START_TIMESTAMP) << TIMESTAMP_LEFT_SHIFT) //
                | (datacenterId << DATACENTER_ID_SHIFT) //
                | (workerId << WORKER_ID_SHIFT) //
                | sequence;
    }

    /**
     * 阻塞到下一个毫秒，直到获得新的时间戳
     * @param lastTimestamp 上次生成ID的时间截
     * @return 当前时间戳
     */
    protected static long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    /**
     * 返回以毫秒为单位的当前时间
     * @return 当前时间(毫秒)
     */
    protected static long timeGen() {
        return System.currentTimeMillis();
    }

    /**
     * 自动获取机器ID和数据中心ID
     * 基于MAC地址和JVM进程ID生成
     */
    private static long[] getMachineIdAndDatacenterId() throws SocketException {
        long[] ids = new long[2];

        // 1. 获取机器ID (基于MAC地址的最后两个字节)
        StringBuilder sb = new StringBuilder();
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        while (interfaces.hasMoreElements()) {
            NetworkInterface ni = interfaces.nextElement();
            byte[] mac = ni.getHardwareAddress();
            if (mac != null) {
                for (byte b : mac) {
                    sb.append(String.format("%02X", b));
                }
            }
        }
        String macHash = sb.toString();
        if (macHash.length() > 0) {
            ids[0] = ((long) macHash.hashCode()) & MAX_WORKER_ID;
        } else {
            ids[0] = 0;
        }

        // 2. 获取数据中心ID (基于JVM进程ID)
        String processName = ManagementFactory.getRuntimeMXBean().getName();
        if (processName.contains("@")) {
            ids[1] = Long.parseLong(processName.substring(0, processName.indexOf('@'))) & MAX_DATACENTER_ID;
        } else {
            ids[1] = ((long) processName.hashCode()) & MAX_DATACENTER_ID;
        }

        return ids;
    }

    /**
     * 解析雪花ID
     * @param id 雪花ID
     * @return 包含时间戳、数据中心ID、机器ID和序列号的数组
     */
    public static long[] parseId(long id) {
        long[] result = new long[4];
        result[0] = (id >> TIMESTAMP_LEFT_SHIFT) + START_TIMESTAMP; // 时间戳
        result[1] = (id >> DATACENTER_ID_SHIFT) & MAX_DATACENTER_ID; // 数据中心ID
        result[2] = (id >> WORKER_ID_SHIFT) & MAX_WORKER_ID; // 机器ID
        result[3] = id & SEQUENCE_MASK; // 序列号
        return result;
    }

    // ==============================Test=============================================
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            long id = SnowflakeIdGenerator.nextId();
            System.out.println("生成的ID: " + id);
            long[] parts = parseId(id);
            System.out.printf("解析结果 => 时间戳: %d, 数据中心ID: %d, 机器ID: %d, 序列号: %d\n",
                    parts[0], parts[1], parts[2], parts[3]);
        }
    }
}
