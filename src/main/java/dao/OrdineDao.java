package dao;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import model.Ordine;

public interface OrdineDao {

	void doSave(Ordine ordine) throws SQLException;
	List<Ordine> doRetrieveByUser(int id) throws SQLException;
	List<Ordine> doRetrieveAll() throws SQLException;
	List<Ordine> doRetrieveByDate(Date inizio, Date fine) throws SQLException;
}
