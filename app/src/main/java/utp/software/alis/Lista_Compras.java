package utp.software.alis;


import java.text.SimpleDateFormat;

import java.util.Date;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

import android.widget.EditText;
import android.widget.ListView;


public class Lista_Compras extends ListActivity {
	Cursor c;
	ListView lista;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lista_compras);

		// Declaraci�n del listview
		lista = getListView();
		// poner mensaje cuendo el listview esta vacio
		lista.setEmptyView(findViewById(R.id.emptyList));
		
		
	
		
		AdminSQLiteOpenHelper dbhelper = new AdminSQLiteOpenHelper(
				getBaseContext());
		SQLiteDatabase db = dbhelper.getReadableDatabase();
		String columns[] = new String[] { "_id","list_name","fecha_ingreso"};
		
	 c = db.query("LISTA", columns,null,
				null, null, null, null);
		
		c.moveToFirst();
		// array para el SimpleCursorAdapter
		String from[] = new String[] { "list_name","fecha_ingreso" };
	
		
		int to[] = new int[] { R.id.name,R.id.cate };

		// Adaptador
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(getBaseContext(),
				R.layout.listas, c, from, to,
				SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

		setListAdapter(adapter);


		// Cerramos la base de datos
		db.close(); 
		
		
		
		lista.setLongClickable(true);
		lista.setOnItemLongClickListener(new OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                    int pos, long id) {
              
            	final int a= (int)id;
            	AdminSQLiteOpenHelper dbhelper = new AdminSQLiteOpenHelper(
						getBaseContext());
				SQLiteDatabase db = dbhelper.getReadableDatabase();
				
				//String x []= new String []{String.valueOf(arg3)};
				String query = String.format("SELECT list_name FROM LISTA WHERE _id=%d", a);
				Cursor c = db.rawQuery(query,null);
				  c.moveToFirst();
				  String n = new String (""+c.getString(0));
				  c.close();
					db.close();
            	jas(arg1,a,n);
            

            	
            	
                return true;
            }
        }); 
		
		// Listener del listview
		lista.setOnItemClickListener(new OnItemClickListener() {

			// M�todo que controla el click en los elementos de la lista
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				AdminSQLiteOpenHelper dbhelper = new AdminSQLiteOpenHelper(
						getBaseContext());
				SQLiteDatabase db = dbhelper.getReadableDatabase();
				
				//String x []= new String []{String.valueOf(arg3)};
				String query = String.format("SELECT * FROM LISTA WHERE _id=%d", arg3);
				Cursor c = db.rawQuery(query,null);
				  c.moveToFirst();
				Intent i = new Intent(Lista_Compras.this, Sub_list.class);
				// String num al cual se le asigna n y se pone como extra al Intent
				i.putExtra("dni", String.valueOf(arg3));
				i.putExtra("nombre", c.getString(2));
				startActivity(i);
				c.close();
				db.close();

			}
		});

	}

	
	protected void onDestroy() {
	    super.onDestroy();
	    c.close();
	  }
public void onBackPressed() {
		
		Intent i = new Intent(Lista_Compras.this, MainActivity.class);
		startActivity(i);
		

	}

public void agregar(View view){
	

	// Declaraci�n del cuadro de di�logo
	AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
	dialogBuilder.setTitle("Agregar Lista de Compras");// T�tulo del cuadro de di�logo
	dialogBuilder.setIcon(R.drawable.nuevalista);// Icono del cuadro de
													// di�logo
	// Contenido texto del cuadro de di�logo
	dialogBuilder.setMessage("Ingrese el nombre de la lista:");
	
	final EditText input = new EditText(this);
	dialogBuilder.setView(input);
	
	// Bot�n cancelar del cuadro de di�logo
	dialogBuilder.setNegativeButton("Cancelar", null);
	// Bot�n Dar Opinion del cuadro de di�logo
	dialogBuilder.setPositiveButton("Aceptar",
			new DialogInterface.OnClickListener() {

				// M�todo conectado con el bot�n dar opini�n del cuadro de
				// di�logo
				@Override
				public void onClick(DialogInterface dialog, int which) {

					String srt = input.getEditableText().toString();
					if(srt.length()>0)
					{
						AdminSQLiteOpenHelper dbhelper = new AdminSQLiteOpenHelper(
								getBaseContext());
						SQLiteDatabase db = dbhelper.getWritableDatabase();
						ContentValues registro = new ContentValues();
						Date cDate = new Date();
						 String fDate = new SimpleDateFormat("dd-MM-yyyy").format(cDate);
						registro.put("list_name", srt);
						registro.put("fecha_ingreso", fDate);
						db.insert("LISTA", null, registro);
						db.close();
						finish();
						startActivity(getIntent());
					}

				}
			});

	// Creaci�n del cuadro de di�logo
	AlertDialog alertDialog = dialogBuilder.create();
	// Mostrar cuadro de di�logo
	alertDialog.show();	
}

public void jas(View v, final int a,String n)
{// Declaraci�n del cuadro de di�logo
	AlertDialog.Builder dialog = new AlertDialog.Builder(this);
	dialog.setTitle("Eliminar lista de compras");// T�tulo del cuadro de di�logo
	dialog.setIcon(R.drawable.excla);
	// Contenido texto del cuadro de di�logo
	dialog.setMessage("�Desea eliminar la lista '"+n+"'?");

	
	// Bot�n cancelar del cuadro de di�logo
	dialog.setNegativeButton("Cancelar", null);
	// Bot�n Dar Opinion del cuadro de di�logo
	dialog.setPositiveButton("Aceptar",
			new DialogInterface.OnClickListener() {

				// M�todo conectado con el bot�n dar opini�n del cuadro de
				// di�logo
				@Override
				public void onClick(DialogInterface dialog, int which) {

				
						AdminSQLiteOpenHelper dbhelper = new AdminSQLiteOpenHelper(
								getBaseContext());
						SQLiteDatabase db = dbhelper.getWritableDatabase();
						
					
						db.delete("LISTA", "_id=" +(int)a, null);
						db.close();
						finish();
						startActivity(getIntent());
					

				}
			});

	// Creaci�n del cuadro de di�logo
	AlertDialog alertDialog = dialog.create();
	// Mostrar cuadro de di�logo
	alertDialog.show();	}
}
