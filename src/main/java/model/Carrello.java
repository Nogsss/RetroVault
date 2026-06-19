package model;


import java.io.Serializable;
import java.util.List;

public class Carrello implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private List<ElementoCarrello> prodotti;
	
	
	public Carrello() {}
	
	//getters e setters
	
	public List<ElementoCarrello> getProdotti() {
		return prodotti;
	}

	public void setProdotti(List<ElementoCarrello> prodotti) {
		this.prodotti = prodotti;
	}
	
	//metodi di gestione carrello
		public void aggiungiProdotto(Prodotto prodotto, int quantità) {
			
			//Se la quantità non è valida non fare nulla
			if(quantità <= 0) return;
			
			//Controllo se il prodotto è già nel carrello
			for(ElementoCarrello elem : prodotti) {
				if(elem.getProdotto().getId() == prodotto.getId()) {
					elem.setQuantità(elem.getQuantità()+quantità);
					return;
				}
			}	//Se esce dal for il prodotto non era già nel carrello e lo aggiungo
			
			ElementoCarrello el = new ElementoCarrello();
			el.setProdotto(prodotto);
			el.setQuantità(quantità);
			prodotti.add(el);
		}
}
