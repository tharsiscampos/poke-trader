package tharsiscampos.poketrader.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import tharsiscampos.poketrader.entity.Pessoa;
import tharsiscampos.poketrader.repo.PessoaREPO;

@Service
public class PessoaSRV {
	
	@Autowired ApplicationContext ac;
	@Autowired PessoaREPO pessoaREPO;
	
	public static class ListagemPessoaTO {
		public Integer id;
		public String nome;
	}

	public List<ListagemPessoaTO> listar() {
		
		// por enquanto só lista os dois primeiros
		
		Pessoa pessoaA = pessoaREPO.findById(PessoaREPO.ID_PESSOA_A).get();
		Pessoa pessoaB = pessoaREPO.findById(PessoaREPO.ID_PESSOA_B).get();
		
		List<ListagemPessoaTO> lista = new ArrayList<>();

		ListagemPessoaTO to = new ListagemPessoaTO();
		to.id = pessoaA.getId();
		to.nome = pessoaA.getNome();
		lista.add(to);

		to = new ListagemPessoaTO();
		to.id = pessoaB.getId();
		to.nome = pessoaB.getNome();
		lista.add(to);
		
		return lista;
	}
		
}