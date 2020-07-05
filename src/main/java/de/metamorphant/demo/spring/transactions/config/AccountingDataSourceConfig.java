package de.metamorphant.demo.spring.transactions.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@PropertySource({"classpath:application.properties"})
@EnableJpaRepositories(
        basePackages = "de.metamorphant.demo.spring.transactions.dao.accounting.repositories",
        entityManagerFactoryRef = "accountingEntityManagerFactory",
        transactionManagerRef = "accountingTransactionManager"
)
public class AccountingDataSourceConfig {

    @Autowired
    private Environment env;

    @Bean
    public LocalContainerEntityManagerFactoryBean accountingEntityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em
                = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(accountingDataSource());
        em.setPackagesToScan(new String[]{"de.metamorphant.demo.spring.transactions.dao.accounting.model"});

        HibernateJpaVendorAdapter vendorAdapter
                = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto",
                env.getProperty("spring.jpa.hibernate.ddl.auto"));
        em.setJpaPropertyMap(properties);

        return em;
    }

    @Bean(name = "accountingDataSource")
    @ConfigurationProperties(prefix = "spring.datasource-accounting")
    public DataSource accountingDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "accountingTransactionManager")
    public PlatformTransactionManager accountingTransactionManager() {
        JpaTransactionManager transactionManager
                = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(
                accountingEntityManagerFactory().getObject());
        return transactionManager;
    }
}
