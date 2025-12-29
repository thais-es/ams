package spa; // R12

import java.util.HashMap;

public class Groupe implements IData {
	private int idGrp;
	private int idAct;  
	private int idCreneau;  
	private String values;
	private HashMap<String, fieldType>map;
	
	public Groupe(int idGrp, int idAct, int idCreneau) {
		super();
		this.idGrp = idGrp;
		this.idAct = idAct;
		this.idCreneau = idCreneau;
	}

	@Override
	public void getStruct() {
		map.put("idGrp", fieldType.INT);
		map.put("idAct", fieldType.INT);
		map.put("idCreneau", fieldType.INT);
		values = "(" + idGrp + "," + idAct  + idCreneau  +"')";
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
		return "Groupe [idGrp=" + idGrp + ", idAct=" + idAct + ", idCreneau=" + idCreneau + "]";
	}

	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return idGrp;
	}
	
	
}
