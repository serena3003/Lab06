package it.polito.tdp.meteo.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.meteo.bean.Rilevamento;

public class MeteoDAO {

	public List<Rilevamento> getAllRilevamenti() {

		final String sql = "SELECT Localita, Data, Umidita FROM situazione ORDER BY data ASC";

		List<Rilevamento> rilevamenti = new ArrayList<Rilevamento>();

		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Rilevamento r = new Rilevamento(rs.getString("Localita"), rs.getDate("Data"), rs.getInt("Umidita"));
				rilevamenti.add(r);
			}

			conn.close();
			return rilevamenti;

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public List<Rilevamento> getAllRilevamentiLocalitaMese(int mese, String localita) {

		return null;
	}

	public Double getAvgRilevamentiLocalitaMese(int mese, String localita) {

		return 0.0;
	}
	
	public String[] getUmidita(int mese) {
		
		String[] res = new String[3];
		int i = 0;
				
		final String sql = "SELECT localita, AVG(umidita) " + 
				"FROM situazione " + 
				"WHERE MONTH(DATA) = ? " + 
				"GROUP BY localita ";
		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			st.setInt(1, mese);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				res[i] = rs.getString("localita") + ": " + rs.getFloat("AVG(umidita)");
				//System.out.println(res[i]);
				i++;
			}

			conn.close();
			return res;

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public int getUmiditaGiornaliera(int mese, int giorno, String localita) {
		
		final String sql = "SELECT umidita " + 
				"FROM situazione " + 
				"WHERE MONTH(DATA) = ? AND DAY(DATA)=? AND localita = ?" ;
		
		int res = 0;
		
		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			st.setInt(1, mese);
			st.setInt(2, giorno);
			st.setString(3, localita);


			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				res = rs.getInt("umidita");
			}

			conn.close();
			return res;

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

}
