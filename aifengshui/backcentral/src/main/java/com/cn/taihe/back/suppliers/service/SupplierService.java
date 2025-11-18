package com.cn.taihe.back.suppliers.service;

import com.cn.taihe.back.suppliers.dto.request.SupplierCreateDTO;
import com.cn.taihe.back.suppliers.dto.request.SupplierQueryDTO;
import com.cn.taihe.back.suppliers.dto.request.SupplierUpdateDTO;
import com.cn.taihe.back.suppliers.entity.Supplier;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * 供应商服务接口
 */
public interface SupplierService {

  /**
   * 新增供应商
   */
  Supplier createSupplier(SupplierCreateDTO createDTO, String operator);

  /**
   * 更新供应商信息
   */
  Supplier updateSupplier(SupplierUpdateDTO updateDTO, String operator);

  /**
   * 新建商品spu关系
   **/

  int createRealiations(String spuId, List list);

  /**
   * 新建商品sKu-供应商关系
   **/

  int createSupplierProductSkuRealiations(String skuId, List list);

  /**
   * 根据ID获取供应商详情
   */
  Supplier getSupplierById(String id);
  /**
   * 根据中间表产品skuID查询已经关联的供应商集合
   **/
  List<Supplier> getBySkuId(String skuId);

  /**
   * 根据ID删除供应商
   */
  void deleteSupplierById(String id, String operator);

  /**
   * 条件分页查询供应商列表
   */
  PageInfo<Supplier> querySupplierPage(SupplierQueryDTO queryDTO, int page, int size);

  /**
   * 查询所有供应商列表
   */
  List<Supplier> getAllSuppliers();

  /**
   * 批量删除供应商
   */
  void batchDeleteSuppliers(List<String> ids, String operator);

  /**
   * 批量更新供应商状态(冻结/解冻)
   */
  void batchUpdateStatus(List<String> ids, Integer status, String operator);
}
