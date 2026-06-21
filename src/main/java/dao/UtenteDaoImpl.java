package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
}
