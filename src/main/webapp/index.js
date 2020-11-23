

$(document).ready(function() {
	new MainVH("#popup-busca", "#card-a", "#card-b", "#situacao-troca"); 
});

class MainVH {
	constructor(seletorBusca, seletorPlayerA, seletorPlayerB, seletorSituacao) {
		
		this.$situacao = $(seletorSituacao);
		this.buscaVH = new BuscaVH(seletorBusca);
		
		var thisVH = this;
	
		this.playerVHA = new PlayerVH({
			seletor: seletorPlayerA, 
			onBusca: function(nomePlayer, onSelecaoFinalizada) {
				thisVH.buscaVH.exibirBuscaPokemons(nomePlayer, onSelecaoFinalizada);
			}, 
			onSelecaoAlterada: function() {
				thisVH.atualizarSituacaoDaTroca();
			}
		});
		
		this.playerVHB = new PlayerVH({
			seletor: seletorPlayerB, 
			onBusca: function(nomePlayer, onSelecaoFinalizada) {
				thisVH.buscaVH.exibirBuscaPokemons(nomePlayer, onSelecaoFinalizada);
			}, 
			onSelecaoAlterada: function() {
				thisVH.atualizarSituacaoDaTroca();
			}
		});
		
		this.atualizarSituacaoDaTroca();
	}
	
	atualizarSituacaoDaTroca() {
		
		this.$situacao.removeClass("alert-primary alert-success alert-danger");
		
		var numA = this.playerVHA.getNumPokemonsNaTroca();
		var numB = this.playerVHB.getNumPokemonsNaTroca();
		
		if (numA <= 0) {
			this.$situacao.addClass("alert-primary").text(this.playerVHA.getNome() + " precisa selecionar pokemons.");
			
		} else if (numA > 6) {
			this.$situacao.addClass("alert-primary").text(this.playerVHA.getNome() + " precisa selecionar no máximo 6 pokemons.");
		
		} else if (numB <= 0) {
			this.$situacao.addClass("alert-primary").text(this.playerVHB.getNome() + " precisa selecionar pokemons.");
			
		} else if (numB > 6) {
			this.$situacao.addClass("alert-primary").text(this.playerVHB.getNome() + " precisa selecionar no máximo 6 pokemons.");
		
		} else {
			var alertaTrocaInjusta = this.getAlertaTrocaInjusta();
			var $button = $("<button class='btn btn-sm btn-primary float-right'>");
			
			if (alertaTrocaInjusta) {
				this.$situacao.addClass("alert-danger").text(alertaTrocaInjusta);
				$button.text("Efetuar troca mesmo assim");
		
			} else {
				this.$situacao.addClass("alert-success").text("A troca está justa!");
				$button.text("Efetuar troca");
			}
			
			this.$situacao.append($button);
		}
	}
	
	getAlertaTrocaInjusta() {
		
		var detalhesPokeTODoMenorBaseExperienceA = this.playerVHA.getDetalhesPokeTODoMenorBaseExperience();
		var detalhesPokeTODoMenorBaseExperienceB = this.playerVHB.getDetalhesPokeTODoMenorBaseExperience();
		var detalhesPokeTODoMenorBaseExperience = detalhesPokeTODoMenorBaseExperienceA.baseExperience < detalhesPokeTODoMenorBaseExperienceB.baseExperience ? detalhesPokeTODoMenorBaseExperienceA : detalhesPokeTODoMenorBaseExperienceB;
		
		var somaA = this.playerVHA.getSomaBaseExperience();
		var somaB = this.playerVHB.getSomaBaseExperience();
		var diferenca = Math.abs(somaA - somaB);
		var msg = null;
		
		if (diferenca >= detalhesPokeTODoMenorBaseExperience.baseExperience) {
			var nomePlayerPerdendo = (somaA > somaB ? this.playerVHA.getNome() : this.playerVHB.getNome());
			msg = "A troca não está sendo justa pois " + nomePlayerPerdendo + " está pelo menos perdendo base experience equivalente a um " + detalhesPokeTODoMenorBaseExperience.nome + ", envolvido na troca."; 
		}
		
		return msg;
	}
}


class PlayerVH {
	
	constructor(cfgs) {
		
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
		
		this.consolidarInfos();
	}
	
	getNome() {
		return this.$player.find(".card-header h4").text();
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
	
	getDetalhesPokeTODoMenorBaseExperience() {
		var detalhesPokeTOMenorBaseExperience = null;
		this.$selecionados.find("tbody tr").each(function() {
			var $tr = $(this);
			var detalhesPokeTO = $tr.data("detalhesPokeTO");
			if (!detalhesPokeTOMenorBaseExperience || detalhesPokeTOMenorBaseExperience.baseExperience > detalhesPokeTO.baseExperience) {
				detalhesPokeTOMenorBaseExperience = detalhesPokeTO;
			}
		});
		return detalhesPokeTOMenorBaseExperience;
	}
	
	consolidarInfos() {
		this.$consolidado.html("<p>" + this.getNumPokemonsNaTroca() + " pokemons com " + this.getSomaBaseExperience() + " de base experience.");
	}
}

class BuscaVH {
	
	constructor(idModal) {
		
		this.api = new Api();
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
		this.$divBusca.html("<div class='spinner-border spinner-border-sm' role='status'><span class='sr-only'>Carregando...</span></div>");
		this.$pokesSelecionados.html("");
		this.$modal.modal();
		var thisVH = this;
		
		this.api.buscarPokes(this.numPagina).done(function(listagemPokesTO) {
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
		
		this.api.detalharPoke(listagemPokeTO.id).done(function(detalhesPokeTO) {
			
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

class Api {
	
	buscarPokes(numPagina) {
		return $.ajax({
			dataType: "json",
			url: "/pokes?numPagina=" + numPagina,
			cache: true
		});
	}
	
	detalharPoke(id) {
		return $.getJSON("/pokes/" + id);
	}
}
