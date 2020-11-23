

$(document).ready(function() {
	
	Backend.getInstancia().recuperarPessoas().done(function(listaListagemPessoaTO) {
		
		new PokeTraderVH({
			seletorBusca: "#popup-busca", 
			seletorPlayerA: "#card-a", 
			seletorPlayerB: "#card-b", 
			seletorSituacao: "#situacao-troca",
			seletorHistorico: "#historico-accordion",
			seletorMenuHistorico: "#botao-menu-historico",
			seletorMenuNovaTroca: "#botao-menu-nova-troca",
			listagemPessoaTOA: listaListagemPessoaTO[0],
			listagemPessoaTOB: listaListagemPessoaTO[1],
		}); 
		
	}).fail(function() {
		console.log(arguments);
		alert("Ocorreu um erro.");
	});
	
});

class PokeTraderVH {
	
	constructor(cfgs) {
		
		this.cfgs = cfgs;
		this.$situacao = $(cfgs.seletorSituacao);
		this.historicoVH = new HistoricoVH({
			seletorHistorico: cfgs.seletorHistorico
		});
		this.buscaVH = new BuscaVH(cfgs.seletorBusca);
		
		var thisVH = this;
	
		this.playerVHA = new PlayerVH({
			listagemPessoaTO: cfgs.listagemPessoaTOA,
			seletor: cfgs.seletorPlayerA, 
			onBusca: function(nomePlayer, onSelecaoFinalizada) {
				thisVH.buscaVH.exibirBuscaPokemons(nomePlayer, onSelecaoFinalizada);
			}, 
			onSelecaoAlterada: function() {
				thisVH.atualizarSituacaoDaTroca();
			}
		});
		
		this.playerVHB = new PlayerVH({
			listagemPessoaTO: cfgs.listagemPessoaTOB,
			seletor: cfgs.seletorPlayerB, 
			onBusca: function(nomePlayer, onSelecaoFinalizada) {
				thisVH.buscaVH.exibirBuscaPokemons(nomePlayer, onSelecaoFinalizada);
			}, 
			onSelecaoAlterada: function() {
				thisVH.atualizarSituacaoDaTroca();
			}
		});
		
		this.atualizarSituacaoDaTroca();
		
		$(cfgs.seletorMenuNovaTroca).on("click", function() {
			document.location = "/";
		});
		
		$(cfgs.seletorMenuHistorico).on("click", function() {
			thisVH.exibirHistorico();
			$(this).closest(".navbar-nav").find("li.nav-item").removeClass("active");
			$(this).closest("li.nav-item").addClass("active");
		});
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
			var dadosParaAnaliseTrocaTO = {
				idPessoaA: this.playerVHA.getIdPessoa(),
				pokesA: this.playerVHA.getIdsPokesTroca(),
				idPessoaB: this.playerVHB.getIdPessoa(),
				pokesB: this.playerVHB.getIdsPokesTroca()
			}

			var thisVH = this;
			
			Backend.getInstancia().analisarTroca(dadosParaAnaliseTrocaTO).done(function(analiseTrocaTO) {
		
				var $button = $("<button class='btn btn-sm btn-primary float-right'>").on("click", function() {
					thisVH.realizarTroca(dadosParaAnaliseTrocaTO);
				});
				
				if (analiseTrocaTO.isTrocaInjusta) {
					thisVH.$situacao.addClass("alert-danger").text(analiseTrocaTO.msgAnalise);
					$button.text("Realizar troca mesmo assim");
			
				} else {
					thisVH.$situacao.addClass("alert-success").text(analiseTrocaTO.msgAnalise);
					$button.text("Realizar troca");
				}
				
				thisVH.$situacao.append($button);
				
			}).fail(function() {
				console.log(arguments);
				alert("Ocorreu um erro na análise.");
			});
			
		}
	}
	
	realizarTroca(dadosParaAnaliseTrocaTO) {

		var thisVH = this;
		
		Backend.getInstancia().realizarTroca(dadosParaAnaliseTrocaTO).done(function() {
			$(thisVH.cfgs.seletorMenuHistorico).trigger("click");
						
		}).fail(function() {
			console.log(arguments);
			alert("Ocorreu um erro na troca.");
		});
	}
	
	exibirHistorico() {
		this.$situacao.hide();
		this.playerVHA.esconder();
		this.playerVHB.esconder();
		this.historicoVH.exibirHistorico();
	}
}
