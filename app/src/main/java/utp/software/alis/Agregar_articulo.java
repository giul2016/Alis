package utp.software.alis;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Agregar_articulo extends Activity implements
		OnItemSelectedListener {
	private EditText et1, et3,pr1,pr2,su1,su2,su3;
	float control = 0;
	Bundle bundle;
	Spinner spinner;
	String cat;
	int position;
	String x;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_art);

		// Declaración de nombre y precio
		et1 = (EditText) findViewById(R.id.editText1);
		et3 = (EditText) findViewById(R.id.editText3);
		pr1 = (EditText) findViewById(R.id.EditText02);
		pr2 = (EditText) findViewById(R.id.EditText04);
		su1 = (EditText) findViewById(R.id.EditText01);
		su2 = (EditText) findViewById(R.id.EditText03);
		su3 = (EditText) findViewById(R.id.EditText05);
		// Declaración del spinner
		spinner = (Spinner) findViewById(R.id.planets_spinner);

		/*// Adaptador del spinner
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, categorias);

		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);*/

		// Listener del spinner
		spinner.setOnItemSelectedListener(this);
		 
		// Loading spinner data from database
		loadSpinnerData();

	/*	// Apply the adapter to the spinner
		spinner.setAdapter(adapter);*/

		// Adquirir Información extra del Intent
		bundle = getIntent().getExtras();
		x = new String(bundle.getString("num"));

		
		// Condicional que controla cuando el usuario viene de hacer click en el producto
		if (x.equals("y")||x.equals("s")) {
			String dni = new String(bundle.getString("id"));
			// Abrir base de datos
			AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(
					getBaseContext());
			SQLiteDatabase bd = admin.getWritableDatabase();
			
			// query de la base de datos almacenado en un cursor
			Cursor fila = bd.rawQuery(
					"select nombre,categoryid, precio,precio1,precio2,super,super1,super2  from PRODUCTO where _id="
							+ dni, null);
			
			if (fila.moveToFirst()) {
				// Asignar al campo nombre el valor que se busco en la base de datos
				et1.setText(fila.getString(0));
				
				int i = fila.getInt(1);
				i--;
						spinner.setSelection(i);
				
				// Asignar al campo precio el valor que se busco en la base de datos
				et3.setText(fila.getString(2));
				pr1.setText(fila.getString(3));
				pr2.setText(fila.getString(4));
				su1.setText(fila.getString(5));
				su2.setText(fila.getString(6));
				su3.setText(fila.getString(7));

			} else {
				Toast.makeText(this, ""+dni,
						Toast.LENGTH_SHORT).show();
			}
			// Cerrar base de datos
			fila.close();
			bd.close();
			
		} else {
			// Controlador de la visibilidad del botón eliminar
			Button bt = (Button) findViewById(R.id.button1);
			bt.setEnabled(false);
		}

	}
	
	
	// Método que conecta con el botón aceptar
	public void aceptar(View v) {

		float precio,precio1,precio2;
		String super1 = null,super2=null,super3=null;
	
		
		// Abrir base de datos
		AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(
				getBaseContext());
		SQLiteDatabase bd = admin.getWritableDatabase();

		String nombre = et1.getText().toString();
		super1 = su1.getText().toString();
		super2 = su2.getText().toString();
		super3= su3.getText().toString();
		// Condicional que controla cuando el precio es null
		if ((et3.getText().toString()).equals("")) {
			precio = control;
		} else {
			precio = Float.valueOf(et3.getText().toString());
		}
		if ((pr1.getText().toString()).equals("")) {
			precio1 = control;
		} else {
			precio1 = Float.valueOf(pr1.getText().toString());
		}
		if ((pr2.getText().toString()).equals("")) {
			precio2 = control;
		} else {
			precio2 = Float.valueOf(pr2.getText().toString());
		}
		
		
		// Condicional que controla cuando el nombre es null
		if (nombre.equals("")) {
			
			et1.setError("Por favor ponga Nombre ");
			
		} else {

			
			bundle = getIntent().getExtras();
		x = new String(bundle.getString("num"));
			
			// Declaracion de contenedor
			ContentValues registro = new ContentValues();
			registro.put("nombre", nombre);
			//registro.put("categoria", cat);
			registro.put("categoryid", position);
			
			registro.put("precio", precio);
			registro.put("precio1", precio1);
			registro.put("precio2", precio2);
			registro.put("super", super1);
			registro.put("super1", super2);
			registro.put("super2", super3);
			
			// Condicional que controla cuando el usuario viene de hacer click en el producto
			if (x.equals("y")||x.equals("s")) {
				int dni = Integer.valueOf(bundle.getString("id"));
				// Actualizar base de datos
				bd.update("producto", registro, "_id=" + dni, null);
				// Mensaje toast
				Toast.makeText(this, "Se modificaron los datos del producto",
						Toast.LENGTH_SHORT).show();
			}
			// Condicional que controla cuando se agrega producto a la lista
			else {
				// Insertar en la base de datos
				bd.insert("producto", null, registro);
				// Mensaje toast
				Toast.makeText(this, "Se agregó el producto ",
						Toast.LENGTH_SHORT).show();
			}
			// Cerrar base de datos
			bd.close();
			// Intent que inicia actividad Lista_general
			if(x.equals("y")||x.equals("n"))
			{// Iniciar actividad Lista_general
			startActivity(new Intent(Agregar_articulo.this, Lista_general.class));
			finish();}
			else {Intent i = new Intent(Agregar_articulo.this,
					Sub_list.class);
				i.putExtra("dni", bundle.getString("dni"));
			// String num al cual se le asigna el valor de y
			i.putExtra("num", "s");
			i.putExtra("nombre", bundle.getString("nombre"));
			startActivity(i);
			finish();}
		}
		
	}

	// Método que conecta con el botón eliminar
	public void Eliminar(View view) {

		// Declaración del cuadro de diálogo
		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
		dialogBuilder.setTitle("Advertencia");// Título del cuadro de diálogo
		dialogBuilder.setIcon(R.drawable.excla);// Icono del cuadro de
												// diálogo
		// Contenido texto del cuadro de diálogo
		dialogBuilder.setMessage("Está a punto de eliminar el producto."
				+ " ¿Desea proceder? ");
		// Botón cancelar del cuadro de diálogo
		dialogBuilder.setNegativeButton("Cancelar", null);
		// Botón Aceptar del cuadro de diálogo
		dialogBuilder.setPositiveButton("Aceptar",
				new DialogInterface.OnClickListener() {

					// Método conectado con el botón aceptar del cuadro de
					// diálogo
					@Override
					public void onClick(DialogInterface dialog, int which) {

						// Abrir base de datos
						AdminSQLiteOpenHelper dbhelper = new AdminSQLiteOpenHelper(
								getBaseContext());
						SQLiteDatabase db = dbhelper.getWritableDatabase();
						
						// Contenido extra del intent
						bundle = getIntent().getExtras();
						int dni = Integer.valueOf(bundle.getString("id"));
						// Borrar elemento con id especifico de la base de datos
						db.delete("producto", "_id=" + dni, null);
						db.delete("POSEE", "pro_id=" + dni, null);
						
						// Pponer EditText en blanco
						et1.setText("");
						et3.setText("");
						// Llamada a método
						showtoast();
						
						// Iniciar actividad Lista_general
						if(x.equals("y")||x.equals("n"))
						{// Iniciar actividad Lista_general
						startActivity(new Intent(Agregar_articulo.this, Lista_general.class));
						finish();}
						else {Intent i = new Intent(Agregar_articulo.this,
								Sub_list.class);
							i.putExtra("dni", bundle.getString("dni"));
						// String num al cual se le asigna el valor de y
						i.putExtra("num", "s");
						i.putExtra("nombre", bundle.getString("nombre"));
						startActivity(i);
						finish();}
						// Cerrar base de datos
						db.close();
					}
				});

		// Creación del cuadro de diálogo
		AlertDialog alertDialog = dialogBuilder.create();
		// Mostrar cuadro de diálogo
		alertDialog.show();

	}

	
	
	 /**
     * Function to load the spinner data from SQLite database
     * */
    private void loadSpinnerData() {
        // database handler
        AdminSQLiteOpenHelper db = new AdminSQLiteOpenHelper(getApplicationContext());
 
        // Spinner Drop down elements
        List<String> lables = db.getAllLabels();
 
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, lables);
 
        // Drop down layout style - list view with radio button
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
 
        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
    }
	
	
	// Método que ayuda a trabajar con el elemento seleccionado del spinner
	@Override
	public void onItemSelected(AdapterView<?> parent, View arg1, int positio,
			long arg3) {
		
		position=((int) arg3)+1;
		
		//position=positio;
		
		//position= Integer.valueOf(position);
		
		//cat =parent.getItemAtPosition(position).toString();

		/*// Almacena la posicion del objeto en el spinner
		int posicion = spinner.getSelectedItemPosition();
		// Gracias a la posicion se busca en el arreglo cual es y se almacena en
		// una variable
		cat = categorias[posicion];*/

	}

	
	
	
	
	@Override
	public void onNothingSelected(AdapterView<?> arg0) {

	}

	// Método que controla cuando el usuario presiona el botón back
	@Override
	public void onBackPressed() {
		
		if(x.equals("y")||x.equals("n"))
		{// Iniciar actividad Lista_general
		startActivity(new Intent(Agregar_articulo.this, Lista_general.class));
		finish();}
		else {Intent i = new Intent(Agregar_articulo.this,
				Sub_list.class);
		
		 
		i.putExtra("dni", bundle.getString("dni"));
		// String num al cual se le asigna el valor de y
		i.putExtra("num", "s");
		i.putExtra("nombre", bundle.getString("nombre"));
		startActivity(i);
		finish();}
	}
	


	// Método que muestra mensaje toast
	public void showtoast() {
		// Mensaje toast
		Toast.makeText(this, "Se borró el producto exitosamente.",
				Toast.LENGTH_SHORT).show();
	}

}
