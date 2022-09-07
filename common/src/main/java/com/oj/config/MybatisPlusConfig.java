package com.oj.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.oj.constants.GlobalConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

/**
 * @Title: MyBatis的全局配置
 * @Description:
 * @Author: zhenyu
 * @DateTime: 2022/8/28 17:25
 */

@Configuration
@Slf4j
public class MybatisPlusConfig implements MetaObjectHandler{

    /**
     * 3.4.0之后版本
     *
     * @return
     */

    /* MyBatisPlus自带的分页插件 */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return mybatisPlusInterceptor;
    }
    //重写一下insertFill方法 在插入时进行时间的自动维护
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("新增更新时间-------------------------");
        this.strictInsertFill(metaObject,"createTime", Date.class, new Date());
        this.strictInsertFill(metaObject,"updateTime", Date.class, new Date());
        this.strictInsertFill(metaObject,"isDel", Integer.class, GlobalConstant.NOT_LOGIC_DELETE);
    }
    // 修改操作是进行时间的维护
    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("修改更新时间-------------------------");
        this.strictUpdateFill(metaObject,"updateTime", Date.class,new Date());
    }

}
