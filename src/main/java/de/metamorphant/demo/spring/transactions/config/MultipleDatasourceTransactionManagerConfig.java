package de.metamorphant.demo.spring.transactions.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class MultipleDatasourceTransactionManagerConfig {

    @Bean(name = "chainedTransactionManager")
    public ChainedTransactionManager chainedTransactionManager(
            @Qualifier("accountingTransactionManager") PlatformTransactionManager accountingTransactionManager,
            @Qualifier("warehouseTransactionManager")PlatformTransactionManager warehouseTransactionManager ){
        return new ChainedTransactionManager(accountingTransactionManager,warehouseTransactionManager);
    }

}
