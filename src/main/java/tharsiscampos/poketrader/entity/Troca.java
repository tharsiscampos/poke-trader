package tharsiscampos.poketrader.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Troca {

	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column private Date data;
	
	@Column private String msgAnalise;

	@OneToMany(fetch = FetchType.EAGER, mappedBy="troca")
	private Set<TrocaPoke> trocasPokes;


	
	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMsgAnalise() {
		return msgAnalise;
	}

	public void setMsgAnalise(String msgAnalise) {
		this.msgAnalise = msgAnalise;
	}

	public Set<TrocaPoke> getTrocasPokes() {
		return trocasPokes;
	}

	public void setTrocasPokes(Set<TrocaPoke> trocasPokes) {
		this.trocasPokes = trocasPokes;
	}
	
	

}
