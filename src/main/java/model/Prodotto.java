package model;

import java.io.Serializable;

public class Prodotto implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String  nome;
	private String descrizione;
	private String categoria;
	private double prezzo;
	private int quantitàDisponibile;
	private String imgPath;
	private boolean attivo;
	
	public Prodotto() {}
	
	//getters and setters
	
	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public double getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(double prezzo) {
		this.prezzo = prezzo;
	}

	public int getQuantitàDisponibile() {
		return quantitàDisponibile;
	}

	public void setQuantitàDisponibile(int quantitàDisponibile) {
		this.quantitàDisponibile = quantitàDisponibile;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public boolean isAttivo() {
		return attivo;
	}

	public void setAttivo(boolean attivo) {
		this.attivo = attivo;
	}

}
