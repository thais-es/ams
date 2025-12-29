package spa; //R8
import java.util.HashMap;
public class Activite implements IData{
	private int idAct;
	private String type;  
	private String nom;
	private String lieu;
	private int nbMin;
	private String values;
	private HashMap<String, fieldType>map;
	
	public Activite(int idAct, String type, String nom, String lieu, int nbMin) {
		super();
		this.idAct = idAct;
		this.type = type;
		this.nom = nom;
		this.lieu = lieu;
		this.nbMin = nbMin;
	}

	@Override
	public void getStruct() {
		map = new HashMap<>();
		map.put("idAct", fieldType.INT);
		map.put("type", fieldType.VARCHAR);
		map.put("nom", fieldType.VARCHAR);
		map.put("lieu", fieldType.VARCHAR);
		map.put("nbMin", fieldType.INT);
		values = "(" + idAct + "," + type + "," + nom + "," + lieu +","+ nbMin + "')";
	}
	
	@Override
	public String toString() {
		return "Activite [idAct=" + idAct + ", type=" + type + ", nom=" + nom + ", lieu=" + lieu + ", nbMin=" + nbMin
				+"]";
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
	
	
}
