package com.shine.moonshine;
import com.apptentive.android.sdk.Apptentive;

import java.util.ArrayList;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuInflater;
import com.shine.moonshine.R;

import android.os.Bundle;
import android.app.ActionBar.LayoutParams;
import android.content.Intent;

import android.support.v4.app.NavUtils;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class ShowRecipe extends SherlockActivity {


     public String WhichRecipe;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_recipe);
		// Show the Up button in the action bar.
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	    Apptentive.onCreate(this, savedInstanceState);
	    DilutionActivity a = new DilutionActivity();
	    
	    Intent intent = getIntent();
	    WhichRecipe = intent.getStringExtra("RecipeName");
	    DisplayRecipe(WhichRecipe);
	   
	     
	     
	}

	public boolean DisplayRecipe(String WhichRecipe)
	{
		TableLayout tl1 = (TableLayout) findViewById(R.id.TableLayout1);
		Shine Recipe = new Shine(WhichRecipe, getBaseContext());
		
		TextView Name = (TextView) findViewById(R.id.txtTitle);
		if (Recipe.getUnits().equals("Standard"))
		{
		Name.setText(Recipe.getName() + "  (" + Recipe.getMashSize() + " Gallons)");
		}
		else
		{
		Name.setText(Recipe.getName() + "  (" + Recipe.getMashSize() + " Liters)");
		}
		ArrayList<String> IngredientListContent = new ArrayList<String>();
		IngredientListContent.addAll(Recipe.getIngredients());
		ArrayList<String> IngredientListAmount = new ArrayList<String>();
		IngredientListAmount.addAll(Recipe.getIngredientAmount());
		ArrayList<String> IngredientListMeasure = new ArrayList<String>();
		IngredientListMeasure.addAll(Recipe.getIngredientMeasure());
	 	if (IngredientListContent.size() >0)
	 	{
		for(int i=0;i<Recipe.getIngredients().size();i++)
			{
			String content = IngredientListContent.get(i);
			String amount = IngredientListAmount.get(i);
			String measure = IngredientListMeasure.get(i);
				//list.add(preferences.getString("recipe"+i, "0"));
			TableRow Tr = new TableRow(this);
			TextView Text = new TextView(this);
			
			Text.setText(amount + " " + measure  + " " + content);
			 Tr.addView(Text);
				
			tl1.addView(Tr, new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT,Gravity.BOTTOM));

			}
	 	}
	 	
		TextView Instruction = (TextView) findViewById(R.id.txtInstruction);
		if (Recipe.getInstructions().length() > 0)
		{
		Instruction.setText(Recipe.getInstructions());
		}
		
		return false;
	    
		
		
	}
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       MenuInflater inflater = getSupportMenuInflater();
       inflater.inflate(R.menu.activity_show_recipie, menu);
       return super.onCreateOptionsMenu(menu);
    }
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
			
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		case R.id.item1:
			Intent intent2 = new Intent(getBaseContext(), Calculators.class);
			startActivity(intent2);	
			return true;
		case R.id.save_button:
			Intent intent = new Intent(getBaseContext(), EditRecipe.class);
			intent.putExtra("newornot", "not");
			intent.putExtra("RecipieName", WhichRecipe);
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
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
	  }
}
