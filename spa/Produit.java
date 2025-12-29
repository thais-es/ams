package spa;
import java.util.HashMap;
public class Produit implements IData{
	private int id; 
	private int lot;
	private String nom;
	private String descr;
	private String cat;
	private double prix;
	private String values;
	private HashMap<String, fieldType>map;

	
	
	public Produit(int id, int lot, String nom, String descr, String cat, double prix, String values) {
		super();
		this.id = id;
		this.lot = lot;
		this.nom = nom;
		this.descr = descr;
		this.cat = cat;
		this.prix = prix;
		}
	
	@Override
	public String toString() {
		return "Produit [id=" + id + ", lot=" + lot + ", nom=" + nom + ", descr=" + descr + ", cat=" + cat + ", prix="
				+ prix + "]";
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getLot() {
		return lot;
	}

	public void setLot(int lot) {
		this.lot = lot;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getCat() {
		return cat;
	}

	public void setCat(String cat) {
		this.cat = cat;
	}

	public double getPrix() {
		return prix;
	}

	public void setPrix(double prix) {
		this.prix = prix;
	}
	
	
	@Override
	public void getStruct() {
		map = new HashMap<>();
		map.put("id", fieldType.INT);
		map.put("lot", fieldType.INT);
		map.put("nom", fieldType.VARCHAR);
		map.put("descr", fieldType.TEXT);
		map.put("cat", fieldType.VARCHAR);
		map.put("prix", fieldType.FLOAT8);
		
		values = "(" + id + "," + lot + ",'" + nom + "','" + descr + "','" + cat + "'," + prix + ")";
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
