
class PlayerVH {
	
	constructor(cfgs) {
		
		this.listagemPessoaTO = cfgs.listagemPessoaTO;
		this.$player = $(cfgs.seletor);
		this.$consolidado = this.$player.find(".card-footer");
		this.$selecionados = this.$player.find(".card-text");
		this.onSelecaoAlterada = cfgs.onSelecaoAlterada;
		var thisVH = this;
		
		this.$player.find("button.buscar-pokemon").on("click", function() {
			cfgs.onBusca(thisVH.getNome(), function(listaDetalhesPokeTO) {
				thisVH.onPokesSelecionados(listaDetalhesPokeTO);
			});
		});
		
		this.$player.find(".card-header h4").text(this.listagemPessoaTO.nome);
		
		this.consolidarInfos();
	}
	
	getIdPessoa() {
		return this.listagemPessoaTO.id;
	}
	
	getNome() {
		return this.listagemPessoaTO.nome;
	}

	onPokesSelecionados(listaDetalhesPokeTO) {
		
		var $tbody = this.$selecionados.find("tbody");
		
		if ($tbody.length == 0) {
			var $table = $("<table class='table table-hover table-borderless'>");
			$table.append("<thead><tr><th>Espécie</th><th colspan='2'>Base Experience</th></tr></thead>");
			$tbody = $("<tbody>");
			$table.append($tbody);
			this.$selecionados.html($table);			
		}
		
		var thisVH = this;
		
		$.each(listaDetalhesPokeTO, function(indice, detalhesPokeTO) {
			var $tr = $("<tr><td>" + detalhesPokeTO.nome + "</td><td>" + detalhesPokeTO.baseExperience + "</td></tr>");
			
			var $botaoRemover = $("<button class='btn btn-link'>&times;</button>").on("click", function() {
				$tr.remove();
				thisVH.consolidarInfos();
				thisVH.onSelecaoAlterada();
			});
			
			$tr.append($("<td style='width: 20px'>").append($botaoRemover));
			
			$tr.data("detalhesPokeTO", detalhesPokeTO);
			
			$tbody.append($tr);
		});
		
		this.consolidarInfos();
		this.onSelecaoAlterada();
	}
	
	getNumPokemonsNaTroca() {
		return this.$selecionados.find("tbody tr").length;
	}
	
	getSomaBaseExperience() {
		var somaBaseExperience = 0;
		this.$selecionados.find("tbody tr").each(function() {
			var $tr = $(this);
			var detalhesPokeTO = $tr.data("detalhesPokeTO");
			somaBaseExperience += detalhesPokeTO.baseExperience;
		});
		return somaBaseExperience;
	}

	getIdsPokesTroca() {
		var ids = [];
		this.$selecionados.find("tbody tr").each(function() {
			var $tr = $(this);
			var detalhesPokeTO = $tr.data("detalhesPokeTO");
			ids.push(detalhesPokeTO.id);
		});
		return ids;
	}
	
	consolidarInfos() {
		this.$consolidado.html("<p>" + this.getNumPokemonsNaTroca() + " pokemons com " + this.getSomaBaseExperience() + " de base experience.");
	}
	
	esconder() {
		this.$player.hide();
	}
}
