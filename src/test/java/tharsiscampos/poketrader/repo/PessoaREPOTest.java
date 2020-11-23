package tharsiscampos.poketrader.repo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import tharsiscampos.poketrader.entity.Pessoa;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class PessoaREPOTest {
	
	@Autowired PessoaREPO pessoaREPO;
	
	private static String NOME1 = "_teste1";
	private static String NOME2 = "_teste2";
	private static Integer ID;
	
	@Test @Order(1) void criar() {
		Pessoa p = new Pessoa();
		p.setNome(NOME1);
		pessoaREPO.save(p);
		ID = p.getId();
	}

	@Test @Order(2) void conferirCriacaoEAlterar() {
		Pessoa p = pessoaREPO.findById(ID).get();
		assertThat(p.getNome()).isEqualTo(NOME1);
		p.setNome(NOME2);
		pessoaREPO.save(p);
	}

	@Test @Order(3) void conferirAlteracaoERemover() {
		Pessoa p = pessoaREPO.findById(ID).get();
		assertThat(p.getNome()).isEqualTo(NOME2);
		pessoaREPO.delete(p);
	}

	@Test @Order(4) void conferirRemocao() {
		assertThat(pessoaREPO.findById(ID).isEmpty()).isTrue();
	}
}
