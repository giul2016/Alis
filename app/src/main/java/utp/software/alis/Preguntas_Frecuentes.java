package utp.software.alis;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class Preguntas_Frecuentes extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.faq_list);

		// Declaraci�n del listview
		ListView list = getListView();

		// Cancelar click en listview
		list.setOnItemClickListener(null);

	}

	// M�todo que conecta con el bot�n �Todav�a con Dudas?
	public void Dudas(View view) {
		
		// Llamada a m�todo
		AclararDudas();

	}

	// M�todo que muestra cuadro de di�logo del bot�n �Todav�a con Dudas?
	private void AclararDudas() {

		// Declaraci�n del cuadro de di�logo
		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
		dialogBuilder.setTitle("Env�anos Tus Dudas");// T�tulo del cuadro de di�logo
		dialogBuilder.setIcon(R.drawable.question);// Icono del cuadro de
													// di�logo
		// Contenido texto del cuadro de di�logo
		dialogBuilder.setMessage("Si todav�a no has podido resolver tu problema,"
						+ " envianos tus dudas y te las responderemos lo"
						+ " antes posible.\n\nSi tienes suerte, puede que tu"
						+ " pregunta aparezca aqu� y ayudes a otros usuarios. ");
		// Bot�n cancelar del cuadro de di�logo
		dialogBuilder.setNegativeButton("Cancelar", null);
		// Bot�n Enviar Duda del cuadro de di�logo
		dialogBuilder.setPositiveButton("Enviar Duda",
				new DialogInterface.OnClickListener() {

					
					// M�todo conectado con el bot�n Enviar Duda del cuadro de
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
								getResources().getString(
										R.string.email_subject2));
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

}
