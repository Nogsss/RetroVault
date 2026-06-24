package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
	
	@Override
	public synchronized List<Ordine> doRetrieveByUser(int id) throws SQLException {
		List<Ordine> ordini = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_ORDINE + 
        		" WHERE id_utente = ? ORDER BY Data DESC";
        
        try(Connection connection = ds.getConnection();
        	PreparedStatement ps = connection.prepareStatement(query)) {
        	
        	ps.setInt(1, id);
        	
        	try(ResultSet rs = ps.executeQuery()) {
        		while(rs.next()) {
        			Ordine ordine = estraiOrdine(rs);
        			caricaDettagliOrdine(connection, ordine);
        			ordini.add(ordine);
        		}
        	}
        }
        return ordini;
	}
	
	@Override
	public synchronized List<Ordine> doRetrieveAll() throws SQLException {
		List<Ordine> ordini = new ArrayList<>();
		String query = "SELECT * FROM " + TABLE_ORDINE + 
				" ORDER BY Data DESC";
		
		try(Connection connection = ds.getConnection();
			PreparedStatement ps = connection.prepareStatement(query)) {
			
			try(ResultSet rs = ps.executeQuery()){
				while(rs.next()) {
					Ordine ordine = estraiOrdine(rs);
					caricaDettagliOrdine(connection, ordine);
					ordini.add(ordine);
				}
			}
		}
        return ordini;
	}
	
	@Override
	public synchronized List<Ordine> doRetrieveByDate(Date inizio, Date fine) throws SQLException {
		List<Ordine> ordini = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_ORDINE + 
        		" WHERE data BETWEEN ? AND ? ORDER BY data DESC";
        
        try (Connection connection = ds.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
             
            ps.setDate(1, inizio);
            ps.setDate(2, fine);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Ordine ordine = estraiOrdine(rs);
                    caricaDettagliOrdine(connection, ordine);
                    ordini.add(ordine);
                }
            }
        }
        return ordini;
	}
	
	
	// METODI DI SUPPORTO PER NON RIPETERE CODICE //
	
	//Metodo per caricare i dettagli di un ordine
	private void caricaDettagliOrdine(Connection connection, Ordine ordine) throws SQLException {
        String query = "SELECT d.*, p.nome FROM " + TABLE_DETTAGLIO + " d " +
                       "JOIN prodotto p ON d.id_prodotto = p.id_prodotto " +
                       "WHERE d.id_ordine = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            
        	ps.setInt(1, ordine.getIdOrdine());
            
        	try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    DettaglioOrdine det = new DettaglioOrdine();
                    det.setIdOrdine(rs.getInt("id_ordine"));
                    det.setIdProdotto(rs.getInt("id_prodotto"));
                    det.setQuantita(rs.getInt("quantita"));
                    det.setPrezzoAcquisto(rs.getDouble("prezzo_acquisto"));
                    det.setNomeProdotto(rs.getString("nome"));
                    
                    ordine.addDettaglio(det);
                }
            }
        }
    }
	
	//Metodo per generare un oggetto ordine dai dati estratti dal resultset
	private Ordine estraiOrdine(ResultSet rs) throws SQLException {
        Ordine ordine = new Ordine();
        ordine.setIdOrdine(rs.getInt("id_ordine"));
        ordine.setIdUtente(rs.getInt("id_utente"));
        ordine.setData(rs.getDate("data"));
        ordine.setTotale(rs.getDouble("totale"));
        ordine.setMetodoPagamento(rs.getString("metodo_pagamento"));
        ordine.setIdTransazione(rs.getString("id_transazione"));
        ordine.setNome(rs.getString("nome"));
        ordine.setCognome(rs.getString("cognome"));
        ordine.setTelefono(rs.getString("telefono"));
        ordine.setViaNum(rs.getString("via_num"));
        ordine.setCitta(rs.getString("citta"));
        ordine.setCap(rs.getString("cap"));
        ordine.setProvincia(rs.getString("provincia"));
        return ordine;
    }
}
