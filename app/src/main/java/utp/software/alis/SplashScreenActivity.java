package utp.software.alis;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;

public class SplashScreenActivity extends Activity {

	SharedPreferences prefs = null;
	// Poner la duracion del splash screen
	private static final long SPLASH_SCREEN_DELAY = 2000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Set portrait orientation
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		// Hide title bar
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.splash_screen);
		prefs=getSharedPreferences("utp.software.alis",Context.MODE_PRIVATE);

		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				
				 if (prefs.getBoolean("firstrun", true)) {
			    	   Intent i = new Intent().setClass(
								SplashScreenActivity.this, Registro.class);
			    	// String ctrl al cual se le asigna a y se pone como extra al Intent
			    	   i.putExtra("ctrl", "a");
						startActivity(i);

			            prefs.edit().putBoolean("firstrun", false).commit();
			            
			         // Cerrar la Actividad para que el usuario no vuela a
						// esta actividad presionando el boton de back
			            finish();
			        }else{

				// Iniciar la siguiente actividad
				Intent mainIntent = new Intent().setClass(
						SplashScreenActivity.this, MainActivity.class);
				startActivity(mainIntent);

				// Cerrar la Actividad para que el usuario no vuela a
				// esta actividad presionando el boton de back
				finish();
				}
			}
		};

		// Simular un proceso de loading largo al inicio de la aplicación
		Timer timer = new Timer();
		timer.schedule(task, SPLASH_SCREEN_DELAY);
	}

}
