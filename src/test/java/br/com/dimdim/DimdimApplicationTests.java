package br.com.dimdim;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import br.com.dimdim.model.Transaction;
import br.com.dimdim.model.TransactionType;
import br.com.dimdim.model.User;
import br.com.dimdim.repository.TransactionRepository;
import br.com.dimdim.repository.UserRepository;

@SpringBootTest(properties = {
    "spring.datasource.url=jdbc:h2:mem:dimdim_ci;MODE=MSSQLServer;NON_KEYWORDS=VALUE;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE",
    "spring.datasource.username=sa",
    "spring.datasource.password=",
    "spring.datasource.driverClassName=org.h2.Driver",
    "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
    "spring.jpa.hibernate.ddl-auto=create-drop"
})
class DimdimApplicationTests {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TransactionRepository transactionRepository;

	@Test
	@Transactional
	void cadastroETransacaoPersistem() {
		User user = new User();
		user.setName("Cliente RM561584");
		user.setBalance(new BigDecimal("100.00"));
		user = userRepository.saveAndFlush(user);

		Transaction transaction = new Transaction();
		transaction.setDescription("Teste de credito");
		transaction.setValue(new BigDecimal("25.00"));
		transaction.setDate(new Date());
		transaction.setType(TransactionType.CREDIT);
		transaction.setUser(user);
		transactionRepository.saveAndFlush(transaction);

		assertEquals(1, transactionRepository.findByUser_Id(user.getId()).size());
	}
}
