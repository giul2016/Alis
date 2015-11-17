package utp.software.alis;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Registro extends Activity {

    EditText iName;
    EditText iEmail;
    EditText iEmailV;
    EditText edt3;
    TextView edt,edt2;
    String name;
    String email;
    String email2;
    int x;
    Bundle bundle;
    String p;
    SharedPreferences prefs = null;
    Button bt,bt2;
   

   @Override
   protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.registro);  
       
       
   	// Adquirir Información extra del Intent
		bundle = getIntent().getExtras();
		p = new String(bundle.getString("ctrl"));
		
		// Condicional que controla cuando el usuario viene de hacer click en el producto
		if (p.equals("a")) {
			
			
		bt2 = (Button) findViewById(R.id.btnNext2);
		bt2.setVisibility(View.INVISIBLE);	
		
		edt = (TextView)findViewById(R.id.textView5);
		edt.setVisibility(TextView.INVISIBLE);
		
		}else
		{
		
		bt = (Button) findViewById(R.id.btnNext);
		bt.setVisibility(View.INVISIBLE);
		
		edt2 = (TextView)findViewById(R.id.textView4);
		edt2.setVisibility(TextView.INVISIBLE);
		
		iEmailV = (EditText)findViewById(R.id.txtVEmail);
		iEmailV.setVisibility(EditText.INVISIBLE);
		
		}
      	prefs=getSharedPreferences("utp.software.alis",Context.MODE_PRIVATE);
      
       iName = (EditText) findViewById(R.id.txtUserName);
       iEmail = (EditText) findViewById(R.id.txtEmail);
       iEmailV = (EditText) findViewById(R.id.txtVEmail);
       
       iName.setText(prefs.getString("nombre", ""));
      iEmail.setText(prefs.getString("correo",""));
      
      
      
      
     
      
   }
   
   public void verificar(View view)
   {
   	
   	 name = iName.getText().toString();
   	 email = iEmail.getText().toString();
     if(p.equals("a")) {  
   	 email2 = iEmailV.getText().toString();
     }
     else
    	email2 = iEmail.getText().toString();	 
    	 
   	if(name.equals(""))
		{iName.setError("Debe ingresar un nombre de usuario.");
//		Log.i(this.getClass().toString(),"ENTRO AL 1 ELSE");
		}
   	else 
   		if(email.equals(""))
   			{iEmail.setError("Debe ingresar un correo.");
   			//Log.i(this.getClass().toString(),"ENTRO AL 2 ELSE");
   			}
   		else 
   			if(email2.equals(""))
   				{iEmailV.setError("Debe ingresar un correo.");
   				//Log.i(this.getClass().toString(),"ENTRO AL 3 ELSE");
   				}
   			else if ((email.equals(email2))==false)
   				{
   				iEmailV.setError("El correo ingresado no coincide con el anterior.");
   				}
   			else{
   					Editor editor=prefs.edit();
   					editor.putString("nombre", name);
   					editor.putString("correo", email);
   					editor.commit();
   					
   					if (p.equals("a")) {
   					Toast.makeText(this, "Se ha registrado exitosamente.", Toast.LENGTH_SHORT).show();
   					}
   					else{
   					Toast.makeText(this, "Se guardaron los cambios.", Toast.LENGTH_SHORT).show();
   					}
   					Intent i = new Intent(this, MainActivity.class);
   					startActivity(i);
   					finish();
   			
   				}

      
   }
}
