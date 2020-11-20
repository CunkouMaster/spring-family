import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.AbstractTemplateEngine;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * mysql 代码生成器演示例子
 * </p>
 *
 * @author jobob
 * @since 2018-09-12
 */
public class MysqlGenerator {
    //数据库类型
    private static final DbType DB_TYPE = DbType.MYSQL;
    //数据库连结信息
    private static final String DB_URL = "jdbc:mysql://localhost:3306/spring_demo?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8";
    //com.mysql.jdbc.Driver 或  com.mysql.cj.jdbc.Driver
    private static final String driverName = "com.mysql.jdbc.Driver";
    private static final String username = "root";
    private static final String password = "root";
    //作者名
    private static final String AUTHOR = "huazai";

    private static AbstractTemplateEngine ENGINE = new FreemarkerTemplateEngine();
    //显示声明templatePath用于自定义mapper.xml的输出目录设置
    private static String XML_TEMPLATE_PATH = "/generator/template/mapper.xml.ftl";

    private static final String BASE_OUT_PATH = "E:\\gitHub\\spring-family\\";
    //项目名
    private static final String PROJECT_NAME = "spring-cloud-client";
    //指定包名
    private static final String PACKAGE_NAME = "spring.demo.mybatis";
    //模块名
    private static final String MODULE_NAME = "cloud-demo-mybatis-plus";

    //指定生成的表名
    private static final String[] tableNames = new String[]{
//            "t_user"
    };
    private static final String TABLE_PREFIX = "t_";


    /**
     * RUN THIS
     */
    public static void main(String[] args) {
        autoGenerator(
                getGlobalConfig(false),
                getDataSourceConfig(),
                getStrategyConfig(tableNames),
                getPackageConfig(PACKAGE_NAME,MODULE_NAME),
                getInjectionConfig(),
                getTemplateConfig()
        );

    }

    /**
     * 集成
     * @param globalConfig    全局变量配置
     * @param dataSourceConfig 配置数据源
     * @param strategyConfig  策略配置
     * @param packageConfig  包名配置
     * @param injectionConfig  自定义配置
     * @param templateConfig  模板配置
     * @author huazai
     */
    private static void autoGenerator(GlobalConfig globalConfig,
                                      DataSourceConfig dataSourceConfig,
                                      StrategyConfig strategyConfig,
                                      PackageConfig packageConfig,
                                      InjectionConfig injectionConfig,
                                      TemplateConfig templateConfig) {
        AutoGenerator generator = new AutoGenerator()
                .setGlobalConfig(globalConfig)
                .setDataSource(dataSourceConfig)
                .setStrategy(strategyConfig)
                .setPackageInfo(packageConfig)
                .setCfg(injectionConfig)
                .setTemplate(templateConfig)
                // 选择 freemarker 引擎需要指定如下加，注意 pom 依赖必须有！
                .setTemplateEngine(ENGINE);
        System.out.println("===================== MyBatis Plus Generator ==================");
        //执行
        generator.execute();
        System.out.println("================= MyBatis Plus Generator Execute Complete ==================");
    }


    /**
     * 全局配置
     * @param serviceNameStartWithI false
     * @return GlobalConfig
     * @author huazai
     */
    private static GlobalConfig getGlobalConfig(boolean serviceNameStartWithI) {

        String projectPath = System.getProperty("user.dir");

        GlobalConfig globalConfig = new GlobalConfig()
                // 是否覆盖同名文件，默认是false
                .setFileOverride(true)
                // 开启 activeRecord 模式
                .setActiveRecord(true)
                // XML 二级缓存
                .setEnableCache(false)
                // XML ResultMap
                .setBaseResultMap(true)
                // XML columList
                .setBaseColumnList(true)
                //作者
                .setAuthor(AUTHOR)
                // 实体属性 Swagger2 注解
//                .setSwagger2(true)
                .setIdType(IdType.AUTO)
                .setDateType(DateType.TIME_PACK)
                //生成后打开文件夹
                .setOpen(false)
                //设置输出路径
//                .setOutputDir(projectPath + "/src/main/java")
                .setOutputDir(getOutputDir(PROJECT_NAME,MODULE_NAME))
                .setFileOverride(true);
        if (!serviceNameStartWithI) {
            // 自定义文件命名，注意 %s 会自动填充表实体属性！
            globalConfig.setEntityName("%sEntity")
//                .setControllerName("%sController")
//                .setServiceName("%sService")
//                .setServiceImplName("%sServiceImpl")
                .setMapperName("%sMapper")
                .setXmlName("%sMapper");
        }
        return globalConfig;
    }

    /**
     * 返回项目路径
     * @param projectName 项目名
     * @param moduleName 模块名称
     * @return 项目路径
     * @author huazai
     */
    private static String getOutputDir(String projectName,String moduleName) {
        return BASE_OUT_PATH + projectName + "\\" + moduleName + "\\src\\main\\java";
    }


    /**
     * 配置数据源
     * @return 数据源配置 DataSourceConfig
     * @author huazai
     */
    private static DataSourceConfig getDataSourceConfig() {

        return new DataSourceConfig()
                .setDbType(DB_TYPE)
                .setUrl(DB_URL)
                .setDriverName(driverName)
                .setUsername(username)
                .setPassword(password)
                .setTypeConvert(new MySqlTypeConvert() {
                    // 自定义数据库表字段类型转换【可选】
                    @Override
                    public DbColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {
                        System.out.println("转换类型：" + fieldType);
                        //tinyint转换成INTEGER
                        if ( fieldType.toLowerCase().contains( "tinyint" ) ) {
                            return DbColumnType.INTEGER;
                        }
                        //                //将数据库中datetime转换成date
                        //                if ( fieldType.toLowerCase().contains( "datetime" ) ) {
                        //                    return DbColumnType.LOCAL_DATE_TIME;
                        //                }
                        return (DbColumnType) super.processTypeConvert(globalConfig, fieldType);
                    }
                });
    }

    /**
     * 设置包名
     * @param packageName 父路径包名
     * @param moduleName 模块名
     * @return PackageConfig 包名配置
     * @author huazai
     */
    private static PackageConfig getPackageConfig(String packageName,String moduleName) {
        return new PackageConfig()
//                .setModuleName(moduleName)
                .setParent(packageName)
                .setXml("mapper.xml")
                .setMapper("mapper")
//                .setService("service")
//                .setServiceImpl("service.impl")
//                .setController("controller")
                .setEntity("entity");
    }
    /**
     * 策略配置
     * @param tableNames 表名
     * @return StrategyConfig
     * @author huazai
     */
    private static StrategyConfig getStrategyConfig(String... tableNames) {

        return new StrategyConfig()
                // 全局大写命名 ORACLE 注意
                .setCapitalMode(true)
                //从数据库表到文件的命名策略
                .setNaming(NamingStrategy.underline_to_camel)
                .setColumnNaming(NamingStrategy.underline_to_camel)
                //需要生成的的表名，多个表名传数组
                .setInclude(tableNames)
                //根据你的表名来建对应的类名，如果你的表名没有什么下划线，比如test，那么你就可以取消这一步
                .setTablePrefix(TABLE_PREFIX)
                //公共父类
//                .setSuperControllerClass(superControllerClass)
//                .setSuperEntityClass(superEntityClass)
                // 写于父类中的公共字段
//                .setSuperEntityColumns("id")
                //自动生成实体字段注解
                .setEntityTableFieldAnnotationEnable(true)
                //自定义实体，公共字段
                .setTableFillList(getTableFillList())
                //设置为true时不会生成entity的get、set方法
                .setEntityLombokModel(false)
                //rest风格
                .setRestControllerStyle(true);
    }

    /**
     * 设置实体类填充属性
     * @return 自定义实体，公共字段
     * @author huazai
     */
    private static List<TableFill> getTableFillList() {
        //实体字段填充注解
        List<TableFill> tableFillList = new ArrayList<>();
        tableFillList.add(new TableFill("create_user", FieldFill.INSERT));
        tableFillList.add(new TableFill("create_time", FieldFill.INSERT));
        tableFillList.add(new TableFill("update_user", FieldFill.UPDATE));
        tableFillList.add(new TableFill("update_time", FieldFill.UPDATE));
        return tableFillList;
    }

    private static InjectionConfig getInjectionConfig(){

        // 自定义配置 【可无】
        InjectionConfig injectionConfig = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        // 如果模板引擎是 freemarker
//        String templatePath = "/templates/mapper.xml.ftl";
        // 如果模板引擎是 velocity
//        String templatePath = "/templates/mapper.xml.vm";

        // 自定义输出文件目录
        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig(XML_TEMPLATE_PATH) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return getCustomOutputDir(PROJECT_NAME,MODULE_NAME) + "/src/main/resources/mapper/"
                        + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        /*
        cfg.setFileCreate(new IFileCreate() {
            @Override
            public boolean isCreate(ConfigBuilder configBuilder, FileType fileType, String filePath) {
                // 判断自定义文件夹是否需要创建
                checkDir("调用默认方法创建的目录");
                return false;
            }
        });
        */
        injectionConfig.setFileOutConfigList(focList);

        return injectionConfig;
    }

    /**
     * 返回xml存储目录
     * @param projectName 项目名
     * @param moduleName 模块名称
     * @return 项目路径
     * @author huazai
     */
    private static String getCustomOutputDir(String projectName,String moduleName) {
        return BASE_OUT_PATH + projectName + "\\" + moduleName;
    }

    /**
     *
     * @return
     */
    private static TemplateConfig getTemplateConfig(){
        // 配置模板 关闭默认生成，如果设置空 OR Null 将不生成该模块。
        return new TemplateConfig()
//                .setMapper(null)
//                .setService(null)
//                .setServiceImpl(null)
                .setController(null)
                .setXml(null); //不再默认路径下生成XML文件

        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        // templateConfig.setEntity("templates/entity2.java");
        // templateConfig.setService();
        // templateConfig.setController();
    }
}
