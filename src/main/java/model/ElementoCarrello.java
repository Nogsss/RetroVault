package model;

import java.io.Serializable;

public class ElementoCarrello implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Prodotto prodotto;
	private int quantità;
	
	public ElementoCarrello() {}

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
