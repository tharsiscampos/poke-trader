package tharsiscampos.poketrader.ctr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tharsiscampos.poketrader.service.PokeSRV;
import tharsiscampos.poketrader.service.PokeSRV.DetalhesPokeTO;
import tharsiscampos.poketrader.service.PokeSRV.ListagemPokesTO;

@RestController
public class PokesCTR {
	
	@Autowired ApplicationContext ac;
	@Autowired PokeSRV pokeService;

	@GetMapping("/pokes")
	public ListagemPokesTO listar(@RequestParam Integer numPagina) {
		return pokeService.listar(numPagina);
	}

	@GetMapping("/pokes/{id}")
	public DetalhesPokeTO recuperar(@PathVariable("id") Integer id) {
		return pokeService.recuperarDetalhes(id);
	}
}