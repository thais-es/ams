package spa; //R4

import java.util.HashMap;

public class Benevole implements IData {
	private int idBenevole; 
	private String nom;
	private String prenom; 
	private String numTel;
	private String mail;		//srting tt le temps ??
	private String adresse;
	private String values;
	private HashMap<String, fieldType>map;
	
	
	
	public Benevole(int idBenevole, String nom, String prenom, String numTel, String mail, String adresse) {
		super();
		this.idBenevole = idBenevole;
		this.nom = nom;
		this.prenom = prenom;
		this.numTel = numTel;
		this.mail = mail;
		this.adresse = adresse;
	}

	@Override
	public void getStruct() {
		map = new HashMap<>();
		map.put("idBenevole", fieldType.INT);
		map.put("nom", fieldType.VARCHAR);
		map.put("prenom", fieldType.VARCHAR);		//text tt le temps  ??
		map.put("numTel", fieldType.VARCHAR);		
		map.put("mail", fieldType.VARCHAR);
		map.put("adresse", fieldType.VARCHAR);
		values = "(" + idBenevole + "," + nom + ",'" + prenom + "','" + numTel + "','"+  mail + "','" +  adresse + "')";
	}
	
	@Override
	public String toString() {
		return "Benevole [idBenevole=" + idBenevole + ", nom=" + nom + ", prenom=" + prenom + ", numTel=" + numTel
				+ ", mail=" + mail + ", adresse=" + adresse + "]";
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

}
