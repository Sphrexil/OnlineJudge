package com.oj.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.oj.entity.TagEntity;
import com.oj.pojo.dto.TagListDto;
import com.oj.utils.ResponseResult;

/**
 * 标签(com.xgg.domain.entity.Tag)表服务接口
 *
 * @author makejava
 * @since 2022-09-10 00:41:55
 */
public interface TagService extends IService<TagEntity> {


    ResponseResult pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto);
}
