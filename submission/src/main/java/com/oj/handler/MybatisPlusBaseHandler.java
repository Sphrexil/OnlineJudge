package com.oj.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class MybatisPlusBaseHandler implements MetaObjectHandler {
    //重写一下insertFill方法 在插入时进行时间的自动维护
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("新增更新时间-------------------------");
        this.strictInsertFill(metaObject,"createTime",Date.class,new Date());
        this.strictInsertFill(metaObject,"updateTime",Date.class,new Date());
    }
    // 修改操作是进行时间的维护
    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("修改更新时间-------------------------");
        this.strictUpdateFill(metaObject,"updateTime",Date.class,new Date());
    }
}
