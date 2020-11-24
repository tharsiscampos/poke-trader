# poke-trader

Aplicação web em Java 15 e Spring Boot 2.2.6 que implementa uma calculadora de troca de pokemons, com análise de "justiça" na troca. A análise foi baseada na característica "base_experience" dos prokemons. 

Para obtenção das informações dos pokemons foi utilizada a API pública https://pokeapi.co/docs/v2. Como a primera regra desta API é "Locally cache resources whenever you request them", o projeto da solução foi pensado levando isso em conta. A listagem dos pokemons é guardada no cache da aplicação (só perde quando reinicia a máquina) e o detalhamento dos pokemons é gravado na base sempre que carregado da API. Para acesso à API foi utilizado o wrapper para java https://github.com/PokeAPI/pokekotlin.

O backend é java e consiste em Controllers traduzindo o request/response para TOs (Transfer Objects). Os TOs são enviados/obtidos dos SRVs (Service, classe de negócio). Cada SRV manipula as classes REPO (Repository) e entidades JPA para realizar as ações.

O frontend é em javascript e consiste em VHs (View Helpers) que mapeiam os eventos da tela para o backend para, então, renderizar o HTML via JQuery, Javascript e Bootstrap (https://getbootstrap.com/).·

Foram escritas classes de teste para simplicar a construção da aplicação, mas nem todas as ações possuem testes escritos pois o processo de desenvolvimento não necessitou.

São duas pessoas envolvidas nas trocas, o player A (Aldanice) e B (Bernardino). A análise se uma troca é justa ou não foi baseada na diferença da base_experience. Se essa diferença for maior ou igual a base_experience de um dos pokemons da troca, julguei não ser justa pois a diferença passa a ficar desproporcional aos valores dos pokemons envolvidos. Difícil saber se o critério é adequado, mas me pareceu "bom", pelo menos.

