package model;


import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Ordine implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private int idOrdine;
	private int idUtente;
	private Date data;
	private double totale;
	private String metodoPagamento;
	private String idTransazione;
	private String nome;
	private String cognome;
	private String telefono;
	private String viaNum;
	private String cap;
	private String provincia;
	private String citta;
	
	private List<DettaglioOrdine> dettagli;
	
	public Ordine() {
		this.dettagli = new ArrayList<DettaglioOrdine>();
	}

	public int getIdOrdine() {
		return idOrdine;
	}

	public void setIdOrdine(int idOrdine) {
		this.idOrdine = idOrdine;
	}

	public int getIdUtente() {
		return idUtente;
	}

	public void setIdUtente(int idUtente) {
		this.idUtente = idUtente;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public double getTotale() {
		return totale;
	}

	public void setTotale(double totale) {
		this.totale = totale;
	}

	public String getMetodoPagamento() {
		return metodoPagamento;
	}

	public void setMetodoPagamento(String metodoPagamento) {
		this.metodoPagamento = metodoPagamento;
	}

	public String getIdTransazione() {
		return idTransazione;
	}

	public void setIdTransazione(String idTransazione) {
		this.idTransazione = idTransazione;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getViaNum() {
		return viaNum;
	}

	public void setViaNum(String viaNum) {
		this.viaNum = viaNum;
	}

	public String getCap() {
		return cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public List<DettaglioOrdine> getDettagli() {
		return dettagli;
	}

	public void setDettagli(List<DettaglioOrdine> dettagli) {
		this.dettagli = dettagli;
	}
	
	public void addDettaglio(DettaglioOrdine dettaglio) {
		this.dettagli.add(dettaglio);
	}

	public String getCitta() {
		return citta;
	}

	public void setCitta(String citta) {
		this.citta = citta;
	}
}
