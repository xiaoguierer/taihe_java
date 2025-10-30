package com.cn.taihe.back.suppliers.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cn.taihe.back.suppliers.entity.SupplierEvaluation;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
@Repository
public interface EvaluationMapper extends BaseMapper<SupplierEvaluation> {

    /**
     * 根据条件查询评价记录
     * @param supplierId 供应商ID
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @param minScore 最低评分
     * @param maxScore 最高评分
     * @return 评价记录列表
     */
    List<SupplierEvaluation> selectEvaluationsByCondition(
            @Param("supplierId") String supplierId,
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate,
            @Param("minScore") BigDecimal minScore,
            @Param("maxScore") BigDecimal maxScore
    );

    /**
     * 复杂条件查询评价记录
     * @param condition 查询条件Map
     * @return 评价记录列表
     */
    List<SupplierEvaluation> selectEvaluationsByMapCondition(@Param("condition") Map<String, Object> condition);

    /**
     * 批量插入评价记录
     * @param evaluations 评价记录列表
     * @return 影响行数
     */
    int batchInsertEvaluations(@Param("list") List<SupplierEvaluation> evaluations);

    /**
     * 批量删除评价记录
     * @param ids 评价记录ID列表
     * @return 影响行数
     */
    int batchDeleteEvaluations(@Param("ids") List<String> ids);

    /**
     * 根据供应商ID批量删除评价记录
     * @param supplierIds 供应商ID列表
     * @return 影响行数
     */
    int batchDeleteBySupplierIds(@Param("supplierIds") List<String> supplierIds);

    /**
     * 计算供应商平均评分
     * @param supplierId 供应商ID
     * @return 平均评分
     */
    BigDecimal calculateAverageScore(@Param("supplierId") String supplierId);
}
