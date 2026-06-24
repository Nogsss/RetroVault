package dao;

import java.sql.SQLException;
import java.util.List;

import model.Prodotto;

public interface ProdottoDao {

	public void doSave(Prodotto prodotto) throws SQLException;
	public boolean doUpdate(Prodotto prodotto) throws SQLException;
	public boolean doDelete(int id) throws SQLException;
	public Prodotto doRetrieveByKey(int id) throws SQLException;
	public List<Prodotto> doRetrieveAll() throws SQLException;
	public List<Prodotto> doRetrieveAllAdmin() throws SQLException;
}
