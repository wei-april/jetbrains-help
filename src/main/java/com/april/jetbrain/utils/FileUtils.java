package com.april.jetbrain.utils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.text.CharSequenceUtil;
import lombok.experimental.UtilityClass;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;

/**
 * @author april
 * @date 2024-04-15
 * @description
 */
@UtilityClass
public class FileUtils {

    public static boolean fileExists(String pathOrFile) {
        return FileUtil.file(new ClassPathResource(pathOrFile).getPath()).exists();
    }

    public static File getFileOrCreat(String pathOrFile) {
        File file = FileUtil.file(new ClassPathResource(pathOrFile).getPath());
        if (!file.exists()) {
            try {
                File parentFile = file.getParentFile();
                if (!parentFile.exists() && !parentFile.mkdir()) {
                    throw new IllegalArgumentException(CharSequenceUtil.format("{} File directory create Failed", pathOrFile));
                }
                if (!file.createNewFile()) {
                    throw new IllegalArgumentException(CharSequenceUtil.format("{} File create failed", pathOrFile));
                }
            } catch (IOException e) {
                throw new IllegalArgumentException(CharSequenceUtil.format("{} File create failed", pathOrFile), e);
            }
        }
        return file;
    }
}
