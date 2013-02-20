package com.shine.moonshine;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuInflater;

import java.text.DecimalFormat;
import java.util.ArrayList;

import com.apptentive.android.sdk.Apptentive;

import android.os.Bundle;
import android.app.ActionBar.LayoutParams;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;

import android.support.v4.app.NavUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class EditRecipe extends SherlockActivity implements OnItemSelectedListener {

	public int CountIng = 300;
	public int z = 0;
	public ArrayList<String> SpinnerValues = new ArrayList<String>();
 	public ArrayList<Spinner> mArraySpinner = new ArrayList<Spinner>();
 	public ArrayList<Integer> IngArrayPosition = new ArrayList<Integer>();
 	public ArrayList<Integer> IngDBrow= new ArrayList<Integer>();
 	public DBAdapter db;
 	public Shine Recipe;
 	public Shine Recipe1;
 	public ArrayList<TableRow> TBR = new ArrayList<TableRow>();
	public ArrayList<String> SetIngredients = new ArrayList<String>();
	public String[] numarray = new String[1000];
	public String[] WholeNumArray = new String[1000];
	public int x = 0;
	public String alt;
			
			
	@Override
    public void onCreate(Bundle savedInstanceState) 
    	{
        	super.onCreate(savedInstanceState);    	
        	setContentView(R.layout.recipe_description);
        	getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        	Apptentive.onCreate(this, savedInstanceState);
        	
        	DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);
            switch(metrics.densityDpi){
                 case DisplayMetrics.DENSITY_LOW:
                    alt = "low";
                            break;
                 case DisplayMetrics.DENSITY_MEDIUM:
                	 alt = "medium";
                             break;
                 case DisplayMetrics.DENSITY_HIGH:
                	 alt = "large";
                             break;
                 default:
                	 alt = "large";
            }
            
            DecimalFormat decimalFormat=new DecimalFormat();
            decimalFormat.setDecimalSeparatorAlwaysShown(false);
        	for(int i=0;i<1000;i++) //Loading an array with the numbers I want for the amount dropdowns
    		{
        		double a = 0.5 * i;
    			numarray[x] = decimalFormat.format(a).toString();
    			WholeNumArray[x] = Integer.toString(x);
    			x += 1;
    		}
        	Intent intent = getIntent();
     	    String WhichRecipe = intent.getStringExtra("RecipieName");
    	    String newornot = intent.getStringExtra("newornot");
    	    
    	    if (newornot.equals("not"))
    	    {
    	    	Recipe = new Shine(WhichRecipe, getBaseContext());
    	    	DisplayRecipe(Recipe);	
    	    }
    	    else
    	    {
        		Recipe = new Shine(getBaseContext());
    	    	Recipe.setIngredients("17", "0", "L","Other",false,getBaseContext(), -1,0);
    	    	DisplayRecipe(Recipe);
    	     	
    	    }
    	  		
        	@SuppressWarnings("unused")
			View.OnClickListener handler = new View.OnClickListener() {
				public void onClick(View v) {
					// TODO Auto-generated method stub
				    TableRow row = (TableRow)findViewById(v.getId());
				    ViewGroup table = null;
					table.removeView(row);
				}
			};

        	ImageButton button = (ImageButton) findViewById(R.id.btNewIg);
            button.setOnClickListener(new View.OnClickListener() {
            	 
      		  public void onClick(View arg0) {
       
      			// custom dialog
      			final Dialog dialog = new Dialog(arg0.getContext());
      			dialog.setContentView(R.layout.activity_add_popup);
      			dialog.setTitle("Which Ingredient");
      			Button dialogButton = (Button) dialog.findViewById(R.id.btnAddGrain);
      			// if button is clicked, close the custom dialog
      			dialogButton.setOnClickListener(new View.OnClickListener() {
      				public void onClick(View v) {
      	        	    SetIngredients.add("No");
      					LoadFirstIngredient("Grain", SetIngredients);
      					
      					dialog.dismiss();
      				}
      			});
      			Button dialogButtonOther = (Button) dialog.findViewById(R.id.btnAddOther);
      			// if button is clicked, close the custom dialog
      			dialogButtonOther.setOnClickListener(new View.OnClickListener() {
      				public void onClick(View v) {
      	        	    SetIngredients.add("No");

      					LoadFirstIngredient("Other",SetIngredients);
      					
      					dialog.dismiss();
      				}
      			});
      			Button dialogButtonSugar = (Button) dialog.findViewById(R.id.btnAddSugar);
      			// if button is clicked, close the custom dialog
      			dialogButtonSugar.setOnClickListener(new View.OnClickListener() {
      				public void onClick(View v) {
      	        	    SetIngredients.add("No");

      					LoadFirstIngredient("Sugar",SetIngredients);
      					dialog.dismiss();
      				}
      			});

      			Button dialogButtonYeast = (Button) dialog.findViewById(R.id.btnAddVitamin);
      			// if button is clicked, close the custom dialog
      			dialogButtonYeast.setOnClickListener(new View.OnClickListener() {
      				public void onClick(View v) {
      	        	    SetIngredients.add("No");

      					LoadFirstIngredient("Vitamin",SetIngredients);
      					dialog.dismiss();

      				}
      			});
      			
      			dialog.show();
      		  }
            });
       			
            this.getWindow().setSoftInputMode(
            	    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);	
            
      
            
        }
	  public void onItemSelected(AdapterView<?> parent, View view,
                                                    int position,long arg3) 
      {
      String type = parent.getTag().toString();
      
     
      }
public long LoadFirstIngredient(String type, ArrayList<String> SetIngs)
{

	ArrayList<String> IngredientListContent = new ArrayList<String>();
	ArrayAdapter<String> arrayAdapter =  new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,IngredientListContent);
	
	ArrayAdapter<String> IngredientListAmountAdapter;
	ArrayAdapter<CharSequence> IngredientListMeasureAdapter;
	
	Spinner Units = (Spinner) findViewById(R.id.spUnitsdd);
	ArrayAdapter myAdapUnits = (ArrayAdapter) Units.getAdapter(); 
	String UnitsPosition = Units.getSelectedItem().toString();

	if(UnitsPosition.equals("Standard"))
	{
		IngredientListAmountAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, numarray);
		IngredientListAmountAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
	
		IngredientListMeasureAdapter = ArrayAdapter.createFromResource(this, R.array.standardType, android.R.layout.simple_spinner_item);
		IngredientListMeasureAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	}
	else
	{
		IngredientListAmountAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, numarray);
		IngredientListAmountAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		IngredientListMeasureAdapter = ArrayAdapter.createFromResource(this, R.array.metricType, android.R.layout.simple_spinner_item);
		IngredientListMeasureAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	}

	String TypeOfMeasure;

	TableLayout t1 = (TableLayout) findViewById(R.id.TBIngredient);
	
	db = new DBAdapter(this);
    db.open();
  	Cursor c= null;
	if (type.contains("Grain"))
	{
		 c = db.getIngredient("1");

	}
	else if(type.contains("Sugar"))
	{
		  c = db.getIngredient("2");
	
	}
	else if(type.contains("Vitamin"))
	{
		  c = db.getIngredient("3");
	
	}
	else if(type.contains("Other"))
	{
		  c = db.getIngredient("4");
	
	}
	
	c.moveToFirst();
 	
	for(int i=0;i<c.getCount();i++)
		{
			IngredientListContent.add(c.getString(c.getColumnIndex("name")));
			c.moveToNext();
		}
	//IngredientListContent.add("Add New"); someday this will allow you to add things on the fly
    c.close();
	db.close();
	Spinner Ingredient = new Spinner(this);
	Ingredient.setOnItemSelectedListener(this);
	Ingredient.setTag(type);
    Ingredient.setAdapter(arrayAdapter); 
    mArraySpinner.add(Ingredient);
    CountIng = CountIng + 1;
    
	Spinner IngredientAmount = new Spinner(this);
    IngredientAmount.setAdapter(IngredientListAmountAdapter); 
    mArraySpinner.add(IngredientAmount);
    CountIng = CountIng + 1;
    
    Spinner IngredientMeasure = new Spinner(this);
    IngredientMeasure.setAdapter(IngredientListMeasureAdapter); 
    mArraySpinner.add(IngredientMeasure);
    CountIng = CountIng + 1;

    if (!SetIngs.contains("No"))
    {
    	IngArrayPosition.add(z);
    	IngDBrow.add(Integer.parseInt(SetIngs.get(3).toString()));

    	z = z + 1;

    	if (type.contains("Grain"))
    	{
    		int IngContent = arrayAdapter.getPosition(SetIngs.get(0));
        	Ingredient.setSelection(IngContent);	
        	int IngAmount = IngredientListAmountAdapter.getPosition(SetIngs.get(1));
        	IngredientAmount.setSelection(IngAmount);	
        	int IngMeasure = IngredientListMeasureAdapter.getPosition(SetIngs.get(2));
        	IngredientMeasure.setSelection(IngMeasure);	
        	

    	}
    	else if(type.contains("Sugar"))
    	{
    		int IngContent = arrayAdapter.getPosition(SetIngs.get(0));
        	Ingredient.setSelection(IngContent);
        	int IngAmount = IngredientListAmountAdapter.getPosition(SetIngs.get(1));
        	IngredientAmount.setSelection(IngAmount);	
        	int IngMeasure = IngredientListMeasureAdapter.getPosition(SetIngs.get(2));
        	IngredientMeasure.setSelection(IngMeasure);	
        	
    	
    	}
    	else if(type.contains("Vitamin"))
    	{
    		int IngContent = arrayAdapter.getPosition(SetIngs.get(0));
        	Ingredient.setSelection(IngContent);	
        	int IngAmount = IngredientListAmountAdapter.getPosition(SetIngs.get(1));
        	IngredientAmount.setSelection(IngAmount);	
        	int IngMeasure = IngredientListMeasureAdapter.getPosition(SetIngs.get(2));
        	IngredientMeasure.setSelection(IngMeasure);	
        	
    	}
    	else if(type.contains("Other"))
    	{
    		int IngContent = arrayAdapter.getPosition(SetIngs.get(0));
        	Ingredient.setSelection(IngContent);
        	int IngAmount = IngredientListAmountAdapter.getPosition(SetIngs.get(1));
        	IngredientAmount.setSelection(IngAmount);	
        	int IngMeasure = IngredientListMeasureAdapter.getPosition(SetIngs.get(2));
        	IngredientMeasure.setSelection(IngMeasure);	
        	
    	}
    	
    	
    	
    }
    else
    {
    	//when a new item is selected add it to the recipe object
    	Recipe.setIngredients(Ingredient.getSelectedItem().toString(), IngredientAmount.getSelectedItem().toString(), IngredientMeasure.getSelectedItem().toString(), "Grain", false, getBaseContext(), -1, 0);
    	IngArrayPosition.add(z);
    	IngDBrow.add(Recipe.getingRowID().get(z));
    	z = z + 1;
    }
    Ingredient.setMinimumWidth(150);
    Button Btn = new Button(this);
    Btn.setText("Del");
    Btn.setId(CountIng+100);
    Btn.setOnClickListener(onClick );
		final TableRow Tr = new TableRow(this);
		final TableRow Tr1 = new TableRow(this);

		Tr.setId(CountIng+100);
		
		TBR.add(Tr);

		//add the row to the table
		 t1.addView(Tr, new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,Gravity.BOTTOM));
			if (alt.equals("small"))
			{
				Tr1.setId(CountIng+101);
				TBR.add(Tr1);
				t1.addView(Tr1, new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,Gravity.BOTTOM));
				Btn.setText("Del " + Ingredient.getSelectedItem());
			}
		 Tr.addView(Ingredient);
		 Tr.addView(IngredientAmount);
		 Tr.addView(IngredientMeasure);
			if (alt.equals("small"))
			{
				Tr1.addView(Btn);
			}
			else
			{
				 Tr.addView(Btn);				
			}

		return 0;
	
}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getSupportMenuInflater();
        inflater.inflate(R.menu.activity_recipe_description, menu);
        return super.onCreateOptionsMenu(menu);
        
    }
public boolean DisplayRecipe(Shine WhichRecipe)
{
	EditText Name = (EditText) findViewById(R.id.edName);
	Name.setText(Recipe.getName());
	
	EditText sg = (EditText) findViewById(R.id.spSG);
	if (Recipe.getSG() > 0)
	{
	sg.setText(Double.toString(Recipe.getSG()));
	}
	else
	{
		sg.setText("0");	
	}
	TextView id = (TextView) findViewById(R.id.txtRecID);
	id.setText(Integer.toString(Recipe.getId()));

	TextView MashSize = (TextView) findViewById(R.id.spMash);
	MashSize.setText(Double.toString(Recipe.getMashSize()));

	Spinner Type = (Spinner) findViewById(R.id.spType);
	ArrayAdapter myAdapType = (ArrayAdapter) Type.getAdapter(); 
	int TypePosition = myAdapType.getPosition(Recipe.gettypeOf());
	Type.setSelection(TypePosition);
	
	Spinner Units = (Spinner) findViewById(R.id.spUnitsdd);
	ArrayAdapter myAdapUnits = (ArrayAdapter) Units.getAdapter(); 
	int UnitsPosition = myAdapUnits.getPosition(Recipe.getUnits());
	Units.setSelection(UnitsPosition);
	
	ArrayList<String> IngredientListContent = new ArrayList<String>();
	IngredientListContent.addAll(Recipe.getIngredients());
	ArrayList<String> IngredientListAmount = new ArrayList<String>();
	IngredientListAmount.addAll(Recipe.getIngredientAmount());
	ArrayList<String> IngredientListMeasure = new ArrayList<String>();
	IngredientListMeasure.addAll(Recipe.getIngredientMeasure());
	ArrayList<String> IngredientListType = new ArrayList<String>();
	IngredientListType.addAll(Recipe.getIngredientType());
	ArrayList<Integer> RowID = new ArrayList<Integer>();
	if (Recipe.getingRowID().size() > 0)
	{
	RowID.addAll(Recipe.getingRowID());
	}
	for(int i=0;i<Recipe.getIngredients().size();i++)
		{
		String content = IngredientListContent.get(i);
		String amount = IngredientListAmount.get(i);
		String measure = IngredientListMeasure.get(i);
		String rowID = RowID.get(i).toString();
		String type = IngredientListType.get(i);

		SetIngredients.add(content);
		SetIngredients.add(amount);
		SetIngredients.add(measure);
		SetIngredients.add(rowID);

		LoadFirstIngredient(type, SetIngredients);
		SetIngredients.clear();
		}
	if(Recipe.getIngredients().size()==0)
	{
		SetIngredients.add("Water");
		SetIngredients.add("0");
		SetIngredients.add("L");
		SetIngredients.add("0");

		LoadFirstIngredient("Other", SetIngredients);
		SetIngredients.clear();
	}
	
	EditText instruct = (EditText) findViewById(R.id.edInstruct);
	instruct.setText(Recipe.getInstructions());
	
	return false;
	
}

    public boolean SaveEverything()
    {
    	TextView id = (TextView) findViewById(R.id.txtRecID);
    	
    	EditText Name = (EditText) findViewById(R.id.edName);
    	Recipe.setName(Name.getText().toString());
    	
    	Spinner Type = (Spinner) findViewById(R.id.spType);
    	Recipe.settypeOf(Type.getSelectedItem().toString());
    	
    	Spinner Units = (Spinner) findViewById(R.id.spUnitsdd);
    	Recipe.setUnits(Units.getSelectedItem().toString());
    	
    	EditText SG = (EditText) findViewById(R.id.spSG);
    	if (SG.length() > 0) //if the SG is not set set it to something. This should be in the class
    	{
    	Recipe.setSG(Double.parseDouble(SG.getText().toString()));
    	}
    	else
    	{
    		Recipe.setSG(0);
    	}
    	EditText Instruct = (EditText) findViewById(R.id.edInstruct);
    	Recipe.setInstructions(Instruct.getText().toString());
   
    	Recipe.setIsUpdate(true);
    	ArrayList<Integer> ids = new ArrayList<Integer>();
    	ids.addAll(Recipe.getingRowID());
    	z =0;
    	DBAdapter db = new DBAdapter(getBaseContext());
	    db.open();
    	for(int i = 0; i < mArraySpinner.size(); i++)
    	{ 
    		Spinner spinner = mArraySpinner.get(i);
    		SpinnerValues.add(spinner.getSelectedItem().toString());
    		if ((i+1)%3==0 && i!=0)
    		{
    			Cursor c = Recipe.getIngredientID(SpinnerValues.get(0).toString(), db);
    		    c.moveToFirst();
    			Recipe.setIngredients(c.getString(c.getColumnIndex("_id")), SpinnerValues.get(1).toString(), SpinnerValues.get(2).toString(), "Grain", Recipe.getIsUpdate(), getBaseContext(), IngDBrow.get(z),IngArrayPosition.get(z));
    			SpinnerValues.clear();
    	  		z = z + 1;
    	  	    c.close();
    		}
  
    	}

    	db.close();
 		if (Recipe.getName().equals("temp"))
 		{
 			Toast.makeText(getBaseContext(), "You must change recipe name", Toast.LENGTH_SHORT).show();
 	 		
 		}
 		else
 		{
 			Recipe.save(this, true);	
    	    Toast.makeText(getBaseContext(), "Saved", Toast.LENGTH_SHORT).show();
 		}
    	return false;
    }

 	@Override
    public boolean onOptionsItemSelected(MenuItem menu) {
     	EditText Name = (EditText) findViewById(R.id.edName);

 	     switch (menu.getItemId()) {
         case R.id.save_button:
        	 if (Name.getText().toString().equals("temp"))
        	 {
      			Toast.makeText(getBaseContext(), "You must change the recipe name", Toast.LENGTH_LONG).show();
        	 }
        	 else
        	 {
        	 SaveEverything();
        	 }
        	 return false;
         default: //Error checking to make sure name is not temp

        	 if (Name.getText().toString().equals("temp"))
        	 {
      			Toast.makeText(getBaseContext(), "You must change the recipe name", Toast.LENGTH_LONG).show();
        	 }
        	 else
        	 {
        		 SaveEverything();
        	    Intent i = new Intent(getBaseContext(), ShowRecipe.class);
                i.putExtra("newornot", "not");
                i.putExtra("RecipeName", Name.getText().toString());
                //i.putExtra("New", "Old");
                startActivity(i);
        	 }  
        	 return true;
         }
 	     
 		
       
    }
 	
 	OnClickListener onClick = new OnClickListener() {
 		@Override
 		public void onClick(View v) {
 			 TableRow row1;		 
 			   TableRow row = (TableRow)findViewById(v.getId());
 			try
 			{
 			   row1 = (TableRow)findViewById(v.getId()+1);
 				}
 			finally
 			{
 				
 			}
 			  Spinner ing = (Spinner) row.getChildAt(0);
 			 DBAdapter db = new DBAdapter(getBaseContext());
 		      db.open();
 				Cursor c = Recipe.getIngredientID(ing.getSelectedItem().toString(), db);
 				 c.moveToFirst();
 				db.deleteIngFromRec(Recipe.getId(),Integer.parseInt(c.getString(c.getColumnIndex("_id"))));
 				c.close();
 				db.close();
 				
 				TableLayout t1 = (TableLayout) findViewById(R.id.TBIngredient);
 				t1.removeView(row);
 				try
 				
 				{
 				t1.removeView(row1);
 				}
 				finally
 				{
 					
 				}
 			}
 		
 	};
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
	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}	
 	  

}
