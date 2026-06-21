package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import model.Utente;

public class UtenteDaoImpl implements UtenteDao{

		private static final String TABLE_NAME = "utente";
		private DataSource ds = null;
		
		public UtenteDaoImpl(DataSource ds) {
			this.ds = ds;
		}
		
		@Override
		public synchronized void doSave(Utente utente) throws SQLException{
			String query = "INSERT INTO " + TABLE_NAME + 
					" (nome, cognome, email, password, admin) VALUES (?, ?, ?, ?, ?)";
	        
	        try (Connection connection = ds.getConnection();
	             PreparedStatement ps = connection.prepareStatement(query)) {
	             
	            ps.setString(1, utente.getNome());
	            ps.setString(2, utente.getCognome());
	            ps.setString(3, utente.getEmail());
	            ps.setString(4, utente.getPassword()); 
	            ps.setBoolean(5, utente.isAdmin()); 
	            
	            ps.executeUpdate();
	        }
		}
		
		@Override
		public synchronized Utente doRetrieveByEmailPassword(String email, String password) throws SQLException{
			Utente utente = null;
			String query = "SELECT * FROM " + TABLE_NAME + 
					" WHERE email = ? AND password = ?";
	        
	        try (Connection connection = ds.getConnection();
	             PreparedStatement ps = connection.prepareStatement(query)) {
	             
	            ps.setString(1, email);
	            ps.setString(2, password);
	            
	            try (ResultSet rs = ps.executeQuery()) {
	                if (rs.next()) {
	                    utente = new Utente();
	                    utente.setId(rs.getInt("id_utente"));
	                    utente.setNome(rs.getString("nome"));
	                    utente.setCognome(rs.getString("cognome"));
	                    utente.setEmail(rs.getString("email"));
	                    utente.setPassword(rs.getString("password"));
	                    utente.setDataRegistrazione(rs.getDate("data_registrazione"));
	                    utente.setAdmin(rs.getBoolean("admin"));
	                }
	            }
	        }
	        return utente;
		}
		
		@Override
		public synchronized boolean doUpdate(Utente utente) throws SQLException {
			String query = "UPDATE " + TABLE_NAME + 
					" SET nome = ?, cognome = ?, email = ?, password = ? WHERE id_utente = ?";
	        
	        try (Connection connection = ds.getConnection();
	             PreparedStatement ps = connection.prepareStatement(query)) {
	             
	            ps.setString(1, utente.getNome());
	            ps.setString(2, utente.getCognome());
	            ps.setString(3, utente.getEmail());
	            ps.setString(4, utente.getPassword());
	            ps.setInt(5, utente.getId());
	            
	            return ps.executeUpdate() != 0;
	        }
		}
}
