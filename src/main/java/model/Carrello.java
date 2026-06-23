package model;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Carrello implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private List<ElementoCarrello> prodotti;
	
	
	public Carrello() {
		this.prodotti = new ArrayList<>();
	}
	
	//getters e setters
	
	public List<ElementoCarrello> getProdotti() {
		return prodotti;
	}

	public void setProdotti(List<ElementoCarrello> prodotti) {
		this.prodotti = prodotti;
	}
	
	public int getNumeroElementi() {
	    return prodotti.size();
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
		
		prodotti.add(new ElementoCarrello(prodotto, quantità));
	}
		
	public void rimuoviProdotto(int id) {
		prodotti.removeIf(el -> el.getProdotto().getId() == id);
	}
	
	public void impostaQuantità(int id, int quantità) {
		//Se la quantità è 0 o meno viene rimosso dal carrello
		if(quantità <= 0) {
			rimuoviProdotto(id);
			return;
		}
		
		for(ElementoCarrello el: prodotti) {
			if(el.getProdotto().getId() == id) {
				el.setQuantità(quantità);
				return;
			}
		}
	}
		
	public void aumentaQuantità(int id){
		
		for(ElementoCarrello el: prodotti) {
			if(el.getProdotto().getId() == id) {
				el.setQuantità(el.getQuantità()+1);
				return;
			}
		}
	}
	
	public void decrementaQuantità(int id) {
		Iterator<ElementoCarrello> iterator = prodotti.iterator();
		
		while(iterator.hasNext()) {
			ElementoCarrello el = iterator.next();
			
			if(el.getProdotto().getId() == id) {
				if(el.getQuantità() - 1 <= 0) iterator.remove();
				else el.setQuantità(el.getQuantità() - 1);
				return;
			}
		}
	}
	
	public void svuota() {
		prodotti.clear();
	}
	
	public double prezzoTotale() {
		double totale = 0;
		
		for(ElementoCarrello el: prodotti) {
			totale += el.getPrezzoTot();
		}
		return totale;
	}
}

