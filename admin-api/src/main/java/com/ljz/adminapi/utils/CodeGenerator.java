package com.ljz.adminapi.utils;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;

import java.util.Collections;

/**
 * The type Code generator.
 *
 * @ClassName : CodeGenerator
 * @Description : mybatis-plus代码生成器
 * @Author : ljz
 * @Date: 2022 /7/3  15:00
 */
public class CodeGenerator {
    /**
     * The Mysql url.
     */
    private static final String MYSQL_URL = "jdbc:mysql://127.0.0.1:3306/framework?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai";
    /**
     * The Mysql username.
     */
    private static final String MYSQL_USERNAME = "root";
    /**
     * The Mysql password.
     */
    private static final String MYSQL_PASSWORD = "ljzyou2513";
    /**
     * The Author.
     */
    private static final String AUTHOR = "ljz";
    /**
     * The Output dir.
     */
    private static final String OUTPUT_DIR = System.getProperty("user.dir") + "/admin-api/src/main/java";
    /**
     * The Date format.
     */
    private static final String DATE_FORMAT = "yyyy-MM-dd HH-mm-ss";
    /**
     * The Table prefix.
     */
    private static final String[] TABLE_PREFIX = {"sys_",};
    /**
     * The Table name.
     */
    private static final String[] TABLE_NAME = {"sys_department",
            "sys_permission",
            "sys_role",
            "sys_role_permission",
            "sys_user",
            "sys_user_role"};

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        // 1. 数据库配置
        FastAutoGenerator.create(MYSQL_URL, MYSQL_USERNAME, MYSQL_PASSWORD)
                // 2. 全局配置
                .globalConfig(builder -> {
                    builder.author(AUTHOR) // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir(OUTPUT_DIR) // 指定输出目录
                            .dateType(DateType.ONLY_DATE) // 时间策略 使用 java8 新的时间类型
                            .commentDate(DATE_FORMAT); // 注释日期
                })
                // 3. 包配置
                .packageConfig(builder -> {
                    builder.parent("com.ljz.adminapi") // 父包名
                            //.moduleName("formwork")   //设置模块包名
                            .entity("pojo") // Entity 包名
                            .service("service") // Service 包名
                            .serviceImpl("service.impl") // Service Impl 包名
                            .mapper("mapper") // Mapper 包名
                            .xml("mapper") // Mapper XML 包名
                            .controller("controller") // Controller 包名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, System.getProperty("user.dir") + "/admin-api/src/main/resources/mapper"));   //配置 **Mapper.xml 路径信息：项目的 resources 目录的 Mapper 目录下
                })
                // TODO 4. 模板配置
                // TODO 5. 注入配置

                //6、策略配置
                .strategyConfig(builder -> {
                    builder.addInclude(TABLE_NAME) // 设置需要生成的数据表名
                            .addTablePrefix(TABLE_PREFIX) // 设置过滤表前缀

                            //4.1、Mapper策略配置
                            .mapperBuilder()
                            .superClass(BaseMapper.class)   //设置父类
                            .formatMapperFileName("%sMapper")   //格式化 mapper 文件名称
                            .enableMapperAnnotation()       //开启 @Mapper 注解
                            .enableBaseResultMap()
                            .enableBaseColumnList()
                            .formatXmlFileName("%sMapper") //格式化 Xml 文件名称

                            //4.2、service 策略配置
                            .serviceBuilder()
                            .formatServiceFileName("%sService") //格式化 service 接口文件名称，%s进行匹配表名，如 UserService
                            .formatServiceImplFileName("%sServiceImpl") //格式化 service 实现类文件名称，%s进行匹配表名，如 UserServiceImpl

                            //4.3、实体类策略配置
                            .entityBuilder()
                            .enableLombok() //开启 Lombok
                            //.disableSerialVersionUID()  //不实现 Serializable 接口，不生产 SerialVersionUID
                            .logicDeleteColumnName("is_delete")   //逻辑删除字段名
                            .naming(NamingStrategy.underline_to_camel)  //数据库表映射到实体的命名策略：下划线转驼峰命
                            .columnNaming(NamingStrategy.underline_to_camel)    //数据库表字段映射到实体的命名策略：下划线转驼峰命
                            .addTableFills(
                                    new Column("create_time", FieldFill.INSERT),
                                    new Column("update_time", FieldFill.INSERT_UPDATE)
                            )   //添加表字段填充，"create_time"字段自动填充为插入时间，"modify_time"字段自动填充为插入修改时间
                            .enableTableFieldAnnotation()       // 开启生成实体时生成字段注解

                            //4.4、Controller策略配置
                            .controllerBuilder()
                            .formatFileName("%sController") //格式化 Controller 类文件名称，%s进行匹配表名，如 UserController
                            .enableRestStyle();  //开启生成 @RestController 控制器

                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
