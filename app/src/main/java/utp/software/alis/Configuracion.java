package utp.software.alis;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Configuracion extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.configuracion);

	}

	// M�todo que conecta con el bot�n Configurar Cuenta
	public void email(View view) {
		// Intent que inicia a la actividad Registro
				Intent i = new Intent(this, Registro.class);
				// String ctrl al cual se le asigna a y se pone como extra al Intent
				i.putExtra("ctrl", "b");
				startActivity(i);
	}

	// M�todo que conecta con el bot�n preguntas frecuentes
	public void faq(View view) {

		// Intent que inicia a la actividad preguntas frecuentes
		Intent i = new Intent(this, Preguntas_Frecuentes.class);
		startActivity(i);

	}

	// M�todo que conecta con el bot�n borrar base de datos
	public void bd(View view) {

		// Llamada a m�todo
		borrar();

	}

	// M�todo que muestra cuadro de di�logo del bot�n borrar base de datos
	private void borrar() {

		// Declaraci�n del cuadro de di�logo
		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
		dialogBuilder.setTitle("Advertencia");// T�tulo del cuadro de di�logo
		dialogBuilder.setIcon(R.drawable.excla);// Icono del cuadro de
												// di�logo
		
		// Contenido texto del cuadro de di�logo
		dialogBuilder.setMessage("Al presionar Aceptar borrar� toda la informaci�n"
						+ " de los productos almacenados. �Desea continuar?");
		// Bot�n cancelar del cuadro de di�logo
		dialogBuilder.setNegativeButton("Cancelar", null);
		// Bot�n Aceptar del cuadro de di�logo
		dialogBuilder.setPositiveButton("Aceptar",
				new DialogInterface.OnClickListener() {

			
					// M�todo conectado con el bot�n Aceptar del cuadro de
					// di�logo
					@Override
					public void onClick(DialogInterface dialog, int which) {

						//Abrir base de datos
						AdminSQLiteOpenHelper dbhelper = new AdminSQLiteOpenHelper(
								getBaseContext());
						SQLiteDatabase db = dbhelper.getWritableDatabase();
						
						// Condicional que asegura que la base de datos exista
						if (db != null) {
							// Vaciar la tabla productos
							db.delete("PRODUCTO", null, null);
							//Llamada a m�todo
							showtoast();
						}
						// Cerrar base de datos
						db.close();

					}
				});
		
		// Creaci�n del cuadro de di�logo
		AlertDialog alertDialog = dialogBuilder.create();
		// Mostrar cuadro de di�logo
		alertDialog.show();

	}

	// M�todo que devuelve un mensaje toast
	public void showtoast() {
		
		// Mensaje toast
		Toast.makeText(this, "Se borr� la informaci�n exitosamente.",
				Toast.LENGTH_SHORT).show();
	}

}
