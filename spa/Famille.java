package spa; //R16
import java.util.HashMap;
public class Famille implements IData {
	private int idFamille;
	private String nom;  
	private String mail;  
	private String adresse;  
	private String values;
	private HashMap<String, fieldType>map;
	
	public Famille(int idFamille, String nom, String mail, String adresse) {
		super();
		this.idFamille = idFamille;
		this.nom = nom;
		this.mail = mail;
		this.adresse = adresse;
		getStruct();
	}

	@Override //pas de void? remplace par hashMap<string,fieldtype>?
	public void getStruct() {
		map = new HashMap<>();
		map.put("idFamille", fieldType.INT);
		map.put("nom", fieldType.VARCHAR);
		map.put("mail", fieldType.VARCHAR);
		map.put("adresse", fieldType.VARCHAR);

		values = "(" + idFamille + "," + nom  + "," + mail + "," + adresse +"')";
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
			if (tableStruct.get(num) != map.get(num)) {
				System.out.println("Type incompatible pour : " + num); 
			return false; 
			}
		}
		return true;
	}

	@Override
	public String toString() {
		return "Famille [idFamille=" + idFamille + ", nom=" + nom + ", mail=" + mail + ", adresse=" + adresse
				+ "]";
	}

	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return idFamille;
	}

	
	
	
}
