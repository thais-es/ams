package spa; //R18
import java.sql.Date;
import java.util.HashMap;
public class Situation implements IData {
	private int idSituation;
	private int idFamille;  
	private String type;  
	private String commentaire;  
	private Date dateD; 
	private Date dateF;
	private String values;
	private HashMap<String, fieldType>map;
	
	public Situation(int idFamille, String type, String commentaire, Date dateD, Date dateF) {
		super();
		this.idFamille = idFamille;
		this.type = type;
		this.commentaire = commentaire;
		this.dateD = dateD;
		this.dateF = dateF;
	}

	@Override
	public void getStruct() {
		map.put("idFamille", fieldType.INT);
		map.put("type", fieldType.VARCHAR);
		map.put("commentaire", fieldType.TEXT);
		map.put("dateD", fieldType.DATE);
		map.put("dateF", fieldType.DATE);
		values = "(" + idSituation + ", " + idFamille + ", '" + type + "', '" + commentaire + "', '" + dateD + "', '" + dateF + "')";
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
		return "Situation [idFamille=" + idFamille + ", type=" + type + ", commentaire=" + commentaire + ", dateD="
				+ dateD + ", dateF=" + dateF + "]";
	}

	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return idSituation;
	}
	
}
