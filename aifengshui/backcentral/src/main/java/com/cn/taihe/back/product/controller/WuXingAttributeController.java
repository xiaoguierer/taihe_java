package com.cn.taihe.back.product.controller;

import com.cn.taihe.back.product.dto.CreateRelationsRequest;
import com.cn.taihe.back.product.dto.WuXingAttributeCreateDTO;
import com.cn.taihe.back.product.dto.WuXingAttributeQueryDTO;
import com.cn.taihe.back.product.dto.WuXingAttributeUpdateDTO;
import com.cn.taihe.back.product.entity.WuXingAttribute;
import com.cn.taihe.back.product.service.WuXingAttributeService;
import com.cn.taihe.common.Result;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

/**
 * 五行属性控制器
 *
 * @author system
 * @since 2025-02-20
 */
@RestController
@RequestMapping("/wu-xing-attributes")
@Api(tags = "五行属性管理接口")
public class WuXingAttributeController {

  private static final Logger logger = LoggerFactory.getLogger(WuXingAttributeController.class);
  private static final String OPERATOR = "ADMIN";

  @Autowired
  private WuXingAttributeService wuXingAttributeService;

  @GetMapping("getById/{id}")
  @ApiOperation(value = "根据ID查询五行属性详情", notes = "根据主键ID查询五行属性详细信息")
  public ResponseEntity<Object> getById(
    @ApiParam(value = "五行属性ID", required = true, example = "1234567890abcdef")
    @PathVariable String id) {
    logger.info("查询五行属性详情 - 操作人: {}, 参数: id={}", OPERATOR, id);
    if (!StringUtils.hasText(id)) {
      logger.warn("查询五行属性详情 - 参数错误: id为空");
      return ResponseEntity.ok(Result.error("ID不能为空"));
    }
    try {
      WuXingAttribute result = wuXingAttributeService.getById(id);
      if (result == null) {
        logger.info("查询五行属性详情 - 记录不存在: id={}", id);
        return ResponseEntity.ok(Result.error("五行属性不存在"));
      }
      logger.info("查询五行属性详情成功 - 操作人: {}, 参数: id={}", OPERATOR, id);
      return ResponseEntity.ok(Result.success(result));
    } catch (Exception e) {
      logger.error("查询五行属性详情异常 - 操作人: {}, 参数: id={}, 异常信息: {}", OPERATOR, id, e.getMessage(), e);
      return ResponseEntity.ok(Result.error("查询五行属性详情失败"));
    }
  }

  @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @ApiOperation(value = "新增五行属性", notes = "创建新的五行属性记录")
  public ResponseEntity<Object> create(
    @ApiParam(value = "五行属性新增参数", required = true)
    @RequestPart("createDTO") @Valid WuXingAttributeCreateDTO createDTO,
    @RequestPart(value = "symbolIconfile", required = false) MultipartFile symbolIconfile,
    @RequestPart(value = "philosophyImagefile", required = false) MultipartFile philosophyImagefile,
    @RequestPart(value = "energyFlowImagefile", required = false) MultipartFile energyFlowImagefile) {
    logger.info("新增五行属性 - 操作人: {}, 参数: {}", OPERATOR, createDTO);
    if (createDTO == null) {
      logger.warn("新增五行属性 - 参数错误: createDTO为空");
      return ResponseEntity.ok(Result.error("参数不能为空"));
    }
    try {
      boolean result = wuXingAttributeService.create(createDTO,symbolIconfile,philosophyImagefile,energyFlowImagefile);
      if (result) {
        logger.info("新增五行属性成功 - 操作人: {}, 参数: {}", OPERATOR, createDTO);
        return ResponseEntity.ok(Result.success(result));
      } else {
        logger.warn("新增五行属性失败 - 操作人: {}, 参数: {}", OPERATOR, createDTO);
        return ResponseEntity.ok(Result.error("新增五行属性失败"));
      }
    } catch (Exception e) {
      logger.error("新增五行属性异常 - 操作人: {}, 参数: {}, 异常信息: {}", OPERATOR, createDTO, e.getMessage(), e);
      return ResponseEntity.ok(Result.error(e.getMessage()));
    }
  }

  @PutMapping(value = "/updateByid", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @ApiOperation(value = "更新五行属性", notes = "更新已存在的五行属性记录")
  public ResponseEntity<Object> update(
    @ApiParam(value = "五行属性更新参数", required = true)
    @RequestPart("updateDTO") @Valid WuXingAttributeUpdateDTO updateDTO,
    @RequestPart(value = "symbolIconfile", required = false) MultipartFile symbolIconfile,
    @RequestPart(value = "philosophyImagefile", required = false) MultipartFile philosophyImagefile,
    @RequestPart(value = "energyFlowImagefile", required = false) MultipartFile energyFlowImagefile) {
    logger.info("更新五行属性 - 操作人: {}, 参数: {}", OPERATOR, updateDTO);
    if (updateDTO == null || !StringUtils.hasText(updateDTO.getId())) {
      logger.warn("更新五行属性 - 参数错误: updateDTO或id为空");
      return ResponseEntity.ok(Result.error("参数错误"));
    }
    try {
      boolean result = wuXingAttributeService.update(updateDTO,symbolIconfile,philosophyImagefile,energyFlowImagefile);
      if (result) {
        logger.info("更新五行属性成功 - 操作人: {}, 参数: {}", OPERATOR, updateDTO);
        return ResponseEntity.ok(Result.success(result));
      } else {
        logger.warn("更新五行属性失败 - 操作人: {}, 参数: {}", OPERATOR, updateDTO);
        return ResponseEntity.ok(Result.error("更新五行属性失败，记录可能不存在"));
      }
    } catch (Exception e) {
      logger.error("更新五行属性异常 - 操作人: {}, 参数: {}, 异常信息: {}", OPERATOR, updateDTO, e.getMessage(), e);
      return ResponseEntity.ok(Result.error(e.getMessage()));
    }
  }

  /**
   * 新增数据
   */
  @PostMapping(value = "/createRealiations")
  @ApiOperation(value = "新增SPU-情感意图关系")
  public ResponseEntity<Object> createRealiations(
    @RequestBody @Valid CreateRelationsRequest request) {
    logger.info("开始处理新增SPU-情感意图关系请求, 操作人: {}, 参数: {}", OPERATOR, request);
    int result = wuXingAttributeService.createRealiations(request.getSpuId(),request.getIntentIds());
    logger.info("新增SPU-情感意图关系请求处理完成, 操作人: {}, 参数: {}, 影响行数: {}",
      OPERATOR, result);
    return ResponseEntity.ok(Result.success(result));
  }

  @DeleteMapping("/del/{id}")
  @ApiOperation(value = "删除五行属性", notes = "根据ID删除五行属性记录")
  public ResponseEntity<Object> deleteById(
    @ApiParam(value = "五行属性ID", required = true, example = "1234567890abcdef")
    @PathVariable String id) {
    logger.info("删除五行属性 - 操作人: {}, 参数: id={}", OPERATOR, id);
    if (!StringUtils.hasText(id)) {
      logger.warn("删除五行属性 - 参数错误: id为空");
      return ResponseEntity.ok(Result.error("ID不能为空"));
    }
    try {
      boolean result = wuXingAttributeService.deleteById(id);
      if (result) {
        logger.info("删除五行属性成功 - 操作人: {}, 参数: id={}", OPERATOR, id);
        return ResponseEntity.ok(Result.success(result));
      } else {
        logger.warn("删除五行属性失败 - 操作人: {}, 参数: id={}", OPERATOR, id);
        return ResponseEntity.ok(Result.error("删除五行属性失败，记录可能不存在"));
      }
    } catch (Exception e) {
      logger.error("删除五行属性异常 - 操作人: {}, 参数: id={}, 异常信息: {}", OPERATOR, id, e.getMessage(), e);
      return ResponseEntity.ok(Result.error("删除五行属性失败"));
    }
  }

  @PostMapping("/page")
  @ApiOperation(value = "分页查询五行属性列表", notes = "根据条件分页查询五行属性列表")
  public ResponseEntity<Object> getPageList(
    @ApiParam(value = "查询条件")
    @RequestBody(required = false) WuXingAttributeQueryDTO queryDTO,
    @ApiParam(value = "页码", defaultValue = "1")
    @RequestParam(defaultValue = "1") Integer page,
    @ApiParam(value = "每页大小", defaultValue = "10")
    @RequestParam(defaultValue = "10") Integer size) {
    logger.info("分页查询五行属性列表 - 操作人: {}, 参数: queryDTO={}, page={}, size={}", OPERATOR, queryDTO, page, size);
    try {
      PageInfo<WuXingAttribute> result = wuXingAttributeService.getPageList(queryDTO, page, size);
      logger.info("分页查询五行属性列表成功 - 操作人: {}, 参数: queryDTO={}, page={}, size={}, 结果数量: {}",
        OPERATOR, queryDTO, page, size, result.getList().size());
      return ResponseEntity.ok(Result.success(result));
    } catch (Exception e) {
      logger.error("分页查询五行属性列表异常 - 操作人: {}, 参数: queryDTO={}, page={}, size={}, 异常信息: {}",
        OPERATOR, queryDTO, page, size, e.getMessage(), e);
      return ResponseEntity.ok(Result.error("查询五行属性列表失败"));
    }
  }

  @PostMapping("/list")
  @ApiOperation(value = "查询五行属性列表", notes = "根据条件查询五行属性列表（不分页）")
  public ResponseEntity<Object> getList(
    @ApiParam(value = "查询条件")
    @RequestBody(required = false) WuXingAttributeQueryDTO queryDTO) {
    logger.info("查询五行属性列表 - 操作人: {}, 参数: queryDTO={}", OPERATOR, queryDTO);
    try {
      List<WuXingAttribute> result = wuXingAttributeService.getList(queryDTO);
      logger.info("查询五行属性列表成功 - 操作人: {}, 参数: queryDTO={}, 结果数量: {}",
        OPERATOR, queryDTO, result.size());
      return ResponseEntity.ok(Result.success(result));
    } catch (Exception e) {
      logger.error("查询五行属性列表异常 - 操作人: {}, 参数: queryDTO={}, 异常信息: {}",
        OPERATOR, queryDTO, e.getMessage(), e);
      return ResponseEntity.ok(Result.error("查询五行属性列表失败"));
    }
  }

  @GetMapping("/all")
  @ApiOperation(value = "查询所有五行属性", notes = "获取所有五行属性记录")
  public ResponseEntity<Object> getAll() {
    logger.info("查询所有五行属性 - 操作人: {}", OPERATOR);
    try {
      List<WuXingAttribute> result = wuXingAttributeService.getAll();
      logger.info("查询所有五行属性成功 - 操作人: {}, 结果数量: {}", OPERATOR, result.size());
      return ResponseEntity.ok(Result.success(result));
    } catch (Exception e) {
      logger.error("查询所有五行属性异常 - 操作人: {}, 异常信息: {}", OPERATOR, e.getMessage(), e);
      return ResponseEntity.ok(Result.error("查询所有五行属性失败"));
    }
  }

  @GetMapping("/active")
  @ApiOperation(value = "查询所有启用的五行属性", notes = "获取所有启用的五行属性记录")
  public ResponseEntity<Object> getAllActive() {
    logger.info("查询所有启用的五行属性 - 操作人: {}", OPERATOR);
    try {
      List<WuXingAttribute> result = wuXingAttributeService.getAllActive();
      logger.info("查询所有启用的五行属性成功 - 操作人: {}, 结果数量: {}", OPERATOR, result.size());
      return ResponseEntity.ok(Result.success(result));
    } catch (Exception e) {
      logger.error("查询所有启用的五行属性异常 - 操作人: {}, 异常信息: {}", OPERATOR, e.getMessage(), e);
      return ResponseEntity.ok(Result.error("查询启用的五行属性失败"));
    }
  }

  @PostMapping("/key-info")
  @ApiOperation(value = "查询关键信息列表", notes = "查询五行属性关键信息列表（简化字段）")
  public ResponseEntity<Object> getKeyInfoList(
    @ApiParam(value = "查询条件")
    @RequestBody(required = false) WuXingAttributeQueryDTO queryDTO) {
    logger.info("查询关键信息列表 - 操作人: {}, 参数: queryDTO={}", OPERATOR, queryDTO);
    try {
      List<WuXingAttribute> result = wuXingAttributeService.getKeyInfoList(queryDTO);
      logger.info("查询关键信息列表成功 - 操作人: {}, 参数: queryDTO={}, 结果数量: {}",
        OPERATOR, queryDTO, result.size());
      return ResponseEntity.ok(Result.success(result));
    } catch (Exception e) {
      logger.error("查询关键信息列表异常 - 操作人: {}, 参数: queryDTO={}, 异常信息: {}",
        OPERATOR, queryDTO, e.getMessage(), e);
      return ResponseEntity.ok(Result.error("查询关键信息列表失败"));
    }
  }

  @GetMapping("/category/{category}")
  @ApiOperation(value = "按分类查询五行属性", notes = "根据元素分类查询启用的五行属性")
  public ResponseEntity<Object> getByCategory(
    @ApiParam(value = "元素分类", required = true, example = "basic")
    @PathVariable String category) {
    logger.info("按分类查询五行属性 - 操作人: {}, 参数: category={}", OPERATOR, category);
    if (!StringUtils.hasText(category)) {
      logger.warn("按分类查询五行属性 - 参数错误: category为空");
      return ResponseEntity.ok(Result.error("分类不能为空"));
    }
    try {
      List<WuXingAttribute> result = wuXingAttributeService.getByCategory(category);
      logger.info("按分类查询五行属性成功 - 操作人: {}, 参数: category={}, 结果数量: {}",
        OPERATOR, category, result.size());
      return ResponseEntity.ok(Result.success(result));
    } catch (Exception e) {
      logger.error("按分类查询五行属性异常 - 操作人: {}, 参数: category={}, 异常信息: {}",
        OPERATOR, category, e.getMessage(), e);
      return ResponseEntity.ok(Result.error("按分类查询五行属性失败"));
    }
  }

  @GetMapping("/tier/{tier}")
  @ApiOperation(value = "按层级查询五行属性", notes = "根据元素层级查询启用的五行属性")
  public ResponseEntity<Object> getByTier(
    @ApiParam(value = "元素层级", required = true, example = "1")
    @PathVariable Integer tier) {
    logger.info("按层级查询五行属性 - 操作人: {}, 参数: tier={}", OPERATOR, tier);
    if (tier == null) {
      logger.warn("按层级查询五行属性 - 参数错误: tier为空");
      return ResponseEntity.ok(Result.error("层级不能为空"));
    }
    try {
      List<WuXingAttribute> result = wuXingAttributeService.getByTier(tier);
      logger.info("按层级查询五行属性成功 - 操作人: {}, 参数: tier={}, 结果数量: {}",
        OPERATOR, tier, result.size());
      return ResponseEntity.ok(Result.success(result));
    } catch (Exception e) {
      logger.error("按层级查询五行属性异常 - 操作人: {}, 参数: tier={}, 异常信息: {}",
        OPERATOR, tier, e.getMessage(), e);
      return ResponseEntity.ok(Result.error("按层级查询五行属性失败"));
    }
  }

  @PostMapping("/batch-delete")
  @ApiOperation(value = "批量删除五行属性", notes = "根据ID集合批量删除五行属性")
  public ResponseEntity<Object> deleteBatch(
    @ApiParam(value = "五行属性ID集合", required = true)
    @RequestBody List<String> ids) {
    logger.info("批量删除五行属性 - 操作人: {}, 参数: ids={}", OPERATOR, ids);
    if (CollectionUtils.isEmpty(ids)) {
      logger.warn("批量删除五行属性 - 参数错误: ids为空");
      return ResponseEntity.ok(Result.error("ID集合不能为空"));
    }
    try {
      boolean result = wuXingAttributeService.deleteBatch(ids);
      if (result) {
        logger.info("批量删除五行属性成功 - 操作人: {}, 参数: ids={}", OPERATOR, ids);
        return ResponseEntity.ok(Result.success(result));
      } else {
        logger.warn("批量删除五行属性失败 - 操作人: {}, 参数: ids={}", OPERATOR, ids);
        return ResponseEntity.ok(Result.error("批量删除五行属性失败"));
      }
    } catch (Exception e) {
      logger.error("批量删除五行属性异常 - 操作人: {}, 参数: ids={}, 异常信息: {}", OPERATOR, ids, e.getMessage(), e);
      return ResponseEntity.ok(Result.error("批量删除五行属性失败"));
    }
  }

  @PutMapping("/{id}/freeze")
  @ApiOperation(value = "冻结五行属性", notes = "根据ID冻结五行属性")
  public ResponseEntity<Object> freeze(
    @ApiParam(value = "五行属性ID", required = true, example = "1234567890abcdef")
    @PathVariable String id) {
    logger.info("冻结五行属性 - 操作人: {}, 参数: id={}", OPERATOR, id);
    if (!StringUtils.hasText(id)) {
      logger.warn("冻结五行属性 - 参数错误: id为空");
      return ResponseEntity.ok(Result.error("ID不能为空"));
    }
    try {
      boolean result = wuXingAttributeService.freeze(id);
      if (result) {
        logger.info("冻结五行属性成功 - 操作人: {}, 参数: id={}", OPERATOR, id);
        return ResponseEntity.ok(Result.success(result));
      } else {
        logger.warn("冻结五行属性失败 - 操作人: {}, 参数: id={}", OPERATOR, id);
        return ResponseEntity.ok(Result.error("冻结五行属性失败，记录可能不存在"));
      }
    } catch (Exception e) {
      logger.error("冻结五行属性异常 - 操作人: {}, 参数: id={}, 异常信息: {}", OPERATOR, id, e.getMessage(), e);
      return ResponseEntity.ok(Result.error("冻结五行属性失败"));
    }
  }

  @PutMapping("/{id}/enable")
  @ApiOperation(value = "启用五行属性", notes = "根据ID启用五行属性")
  public ResponseEntity<Object> enable(
    @ApiParam(value = "五行属性ID", required = true, example = "1234567890abcdef")
    @PathVariable String id) {
    logger.info("启用五行属性 - 操作人: {}, 参数: id={}", OPERATOR, id);
    if (!StringUtils.hasText(id)) {
      logger.warn("启用五行属性 - 参数错误: id为空");
      return ResponseEntity.ok(Result.error("ID不能为空"));
    }
    try {
      boolean result = wuXingAttributeService.enable(id);
      if (result) {
        logger.info("启用五行属性成功 - 操作人: {}, 参数: id={}", OPERATOR, id);
        return ResponseEntity.ok(Result.success(result));
      } else {
        logger.warn("启用五行属性失败 - 操作人: {}, 参数: id={}", OPERATOR, id);
        return ResponseEntity.ok(Result.error("启用五行属性失败，记录可能不存在"));
      }
    } catch (Exception e) {
      logger.error("启用五行属性异常 - 操作人: {}, 参数: id={}, 异常信息: {}", OPERATOR, id, e.getMessage(), e);
      return ResponseEntity.ok(Result.error("启用五行属性失败"));
    }
  }

  @PostMapping("/batch-freeze")
  @ApiOperation(value = "批量冻结五行属性", notes = "根据ID集合批量冻结五行属性")
  public ResponseEntity<Object> freezeBatch(
    @ApiParam(value = "五行属性ID集合", required = true)
    @RequestBody List<String> ids) {
    logger.info("批量冻结五行属性 - 操作人: {}, 参数: ids={}", OPERATOR, ids);
    if (CollectionUtils.isEmpty(ids)) {
      logger.warn("批量冻结五行属性 - 参数错误: ids为空");
      return ResponseEntity.ok(Result.error("ID集合不能为空"));
    }
    try {
      boolean result = wuXingAttributeService.freezeBatch(ids);
      if (result) {
        logger.info("批量冻结五行属性成功 - 操作人: {}, 参数: ids={}", OPERATOR, ids);
        return ResponseEntity.ok(Result.success(result));
      } else {
        logger.warn("批量冻结五行属性失败 - 操作人: {}, 参数: ids={}", OPERATOR, ids);
        return ResponseEntity.ok(Result.error("批量冻结五行属性失败"));
      }
    } catch (Exception e) {
      logger.error("批量冻结五行属性异常 - 操作人: {}, 参数: ids={}, 异常信息: {}", OPERATOR, ids, e.getMessage(), e);
      return ResponseEntity.ok(Result.error("批量冻结五行属性失败"));
    }
  }

  @PostMapping("/batch-enable")
  @ApiOperation(value = "批量启用五行属性", notes = "根据ID集合批量启用五行属性")
  public ResponseEntity<Object> enableBatch(
    @ApiParam(value = "五行属性ID集合", required = true)
    @RequestBody List<String> ids) {
    logger.info("批量启用五行属性 - 操作人: {}, 参数: ids={}", OPERATOR, ids);
    if (CollectionUtils.isEmpty(ids)) {
      logger.warn("批量启用五行属性 - 参数错误: ids为空");
      return ResponseEntity.ok(Result.error("ID集合不能为空"));
    }
    try {
      boolean result = wuXingAttributeService.enableBatch(ids);
      if (result) {
        logger.info("批量启用五行属性成功 - 操作人: {}, 参数: ids={}", OPERATOR, ids);
        return ResponseEntity.ok(Result.success(result));
      } else {
        logger.warn("批量启用五行属性失败 - 操作人: {}, 参数: ids={}", OPERATOR, ids);
        return ResponseEntity.ok(Result.error("批量启用五行属性失败"));
      }
    } catch (Exception e) {
      logger.error("批量启用五行属性异常 - 操作人: {}, 参数: ids={}, 异常信息: {}", OPERATOR, ids, e.getMessage(), e);
      return ResponseEntity.ok(Result.error("批量启用五行属性失败"));
    }
  }

  @GetMapping("/element-key/{elementKey}")
  @ApiOperation(value = "按元素键名查询", notes = "根据元素键名查询五行属性")
  public ResponseEntity<Object> getByElementKey(
    @ApiParam(value = "元素键名", required = true, example = "metal")
    @PathVariable String elementKey) {
    logger.info("按元素键名查询 - 操作人: {}, 参数: elementKey={}", OPERATOR, elementKey);
    if (!StringUtils.hasText(elementKey)) {
      logger.warn("按元素键名查询 - 参数错误: elementKey为空");
      return ResponseEntity.ok(Result.error("元素键名不能为空"));
    }
    try {
      WuXingAttribute result = wuXingAttributeService.getByElementKey(elementKey);
      if (result == null) {
        logger.info("按元素键名查询 - 记录不存在: elementKey={}", elementKey);
        return ResponseEntity.ok(Result.error("五行属性不存在"));
      }
      logger.info("按元素键名查询成功 - 操作人: {}, 参数: elementKey={}", OPERATOR, elementKey);
      return ResponseEntity.ok(Result.success(result));
    } catch (Exception e) {
      logger.error("按元素键名查询异常 - 操作人: {}, 参数: elementKey={}, 异常信息: {}",
        OPERATOR, elementKey, e.getMessage(), e);
      return ResponseEntity.ok(Result.error("按元素键名查询失败"));
    }
  }
  /**
   * @description:
   * 根据商品spu查询所属五行信息
   **/

  @GetMapping("/selectBySpuID/{productId}")
  @ApiOperation(value = "商品spu id查询五行属性", notes = "商品spu id查询启用的五行属性")
  public ResponseEntity<Object> selectBySpuID(
    @ApiParam(value = "商品spu id", required = true)
    @PathVariable String productId) {
    logger.info("按商品spu id 查询五行属性 - 操作人: {}, 参数: productId={}", OPERATOR, productId);
    if (productId == null) {
      logger.warn("查询五行属性 - 参数错误: productId为空");
      return ResponseEntity.ok(Result.error("productId不能为空"));
    }
    try {
      List<WuXingAttribute> result = wuXingAttributeService.selectBySpuID(productId);
      logger.info("查询五行属性成功 - 操作人: {}, 参数: productId={}, 结果数量: {}",
        OPERATOR, productId, result.size());
      return ResponseEntity.ok(Result.success(result));
    } catch (Exception e) {
      logger.error("查询五行属性异常 - 操作人: {}, 参数: productId={}, 异常信息: {}",
        OPERATOR, productId, e.getMessage(), e);
      return ResponseEntity.ok(Result.error("查询五行属性失败"));
    }
  }
}
