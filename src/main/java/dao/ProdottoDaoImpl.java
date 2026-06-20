package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import model.Prodotto;

public class ProdottoDaoImpl implements ProdottoDao{

	private static final String TABLE_NAME = "prodotto";
	private DataSource ds = null;

	public ProdottoDaoImpl(DataSource ds) {
		this.ds = ds;
	}
	
	@Override
	public synchronized void doSave(Prodotto prodotto) throws SQLException {
		String query = "INSERT INTO " + TABLE_NAME + 
				" (nome, descrizione, categoria, prezzo, quantita_disp, attivo, img_path) VALUES (?, ?, ?, ?, ?, ?, ?)";
		
		 try (Connection connection = ds.getConnection();
	             PreparedStatement ps = connection.prepareStatement(query)) {
	             
	            ps.setString(1, prodotto.getNome());
	            ps.setString(2, prodotto.getDescrizione());
	            ps.setString(3, prodotto.getCategoria());
	            ps.setDouble(4, prodotto.getPrezzo());
	            ps.setInt(5, prodotto.getQuantitàDisponibile());
	            ps.setBoolean(6, prodotto.isAttivo()); 
	            ps.setString(7, prodotto.getImgPath());
	            
	            ps.executeUpdate();
		 }
	}
	
	 @Override
	 public synchronized boolean doUpdate(Prodotto prodotto) throws SQLException {
		 String query = "UPDATE " + TABLE_NAME + 
				 " SET nome = ?, descrizione = ?, categoria = ?, prezzo = ?, quantita_disp = ?, attivo = ?, img_path = ? WHERE ID_Prodotto = ?";
	        
	        try (Connection connection = ds.getConnection();
	             PreparedStatement ps = connection.prepareStatement(query)) {
	             
	            ps.setString(1, prodotto.getNome());
	            ps.setString(2, prodotto.getDescrizione());
	            ps.setString(3, prodotto.getCategoria());
	            ps.setDouble(4, prodotto.getPrezzo());
	            ps.setInt(5, prodotto.getQuantitàDisponibile());
	            ps.setBoolean(6, prodotto.isAttivo()); 
	            ps.setString(7, prodotto.getImgPath());
	            ps.setInt(8, prodotto.getId());
	            
	            int righeAggiornate = ps.executeUpdate();
	            return righeAggiornate != 0; 
	        }
	    }
	 
	 @Override
	 public synchronized boolean doDelete(int id) throws SQLException {
		 String query = "UPDATE " + TABLE_NAME + 
				 " SET attivo = false WHERE id_prodotto = ?";
	        
	        try (Connection connection = ds.getConnection();
	             PreparedStatement ps = connection.prepareStatement(query)) {
	             
	            ps.setInt(1, id);
	            
	            int righeAggiornate = ps.executeUpdate();
	            return righeAggiornate != 0; 
	        }
	    }
	 
	 @Override
	 public synchronized Prodotto doRetrieveByKey(int id) throws SQLException {
		 Prodotto prod = null;
	     String query = "SELECT * FROM " + TABLE_NAME + 
	    		 " WHERE id_prodotto = ?";

	        try (Connection connection = ds.getConnection();
	             PreparedStatement ps = connection.prepareStatement(query)) {
	             
	            ps.setInt(1, id);

	            try (ResultSet rs = ps.executeQuery()) {
	                if (rs.next()) {
	                    prod = new Prodotto();
	                    prod.setId(rs.getInt("id_prodotto"));
	                    prod.setNome(rs.getString("nome"));
	                    prod.setDescrizione(rs.getString("descrizione"));
	                    prod.setCategoria(rs.getString("categoria"));
	                    prod.setPrezzo(rs.getDouble("prezzo"));
	                    prod.setQuantitàDisponibile(rs.getInt("quantita_disp"));
	                    prod.setAttivo(rs.getBoolean("attivo"));
	                    prod.setImgPath(rs.getString("img_path"));
	                }
	            }
	        }
	        return prod;
	    }
	 
	 @Override
	 public List<Prodotto> doRetrieveAll() throws SQLException {
		 List<Prodotto> prodotti = new ArrayList<>();
		 String query = "SELECT * FROM " + TABLE_NAME + 
				 " WHERE attivo = true";

	        try (Connection connection = ds.getConnection();
	             PreparedStatement ps = connection.prepareStatement(query)) {
	        	
	        	try(ResultSet rs = ps.executeQuery()){
	        		while (rs.next()) {
	        			Prodotto elem = new Prodotto();
	        			elem.setId(rs.getInt("id_prodotto"));
	        			elem.setNome(rs.getString("nome"));
	        			elem.setDescrizione(rs.getString("descrizione"));
	        			elem.setCategoria(rs.getString("categoria"));
	        			elem.setPrezzo(rs.getDouble("prezzo"));
	        			elem.setQuantitàDisponibile(rs.getInt("quantita_disp"));
	        			elem.setAttivo(rs.getBoolean("attivo"));
	        			elem.setImgPath(rs.getString("img_path"));
	                
	        			prodotti.add(elem);
	        		}
	        	}
	        }
	        return prodotti;
	    }
}
