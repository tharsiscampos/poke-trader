
class Backend {
	
	static getInstancia() {
		if (this.singleton == null) {
			this.singleton = new Backend();
		}
		
		return this.singleton;
	}
	
	buscarPokes(numPagina) {
		return $.ajax({
			url: "/pokes?numPagina=" + numPagina,
			cache: true
		});
	}
	
	detalharPoke(id) {
		return $.ajax({
			url: "/pokes/" + id,
			cache: true
		});
	}
	
	recuperarPessoas() {
		return $.ajax({
			dataType: "json",
			url: "/pessoas",
		});
	}
	
	analisarTroca(dadosParaAnaliseTrocaTO) {
		return $.ajax({
			type: "POST",
			dataType: "json",
			url: "/trade/analisar",
			data: JSON.stringify(dadosParaAnaliseTrocaTO),
			contentType: "application/json"
		});
	}
	
	realizarTroca(dadosParaAnaliseTrocaTO) {
		return $.ajax({
			type: "POST",
			url: "/trade/realizar",
			data: JSON.stringify(dadosParaAnaliseTrocaTO),
			contentType: "application/json"
		});
	}
	
	listarTrocas() {
		return $.ajax({
			dataType: "json",
			url: "/trade/listar",
		});
	}
}
