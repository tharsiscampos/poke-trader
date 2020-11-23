package tharsiscampos.poketrader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tharsiscampos.poketrader.PokeService.DetalhesPokeTO;
import tharsiscampos.poketrader.PokeService.ListagemPokesTO;

@RestController
public class PTRestController {
	
	@Autowired ApplicationContext ac;
	@Autowired PokeService pokeService;

	@GetMapping("/pokes")
	public ListagemPokesTO listar(@RequestParam Integer numPagina) {
		return pokeService.listar(numPagina);
	}

	@GetMapping("/pokes/{id}")
	public DetalhesPokeTO recuperar(@PathVariable("id") Integer id) {
		return pokeService.recuperar(id);
	}
}