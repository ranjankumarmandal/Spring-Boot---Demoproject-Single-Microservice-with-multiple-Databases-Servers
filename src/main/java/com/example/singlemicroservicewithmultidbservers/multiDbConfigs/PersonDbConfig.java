package com.example.singlemicroservicewithmultidbservers.multiDbConfigs;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaRepositories(basePackages = {"com.example.singlemicroservicewithmultidbservers.personDb"},
        entityManagerFactoryRef = "getAuthorEM")
public class PersonDbConfig {
    @Bean
    @ConfigurationProperties(
            prefix = "spring.personds"
    )public DataSource getPersonDsource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean getPersonEM(){
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(getPersonDsource());
        em.setPackagesToScan("com.example.demo.personDb");

        Map<String,Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto" , "create ");
        properties.put("hibernate.dialect" , "org.hibernate.dialect.MySQL8Dialect");

        HibernateJpaVendorAdapter hibernateJpaVendorAdapter= new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(hibernateJpaVendorAdapter);
        em.setJpaPropertyMap(properties);
        return em;
    }

    @Bean
    public PlatformTransactionManager getPersonTxnManager(){
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(getPersonEM().getObject());
        return jpaTransactionManager;
    }
}
