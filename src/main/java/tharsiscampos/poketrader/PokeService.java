package tharsiscampos.poketrader;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import me.sargunvohra.lib.pokekotlin.client.PokeApi;
import me.sargunvohra.lib.pokekotlin.model.NamedApiResource;
import me.sargunvohra.lib.pokekotlin.model.NamedApiResourceList;
import me.sargunvohra.lib.pokekotlin.model.Pokemon;

@Service
public class PokeService {
	
	@Autowired ApplicationContext ac;
	@Autowired PokeApi pokeApi;

	public static class ListagemPokesTO {
		public List<ListagemPokeTO> listaListagemPokeTO;
		public Integer numPagina;
	}
	
	public static class ListagemPokeTO {
		public Integer id;
		public String nome;
	}

	@Cacheable("poke-listagem")
	public ListagemPokesTO listar(Integer numPagina) {
		ListagemPokesTO to = new ListagemPokesTO();
		
		to.listaListagemPokeTO = new ArrayList<>();
		to.numPagina = numPagina;
		
		if (to.numPagina == null) {
			to.numPagina = 1;
		}
		
		int offset = (to.numPagina - 1) * 20;
		
		NamedApiResourceList narl = pokeApi.getPokemonList(offset, 20);
		
		for (NamedApiResource nar : narl.getResults()) {
			ListagemPokeTO pTO = new ListagemPokeTO();
			pTO.id = nar.getId();
			pTO.nome= nar.getName();
			to.listaListagemPokeTO.add(pTO);
		}
		
		return to;
	}
	
	public static class DetalhesPokeTO {
		public Integer id;
		public String nome;
		public Integer baseExperience;
	}

	@Cacheable("poke-detalhes")
	public DetalhesPokeTO recuperar(Integer id) {
		Pokemon p = pokeApi.getPokemon(id);
		DetalhesPokeTO to = new DetalhesPokeTO();
		to.id = p.getId();
		to.nome = p.getName();
		to.baseExperience = p.getBaseExperience();
		return to;
	}
	
}