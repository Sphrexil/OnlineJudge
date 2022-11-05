package com.oj.controller;



import com.oj.entity.TagEntity;
import com.oj.pojo.dto.TagListDto;
import com.oj.service.ArticleService;
import com.oj.service.CategoryService;
import com.oj.service.TagService;
import com.oj.utils.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 标签(Tag)表控制层
 *
 * @author makejava
 * @since 2022-09-10 01:07:28
 */
@Api(tags = "标签")
@RestController
@RequestMapping("/content")
public class TagController {
    /**
     * 服务对象
     */
    @Resource
    private TagService tagService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ArticleService articleService;

    /**
     * 查询所有数据
     *
     * @return 实例对象集合
     */
    @ApiOperation("查询所有数据")
    @GetMapping("/tag/list")
    public ResponseResult getAllTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto) {
        return tagService.pageTagList(pageNum, pageSize, tagListDto);
    }

      /**
     * 查询所有数据带分页
     *
     * @return 实例对象集合
     */
    @GetMapping(value = "/queryListPage")
    @PreAuthorize("@PermissionService.hasPermissions('content:link:list')")
    @ApiOperation("查询所有数据带分页") @ApiImplicitParams({
            @ApiImplicitParam(name = "pageSize", required = true, value = "每页条数",dataType = "int",paramType = "query"),
            @ApiImplicitParam(name = "pageNo", required = true, value = "当前页",dataType = "int",paramType = "query")})
    public ResponseResult queryListPage() {
        try{

            List<TagEntity> list = tagService.list();
            return ResponseResult.okResult(list);
        } catch (Exception e) {
            return ResponseResult.okResult();
        }
    }




    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @ApiOperation("通过主键查询单条数据")
    @GetMapping("/tag/{id}")
    public ResponseResult queryById(@PathVariable("id") Long id) {
        return ResponseResult.okResult(this.tagService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param tag 实体
     * @return 新增结果
     */
    @ApiOperation("新增数据")
    @PostMapping("/tag/")
    public ResponseResult add(@RequestBody TagEntity tag) {
        return ResponseResult.okResult(this.tagService.save(tag));
    }

    /**
     * 编辑数据
     *
     * @param tag 实体
     * @return 编辑结果
     */
    @ApiOperation("编辑数据")
    @PutMapping("/tag/")
    public ResponseResult edit(@RequestBody TagEntity tag) {
        return ResponseResult.okResult(this.tagService.updateById(tag));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @ApiOperation("删除数据")
    @DeleteMapping("/tag/{id}")
    public ResponseResult deleteById(@PathVariable("id")Long id) {
        return ResponseResult.okResult(this.tagService.removeById(id));
    }


    @GetMapping("/category/listAllCategory")
    public ResponseResult getAllCategoryList() {

        return categoryService.getCategoryList();
    }
}
