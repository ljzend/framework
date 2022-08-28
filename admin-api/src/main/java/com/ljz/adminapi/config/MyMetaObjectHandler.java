package com.ljz.adminapi.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * <p>属性自动填充</p>
 * <ul>
 *     <li>create_time 创建时间</li>
 *     <li>update_time 更新时间</li>
 * </ul>
 *
 * @Author : ljz
 * @Date: 2022/8/27  14:35
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    private static final Logger log = LoggerFactory.getLogger(MyMetaObjectHandler.class);

    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("填充 create_time 字段");
        this.strictInsertFill(metaObject, "createTime", Date::new, Date.class); // 起始版本 3.3.3(推荐)
        this.strictUpdateFill(metaObject, "updateTime", Date::new, Date.class); // 起始版本 3.3.3(推荐)
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("填充 update_time 字段");
        this.strictUpdateFill(metaObject, "updateTime", Date::new, Date.class); // 起始版本 3.3.3(推荐)
    }
}
