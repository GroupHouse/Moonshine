package com.shine.moonshine;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuInflater;
import java.util.ArrayList;

import com.actionbarsherlock.app.SherlockActivity;
import com.apptentive.android.sdk.Apptentive;
import com.shine.moonshine.R;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.NavUtils;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
//import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class RecipeList extends SherlockActivity {
	public DBAdapter db;
 
    public void onCreate(Bundle savedInstanceState) {
    	   	 
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Apptentive.onCreate(this, savedInstanceState);

        db = new DBAdapter(this);
        db.open();
        Cursor c = db.getAllRecipies();
        LoadList(c); //load all of the recipies on file
        c.close();
        db.close();
        
   	    ListView lv = (ListView) findViewById(android.R.id.list);
        registerForContextMenu(lv);

   	    lv.setTextFilterEnabled(true);
        lv.setOnItemClickListener(new OnItemClickListener() {
          public void onItemClick(AdapterView<?> parent, View v,int position, long id) {
        		 ListView list1 = (ListView) findViewById(android.R.id.list);

           	  String selectedValue = (String) list1.getItemAtPosition(position);
           	  
              //Toast.makeText(Context,"Selected item is "+ position, Toast.LENGTH_SHORT).show();    
              Intent i = new Intent(getBaseContext(), ShowRecipe.class);
              i.putExtra("newornot", "not");
              i.putExtra("RecipeName", selectedValue);
              //i.putExtra("New", "Old");
              startActivity(i);

           }
      });
        

}		


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
        ContextMenuInfo menuInfo) {
      android.view.MenuInflater inflater = getMenuInflater();
      inflater.inflate(R.menu.contextmenu, menu);
      super.onCreateContextMenu(menu, v, menuInfo);
     }

    
    @Override
    public boolean onOptionsItemSelected(MenuItem menu){
    	
        switch (menu.getItemId()) {
        case R.id.save_button:
        	Intent intent = new Intent(getBaseContext(), EditRecipe.class);
			intent.putExtra("newornot", "New");
			startActivity(intent);
			return false;
        default:
        	 NavUtils.navigateUpFromSameTask(this);
             return true;
        }
        
        }
        

    @Override
    public boolean onContextItemSelected(android.view.MenuItem item)
    {
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        final ListView list1 = (ListView) findViewById(android.R.id.list);
        Long a = info.id;
        
   	  final String selectedValue1 = (String) list1.getItemAtPosition(a.intValue()).toString();
   	    
        switch (item.getItemId()) {
        case R.id.edit:
      	    Intent i = new Intent(getBaseContext(), EditRecipe.class);   
              i.putExtra("RecipieName", selectedValue1);
              i.putExtra("newornot", "not");
              startActivity(i);
          return true;
        case R.id.delete:
      		Shine Recipe = new Shine(selectedValue1, RecipeList.this);
      		Recipe.remove(getBaseContext());
      		Intent intent2 = new Intent(RecipeList.this, RecipeList.class); //go to recipe list
   		    startActivity(intent2);
          return true;
        case R.id.copy:
            //deleteNote(info.id);
            return true;
        default:
		return false;
        }
    }
    @SuppressLint("ParserError")
    
	private void LoadList(Cursor c)
    {
  	
    //	SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
    //	int numofrecipes = preferences.getInt("numofrecipes", 0);
    	ArrayList<String> Name = new ArrayList<String>();
    	c.moveToFirst();
		for(int i=0;i<c.getCount();i++)
		{
			//list.add(preferences.getString("recipe"+i, "0"));
			Name.add(c.getString(c.getColumnIndex("recipieName")));
			c.moveToNext();

		}
		
    	   ListView list1 = (ListView) findViewById(android.R.id.list);
    	   ArrayAdapter<String> arrayAdapter =  new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, Name);
    	   list1.setAdapter(arrayAdapter); 
    	
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       MenuInflater inflater = getSupportMenuInflater();
       inflater.inflate(R.menu.menu, menu);
       return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onStart() {
      super.onStart();
      Apptentive.onStart(this);
    }

    @Override
    protected void onStop() {
      super.onStop();
      Apptentive.onStop(this);
    }

    @Override
    protected void onDestroy() {
      super.onDestroy();
      Apptentive.onDestroy(this);
      db.close();
    }   


}
