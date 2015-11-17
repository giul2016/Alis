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

	// M�todo que conecta con el bot�n productos
	public void lanzar(View view) {
		// Iniciar actividad lista_general la cual organiza los productos
		Intent i = new Intent(this, Lista_general.class);
		startActivity(i);
		finish();
	}

	// M�todo que conecta con el bot�n sub-listas
	public void lista_c(View view) {

		// Iniciar actividad Lista_Compras la cual contiene las listas
		Intent i = new Intent(this, Lista_Compras.class);
		startActivity(i);

	}

	// M�todo que conecta con el bot�n configuraci�n
	public void config(View view) {
		// Iniciar actividad Configuraci�n en la cual se hacen configuraciones
		// generales
		Intent i = new Intent(this, Configuracion.class);
		startActivity(i);

	}

	// M�todo que conecta con el bot�n contactenos
	public void contactenos(View view) {
		// Llamada a m�todo
		mostrarDialogo();

	}

	// M�todo que muestra cuadro de di�logo del bot�n contactenos
	private void mostrarDialogo() {

		// Declaraci�n del cuadro de di�logo
		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
		dialogBuilder.setTitle("Saludos");// T�tulo del cuadro de di�logo
		dialogBuilder.setIcon(R.drawable.ic_launcher);// Icono del cuadro de
														// di�logo
		// Contenido texto del cuadro de di�logo
		dialogBuilder.setMessage("Aplicaci�n desarrollada por estudiantes de"
				+ " la Universidad Tecnol�gica De Panam�. "
				+ " Para comentarios puedes enviarnos un correo"
				+ " con tus opiniones, solo tienes que dar click"
				+ " al bot�n Dar Opini�n. ");
		// Bot�n cancelar del cuadro de di�logo
		dialogBuilder.setNegativeButton("Cancelar", null);
		// Bot�n Dar Opinion del cuadro de di�logo
		dialogBuilder.setPositiveButton("Dar Opinion",
				new DialogInterface.OnClickListener() {

					// M�todo conectado con el bot�n dar opini�n del cuadro de
					// di�logo
					@Override
					public void onClick(DialogInterface dialog, int which) {

						// Intent con la acci�n de enviar mensaje
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
						// Tipo de mensaje, en est� caso de tipo simple texto
						emailIntent.setType("plain/text");
						startActivity(emailIntent);

					}
				});

		// Creaci�n del cuadro de di�logo
		AlertDialog alertDialog = dialogBuilder.create();
		// Mostrar cuadro de di�logo
		alertDialog.show();

	}

	// M�todo que controla cuando el usuario presiona el bot�n back
	public void onBackPressed() {

		// Intent que envia al usuario al desktop del celular
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		startActivity(intent);

	}

}
