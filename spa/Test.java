package spa;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;

public class Test {

    public static void main(String[] args) {
        try {
            // Connexion à la base
            Connection conn = Connect.seConnecter();
            Gestion g = new Gestion(conn);

            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            String line;
            System.out.println("Client AMS - commandes possible : CREATE(, INSERT(int,int,varchar,text,text,double), DISPLAY, REMOVE, STRUCT, DROP, QUIT");

            while (true) {
                System.out.print("> ");
                line = in.readLine();
                if (line == null) break;
                String cmd = line.trim().toUpperCase();

                if (cmd.equals("QUIT")) break;

                switch (cmd) {
                    case "CREATE":
                    	String creationSql =
                        "CREATE TABLE if not exists produit (" +
                        "id INT PRIMARY KEY," +
                        "lot INT," +
                        "nom VARCHAR(100)," +
                        "descr text," +
                        "cat text," +
                        "prix FLOAT8" +
                        ");";

                        g.execute(creationSql);
                        System.out.println("Table 'produit' créée !");
                        break;

                    case "INSERT":
                        // recup des reponse
                        System.out.print("id : ");
                        int id = Integer.parseInt(in.readLine());
                        System.out.print("lot : ");
                        int lot = Integer.parseInt(in.readLine());
                        System.out.print("nom : ");
                        String nom = in.readLine();
                        System.out.print("descr : ");
                        String descr = in.readLine();
                        System.out.print("cat : ");
                        String cat = in.readLine();
                        System.out.print("prix : ");
                        double prix = Double.parseDouble(in.readLine());
                        Produit p = new Produit(id, lot, nom, descr, cat, prix, "");
                        g.insert(p, "produit");
                        break;

                    case "DISPLAY":
                        g.displayTable("produit");
                        break;

                    case "REMOVE":
                        System.out.print("id à supprimer : ");
                        int idRem = Integer.parseInt(in.readLine());
                        g.execute("DELETE FROM produit WHERE id=" + idRem);
                        System.out.println("Produit supprimé si existant.");
                        break;

                    case "STRUCT":
                        g.structTable("produit", true);
                        break;

                    case "DROP":
                        System.out.print("Confirmer la suppression de la table produit ? (oui/non) : ");
                        String rep = in.readLine();
                        if (rep.equalsIgnoreCase("oui")) {
                            g.execute("DROP TABLE IF EXISTS produit");
                            System.out.println("Table supprimée");
                        } else {
                            System.out.println("Suppression annulée.");
                        }
                        break;

                    default:
                        System.out.println("Commande inconnue");
                        break;
                }
            }

            conn.close();
            System.out.println("Fin du client AMS.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
