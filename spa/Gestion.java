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

	public int getNextId(String table, String idColumn) throws SQLException {
	    Statement st = laConnection.createStatement();
	    String sql = "SELECT MAX(" + idColumn + ") FROM " + table;
	    ResultSet rs = st.executeQuery(sql);

	    if (rs.next()) {
	        return rs.getInt(1) + 1; // dernier ID + 1
	    }
	    return 1; // si la table est vide
	}
	
/*ANIMAL
 * public void searchAnimal(String critereSQL)   // Pourquoi avec critere et pas id plutot ??
 * */ 
	/**
	 * permet de chercher un animal par rapport a un critere
	 * @param critereSQL est de la forme "race=chat"
	 * @throws SQLException
	 */
	public void chercheAnimal(String critereSQL) throws SQLException{
		String sql = "SELECT * FROM animal WHERE " + critereSQL; 
		displayQuery(sql);//il manque un message si il y a rien
	}
	
	public void chercheAnimalId(int IdAnimal) throws SQLException{
		String sql = "SELECT * FROM animal WHERE IdAnimal = " + IdAnimal; 
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
	/**
	 * Calcule si on a besoin de benevoles pour un creno ou pas 
	 * @param idCreno
	 * @throws SQLException
	 */
	public void besoinBenevoles(int idCreno) throws SQLException{
		String sql ="SELECT a.nbMin, COUNT(gb.idBenevole) AS nbBeneInscrits " +
					"FROM Groupe g " +
					"JOIN Activite a ON g.idAct=a.idAct"+
					"LEFT JOIN GrpBenevole gb ON g.idGrp=gb.idGrp"+
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
	
	public void chercheBenevoleId(int IdBene) throws SQLException{
		String sql = "SELECT * FROM Benevole WHERE IdBenevole = " + IdBene; 
		displayQuery(sql);
	}

	
/*CRENAUX
 * public void inscrireParticipant(int idAct, int idParticipant, String role)
 * planing
 * afficher activite
 * remplacer une activite
*/
	
	/**
	 * inscrit un participant (bénévole ou animal en fonction à une activité en fonction de l'acticité et du créneau
	 * @param idAct
	 * @param idCreno
	 * @param idParticipant
	 * @param type
	 * @throws SQLException
	 */
	public void inscrireParticipant(int idAct, int idCreno, int idParticipant, String type) throws SQLException {
		Statement st = laConnection.createStatement();
		String sqlGrp="SELECT idGrp FROM Groupe WHERE idAct="+idAct + " AND idCreneau = " + idCreno;
		ResultSet rs = st.executeQuery(sqlGrp);
		int idGrp; 
		if(!rs.next()){
			String sqlAjoutGrp = "INSERT INTO Groupe (idAct, idCreneau) VALUES (" + idAct + ", " + idCreno + ")";
			st.executeUpdate(sqlAjoutGrp, Statement.RETURN_GENERATED_KEYS);
			ResultSet rsKey = st.getGeneratedKeys();
			if(rsKey.next()){
				idGrp=rsKey.getInt(1);
				System.out.println("Nouveau groupe créé pour l'activité " + idAct + ", et le créneau : " + idCreno);
			}
			else {
				System.out.println("erreur création du groupe" );
				rs.close();
				return; 
			}
			rsKey.close();
		}
		else {
			idGrp=rs.getInt("idGrp");
		}
		rs.close();
		//inscrire participant en fct du type 
		if(type.equalsIgnoreCase("bénévole")){
			//verifer si le bénévole est ideja nscrit
			String sqlBene="SELECT * FROM GrpBenevole WHERE idGrp = "+idGrp + " AND idBenevole = " + idParticipant;
			ResultSet rsVerifBene=st.executeQuery(sqlBene);
			
			if(rsVerifBene.next()){
				System.out.println("Le participant "+idParticipant + "est deja inscrit à l'activité " + idAct);
				rsVerifBene.close();
				st.close();
				return; 
			}
			rsVerifBene.close();
			
			//inscrire le bénévole 
			String sqlAjoutBene = "INSERT INTO GrpBenevole (idGrp, idBenevole) (" + idGrp + ", "+idParticipant+ " )";
			st.executeUpdate(sqlAjoutBene);
			System.out.println("Bénévole "+ idParticipant + "est bien inscrit à l'activité"+ idAct);
		}
		else if (type.equalsIgnoreCase("animal")) {
			//verifer si l'animal est ideja nscrit
			String sqlVerifAni="SELECT * FROM GrpAnimal WHERE idGrp = "+idGrp + " AND idAnimal = " + idParticipant;
			ResultSet rsVerifAni=st.executeQuery(sqlVerifAni);
			if(rsVerifAni.next()){
				System.out.println("L'animal "+idParticipant + "est deja inscrit à l'activité " + idAct);
				rsVerifAni.close();
				st.close();
				return; 
			}
			rsVerifAni.close();
			
			//inscrire le bénévole 
			String sqlAjoutAni = "INSERT INTO GrpAnimal (idGrp, idAnimal) (" + idGrp + ", "+idParticipant+ " )";
			st.executeUpdate(sqlAjoutAni);
			System.out.println("Animal "+ idParticipant + "est bien inscrit à l'activité"+ idAct);
		}
		else {
			System.out.println("Mauvais type"+type +" (mettre 'animal' ou 'bénévole')");
		}
		st.close();
	}


	
	/**
	 * Affiche quels sont les benevoles et participants des activité en fonction d'un creneau
	 * @param idCreno
	 * @throws SQLException
	 */

	public void afficherParticipant(int idCreno) throws SQLException{
		Statement st = laConnection.createStatement();
		String sqlGrp = "SELECT g.idGrp, g.idAct, a.nom AS activite " + 
						"FROM Groupe g " +
						"JOIN Activite a ON g.idAct = a.idAct " +
						"WHERE g.idCreneau = " + idCreno;
		ResultSet rsGrp = st.executeQuery(sqlGrp);
		if(!rsGrp.next()) {
			System.out.println("Il n'y a aucun goupe pour le créneau : " +idCreno);
			st.close();
			return;
		}
		int idGrp =rsGrp.getInt("idGrp");
		int idAct =rsGrp.getInt("idAct");
		String activite =rsGrp.getString("activite");
		System.out.println("Activité : " + activite+ " id : " + idAct);
		String sqlBene = "SELECT b.idBenevole, b.nom, b.prenom "+
						"FROM GrpBenevole gB " +
						"JOIN Benevole b ON gB.idBenevole=b.idBenevole "+
						"WHERE gB.idGrp = " + idGrp;
		
		ResultSet rsBene = st.executeQuery(sqlBene);
		System.out.println("Benevoles pour l'activité : ");
		boolean benevoleAct = false; 
		while (rsBene.next()) {
			benevoleAct =true;
			int idBene= rsBene.getInt("idBenevole");
			String nom = rsBene.getString("nom");
			String prenom = rsBene.getString("prenom");
			System.out.println("- " + nom + " "+prenom+ " id : " + idBene);
		}
		if(!benevoleAct) {
			System.out.println("Il n'y a pas de benevoles pour cette activité ");
		}
		rsBene.close();
		
		String sqlAni = "SELECT a.idAnimal, a.nom, a.type "+
				"FROM GrpAnimal gA " +
				"JOIN Animal a ON gA.idAnimal=a.idAnimal "+
				"WHERE gA.idGrp = " + idGrp;

		ResultSet rsAni = st.executeQuery(sqlAni);
		System.out.println("Animaux pour l'activité : ");
		boolean animalAct= false; 
		while (rsAni.next()) {
			animalAct =true;
			int idAnimal= rsAni.getInt("idAnimal");
			String nom = rsAni.getString("nom");
			String type = rsAni.getString("type");
			System.out.println("* " + nom + " "+type+ " id : " + idAnimal);
		}
		if(!animalAct) {
			System.out.println("Il n'y a pas d'animal pour cette activité ");
		}
		rsAni.close();
		st.close();
	}

/*BOX
 * Ajouter un animal dans un box -->fait 
 * Améliorer capacité (??)
 * Enlever / déplacer un animal --> fait 
 * */
	
	/**
	 * ajoute en animal en calculant la capacité et en modifiant les dates de debut et de fin 
	 * @param idBox
	 * @param idAnimal
	 * @param dateD
	 * @throws SQLException
	 */
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
		String sqlajoutBoxAnimal = "INSERT INTO BoxAnimal (idBox, idAnimal, dateD, dateF) "
				+ "VALUES (" + idBox + ", " + idAnimal + ", '" + dateD + "', NULL)";
		st.executeUpdate(sqlajoutBoxAnimal);
		System.out.println("L'animal "+ idAnimal + " est ajouté dans le box " +idBox );
		st.close();
		
	}
	
	/**
	 * deplace l'animal d'un box à un autre en calculant la capacité et en modifiant les dates de debut et de fin 
	 * @param idAnimal
	 * @param idAncienBox
	 * @param idNvBox
	 * @param date
	 * @throws SQLException
	 */
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
	
	/**
	 * supprime l'animal d'un box ou il etait 
	 * @param idAnimal
	 * @param idBox
	 * @param dateF
	 * @throws SQLException
	 */
	public void suppAnimal(int idAnimal, int idBox, Date dateF) throws SQLException {
		String sqlModif="UPDATE BoxAnimal SET dateF = '"+dateF+"' " + "WHERE idBox = " +idBox + "AND idAnimal = " +idAnimal + "AND dateF IS NULL";
		Statement st = laConnection.createStatement();
		int modifLigne=st.executeUpdate(sqlModif);
		if(modifLigne>0) {
			System.out.println("L'animal "+idAnimal+ " n'est plus dans le box " + idBox);
		}
		else {
			System.out.println("Erreur, l'animal "+idAnimal +" n'est pas trouvé dans le box");
		}
	}

	
}