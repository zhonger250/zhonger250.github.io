package com.example.common.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author 丁祥瑞
 */
@Component
public class MyBatisPlusMetaObjectHandler implements MetaObjectHandler {
    /**
     * 使用MyBatis向表中出入数据时, 有些字段需要赋予默认值,
     * MyBatisPlus自动调用insertFill方法, 实现自动填充
     * @param metaObject 元对象
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        setFieldValByName("version",1,metaObject);
        setFieldValByName("creationDate",new Date(),metaObject);
//        setFieldValByName("modifyDate",new Date(),metaObject);
        setFieldValByName("deleted",0,metaObject);
    }

    /**
     * 使用MyBatis更新表中的数据时, 有些字段需要进行更新
     * @param metaObject 元对象
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        setFieldValByName("modifyDate",new Date(),metaObject);
    }
}
