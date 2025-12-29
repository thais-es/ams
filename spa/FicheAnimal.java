package spa;

import java.sql.Date;
import java.util.HashMap;

public class FicheAnimal implements IData {
	private int idFiche;  
	private int idAnimal;  
	private Date dSoin;  
	private String ordonnance;  
	private String commentaire;  
	private Date prochainRdv;  
	private String maladie;  
	private String values;
	private HashMap<String, fieldType>map;
	
	public FicheAnimal(int idFiche, int idAnimal, Date soin, String ordonnance, String commentaire, Date prochainRdv,
			String maladie) {
		super();
		this.idFiche = idFiche;
		this.idAnimal = idAnimal;
		this.dSoin = soin;
		this.ordonnance = ordonnance;
		this.commentaire = commentaire;
		this.prochainRdv = prochainRdv;
		this.maladie = maladie;
	}

	@Override
	public void getStruct() {
		map.put("idFiche", fieldType.INT);
		map.put("idAnimal", fieldType.INT);
		map.put("dSoin", fieldType.DATE);
		map.put("ordonnance", fieldType.TEXT);
		map.put("commentaire", fieldType.TEXT);
		map.put("prochainRdv", fieldType.DATE);		
		map.put("maladie", fieldType.VARCHAR);
		values = "(" + idFiche + "," + idAnimal + ","+ dSoin + ","+ ordonnance +","+ commentaire + ","+prochainRdv+";" +maladie+"')";
	}

	@Override
	public String getValues() {
		return values;
	}

	@Override
	public HashMap<String, fieldType> getMap() {
		return map;
	}

	@Override
	public boolean check(HashMap<String, fieldType> tableStruct) {	//il faut mettre la meme a chaque fois ??
		for (String num:map.keySet()) {
			if(!tableStruct.containsKey(num)) {
				System.out.println("il manque : "+num);
				return false;
			}
		}
		return true;
	}

	@Override
	public String toString() {
		return "FicheAnimal [idFiche=" + idFiche + ", idAnimal=" + idAnimal + ", dSoin=" + dSoin + ", ordonnance="
				+ ordonnance + ", commentaire=" + commentaire + ", prochainRdv=" + prochainRdv + ", maladie=" + maladie
				+ "]";
	}

	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return idFiche;
	}
	
	
}
