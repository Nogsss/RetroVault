package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.sql.DataSource;

import model.DettaglioOrdine;
import model.Ordine;

public class OrdineDaoImpl implements OrdineDao{

	private static final String TABLE_ORDINE = "ordine";
	private static final String TABLE_DETTAGLIO = "dettaglio_ordine";
	private DataSource ds = null;
	
	public OrdineDaoImpl(DataSource ds) {
		this.ds = ds;
	}
	
	@Override
	public synchronized void doSave(Ordine ordine) throws SQLException {
        String queryOrdine = "INSERT INTO " + TABLE_ORDINE + 
        		" (id_utente, totale, metodo_pagamento, id_transazione, nome, cognome, telefono, via_num, citta, cap, provincia) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        String queryDettaglio = "INSERT INTO " + TABLE_DETTAGLIO + 
        		" (id_ordine, id_prodotto, quantita, prezzo_acquisto) VALUES (?, ?, ?, ?)";

        try(Connection connection = ds.getConnection()) {
        	
        	/*Devo aggiornare 2 tabelle contemporaneamente quindi bisogna fare una transazione sul db 
        	 * ed evitare che venga committata solo una parte dell'operazione */
        	connection.setAutoCommit(false);
        	
        	try {
        		int idOrdine = 0;
        		
        		//Faccio la prima operazione sulla tabella ordine e mi prendo la PK generata per settarla come FK in dettaglio_ordine
        		try(PreparedStatement psOrdine = connection.prepareStatement(queryOrdine, Statement.RETURN_GENERATED_KEYS)){
        			psOrdine.setInt(1, ordine.getIdUtente());
                    psOrdine.setDouble(2, ordine.getTotale());
                    psOrdine.setString(3, ordine.getMetodoPagamento());
                    psOrdine.setString(4, ordine.getIdTransazione());
                    psOrdine.setString(5, ordine.getNome());
                    psOrdine.setString(6, ordine.getCognome());
                    psOrdine.setString(7, ordine.getTelefono());
                    psOrdine.setString(8, ordine.getViaNum());
                    psOrdine.setString(9, ordine.getCitta());
                    psOrdine.setString(10, ordine.getCap());
                    psOrdine.setString(11, ordine.getProvincia());
                    
                    psOrdine.executeUpdate();
                    
                    try(ResultSet key = psOrdine.getGeneratedKeys()) {
                    	if(key.next()) {
                    		idOrdine = key.getInt(1);
                    		ordine.setIdOrdine(idOrdine);
                    	}
                    	else throw new SQLException("Creazione ordine fallita");
                    }
        		}
        		
        		//Adesso opero sulla seconda tabella dettaglio_ordine
        		try(PreparedStatement psDettaglio = connection.prepareStatement(queryDettaglio)){
        			for(DettaglioOrdine det : ordine.getDettagli()) { //Aggiungo i dettagli dell'ordine uno alla volta con un for
        				psDettaglio.setInt(1, idOrdine);
        				psDettaglio.setInt(2, det.getIdProdotto());
        				psDettaglio.setInt(3, det.getQuantita());
        				psDettaglio.setDouble(4, det.getPrezzoAcquisto());
        				
        				psDettaglio.executeUpdate();
        			}
        		}
        		connection.commit(); //A questo punto le operazioni sono tutte terminate correttamente e posso committare
        	}
        	catch (SQLException e) {
    			connection.rollback();	//Se ci sono stati errori faccio il rollback
    			throw e;
    		}
        	finally {
        		connection.setAutoCommit(true); //Reimposto l'autocommit a true
        	}
		} 
	}
}
