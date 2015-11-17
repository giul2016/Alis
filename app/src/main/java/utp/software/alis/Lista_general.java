package utp.software.alis;



import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ImageView;

import android.widget.ListView;
import android.widget.TextView;

//Lista general donde se ven los productos en un listview
public class Lista_general extends ListActivity {
	Cursor c ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lista_general);

	
		EditText edit =(EditText)findViewById(R.id.editText1);
		
		// comandos para abrir base de datos
		AdminSQLiteOpenHelper dbhelper = new AdminSQLiteOpenHelper(
				getBaseContext());
		
		 c = dbhelper.fet();
		//String x = new String (c.getColumnName(c.getInt(0)));
		// array para el SimpleCursorAdapter
		String from[] = new String[] { "nombre","category", };
		//String from[] = new String[] { "nombre", "categoria", };
		
		int to[] = new int[] { R.id.name, R.id.cate, };

		// Adaptador
		final MyCursorAdapter adapter = new MyCursorAdapter(getBaseContext(),
				R.layout.productos, c, from, to,
				SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

		// Declarar listview
		 final ListView list = (ListView) findViewById(android.R.id.list);
		// Poner mensaje cuendo el listview esta vacio
		list.setEmptyView(findViewById(R.id.empty));
		

		setListAdapter(adapter);
     

		// Cerramos la base de datos

		list.setTextFilterEnabled(true);
edit.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				//adapter.getFilter().filter(s);
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				//adapter.getFilter().filter(s);
				
				adapter.getFilter().filter(s.toString());
				  
				  
			}
		});

adapter.setFilterQueryProvider(new FilterQueryProvider() {
	
	@Override
	public Cursor runQuery(CharSequence constraint) {
		// TODO Auto-generated method stub
		
		AdminSQLiteOpenHelper dbhelper = new AdminSQLiteOpenHelper(
				getBaseContext());
		SQLiteDatabase db = dbhelper.getReadableDatabase();

		 
		 Cursor mCursor = null;
    	  if (constraint == null  || constraint.length () == 0)  {
    		  String columns[] = new String[] { "PRODUCTO._id", "nombre", "category","categoryid" };

   		 	  String where = "categoryid = CATEGORIAS._id";

    		    mCursor = db.query("PRODUCTO, CATEGORIAS",columns, where,
    		  null, null, null, null);
    		  
    	  
    	 
    	  }
    	  else {
    	       	 
    	  String[] params = { (String) constraint };
    	  String columns[] = new String[] { "PRODUCTO._id", "nombre", "category","categoryid" };

		 	  String where = "categoryid = CATEGORIAS._id AND nombre LIKE ('%' || ? || '%')";

		    mCursor = db.query("PRODUCTO, CATEGORIAS",columns, where,
		  params, null, null, null);
    	   
    	  
    	  
    	  }
    	  if (mCursor != null) {
    	   mCursor.moveToFirst();
    	  }
    	  
		
		
		return mCursor;
	}
});

		// Listener del listview
		list.setOnItemClickListener(new OnItemClickListener() {

			// Método que controla el click en los elementos de la lista
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				// Iniciar actividad Agregar articulo
				Intent i = new Intent(Lista_general.this,
						Agregar_articulo.class);
				
				 
				i.putExtra("id", (String.valueOf(arg3)));
				// String num al cual se le asigna el valor de y
				i.putExtra("num", "y");
				startActivity(i);
              
			}
		});
		
		
		 
	}

	
	
	protected void onDestroy() {
	    super.onDestroy();
	    c.close();
	  }
	 
	
	// Método que conecta con el botón agregar artículo
	public void lanzar(View view) {

		// Intent que iniciar actividad agregar articulo
		Intent i = new Intent(Lista_general.this, Agregar_articulo.class);
		// String num al cual se le asigna n y se pone como extra al Intent
		i.putExtra("num", "n");
		startActivity(i);
	}
	
	
public void category(View view){
		
		Intent i = new Intent(this, Categorias.class);
		startActivityForResult(i,1);
		
	}
protected void onActivityResult(int requestCode, int resultCode, Intent data) {

	  if (requestCode == 1) {

	     if(resultCode == RESULT_OK){      
	    	 finish();
	    	 startActivity(getIntent());
	     }
	     if (resultCode == RESULT_CANCELED) {    
	         //Write your code if there's no result
	     }
	  }
	}//onActivityResult

	// Método que controla cuando el usuario presiona el botón back
	@Override
	public void onBackPressed() {
		// Iniciar actividad MainActivity
		startActivity(new Intent(Lista_general.this, MainActivity.class));
		finish();
	}
	
	private class MyCursorAdapter extends SimpleCursorAdapter{
		private final Context mContext;
		private final int mLayout;
		private final Cursor mCursor;
		private final int mNameIndex;
		private  int mImaIndex;
		private final int mCateIndex;
	
		private final LayoutInflater mLayoutInflater;
		
		  public MyCursorAdapter(Context context, int layout, Cursor c,
		    String[] from, int[] to, int flags) {
		   super(context, layout, c, from, to, flags);
		   this.mContext = context;
		    this.mLayout = layout;
		    this.mCursor = c;
		    this.mImaIndex = mCursor.getColumnIndex("categoryid");
		    this.mCateIndex = mCursor.getColumnIndex("category");
		    this.mNameIndex = mCursor.getColumnIndex("nombre");
		    this.mLayoutInflater = LayoutInflater.from(mContext);
		  }  
		  private final class ViewHolder {
			    public TextView name;
			    public TextView cate;
			    public ImageView image;
			   
			}
		
		  public View getView(int position, View convertView, ViewGroup parent) {  
			  
		        //View view = super.getView(position, convertView, parent);
		       Cursor c = getCursor();
			  if (c.moveToPosition(position)) {
				  ViewHolder viewHolder;
			        if (convertView == null) {
			            convertView = mLayoutInflater.inflate(mLayout, null);
			            if(position % 2 == 0){  
				        	convertView.setBackgroundColor(Color.rgb(238, 233, 233));
				           }
				           else {
				        	   convertView.setBackgroundColor(Color.rgb(255, 255, 255));
				           }
			            viewHolder = new ViewHolder();
			            viewHolder.cate = (TextView) convertView.findViewById(R.id.cate);
			            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
			            viewHolder.image = (ImageView) convertView.findViewById(R.id.icon);
			         

			            convertView.setTag(viewHolder);
			        }
			        else {
			            viewHolder = (ViewHolder) convertView.getTag();
			        }
			        String cate = c.getString(mCateIndex);
			        String name = c.getString(mNameIndex);
			       int d= c.getInt(mImaIndex);
			         			      
			     	       
			        viewHolder.cate.setText(name);
			        viewHolder.name.setText(cate);
			     
			  
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

}}
