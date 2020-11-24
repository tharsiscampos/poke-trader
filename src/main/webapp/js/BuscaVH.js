
class BuscaVH extends VH {
	
	constructor(idModal) {
		
		super();
		
		this.numPagina = 1;
		this.$playerCard = null;
		this.$modal = $(idModal);
		this.$divBusca = this.$modal.find(".modal-body .busca-pokes");
		this.$pokesSelecionados = this.$modal.find(".pokes-selecionados");
		var thisVH = this;
		
		this.$modal.find(".botao-finalizar-selecao").on("click", function() {
			thisVH.onFinalizarSelecao();
		});
	}
	
	exibirBuscaPokemons(nomePlayer, onSelecaoFinalizada) {

		this.onSelecaoFinalizada = onSelecaoFinalizada;
		this.$modal.find(".modal-title span").text(nomePlayer);
		this.$divBusca.html(this.getDivAguardando());
		this.$pokesSelecionados.html("");
		this.$modal.modal();
		var thisVH = this;
		
		Backend.getInstancia().buscarPokes(this.numPagina).done(function(listagemPokesTO) {
			this.numPagina = listagemPokesTO.numPagina;
			thisVH.$divBusca.html("");
			
			$.each(listagemPokesTO.listaListagemPokeTO, function(indice, listagemPokeTO) {
				var $poke = $("<a class='btn btn-link col-md-6'>" + listagemPokeTO.nome + "</a>");
				$poke.data("listagemPokeTO", listagemPokeTO);
				$poke.on("click", function() {
					thisVH.onPokeSelecionado(this);
				})
				thisVH.$divBusca.append($poke);
			});
			
		}).fail(function() {
			thisVH.$divBusca.html("Ops, ocorreu um erro na listagem dos pokemons");
		});
	}
	
	onPokeSelecionado(linkPoke) {
		
		var listagemPokeTO = $(linkPoke).data("listagemPokeTO");
		var thisVH = this;
		
		Backend.getInstancia().detalharPoke(listagemPokeTO.id).done(function(detalhesPokeTO) {
			
			var $tr = $("<tr class='poke-selecionado'>");
			
			$tr.data("detalhesPokeTO", detalhesPokeTO);
			
			$tr.append("<td class='text-center'>" + detalhesPokeTO.nome + "</td>");
			$tr.append("<td class='text-center'>" + detalhesPokeTO.baseExperience + "</td>");
			
			var $botaoRemover = $("<button class='btn btn-link'>&times;</button>").on("click", function() {
				$tr.remove();
			});
			
			$tr.append($("<td class='text-center'>").append($botaoRemover));
			
			thisVH.$pokesSelecionados.append($tr);
			
		}).fail(function() {
		});
	}
	
	onFinalizarSelecao() {
		var tos = [];
		
		this.$pokesSelecionados.find(".poke-selecionado").each(function() {
			var $poke = $(this);
			var detalhesPokeTO = $poke.data("detalhesPokeTO");
			tos.push(detalhesPokeTO);
		});
		
		this.onSelecaoFinalizada(tos);
	}
}

