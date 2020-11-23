package tharsiscampos.poketrader.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import tharsiscampos.poketrader.repo.PessoaREPO;
import tharsiscampos.poketrader.service.PessoaSRV.ListagemPessoaTO;

@SpringBootTest
public class PessoaSRVTest {
	
	@Autowired PessoaSRV pessoaSRV;
	
	@Test void listar() {
		List<ListagemPessoaTO> tos = pessoaSRV.listar();
		
		assertThat(tos).hasSize(2);
		
		ListagemPessoaTO to = tos.get(0);
		
		assertThat(to.id).isEqualTo(PessoaREPO.ID_PESSOA_A);
		assertThat(to.nome).isEqualTo("Alice");
		
		to = tos.get(1);
		
		assertThat(to.id).isEqualTo(PessoaREPO.ID_PESSOA_B);
		assertThat(to.nome).isEqualTo("Beto");
	}
}
