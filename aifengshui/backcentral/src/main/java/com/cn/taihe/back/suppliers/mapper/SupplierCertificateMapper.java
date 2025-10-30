package com.cn.taihe.back.suppliers.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cn.taihe.back.suppliers.dto.request.SupplierCertificateQueryDTO;
import com.cn.taihe.back.suppliers.dto.request.SupplierCertificateUpdateDTO;
import com.cn.taihe.back.suppliers.entity.SupplierCertificate;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 供应商资质文件Mapper接口
 * 使用MyBatis-Plus纯注解方式实现
 */
@Mapper
@Repository
public interface SupplierCertificateMapper extends BaseMapper<SupplierCertificate> {

    /**
     * 根据主键查询资质文件
     * @param id 主键ID
     * @return 资质文件信息
     */
    @Select("SELECT * FROM supplier_certificates WHERE id = #{id}")
    SupplierCertificate selectById(@Param("id") String id);

    /**
     * 新增资质文件
     * @param dto 新增参数
     * @return 影响行数
     */
    @Insert("INSERT INTO supplier_certificates (id, supplier_id, certificate_type, certificate_name, certificate_number, " +
            "issuing_authority, issue_date, expiry_date, file_url, file_name, file_size, remark) " +
            "VALUES (#{dto.id}, #{dto.supplierId}, #{dto.certificateType}, #{dto.certificateName}, #{dto.certificateNumber}, " +
            "#{dto.issuingAuthority}, #{dto.issueDate}, #{dto.expiryDate}, #{dto.fileUrl}, #{dto.fileName}, #{dto.fileSize}, #{dto.remark})")
    int insert(@Param("dto") SupplierCertificate dto);

    /**
     * 根据主键更新资质文件（只更新非空字段）
     * @param id 主键ID
     * @param dto 更新参数
     * @return 影响行数
     */
    @Update("<script>" +
            "UPDATE supplier_certificates " +
            "<set> " +
            "    <if test='dto.certificateType != null'>certificate_type = #{dto.certificateType},</if> " +
            "    <if test='dto.certificateName != null'>certificate_name = #{dto.certificateName},</if> " +
            "    <if test='dto.certificateNumber != null'>certificate_number = #{dto.certificateNumber},</if> " +
            "    <if test='dto.issuingAuthority != null'>issuing_authority = #{dto.issuingAuthority},</if> " +
            "    <if test='dto.issueDate != null'>issue_date = #{dto.issueDate},</if> " +
            "    <if test='dto.expiryDate != null'>expiry_date = #{dto.expiryDate},</if> " +
            "    <if test='dto.fileUrl != null'>file_url = #{dto.fileUrl},</if> " +
            "    <if test='dto.fileName != null'>file_name = #{dto.fileName},</if> " +
            "    <if test='dto.fileSize != null'>file_size = #{dto.fileSize},</if> " +
            "    <if test='dto.isValid != null'>is_valid = #{dto.isValid},</if> " +
            "    <if test='dto.remark != null'>remark = #{dto.remark},</if> " +
            "    updated_at = NOW() " +
            "</set> " +
            "WHERE id = #{id}" +
            "</script>")
    int updateById(@Param("id") String id, @Param("dto") SupplierCertificateUpdateDTO dto);

    /**
     * 根据主键删除资质文件
     * @param id 主键ID
     * @return 影响行数
     */
    @Delete("DELETE FROM supplier_certificates WHERE id = #{id}")
    int deleteById(@Param("id") String id);

    /**
     * 根据条件查询资质文件列表
     * @param queryDTO 查询条件
     * @return 资质文件列表
     */
    @Select("<script>" +
            "SELECT * FROM supplier_certificates " +
            "<where> " +
            "    <if test='dto.supplierId != null and dto.supplierId != \"\"'>AND supplier_id = #{dto.supplierId}</if> " +
            "    <if test='dto.certificateType != null and dto.certificateType != \"\"'>AND certificate_type = #{dto.certificateType}</if> " +
            "    <if test='dto.certificateName != null and dto.certificateName != \"\"'>AND certificate_name LIKE CONCAT('%', #{dto.certificateName}, '%')</if> " +
            "    <if test='dto.certificateNumber != null and dto.certificateNumber != \"\"'>AND certificate_number LIKE CONCAT('%', #{dto.certificateNumber}, '%')</if> " +
            "    <if test='dto.isValid != null'>AND is_valid = #{dto.isValid}</if> " +
            "</where> " +
            "ORDER BY created_at DESC" +
            "</script>")
    List<SupplierCertificate> selectByCondition(@Param("dto") SupplierCertificateQueryDTO queryDTO);

    /**
     * 查询所有资质文件
     * @return 所有资质文件列表
     */
    @Select("SELECT * FROM supplier_certificates ORDER BY created_at DESC")
    List<SupplierCertificate> selectAll();

    /**
     * 根据主键集合批量删除资质文件
     * @param ids 主键ID集合
     * @return 影响行数
     */
    @Delete("<script>" +
            "DELETE FROM supplier_certificates WHERE id IN " +
            "<foreach collection='ids' item='id' open='(' separator=',' close=')'>" +
            "    #{id}" +
            "</foreach>" +
            "</script>")
    int deleteBatchIds(@Param("ids") List<String> ids);

    /**
     * 根据主键更新状态（冻结/解冻）
     * @param id 主键ID
     * @param isValid 状态：0-有效，1-无效/冻结
     * @return 影响行数
     */
    @Update("UPDATE supplier_certificates SET is_valid = #{isValid}, updated_at = NOW() WHERE id = #{id}")
    int updateStatusById(@Param("id") String id, @Param("isValid") Integer isValid);

    /**
     * 批量更新状态（批量冻结/解冻）
     * @param ids 主键ID集合
     * @param isValid 状态：0-有效，1-无效/冻结
     * @return 影响行数
     */
    @Update("<script>" +
            "UPDATE supplier_certificates SET is_valid = #{isValid}, updated_at = NOW() WHERE id IN " +
            "<foreach collection='ids' item='id' open='(' separator=',' close=')'>" +
            "    #{id}" +
            "</foreach>" +
            "</script>")
    int updateBatchStatus(@Param("ids") List<String> ids, @Param("isValid") Integer isValid);

    /**
     * 根据供应商ID查询资质文件列表
     * @param supplierId 供应商ID
     * @return 资质文件列表
     */
    @Select("SELECT * FROM supplier_certificates WHERE supplier_id = #{supplierId} ORDER BY created_at DESC")
    List<SupplierCertificate> selectBySupplierId(@Param("supplierId") String supplierId);

    /**
     * 根据供应商ID和资质类型查询
     * @param supplierId 供应商ID
     * @param certificateType 资质类型
     * @return 资质文件列表
     */
    @Select("SELECT * FROM supplier_certificates WHERE supplier_id = #{supplierId} AND certificate_type = #{certificateType} ORDER BY created_at DESC")
    List<SupplierCertificate> selectBySupplierIdAndType(@Param("supplierId") String supplierId, @Param("certificateType") String certificateType);
}