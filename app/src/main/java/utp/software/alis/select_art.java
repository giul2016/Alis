package utp.software.alis;



import android.app.ListActivity;
import android.content.ContentValues;
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
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class select_art extends ListActivity{
	Bundle bundle;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lista_g2);
		
		// Declarar listview
		ListView list = getListView();
		// Poner mensaje cuendo el listview esta vacio
		list.setEmptyView(findViewById(R.id.empty));
		
		bundle = getIntent().getExtras();
		EditText edit =(EditText)findViewById(R.id.editText1);
		
		// comandos para abrir base de datos
		AdminSQLiteOpenHelper dbhelper = new AdminSQLiteOpenHelper(
				getBaseContext());
		SQLiteDatabase db = dbhelper.getReadableDatabase();

		// array para el SimpleCursorAdapter
		String columns[] = new String[] { "PRODUCTO._id", "nombre", "category","categoryid"};
		
	
		String orderBy = "category";
		// query de la base de datos
final Cursor c = db.query("PRODUCTO, CATEGORIAS WHERE CATEGORIAS._id = categoryid  ", columns,null,
		null, null, null, orderBy);
		
	
		c.moveToFirst();
		// array para el SimpleCursorAdapter
		String from[] = new String[] { "nombre", "category", };
		//String from[] = new String[] { "nombre", "categoria", };
		
		int to[] = new int[] { R.id.cate, R.id.name };

		// Adaptador
		final MyCursorAdapter adapter = new MyCursorAdapter(getBaseContext(),
				R.layout.productos, c, from, to,
				SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

		setListAdapter(adapter);


		// Cerramos la base de datos
		db.close();
		
		
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
				AdminSQLiteOpenHelper dbhelper = new AdminSQLiteOpenHelper(
						getBaseContext());
				SQLiteDatabase db = dbhelper.getWritableDatabase();
				ContentValues registro = new ContentValues();
				String qury = String.format("SELECT * FROM POSEE WHERE pro_id=%d AND list_id=%d", arg3,bundle.getInt("id"));
				Cursor h = db.rawQuery(qury,null);
				
				  if( h.moveToFirst())
				{db.delete("POSEE", "_id="+(int)arg3, null);
				  toaste(arg1);
				  arg1.setBackgroundColor(Color.rgb(255, 255, 255));
					  }
				  else {String query = String.format("SELECT _id, categoryid FROM PRODUCTO WHERE _id=%d", arg3);
					Cursor c = db.rawQuery(query,null);
					  c.moveToFirst();
					toast(arg1);
					registro.put("cate_id",c.getString(1));
					registro.put("pro_id", String.valueOf(arg3));
					registro.put("list_id", bundle.getString("id"));
					registro.put("checks", 0);
					db.insert("POSEE", null, registro);
					c.close();
					arg1.setBackgroundColor(Color.rgb(238, 233, 233));}
					db.close();
					h.close();} 
		});
}
	public void toast(View view) {


		Toast.makeText(this, "Se agregó el producto.",
				Toast.LENGTH_SHORT).show();
	}
	public void toaste(View view) {


		Toast.makeText(this, "Se borró el producto.",
				Toast.LENGTH_SHORT).show();
	}
	
	public void lanzar(View view) {

		// Intent que iniciar actividad agregar articulo
		
		
		Intent i = new Intent(select_art.this, Sub_list.class);
		i.putExtra("dni", bundle.getString("id"));
		i.putExtra("nombre", bundle.getString("nombre"));
		startActivity(i);
	
	}
	
    public void onBackPressed() {
		
	Intent i = new Intent(select_art.this, Sub_list.class);
	i.putExtra("dni", bundle.getString("id"));
	i.putExtra("nombre", bundle.getString("nombre"));
	startActivity(i);

	}

	private class MyCursorAdapter extends SimpleCursorAdapter{
		private final Context mContext;
		private final int mLayout;
		private final Cursor mCursor;
		private final int mNameIndex;
		private  int mImaIndex;
       	private  int mIndex;
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
		     this.mIndex = mCursor.getColumnIndex("_id");
		    this.mLayoutInflater = LayoutInflater.from(mContext);
		  }  
		  private final class ViewHolder {
			    public TextView name;
			    public TextView cate;
			    public ImageView image;
			   
			}
		
		  public View getView(int position, View convertView, ViewGroup parent) {  
			  
		          Cursor sor= getCursor();
			  if (sor.moveToPosition(position)) {
				  ViewHolder viewHolder;
			        if (convertView == null) {
			            convertView = mLayoutInflater.inflate(mLayout, null);
			     
				        int ds = sor.getInt(mIndex);
			            
			            AdminSQLiteOpenHelper dbhelper = new AdminSQLiteOpenHelper(
								getBaseContext());
						SQLiteDatabase db = dbhelper.getWritableDatabase();
					   String qury = String.format("SELECT * FROM POSEE WHERE pro_id=%d", ds);
						Cursor h = db.rawQuery(qury,null);
						if(h.moveToFirst())
						{convertView.setBackgroundColor(Color.rgb(238, 233, 233));}
						else {convertView.setBackgroundColor(Color.rgb(255, 255, 255));}
						h.close();
						db.close();
						
			            viewHolder = new ViewHolder();
			            viewHolder.cate = (TextView) convertView.findViewById(R.id.cate);
			            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
			            viewHolder.image = (ImageView) convertView.findViewById(R.id.icon);
			         
                     
			            convertView.setTag(viewHolder);
			        }
			        else {
			            viewHolder = (ViewHolder) convertView.getTag();
			        }
			        String cate = sor.getString(mCateIndex);
			        String name = sor.getString(mNameIndex);
			       int d= sor.getInt(mImaIndex);
			         			      
			     	       
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

}
	
}
