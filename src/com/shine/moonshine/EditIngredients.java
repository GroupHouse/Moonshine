package com.shine.moonshine;

import java.util.ArrayList;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuInflater;
import com.shine.moonshine.R;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.view.ContextMenu;

import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

public class EditIngredients extends SherlockActivity {

	public ArrayAdapter<String> arrayAdapter;
	public ArrayAdapter<String> grainAdapter;
	public ArrayAdapter<String> VitAdapter;
	public ArrayAdapter<String> OtherAdapter;
	public DBAdapter db;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_ingredients);
		// Show the Up button in the action bar.
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		TabHost tabHost=(TabHost)findViewById(R.id.tabhost);
		tabHost.setup();

		TabSpec spec1=tabHost.newTabSpec("Tab 1");
	    //Intent intent = new Intent(this, EditSugar.class); //or go to calculator list
	    //spec1.setContent(intent);
		spec1.setContent(R.id.tab1);
		spec1.setIndicator("Sugar");
	//	spec1.setIndicator("Sugar",getResources().getDrawable(R.drawable.onscreensugar));
		
		TabSpec spec2=tabHost.newTabSpec("Tab 2");
		spec2.setIndicator("Grain");
		spec2.setContent(R.id.tab2);
		//spec2.setIndicator("Grain",getResources().getDrawable(R.drawable.onscreensugar));

		TabSpec spec3=tabHost.newTabSpec("Tab 3");
		spec3.setIndicator("Vitamin");
		spec3.setContent(R.id.tab3);
	//	spec3.setIndicator("Vitamin",getResources().getDrawable(R.drawable.onscreensugar));

		TabSpec spec4=tabHost.newTabSpec("Tab 4");
		spec4.setIndicator("Other");
		spec4.setContent(R.id.tab4);
	//	spec4.setIndicator("Other",getResources().getDrawable(R.drawable.onscreensugar));

		
		tabHost.addTab(spec1);
		tabHost.addTab(spec2);
		tabHost.addTab(spec3);
		tabHost.addTab(spec4);
		
		ArrayList<String> Sugars = new ArrayList<String>();
		ArrayList<String> Grains = new ArrayList<String>();
		ArrayList<String> Vitamins = new ArrayList<String>();
		ArrayList<String> Other = new ArrayList<String>();
		
		db = new DBAdapter(getBaseContext());
	    db.open();

	    Cursor c = db.getIngredient("2", "edit");
	
	    c.moveToFirst();
	    for (int i=0; i<c.getCount(); i++)
	    {
	    	Sugars.add(c.getString(c.getColumnIndex("name")));
	        c.moveToNext();
	    }
	    c.close();
	   
		arrayAdapter =  new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,Sugars);
    	ListView list1 = (ListView) findViewById(R.id.Sugars);
    	   //ArrayAdapter<String> arrayAdapter =  new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, Name);
    	list1.setAdapter(arrayAdapter); 
        registerForContextMenu(list1);
	
	    Cursor c2 = db.getIngredient("1", "edit");
	
	    c2.moveToFirst();
	    for (int i=0; i<c2.getCount(); i++)
	    {
	    	Grains.add(c2.getString(c2.getColumnIndex("name")));
	        c2.moveToNext();
	    }
	    c2.close();

		grainAdapter =  new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,Grains);
    	ListView grain = (ListView) findViewById(R.id.Grains);
    	   //ArrayAdapter<String> arrayAdapter =  new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, Name);
    	grain.setAdapter(grainAdapter); 
        registerForContextMenu(grain);
    	
	    Cursor c3 = db.getIngredient("3", "edit");
		
	    c3.moveToFirst();
	    for (int i=0; i<c3.getCount(); i++)
	    {
	    	Vitamins.add(c3.getString(c3.getColumnIndex("name")));
	        c3.moveToNext();
	    }
	    c3.close();

		VitAdapter =  new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,Vitamins);
    	ListView Vitamin = (ListView) findViewById(R.id.Vitamins1);
    	   //ArrayAdapter<String> arrayAdapter =  new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, Name);
    	Vitamin.setAdapter(VitAdapter); 
        registerForContextMenu(Vitamin);
    	
	    Cursor c4 = db.getIngredient("4", "edit");
		
	    c4.moveToFirst();
	    for (int i=0; i<c4.getCount(); i++)
	    {
	    	Other.add(c4.getString(c4.getColumnIndex("name")));
	        c4.moveToNext();
	    }
	    c4.close();
	    db.close();

		OtherAdapter =  new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,Other);
    	ListView ot = (ListView) findViewById(R.id.Others);
    	   //ArrayAdapter<String> arrayAdapter =  new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, Name);
    	ot.setAdapter(OtherAdapter); 
        registerForContextMenu(ot);    	
    	
    	
    	
    	Button GrainButton = (Button)findViewById(R.id.GrainButton);
    	GrainButton.setOnClickListener(new View.OnClickListener() 
   		{
   			public void onClick(View v) 
   			{
   			  InputMethodManager inputManager = (InputMethodManager)
                      getSystemService(Context.INPUT_METHOD_SERVICE); 
 inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                         InputMethodManager.HIDE_NOT_ALWAYS);
   				addItem(1);
   		   
   		    }
   		});
		

    	Button SugarButton = (Button)findViewById(R.id.SugarButton);
    	SugarButton.setOnClickListener(new View.OnClickListener() 
   		{
   			public void onClick(View v) 
   			{
      		     InputMethodManager inputManager = (InputMethodManager)
                         getSystemService(Context.INPUT_METHOD_SERVICE); 

    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
   				addItem(2);

   		    }
   		});
		

      	Button VitaminButton = (Button)findViewById(R.id.VitaminButton);
      	VitaminButton.setOnClickListener(new View.OnClickListener() 
   		{
   			public void onClick(View v) 
   			{
      		     InputMethodManager inputManager = (InputMethodManager)
                         getSystemService(Context.INPUT_METHOD_SERVICE); 

    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
   				addItem(3);

   		    }
   		});
 
     	Button OtherButton = (Button)findViewById(R.id.OtherButton);
      	OtherButton.setOnClickListener(new View.OnClickListener() 
   		{
   			public void onClick(View v) 
   			{
      		     InputMethodManager inputManager = (InputMethodManager)
                         getSystemService(Context.INPUT_METHOD_SERVICE); 

    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
   				addItem(4);

   		    }
   		});
      	
    	
	}
	public void addItem(int type)
	{
		DBAdapter db = new DBAdapter(getBaseContext());
	    db.open();
	    String name = "NA";
	    
	    String tag = "Added";
		if (type == 2) 
		{
				EditText sugar = (EditText) findViewById(R.id.editText1);
				name = sugar.getText().toString();
				sugar.setText("");
				if (!name.equals("NA") && name.length() > 0)
					{
					long i = db.insertoneingredient(name, type);
					if (i == 1)
						{
							tag = "Duplicate Entry";
						}
					else
						{
							arrayAdapter.add(name);
							arrayAdapter.notifyDataSetChanged();
						}
					}
		}
		if (type == 1)
		{
			EditText grain = (EditText) findViewById(R.id.GrainText);
			name = grain.getText().toString();
			grain.setText("");
			if (!name.equals("NA") && name.length() > 0)
			{
				long i = db.insertoneingredient(name, type);
				if (i == 1)
				{
					tag = "Duplicate Entry";
				}
				else
				{
					grainAdapter.add(name);
					grainAdapter.notifyDataSetChanged();
				}
			}
		}
		if (type == 3) 
		{
			EditText vitamin = (EditText) findViewById(R.id.vitamintext);
			name = vitamin.getText().toString();
			vitamin.setText("");
			if (!name.equals("NA") && name.length() > 0)
			{
				long i = db.insertoneingredient(name, type);
				if (i == 1)
				{
					tag = "Duplicate Entry";
				}
				else
				{
					VitAdapter.add(name);
					VitAdapter.notifyDataSetChanged();
				}
			}
		}
		if (type == 4) 
		{
			EditText other = (EditText) findViewById(R.id.othertext);
			name = other.getText().toString();
			other.setText("");
			if (!name.equals("NA") && name.length() > 0)
			{
				long i = db.insertoneingredient(name, type);
				if (i == 1)
				{
					tag = "Duplicate Entry";
				}
				else
				{
					OtherAdapter.add(name);
					OtherAdapter.notifyDataSetChanged();
				}
			}
		}
		Toast.makeText(getBaseContext(), tag + ": " + name , Toast.LENGTH_LONG).show();
	}
	

	
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getSupportMenuInflater();
        inflater.inflate(R.menu.activity_converter, menu);
        return super.onCreateOptionsMenu(menu);
     }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

	
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
        ContextMenuInfo menuInfo) {
    	
      android.view.MenuInflater inflater = getMenuInflater();
      inflater.inflate(R.menu.contextmenu, menu);
      super.onCreateContextMenu(menu, v, menuInfo);
     }
    @Override
    public boolean onContextItemSelected(android.view.MenuItem item)
    {
    	
    	AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        final ListView list1 = (ListView) info.targetView.getParent();
        Long a = info.id;
   	    final String selectedValue1 = (String) list1.getItemAtPosition(a.intValue()).toString();
   	    
        switch (item.getItemId()) {
        case R.id.edit:
      	    
        	
          return true;
        case R.id.delete:
    		
    		DBAdapter db = new DBAdapter(getBaseContext());
    	    db.open();
    	    int i = db.deleteIng(selectedValue1);
        	if (i == 1)
        	{
        		Toast.makeText(getBaseContext(), "Cannot delete "+ selectedValue1 + " it is being used in a recipe" , Toast.LENGTH_LONG).show();
	
        	}
        	else
        	{
        		if(list1.getTag().equals("Sugars"))
        		{
        			arrayAdapter.remove(selectedValue1);
					arrayAdapter.notifyDataSetChanged();
        		}
        		else if(list1.getTag().equals("Grains"))
        		{
        			grainAdapter.remove(selectedValue1);
        			grainAdapter.notifyDataSetChanged();
        		}
        		else if(list1.getTag().equals("Vitamin"))
        		{
        			VitAdapter.remove(selectedValue1);
        			VitAdapter.notifyDataSetChanged();	
        		}
        		else if(list1.getTag().equals("Other"))
        		{
        			OtherAdapter.remove(selectedValue1);
        			OtherAdapter.notifyDataSetChanged();
        		}
        			
        			Toast.makeText(getBaseContext(), "Deleted "+ selectedValue1, Toast.LENGTH_LONG).show();
        	}
    	    
    	    
    	    db.close();
        	
          return true;
        case R.id.copy:
            //deleteNote(info.id);
            return true;
        default:
		return false;
        }
    }
    public void onDestroy() {
        super.onDestroy();
        db.close();
    }

}
