package tharsiscampos.poketrader.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class TrocaPoke {


	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@ManyToOne
	private Troca troca;

	@ManyToOne
	private Pessoa pessoaOrigem;
	
	@ManyToOne
	private Poke poke;

	
	
	public Troca getTroca() {
		return troca;
	}

	public void setTroca(Troca troca) {
		this.troca = troca;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Pessoa getPessoaOrigem() {
		return pessoaOrigem;
	}

	public void setPessoaOrigem(Pessoa pessoaOrigem) {
		this.pessoaOrigem = pessoaOrigem;
	}

	public Poke getPoke() {
		return poke;
	}

	public void setPoke(Poke poke) {
		this.poke = poke;
	}
	
	
}
