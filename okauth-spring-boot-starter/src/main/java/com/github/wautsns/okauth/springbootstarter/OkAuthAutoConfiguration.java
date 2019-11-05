package com.github.wautsns.okauth.springbootstarter;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.wautsns.okauth.core.manager.OkAuthManager;
import com.github.wautsns.okauth.core.manager.OkAuthManagerBuilder;
import com.github.wautsns.okauth.springbootstarter.properties.OkAuthProperties;

/**
 *
 * @author wautsns
 */
@Configuration
@EnableConfigurationProperties(OkAuthProperties.class)
public class OkAuthAutoConfiguration {

    @Bean
    public OkAuthManager okAuthManager(OkAuthProperties okAuthProperties) {
        OkAuthManagerBuilder okAuthManagerBuilder = new OkAuthManagerBuilder();
        okAuthProperties.getClients().forEach(okAuthManagerBuilder::register);
        return okAuthManagerBuilder.build();
    }

}
