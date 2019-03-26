package com.it.ymk.bubble.core.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.*;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.junrar.Archive;
import com.github.junrar.rarfile.FileHeader;

/**
 *文件处理工具类
 *
 * @author chenzhiqiang 
 * @version $v: 1.0.0, $Id:BopUtilExcel.java, $time:2016-12-13 Exp $
 *
 */
public class FileUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtil.class);

    /**
     * 解析zip文件 
     * @param filePath  文件路径
     * @param filePathTo 存放路径
     * @return
     */
    public static void readZip(String filePath, String filePathTo) {

        LOGGER.info("开始解压：" + filePath);
        File file = new File(filePath);
        if (StringUtils.isEmpty(filePathTo)) {
            filePathTo = file.getParent();
        }
        BufferedInputStream bi = null;
        BufferedOutputStream bos = null;
        ZipFile zf = null;
        try {
            zf = new ZipFile(file, "gb2312");
            Enumeration e = zf.getEntries();
            while (e.hasMoreElements()) {
                ZipEntry ze2 = (ZipEntry) e.nextElement();
                String entryName = ze2.getName();
                String path = filePathTo + "/" + entryName;
                if (ze2.isDirectory()) {
                    File decompressDirFile = new File(path);
                    if (!decompressDirFile.exists()) {
                        decompressDirFile.mkdirs();
                    }
                }
                else {
                    String fileDir = path.substring(0, path.lastIndexOf("/"));
                    File fileDirFile = new File(fileDir);
                    if (!fileDirFile.exists()) {
                        fileDirFile.mkdirs();
                    }
                    bos = new BufferedOutputStream(new FileOutputStream(
                        filePathTo + "/" + entryName));

                    bi = new BufferedInputStream(zf.getInputStream(ze2));
                    byte[] readContent = new byte[1024];
                    int readCount = bi.read(readContent);
                    while (readCount != -1) {
                        bos.write(readContent, 0, readCount);
                        readCount = bi.read(readContent);
                    }
                }
                bos.close();
            }

        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            //            throw new Exception("解析压缩包失败!");
        } finally {
            try {
                zf.close();
                bos.close();
                bi.close();
                zf.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        LOGGER.info("解压完毕：" + filePath);
    }

    /**
     * 解析文件 
     * @param filePath  文件路径
     * @param filePathTo 存放路径
     * @return
     */
    public static void readFile(String filePath, String filePathTo) {
        File file = new File(filePath);
        String name = file.getName();
        if (name.endsWith(".zip")) {
            readZip(filePath, filePathTo);
        }
        else if (name.endsWith(".rar")) {
            unrarFile(filePath, filePathTo);
        }
    }

    /**
     * TODO 待升级
     * rar压缩包处理
     * @param filePath 源文件路径
     * @param destDir 解析文件保存路径
     * @return fileID 组ID
     */
    public static void unrarFile(String filePath, String destDir) {
        File fileTemp = new File(filePath);
        Archive a = null;
        FileOutputStream fos = null;
        if (StringUtils.isEmpty(destDir)) {
            destDir = fileTemp.getParent();
        }
        try {
            a = new Archive(fileTemp);
            a.getMainHeader().print();
            FileHeader fh = a.nextFileHeader();
            while (fh != null) {
                if (!fh.isDirectory()) {
                    //1 根据不同的操作系统拿到相应的 destDirName 和 destFileName
                    /*处理文件名中文乱码问题*/
                    String compressFileName = "";
                    if (fh.isUnicode()) {
                        compressFileName = fh.getFileNameW().trim();
                    }
                    else {
                        compressFileName = fh.getFileNameString().trim();
                    }
                    String destFileName = "";
                    String destDirName = "";
                    //非windows系统     
                    if (File.separator.equals("/")) {
                        destFileName = destDir + "/" + compressFileName.replaceAll("\\\\", "/");
                        int aaa = destFileName.lastIndexOf("/");
                        int bbb = destFileName.lastIndexOf("\\");
                        if (aaa > bbb) {
                            destDirName = destFileName.substring(0, aaa);
                        }
                        else {
                            destDirName = destFileName.substring(0, bbb);
                        }
                        //windows系统      
                    }
                    else {
                        destFileName = destDir + "/" + compressFileName.replaceAll("/", "\\\\");
                        int aaa = destFileName.lastIndexOf("/");
                        int bbb = destFileName.lastIndexOf("\\");
                        if (aaa > bbb) {
                            destDirName = destFileName.substring(0, aaa);
                        }
                        else {
                            destDirName = destFileName.substring(0, bbb);
                        }
                    }
                    //2创建文件夹     
                    File dir = new File(destDirName);
                    if (!dir.exists() || !dir.isDirectory()) {
                        dir.mkdirs();
                    }
                    //3解压缩文件
                    File file = new File(destFileName);
                    fos = new FileOutputStream(file);
                    try {
                        a.extractFile(fh, fos);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    fos.close();
                }
                fh = a.nextFileHeader();
            }
            a.close();
            a = null;
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        } catch (Exception e) {
            LOGGER.error("解析压缩包失败！");
            //            throw new Exception("解析压缩包失败!");
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                    fos = null;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (a != null) {
                try {
                    a.close();
                    a = null;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static boolean deleteFile(String sPath) {
        boolean flag = false;
        File file = new File(sPath);
        // 路径为文件且不为空则进行删除  
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }

    /**
     * 数据加一列
     * @param arr
     * @param str
     * @return
     */
    public static String[] insert(String[] arr, String str) {
        int size = arr.length;

        String[] tmp = new String[size + 1];

        System.arraycopy(arr, 0, tmp, 0, size);

        tmp[size] = str;

        return tmp;
    }

    /**
     * map转Bean
     * @param type
     * @param map
     * @return
     * @throws Exception
     */
    public static Object convertMap(Class type, Map map)
        throws Exception {
        BeanInfo beanInfo = Introspector.getBeanInfo(type); // 获取类属性   
        Object obj = type.newInstance(); // 创建 JavaBean 对象   
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        // 给 JavaBean 对象的属性赋值   
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (int i = 0; i < propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();

            if (map.containsKey(propertyName)) {
                Method method;
                try {
                    Object value = map.get(propertyName);

                    Object[] args = new Object[1];

                    Method setter = descriptor.getWriteMethod();
                    if (setter.getParameterTypes()[0] == java.util.Date.class) {
                        if (!StringUtils.isEmpty(value.toString())) {
                            args[0] = df.parse(value.toString());
                        }
                    }
                    else {
                        args[0] = value;
                    }
                    setter.invoke(obj, args);
                    //descriptor.getWriteMethod().invoke(obj, args);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return obj;
    }

    /**
     * 
     * @param str
     * @return
     */
    public static String UpStr(String str) {
        return str.replaceFirst(str.substring(0, 1), str.substring(0, 1).toUpperCase());
    }

    /**
     * 保存文件
     * 
     * @param filePath 文件保存路径
     * @param content 内容
     * @throws IOException 异常
     */
    public static void saveFile(String filePath, byte[] content) throws IOException {
        BufferedOutputStream bos = null;
        try {
            File file = new File(filePath);
            //判断文件路径是否存在
            if (!file.getParentFile().exists()) {
                //文件路径不存在时，创建保存文件所需要的路径
                file.getParentFile().mkdirs();
            }
            //创建文件（这是个空文件，用来写入上传过来的文件的内容）
            file.createNewFile();
            bos = new BufferedOutputStream(new FileOutputStream(file));
            bos.write(content);
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("文件不存在。");
        } finally {
            if (null != bos) {
                bos.close();
            }
        }
    }

    /**
     * 获取文件扩展名
     *
     * @param fileName 文件
     * @return String 扩展名
     */
    public static String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf('.'));
    }

    /**
     * 删除目录及文件
     *
     * @param sPath 文件路径
     * @return boolean 成功与否
     */
    public static boolean deleteDirectory(String sPath) {
        //如果sPath不以文件分隔符结尾，自动添加文件分隔符
        if (!sPath.endsWith(File.separator)) {
            sPath = sPath + File.separator;
        }
        File dirFile = new File(sPath);
        //如果dir对应的文件不存在，或者不是一个目录，则退出
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        boolean flag = true;
        //删除文件夹下的所有文件(包括子目录)
        File[] files = dirFile.listFiles();
        if (null != files) {
            for (int i = 0; i < files.length; i++) {
                //删除子文件
                if (files[i].isFile()) {
                    flag = deleteFile(files[i].getAbsolutePath());
                    if (!flag) {
                        break;
                    }

                } //删除子目录
                else {
                    flag = deleteDirectory(files[i].getAbsolutePath());
                    if (!flag) {
                        break;
                    }

                }
            }
        }

        if (!flag) {
            return false;
        }
        //删除当前目录
        if (dirFile.delete()) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * 删除文件夹
     *
     * @param sPath 文件路径
     * @return  boolean 成功与否
     */
    public static boolean deleteFolder(String sPath) {
        boolean flag = false;
        File file = new File(sPath);
        // 判断目录或文件是否存在
        if (!file.exists()) { // 不存在返回 false
            return flag;
        }
        else {
            // 判断是否为文件
            if (file.isFile()) { // 为文件时调用删除文件方法
                return deleteFile(sPath);
            }
            else { // 为目录时调用删除目录方法
                return deleteDirectory(sPath);
            }
        }
    }

    /**
     * 获取文件大小
     *
     * @param file 文件对象
     * @return long 文件大小
     */
    public long getFileSize(File file) {
        if (file.exists() && file.isFile()) {
            return file.length();
        }
        else {
            return 0;
        }
    };
}
