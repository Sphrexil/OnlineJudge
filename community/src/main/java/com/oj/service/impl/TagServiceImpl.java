package com.oj.service.impl;



import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oj.Dao.TagDao;
import com.oj.entity.TagEntity;

import com.oj.pojo.dto.TagListDto;
import com.oj.service.TagService;
import com.oj.utils.PageUtils;
import com.oj.utils.PathUtils;
import com.oj.utils.ResponseResult;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


/**
 * 标签(com.xgg.domain.entity.Tag)表服务实现类
 *
 * @author makejava
 * @since 2022-09-10 00:41:59
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagDao, TagEntity> implements TagService {

    @Override
    public ResponseResult pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto) {

        LambdaQueryWrapper<TagEntity> wrapper = new LambdaQueryWrapper<TagEntity>()
                .eq(StringUtils.hasText(tagListDto.getName()),
                        TagEntity::getName, tagListDto.getName())
                .eq(StringUtils.hasText(tagListDto.getRemark()),
                        TagEntity::getRemark, tagListDto.getRemark());


        IPage<TagEntity> iPage = PageUtils.getPage(pageNum, pageSize, TagEntity.class);
        page(iPage, wrapper);

        return ResponseResult.okResult(new PageUtils<>(iPage));
    }
}
