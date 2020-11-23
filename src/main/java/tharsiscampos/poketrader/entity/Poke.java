package tharsiscampos.poketrader.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Poke {

	@Id private Integer id;
	
	private String nome;
	
	private Integer baseExperience;

	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getBaseExperience() {
		return baseExperience;
	}

	public void setBaseExperience(Integer baseExperience) {
		this.baseExperience = baseExperience;
	}
	
	
}
