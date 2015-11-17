package utp.software.alis;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Categorias extends Activity implements OnItemSelectedListener {

	EditText et1;
	Spinner spin;
	int eliminar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.categorias);
	
	
		  // Spinner element
        spin = (Spinner) findViewById(R.id.spin);
	
		  // Spinner click listener
        spin.setOnItemSelectedListener(this);
 
        
        // new label input field
        et1 = (EditText) findViewById(R.id.et1);
        
        
        // Loading spinner data from database
        loadSpinnerData();
	
	}

	
	
	public void add(View view){
		  String label = et1.getText().toString();
		  
          if (label.trim().length() > 0) {
              // database handler
              AdminSQLiteOpenHelper db = new AdminSQLiteOpenHelper(
                      getApplicationContext());

              // inserting new label into database
              db.insertLabel(label);

              // making input filed text to blank
              et1.setText("");

              // Hiding the keyboard
              InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
              imm.hideSoftInputFromWindow(et1.getWindowToken(), 0);

              // loading spinner with newly added data
              loadSpinnerData();
              
              Intent returnIntent = new Intent();
              setResult(RESULT_OK, returnIntent);        
              finish();
          } else {
              Toast.makeText(getApplicationContext(), "Please enter label name",
                      Toast.LENGTH_SHORT).show();
          }
		
		
	}
	
	public void eliminate(View view){
		
		// Declaración del cuadro de diálogo
				AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
				dialogBuilder.setTitle("Advertencia");// Título del cuadro de diálogo
				dialogBuilder.setIcon(R.drawable.excla);// Icono del cuadro de
														// diálogo
				// Contenido texto del cuadro de diálogo
				dialogBuilder.setMessage("Está a punto de eliminar la categoria."
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
								SQLiteDatabase db = dbhelper.getReadableDatabase();
								
							
								// Borrar elemento con id especifico de la base de datos
								db.delete("CATEGORIAS", "_id="+eliminar, null);
								
								
								db.close();
								
								 Intent returnIntent = new Intent();
					             setResult(RESULT_OK, returnIntent);        
					             finish();
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
        spin.setAdapter(dataAdapter);
		// Listener del listview
		
		 
	}



	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		
	
		eliminar =arg2;
		eliminar++;
		String c= "TAG";
		Log.v(c,"ID="+eliminar);
	
	}



	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}

    
	



	
	}




