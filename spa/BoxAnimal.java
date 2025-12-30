package spa; //  R1
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
public class BoxAnimal implements IData {
	private int idBox; 
	private int idAnimal;
	private Date dateD; // a voir si on peut utiliser le type date 
	private Date dateF;
	private String values;
	private HashMap<String, fieldType>map;
	
	public BoxAnimal(int idBox, int idAnimal, Date dateD, Date dateF) {
		this.idBox = idBox;
		this.idAnimal = idAnimal;
		this.dateD = dateD;
		this.dateF = dateF;
	}

	@Override
	public String toString() {
		return "Box [idBox=" + idBox + ", idAnimal=" + idAnimal + ", dateD=" + dateD + ", dateF=" + dateF+"]";
	}

	public int getidBox() {
		return idBox;
	}

	public void setidBox(int idBox) {
		this.idBox = idBox;
	}

	public int getidAnimal() {
		return idAnimal;
	}

	public void setidAnimal(int idAnimal) {
		this.idAnimal = idAnimal;
	}

	public Date getdateD() {
		return dateD;
	}

	public void setdateD(Date dateD) {
		this.dateD = dateD;
	}

	public Date getdateF() {
		return dateF;
	}

	public void setdateF(Date dateF) {
		this.dateF = dateF;
	}

	public HashMap<String, fieldType> getMap() {
		return map;
	}

	public void setMap(HashMap<String, fieldType> map) {
		this.map = map;
	}
	
	public String getValues() {
		return values;
	}
	
	@Override
	public boolean check(HashMap<String, fieldType> tableStruct) {
		for (String num:map.keySet()) {
			if(!tableStruct.containsKey(num)) {
				System.out.println("il manque : "+num);
				return false;
			}
		}
		return true;
	}
	
	@Override
	public void getStruct() {
		map = new HashMap<>();
		map.put("idBox", fieldType.INT);
		map.put("idAnimal", fieldType.INT);
		map.put("dateD", fieldType.DATE);
		map.put("dateF", fieldType.DATE);		
		values = "(" + idBox + "," + idAnimal + ",'" + dateD + "','" + dateF + "')";
	}

	@Override
	public int getId() {
		return -1; // Ou alors exception, a voir ce qu'on fait au final
	}

	@Override
	public Map<String, Integer> getDeuxID() {
		Map<String, Integer> idBoxAni =new HashMap<>();
		idBoxAni.put("idBox", idBox);
		idBoxAni.put("idAnimal", idAnimal);
		return idBoxAni;
	}
	
	
}
