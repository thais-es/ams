package spa; //R19
import java.util.HashMap;
public class SituationAnimal implements IData {
	private int idSituation;  
	private int idAnimal;
	private String values;
	private HashMap<String, fieldType>map;
	
	public SituationAnimal(int idSituation, int idAnimal) {
		super();
		this.idSituation = idSituation;
		this.idAnimal = idAnimal;
		map = new HashMap<>();
	}

	@Override
	public void getStruct() {
		map.put("idSituation", fieldType.INT);
		map.put("idAnimal", fieldType.INT);
		values = "(" + idSituation + "," + idAnimal +")";
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
		return "SituationAnimal [idSituation=" + idSituation + ", idAnimal=" + idAnimal + "]";
	}
	
	
}
