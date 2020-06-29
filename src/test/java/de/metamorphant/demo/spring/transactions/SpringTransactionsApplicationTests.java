package de.metamorphant.demo.spring.transactions;

import de.metamorphant.demo.spring.transactions.service.OrderProcessorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringTransactionsApplicationTests {

	@Autowired
	private OrderProcessorService service;

	@Test
	void contextLoads() {
	}

}
