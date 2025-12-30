
package spa; // R10

import java.util.HashMap;
import java.util.Map;

public class Creneau implements IData{
	private int idCreneau;
	private String heureD; //voir type heure ?
	private String heureF;
	private String values;
	private HashMap<String, fieldType>map;
	
	public Creneau(int idCreneau, String heureD, String heureF) {
		super();
		this.idCreneau = idCreneau;
		this.heureD = heureD;
		this.heureF = heureF;
	}

	@Override
	public void getStruct() {
		map = new HashMap<>();
		map.put("idCreneau", fieldType.INT);
		map.put("heureD", fieldType.VARCHAR);
		map.put("heureF", fieldType.VARCHAR);

		values = "(" + idCreneau + "," + heureD + heureF + "')";
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
		return "Creneau [idCreneau=" + idCreneau + ", heureD=" + heureD + ", heureF=" + heureF + "]";
	}

	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return idCreneau;
	}
	
	@Override
	public Map<String, Integer> getDeuxID() {
		// TODO Auto-generated method stub
		return null;  // null ou exception à voir ce qu'on fait 
	}
	

}
