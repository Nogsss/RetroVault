function aggiungiAlCarrello(idProdotto, quantita) {
    var request = createXMLHttpRequest();
    //callback
    if(request){
		request.onreadystatechange = function() {
        	if (this.readyState == 4 && this.status == 200) {
            	var response = JSON.parse(this.responseText);
            	document.getElementById("cart-counter").innerHTML = response.nuovoTotale;
        	}
		}
	};
	
	setTimeout(function() { 
		if(request.readyState < 4){
			request.abort();
		}
	}, 15000);
 	
    var url ="RetroVault/carrello?action=addAjax&id=" + idProdotto + "&quantita=" + quantita;
    
    request.open("GET", url, true);
    request.send();
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