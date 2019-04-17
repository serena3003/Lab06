package it.polito.tdp.meteo;

import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.meteo.bean.SimpleCity;
import it.polito.tdp.meteo.db.MeteoDAO;

public class Model {

	private final static int COST = 100;
	private final static int NUMERO_GIORNI_CITTA_CONSECUTIVI_MIN = 3;
	private final static int NUMERO_GIORNI_CITTA_MAX = 6;
	private final static int NUMERO_GIORNI_TOTALI = 15;
	
	private MeteoDAO md;
	private List<SimpleCity> soluzione;

	public Model() {
		 this.md = new MeteoDAO();
	}

	public String[] getUmiditaMedia(int mese) {
		String[] res = md.getUmidita(mese);
		return res;
	}

	public String trovaSequenza(int mese) {
		
		soluzione = new ArrayList<SimpleCity>();
		//SimpleCity parziale = new SimpleCity();
		String parziale="";
		cercaSequenza(parziale,0);

		return "TODO!";
	}

	private Double punteggioSoluzione(List<SimpleCity> soluzioneCandidata) {

		double score = 0.0;
		return score;
	}

	private boolean controllaParziale(List<SimpleCity> parziale) {
		
		int t = 0;
		int g = 0;
		int m = 0;
		
		for(SimpleCity s : parziale) {
			if (s.equals("Milano")) {
				m++;
			} else if (s.equals("Genova")) {
				g++;
			} else if(s.equals("Torino")) {
				t++;
			}
		}
		
		if((t==0)||(g==0)||(m==0)) {
			return false;
		} else if ((t>6)||(m>6)||(g>6)) {
			return false;
		}
		return true;
	}
	
	private void cercaSequenza(String seq, int l){
		
		if(l==5) {
			if(controllaParziale(parziale)) {
				//aggiungo alla sequenza finale
			} 
			//return
		}
		for(String s : ["Milano", "Torino", "Genova"]) {
			
		}
		
	}
	

}
