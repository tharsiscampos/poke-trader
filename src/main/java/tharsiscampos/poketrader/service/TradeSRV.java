package tharsiscampos.poketrader.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import tharsiscampos.poketrader.entity.Pessoa;
import tharsiscampos.poketrader.entity.Poke;
import tharsiscampos.poketrader.entity.Troca;
import tharsiscampos.poketrader.entity.TrocaPoke;
import tharsiscampos.poketrader.repo.PessoaREPO;
import tharsiscampos.poketrader.repo.TrocaPokeREPO;
import tharsiscampos.poketrader.repo.TrocaREPO;

@Service
public class TradeSRV {
	
	@Autowired ApplicationContext ac;
	@Autowired PokeSRV pokeSRV;
	@Autowired PessoaREPO pessoaREPO;
	@Autowired TrocaREPO trocaREPO;
	@Autowired TrocaPokeREPO trocaPokeREPO;

	public static class DadosParaAnaliseTrocaTO {
		public Integer idPessoaA;
		public List<Integer> pokesA;
		public Integer idPessoaB;
		public List<Integer> pokesB;
	}

	public static class AnaliseTrocaTO {
		public String msgAnalise;
		public Boolean isTrocaInjusta;
	}

	public AnaliseTrocaTO analisarTroca(DadosParaAnaliseTrocaTO to) {
		
		String msgTrocaInjusta = getMsgTrocaInjusta(to);
		
		AnaliseTrocaTO analiseTO = new AnaliseTrocaTO();
		
		if (msgTrocaInjusta != null) {
			analiseTO.msgAnalise = msgTrocaInjusta;
			analiseTO.isTrocaInjusta = true;
		} else {
			analiseTO.msgAnalise = "A troca é justa!";
			analiseTO.isTrocaInjusta = false;
		}
		
		return analiseTO;
	}
	
	private String getMsgTrocaInjusta(DadosParaAnaliseTrocaTO to) {
		
		List<Integer> todosIds = new ArrayList<>();
		todosIds.addAll(to.pokesA);
		todosIds.addAll(to.pokesB);
		
		Poke pokeComMenorBaseExperience = getComMenorBaseExperience(todosIds);
		int somaBaseExperienceA = somarBaseExperience(to.pokesA);
		int somaBaseExperienceB = somarBaseExperience(to.pokesB);
		
		int diferenca = Math.abs(somaBaseExperienceA - somaBaseExperienceB);
		
		Pessoa pessoaA = pessoaREPO.findById(to.idPessoaA).get();
		Pessoa pessoaB = pessoaREPO.findById(to.idPessoaB).get();
		
		if (diferenca >= pokeComMenorBaseExperience.getBaseExperience()) {
			String nomePessoaPerdendo = (somaBaseExperienceA > somaBaseExperienceB ? pessoaA.getNome() : pessoaB.getNome());
			return "A troca não é justa pois " + nomePessoaPerdendo + " está pelo menos perdendo base experience equivalente a um " + pokeComMenorBaseExperience.getNome() + ", envolvido na troca.";
		}

		return null;
	}
	
	private int somarBaseExperience(List<Integer> idsPokes) {

		int soma = 0;
		
		for (Integer id : idsPokes) {
			Poke p = pokeSRV.recuperar(id);
			
			soma += p.getBaseExperience();
		}
		
		return soma;
	}

	private Poke getComMenorBaseExperience(List<Integer> idsPokes) {
		
		Poke poke = null;
		
		for (Integer id : idsPokes) {
			Poke p2 = pokeSRV.recuperar(id);
			
			if (poke == null || poke.getBaseExperience() > p2.getBaseExperience()) {
				poke = p2;
			}
		}
		
		return poke;
	}

	public void realizarTroca(DadosParaAnaliseTrocaTO to) {
		
		AnaliseTrocaTO analiseTO = analisarTroca(to);
		
		Troca t = new Troca();
		t.setData(new Date());
		t.setMsgAnalise(analiseTO.msgAnalise);
		t.setTrocasPokes(new HashSet<>());
		trocaREPO.save(t);
		
		Pessoa pessoaA = pessoaREPO.findById(to.idPessoaA).get();
		Pessoa pessoaB = pessoaREPO.findById(to.idPessoaB).get();

		for (Integer id : to.pokesA) {
			Poke poke = pokeSRV.recuperar(id);
			TrocaPoke tp = new TrocaPoke();
			tp.setTroca(t);
			tp.setPessoaOrigem(pessoaA);
			tp.setPoke(poke);
			trocaPokeREPO.save(tp);
		}
		
		for (Integer id : to.pokesB) {
			Poke poke = pokeSRV.recuperar(id);
			TrocaPoke tp = new TrocaPoke();
			tp.setTroca(t);
			tp.setPessoaOrigem(pessoaB);
			tp.setPoke(poke);
			trocaPokeREPO.save(tp);
		}
	}

	public static class ListagemTrocaTO {
		public Integer id;
		public String dataTroca;
		public String msgAnalise;
		public DetalhesTrocaPessoaTO detalhesTrocaPessoaTOA;
		public DetalhesTrocaPessoaTO detalhesTrocaPessoaTOB;
	}
	
	public static class DetalhesTrocaPessoaTO {
		public Integer idPessoa;
		public String nomePessoa;
		public String ofertas;
	}
	
	public List<ListagemTrocaTO> listarTrocas() {
		List<ListagemTrocaTO> tos = new ArrayList<>();
		
		for (Troca t : trocaREPO.findAllByOrderByDataDesc()) {
			ListagemTrocaTO to = new ListagemTrocaTO();
			to.id = t.getId();
			to.dataTroca = UtilData.toUsuarioDataHora(t.getData());
			to.msgAnalise = t.getMsgAnalise();
			tos.add(to);
			
			for (TrocaPoke tp : t.getTrocasPokes()) {
				Pessoa pessoa = tp.getPessoaOrigem();
				Poke poke = tp.getPoke();
				DetalhesTrocaPessoaTO dTO = null;
				
				if (to.detalhesTrocaPessoaTOA == null) {
					to.detalhesTrocaPessoaTOA = new DetalhesTrocaPessoaTO();
					to.detalhesTrocaPessoaTOA.nomePessoa = pessoa.getNome();
					to.detalhesTrocaPessoaTOA.idPessoa = pessoa.getId();
					dTO = to.detalhesTrocaPessoaTOA;
					
				} else if (to.detalhesTrocaPessoaTOA.idPessoa.equals(pessoa.getId())) {
					dTO = to.detalhesTrocaPessoaTOA;
					
				} else if (to.detalhesTrocaPessoaTOB == null) {
					to.detalhesTrocaPessoaTOB = new DetalhesTrocaPessoaTO();
					to.detalhesTrocaPessoaTOB.nomePessoa = pessoa.getNome();
					to.detalhesTrocaPessoaTOB.idPessoa = pessoa.getId();
					dTO = to.detalhesTrocaPessoaTOB;
					
				} else if (to.detalhesTrocaPessoaTOB.idPessoa.equals(pessoa.getId())) {
					dTO = to.detalhesTrocaPessoaTOB;
				}
				
				String oferta = poke.getNome() + " (" + poke.getBaseExperience() + ")";
				
				if (dTO.ofertas == null) {
					dTO.ofertas = oferta;
				} else {
					dTO.ofertas += ", " + oferta;
				}
			}
		}
		
		return tos;
	}
}