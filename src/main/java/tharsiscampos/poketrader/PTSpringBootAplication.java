package tharsiscampos.poketrader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import com.fasterxml.jackson.databind.ObjectMapper;

import me.sargunvohra.lib.pokekotlin.client.PokeApi;
import me.sargunvohra.lib.pokekotlin.client.PokeApiClient;

@SpringBootApplication
@EnableCaching
public class PTSpringBootAplication {
	public static void main(String[] args) throws Throwable {
		SpringApplication.run(PTSpringBootAplication.class, args);
	}

	@Bean @Primary public ObjectMapper objectMapper() {
        return UtilJson.OBJECT_MAPPER;
    }

	@Bean @Primary public PokeApi pokeApi() {
        return new PokeApiClient();
    }
}
