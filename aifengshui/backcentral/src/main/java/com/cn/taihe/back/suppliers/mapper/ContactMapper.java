package com.cn.taihe.back.suppliers.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cn.taihe.back.suppliers.entity.SupplierContact;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
@Repository
public interface ContactMapper extends BaseMapper<SupplierContact> {

    /**
     * 根据条件查询联系人
     * @param supplierId 供应商ID
     * @param name 联系人姓名(模糊)
     * @param department 部门
     * @param isPrimary 是否主联系人
     * @return 联系人列表
     */
    List<SupplierContact> selectContactsByCondition(
            @Param("supplierId") String supplierId,
            @Param("name") String name,
            @Param("department") String department,
            @Param("isPrimary") Boolean isPrimary
    );

    /**
     * 复杂条件查询联系人
     * @param condition 查询条件Map
     * @return 联系人列表
     */
    List<SupplierContact> selectContactsByMapCondition(@Param("condition") Map<String, Object> condition);

    /**
     * 批量插入联系人
     * @param contacts 联系人列表
     * @return 影响行数
     */
    int batchInsertContacts(@Param("list") List<SupplierContact> contacts);

    /**
     * 批量删除联系人
     * @param ids 联系人ID列表
     * @return 影响行数
     */
    int batchDeleteContacts(@Param("ids") List<String> ids);

    /**
     * 根据供应商ID批量删除联系人
     * @param supplierIds 供应商ID列表
     * @return 影响行数
     */
    int batchDeleteBySupplierIds(@Param("supplierIds") List<String> supplierIds);

    /**
     * 设置/取消主联系人
     * @param contactId 联系人ID
     * @param isPrimary 是否主联系人
     * @return 影响行数
     */
    int updatePrimaryStatus(@Param("id") String contactId, @Param("isPrimary") Boolean isPrimary);
}
