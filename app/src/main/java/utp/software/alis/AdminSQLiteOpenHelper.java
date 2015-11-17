package utp.software.alis;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {

	private static int version = 1;
	private static String name = "Alis";
	private static CursorFactory factory = null;

	// Constructor de la base de datos
	public AdminSQLiteOpenHelper(Context context) {
		super(context, name, factory, version);

	}

	// Método que crea la base de datos
	@Override
	public void onCreate(SQLiteDatabase db) {

		db.execSQL("CREATE TABLE CATEGORIAS("
				+ " _id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "category TEXT NOT NULL)");
		
		
		db.execSQL("INSERT INTO CATEGORIAS(category) VALUES('Lacteos')");
		db.execSQL("INSERT INTO CATEGORIAS(category) VALUES('Frutas, Vegetales y Legumbres')");
		db.execSQL("INSERT INTO CATEGORIAS(category) VALUES('Articulos del hogar')");
		db.execSQL("INSERT INTO CATEGORIAS(category) VALUES('Ferreteria')");
		db.execSQL("INSERT INTO CATEGORIAS(category) VALUES('Granos y Cereales')");
		db.execSQL("INSERT INTO CATEGORIAS(category) VALUES('Carnes')");
		db.execSQL("INSERT INTO CATEGORIAS(category) VALUES('Embutidos')");
		db.execSQL("INSERT INTO CATEGORIAS(category) VALUES('Congelados')");
		db.execSQL("INSERT INTO CATEGORIAS(category) VALUES('Belleza y Aseo Personal')");
		db.execSQL("INSERT INTO CATEGORIAS(category) VALUES('Bebidas y Licores')");
		db.execSQL("INSERT INTO CATEGORIAS(category) VALUES('Articulos Varios')");
		db.execSQL("INSERT INTO CATEGORIAS(category) VALUES('Farmacia')");
		db.execSQL("INSERT INTO CATEGORIAS(category) VALUES('Prueba')");
		
	/*	// Creacion de la tabla producto
		db.execSQL("CREATE TABLE PRODUCTO("
				+ " _id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ " nombre TEXT NOT NULL, " + " categoria TEXT NOT NULL, "
				+ " precio FLOAT NULL)");*/
		
		db.execSQL("CREATE TABLE PRODUCTO("
				+ " _id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "categoryid INTEGER NOT NULL, "  
				+ "nombre TEXT NOT NULL, "
				+ "precio FLOAT NULL, "
				+ "precio1 FLOAT NULL, "
				+ "precio2 FLOAT NULL, "
				+ "super TEXT NULL, "
				+ "super1 TEXT NULL, "
				+ "super2 TEXT NULL, "
				+ "FOREIGN KEY(categoryid) REFERENCES CATEGORIAS(_id))");
		
		db.execSQL("CREATE TABLE LISTA("
				+ " _id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+"fecha_ingreso TEXT NOT NULL ,"
				+ "list_name TEXT NOT NULL)");
		
		db.execSQL("CREATE TABLE POSEE("
				+ "_id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "cate_id INTEGER NOT NULL, "  
				+ "pro_id INTEGER NOT NULL, "  
				+ "list_id INTEGER NOT NULL, "
				+ "checks INTEGER NOT NULL, "
				+ "FOREIGN KEY(cate_id) REFERENCES CATEGORIAS(_id),"
				+ "FOREIGN KEY(pro_id) REFERENCES PRODUCTO(_id),"
				+ "FOREIGN KEY(list_id) REFERENCES LISTA(id_list))");

	}

	// Método que actualiza la base de datos
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		// Eliminar tabla producto
		db.execSQL("drop table if exists PRODUCTO");
		// Crear tabla Producto
		db.execSQL("CREATE TABLE PRODUCTO("
				+ " _id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ " nombre TEXT NOT NULL, " + " categoria TEXT NOT NULL, "
				+ " precio FLOAT NULL)");
	}
	
	 /**
     * Inserting new lable into lables table
     * */
    public void insertLabel(String label){
        SQLiteDatabase db = this.getWritableDatabase();
         
        ContentValues values = new ContentValues();
        values.put("category", label);
          
        // Inserting Row
        db.insert("CATEGORIAS", null, values);
        db.close(); // Closing database connection
    }
	
	 /**
     * Getting all labels
     * returns list of labels
     * */
    public List<String> getAllLabels(){
        List<String> labels = new ArrayList<String>();
         
        // Select All Query
        String selectQuery = "SELECT  * FROM CATEGORIAS";
      
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
      
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                labels.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }
         
        // closing connection
        cursor.close();
        db.close();
         
        // returning lables
        return labels;}
    
public Cursor fet()
{
	SQLiteDatabase db = this.getWritableDatabase();

// array para el SimpleCursorAdapter
String columns[] = new String[] { "PRODUCTO._id", "nombre", "category","categoryid" };


String orderBy = "category";
String where = "categoryid = CATEGORIAS._id";

final Cursor  c = db.query("PRODUCTO, CATEGORIAS",columns, where,
null, null, null, orderBy);
c.moveToFirst();

db.close();
return c;}


}
