package spa; //R21
import java.util.HashMap;
import java.util.Map;

public class Box implements IData {
	private int idBox;  
	private String espece;
	private int capMax;  
	private String values;
	private HashMap<String, fieldType>map;
	
	public Box(int idBox, String espece, int capMax) {
		super();
		this.idBox = idBox;
		this.espece = espece;
		this.capMax = capMax;
		map = new HashMap<>();
		getStruct();
		}

	@Override
	public void getStruct() {
		map.put("idBox", fieldType.INT);
		map.put("espece", fieldType.VARCHAR);
		map.put("capMax", fieldType.INT);
		values = "(" + idBox + "," + espece + ","+ capMax + "')";
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
	public String toString() {
		return "Box [idBox=" + idBox + ", espece=" + espece + ", capMax=" + capMax + "]";
	}

	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return idBox;
	}
	@Override
	public Map<String, Integer> getDeuxID() {
		// TODO Auto-generated method stub
		return null;  // null ou exception à voir ce qu'on fait 
	}
}