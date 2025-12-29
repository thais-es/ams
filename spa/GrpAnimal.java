package spa; //R13

import java.util.HashMap;

public class GrpAnimal implements IData {
	private int idGrp;
	private int idAnimal;  
	private String values;
	private HashMap<String, fieldType>map;
	
	public GrpAnimal(int idGrp, int idAnimal) {
		super();
		this.idGrp = idGrp;
		this.idAnimal = idAnimal;
	}

	@Override
	public void getStruct() {
		map.put("idGrp", fieldType.INT);
		map.put("idAnimal", fieldType.INT);
		values = "(" + idGrp + "," + idAnimal  + "')";
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
		return "GrpAnimal [idGrp=" + idGrp + ", idAnimal=" + idAnimal + "]";
	}
	
	
	
}
