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
		ResultSet elt=base.executeQuery("SELECT *FROM " + table +" WHERE id="+((Produit)data).getId());
		
		if(elt.next()){
			double vieuxPrix=elt.getDouble("prix");
			String vieilleDescr=elt.getString("descr");
			double nvPrix=vieuxPrix+((Produit)data).getPrix();
			String nvDescr=vieilleDescr+" "+((Produit)data).getDescr();
		
			String requeteModif= "UPDATE "+table+" SET prix="+nvPrix+", descr='"+nvDescr+"'"+" WHERE id="+((Produit)data).getId();
			base.execute(requeteModif);
		}
		else {
			String insertRequete="INSERT INTO "+table+" VALUES "+data.getValues();
			base.execute(insertRequete);

		}
	}

}
