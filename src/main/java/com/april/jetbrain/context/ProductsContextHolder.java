package com.april.jetbrain.context;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.json.JSONUtil;
import com.april.jetbrain.utils.FileUtils;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.List;

/**
 * @author april
 * @date 2024-04-15
 * @description
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductsContextHolder {

    private static final String PRODUCT_JSON_FILE_NAME = "product.json";

    private static List<ProductCache> productCacheList;

    public static void init() {
        log.info("Product context init loading...");
        File productJsonFile = FileUtils.getFileOrCreat(PRODUCT_JSON_FILE_NAME);

        String productJsonArray;
        try {
            productJsonArray = IoUtil.readUtf8(FileUtil.getInputStream(productJsonFile));
        } catch (IORuntimeException e) {
            throw new IllegalArgumentException(CharSequenceUtil.format("{} File read failed !", PRODUCT_JSON_FILE_NAME), e);
        }
        if (CharSequenceUtil.isBlank(productJsonArray) || !JSONUtil.isTypeJSON(productJsonArray)) {
            log.error("Jetbrains Product data does not exist !");
        } else {
            productCacheList = JSONUtil.toList(productJsonArray, ProductCache.class);
            log.info("Product context init success !");
        }
    }

    public static List<ProductCache> productCacheList() {
        return ProductsContextHolder.productCacheList;
    }

    @Data
    public static class ProductCache {

        private String name;
        private String productCode;
        private String iconClass;
    }
}
