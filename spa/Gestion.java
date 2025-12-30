package spa;
import java.sql.*;
import java.util.HashMap;

public class Gestion {
	private Connection laConnection;

	public Gestion(Connection laConnection) {
		super();
		this.laConnection = laConnection;
	}
	
	public HashMap<String,fieldType> structTable(String table, boolean display) throws SQLException{
		HashMap<String,fieldType>map=new HashMap<>();
		DatabaseMetaData base =laConnection.getMetaData();
		ResultSet col =base.getColumns(null, null, table, null);
		while(col.next()) {
			String cnom=col.getString("COLUMN_NAME");
			String ctype=col.getString("TYPE_NAME");
			fieldType type=quelTypeField(ctype);
			map.put(cnom,type);
			
			if(display) {
				System.out.println(cnom + ":" +type);
			}
		}
		return map;
	}	
	
	private fieldType quelTypeField(String typeSql) {
		switch(typeSql.toUpperCase()) {
		case "NUMERIC": return fieldType.NUMERIC;
		case "VARCHAR": return fieldType.VARCHAR;
		case "FLOAT8": return fieldType.FLOAT8;
		case "INT": return fieldType.INT;
		case "TEXT": return fieldType.TEXT;
		default: return fieldType.TEXT;

		}
		
	}
	
	public void displayTable(String table) throws SQLException{
		String requete ="SELECT * FROM " +table;
		PreparedStatement base =laConnection.prepareStatement(requete);
		ResultSet elt =base.executeQuery();
		ResultSetMetaData donnee = elt.getMetaData();
		int nbcol=donnee.getColumnCount();
		while(elt.next()) {
			for(int i=1; i<=nbcol;i++) {
				System.out.print(donnee.getColumnName(i)+" :"+elt.getString(i));
			}
			System.out.println();
		}
	}
	
	public void execute(String query) throws SQLException{
		Statement base = laConnection.createStatement();
	    try {
	        base.execute(query);
	    } finally {
	        base.close();
	    }
	}
	
	public void insert(IData data, String table)throws SQLException{
		HashMap<String,fieldType> map=structTable(table, false);
		data.getStruct();
		if(!data.check(map)) {
			System.out.println("Erreur structure differente !");
			return;
		}
		
		Statement base=laConnection.createStatement();
		String st = "SELECT * FROM " + table + " WHERE id = " + data.getId();
		ResultSet elt = base.executeQuery(st);
		if(elt.next()){
			/*double vieuxPrix=elt.getDouble("prix");
			String vieilleDescr=elt.getString("descr");
			double nvPrix=vieuxPrix+((Produit)data).getPrix();
			String nvDescr=vieilleDescr+" "+((Produit)data).getDescr();
		
			String requeteModif= "UPDATE "+table+" SET prix="+nvPrix+", descr='"+nvDescr+"'"+" WHERE id="+((Produit)data).getId();
			base.execute(requeteModif);*/
			update(data,table);
		}
		else {
			String insertRequete="INSERT INTO "+table+" VALUES "+data.getValues();
			base.execute(insertRequete);
			base.close();
		}
	}
	public void update(IData data, String table) throws SQLException {

	    // 1. Vérifier la structure
	    HashMap<String, fieldType> tableStruct = structTable(table, false);
	    data.getStruct();

	    if (!data.check(tableStruct)) {
	        System.out.println("Erreur : structure différente !");
	        return;
	    }

	    // 2. Construire la requête UPDATE
	    StringBuilder requete = new StringBuilder("UPDATE " + table + " SET ");

	    // On récupère la map des colonnes/types
	    HashMap<String, fieldType> map = data.getMap();

	    for (String col : map.keySet()) {
	        if (!col.equals("id")) {  // on ne modifie jamais l'id
	            requete.append(col)
	                   .append(" = ");

	            // On récupère la valeur dans la chaîne values
	            // values = "(id, 'nom', 'date', ...)"
	            // On doit donc reconstruire les valeurs
	        }
	    }

	    // 3. Récupérer les valeurs depuis getValues()
	    // Exemple : "(1,'Rex','2024-01-01','chien')"
	    String values = data.getValues();
	    values = values.substring(1, values.length() - 1); // enlever les parenthèses
	    String[] val = values.split(",");
	    int i = 0;
	    for (String col : map.keySet()) {
	        if (!col.equals("id")) {
	            requete.append(val[i].trim()).append(", ");
	        }
	        i++;
	    }
	    // enlever la dernière virgule
	    requete.setLength(requete.length() - 2);
	    // 4. WHERE id = ...
	    requete.append(" WHERE id = ").append(data.getId());
	    // 5. Exécuter
	    Statement st = laConnection.createStatement();
	    st.execute(requete.toString());
	    st.close();
	    System.out.println("Mise à jour effectuée !");
	}
	
	/**
	 * fct utilitaire qui permet d'executer un script sql
	 * @param sql
	 * @throws SQLException
	 */
	private void displayQuery(String sql) throws SQLException {
	    PreparedStatement ps = laConnection.prepareStatement(sql);
	    ResultSet rs = ps.executeQuery();
	    ResultSetMetaData meta = rs.getMetaData();
	    int nb = meta.getColumnCount();

	    while (rs.next()) {
	        for (int i = 1; i <= nb; i++) {
	            System.out.print(meta.getColumnName(i) + " : " + rs.getString(i) + " | ");
	        }
	        System.out.println();
	    }
	}

/*ANIMAL
 * public void searchAnimal(String critereSQL)
 * */
	/**
	 * permet de chercher un animal par rapport a un critere
	 * @param critereSQL est de la forme "race=chat"
	 * @throws SQLException
	 */
	public void chercheAnimal(String critereSQL) throws SQLException{
		String sql = "SELECT * FROM animal WHERE " + critereSQL; 
		displayQuery(sql);
	}
/*SITUATIONANIMAL
 * public void updateStatutAnimal(int idAnimal, String nouveauStatut)*/
	
	public void updateStatutAnimal(int idAnimal, String nouveauStatut) throws SQLException {
	    String sql = "UPDATE animal SET statut = '" + nouveauStatut + "' WHERE id = " + idAnimal;

	    Statement st = laConnection.createStatement();
	    int l = st.executeUpdate(sql);
	    st.close();

	    if (l > 0) {
	        System.out.println("MIS A JOUR: Statut de l'animal-> " + idAnimal + "  : " + nouveauStatut);
	    } else {
	        System.out.println("Aucun animal avec l'id : " + idAnimal);
	    }
	}
	
/*HISTORIQUE SITUATION BOX FICHE?
 *public void historiqueAnimal(int idAnimal) */
	public void historiqueAnimal(int idAnimal) throws SQLException{
		displayQuery("SELECT * FROM animal WHERE id = " + idAnimal);
		displayQuery("SELECT * FROM situationanimal sa join situation s ON sa.idSituation = s.idSituation WHERE sa.idAnimal = " + idAnimal + " ORDER BY s.dateD");
		displayQuery("SELECT * FROM boxanimal WHERE idAnimal = " + idAnimal + " ORDER BY dateD");
		displayQuery("SELECT * FROM fiche WHERE idAnimal = " + idAnimal + " ORDER BY dsoin");
	}

	/*SITUATION
	 *Ajouter adoption 
	 *Retour adoption*/
	public void ajouterSituation(int idAnimal, int idFamille, String type, String commentaire, Date dateD) throws SQLException {
	    Statement st = laConnection.createStatement();
	    String sql =
	        "INSERT INTO situation (idFamille, type, commentaire, dateD, dateF) " +
	        "VALUES (" + idFamille + ", '" + type + "', '" + commentaire + "', '" + dateD + "', NULL)";
	    st.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);

	    // 2. Récupérer l'idSituation
	    ResultSet rs = st.getGeneratedKeys();
	    int idSituation = 0;
	    if (rs.next()) idSituation = rs.getInt(1);

	    // 3. Lier l’animal à cette situation
	    String sqlLien =
	        "INSERT INTO situationanimal (idSituation, idAnimal) VALUES (" + idSituation + ", " + idAnimal + ")";
	    st.executeUpdate(sqlLien);

	    st.close();
	    System.out.println("Situation ajoutée : " + type);
	}
	public void finSituation(int idAnimal, String type, Date dateF, String commentaire) throws SQLException {
	    Statement st = laConnection.createStatement();
	    // Trouver la situation de l'animal en cours (dateF = NULL)
	    String sqlFind =
	        "SELECT s.idSituation FROM situation s " +
	        "JOIN situationanimal sa ON s.idSituation = sa.idSituation " +
	        "WHERE sa.idAnimal = " + idAnimal +
	        " AND s.type = '" + type + "' AND s.dateF IS NULL";
	    ResultSet rs = st.executeQuery(sqlFind);
	    if (!rs.next()) {
	        System.out.println("Aucune situation '" + type + "' en cours.");
	        return;
	    }
	    int idSituation = rs.getInt("idSituation");
	    // Mettre fin à la situation
	    String sqlUpdate =
	        "UPDATE situation SET dateF = '" + dateF + "', commentaire = '" + commentaire + "' " +
	        "WHERE idSituation = " + idSituation;

	    st.executeUpdate(sqlUpdate);
	    st.close();
	    System.out.println("Situation '" + type + "' terminée.");
	}

/*FAMILLE
 *public void searchFamille(String critereSQL)
 */
	public void chercheFamille(String critereSQL) throws SQLException{
		String sql = "SELECT * FROM famille WHERE " + critereSQL; 
		displayQuery(sql);
	}
/*BENEVOLE
 * public void searchBenevole(String critereSQL)
 * 
*/
	public void chercheBenevole(String critereSQL) throws SQLException{
		String sql = "SELECT * FROM benevole WHERE " + critereSQL; 
		displayQuery(sql);
	}
/*GRPBENEVOLE
 * public void besoinBenevoles(int idCreno)  // je lai mis en void pr savoir combien de benevole on a besoin  
*/
	public void besoinBenevoles(int idCreno) throws SQLException{
		String sql ="SELECT a.nbMin, COUNT(gb.idBenevole) AS nbBeneInscrits " +
					"FROM Groupe g " +
					"JOIN Activite a ON g.idAct=a.idAct"+
					"LEFT JOIN GrpBenvole gb ON g.idGrp=gb.idGrp"+
					"WHERE g.idCreneau =" + idCreno + " "+
					"GROUP BY a.nbMin";
		Statement st = laConnection.createStatement();
		ResultSet rs = st.executeQuery(sql);
		if(rs.next()) {
			int nbMin=rs.getInt("nbMin");
			int nbBeneInscrits =rs.getInt("nbBeneInscrits");
			if(nbBeneInscrits<nbMin) {
				int nbManque = nbMin-nbBeneInscrits;
				System.out.println("Il manque " + nbManque + " bénévoles pour le créneau "+ idCreno);
			}
			else {
				System.out.println("Il ne manque pas de bénévoles pour le créneau "+ idCreno);
			}
		}
		else {
			System.out.println("Il n'y a pas de groupes pour le créneau " + idCreno);  // jsp si on peut mettre une exception la ? 
		}
	}
	

	
/*CRENAUX
 * public void inscrireParticipant(int idAct, int idParticipant, String role)
 * planing
 * afficher activite
 * remplacer une activite
*/
	/*public void inscrireParticipant(int idAct, int idParticipant, String role) throws SQLException {
		String sqlGrp="SELECT idGrp FROM Groupe WHERE idAct="+idAct;
		Statement st = laConnection.createStatement();
		ResultSet rs = st.executeQuery(sqlGrp);
		if(!rs.next()){
			System.out.println("Il n'y a ");
		}
	} /*
	// A COMPLETER POUR LE ROLE 

/*BOX
 * Ajouter un animal dans un box 
 * Améliorer capacité
 * Enlever / déplacer un animal*/
	
	//A voir si pour la date on met valueOf(LocalDate.now()); pour avoir direct la date 
	public void ajoutAnimalBox(int idBox, int idAnimal, Date dateD) throws SQLException {
		String sqlCptAnimaux="SELECT COUNT(*) AS nbAnimaux FROM BoxAnimal WHERE idBox= " +idBox;
		Statement st = laConnection.createStatement();
		ResultSet rs = st.executeQuery(sqlCptAnimaux);
		int nbAnimaux=0;
		if(rs.next()) {
			nbAnimaux=rs.getInt("nbAnimaux");
		}
		rs.close();
		
		String sqlCapMax="SELECT capMax FROM Box WHERE idBox= "+ idBox;
		ResultSet rsCapMax= st.executeQuery(sqlCapMax);
		int capMax=0;
		if(rsCapMax.next()) {
			capMax=rs.getInt("capMax");
		}		
		rsCapMax.close();
		
		if(nbAnimaux>=capMax) {
			System.out.println("Le Box" +idBox + "est plein");
			st.close();
			return;
		}
		String sqlajoutBoxAnimal="INSERT INTO BoxAnimal(idBox, idAnimal, dateD, dateF)"+
								"VALUES (" + idBox +", "+idAnimal+", "+dateD+", "+"NULL )";
		st.executeUpdate(sqlajoutBoxAnimal);
		System.out.println("L'animal "+ idAnimal + " est ajouté dans le box " +idBox );
		st.close();
		
	}
	
	//j'ai mis que la date de debut et de fin sont les memes 
	public void deplacerAnimal(int idAnimal, int idAncienBox, int idNvBox, Date date) throws SQLException {
		Statement st = laConnection.createStatement();
		String sqlCptAnimaux="SELECT COUNT(*) AS nbAnimaux FROM BoxAnimal WHERE idBox= " +idNvBox;
		ResultSet rs =st.executeQuery(sqlCptAnimaux);
		int nbAnimaux=0;
		if(rs.next()) {
			nbAnimaux=rs.getInt("nbAnimaux");
		}
		rs.close();
		String sqlCapMax="SELECT capMax FROM Box WHERE idBox= "+ idNvBox;
		ResultSet rsCapMax= st.executeQuery(sqlCapMax);
		
		int capMax=0;
		if(rsCapMax.next()) {
			capMax=rs.getInt("capMax");
		}		
		rsCapMax.close();
	
		if(nbAnimaux>=capMax) {
			System.out.println("Le Box" +idNvBox + "est plein, vous ne pouvez pas ajouté d'animaux");
			st.close();
			return;
		}
		String sqlModifAncienBox="UPDATE BoxAnimal SET dateF=' "+date + "' "+" WHERE idAnimal= "+ idAnimal+  " AND idBox = " +idAncienBox+ " AND dateF IS NULL";
		int modifLigne=st.executeUpdate(sqlModifAncienBox);
		if (modifLigne==0) {
			System.out.println("L'animal"+ idAnimal +" n'est pas dans le box");
			st.close();
			return;
		}
		String sqlModifNvBox="INSERT INTO BoxAnimal (idBox, idAnimal, dateD, dateF) "+ 
							"VALUES ("+ idNvBox+ ", "+ idAnimal+ ", '"+ date + "', NULL)";
		st.execute(sqlModifNvBox);
		System.out.println("Animal : " + idAnimal + ", Ancien box : "+ idAncienBox +", Nouveau Box : " + idNvBox);
		st.close();
	}
}