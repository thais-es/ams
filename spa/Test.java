package spa;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Date;

public class Test {

    public static void main(String[] args) {
        try {
            Connection conn = Connect.seConnecter();
            Gestion g = new Gestion(conn);
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                System.out.println("=== MENU PRINCIPAL ===");
                System.out.println("1. Mode CLIENT");
                System.out.println("2. Mode GESTIONNAIRE");
                System.out.println("3. Mode Admin");
                System.out.println("0. Quitter");
                System.out.print("> ");

                int choix = Integer.parseInt(in.readLine());

                if (choix == 1) menuClient(g, in);
                else if (choix == 2) menuGestionnaire(g, in);
                else if (choix== 3) menuAdmin(g, in);
                else if (choix == 0) break;
                else System.out.println("Choix invalide");
            }
            conn.close();
            System.out.println("Fin du client SPA.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void menuAdmin(Gestion g, BufferedReader in) throws Exception {
    	 int choix = -1;

         while (choix != 0) {
        	 System.out.println("   MODE GESTIONNAIRE  ");
             System.out.println("1. Cretion d'une table");
             System.out.println("2. Display");
             System.out.println("3. Struct");
             System.out.println("4. Insert");
             System.out.println("5. drop");
             System.out.println("0. Retour menu principal");
             System.out.print("> ");
             choix = Integer.parseInt(in.readLine());
             switch(choix) {
             case 1:
            	 System.out.println("Création de toutes les tables SPA..."); 
            	 g.execute("CREATE TABLE IF NOT EXISTS BoxTH ( "
             	 		+ "idBox INT PRIMARY KEY, "
             	 		+ "espece VARCHAR(10), "
             	 		+ "capMax INT);"); 
            	 g.execute("CREATE TABLE IF NOT EXISTS animalTH ("
            	 		+ "idAnimal INT PRIMARY KEY, "
            			+ "idBox INT, "
            	 		+ "nom VARCHAR(15), "
            	 		+ "arrive DATE, "
            	 		+ "type VARCHAR(15), "
            	 		+ "race VARCHAR(50), "
            	 		+ "naissance DATE, "
            	 		+ "statut VARCHAR(50), "
            	 		+ "CONSTRAINT fk_animalTH_BoxTH FOREIGN KEY (idBox) REFERENCES BoxTH(idBox));"); 
            	 g.execute("CREATE TABLE IF NOT EXISTS FamilleTH ("
            	 		+ "idFamille INT PRIMARY KEY, "
            	 		+ "nom VARCHAR(15), "
            	 		+ "tel VARCHAR(20), "
            	 		+ "mail VARCHAR(100), "
            	 		+ "adresse VARCHAR(100));"); 
            	 g.execute("CREATE TABLE IF NOT EXISTS benevoleTH ("
            	 		+ "idBenevole INT PRIMARY KEY, "
            	 		+ "nom VARCHAR(15), "
            	 		+ "prenom VARCHAR(15), "
            	 		+ "numtel VARCHAR(20), "
            	 		+ "mail VARCHAR(100), "
            	 		+ "adresse VARCHAR(100));"); 
            	 g.execute("CREATE TABLE IF NOT EXISTS SituationTH ("
            	 		+ "idSituation INT PRIMARY KEY, "
            	 		+ "idFamille INT, "
            	 		+ "type VARCHAR(50), "
            	 		+ "commentaire TEXT, "
            	 		+ "dateD DATE, "
            	 		+ "dateF DATE, "
            	 		+ "CONSTRAINT fk_SituationTH_FamilleTH FOREIGN KEY (idFamille) REFERENCES FamilleTH(idFamille));"); 
            	 g.execute("CREATE TABLE IF NOT EXISTS SituationanimalTH ( "
            	 		+ "idSituation INT, "
            	 		+ "idAnimal INT, "
            	 		+ "PRIMARY KEY (idSituation, idAnimal), "
            	 		+ "CONSTRAINT fk_SituationanimalTH_SituationTH FOREIGN KEY (idSituation) REFERENCES SituationTH(idSituation), "
            	 		+ "CONSTRAINT fk_SituationanimalTH_animalTH FOREIGN KEY (idAnimal) REFERENCES animalTH(idAnimal));"); 
            	
            	 g.execute("CREATE TABLE IF NOT EXISTS BoxanimalTH ( "
            	 		+ "idBox INT, "
            	 		+ "idAnimal INT, "
            	 		+ "dateD DATE, "
            	 		+ "dateF DATE, "
            	 		+ "primary key (idBox,idAnimal), "
            	 		+ "CONSTRAINT fk_BoxanimalTH_BoxTH FOREIGN KEY (idBox) REFERENCES BoxTH(idBox), "
            	 		+ "CONSTRAINT fk_BoxanimalTH_animalTH FOREIGN KEY (idAnimal) REFERENCES animalTH(idAnimal));"); 
            	 g.execute("CREATE TABLE IF NOT EXISTS FicheanimalTH ( "
            	 		+ "idFiche INT PRIMARY KEY, "
            	 		+ "idAnimal INT, "
            	 		+ "dsoin DATE, "
            	 		+ "ordonnance TEXT, "
            	 		+ "commentaire TEXT, "
            	 		+ "prochainRdv DATE, "
            	 		+ "maladie VARCHAR(50), "
            	 		+ "CONSTRAINT fk_FicheanimalTH_animalTH FOREIGN KEY (idAnimal) REFERENCES animalTH(idAnimal));"); 
            	 g.execute("CREATE TABLE IF NOT EXISTS ActiviteTH ( "
            	 		+ "idAct INT PRIMARY KEY, "
            	 		+ "type VARCHAR(10), "
            	 		+ "nom VARCHAR(10), "
            	 		+ "lieu VARCHAR(20), "
            	 		+ "nbMin INT);"); 
            	 g.execute("CREATE TABLE IF NOT EXISTS CreneauTH ( "
             	 		+ "idCreneau INT PRIMARY KEY, "
             	 		+ "heureD VARCHAR(20), "
             	 		+ "heureF VARCHAR(20));"); 
            	 g.execute("CREATE TABLE IF NOT EXISTS GroupeTH ( "
            	 		+ "idGrp INT PRIMARY KEY, "
            	 		+ "idAct INT, "
            	 		+ "idCreneau INT, "
            	 		+ "CONSTRAINT fk_GroupeTH_CreneauTH FOREIGN KEY (idCreneau) REFERENCES CreneauTH(idCreneau), "
            	 		+ "CONSTRAINT fk_GroupeTH_ActiviteTH FOREIGN KEY (idAct) REFERENCES ActiviteTH(idAct)"
            	 		+ ");"); 
            	 g.execute("CREATE TABLE IF NOT EXISTS Grpbenevoleth ( "
            	 		+ "idGrp INT, "
            	 		+ "idBenevole INT, "
            	 		+ "primary key (idGrp,idBenevole), "
            	 		+ "CONSTRAINT fk_GrpbenevoleTH_GroupeTH FOREIGN KEY (idGrp) REFERENCES GroupeTH(idGrp), "
            	 		+ "CONSTRAINT fk_GrpbenevoleTH_benevoleTH FOREIGN KEY (idBenevole) REFERENCES benevoleTH(idBenevole)"
            	 		+ ");"); 
            	
            	 g.execute("CREATE TABLE IF NOT EXISTS GrpanimalTH ( "
            	 		+ "idGrp int, "
            	 		+ "idAnimal int, "
            	 		+ "primary key (idGrp,idAnimal),"
            	 		+ "CONSTRAINT fk_GrpanimalTH_GroupeTH FOREIGN KEY (idGrp) REFERENCES GroupeTH(idGrp),"
            	 		+ "CONSTRAINT fk_GrpanimalTH_animalTH FOREIGN KEY (idAnimal) REFERENCES animalTH(idAnimal));");
            	 System.out.println("Toutes les tables ont été créées !"); 
            	 break;
             case 2:
            	 System.out.print("Nom de la table à afficher : "); 
            	 String tableDisplay = in.readLine(); 
            	 g.displayTable(tableDisplay); 
            	 break;
             case 3:
            	 System.out.print("Nom de la table : "); 
            	 String tableStruct = in.readLine(); 
            	 g.structTable(tableStruct, true); 
            	 break;
             case 4:
            	 System.out.println("=== INSERTION ==="); 
            	 System.out.println("1. Animal"); 
            	 System.out.println("2. Famille"); 
            	 System.out.println("3. Bénévole"); 
            	 System.out.println("4. Box"); 
            	 System.out.println("5. Activité"); 
            	 System.out.println("6. Fiche"); 
            	 System.out.println("0. Annuler"); 
            	 System.out.print("> "); 
            	 int typeInsert = Integer.parseInt(in.readLine()); 
            	 switch(typeInsert) { 
            	 case 1: 
            		 insertAnimal(g, in); 
            		 break; 
            	 case 2: 
            			 insertFamille(g, in); 
            			 break; 
            	 case 3: 
            		 insertBenevole(g, in); 
            		 break; 
            	 case 4: 
            		 insertBox(g, in); 
            		 break; 
            	 case 5: 
            		 insertActivite(g, in); 
            		 break; 
            	 case 6: 
            		 insertFiche(g, in); 
            		 break; 

            	 case 0: System.out.println("Insertion annulée."); break; 
            	 default: 
            		 System.out.println("Choix invalide."); } 
            	 break;
             case 5:
            	 System.out.print("Nom de la table à supprimer : "); 
            	 String tableDrop = in.readLine(); 
            	 System.out.print("Confirmer (oui/non) : "); 
            	 if (in.readLine().equalsIgnoreCase("oui")) { 
            		 g.execute("DROP TABLE IF EXISTS " + tableDrop + "CASCADE;"); 
            		 System.out.println("Table supprimée."); } 
            	 else { 
            		 System.out.println("Annulé."); } 
            	 break;
             case 0:
            	 System.out.println("Retour au menu principal."); 
            	 break;
             default:
                 System.out.println("Choix invalide");
             }
         }
    }
    public static void insertAnimal(Gestion g, BufferedReader in) throws Exception {
    	//System.out.print("id : "); 
    	//int id = Integer.parseInt(in.readLine());
    	int newId = g.getNextId("animalTH", "idanimal");
    	System.out.print("idBox : "); 
    	int idB = Integer.parseInt(in.readLine());
    	System.out.print("nom : "); 
    	String nom = in.readLine(); 
    	System.out.print("date d'arrivée (YYYY-MM-DD) : "); 
    	Date arrive = Date.valueOf(in.readLine()); 
    	System.out.print("type (chien/chat/autre) : "); 
    	String type = in.readLine(); 
    	System.out.print("race : "); String race = in.readLine(); 
    	System.out.print("date de naissance (YYYY-MM-DD) : "); 
    	Date naissance = Date.valueOf(in.readLine()); 
    	System.out.print("statut : "); 
    	String statut = in.readLine();
        Animal a = new Animal(newId,idB,nom,arrive,type,race, naissance, statut);
        g.insert(a, "animalTH");
    }
    public static void insertFamille(Gestion g, BufferedReader in) throws Exception {
    	/*System.out.print("idFamille : "); 
    	int idFamille = Integer.parseInt(in.readLine()); */
    	int newId = g.getNextId("FamilleTH", "idfamille");
    	System.out.print("nom : "); 
    	String nom = in.readLine(); 
    	System.out.print("tel : "); 
    	String tel = in.readLine();
    	System.out.print("mail : "); 
    	String mail = in.readLine(); 
    	System.out.print("adresse : "); 
    	String adresse = in.readLine();
        Famille f = new Famille(newId,nom,tel, mail, adresse);
        g.insert(f, "FamilleTH");
    }
    public static void insertBenevole(Gestion g, BufferedReader in) throws Exception {
    	/*System.out.print("idBenevole : "); 
    	int idBenevole = Integer.parseInt(in.readLine()); */
    	int newId = g.getNextId("benevoleth", "idbenevole");
    	System.out.print("nom : "); 
    	String nom = in.readLine(); 
    	System.out.print("prenom : "); 
    	String prenom = in.readLine(); 
    	System.out.print("numTel : "); 
    	String numTel = in.readLine(); 
    	System.out.print("mail : "); 
    	String mail = in.readLine(); 
    	System.out.print("adresse : "); 
    	String adresse = in.readLine();
        Benevole b = new Benevole(newId,nom, prenom, numTel, mail, adresse);
        g.insert(b, "benevoleTH");
    }
    public static void insertBox(Gestion g, BufferedReader in) throws Exception {
    	int newId = g.getNextId("BoxTH", "idbox");
        //System.out.print("idBox : ");
        //int id = Integer.parseInt(in.readLine());
        System.out.print("espece (chien/chat/autre) : "); 
        String espece = in.readLine();
        System.out.print("capMax : ");
        int cap = Integer.parseInt(in.readLine());

        Box b = new Box(newId,espece, cap);
        g.insert(b, "box");
    }
    public static void insertActivite(Gestion g, BufferedReader in) throws Exception {
    	/*System.out.print("idAct : "); 
    	int idAct = Integer.parseInt(in.readLine()); */
    	int newId = g.getNextId("Activite", "idact");
    	System.out.print("type : "); 
    	String type = in.readLine(); 
    	System.out.print("nom : "); 
    	String nom = in.readLine(); 
    	System.out.print("lieu : "); 
    	String lieu = in.readLine(); 
    	System.out.print("nbMin : "); 
    	int nbMin = Integer.parseInt(in.readLine());

        Activite a = new Activite(newId, type, nom, lieu, nbMin);
        g.insert(a, "activiteTH");
    }
    public static void insertFiche(Gestion g, BufferedReader in) throws Exception {
    	/*System.out.print("idFiche : "); 
    	int idFiche = Integer.parseInt(in.readLine());*/
    	int newId = g.getNextId("FicheanimalTH", "idfiche");
    	System.out.print("idBox : ");
    	System.out.print("idAnimal : "); 
    	int idAnimal = Integer.parseInt(in.readLine()); 
    	System.out.print("date soin (YYYY-MM-DD) : "); 
    	Date dSoin = Date.valueOf(in.readLine()); 
    	System.out.print("ordonnance : "); 
    	String ordonnance = in.readLine(); 
    	System.out.print("commentaire : "); 
    	String commentaire = in.readLine(); 
    	System.out.print("prochain RDV (YYYY-MM-DD) : "); 
    	Date prochainRdv = Date.valueOf(in.readLine()); 
    	System.out.print("maladie : "); 
    	String maladie = in.readLine();
        FicheAnimal f = new FicheAnimal(newId, idAnimal, dSoin, ordonnance, commentaire, maladie, prochainRdv, maladie); // idFiche = auto SERIAL
        g.insert(f, "ficheTH");
    }

    public static void menuGestionnaire(Gestion g, BufferedReader in) throws Exception {

        int choix = -1;

        while (choix != 0) {
            System.out.println("  MODE GESTIONNAIRE  ");
            System.out.println("1. Ajouter une situation");
            System.out.println("2. Terminer une situation");
            System.out.println("3. Ajouter un animal dans un box");
            System.out.println("4. Déplacer un animal");
            System.out.println("5. Besoin de bénévoles ?");
            System.out.println("6. Chercher un bénévole");
            System.out.println("0. Retour menu principal");
            System.out.print("> ");

            choix = Integer.parseInt(in.readLine());

            switch (choix) {

                case 1:
                    System.out.print("ID animal : ");
                    int idA = Integer.parseInt(in.readLine());
                    System.out.print("ID famille : ");
                    int idF = Integer.parseInt(in.readLine());
                    System.out.print("Type : ");
                    String type = in.readLine();
                    System.out.print("Commentaire : ");
                    String com = in.readLine();
                    System.out.print("Date début (YYYY-MM-DD) : ");
                    Date dD = Date.valueOf(in.readLine());
                    g.ajouterSituation(idA, idF, type, com, dD);
                    break;

                case 2:
                    System.out.print("ID animal : ");
                    int idAF = Integer.parseInt(in.readLine());
                    System.out.print("Type : ");
                    String typeF = in.readLine();
                    System.out.print("Date fin (YYYY-MM-DD) : ");
                    Date dF = Date.valueOf(in.readLine());
                    System.out.print("Commentaire fin : ");
                    String comF = in.readLine();
                    g.finSituation(idAF, typeF, dF, comF);
                    break;

                case 3:
                    System.out.print("ID box : ");
                    int idB = Integer.parseInt(in.readLine());
                    System.out.print("ID animal : ");
                    int idAB = Integer.parseInt(in.readLine());
                    System.out.print("Date début (YYYY-MM-DD) : ");
                    Date dBox = Date.valueOf(in.readLine());
                    g.ajoutAnimalBox(idB, idAB, dBox);
                    break;

                case 4:
                    System.out.print("ID animal : ");
                    int idAD = Integer.parseInt(in.readLine());
                    System.out.print("Ancien box : ");
                    int idOld = Integer.parseInt(in.readLine());
                    System.out.print("Nouveau box : ");
                    int idNew = Integer.parseInt(in.readLine());
                    System.out.print("Date (YYYY-MM-DD) : ");
                    Date dDep = Date.valueOf(in.readLine());
                    g.deplacerAnimal(idAD, idOld, idNew, dDep);
                    break;

                case 5:
                    System.out.print("ID créneau : ");
                    g.besoinBenevoles(Integer.parseInt(in.readLine()));
                    break;

                case 6:
                    System.out.print("Critère bénévole : ");
                    g.chercheBenevole(in.readLine());
                    break;

                case 7:
                    System.out.print("ID activité : ");
                    int idAct = Integer.parseInt(in.readLine());
                    System.out.print("ID créneau : ");
                    int idCreneau = Integer.parseInt(in.readLine());
                    System.out.print("ID participant : ");
                    int idParticipant = Integer.parseInt(in.readLine());
                    System.out.print("Type (bénévole/animal) : ");
                    String typeParticipant = in.readLine();
                    g.inscrireParticipant(idAct, idCreneau, idParticipant, typeParticipant);
                    break;

                case 8:
                    System.out.print("ID créneau : ");
                    int idCreneauAff = Integer.parseInt(in.readLine());
                    g.afficherParticipant(idCreneauAff);
                    break;

                case 9:
                    System.out.print("ID animal : ");
                    int idAnimalSupp = Integer.parseInt(in.readLine());
                    System.out.print("ID box : ");
                    int idBoxSupp = Integer.parseInt(in.readLine());
                    System.out.print("Date de fin (YYYY-MM-DD) : ");
                    Date dateFSupp = Date.valueOf(in.readLine());
                    g.suppAnimal(idAnimalSupp, idBoxSupp, dateFSupp);
                    break;
                case 0:
                    System.out.println("Retour au menu principal");
                    break;

                default:
                    System.out.println("Choix invalide");
            }
        }
    }
    public static void menuClient(Gestion g, BufferedReader in) throws Exception {

        int choix = -1;

        while (choix != 0) {
            System.out.println("\n=== MODE CLIENT ===");
            System.out.println("1. Chercher un animal");
            System.out.println("2. Historique d’un animal");
            System.out.println("3. Chercher une famille d’accueil");
            System.out.println("4. Chercher un bénévole");
            System.out.println("5. Mettre à jour le statut d'un animal");
            System.out.println("0. Retour menu principal");
            System.out.print("> ");

            choix = Integer.parseInt(in.readLine());

            switch (choix) {

                case 1:
                    System.out.print("Critère (ex: race='chien') : ");
                    g.chercheAnimal(in.readLine());
                    break;

                case 2:
                    System.out.print("ID animal : ");
                    g.historiqueAnimal(Integer.parseInt(in.readLine()));
                    break;

                case 3:
                    System.out.print("Critère famille : ");
                    g.chercheFamille(in.readLine());
                    break;
                
                case 4:
                	System.out.println("Critère bénévole :");
                    g.chercheBenevole(in.readLine());
                    break;

                case 5:
                    System.out.print("ID animal : ");
                    int idAnimalStatut = Integer.parseInt(in.readLine());
                    System.out.print("Nouveau statut : ");
                    String nouveauStatut = in.readLine();
                    g.updateStatutAnimal(idAnimalStatut, nouveauStatut);
                    break;
                    
                case 0:
                    System.out.println("Retour au menu principal");
                    break;

                default:
                    System.out.println("Choix invalide");
            }
        }
    }


}
