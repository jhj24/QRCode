package com.jhj.zxing;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.widget.Toast;

import java.io.File;
import java.util.Locale;

/**
 * 文件管理工具类
 * Created by jhj on 17-7-27.
 */

public class FileUtil {

    /**
     * 新建文件夹
     *
     * @param subDir 　文件夹名称
     * @return　文件夹路径
     */
    public static String getSDPath(String subDir) {

        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            String path = Environment.getExternalStorageDirectory().getAbsolutePath();
            if (!path.endsWith("/"))
                path += "/";

            if (subDir != null && subDir.trim().length() > 0)
                path += (subDir + "/");

            File f = new File(path);

            if (!f.exists()) {
                if (f.mkdirs())
                    return path;
                else
                    return null;
            } else {
                if (f.isFile()) {
                    if (f.delete()) {
                        if (f.mkdir())
                            return path;
                        else
                            return null;
                    } else
                        return null;
                } else
                    return path;
            }
        }
        return null;
    }

    /**
     * 判断路径是否存在
     *
     * @param path 路径
     * @return　path
     */
    public static boolean isExist(String path) {
        if (path == null || path.trim().equals(""))
            return false;
        File f = new File(path);
        return f.exists();
    }

    /**
     * 获取指定文件大小
     *
     * @param file 文件
     * @return 文件的大小 （字节）
     */
    public static String getFileSize(File file) {
        String fileSize = null;
        if (file.exists()) {
            long size = file.length();
            if (size / 1024f / 1024f > 1) {
                float length = size / 1024f / 1024f;
                fileSize = String.format(Locale.CHINA, "%.2f", length) + "MB";
            } else if (size / 1024f > 1 && size / 1024f / 1024f < 1) {
                float length = size / 1024f;
                fileSize = String.format(Locale.CHINA, "%.2f", length) + "KB";
            } else {
                fileSize = size + "B";
            }

        }
        return fileSize;
    }

    /**
     * 删除
     *
     * @param file
     */
    public static void deleteFile(File file) {
        if (file != null && file.exists()) { // 判断文件是否存在
            if (file.isFile()) { // 判断是否是文件
                file.delete();
            } else if (file.isDirectory()) { // 否则如果它是一个目录
                File files[] = file.listFiles(); // 声明目录下所有的文件 files[];
                for (File file1 : files) { // 遍历目录下所有的文件
                    deleteFile(file1); // 把每个文件 用这个方法进行迭代
                }
            }
            file.delete();
        }
    }

    /**
     * 获取文件的名称
     *
     * @param path 路径
     * @return 文件名
     */
    public static String getFileName(String path) {
        int start = path.lastIndexOf("/");
        int end = path.lastIndexOf(".");
        if (start != -1 && end != -1) {
            return path.substring(start + 1);
        } else {
            return null;
        }
    }


}
