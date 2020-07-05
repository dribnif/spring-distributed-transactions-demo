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
        basePackages = "de.metamorphant.demo.spring.transactions.dao.warehouse.repositories",
        entityManagerFactoryRef = "warehouseEntityManagerFactory",
        transactionManagerRef = "warehouseTransactionManager"
)
public class WarehouseDataSourceConfig {

    @Autowired
    private Environment env;

    @Bean
    public LocalContainerEntityManagerFactoryBean warehouseEntityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em
                = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(warehouseDataSource());
        em.setPackagesToScan(new String[]{"de.metamorphant.demo.spring.transactions.dao.warehouse.model"});

        HibernateJpaVendorAdapter vendorAdapter
                = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto",
                env.getProperty("spring.jpa.hibernate.ddl.auto"));
        em.setJpaPropertyMap(properties);

        return em;
    }

    @Bean(name = "warehouseDataSource")
    @ConfigurationProperties(prefix = "spring.datasource-warehouse")
    public DataSource warehouseDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "warehouseTransactionManager")
    public PlatformTransactionManager warehouseTransactionManager() {
        JpaTransactionManager transactionManager
                = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(
                warehouseEntityManagerFactory().getObject());
        return transactionManager;
    }
}
