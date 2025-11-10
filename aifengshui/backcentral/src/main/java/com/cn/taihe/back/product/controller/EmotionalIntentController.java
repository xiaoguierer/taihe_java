package com.cn.taihe.back.product.controller;

import com.cn.taihe.back.product.dto.EmotionalIntentCreateDTO;
import com.cn.taihe.back.product.dto.EmotionalIntentQueryDTO;
import com.cn.taihe.back.product.dto.EmotionalIntentUpdateDTO;
import com.cn.taihe.back.product.entity.EmotionalIntent;
import com.cn.taihe.back.product.service.EmotionalIntentService;
import com.cn.taihe.common.Result;
import com.cn.taihe.common.objectutils.PageInfoJsonConverter;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

/**
 * 情感意图管理控制器
 *
 * @author system
 * @date 2025-11-06
 */
@RestController
@RequestMapping("/emotional-intent")
@Api(tags = "情感意图管理控制器")
@Validated
public class EmotionalIntentController {

  private static final Logger logger = LoggerFactory.getLogger(EmotionalIntentController.class);

  private static final String OPERATOR = "ADMIN"; // 默认操作人
  @Autowired
  private PageInfoJsonConverter pageInfoJsonConverter;

  @Autowired
  private EmotionalIntentService emotionalIntentService;

  /**
   * 根据ID查询情感意图详情
   */
  @GetMapping("/getByid/{id}")
  @ApiOperation(value = "根据ID查询情感意图详情", notes = "根据主键ID查询情感意图的详细信息")
  public ResponseEntity<Object> getById(
    @ApiParam(value = "情感意图ID", required = true, example = "1234567890")
    @PathVariable String id) {

    logger.info("查询情感意图详情接口调用开始，操作人：{}，参数：id={}", OPERATOR, id);

    try {
      if (!StringUtils.hasText(id)) {
        logger.warn("查询情感意图详情接口参数验证失败，ID不能为空，操作人：{}", OPERATOR);
        return ResponseEntity.badRequest().body(Result.error("ID不能为空"));
      }
      EmotionalIntent result = emotionalIntentService.getById(id);
      if (result == null) {
        logger.info("查询情感意图详情接口未找到数据，操作人：{}，ID：{}", OPERATOR, id);
        return ResponseEntity.ok(Result.success("未找到对应数据"));
      }
      logger.info("查询情感意图详情接口调用成功，操作人：{}，结果：{}", OPERATOR, result);
      return ResponseEntity.ok(Result.success(result));
    } catch (IllegalArgumentException e) {
      logger.error("查询情感意图详情接口参数错误，操作人：{}，参数：id={}，异常信息：", OPERATOR, id, e);
      return ResponseEntity.badRequest().body(Result.error(e.getMessage()));
    } catch (Exception e) {
      logger.error("查询情感意图详情接口异常，操作人：{}，参数：id={}，异常信息：", OPERATOR, id, e);
      return ResponseEntity.badRequest().body(Result.error("查询情感意图详情失败"));
    }
  }

  /**
   * 分页查询情感意图列表
   */
  @PostMapping("/page")
  @ApiOperation(value = "分页查询情感意图列表", notes = "根据查询条件分页查询情感意图列表")
  public ResponseEntity<Object> getByPage(
    @ApiParam(value = "查询条件")
    @Valid @RequestBody EmotionalIntentQueryDTO queryDTO,
    @ApiParam(value = "页码，默认1", example = "1")
    @RequestParam(defaultValue = "1") @Min(value = 1, message = "页码必须大于等于1") Integer page,
    @ApiParam(value = "每页大小，默认10", example = "10")
    @RequestParam(defaultValue = "10") @Min(value = 1, message = "每页大小必须大于等于1") Integer size) {

    logger.info("分页查询情感意图列表接口调用开始，操作人：{}，参数：page={}, size={}, queryDTO={}",
      OPERATOR, page, size, queryDTO);

    try {
      PageInfo<EmotionalIntent> pageInfo = emotionalIntentService.getByPage(queryDTO, page, size);
      logger.info("分页查询情感意图列表接口调用成功，操作人：{}，总记录数：{}，当前页记录数：{}",
        OPERATOR, pageInfo.getTotal(), pageInfo.getSize());
      logger.info("---------操作结果----------: " + pageInfoJsonConverter.convertToJson(pageInfo));
      return ResponseEntity.ok(Result.success(pageInfo));

    } catch (Exception e) {
      logger.error("分页查询情感意图列表接口异常，操作人：{}，参数：page={}, size={}, queryDTO={}，异常信息：",
        OPERATOR, page, size, queryDTO, e);
      return ResponseEntity.badRequest().body(Result.error("分页查询情感意图列表失败"));
    }
  }

  /**
   * 查询所有情感意图列表
   */
  @GetMapping("/list")
  @ApiOperation(value = "查询所有情感意图列表", notes = "查询所有启用状态的情感意图列表")
  public ResponseEntity<Object> getAll() {
    logger.info("查询所有情感意图列表接口调用开始，操作人：{}", OPERATOR);

    try {
      List<EmotionalIntent> result = emotionalIntentService.getAll();
      logger.info("查询所有情感意图列表接口调用成功，操作人：{}，记录数：{}", OPERATOR, result.size());
      return ResponseEntity.ok(Result.success(result));
    } catch (Exception e) {
      logger.error("查询所有情感意图列表接口异常，操作人：{}，异常信息：", OPERATOR, e);
      return ResponseEntity.badRequest().body(Result.error("查询所有情感意图列表失败"));
    }
  }

  /**
   * 查询所有情感意图关键信息
   */
  @GetMapping("/key-info")
  @ApiOperation(value = "查询所有情感意图关键信息", notes = "查询所有情感意图的关键信息（简化字段）")
  public ResponseEntity<Object> getAllKeyInfo() {
    logger.info("查询所有情感意图关键信息接口调用开始，操作人：{}", OPERATOR);

    try {
      List<EmotionalIntent> result = emotionalIntentService.getAllKeyInfo();

      logger.info("查询所有情感意图关键信息接口调用成功，操作人：{}，记录数：{}", OPERATOR, result.size());
      return ResponseEntity.ok(Result.success(result));

    } catch (Exception e) {
      logger.error("查询所有情感意图关键信息接口异常，操作人：{}，异常信息：", OPERATOR, e);
      return ResponseEntity.badRequest().body(Result.error("查询所有情感意图关键信息失败"));
    }
  }

  /**
   * 新增情感意图
   */
  @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @ApiOperation(value = "新增情感意图", notes = "创建新的情感意图")
  public ResponseEntity<Object> create(
    @ApiParam(value = "新增参数", required = true)
    @RequestPart("createDTO") @Valid EmotionalIntentCreateDTO createDTO,
    @RequestPart(value = "iconfile", required = false) MultipartFile iconfile,
    @RequestPart(value = "symbolImageFile", required = false) MultipartFile symbolImageFile,
    @RequestPart(value = "energyImageFile", required = false) MultipartFile energyImageFile,
    @RequestPart(value = "applicationImageFile", required = false) MultipartFile applicationImageFile,
    @RequestPart(value = "meditationImageFile", required = false) MultipartFile meditationImageFile) {

    logger.info("新增情感意图接口调用开始，操作人：{}，参数：{}", OPERATOR, createDTO);
    try {
      String id = emotionalIntentService.create(createDTO,iconfile,symbolImageFile,energyImageFile,applicationImageFile,meditationImageFile);
      logger.info("新增情感意图接口调用成功，操作人：{}，生成的ID：{}", OPERATOR, id);
      return ResponseEntity.ok(Result.success(id));
    } catch (IllegalArgumentException e) {
      logger.error("新增情感意图接口参数错误，操作人：{}，参数：{}，异常信息：", OPERATOR, createDTO, e);
      return ResponseEntity.badRequest().body(Result.error(e.getMessage()));
    } catch (RuntimeException e) {
      logger.error("新增情感意图接口业务异常，操作人：{}，参数：{}，异常信息：", OPERATOR, createDTO, e);
      return ResponseEntity.badRequest().body(Result.error(e.getMessage()));
    } catch (Exception e) {
      logger.error("新增情感意图接口异常，操作人：{}，参数：{}，异常信息：", OPERATOR, createDTO, e);
      return ResponseEntity.badRequest().body(Result.error("新增情感意图失败"));
    }
  }

  /**
   * 更新情感意图信息
   */
  @PutMapping(value = "/updateByid", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @ApiOperation(value = "更新情感意图信息", notes = "更新情感意图的详细信息")
  public ResponseEntity<Object> update(
    @ApiParam(value = "更新参数", required = true)
    @RequestPart("updateDTO") @Valid EmotionalIntentUpdateDTO updateDTO,
    @RequestPart(value = "iconfile", required = false) MultipartFile iconfile,
    @RequestPart(value = "symbolImageFile", required = false) MultipartFile symbolImageFile,
    @RequestPart(value = "energyImageFile", required = false) MultipartFile energyImageFile,
    @RequestPart(value = "applicationImageFile", required = false) MultipartFile applicationImageFile,
    @RequestPart(value = "meditationImageFile", required = false) MultipartFile meditationImageFile) {
    logger.info("更新情感意图信息接口调用开始，操作人：{}，参数：{}", OPERATOR, updateDTO);
    try {
      Boolean result = emotionalIntentService.update(updateDTO,iconfile,symbolImageFile,energyImageFile,applicationImageFile,meditationImageFile);
      if (result) {
        logger.info("更新情感意图信息接口调用成功，操作人：{}，ID：{}", OPERATOR, updateDTO.getId());
        return ResponseEntity.ok(Result.success(result));
      } else {
        logger.warn("更新情感意图信息接口调用失败，操作人：{}，ID：{}", OPERATOR, updateDTO.getId());
        return ResponseEntity.ok(Result.error("更新失败，数据可能不存在"));
      }
    } catch (IllegalArgumentException e) {
      logger.error("更新情感意图信息接口参数错误，操作人：{}，参数：{}，异常信息：", OPERATOR, updateDTO, e);
      return ResponseEntity.badRequest().body(Result.error(e.getMessage()));
    } catch (RuntimeException e) {
      logger.error("更新情感意图信息接口业务异常，操作人：{}，参数：{}，异常信息：", OPERATOR, updateDTO, e);
      return ResponseEntity.badRequest().body(Result.error(e.getMessage()));
    } catch (Exception e) {
      logger.error("更新情感意图信息接口异常，操作人：{}，参数：{}，异常信息：", OPERATOR, updateDTO, e);
      return ResponseEntity.badRequest().body(Result.error("更新情感意图信息失败"));
    }
  }

  /**
   * 根据ID删除情感意图（逻辑删除）
   */
  @DeleteMapping("/del/{id}")
  @ApiOperation(value = "删除情感意图", notes = "根据ID逻辑删除情感意图")
  public ResponseEntity<Object> deleteById(
    @ApiParam(value = "情感意图ID", required = true, example = "1234567890")
    @PathVariable String id) {
    logger.info("删除情感意图接口调用开始，操作人：{}，参数：id={}", OPERATOR, id);
    try {
      if (!StringUtils.hasText(id)) {
        logger.warn("删除情感意图接口参数验证失败，ID不能为空，操作人：{}", OPERATOR);
        return ResponseEntity.badRequest().body(Result.error("ID不能为空"));
      }
      Boolean result = emotionalIntentService.deleteById(id);
      if (result) {
        logger.info("删除情感意图接口调用成功，操作人：{}，ID：{}", OPERATOR, id);
        return ResponseEntity.ok(Result.success(result));
      } else {
        logger.warn("删除情感意图接口调用失败，操作人：{}，ID：{}", OPERATOR, id);
        return ResponseEntity.ok(Result.error("删除失败，数据可能不存在"));
      }

    } catch (IllegalArgumentException e) {
      logger.error("删除情感意图接口参数错误，操作人：{}，参数：id={}，异常信息：", OPERATOR, id, e);
      return ResponseEntity.badRequest().body(Result.error(e.getMessage()));
    } catch (Exception e) {
      logger.error("删除情感意图接口异常，操作人：{}，参数：id={}，异常信息：", OPERATOR, id, e);
      return ResponseEntity.badRequest().body(Result.error("删除情感意图失败"));
    }
  }

  /**
   * 批量删除情感意图
   */
  @PostMapping("/batch-delete")
  @ApiOperation(value = "批量删除情感意图", notes = "批量逻辑删除情感意图")
  public ResponseEntity<Object> deleteBatch(
    @ApiParam(value = "情感意图ID列表", required = true)
    @RequestBody List<String> ids) {

    logger.info("批量删除情感意图接口调用开始，操作人：{}，参数：ids={}", OPERATOR, ids);

    try {
      if (ids == null || ids.isEmpty()) {
        logger.warn("批量删除情感意图接口参数验证失败，ID列表不能为空，操作人：{}", OPERATOR);
        return ResponseEntity.badRequest().body(Result.error("ID列表不能为空"));
      }

      Integer result = emotionalIntentService.deleteBatch(ids);

      logger.info("批量删除情感意图接口调用成功，操作人：{}，删除数量：{}", OPERATOR, result);
      return ResponseEntity.ok(Result.success(result));

    } catch (IllegalArgumentException e) {
      logger.error("批量删除情感意图接口参数错误，操作人：{}，参数：ids={}，异常信息：", OPERATOR, ids, e);
      return ResponseEntity.badRequest().body(Result.error(e.getMessage()));
    } catch (Exception e) {
      logger.error("批量删除情感意图接口异常，操作人：{}，参数：ids={}，异常信息：", OPERATOR, ids, e);
      return ResponseEntity.badRequest().body(Result.error("批量删除情感意图失败"));
    }
  }

  /**
   * 冻结情感意图
   */
  @PutMapping("/freeze/{id}")
  @ApiOperation(value = "冻结情感意图", notes = "将情感意图状态设置为冻结（0）")
  public ResponseEntity<Object> freeze(
    @ApiParam(value = "情感意图ID", required = true, example = "1234567890")
    @PathVariable String id) {

    logger.info("冻结情感意图接口调用开始，操作人：{}，参数：id={}", OPERATOR, id);

    try {
      if (!StringUtils.hasText(id)) {
        logger.warn("冻结情感意图接口参数验证失败，ID不能为空，操作人：{}", OPERATOR);
        return ResponseEntity.badRequest().body(Result.error("ID不能为空"));
      }

      Boolean result = emotionalIntentService.freeze(id);

      if (result) {
        logger.info("冻结情感意图接口调用成功，操作人：{}，ID：{}", OPERATOR, id);
        return ResponseEntity.ok(Result.success(result));
      } else {
        logger.warn("冻结情感意图接口调用失败，操作人：{}，ID：{}", OPERATOR, id);
        return ResponseEntity.ok(Result.error("冻结失败，数据可能不存在"));
      }

    } catch (IllegalArgumentException e) {
      logger.error("冻结情感意图接口参数错误，操作人：{}，参数：id={}，异常信息：", OPERATOR, id, e);
      return ResponseEntity.badRequest().body(Result.error(e.getMessage()));
    } catch (RuntimeException e) {
      logger.error("冻结情感意图接口业务异常，操作人：{}，参数：id={}，异常信息：", OPERATOR, id, e);
      return ResponseEntity.badRequest().body(Result.error(e.getMessage()));
    } catch (Exception e) {
      logger.error("冻结情感意图接口异常，操作人：{}，参数：id={}，异常信息：", OPERATOR, id, e);
      return ResponseEntity.badRequest().body(Result.error("冻结情感意图失败"));
    }
  }

  /**
   * 启用情感意图
   */
  @PutMapping("/activate/{id}")
  @ApiOperation(value = "启用情感意图", notes = "将情感意图状态设置为启用（1）")
  public ResponseEntity<Object> activate(
    @ApiParam(value = "情感意图ID", required = true, example = "1234567890")
    @PathVariable String id) {

    logger.info("启用情感意图接口调用开始，操作人：{}，参数：id={}", OPERATOR, id);

    try {
      if (!StringUtils.hasText(id)) {
        logger.warn("启用情感意图接口参数验证失败，ID不能为空，操作人：{}", OPERATOR);
        return ResponseEntity.badRequest().body(Result.error("ID不能为空"));
      }

      Boolean result = emotionalIntentService.activate(id);

      if (result) {
        logger.info("启用情感意图接口调用成功，操作人：{}，ID：{}", OPERATOR, id);
        return ResponseEntity.ok(Result.success(result));
      } else {
        logger.warn("启用情感意图接口调用失败，操作人：{}，ID：{}", OPERATOR, id);
        return ResponseEntity.ok(Result.error("启用失败，数据可能不存在"));
      }

    } catch (IllegalArgumentException e) {
      logger.error("启用情感意图接口参数错误，操作人：{}，参数：id={}，异常信息：", OPERATOR, id, e);
      return ResponseEntity.badRequest().body(Result.error(e.getMessage()));
    } catch (RuntimeException e) {
      logger.error("启用情感意图接口业务异常，操作人：{}，参数：id={}，异常信息：", OPERATOR, id, e);
      return ResponseEntity.badRequest().body(Result.error(e.getMessage()));
    } catch (Exception e) {
      logger.error("启用情感意图接口异常，操作人：{}，参数：id={}，异常信息：", OPERATOR, id, e);
      return ResponseEntity.badRequest().body(Result.error("启用情感意图失败"));
    }
  }

  /**
   * 批量冻结情感意图
   */
  @PostMapping("/batch-freeze")
  @ApiOperation(value = "批量冻结情感意图", notes = "批量将情感意图状态设置为冻结")
  public ResponseEntity<Object> freezeBatch(
    @ApiParam(value = "情感意图ID列表", required = true)
    @RequestBody List<String> ids) {

    logger.info("批量冻结情感意图接口调用开始，操作人：{}，参数：ids={}", OPERATOR, ids);

    try {
      if (ids == null || ids.isEmpty()) {
        logger.warn("批量冻结情感意图接口参数验证失败，ID列表不能为空，操作人：{}", OPERATOR);
        return ResponseEntity.badRequest().body(Result.error("ID列表不能为空"));
      }

      Integer result = emotionalIntentService.freezeBatch(ids);

      logger.info("批量冻结情感意图接口调用成功，操作人：{}，冻结数量：{}", OPERATOR, result);
      return ResponseEntity.ok(Result.success(result));

    } catch (IllegalArgumentException e) {
      logger.error("批量冻结情感意图接口参数错误，操作人：{}，参数：ids={}，异常信息：", OPERATOR, ids, e);
      return ResponseEntity.badRequest().body(Result.error(e.getMessage()));
    } catch (Exception e) {
      logger.error("批量冻结情感意图接口异常，操作人：{}，参数：ids={}，异常信息：", OPERATOR, ids, e);
      return ResponseEntity.badRequest().body(Result.error("批量冻结情感意图失败"));
    }
  }

  /**
   * 批量启用情感意图
   */
  @PostMapping("/batch-activate")
  @ApiOperation(value = "批量启用情感意图", notes = "批量将情感意图状态设置为启用")
  public ResponseEntity<Object> activateBatch(
    @ApiParam(value = "情感意图ID列表", required = true)
    @RequestBody List<String> ids) {

    logger.info("批量启用情感意图接口调用开始，操作人：{}，参数：ids={}", OPERATOR, ids);

    try {
      if (ids == null || ids.isEmpty()) {
        logger.warn("批量启用情感意图接口参数验证失败，ID列表不能为空，操作人：{}", OPERATOR);
        return ResponseEntity.badRequest().body(Result.error("ID列表不能为空"));
      }

      Integer result = emotionalIntentService.activateBatch(ids);

      logger.info("批量启用情感意图接口调用成功，操作人：{}，启用数量：{}", OPERATOR, result);
      return ResponseEntity.ok(Result.success(result));

    } catch (IllegalArgumentException e) {
      logger.error("批量启用情感意图接口参数错误，操作人：{}，参数：ids={}，异常信息：", OPERATOR, ids, e);
      return ResponseEntity.badRequest().body(Result.error(e.getMessage()));
    } catch (Exception e) {
      logger.error("批量启用情感意图接口异常，操作人：{}，参数：ids={}，异常信息：", OPERATOR, ids, e);
      return ResponseEntity.badRequest().body(Result.error("批量启用情感意图失败"));
    }
  }

  /**
   * 更新排序值
   */
  @PutMapping("/sort-order/{id}")
  @ApiOperation(value = "更新排序值", notes = "更新情感意图的排序值")
  public ResponseEntity<Object> updateSortOrder(
    @ApiParam(value = "情感意图ID", required = true, example = "1234567890")
    @PathVariable String id,
    @ApiParam(value = "排序值", required = true, example = "10")
    @RequestParam @Min(value = 0, message = "排序值必须大于等于0") Integer sortOrder) {

    logger.info("更新排序值接口调用开始，操作人：{}，参数：id={}, sortOrder={}", OPERATOR, id, sortOrder);

    try {
      if (!StringUtils.hasText(id)) {
        logger.warn("更新排序值接口参数验证失败，ID不能为空，操作人：{}", OPERATOR);
        return ResponseEntity.badRequest().body(Result.error("ID不能为空"));
      }

      Boolean result = emotionalIntentService.updateSortOrder(id, sortOrder);

      if (result) {
        logger.info("更新排序值接口调用成功，操作人：{}，ID：{}，排序值：{}", OPERATOR, id, sortOrder);
        return ResponseEntity.ok(Result.success(result));
      } else {
        logger.warn("更新排序值接口调用失败，操作人：{}，ID：{}，排序值：{}", OPERATOR, id, sortOrder);
        return ResponseEntity.ok(Result.error("更新排序值失败，数据可能不存在"));
      }

    } catch (IllegalArgumentException e) {
      logger.error("更新排序值接口参数错误，操作人：{}，参数：id={}, sortOrder={}，异常信息：", OPERATOR, id, sortOrder, e);
      return ResponseEntity.badRequest().body(Result.error(e.getMessage()));
    } catch (Exception e) {
      logger.error("更新排序值接口异常，操作人：{}，参数：id={}, sortOrder={}，异常信息：", OPERATOR, id, sortOrder, e);
      return ResponseEntity.badRequest().body(Result.error("更新排序值失败"));
    }
  }

  /**
   * 更新推荐状态
   */
  @PutMapping("/featured/{id}")
  @ApiOperation(value = "更新推荐状态", notes = "更新情感意图的推荐状态")
  public ResponseEntity<Object> updateFeaturedStatus(
    @ApiParam(value = "情感意图ID", required = true, example = "1234567890")
    @PathVariable String id,
    @ApiParam(value = "推荐状态：1-推荐，0-不推荐", required = true, example = "1")
    @RequestParam Integer isFeatured) {

    logger.info("更新推荐状态接口调用开始，操作人：{}，参数：id={}, isFeatured={}", OPERATOR, id, isFeatured);

    try {
      if (!StringUtils.hasText(id)) {
        logger.warn("更新推荐状态接口参数验证失败，ID不能为空，操作人：{}", OPERATOR);
        return ResponseEntity.badRequest().body(Result.error("ID不能为空"));
      }

      if (isFeatured == null || (isFeatured != 0 && isFeatured != 1)) {
        logger.warn("更新推荐状态接口参数验证失败，推荐状态值必须为0或1，操作人：{}", OPERATOR);
        return ResponseEntity.badRequest().body(Result.error("推荐状态值必须为0或1"));
      }

      Boolean result = emotionalIntentService.updateFeaturedStatus(id, isFeatured);

      if (result) {
        logger.info("更新推荐状态接口调用成功，操作人：{}，ID：{}，推荐状态：{}", OPERATOR, id, isFeatured);
        return ResponseEntity.ok(Result.success(result));
      } else {
        logger.warn("更新推荐状态接口调用失败，操作人：{}，ID：{}，推荐状态：{}", OPERATOR, id, isFeatured);
        return ResponseEntity.ok(Result.error("更新推荐状态失败，数据可能不存在"));
      }

    } catch (IllegalArgumentException e) {
      logger.error("更新推荐状态接口参数错误，操作人：{}，参数：id={}, isFeatured={}，异常信息：", OPERATOR, id, isFeatured, e);
      return ResponseEntity.badRequest().body(Result.error(e.getMessage()));
    } catch (Exception e) {
      logger.error("更新推荐状态接口异常，操作人：{}，参数：id={}, isFeatured={}，异常信息：", OPERATOR, id, isFeatured, e);
      return ResponseEntity.badRequest().body(Result.error("更新推荐状态失败"));
    }
  }

  /**
   * 更新显示状态
   */
  @PutMapping("/show-status/{id}")
  @ApiOperation(value = "更新显示状态", notes = "更新情感意图在导航中的显示状态")
  public ResponseEntity<Object> updateShowStatus(
    @ApiParam(value = "情感意图ID", required = true, example = "1234567890")
    @PathVariable String id,
    @ApiParam(value = "显示状态：1-显示，0-不显示", required = true, example = "1")
    @RequestParam Integer showInNavigation) {

    logger.info("更新显示状态接口调用开始，操作人：{}，参数：id={}, showInNavigation={}", OPERATOR, id, showInNavigation);

    try {
      if (!StringUtils.hasText(id)) {
        logger.warn("更新显示状态接口参数验证失败，ID不能为空，操作人：{}", OPERATOR);
        return ResponseEntity.badRequest().body(Result.error("ID不能为空"));
      }

      if (showInNavigation == null || (showInNavigation != 0 && showInNavigation != 1)) {
        logger.warn("更新显示状态接口参数验证失败，显示状态值必须为0或1，操作人：{}", OPERATOR);
        return ResponseEntity.badRequest().body(Result.error("显示状态值必须为0或1"));
      }

      Boolean result = emotionalIntentService.updateShowStatus(id, showInNavigation);

      if (result) {
        logger.info("更新显示状态接口调用成功，操作人：{}，ID：{}，显示状态：{}", OPERATOR, id, showInNavigation);
        return ResponseEntity.ok(Result.success(result));
      } else {
        logger.warn("更新显示状态接口调用失败，操作人：{}，ID：{}，显示状态：{}", OPERATOR, id, showInNavigation);
        return ResponseEntity.ok(Result.error("更新显示状态失败，数据可能不存在"));
      }

    } catch (IllegalArgumentException e) {
      logger.error("更新显示状态接口参数错误，操作人：{}，参数：id={}, showInNavigation={}，异常信息：", OPERATOR, id, showInNavigation, e);
      return ResponseEntity.badRequest().body(Result.error(e.getMessage()));
    } catch (Exception e) {
      logger.error("更新显示状态接口异常，操作人：{}，参数：id={}, showInNavigation={}，异常信息：", OPERATOR, id, showInNavigation, e);
      return ResponseEntity.badRequest().body(Result.error("更新显示状态失败"));
    }
  }

  /**
   * 检查意图代码是否已存在
   */
  @GetMapping("/check-intent-code")
  @ApiOperation(value = "检查意图代码是否已存在", notes = "检查意图代码是否已被使用")
  public ResponseEntity<Object> checkIntentCodeExists(
    @ApiParam(value = "意图代码", required = true, example = "LOVE001")
    @RequestParam String intentCode,
    @ApiParam(value = "排除的ID（用于更新时检查）", example = "1234567890")
    @RequestParam(required = false) String excludeId) {

    logger.info("检查意图代码是否已存在接口调用开始，操作人：{}，参数：intentCode={}, excludeId={}", OPERATOR, intentCode, excludeId);

    try {
      if (!StringUtils.hasText(intentCode)) {
        logger.warn("检查意图代码是否已存在接口参数验证失败，意图代码不能为空，操作人：{}", OPERATOR);
        return ResponseEntity.badRequest().body(Result.error("意图代码不能为空"));
      }

      Boolean exists = emotionalIntentService.checkIntentCodeExists(intentCode, excludeId);

      logger.info("检查意图代码是否已存在接口调用成功，操作人：{}，意图代码：{}，结果：{}", OPERATOR, intentCode, exists);
      return ResponseEntity.ok(Result.success(exists));

    } catch (Exception e) {
      logger.error("检查意图代码是否已存在接口异常，操作人：{}，参数：intentCode={}, excludeId={}，异常信息：", OPERATOR, intentCode, excludeId, e);
      return ResponseEntity.badRequest().body(Result.error("检查意图代码是否已存在失败"));
    }
  }

  /**
   * 根据意图代码查询情感意图
   */
  @GetMapping("/by-code/{intentCode}")
  @ApiOperation(value = "根据意图代码查询情感意图", notes = "根据意图代码查询情感意图详情")
  public ResponseEntity<Object> getByIntentCode(
    @ApiParam(value = "意图代码", required = true, example = "LOVE001")
    @PathVariable String intentCode) {

    logger.info("根据意图代码查询情感意图接口调用开始，操作人：{}，参数：intentCode={}", OPERATOR, intentCode);

    try {
      if (!StringUtils.hasText(intentCode)) {
        logger.warn("根据意图代码查询情感意图接口参数验证失败，意图代码不能为空，操作人：{}", OPERATOR);
        return ResponseEntity.badRequest().body(Result.error("意图代码不能为空"));
      }

      EmotionalIntent result = emotionalIntentService.getByIntentCode(intentCode);

      if (result == null) {
        logger.info("根据意图代码查询情感意图接口未找到数据，操作人：{}，意图代码：{}", OPERATOR, intentCode);
        return ResponseEntity.ok(Result.success("未找到对应数据"));
      }

      logger.info("根据意图代码查询情感意图接口调用成功，操作人：{}，结果：{}", OPERATOR, result);
      return ResponseEntity.ok(Result.success(result));

    } catch (IllegalArgumentException e) {
      logger.error("根据意图代码查询情感意图接口参数错误，操作人：{}，参数：intentCode={}，异常信息：", OPERATOR, intentCode, e);
      return ResponseEntity.badRequest().body(Result.error(e.getMessage()));
    } catch (Exception e) {
      logger.error("根据意图代码查询情感意图接口异常，操作人：{}，参数：intentCode={}，异常信息：", OPERATOR, intentCode, e);
      return ResponseEntity.badRequest().body(Result.error("根据意图代码查询情感意图失败"));
    }
  }

  /**
   * 获取启用状态的情感意图数量
   */
  @GetMapping("/active-count")
  @ApiOperation(value = "获取启用状态的情感意图数量", notes = "获取当前启用状态的情感意图总数")
  public ResponseEntity<Object> getActiveCount() {
    logger.info("获取启用状态的情感意图数量接口调用开始，操作人：{}", OPERATOR);

    try {
      Long count = emotionalIntentService.getActiveCount();

      logger.info("获取启用状态的情感意图数量接口调用成功，操作人：{}，数量：{}", OPERATOR, count);
      return ResponseEntity.ok(Result.success(count));

    } catch (Exception e) {
      logger.error("获取启用状态的情感意图数量接口异常，操作人：{}，异常信息：", OPERATOR, e);
      return ResponseEntity.badRequest().body(Result.error("获取启用状态的情感意图数量失败"));
    }
  }

  /**
   * 获取推荐的情感意图列表
   */
  @GetMapping("/featured")
  @ApiOperation(value = "获取推荐的情感意图列表", notes = "获取所有推荐的情感意图列表")
  public ResponseEntity<Object> getFeaturedList() {
    logger.info("获取推荐的情感意图列表接口调用开始，操作人：{}", OPERATOR);

    try {
      List<EmotionalIntent> result = emotionalIntentService.getFeaturedList();

      logger.info("获取推荐的情感意图列表接口调用成功，操作人：{}，记录数：{}", OPERATOR, result.size());
      return ResponseEntity.ok(Result.success(result));

    } catch (Exception e) {
      logger.error("获取推荐的情感意图列表接口异常，操作人：{}，异常信息：", OPERATOR, e);
      return ResponseEntity.badRequest().body(Result.error("获取推荐的情感意图列表失败"));
    }
  }

  /**
   * 获取在导航显示的情感意图列表
   */
  @GetMapping("/navigation")
  @ApiOperation(value = "获取在导航显示的情感意图列表", notes = "获取所有在导航中显示的情感意图列表")
  public ResponseEntity<Object> getNavigationList() {
    logger.info("获取在导航显示的情感意图列表接口调用开始，操作人：{}", OPERATOR);

    try {
      List<EmotionalIntent> result = emotionalIntentService.getNavigationList();

      logger.info("获取在导航显示的情感意图列表接口调用成功，操作人：{}，记录数：{}", OPERATOR, result.size());
      return ResponseEntity.ok(Result.success(result));

    } catch (Exception e) {
      logger.error("获取在导航显示的情感意图列表接口异常，操作人：{}，异常信息：", OPERATOR, e);
      return ResponseEntity.badRequest().body(Result.error("获取在导航显示的情感意图列表失败"));
    }
  }
}
