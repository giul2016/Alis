package utp.software.alis;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

	}

	// Método que conecta con el botón productos
	public void lanzar(View view) {
		// Iniciar actividad lista_general la cual organiza los productos
		Intent i = new Intent(this, Lista_general.class);
		startActivity(i);
		finish();
	}

	// Método que conecta con el botón sub-listas
	public void lista_c(View view) {

		// Iniciar actividad Lista_Compras la cual contiene las listas
		Intent i = new Intent(this, Lista_Compras.class);
		startActivity(i);

	}

	// Método que conecta con el botón configuración
	public void config(View view) {
		// Iniciar actividad Configuración en la cual se hacen configuraciones
		// generales
		Intent i = new Intent(this, Configuracion.class);
		startActivity(i);

	}

	// Método que conecta con el botón contactenos
	public void contactenos(View view) {
		// Llamada a método
		mostrarDialogo();

	}

	// Método que muestra cuadro de diálogo del botón contactenos
	private void mostrarDialogo() {

		// Declaración del cuadro de diálogo
		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
		dialogBuilder.setTitle("Saludos");// Título del cuadro de diálogo
		dialogBuilder.setIcon(R.drawable.ic_launcher);// Icono del cuadro de
														// diálogo
		// Contenido texto del cuadro de diálogo
		dialogBuilder.setMessage("Aplicación desarrollada por estudiantes de"
				+ " la Universidad Tecnológica De Panamá. "
				+ " Para comentarios puedes enviarnos un correo"
				+ " con tus opiniones, solo tienes que dar click"
				+ " al botón Dar Opinión. ");
		// Botón cancelar del cuadro de diálogo
		dialogBuilder.setNegativeButton("Cancelar", null);
		// Botón Dar Opinion del cuadro de diálogo
		dialogBuilder.setPositiveButton("Dar Opinion",
				new DialogInterface.OnClickListener() {

					// Método conectado con el botón dar opinión del cuadro de
					// diálogo
					@Override
					public void onClick(DialogInterface dialog, int which) {

						// Intent con la acción de enviar mensaje
						Intent emailIntent = new Intent(
								android.content.Intent.ACTION_SEND);
						// Destinatario del mensaje
						String aEmailList[] = { getResources().getString(
								R.string.email_address) };
						// Poner destinatario en el mensaje
						emailIntent.putExtra(
								android.content.Intent.EXTRA_EMAIL, aEmailList);
						// Poner asunto del mensaje
						emailIntent.putExtra(
								android.content.Intent.EXTRA_SUBJECT,
								getResources()
										.getString(R.string.email_subject));
						// Tipo de mensaje, en esté caso de tipo simple texto
						emailIntent.setType("plain/text");
						startActivity(emailIntent);

					}
				});

		// Creación del cuadro de diálogo
		AlertDialog alertDialog = dialogBuilder.create();
		// Mostrar cuadro de diálogo
		alertDialog.show();

	}

	// Método que controla cuando el usuario presiona el botón back
	public void onBackPressed() {

		// Intent que envia al usuario al desktop del celular
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		startActivity(intent);

	}

}
