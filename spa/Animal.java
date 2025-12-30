package spa;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
public class Animal implements IData {
	private int idAnimal;  
	private String nom;
	private Date arrive;  
	private String type;  
	private String race;  
	private Date naissance;  
	private String statut;  
	private String values;
	private HashMap<String, fieldType>map;
	
	public Animal(int idAnimal, String nom, Date arrive, String type, String race, Date naissance, String statut) {
		super();
		this.idAnimal = idAnimal;
		this.nom = nom;
		this.arrive = arrive;
		this.type = type;
		this.race = race;
		this.naissance = naissance;
		this.statut = statut;
	}

	@Override
	public void getStruct() {
		map.put("idAnimal", fieldType.INT);
		map.put("nom", fieldType.VARCHAR);
		map.put("arrive", fieldType.DATE);
		map.put("type", fieldType.VARCHAR);
		map.put("race", fieldType.VARCHAR);
		map.put("naissance", fieldType.DATE);		
		map.put("statut", fieldType.VARCHAR);
		values = "(" + idAnimal + "," + nom + ","+ arrive + ","+ type +","+ race + ","+naissance+";" +statut+"')";
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
		return "Animal [idAnimal=" + idAnimal + ", nom=" + nom + ", arrive=" + arrive + ", type=" + type + ", race="
				+ race + ", naissance=" + naissance + ", statut=" + statut + "]";
	}

	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return idAnimal;
	}

	@Override
	public Map<String, Integer> getDeuxID() {
		// TODO Auto-generated method stub
		return null;  // null ou exception à voir ce qu'on fait 
	}
	
	
}