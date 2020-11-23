package tharsiscampos.poketrader.repo;

import org.springframework.data.repository.CrudRepository;

import tharsiscampos.poketrader.entity.Pessoa;

public interface PessoaREPO extends CrudRepository<Pessoa, Integer> {

	public static Integer ID_PESSOA_A = 1;
	public static Integer ID_PESSOA_B = 2;
	
}
