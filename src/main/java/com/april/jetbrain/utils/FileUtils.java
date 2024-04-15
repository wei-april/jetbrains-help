package com.april.jetbrain.utils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ObjectUtil;
import lombok.experimental.UtilityClass;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.core.io.ClassPathResource;

import java.io.File;

/**
 * @author april
 * @date 2024-04-15
 * @description
 */
@UtilityClass
public class FileUtils {

    ApplicationHome application = new ApplicationHome();


    public static boolean fileExists(String path) {
        return getFile(path).exists();
    }

    public static File getFile(String path) {
        File homeDir = application.getDir();
        File source = application.getSource();
        ClassPathResource classPathResource = new ClassPathResource(path);
        return ObjectUtil.isNull(source) ? FileUtil.file(classPathResource.getPath()) : FileUtil.file(homeDir, path);
    }

    public static File getFileOrCreat(String path) {
        File file = getFile(path);
        if (ObjectUtil.isNotNull(application.getSource())) {
            ClassPathResource classPathResource = new ClassPathResource(path);
            File classPathFile = FileUtil.file(classPathResource.getPath());
            if (classPathResource.exists() && !file.exists()) {
                try {
                    FileUtil.writeFromStream(classPathResource.getInputStream(), classPathFile);
                } catch (Exception e) {
                    throw new IllegalArgumentException(CharSequenceUtil.format("{} File read failed", classPathFile.getPath()), e);
                }
                FileUtil.copy(classPathFile, file, true);
            }
        }
        return file;

    }
}
