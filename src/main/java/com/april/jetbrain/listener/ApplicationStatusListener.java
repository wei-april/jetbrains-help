package com.april.jetbrain.listener;

import com.april.jetbrain.context.AgentContextHolder;
import com.april.jetbrain.context.CertificateContextHolder;
import com.april.jetbrain.context.PluginsContextHolder;
import com.april.jetbrain.context.ProductsContextHolder;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author april
 * @date 2024-04-15
 * @description
 */
@Component
public class ApplicationStatusListener {

    @EventListener(ApplicationReadyEvent.class)
    public void ready() {
        ProductsContextHolder.init();
        PluginsContextHolder.init();
        CertificateContextHolder.init();
        AgentContextHolder.init();
    }

}
