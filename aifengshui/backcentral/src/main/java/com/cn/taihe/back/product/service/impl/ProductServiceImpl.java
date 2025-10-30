package com.cn.taihe.back.product.service.impl;
import com.cn.taihe.back.product.entity.Product;
import com.cn.taihe.back.product.mapper.ProductMapper;
import com.cn.taihe.back.product.service.ProductService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * 商品信息服务实现类
 *
 * @author auto generator
 * @since 2024-01-01
 */
@Service
public class ProductServiceImpl implements ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    private ProductMapper productMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveProduct(Product product) {
        try {
            logger.info("开始新增商品，商品名称：{}", product.getName());
            int result = productMapper.insert(product);
            if (result > 0) {
                logger.info("新增商品成功，商品ID：{}", product.getId());
                return true;
            }
            logger.error("新增商品失败，商品名称：{}", product.getName());
            return false;
        } catch (Exception e) {
            logger.error("新增商品异常，商品名称：{}，异常信息：{}", product.getName(), e.getMessage(), e);
            throw e;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveBatchProduct(List<Product> productList) {
        if (CollectionUtils.isEmpty(productList)) {
            logger.warn("批量新增商品，商品列表为空");
            return false;
        }
        try {
            logger.info("开始批量新增商品，商品数量：{}", productList.size());
            int result = productMapper.insertBatch(productList);
            if (result == productList.size()) {
                logger.info("批量新增商品成功，商品数量：{}", productList.size());
                return true;
            }
            logger.error("批量新增商品失败，预期数量：{}，实际数量：{}", productList.size(), result);
            return false;
        } catch (Exception e) {
            logger.error("批量新增商品异常，异常信息：{}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteProductById(String id) {
        try {
            logger.info("开始删除商品，商品ID：{}", id);
            int result = productMapper.deleteById(id);
            if (result > 0) {
                logger.info("删除商品成功，商品ID：{}", id);
                return true;
            }
            logger.warn("删除商品失败，商品可能不存在，商品ID：{}", id);
            return false;
        } catch (Exception e) {
            logger.error("删除商品异常，商品ID：{}，异常信息：{}", id, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteBatchProductByIds(List<String> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            logger.warn("批量删除商品，ID列表为空");
            return false;
        }
        try {
            logger.info("开始批量删除商品，ID数量：{}", ids.size());
            int result = productMapper.deleteBatchByIds(ids);
            if (result == ids.size()) {
                logger.info("批量删除商品成功，ID数量：{}", ids.size());
                return true;
            }
            logger.error("批量删除商品失败，预期数量：{}，实际数量：{}", ids.size(), result);
            return false;
        } catch (Exception e) {
            logger.error("批量删除商品异常，异常信息：{}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateProduct(Product product) {
        try {
            logger.info("开始更新商品，商品ID：{}", product.getId());
            int result = productMapper.updateById(product);
            if (result > 0) {
                logger.info("更新商品成功，商品ID：{}", product.getId());
                return true;
            }
            logger.warn("更新商品失败，商品可能不存在，商品ID：{}", product.getId());
            return false;
        } catch (Exception e) {
            logger.error("更新商品异常，商品ID：{}，异常信息：{}", product.getId(), e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Product getProductById(String id) {
        try {
            logger.debug("开始查询商品，商品ID：{}", id);
            Product product = productMapper.selectById(id);
            if (product != null && product.getIsDeleted() == 0) {
                logger.debug("查询商品成功，商品ID：{}", id);
                return product;
            }
            logger.warn("查询商品失败，商品不存在或已删除，商品ID：{}", id);
            return null;
        } catch (Exception e) {
            logger.error("查询商品异常，商品ID：{}，异常信息：{}", id, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public PageInfo<Product> getProductListByPage(int pageNum, int pageSize, Map<String, Object> params) {
        try {
            logger.debug("开始分页查询商品列表，页码：{}，每页数量：{}", pageNum, pageSize);
            PageHelper.startPage(pageNum, pageSize);
            List<Product> productList = productMapper.selectByCondition(params);
            PageInfo<Product> pageInfo = new PageInfo<>(productList);
            logger.debug("分页查询商品列表成功，总数量：{}", pageInfo.getTotal());
            return pageInfo;
        } catch (Exception e) {
            logger.error("分页查询商品列表异常，异常信息：{}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<Product> getProductListByCondition(Map<String, Object> params) {
        try {
            logger.debug("开始根据条件查询商品列表");
            List<Product> productList = productMapper.selectByCondition(params);
            logger.debug("根据条件查询商品列表成功，数量：{}", productList.size());
            return productList;
        } catch (Exception e) {
            logger.error("根据条件查询商品列表异常，异常信息：{}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<Product> getProductListByCategoryId(String categoryId) {
        try {
            logger.debug("开始根据分类ID查询商品列表，分类ID：{}", categoryId);
            List<Product> productList = productMapper.selectByCategoryId(categoryId);
            logger.debug("根据分类ID查询商品列表成功，分类ID：{}，数量：{}", categoryId, productList.size());
            return productList;
        } catch (Exception e) {
            logger.error("根据分类ID查询商品列表异常，分类ID：{}，异常信息：{}", categoryId, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<Product> getProductListByBrandId(String brandId) {
        try {
            logger.debug("开始根据品牌ID查询商品列表，品牌ID：{}", brandId);
            List<Product> productList = productMapper.selectByBrandId(brandId);
            logger.debug("根据品牌ID查询商品列表成功，品牌ID：{}，数量：{}", brandId, productList.size());
            return productList;
        } catch (Exception e) {
            logger.error("根据品牌ID查询商品列表异常，品牌ID：{}，异常信息：{}", brandId, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateProductStatus(String id, Integer status) {
        try {
            logger.info("开始更新商品状态，商品ID：{}，状态：{}", id, status);
            int result = productMapper.updateStatus(id, status);
            if (result > 0) {
                logger.info("更新商品状态成功，商品ID：{}，状态：{}", id, status);
                return true;
            }
            logger.warn("更新商品状态失败，商品可能不存在，商品ID：{}", id);
            return false;
        } catch (Exception e) {
            logger.error("更新商品状态异常，商品ID：{}，异常信息：{}", id, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateProductSales(String id, Integer sales) {
        try {
            logger.info("开始更新商品销量，商品ID：{}，销量：{}", id, sales);
            int result = productMapper.updateSales(id, sales);
            if (result > 0) {
                logger.info("更新商品销量成功，商品ID：{}，销量：{}", id, sales);
                return true;
            }
            logger.warn("更新商品销量失败，商品可能不存在，商品ID：{}", id);
            return false;
        } catch (Exception e) {
            logger.error("更新商品销量异常，商品ID：{}，异常信息：{}", id, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateBatchProductStatus(List<String> ids, Integer status) {
        if (CollectionUtils.isEmpty(ids)) {
            logger.warn("批量更新商品状态，ID列表为空");
            return false;
        }
        try {
            logger.info("开始批量更新商品状态，ID数量：{}，状态：{}", ids.size(), status);
            int successCount = 0;
            for (String id : ids) {
                if (productMapper.updateStatus(id, status) > 0) {
                    successCount++;
                }
            }
            if (successCount == ids.size()) {
                logger.info("批量更新商品状态成功，ID数量：{}", ids.size());
                return true;
            }
            logger.error("批量更新商品状态失败，预期数量：{}，实际成功数量：{}", ids.size(), successCount);
            return false;
        } catch (Exception e) {
            logger.error("批量更新商品状态异常，异常信息：{}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public long getProductCount(Map<String, Object> params) {
        try {
            logger.debug("开始获取商品总数");
            long count = productMapper.countByCondition(params);
            logger.debug("获取商品总数成功，数量：{}", count);
            return count;
        } catch (Exception e) {
            logger.error("获取商品总数异常，异常信息：{}", e.getMessage(), e);
            throw e;
        }
    }
}
