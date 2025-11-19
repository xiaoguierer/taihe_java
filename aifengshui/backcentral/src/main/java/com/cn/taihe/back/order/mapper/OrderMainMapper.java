package com.cn.taihe.back.order.mapper;

import com.cn.taihe.back.order.dto.OrderMainQueryDTO;
import com.cn.taihe.back.order.entity.OrderMain;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 订单主表Mapper接口
 *
 * @author system
 */
@Mapper
@Repository
public interface OrderMainMapper {

  /**
   * 根据主键查询订单
   */
  OrderMain selectById(@Param("id") String id);

  /**
   * 根据用户ID查询订单列表
   */
  List<OrderMain> selectByUserId(@Param("userId") String userId);

  /**
   * 新增订单
   */
  int insert(OrderMain orderMain);

  /**
   * 根据主键更新订单（只更新非空字段）
   */
  int updateById(OrderMain orderMain);

  /**
   * 根据主键删除订单
   */
  int deleteById(@Param("id") String id);

  /**
   * 条件查询订单列表（支持分页）
   */
  List<OrderMain> selectByCondition(OrderMainQueryDTO queryDTO);

  /**
   * 根据主键集合批量删除订单
   */
  int deleteBatchIds(@Param("ids") List<String> ids);

  /**
   * 根据主键更新订单状态
   */
  int updateStatusById(@Param("id") String id, @Param("status") Integer status);
}
