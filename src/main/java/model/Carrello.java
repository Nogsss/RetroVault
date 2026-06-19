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
}
