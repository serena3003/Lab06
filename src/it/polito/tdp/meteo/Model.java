package it.polito.tdp.meteo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import it.polito.tdp.meteo.bean.SimpleCity;
import it.polito.tdp.meteo.db.MeteoDAO;

public class Model {

	private final static int COST = 100;
	private final static int NUMERO_GIORNI_CITTA_CONSECUTIVI_MIN = 3;
	private final static int NUMERO_GIORNI_CITTA_MAX = 6;
	private final static int NUMERO_GIORNI_TOTALI = 15;

	private String[] citta = { "Genova", "Milano", "Torino" };

	private MeteoDAO md;
	private Map<String, Integer> soluzione;
	private String solfinale;

	public Model() {
		this.md = new MeteoDAO();
	}

	public String[] getUmiditaMedia(int mese) {
		String[] res = md.getUmidita(mese);
		return res;
	}

	public String trovaSequenza(int mese) {

		soluzione = new TreeMap<>();
		String parziale[] = new String[15];

		cerca(parziale, 0, mese);

		/*
		 * Entry<List<SimpleCity>, Integer> min = null; for (Entry<List<SimpleCity>,
		 * Integer> entry : soluzione.entrySet()) { if (min == null || min.getValue() >
		 * entry.getValue()) { min = entry; } }
		 * 
		 * System.out.println(min.getKey().toString());
		 * 
		 * return min.getKey().toString();
		 */

		// int min = Collections.min(soluzione.values());

		// return soluzione.;

		int min = 1000000000;
		
		for (String k : soluzione.keySet()) {
			if (soluzione.get(k) < min) {
				min = soluzione.get(k);
				solfinale = k;
			}
		}

		return solfinale = solfinale + min;
	}

	//private int punteggioSoluzione(List<SimpleCity> soluzioneCandidata) {
	private int punteggioSoluzione(String[] soluzioneCandidata, int l, int mese) {
		
		int res = 0;

		for (int i = 0; i<soluzioneCandidata.length; i++) {
			if(soluzioneCandidata[i].equals(soluzioneCandidata[i-1])) {
				res = res + 100 + md.getUmiditaGiornaliera(mese, l+1, soluzioneCandidata[i]);
			}
			else {
				res = res + md.getUmiditaGiornaliera(mese, l+1, soluzioneCandidata[i]);
			}
		}

		return res;
	}

	private boolean controllaParziale(String[] parziale) {

		int t = 0;
		int g = 0;
		int m = 0;

		for (String s : parziale) {
			if (s.equals("Milano")) {
				m++;
			} else if (s.equals("Genova")) {
				g++;
			} else if (s.equals("Torino")) {
				t++;
			}
		}

		if ((t == 0) || (g == 0) || (m == 0)) {
			return false;
		} else if ((t > NUMERO_GIORNI_CITTA_MAX) || (m > NUMERO_GIORNI_CITTA_MAX) || (g > NUMERO_GIORNI_CITTA_MAX)) {
			return false;
		}
		return true;
	}

	//private void cerca(List<SimpleCity> parziale, int l, int mese) {
	private void cerca(String[] parziale, int l, int mese) {

		if (l == 5) {
			if (controllaParziale(parziale)) {
				int costo = punteggioSoluzione(parziale, l, mese);
				soluzione.put(parziale, costo);
				System.out.println(soluzione.keySet().toString());
			}
			return;
		}
		for (String c : citta) {

			/*int g = l;
			SimpleCity sc1 = new SimpleCity(c, md.getUmiditaGiornaliera(mese, g, c));
			parziale.add(sc1);
			g++;
			SimpleCity sc2 = new SimpleCity(c, md.getUmiditaGiornaliera(mese, g, c));
			parziale.add(sc2);
			g++;
			SimpleCity sc3 = new SimpleCity(c, md.getUmiditaGiornaliera(mese, g, c));
			parziale.add(sc3);*/

			cerca(parziale, l + 1, mese);

			parziale.remove(sc1);
			parziale.remove(sc2);
			parziale.remove(sc3);
		}

	}

}
