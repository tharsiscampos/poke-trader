package tharsiscampos.poketrader.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import tharsiscampos.poketrader.entity.Troca;

public interface TrocaREPO extends CrudRepository<Troca, Integer> {

	public List<Troca> findAllByOrderByDataDesc();
}
