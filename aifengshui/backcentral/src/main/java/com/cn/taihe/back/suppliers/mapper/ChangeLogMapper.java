package com.cn.taihe.back.suppliers.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cn.taihe.back.suppliers.entity.SupplierChangeLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;
@Repository
public interface ChangeLogMapper extends BaseMapper<SupplierChangeLog> {

    /**
     * 根据条件查询变更日志
     * @param supplierId 供应商ID
     * @param changeType 变更类型
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return 变更日志列表
     */
    List<SupplierChangeLog> selectChangeLogsByCondition(
            @Param("supplierId") String supplierId,
            @Param("changeType") String changeType,
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate
    );

    /**
     * 复杂条件查询变更日志
     * @param condition 查询条件Map
     * @return 变更日志列表
     */
    List<SupplierChangeLog> selectChangeLogsByMapCondition(@Param("condition") Map<String, Object> condition);

    /**
     * 批量插入变更日志
     * @param changeLogs 变更日志列表
     * @return 影响行数
     */
    int batchInsertChangeLogs(@Param("list") List<SupplierChangeLog> changeLogs);

    /**
     * 根据供应商ID批量删除变更日志
     * @param supplierIds 供应商ID列表
     * @return 影响行数
     */
    int batchDeleteBySupplierIds(@Param("supplierIds") List<String> supplierIds);

    /**
     * 获取最近变更记录
     * @param supplierId 供应商ID
     * @param limit 记录条数
     * @return 变更日志列表
     */
    List<SupplierChangeLog> selectRecentChangeLogs(
            @Param("supplierId") String supplierId,
            @Param("limit") int limit
    );
}