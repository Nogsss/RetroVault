package model;

import java.io.Serializable;

public class ElementoCarrello implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Prodotto prodotto;
	private int quantità;
	
	public ElementoCarrello() {}
	
	
	//Costruttore pieno per pulizia del codice in Carrello.java
	public ElementoCarrello(Prodotto prodotto, int quantità) {
		this.prodotto = prodotto;
		this.quantità = quantità;
	}
	
	//getters e setters 
	
	public Prodotto getProdotto() {
		return prodotto;
	}

	public void setProdotto(Prodotto prodotto) {
		this.prodotto = prodotto;
	}

	public int getQuantità() {
		return quantità;
	}

	public void setQuantità(int quantità) {
		this.quantità = quantità;
	}
	
	public double getPrezzoTot() {
		return prodotto.getPrezzo() * quantità;
	}
}
