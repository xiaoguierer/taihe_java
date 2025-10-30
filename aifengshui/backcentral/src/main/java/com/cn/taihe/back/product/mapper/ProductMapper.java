package com.cn.taihe.back.product.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cn.taihe.back.product.entity.Product;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 商品信息Mapper接口
 *
 * @author auto generator
 * @since 2024-01-01
 */
@Mapper
@Repository
public interface ProductMapper extends BaseMapper<Product> {

    /**
     * 根据条件动态查询商品列表
     *
     * @param params 查询参数
     * @return 商品列表
     */
    List<Product> selectByCondition(@Param("params") Map<String, Object> params);

    /**
     * 根据条件动态查询商品数量
     *
     * @param params 查询参数
     * @return 商品数量
     */
    @SelectProvider(type = ProductSqlBuilder.class, method = "buildCountByCondition")
    Long countByCondition(@Param("params") Map<String, Object> params);

    /**
     * 根据主键更新指定字段
     *
     * @param product 商品对象
     * @return 更新影响行数
     */
    @UpdateProvider(type = ProductSqlBuilder.class, method = "buildUpdateById")
    int updateByIdSelective(Product product);

    /**
     * 批量插入商品信息
     *
     * @param productList 商品列表
     * @return 插入影响行数
     */
    @Insert("<script>" +
            "INSERT INTO product (id, category_id, brand_id, name, sub_title, main_image, " +
            "detail, specs, price_range, sales, status, is_deleted, create_time, update_time, default_supplier_id) " +
            "VALUES " +
            "<foreach collection='list' item='item' separator=','>" +
            "(#{item.id}, #{item.categoryId}, #{item.brandId}, #{item.name}, #{item.subTitle}, " +
            "#{item.mainImage}, #{item.detail}, #{item.specs}, #{item.priceRange}, #{item.sales}, " +
            "#{item.status}, #{item.isDeleted}, #{item.createTime}, #{item.updateTime}, #{item.defaultSupplierId})" +
            "</foreach>" +
            "</script>")
    int insertBatch(List<Product> productList);

    /**
     * 根据ID列表批量删除商品（逻辑删除）
     *
     * @param ids 商品ID列表
     * @return 删除影响行数
     */
    @Update("<script>" +
            "UPDATE product SET is_deleted = 1 WHERE id IN " +
            "<foreach collection='ids' item='id' open='(' separator=',' close=')'>" +
            "#{id}" +
            "</foreach>" +
            "</script>")
    int deleteBatchByIds(@Param("ids") List<String> ids);

    /**
     * 根据分类ID查询商品列表
     *
     * @param categoryId 分类ID
     * @return 商品列表
     */
    @Select("SELECT * FROM product WHERE category_id = #{categoryId} AND is_deleted = 0")
    List<Product> selectByCategoryId(@Param("categoryId") String categoryId);

    /**
     * 根据品牌ID查询商品列表
     *
     * @param brandId 品牌ID
     * @return 商品列表
     */
    @Select("SELECT * FROM product WHERE brand_id = #{brandId} AND is_deleted = 0")
    List<Product> selectByBrandId(@Param("brandId") String brandId);

    /**
     * 根据状态查询商品列表
     *
     * @param status 状态(0-下架，1-上架)
     * @return 商品列表
     */
    @Select("SELECT * FROM product WHERE status = #{status} AND is_deleted = 0")
    List<Product> selectByStatus(@Param("status") Integer status);

    /**
     * 更新商品销量
     *
     * @param id 商品ID
     * @param sales 销量
     * @return 更新影响行数
     */
    @Update("UPDATE product SET sales = #{sales}, update_time = NOW() WHERE id = #{id} AND is_deleted = 0")
    int updateSales(@Param("id") String id, @Param("sales") Integer sales);

    /**
     * 更新商品状态
     *
     * @param id 商品ID
     * @param status 状态(0-下架，1-上架)
     * @return 更新影响行数
     */
    @Update("UPDATE product SET status = #{status}, update_time = NOW() WHERE id = #{id} AND is_deleted = 0")
    int updateStatus(@Param("id") String id, @Param("status") Integer status);

    /**
     * SQL构建器类
     */
    class ProductSqlBuilder {

        private static final String TABLE_NAME = "product";

        /**
         * 构建动态查询SQL
         */
        public String buildSelectByCondition(Map<String, Object> params) {
            String sql =  new SQL() {{
                SELECT("*");
                FROM(TABLE_NAME);
                WHERE("is_deleted = 0");

                if (params != null) {
                    // 动态条件
                    if (params.get("categoryId") != null) {
                        WHERE("category_id = #{params.categoryId}");
                    }
                    if (params.get("brandId") != null) {
                        WHERE("brand_id = #{params.brandId}");
                    }
                    if (params.get("name") != null) {
                        WHERE("name LIKE CONCAT('%', #{params.name}, '%')");
                    }
                    if (params.get("status") != null) {
                        WHERE("status = #{params.status}");
                    }
                    if (params.get("minSales") != null) {
                        WHERE("sales >= #{params.minSales}");
                    }
                    if (params.get("maxSales") != null) {
                        WHERE("sales <= #{params.maxSales}");
                    }
                    if (params.get("defaultSupplierId") != null) {
                        WHERE("default_supplier_id = #{params.defaultSupplierId}");
                    }
                }

                // 添加排序
                if (params != null && params.get("orderBy") != null) {
                    ORDER_BY(params.get("orderBy").toString());
                } else {
                    ORDER_BY("create_time DESC");
                }
            }}.toString();
            System.out.println("00000000000000000         :"+sql);
            return sql;
        }

        /**
         * 构建动态计数SQL
         */
        public String buildCountByCondition(Map<String, Object> params) {
            return new SQL() {{
                SELECT("COUNT(*)");
                FROM(TABLE_NAME);
                buildWhereClause(this, params);
            }}.toString();
        }

        /**
         * 构建动态更新SQL
         */
        public String buildUpdateById(Product product) {
            return new SQL() {{
                UPDATE(TABLE_NAME);
                // 动态设置更新字段
                if (product.getCategoryId() != null) {
                    SET("category_id = #{categoryId}");
                }
                if (product.getBrandId() != null) {
                    SET("brand_id = #{brandId}");
                }
                if (product.getName() != null) {
                    SET("name = #{name}");
                }
                if (product.getSubTitle() != null) {
                    SET("sub_title = #{subTitle}");
                }
                if (product.getMainImage() != null) {
                    SET("main_image = #{mainImage}");
                }
                if (product.getDetail() != null) {
                    SET("detail = #{detail}");
                }
                if (product.getSpecs() != null) {
                    SET("specs = #{specs}");
                }
                if (product.getPriceRange() != null) {
                    SET("price_range = #{priceRange}");
                }
                if (product.getSales() != null) {
                    SET("sales = #{sales}");
                }
                if (product.getStatus() != null) {
                    SET("status = #{status}");
                }
                if (product.getDefaultSupplierId() != null) {
                    SET("default_supplier_id = #{defaultSupplierId}");
                }
                // 更新时间自动设置
                SET("update_time = NOW()");
                WHERE("id = #{id}");
                WHERE("is_deleted = 0");
            }}.toString();
        }

        /**
         * 构建WHERE条件子句
         */
        private void buildWhereClause(SQL sql, Map<String, Object> params) {
            sql.WHERE("is_deleted = 0");

            if (params.get("id") != null) {
                sql.WHERE("id = #{params.id}");
            }
            if (params.get("categoryId") != null) {
                sql.WHERE("category_id = #{params.categoryId}");
            }
            if (params.get("brandId") != null) {
                sql.WHERE("brand_id = #{params.brandId}");
            }
            if (params.get("name") != null) {
                sql.WHERE("name LIKE CONCAT('%', #{params.name}, '%')");
            }
            if (params.get("status") != null) {
                sql.WHERE("status = #{params.status}");
            }
            if (params.get("minSales") != null) {
                sql.WHERE("sales >= #{params.minSales}");
            }
            if (params.get("maxSales") != null) {
                sql.WHERE("sales <= #{params.maxSales}");
            }
            if (params.get("defaultSupplierId") != null) {
                sql.WHERE("default_supplier_id = #{params.defaultSupplierId}");
            }
            // 价格区间查询
            if (params.get("minPrice") != null || params.get("maxPrice") != null) {
                // 这里需要根据price_range字段进行特殊处理，实际项目中可能需要更复杂的逻辑
            }
        }
    }
}