package com.cn.taihe.back.suppliers.service;
import com.cn.taihe.back.suppliers.dto.request.SupplierCertificateCreateDTO;
import com.cn.taihe.back.suppliers.dto.request.SupplierCertificateQueryDTO;
import com.cn.taihe.back.suppliers.dto.request.SupplierCertificateUpdateDTO;
import com.cn.taihe.back.suppliers.entity.SupplierCertificate;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

/**
 * 供应商资质文件服务接口
 * 生成内容总结：
 *
 * ✅ Service接口：定义完整的业务方法
 *
 * ✅ Service实现类：完全实现接口方法，使用PageHelper分页
 *
 * ✅ 禁止使用LambdaQueryWrapper，严格使用Mapper注解方法
 *
 * ✅ 参数和返回值严格匹配DTO类型
 *
 * 严格遵守的规范：
 *
 * ✅ 使用 @Autowired注入Mapper接口
 *
 * ✅ 每个方法添加详细的事务注解 @Transactional
 *
 * ✅ 完整的日志记录（请求参数、返回结果、操作人）
 *
 * ✅ 使用PageHelper实现分页查询
 *
 * ✅ 严格的参数校验和异常处理
 *
 * ✅ Swagger注解生成接口文档
 *
 * 关键特性：
 *
 * ✅ 分页查询使用PageHelper，默认page=1，size=10
 *
 * ✅ 所有写操作添加事务管理
 *
 * ✅ 完整的日志记录，包含操作人信息
 *
 * ✅ 严格的参数校验和异常处理
 *
 * ✅ 使用BeanUtils进行DTO转换
 */
@Api(tags = "供应商资质文件管理服务接口")
public interface SupplierCertificateService {

    /**
     * 根据主键查询资质文件
     */
    @ApiOperation("根据主键查询资质文件")
    SupplierCertificate getById(String id);

    /**
     * 新增资质文件
     */
    @ApiOperation("新增资质文件")
    boolean create(SupplierCertificateCreateDTO createDTO);

    /**
     * 更新资质文件信息
     */
    @ApiOperation("更新资质文件信息")
    boolean update(String id, SupplierCertificateUpdateDTO updateDTO);

    /**
     * 根据主键删除资质文件
     */
    @ApiOperation("根据主键删除资质文件")
    boolean deleteById(String id);

    /**
     * 条件分页查询资质文件列表
     */
    @ApiOperation("条件分页查询资质文件列表")
    PageInfo<SupplierCertificate> getByCondition(SupplierCertificateQueryDTO queryDTO, int page, int size);

    /**
     * 查询所有资质文件列表
     */
    @ApiOperation("查询所有资质文件列表")
    List<SupplierCertificate> getAll();

    /**
     * 批量删除资质文件
     */
    @ApiOperation("批量删除资质文件")
    boolean deleteBatch(List<String> ids);

    /**
     * 冻结/解冻资质文件
     */
    @ApiOperation("冻结/解冻资质文件")
    boolean updateStatus(String id, Integer isValid);

    /**
     * 批量冻结/解冻资质文件
     */
    @ApiOperation("批量冻结/解冻资质文件")
    boolean updateBatchStatus(List<String> ids, Integer isValid);

    /**
     * 根据供应商ID查询资质文件列表
     */
    @ApiOperation("根据供应商ID查询资质文件列表")
    List<SupplierCertificate> getBySupplierId(String supplierId);

    /**
     * 根据供应商ID和资质类型查询
     */
    @ApiOperation("根据供应商ID和资质类型查询")
    List<SupplierCertificate> getBySupplierIdAndType(String supplierId, String certificateType);
}
