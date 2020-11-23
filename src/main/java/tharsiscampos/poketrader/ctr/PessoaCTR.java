package tharsiscampos.poketrader.ctr;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import tharsiscampos.poketrader.service.PessoaSRV;
import tharsiscampos.poketrader.service.PessoaSRV.ListagemPessoaTO;

@RestController
public class PessoaCTR {
	
	@Autowired ApplicationContext ac;
	@Autowired PessoaSRV pessoaSRV;

	@GetMapping("/pessoas")
	public List<ListagemPessoaTO> listar() {
		return pessoaSRV.listar();
	}
}