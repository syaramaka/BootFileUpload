package com.mkyong;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mkyong.util.InMemoryDatabaseFactoryBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.sql.DataSource;

//https://www.agilegroup.co.jp/technote/springboot-fileupload-error-handling.html
@SpringBootApplication
@EnableJpaRepositories("com.mkyong.repository")
@EntityScan("com.mkyong.model")
public class SpringBootWebApplication {

    private int maxUploadSizeInMb = 10 * 1024 * 1024; // 10 MB

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SpringBootWebApplication.class, args);
    }

    @Bean
    public FactoryBean<DataSource> dataSource() {
        return new InMemoryDatabaseFactoryBean("db/schema.sql");
    }

//    @Bean
//    public ObjectMapper objectMapper() {
//        return new ObjectMapper();
//    }
    //Tomcat large file upload connection reset
    //http://www.mkyong.com/spring/spring-file-upload-and-connection-reset-issue/
    /*@Bean
    public TomcatEmbeddedServletContainerFactory tomcatEmbedded() {

        TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();

        tomcat.addConnectorCustomizers((TomcatConnectorCustomizer) connector -> {
            if ((connector.getProtocolHandler() instanceof AbstractHttp11Protocol<?>)) {
                //-1 means unlimited
                ((AbstractHttp11Protocol<?>) connector.getProtocolHandler()).setMaxSwallowSize(-1);
            }
        });

        return tomcat;

    }*/

}