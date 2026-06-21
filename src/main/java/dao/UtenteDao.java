package dao;

import java.sql.SQLException;

import model.Utente;

public interface UtenteDao {

	void doSave(Utente utente) throws SQLException;
	Utente doRetrieveByEmailPassword(String email, String password) throws SQLException;
	boolean doUpdate(Utente utente) throws SQLException;
	boolean doDelete(int id) throws SQLException;
}
