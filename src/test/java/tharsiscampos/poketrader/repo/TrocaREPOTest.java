package tharsiscampos.poketrader.repo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import tharsiscampos.poketrader.entity.Troca;
import tharsiscampos.poketrader.entity.TrocaPoke;
import tharsiscampos.poketrader.service.PokeSRV;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class TrocaREPOTest {
	
	@Autowired TrocaREPO trocaREPO;
	@Autowired TrocaPokeREPO trocaPokeREPO;
	@Autowired PokeSRV pokeSRV;
	@Autowired PessoaREPO pessoaREPO;
	
	private static Integer ID;
	
	@Test @Order(1) void criar() {
		
		Troca t = new Troca();
		t.setMsgAnalise("teste");
		t.setTrocasPokes(new HashSet<>());
		t.setData(new Date());
		trocaREPO.save(t);
		
		TrocaPoke tp = new TrocaPoke();
		tp.setTroca(t);
		tp.setPessoaOrigem(pessoaREPO.findById(PessoaREPO.ID_PESSOA_A).get());
		tp.setPoke(pokeSRV.recuperar(1));
		trocaPokeREPO.save(tp);

		tp = new TrocaPoke();
		tp.setTroca(t);
		tp.setPessoaOrigem(pessoaREPO.findById(PessoaREPO.ID_PESSOA_A).get());
		tp.setPoke(pokeSRV.recuperar(2));
		trocaPokeREPO.save(tp);

		tp = new TrocaPoke();
		tp.setTroca(t);
		tp.setPessoaOrigem(pessoaREPO.findById(PessoaREPO.ID_PESSOA_B).get());
		tp.setPoke(pokeSRV.recuperar(3));
		trocaPokeREPO.save(tp);
		
		ID = t.getId();
	}

	@Test @Order(2) void confirmarCriacao() {
		Troca t = trocaREPO.findById(ID).get();
		List<Integer> idsPokesA = new ArrayList<>(Arrays.asList(1, 2));
		List<Integer> idsPokesB = new ArrayList<>(Arrays.asList(3));
		
		for (TrocaPoke tp : t.getTrocasPokes()) {
			if (tp.getPessoaOrigem().getId().equals(PessoaREPO.ID_PESSOA_A)) {
				idsPokesA.remove(tp.getPoke().getId());
			} else if (tp.getPessoaOrigem().getId().equals(PessoaREPO.ID_PESSOA_B)) {
				idsPokesB.remove(tp.getPoke().getId());
			}
		}
		
		assertThat(idsPokesA).isEmpty();
		assertThat(idsPokesB).isEmpty();
	}
}
