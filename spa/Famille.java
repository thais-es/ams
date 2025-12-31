package spa; //R16
import java.util.HashMap;
import java.util.Map;
public class Famille implements IData {
	private int idFamille;
	private String nom;  
	private String tel;
	private String mail;  
	private String adresse;  
	private String values;
	private HashMap<String, fieldType>map;
	
	public Famille(int idFamille, String nom,String tel ,String mail, String adresse) {
		super();
		this.idFamille = idFamille;
		this.nom = nom;
		this.tel=tel;
		this.mail = mail;
		this.adresse = adresse;
		map = new HashMap<>();
		getStruct();
	}

	@Override //pas de void? remplace par hashMap<string,fieldtype>?
	public void getStruct() {
		map.put("idfamille", fieldType.INT);
		map.put("nom", fieldType.VARCHAR);
		map.put("tel", fieldType.VARCHAR);
		map.put("mail", fieldType.VARCHAR);
		map.put("adresse", fieldType.VARCHAR);

		values = "(" + idFamille + "," +tel +"," + nom  + "," + mail + "," + adresse +"')";
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
			if (tableStruct.get(num) != map.get(num)) {
				System.out.println("Type incompatible pour : " + num); 
			return false; 
			}
		}
		return true;
	}

	@Override
	public String toString() {
		return "Famille [idFamille=" + idFamille + ", nom=" + nom +", tel=" + tel + ", mail=" + mail + ", adresse=" + adresse
				+ "]";
	}

	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return idFamille;
	}

	@Override
	public Map<String, Integer> getDeuxID() {
		// TODO Auto-generated method stub
		return null;  // null ou exception à voir ce qu'on fait 
	}
	
	
}