package com.april.jetbrain;

import cn.hutool.extra.spring.SpringUtil;
import com.april.jetbrain.context.AgentContextHolder;
import com.april.jetbrain.context.CertificateContextHolder;
import com.april.jetbrain.context.PluginsContextHolder;
import com.april.jetbrain.context.ProductsContextHolder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Import;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @author april
 * @date 2024-04-15
 * @description
 */
@SpringBootApplication
@EnableScheduling
@Import(SpringUtil.class)
public class JetbrainsHelpApplication {

    public static void main(String[] args) {
        SpringApplication.run(JetbrainsHelpApplication.class, args);
    }


    @Scheduled(cron = "0 0 12 * * ?")
    public void refresh() {
        PluginsContextHolder.refreshJsonFile();
    }
}
