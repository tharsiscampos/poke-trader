package tharsiscampos.poketrader;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PTRestController {

	public static class ListagemPokesTO {
		public Integer id;
		public String nome;
	}
	
	@GetMapping("/pokes")
	public List<ListagemPokesTO> listar() {
		ListagemPokesTO to1 = new ListagemPokesTO();
		to1.id = 1;
		to1.nome= "teste1";
		
		ListagemPokesTO to2 = new ListagemPokesTO();
		to2.id = 2;
		to2.nome= "teste2";
		
		return Arrays.asList(to1, to2);
	}
}