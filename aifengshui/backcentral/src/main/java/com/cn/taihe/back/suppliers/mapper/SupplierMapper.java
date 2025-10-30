package com.cn.taihe.back.suppliers.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cn.taihe.back.suppliers.dto.request.SupplierQueryDTO;
import com.cn.taihe.back.suppliers.entity.Supplier;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 供应商Mapper接口
 */
@Mapper
@Repository
public interface SupplierMapper extends BaseMapper<Supplier> {

    /**
     * 根据ID查询供应商
     */
    @Select("SELECT * FROM suppliers WHERE id = #{id}")
    Supplier selectById(@Param("id") String id);

    /**
     * 新增供应商
     */
    @Insert("INSERT INTO suppliers (" +
            "id, supplier_code, name, short_name, status, supplier_level, company_type, " +
            "unified_credit_code, legal_person, registered_capital, registered_address, " +
            "business_address, establish_date, business_scope, main_categories, " +
            "contact_person, contact_phone, contact_email, website, bank_name, " +
            "bank_account, bank_account_name, tax_type, tax_id, invoice_type, " +
            "settlement_cycle, currency, min_order_amount, min_order_quantity, " +
            "lead_time, cooperation_start_date, cooperation_end_date, " +
            "contract_expire_date, performance_score, quality_score, " +
            "delivery_score, service_score, created_by, remark" +
            ") VALUES (" +
            "#{id}, #{supplierCode}, #{name}, #{shortName}, #{status}, #{supplierLevel}, #{companyType}, " +
            "#{unifiedCreditCode}, #{legalPerson}, #{registeredCapital}, #{registeredAddress}, " +
            "#{businessAddress}, #{establishDate}, #{businessScope}, #{mainCategories}, " +
            "#{contactPerson}, #{contactPhone}, #{contactEmail}, #{website}, #{bankName}, " +
            "#{bankAccount}, #{bankAccountName}, #{taxType}, #{taxId}, #{invoiceType}, " +
            "#{settlementCycle}, #{currency}, #{minOrderAmount}, #{minOrderQuantity}, " +
            "#{leadTime}, #{cooperationStartDate}, #{cooperationEndDate}, " +
            "#{contractExpireDate}, #{performanceScore}, #{qualityScore}, " +
            "#{deliveryScore}, #{serviceScore}, #{createdBy}, #{remark}" +
            ")")
    int insertSupplier(Supplier supplierDTO);

    /**
     * 更新供应商信息(只更新非空字段)
     */
    @Update("<script>" +
            "UPDATE suppliers SET " +
            "<if test='dto.name != null'>name = #{dto.name},</if> " +
            "<if test='dto.shortName != null'>short_name = #{dto.shortName},</if> " +
            "<if test='dto.status != null'>status = #{dto.status},</if> " +
            "<if test='dto.supplierLevel != null'>supplier_level = #{dto.supplierLevel},</if> " +
            "<if test='dto.businessAddress != null'>business_address = #{dto.businessAddress},</if> " +
            "<if test='dto.establishDate != null'>establish_date = #{dto.establishDate},</if> " +
            "<if test='dto.businessScope != null'>business_scope = #{dto.businessScope},</if> " +
            "<if test='dto.mainCategories != null'>main_categories = #{dto.mainCategories},</if> " +
            "<if test='dto.contactPerson != null'>contact_person = #{dto.contactPerson},</if> " +
            "<if test='dto.contactPhone != null'>contact_phone = #{dto.contactPhone},</if> " +
            "<if test='dto.contactEmail != null'>contact_email = #{dto.contactEmail},</if> " +
            "<if test='dto.website != null'>website = #{dto.website},</if> " +
            "<if test='dto.bankName != null'>bank_name = #{dto.bankName},</if> " +
            "<if test='dto.bankAccount != null'>bank_account = #{dto.bankAccount},</if> " +
            "<if test='dto.bankAccountName != null'>bank_account_name = #{dto.bankAccountName},</if> " +
            "<if test='dto.taxType != null'>tax_type = #{dto.taxType},</if> " +
            "<if test='dto.invoiceType != null'>invoice_type = #{dto.invoiceType},</if> " +
            "<if test='dto.settlementCycle != null'>settlement_cycle = #{dto.settlementCycle},</if> " +
            "<if test='dto.currency != null'>currency = #{dto.currency},</if> " +
            "<if test='dto.minOrderAmount != null'>min_order_amount = #{dto.minOrderAmount},</if> " +
            "<if test='dto.minOrderQuantity != null'>min_order_quantity = #{dto.minOrderQuantity},</if> " +
            "<if test='dto.leadTime != null'>lead_time = #{dto.leadTime},</if> " +
            "<if test='dto.contractExpireDate != null'>contract_expire_date = #{dto.contractExpireDate},</if> " +
            "<if test='dto.performanceScore != null'>performance_score = #{dto.performanceScore},</if> " +
            "<if test='dto.qualityScore != null'>quality_score = #{dto.qualityScore},</if> " +
            "<if test='dto.deliveryScore != null'>delivery_score = #{dto.deliveryScore},</if> " +
            "<if test='dto.serviceScore != null'>service_score = #{dto.serviceScore},</if> " +
            "<if test='dto.remark != null'>remark = #{dto.remark},</if> " +
            "updated_by = #{dto.updatedBy}, updated_at = NOW() " +
            "WHERE id = #{dto.id}" +
            "</script>")
    int updateSupplierSelective(@Param("dto") Supplier supplierDTO);

    /**
     * 根据ID删除供应商
     */
    @Delete("DELETE FROM suppliers WHERE id = #{id}")
    int deleteById(@Param("id") String id);

    /**
     * 条件查询供应商列表
     */
    @Select("<script>" +
            "SELECT * FROM suppliers WHERE 1=1 " +
            "<if test='queryDTO.supplierCode != null and queryDTO.supplierCode != \"\"'> " +
            "AND supplier_code = #{queryDTO.supplierCode} " +
            "</if> " +
            "<if test='queryDTO.name != null and queryDTO.name != \"\"'> " +
            "AND name LIKE CONCAT('%', #{queryDTO.name}, '%') " +
            "</if> " +
            "<if test='queryDTO.shortName != null and queryDTO.shortName != \"\"'> " +
            "AND short_name LIKE CONCAT('%', #{queryDTO.shortName}, '%') " +
            "</if> " +
            "<if test='queryDTO.status != null'> " +
            "AND status = #{queryDTO.status} " +
            "</if> " +
            "<if test='queryDTO.supplierLevel != null'> " +
            "AND supplier_level = #{queryDTO.supplierLevel} " +
            "</if> " +
            "<if test='queryDTO.companyType != null and queryDTO.companyType != \"\"'> " +
            "AND company_type = #{queryDTO.companyType} " +
            "</if> " +
            "<if test='queryDTO.unifiedCreditCode != null and queryDTO.unifiedCreditCode != \"\"'> " +
            "AND unified_credit_code = #{queryDTO.unifiedCreditCode} " +
            "</if> " +
            "<if test='queryDTO.legalPerson != null and queryDTO.legalPerson != \"\"'> " +
            "AND legal_person LIKE CONCAT('%', #{queryDTO.legalPerson}, '%') " +
            "</if> " +
            "<if test='queryDTO.registeredAddress != null and queryDTO.registeredAddress != \"\"'> " +
            "AND registered_address LIKE CONCAT('%', #{queryDTO.registeredAddress}, '%') " +
            "</if> " +
            "<if test='queryDTO.contactPerson != null and queryDTO.contactPerson != \"\"'> " +
            "AND contact_person LIKE CONCAT('%', #{queryDTO.contactPerson}, '%') " +
            "</if> " +
            "<if test='queryDTO.contactPhone != null and queryDTO.contactPhone != \"\"'> " +
            "AND contact_phone = #{queryDTO.contactPhone} " +
            "</if> " +

            "<if test='queryDTO.performanceScoreMin != null'> " +
            "AND performance_score &gt;= #{queryDTO.performanceScoreMin} " +
            "</if> " +
            "<if test='queryDTO.performanceScoreMax != null'> " +
            "AND performance_score &lt;= #{queryDTO.performanceScoreMax} " +
            "</if> " +
            "<if test='queryDTO.mainCategories != null and queryDTO.mainCategories != \"\"'> " +
            "AND main_categories LIKE CONCAT('%', #{queryDTO.mainCategories}, '%') " +
            "</if> " +
            "ORDER BY created_at DESC" +
            "</script>")
    List<Supplier> selectByCondition(@Param("queryDTO") SupplierQueryDTO queryDTO);

    /**
     * 查询所有供应商
     */
    @Select("SELECT * FROM suppliers ORDER BY created_at DESC")
    List<Supplier> selectAll();

    /**
     * 根据ID集合批量删除供应商
     */
    @Delete("<script>" +
            "DELETE FROM suppliers WHERE id IN " +
            "<foreach collection='ids' item='id' open='(' separator=',' close=')'>" +
            "#{id}" +
            "</foreach>" +
            "</script>")
    int batchDeleteByIds(@Param("ids") List<String> ids);

    /**
     * 批量更新供应商状态
     */
    @Update("<script>" +
            "UPDATE suppliers SET " +
            "status = #{status}, " +
            "updated_by = #{operator}, " +
            "updated_at = NOW() " +
            "WHERE id IN " +
            "<foreach collection='ids' item='id' open='(' separator=',' close=')'>" +
            "#{id}" +
            "</foreach>" +
            "</script>")
    int batchUpdateStatus(@Param("ids") List<String> ids,
                          @Param("status") Integer status,
                          @Param("operator") String operator);
}