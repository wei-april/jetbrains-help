package com.april.jetbrain.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.StrUtil;
import com.april.jetbrain.context.LicenseContextHolder;
import com.april.jetbrain.context.PluginsContextHolder;
import com.april.jetbrain.context.ProductsContextHolder;
import lombok.Data;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @author april
 * @date 2024-04-15
 * @description
 */
@RestController
public class JetBrainsHelpController {

    @PostMapping("generateLicense")
    public String generateLicense(@RequestBody GenerateLicenseReqBody body) {
        Set<String> productCodeSet;
        if (CharSequenceUtil.isBlank(body.getProductCode())) {
            List<String> productCodeList = ProductsContextHolder.productCacheList()
                    .stream()
                    .map(ProductsContextHolder.ProductCache::getProductCode)
                    .filter(StrUtil::isNotBlank)
                    .map(productCode -> CharSequenceUtil.splitTrim(productCode, ","))
                    .flatMap(Collection::stream)
                    .toList();
            List<String> pluginCodeList = PluginsContextHolder.pluginCacheList()
                    .stream()
                    .map(PluginsContextHolder.PluginCache::getProductCode)
                    .filter(StrUtil::isNotBlank)
                    .toList();
            productCodeSet = CollUtil.newHashSet(productCodeList);
            productCodeSet.addAll(pluginCodeList);
        }else {
            productCodeSet = CollUtil.newHashSet(CharSequenceUtil.splitTrim(body.getProductCode(), ','));
        }
        return LicenseContextHolder.generateLicense(
                body.getLicenseName(),
                body.getAssigneeName(),
                body.getExpiryDate(),
                productCodeSet
        );
    }

    @Data
    public static class GenerateLicenseReqBody {

        private String licenseName;

        private String assigneeName;

        private String expiryDate;

        private String productCode;
    }
}
