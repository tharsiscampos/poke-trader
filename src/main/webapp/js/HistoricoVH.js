
class HistoricoVH {
	
	constructor(cfgs) {
		this.$accordion = $(cfgs.seletorHistorico);
		this.idAtualCard = 0;
	}
	
	exibirHistorico() {
		var thisVH = this;
		Backend.getInstancia().listarTrocas().done(function(listaListagemTrocaTO) {
			$.each(listaListagemTrocaTO, function(indice, listagemTrocaTO) {
				var idCard = "card_" + thisVH.idAtualCard++;
				var detalhes = [];
				
				detalhes.push("<p><strong>" + listagemTrocaTO.detalhesTrocaPessoaTOA.nomePessoa + "</strong> entrou com " + listagemTrocaTO.detalhesTrocaPessoaTOA.ofertas + "</p>"); 
				detalhes.push("<p><strong>" + listagemTrocaTO.detalhesTrocaPessoaTOB.nomePessoa + "</strong> entrou com " + listagemTrocaTO.detalhesTrocaPessoaTOB.ofertas + "</p>");
				detalhes.push("<p><em>" + listagemTrocaTO.msgAnalise + "</em></p>");
				var $card = $(
					'<div class="card">' +
					'	<div class="card-header">' +
					'		<button class="btn btn-link btn-block text-left collapsed" type="button" data-toggle="collapse" data-target="#' + idCard + '" aria-expanded="false" aria-controls="collapseTwo">' + 
					'			Troca realizada em ' + listagemTrocaTO.dataTroca + 
					'		</button>' +
					'	</div>' +
					'	<div class="collapse" id="' + idCard + '" data-parent="#' + thisVH.$accordion.attr("id") + '">' + 
					'		<div class="card-body">' + 
					'			' + detalhes.join("") + 
					'		</div>' +
					'	</div>' +
					'</div>'
				);
				thisVH.$accordion.append($card);
			});
			thisVH.$accordion.show();
			setTimeout(function() {
				thisVH.$accordion.find(".card:first .card-header button").trigger("click");
			}, 500);
		}).fail(function() {
			console.log(arguments);
			alert("Ocorreu um erro.");
		});
	}
		
	esconder() {
		this.$accordion.hide();
	}
}
