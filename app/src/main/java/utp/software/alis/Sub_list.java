package utp.software.alis;



import java.text.DecimalFormat;
import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Sub_list extends ListActivity {
	Bundle bundle;
	Cursor c;
	ListView lista;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sub_list);
		
		bundle = getIntent().getExtras();
		 lista = getListView();
		lista.setEmptyView(findViewById(R.id.emptyList));
		
		// comandos para abrir base de datos
		AdminSQLiteOpenHelper dbhelper = new AdminSQLiteOpenHelper(
				getBaseContext());
		SQLiteDatabase db = dbhelper.getReadableDatabase();
		
		
		TextView	txt = (TextView) findViewById(R.id.text);
		txt.setText(bundle.getString("nombre"));
		// array para el SimpleCursorAdapter
		//String columns[] = new String[] { "LISTA._id ", "nombre", "category","cate_id","pro_id","list_id" };
		
		//String where = "cate_id= CATEGORIAS._id AND pro_id=PRODUCTO._id AND list_id= ";
		
		String query = String.format("SELECT PRODUCTO._id,pro_id,cate_id,list_id,nombre,precio,precio1,precio2,category,categoryid,checks  FROM POSEE,CATEGORIAS,PRODUCTO WHERE cate_id= CATEGORIAS._id AND pro_id=PRODUCTO._id AND list_id=%d", Integer.valueOf(bundle.getString("dni")));
	
		 c = db.rawQuery(query,null);	


				c.moveToFirst();
				
				String from[] = new String[] { "nombre","category","precio" };
				
				int to[] = new int[] { R.id.name, R.id.cate,R.id.textView1 };

				// Adaptador
				MyCursorAdapter adapter = new MyCursorAdapter(getBaseContext(),
						R.layout.produ_list, c, from, to,
						SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

			
				String qury = String.format("SELECT POSEE._id,precio,precio1,precio2,list_id,pro_id FROM POSEE,PRODUCTO WHERE pro_id=PRODUCTO._id AND list_id=%d", Integer.valueOf(bundle.getString("dni")));
				Cursor h =db.rawQuery(qury,null);
				
				float suma=0, total=0;
			
			
				 while(h.moveToNext())
               { if(h.getFloat(1)!=0 && h.getFloat(2)!=0 && h.getFloat(3)!=0)
			       {  suma = (h.getFloat(1)+h.getFloat(2)+h.getFloat(3))/3;}
			       else{if((h.getFloat(1)==0 && h.getFloat(2)!=0 && h.getFloat(3)!=0)||(h.getFloat(1)!=0 && h.getFloat(2)==0 && h.getFloat(3)!=0)||(h.getFloat(1)!=0 && h.getFloat(2)!=0 && h.getFloat(3)==0)) 
			    	   suma = (h.getFloat(1)+h.getFloat(2)+h.getFloat(3))/2; 
			       else{if((h.getFloat(1)==0 && h.getFloat(2)==0 && h.getFloat(3)!=0)||(h.getFloat(1)==0 && h.getFloat(2)!=0 && h.getFloat(3)==0)||(h.getFloat(1)!=0 && h.getFloat(2)==0 && h.getFloat(3)==0))
			    	   {suma = (h.getFloat(1)+h.getFloat(2)+h.getFloat(3));}}}
               total=total+suma; 
              }    
               
               TextView tot = (TextView) findViewById(R.id.textView2);
 			  DecimalFormat x= new DecimalFormat("0.00"); 
 			  tot.setText("$"+x.format(total));
				setListAdapter(adapter);
				
				
			h.close();	
		      db.close();
		      
				lista.setOnItemClickListener(new OnItemClickListener() {

					// Método que controla el click en los elementos de la lista
					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
							long arg3) {

						// Iniciar actividad Agregar articulo
						Intent i = new Intent(Sub_list.this,
								Agregar_articulo.class);
						
						 
						i.putExtra("id", (String.valueOf(arg3)));
						// String num al cual se le asigna el valor de y
						i.putExtra("num", "s");
						i.putExtra("nombre", bundle.getString("nombre"));
						i.putExtra("dni", bundle.getString("dni"));
						startActivity(i);
		              
					}
				});		
	}
	public void enviar(View view){
		
		
		// Declaración del cuadro de diálogo
		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
		dialogBuilder.setTitle("Enviar");// Título del cuadro de diálogo
		dialogBuilder.setIcon(R.drawable.ic_send2);// Icono del cuadro de
														// diálogo
		// Contenido texto del cuadro de diálogo
		dialogBuilder.setMessage("¿Desea enviar la lista de compras al correo electrónico?");
		// Botón cancelar del cuadro de diálogo
		dialogBuilder.setNegativeButton("Cancelar", null);
		// Botón Enviar del cuadro de diálogo
		dialogBuilder.setPositiveButton("Enviar",
				new DialogInterface.OnClickListener() {

					// Método conectado con el botón dar opinión del cuadro de
					// diálogo
					@Override
					public void onClick(DialogInterface dialog, int which) {

						
						
						ArrayList<String> listOfSomething = new ArrayList<String>();
						
						
						// Abrir base de datos
						AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(
								getBaseContext());
						SQLiteDatabase bd = admin.getWritableDatabase();

						for (int s = 0; s < lista.getCount(); s++) {
							
							
							String prueba = String.valueOf(lista.getItemIdAtPosition(s));
							
							// query de la base de datos almacenado en un cursor
							Cursor filas = bd.rawQuery("select nombre from PRODUCTO where _id="
											+ prueba, null);
							
							
							filas.moveToFirst();
							
							String prueba2 = filas.getString(0);
							
							
							listOfSomething.add(prueba2);
							filas.close();
						
						}
				
						StringBuilder sb = new StringBuilder(); 
						for (String s : listOfSomething) {
								sb.append(s);
								sb.append("\n");
							}
					
					
					Intent intent = new Intent(Intent.ACTION_SEND); 
			         intent.setType("plain/text");
			         //intent.putExtra(Intent.EXTRA_EMAIL, "frederick.sanson@gmail.com");  
			         intent.putExtra(Intent.EXTRA_SUBJECT, "Lista De Compras");
			        intent.putExtra(Intent.EXTRA_TEXT, "Lista De Compras: \n\n"+sb.toString());
			         startActivity(intent);
					
					
					// Cerrar base de datos
					bd.close();
							

					}
				});

		// Creación del cuadro de diálogo
		AlertDialog alertDialog = dialogBuilder.create();
		// Mostrar cuadro de diálogo
		alertDialog.show();

			
		}
	public void onBackPressed() {
		
		Intent i = new Intent(Sub_list.this, Lista_Compras.class);
		startActivity(i);
		

	}
	public void lanzar(View view) {
		
		// Intent que iniciar actividad agregar articulo
		Intent i = new Intent(Sub_list.this, select_art.class);
		// String num al cual se le asigna n y se pone como extra al Intent
		i.putExtra("id", bundle.getString("dni"));
		i.putExtra("nombre", bundle.getString("nombre"));
		startActivity(i);
		
	}
public void borrar(View view) {
		
	AdminSQLiteOpenHelper dbhelper = new AdminSQLiteOpenHelper(
			getBaseContext());
	SQLiteDatabase db = dbhelper.getWritableDatabase();
	
String query= String.format("select * from POSEE where list_id=%d",Integer.valueOf(bundle.getString("dni")));
Cursor h =db.rawQuery(query,null);
String TAG="Chelly";
while(h.moveToNext())
{Log.v(TAG,""+h.getInt(4));
	if(h.getInt(4)==1)
	{db.delete("POSEE","_id="+h.getInt(0), null);
	
	Log.v(TAG,"Entró Putamadre");}}
h.close();
db.close();	
finish();
startActivity(getIntent());
	
		
	}
	protected void onDestroy() {
	    super.onDestroy();
	    c.close();
	  }
	private class MyCursorAdapter extends SimpleCursorAdapter{
		private final Context mContext;
		private final int mLayout;
		private final Cursor mCursor;
		private final int mNameIndex;
		private  int mImaIndex;
		private final int mPreIndex;
		private final int mPre1Index;
		private final int mPre2Index;
		private final int mCateIndex;
		private final int mPoseIndex;
		private final int mCheckIndex;
		
	
		private final LayoutInflater mLayoutInflater;
		
		  public MyCursorAdapter(Context context, int layout, Cursor c,
		    String[] from, int[] to, int flags) {
		   super(context, layout, c, from, to, flags);
		   this.mContext = context;
		    this.mLayout = layout;
		    this.mCursor = c;
		    this.mPre2Index = mCursor.getColumnIndex("precio2");
		    this.mPre1Index = mCursor.getColumnIndex("precio1");
		    this.mPreIndex = mCursor.getColumnIndex("precio");
		    this.mImaIndex = mCursor.getColumnIndex("categoryid");
		    this.mCateIndex = mCursor.getColumnIndex("category");
		    this.mNameIndex = mCursor.getColumnIndex("nombre");
		    this.mPoseIndex = mCursor.getColumnIndex("_id");
		    this.mCheckIndex = mCursor.getColumnIndex("checks");
		    this.mLayoutInflater = LayoutInflater.from(mContext);
		  }  
		  private final class ViewHolder {
			    public TextView name,precio;
			    public TextView cate;
			    public ImageView image;
			   public CheckBox check;
			}
		 
		  public View getView(int position, View convertView, ViewGroup parent) {  
			  
		        //View view = super.getView(position, convertView, parent);
			  float suma = 0;
			  if (mCursor.moveToPosition(position)) {
				  final ViewHolder viewHolder;
			        if (convertView == null) {
			            convertView = mLayoutInflater.inflate(mLayout, null);
			            
			            viewHolder = new ViewHolder();
			            viewHolder.cate = (TextView) convertView.findViewById(R.id.cate);
			            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
			            viewHolder.precio = (TextView) convertView.findViewById(R.id.textView1);
			            viewHolder.image = (ImageView) convertView.findViewById(R.id.icon);
			            viewHolder.check = (CheckBox) convertView.findViewById(R.id.checkBox1);

			            convertView.setTag(viewHolder);
			           final View h = convertView;
			            viewHolder.check.setOnClickListener( new View.OnClickListener() {  
					     public void onClick(View v) {  
					      CheckBox cb = (CheckBox) v ; 
					      AdminSQLiteOpenHelper dbhelper = new AdminSQLiteOpenHelper(
									getBaseContext());
							SQLiteDatabase db = dbhelper.getReadableDatabase();
							ContentValues registro = new ContentValues();
							  int p= mCursor.getInt(mPoseIndex);
					      if(cb.isChecked())
					      {viewHolder.name.setPaintFlags(  viewHolder.name.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG );
					     registro.put("checks", 1);h.setBackgroundColor(Color.rgb(238, 233, 233));
					      db.update("POSEE", registro, "_id=" + p, null);}
					      else{viewHolder.name.setPaintFlags(  viewHolder.name.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
					      registro.put("checks", 0);h.setBackgroundColor(Color.rgb(255, 255, 255));
					      db.update("POSEE", registro, "_id=" + p, null);}
					      db.close();
					     }  
					     			     
					     
					    });
			        }
			        else {
			            viewHolder = (ViewHolder) convertView.getTag();
			        }
			        String cate = mCursor.getString(mCateIndex);
			        String name = mCursor.getString(mNameIndex);
			        Boolean check = (mCursor.getInt(mCheckIndex) == 1)? true : false;
			      
			       int d= mCursor.getInt(mImaIndex);
			       float e= mCursor.getFloat(mPreIndex);
			       float f= mCursor.getFloat(mPre1Index);
			       float g= mCursor.getFloat(mPre2Index);
			 
			       if(e!=0 && g!=0 && f!=0)
			       {  suma = (e+f+g)/3;}
			       else{if((e==0 && g!=0 && f!=0)||(e!=0 && g==0 && f!=0)||(e!=0 && g!=0 && f==0)) 
			    	   suma = (e+f+g)/2; 
			       else{if((e==0 && g==0 && f!=0)||(e==0 && g!=0 && f==0)||(e!=0 && g==0 && f==0))
			    	   {suma = (e+f+g);}}}
			    
			        DecimalFormat x= new DecimalFormat("0.00");
			       
			        viewHolder.cate.setText(cate);
			        viewHolder.name.setText(name);
			        viewHolder.check.setChecked(check);
			        viewHolder.precio.setText("$"+x.format(suma));
			        
			        AdminSQLiteOpenHelper dbhelper = new AdminSQLiteOpenHelper(
							getBaseContext());
					SQLiteDatabase db = dbhelper.getReadableDatabase();
					ContentValues registro = new ContentValues();
					  int p= mCursor.getInt(mPoseIndex);
			      
					  if(viewHolder.check.isChecked())
			      {viewHolder.name.setPaintFlags(  viewHolder.name.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG );
			     registro.put("checks", 1);convertView.setBackgroundColor(Color.rgb(238, 233, 233));
			      db.update("POSEE", registro, "_id=" + p, null);}
			      else{viewHolder.name.setPaintFlags(  viewHolder.name.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
			      registro.put("checks", 0);convertView.setBackgroundColor(Color.rgb(255, 255, 255));
			      db.update("POSEE", registro, "_id=" + p, null);}
			      db.close();
			  
			        switch(d)
			        {
			        case 1: viewHolder.image.setImageDrawable(getResources().getDrawable(R.drawable.ic_lacteos));break;
			        case 2: viewHolder.image.setImageDrawable(getResources().getDrawable(R.drawable.ic_fruta));break;
			        case 3: viewHolder.image.setImageDrawable(getResources().getDrawable(R.drawable.ic_hogar));break;
			        case 4: viewHolder.image.setImageDrawable(getResources().getDrawable(R.drawable.ic_ferreteria));break;
			        case 5: viewHolder.image.setImageDrawable(getResources().getDrawable(R.drawable.ic_granos));break;
			        case 6: viewHolder.image.setImageDrawable(getResources().getDrawable(R.drawable.ic_carnes));break;
			        case 7: viewHolder.image.setImageDrawable(getResources().getDrawable(R.drawable.ic_embutidos));break;
			        case 8: viewHolder.image.setImageDrawable(getResources().getDrawable(R.drawable.ic_congelados));break;
			        case 9: viewHolder.image.setImageDrawable(getResources().getDrawable(R.drawable.ic_belleza));break;
			        case 10: viewHolder.image.setImageDrawable(getResources().getDrawable(R.drawable.ic_bebidas));break;
			        case 11: viewHolder.image.setImageDrawable(getResources().getDrawable(R.drawable.ic_varios));break;
			        case 12: viewHolder.image.setImageDrawable(getResources().getDrawable(R.drawable.ic_farmacia));break;
			        default : viewHolder.image.setImageDrawable( getResources().getDrawable(R.drawable.ic_launcher));
			        
			        }
			        
			        
			    }
			
			    return convertView;
		
		 } 

}
	
 
}
