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

	// Método que conecta con el botón Configurar Cuenta
	public void email(View view) {
		// Intent que inicia a la actividad Registro
				Intent i = new Intent(this, Registro.class);
				// String ctrl al cual se le asigna a y se pone como extra al Intent
				i.putExtra("ctrl", "b");
				startActivity(i);
	}

	// Método que conecta con el botón preguntas frecuentes
	public void faq(View view) {

		// Intent que inicia a la actividad preguntas frecuentes
		Intent i = new Intent(this, Preguntas_Frecuentes.class);
		startActivity(i);

	}

	// Método que conecta con el botón borrar base de datos
	public void bd(View view) {

		// Llamada a método
		borrar();

	}

	// Método que muestra cuadro de diálogo del botón borrar base de datos
	private void borrar() {

		// Declaración del cuadro de diálogo
		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
		dialogBuilder.setTitle("Advertencia");// Título del cuadro de diálogo
		dialogBuilder.setIcon(R.drawable.excla);// Icono del cuadro de
												// diálogo
		
		// Contenido texto del cuadro de diálogo
		dialogBuilder.setMessage("Al presionar Aceptar borrará toda la información"
						+ " de los productos almacenados. ¿Desea continuar?");
		// Botón cancelar del cuadro de diálogo
		dialogBuilder.setNegativeButton("Cancelar", null);
		// Botón Aceptar del cuadro de diálogo
		dialogBuilder.setPositiveButton("Aceptar",
				new DialogInterface.OnClickListener() {

			
					// Método conectado con el botón Aceptar del cuadro de
					// diálogo
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
							//Llamada a método
							showtoast();
						}
						// Cerrar base de datos
						db.close();

					}
				});
		
		// Creación del cuadro de diálogo
		AlertDialog alertDialog = dialogBuilder.create();
		// Mostrar cuadro de diálogo
		alertDialog.show();

	}

	// Método que devuelve un mensaje toast
	public void showtoast() {
		
		// Mensaje toast
		Toast.makeText(this, "Se borró la información exitosamente.",
				Toast.LENGTH_SHORT).show();
	}

}
