package com.it.ymk.bubble.core.utils;

import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author yanmingkun
 * @date 2018-11-30 11:31
 */
public class VelocityUtil {
    private static final Logger                LOGGER            = LoggerFactory.getLogger(VelocityUtil.class);
    private static final String                LOADER_TYPE_URL   = "url_";
    private static final String                LOADER_TYPE_PATH  = "path_";
    private static final String                LOADER_TYPE_CLASS = "class_";
    private static Map<String, VelocityEngine> velocityEngines   = new HashMap<String, VelocityEngine>();

    private static VelocityEngine getVelocityEngine(String loaderType, String templatePath) {
        VelocityEngine engine = velocityEngines.get(loaderType + templatePath);
        if (engine == null) {
            engine = new VelocityEngine();
            Properties properties = new Properties();
            if (LOADER_TYPE_CLASS.equals(loaderType)) {
                properties.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
                properties.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
            }
            else if (LOADER_TYPE_URL.equals(loaderType)) {
                engine = new VelocityEngine(templatePath);
                properties.setProperty("file.resource.loader.path", templatePath);
            }
            else {
                properties.setProperty("input.encoding", "utf-8");
                properties.setProperty("output.encoding", "utf-8");
                properties.setProperty("file.resource.loader.path", templatePath);
            }
            engine.init(properties);
            velocityEngines.put(loaderType + templatePath, engine);
        }
        return engine;
    }

    /**
     * 根据模板生成内容
     *
     * @param basePath velocity模板基本路径
     * @param templateName velocity文件名
     * @param data 变量Map
     * @return 转换后的字符串
     * @throws Exception
     */
    public static String transform(String basePath, String templateName,
        Map<String, Object> data) throws Exception {
        try {
            VelocityEngine velocityEngine = VelocityUtil.getVelocityEngine(LOADER_TYPE_PATH, basePath);
            Template template = velocityEngine.getTemplate(templateName);
            StringWriter writer = new StringWriter();
            VelocityContext context = new VelocityContext(data);
            template.merge(context, writer);
            return writer.toString();
        } catch (Exception e) {
            throw new Exception(e.getMessage(), e);
        }
    }

    /**
     * 模版替换
     *
     * @param templatePath  模板地址
     * @param contentMap    变量
     * @return String 转换后的字符串
     * @throws Exception
     */
    public static String transformUrlPath(String templatePath, Map<String, Object> contentMap) throws Exception {
        URL templateFileUrl = LOGGER.getClass().getClassLoader().getResource(templatePath);

        StringWriter stringWriter = new StringWriter();
        String filePath = StringUtils.substringBeforeLast(templateFileUrl.getPath(), "/");
        String fileName = StringUtils.substringAfterLast(templatePath, "/");
        try {
            VelocityContext velocityContext = new VelocityContext(contentMap);
            VelocityEngine velocityEngine = VelocityUtil.getVelocityEngine(LOADER_TYPE_URL, filePath);
            Template template = velocityEngine.getTemplate(fileName);
            template.merge(velocityContext, stringWriter);
        } catch (Exception e) {
            LOGGER.error("数据转换失败，模板=" + templatePath, e);
            throw new Exception(e.getMessage(), e);
        }

        return stringWriter.toString();
    }

    public static String transformClassPath(String templatePath, Map<String, Object> contentMap) throws Exception {
        StringWriter writer = new StringWriter();
        try {
            VelocityEngine ve = VelocityUtil.getVelocityEngine(LOADER_TYPE_CLASS, templatePath);
            VelocityContext context = new VelocityContext(contentMap);
            Template t = ve.getTemplate(templatePath);

            t.merge(context, writer);
        } catch (Exception e) {
            LOGGER.error("数据转换失败，模板=" + contentMap, e);
            throw new Exception(e.getMessage(), e);
        }
        return writer.toString();
    }

    /**
     * 根据模板生成文件
     *
     * @param basePath  velocity模板基本路径
     * @param templateName velocity文件名
     * @param data 变量Map
     * @param outFilePath 生成后的文件名，绝对路径
     * @throws Exception
     */
    public static void transform(String basePath, String templateName,
        Map<String, Object> data, String outFilePath) throws Exception {
        String content = transform(basePath, templateName, data);
        BufferedWriter bw = null;
        try {
            File jsfile = new File(outFilePath);
            jsfile.createNewFile();
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(jsfile), "UTF-8"));
            bw.write(content);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        } finally {
            if (null != bw) {
                bw.close();
            }
        }
    }

    public static String transform(String templateName, Map<String, Object> data)
        throws Exception {

        String userDir = System.getProperty("user.dir");
        String path = userDir + File.separator + "config" + File.separator + "vm";
        return transform(path, templateName, data);

    }

    public static String generateHtml(String vmTemplate, Object obj) {
        VelocityEngine velocityEngine = getVelocityEngine("class_", vmTemplate);

        StringWriter sw = new StringWriter();
        try {
            VelocityContext ctx = new VelocityContext();
            ctx.put("data", obj);
            velocityEngine.getTemplate(vmTemplate, "UTF-8").merge(ctx, sw);
        } catch (Exception e) {
            LOGGER.error("转化vm模板出错", e);
        } finally {
            if (sw != null) {
                try {
                    sw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sw.toString();

    }
}
