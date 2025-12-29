package spa;  //R5
import java.util.HashMap;
public class GrpBenevole implements IData {
	private int idGrp;
	private int idBenevole;  
	private String values;
	private HashMap<String, fieldType>map;
	
	public GrpBenevole(int idGrp, int idBenevole) {
		super();
		this.idGrp = idGrp;
		this.idBenevole = idBenevole;
	}

	@Override
	public void getStruct() {
		map = new HashMap<>();
		map.put("idGrp", fieldType.INT);
		map.put("idBenevole", fieldType.INT);
		values = "(" + idGrp + "," + idBenevole + "')";
	}

	@Override
	public String toString() {
		return "GrpBenevole [idGrp=" + idGrp + ", idBenevole=" + idBenevole + "]";
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
	public String getValues() {
		return values;
	}

}
