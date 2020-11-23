package tharsiscampos.poketrader.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import me.sargunvohra.lib.pokekotlin.client.PokeApi;
import me.sargunvohra.lib.pokekotlin.model.NamedApiResource;
import me.sargunvohra.lib.pokekotlin.model.NamedApiResourceList;
import me.sargunvohra.lib.pokekotlin.model.Pokemon;
import tharsiscampos.poketrader.entity.Poke;
import tharsiscampos.poketrader.repo.PokeREPO;

@Service
public class PokeSRV {
	
	@Autowired ApplicationContext ac;
	@Autowired PokeApi pokeApi;
	@Autowired PokeREPO pokeREPO;

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

	public DetalhesPokeTO recuperarDetalhes(Integer id) {
		
		Poke poke = recuperar(id);
		
		DetalhesPokeTO to = new DetalhesPokeTO();
		to.id = poke.getId();
		to.nome = poke.getNome();
		to.baseExperience = poke.getBaseExperience();
		
		return to;
	}
	
	public Poke recuperar(Integer id) {
		Optional<Poke> opPoke = pokeREPO.findById(id);
		Poke poke = null;
		
		if (opPoke.isEmpty()) {
			Pokemon p = pokeApi.getPokemon(id);
			
			poke = new Poke();
			poke.setId(id);
			poke.setNome(p.getName());
			poke.setBaseExperience(p.getBaseExperience());
			
			pokeREPO.save(poke);
			
		} else {
			poke = opPoke.get();
		}
		
		return poke;
	}

}