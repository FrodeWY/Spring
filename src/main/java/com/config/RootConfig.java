package com.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.support.PersistenceAnnotationBeanPostProcessor;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by K on 2017/12/3.
 */
@Configuration
@ComponentScan(basePackages = "com",excludeFilters = {@ComponentScan.Filter(type=FilterType.ANNOTATION,value = EnableWebMvc.class)})
@EnableJpaRepositories(basePackages = "com.repository",repositoryImplementationPostfix = "Impl")//可以指定实现类与接口关联的后缀，默认Impl，可以改别的
@EnableTransactionManagement
public class RootConfig {

    @Bean
    public DataSource dataSource(){
        BasicDataSource basicDataSource=new BasicDataSource();
        basicDataSource.setUsername("root");
        basicDataSource.setPassword("123456");
//        basicDataSource.setUrl("jdbc:mysql://localhost:3306/wangyang01?useUnicode=true&characterEncoding=utf8&useSSL=true");
        basicDataSource.setUrl("jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf8&useSSL=true");
        basicDataSource.setDriverClassName("com.mysql.jdbc.Driver");

        return basicDataSource;
    }
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, JpaVendorAdapter jpaVendorAdapter){
        LocalContainerEntityManagerFactoryBean factoryBean=new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setPackagesToScan("com.domain");
        factoryBean.setJpaVendorAdapter(jpaVendorAdapter);
        Properties properties=new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto","update");
        factoryBean.setJpaProperties(properties);
//        factoryBean.afterPropertiesSet();
        return factoryBean;
    }

    @Bean
    public JpaVendorAdapter vendorAdapter(){
        HibernateJpaVendorAdapter jpaVendorAdapter=new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setDatabase(Database.MYSQL);
        jpaVendorAdapter.setDatabasePlatform("org.hibernate.dialect.MySQL5Dialect");
        jpaVendorAdapter.setShowSql(true);
        jpaVendorAdapter.setGenerateDdl(false);//是否开启自动创建表结构
        return jpaVendorAdapter;
    }
   /* @Bean
    public PersistenceAnnotationBeanPostProcessor persistenceAnnotationBeanPostProcessor(){
        return new PersistenceAnnotationBeanPostProcessor();
    }*/

    @Bean/*事务管理默认的bean名称是transactionManager所以如果不指定beanId则方法取名为transactionManager*/
    public PlatformTransactionManager transactionManager(EntityManagerFactory factoryBean){
        JpaTransactionManager transactionManager=new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(factoryBean);
        return transactionManager;
    }
}
