package com.it.ymk.bubble.core.freemark;

import java.io.*;
import java.util.Map;

import com.it.ymk.bubble.core.utils.JdbcUtil;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 *  模版引擎工具类
 *
 * @author Yanmingkun
 * @version $v:1.0, $time:2017-07-21, $id:FreemarkUtil.java, Exp $
 */
public class FreemarkUtil {
    private static String        driver   = "com.mysql.jdbc.Driver";
    private static String        url      = "jdbc:mysql://localhost:3306/skyrim";
    private static String        username = "skyrim";
    private static String        password = "skyrim";
    //你模板放置的路径
    private static String        basePath = "/opt/freemark";
    // 创建Configuration实例
    private static Configuration cfg      = new Configuration();

    public static void genateCode() {
        Template temp = null;
        File loadTemplateFile = new File(basePath);
        if (!loadTemplateFile.exists()) {
            loadTemplateFile.mkdirs();
        }
        try {
            cfg.setDirectoryForTemplateLoading(loadTemplateFile);
            String saveDir = basePath + "/skyrim";

            Map<String, Map<String, Params>> classPropties = JdbcUtil
                .buildFreemark(driver, url, username, password);
            SetJavaCode(temp, saveDir, classPropties);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 首字母大写
    public static String toUpString(String className) {
        char[] cs = className.toCharArray();
        cs[0] -= 32;
        String ClassName = String.valueOf(cs);
        return ClassName;
    }

    public static void SetJavaCode(Template temp, String saveDir,
        Map<String, Map<String, Params>> classPropties) {

        Map<String, Params> m = classPropties.get("Paramss");
        //每一个实例里面都存有tableName，这里取第0个拿tableName
        String tableName = (String) (m.get("Params0").getTableName());
        //表明为m_user,指定类名为User
        String className = tableName.replaceAll("m_", "");
        // 使首字母大写
        String ClassName = toUpString(className);
        // 存入，作为参数给页面 className：作为引用名 ClassName：作为类名
        String fileNameEntityPath = saveDir
                                    + "/entity/" + ClassName + ".java";
        String fileNameDaoPath = saveDir
                                 + "/dao/" + ClassName + "Dao.java";
        String fileNameServicePath = saveDir
                                     + "/service/" + ClassName + "Service.java";
        String fileNameServiceImplPath = saveDir
                                         + "/service/impl/" + ClassName + "ServiceImpl.java";
        File newsDir = new File(saveDir);
        File newsDir2 = new File(saveDir + "/dao/");
        File newsDir3 = new File(saveDir + "/service/");
        File newsDir4 = new File(saveDir + "/service/impl/");
        File newsDir5 = new File(saveDir + "/entity/");
        boolean flag = false;
        flag = (Boolean) (newsDir.exists() == false ? newsDir.mkdirs() : true);
        flag = (Boolean) (newsDir2.exists() == false ? newsDir2.mkdirs() : true);
        flag = (Boolean) (newsDir3.exists() == false ? newsDir3.mkdirs() : true);
        flag = (Boolean) (newsDir4.exists() == false ? newsDir4.mkdirs() : true);
        flag = (Boolean) (newsDir5.exists() == false ? newsDir5.mkdirs() : true);

        try (Writer out = new OutputStreamWriter(new FileOutputStream(
            fileNameDaoPath), "utf-8");
            Writer out2 = new OutputStreamWriter(new FileOutputStream(
                fileNameServicePath), "utf-8");
            Writer out3 = new OutputStreamWriter(new FileOutputStream(
                fileNameServiceImplPath), "utf-8");
            Writer out4 = new OutputStreamWriter(new FileOutputStream(
                fileNameEntityPath), "utf-8");) {

            temp = cfg.getTemplate("Dao.ftl");
            temp.process(classPropties, out);
            System.out.println("------------DAO生成完毕-------------");

            temp = cfg.getTemplate("Service.ftl");
            temp.process(classPropties, out2);
            System.out.println("------------Service生成完毕-------------");

            temp = cfg.getTemplate("ServiceImpl.ftl");
            temp.process(classPropties, out3);
            System.out.println("------------ServiceImpl生成完毕-------------");

            temp = cfg.getTemplate("Object.ftl");
            temp.process(classPropties, out4);
            System.out.println("------------Entity生成完毕-------------");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        genateCode();
    }
}