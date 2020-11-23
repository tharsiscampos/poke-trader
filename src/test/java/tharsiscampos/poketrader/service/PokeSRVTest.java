package tharsiscampos.poketrader.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import tharsiscampos.poketrader.service.PokeSRV.DetalhesPokeTO;

@SpringBootTest
public class PokeSRVTest {
	
	@Autowired PokeSRV pokeService;
	
	@Test void recuperar() {
		DetalhesPokeTO to = pokeService.recuperarDetalhes(1);
		assertThat(to.nome).isEqualTo("bulbasaur");
	}
}
