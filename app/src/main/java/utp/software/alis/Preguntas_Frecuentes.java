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

		// Declaración del listview
		ListView list = getListView();

		// Cancelar click en listview
		list.setOnItemClickListener(null);

	}

	// Método que conecta con el botón ¿Todavía con Dudas?
	public void Dudas(View view) {
		
		// Llamada a método
		AclararDudas();

	}

	// Método que muestra cuadro de diálogo del botón ¿Todavía con Dudas?
	private void AclararDudas() {

		// Declaración del cuadro de diálogo
		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
		dialogBuilder.setTitle("Envíanos Tus Dudas");// Título del cuadro de diálogo
		dialogBuilder.setIcon(R.drawable.question);// Icono del cuadro de
													// diálogo
		// Contenido texto del cuadro de diálogo
		dialogBuilder.setMessage("Si todavía no has podido resolver tu problema,"
						+ " envianos tus dudas y te las responderemos lo"
						+ " antes posible.\n\nSi tienes suerte, puede que tu"
						+ " pregunta aparezca aquí y ayudes a otros usuarios. ");
		// Botón cancelar del cuadro de diálogo
		dialogBuilder.setNegativeButton("Cancelar", null);
		// Botón Enviar Duda del cuadro de diálogo
		dialogBuilder.setPositiveButton("Enviar Duda",
				new DialogInterface.OnClickListener() {

					
					// Método conectado con el botón Enviar Duda del cuadro de
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
								getResources().getString(
										R.string.email_subject2));
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

}
