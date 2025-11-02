package com.cn.taihe;

/**
 * @BelongsProject: taihe_java
 * @BelongsPackage: com.cn.taihe
 * @Author: 大咖
 * @CreateTime: 2025-11-03  00:03
 * @Description: TODO
 * @Version: 1.0
 */
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BasicTest {
  @Test
  void testBasic() {
    System.out.println("=== 基础测试开始 ===");
    System.out.println("如果看到这行，说明测试框架正常");
    assertTrue(true, "基础断言测试");
    System.out.println("=== 基础测试结束 ===");
  }
}
