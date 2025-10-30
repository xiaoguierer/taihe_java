package com.cn.taihe.back.product.service;
import com.cn.taihe.back.product.entity.Product;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 商品信息服务接口
 *
 * @author auto generator
 * @since 2024-01-01
 */
@Api(tags = "商品管理接口")
public interface ProductService {

    /**
     * 新增商品
     *
     * @param product 商品信息
     * @return 是否成功
     */
    @ApiOperation("新增商品")
    @Transactional(rollbackFor = Exception.class)
    boolean saveProduct(Product product);

    /**
     * 批量新增商品
     *
     * @param productList 商品列表
     * @return 是否成功
     */
    @ApiOperation("批量新增商品")
    @Transactional(rollbackFor = Exception.class)
    boolean saveBatchProduct(List<Product> productList);

    /**
     * 根据ID删除商品（逻辑删除）
     *
     * @param id 商品ID
     * @return 是否成功
     */
    @ApiOperation("根据ID删除商品")
    @Transactional(rollbackFor = Exception.class)
    boolean deleteProductById(String id);

    /**
     * 批量删除商品（逻辑删除）
     *
     * @param ids 商品ID列表
     * @return 是否成功
     */
    @ApiOperation("批量删除商品")
    @Transactional(rollbackFor = Exception.class)
    boolean deleteBatchProductByIds(List<String> ids);

    /**
     * 更新商品信息
     *
     * @param product 商品信息
     * @return 是否成功
     */
    @ApiOperation("更新商品信息")
    @Transactional(rollbackFor = Exception.class)
    boolean updateProduct(Product product);

    /**
     * 根据ID查询商品信息
     *
     * @param id 商品ID
     * @return 商品信息
     */
    @ApiOperation("根据ID查询商品信息")
    Product getProductById(String id);

    /**
     * 分页查询商品列表
     *
     * @param pageNum 页码
     * @param pageSize 每页数量
     * @param params 查询参数
     * @return 分页商品列表
     */
    @ApiOperation("分页查询商品列表")
    PageInfo<Product> getProductListByPage(int pageNum, int pageSize, Map<String, Object> params);

    /**
     * 根据条件查询商品列表
     *
     * @param params 查询参数
     * @return 商品列表
     */
    @ApiOperation("根据条件查询商品列表")
    List<Product> getProductListByCondition(Map<String, Object> params);

    /**
     * 根据分类ID查询商品列表
     *
     * @param categoryId 分类ID
     * @return 商品列表
     */
    @ApiOperation("根据分类ID查询商品列表")
    List<Product> getProductListByCategoryId(String categoryId);

    /**
     * 根据品牌ID查询商品列表
     *
     * @param brandId 品牌ID
     * @return 商品列表
     */
    @ApiOperation("根据品牌ID查询商品列表")
    List<Product> getProductListByBrandId(String brandId);

    /**
     * 更新商品状态
     *
     * @param id 商品ID
     * @param status 状态(0-下架，1-上架)
     * @return 是否成功
     */
    @ApiOperation("更新商品状态")
    @Transactional(rollbackFor = Exception.class)
    boolean updateProductStatus(String id, Integer status);

    /**
     * 更新商品销量
     *
     * @param id 商品ID
     * @param sales 销量
     * @return 是否成功
     */
    @ApiOperation("更新商品销量")
    @Transactional(rollbackFor = Exception.class)
    boolean updateProductSales(String id, Integer sales);

    /**
     * 批量更新商品状态
     *
     * @param ids 商品ID列表
     * @param status 状态(0-下架，1-上架)
     * @return 是否成功
     */
    @ApiOperation("批量更新商品状态")
    @Transactional(rollbackFor = Exception.class)
    boolean updateBatchProductStatus(List<String> ids, Integer status);

    /**
     * 获取商品总数
     *
     * @param params 查询参数
     * @return 商品总数
     */
    @ApiOperation("获取商品总数")
    long getProductCount(Map<String, Object> params);
}
