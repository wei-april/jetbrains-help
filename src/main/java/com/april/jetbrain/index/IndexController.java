package com.april.jetbrain.index;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.text.CharSequenceUtil;
import com.april.jetbrain.context.AgentContextHolder;
import com.april.jetbrain.context.PluginsContextHolder;
import com.april.jetbrain.context.ProductsContextHolder;
import com.april.jetbrain.properties.JetbrainsHelpProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.util.List;

import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;
import static org.springframework.http.MediaType.APPLICATION_OCTET_STREAM;

/**
 * @author april
 * @date 2024-04-15
 * @description
 */
@Controller
@RequiredArgsConstructor
public class IndexController {

    private final JetbrainsHelpProperties jetbrainsHelpProperties;

    @GetMapping
    public String index(Model model) {
        List<ProductsContextHolder.ProductCache> productCacheList = ProductsContextHolder.productCacheList();
        List<PluginsContextHolder.PluginCache> pluginCacheList = PluginsContextHolder.pluginCacheList();
        model.addAttribute("products", productCacheList);
        model.addAttribute("plugins", pluginCacheList);
        model.addAttribute("defaults", jetbrainsHelpProperties);
        return "index";
    }

    @GetMapping("search")
    public String index(@RequestParam(required = false) String search, Model model) {
        List<ProductsContextHolder.ProductCache> productCacheList = ProductsContextHolder.productCacheList();
        List<PluginsContextHolder.PluginCache> pluginCacheList = PluginsContextHolder.pluginCacheList();
        if (CharSequenceUtil.isNotBlank(search)) {
            productCacheList = productCacheList.stream()
                    .filter(productCache -> CharSequenceUtil.containsIgnoreCase(productCache.getName(), search))
                    .toList();
            pluginCacheList = pluginCacheList.stream()
                    .filter(pluginCache -> CharSequenceUtil.containsIgnoreCase(pluginCache.getName(), search))
                    .toList();
        }
        model.addAttribute("products", productCacheList);
        model.addAttribute("plugins", pluginCacheList);
        model.addAttribute("defaults", jetbrainsHelpProperties);
        return "index::product-list";
    }

    @GetMapping("ja-netfilter")
    @ResponseBody
    public ResponseEntity<Resource> downloadJaNetfilter() {
        File jaNetfilterZipFile = AgentContextHolder.jaNetfilterZipFile();
        return ResponseEntity.ok()
                .header(CONTENT_DISPOSITION, "attachment;filename=" + jaNetfilterZipFile.getName())
                .contentType(APPLICATION_OCTET_STREAM)
                .body(new InputStreamResource(FileUtil.getInputStream(jaNetfilterZipFile)));
    }
}
