package uk.edwinek.service;

import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImplExporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Configuration
@ComponentScan({"uk.edwinek.api"})
public class JsonRpcServiceConfig {

    private static Logger LOGGER = LoggerFactory.getLogger(JsonRpcServiceConfig.class);

    @PostConstruct
    public void init() {
        LOGGER.info( "Service started." );
    }

    @Bean
    public static AutoJsonRpcServiceImplExporter autoJsonRpcServiceImplExporter() {
        return new AutoJsonRpcServiceImplExporter();
    }

    @PreDestroy
    public void shutDown() {
        LOGGER.info( "Service shut down." );
    }
}
