package tharsiscampos.poketrader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import com.fasterxml.jackson.databind.ObjectMapper;

import me.sargunvohra.lib.pokekotlin.client.PokeApi;
import me.sargunvohra.lib.pokekotlin.client.PokeApiClient;
import tharsiscampos.poketrader.entity.Pessoa;
import tharsiscampos.poketrader.repo.PessoaREPO;

@SpringBootApplication
@EnableCaching
public class PokeTraderSpringBootAplication {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public static void main(String[] args) throws Throwable {
		SpringApplication.run(PokeTraderSpringBootAplication.class, args);
	}

	@Bean @Primary public ObjectMapper objectMapper() {
        return UtilJson.OBJECT_MAPPER;
    }
	
	@Bean @Primary public PokeApi pokeApi() {
        return new PokeApiClient();
    }
	
	@Bean public CommandLineRunner loadData(PessoaREPO pessoaREPO) {
	    return (args) -> {
	        if (pessoaREPO.findById(PessoaREPO.ID_PESSOA_A).isEmpty()) {
	        	Pessoa a = new Pessoa();
	        	a.setNome("Aldanice");
	        	a.setId(1);
	        	pessoaREPO.save(a);
	        	logger.info("Pessoa Aldanice criada.");
	        }
	        if (pessoaREPO.findById(PessoaREPO.ID_PESSOA_B).isEmpty()) {
	        	Pessoa b = new Pessoa();
	        	b.setNome("Bernardino");
	        	b.setId(2);
	        	pessoaREPO.save(b);
	        	logger.info("Pessoa Bernardino criada.");
	        }
	    };
	}
}
