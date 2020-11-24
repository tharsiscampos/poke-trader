package tharsiscampos.poketrader.ctr;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import tharsiscampos.poketrader.service.TradeSRV;
import tharsiscampos.poketrader.service.TradeSRV.AnaliseTrocaTO;
import tharsiscampos.poketrader.service.TradeSRV.DadosParaAnaliseTrocaTO;
import tharsiscampos.poketrader.service.TradeSRV.ListagemTrocaTO;

@RestController
public class TradeCTR {
	
	@Autowired ApplicationContext ac;
	@Autowired TradeSRV tradeSRV;

	@PostMapping("/trade/analisar")
	public AnaliseTrocaTO analisar(@RequestBody DadosParaAnaliseTrocaTO to) {
		return tradeSRV.analisarTroca(to);
	}

	@PostMapping("/trade/realizar")
	public void realizar(@RequestBody DadosParaAnaliseTrocaTO to) {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tradeSRV.realizarTroca(to);
	}

	@GetMapping("/trade/listar")
	public List<ListagemTrocaTO> listar() {
		return tradeSRV.listarTrocas();
	}
}