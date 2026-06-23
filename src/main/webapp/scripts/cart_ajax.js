function aggiungiAlCarrello(idProdotto, quantita, path) {
    var request = createXMLHttpRequest();
    if(request){
		request.onreadystatechange = function() {
        	if (this.readyState == 4 && this.status == 200) {
            	var response = JSON.parse(this.responseText);
				
				// Aggiorna il contatore del carrello nell'header
            	document.getElementById("cart-counter").innerHTML = response.nuovoTotale;
        	}
		}
	}
	
	setTimeout(function() { 
		if(request.readyState < 4){
			request.abort();
		}
	}, 15000);
 	
	var url = path + "/carrello?action=addAjax&id=" + idProdotto + "&quantita=" + quantita;
    
    request.open("GET", url, true);
    request.send();
}

function eliminaElemento(idProdotto, path) {
    var request = createXMLHttpRequest();
    if(request){
		request.onreadystatechange = function() {
        	if (this.readyState == 4 && this.status == 200) {
            	var response = JSON.parse(this.responseText);
            	
            	// Aggiorna il contatore del carrello nell'header
            	document.getElementById("cart-counter").innerHTML = response.nuovoTotale;
            	
            	// Rimuovi la riga del prodotto dal DOM
            	var riga = document.getElementById("riga_" + idProdotto);
            	if (riga) {
            	    riga.parentNode.removeChild(riga);
            	}
            	
            	// Aggiorna il totale o mostra il messaggio di carrello vuoto
            	if (response.nuovoTotale == 0) {
            	    mostraCarrelloVuoto(path);
            	} else {
            	    document.getElementById("prezzo-totale").innerHTML = response.prezzoTotale.toFixed(2);
            	}
        	}
		}
	}
	
	var url = path + "/carrello?action=removeAjax&id=" + idProdotto;
    request.open("GET", url, true);
    request.send();
}

function modificaQuantita(idProdotto, quantita, path) {
    // Se la quantità è invalida o minore/uguale a 0, rimuovi l'elemento
    var q = parseInt(quantita);
    if (isNaN(q) || q <= 0) {
        eliminaElemento(idProdotto, path);
        return;
    }

    var request = createXMLHttpRequest();
    if(request){
		request.onreadystatechange = function() {
        	if (this.readyState == 4 && this.status == 200) {
            	var response = JSON.parse(this.responseText);
            	
            	// Aggiorna il contatore nell'header e il totale dell'ordine
            	document.getElementById("cart-counter").innerHTML = response.nuovoTotale;
            	document.getElementById("prezzo-totale").innerHTML = response.prezzoTotale.toFixed(2);
            	
            	// Aggiorna il valore dell'input quantita
            	var input = document.getElementById("quantita_" + idProdotto);
            	if (input) input.value = q;
            	
            	// Calcola e aggiorna il subtotale
            	var unitPrice = parseFloat(document.getElementById("prezzo_unitario_" + idProdotto).getAttribute("data-valore"));
            	var subtotal = unitPrice * q;
            	document.getElementById("subtotale_" + idProdotto).innerHTML = subtotal.toFixed(2);
        	}
		}
	}
	
	var url = path + "/carrello?action=updateAjax&id=" + idProdotto + "&quantita=" + q;
    request.open("GET", url, true);
    request.send();
}

function incrementa(idProdotto, path) {
    var input = document.getElementById("quantita_" + idProdotto);
    if (input) {
        var val = parseInt(input.value) + 1;
        modificaQuantita(idProdotto, val, path);
    }
}

function decrementa(idProdotto, path) {
    var input = document.getElementById("quantita_" + idProdotto);
    if (input) {
        var val = parseInt(input.value) - 1;
        modificaQuantita(idProdotto, val, path);
    }
}

function svuotaInteroCarrello(path) {
    var request = createXMLHttpRequest();
    if(request){
		request.onreadystatechange = function() {
        	if (this.readyState == 4 && this.status == 200) {
            	// Aggiorna il contatore
            	document.getElementById("cart-counter").innerHTML = "0";
            	mostraCarrelloVuoto(path);
        	}
		}
	}
	
	var url = path + "/carrello?action=clearAjax";
    request.open("GET", url, true);
    request.send();
}


//Funzioni di supporto
function mostraCarrelloVuoto(path) {
    var content = document.getElementById("cart-content");
    if (content) {
        content.innerHTML = 
            '<div class="empty-cart-message">' +
            '    <p>Il tuo carrello è vuoto. Cerca qualcosa nel catalogo!</p>' +
            '    <a href="' + path + '/catalogo" class="btn-primary">Vai al Catalogo</a>' +
            '</div>';
    }
}

function createXMLHttpRequest() {
	var request;
	try{
		request = new XMLHttpRequest();
	}
	catch(e) {
		try{
			request = new ActiveXObject("Msxm12.XMLHTTP");
		}
		catch(e) {
			try{
				request = new ActiveXObject("Microsoft.XMLHTTP");
			}
			catch(e){
				alert("Il browser non supporta AJAX");
				return null;
			}
		}
	}
	return request;
}