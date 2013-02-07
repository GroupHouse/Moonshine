package com.shine.moonshine;

import java.io.IOException;

import com.apptentive.android.sdk.Apptentive;
import com.apptentive.android.sdk.ApptentiveActivity;

import com.apptentive.android.sdk.SurveyModule;
import com.apptentive.android.sdk.module.survey.OnSurveyCompletedListener;
import com.apptentive.android.sdk.module.survey.OnSurveyFetchedListener;
import com.shine.moonshine.R;

import android.R.string;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

public class Home extends ApptentiveActivity  
{
	private static String LOG_TAG = "Apptentive Example";
	public string SetUnitType ;
	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
     
       //Loading what the user preferences are - set as true if you want to clear preferences(good for debug)
        LoadPreferences(true);
      
        //check to see if this is first time with the app and load some starter recpies if yes
   		isFirstTime();
   		
   		// Listen for the new recipe button and when it is clicked take it to the appropriate activity
   		ImageButton NewRecipe = (ImageButton)findViewById(R.id.Recipes);
   		NewRecipe.setOnClickListener(new View.OnClickListener() 
   		{
   			public void onClick(View v) 
   			{
   		        Intent intent = new Intent(v.getContext(), RecipeList.class); //go to recipe list
   		        startActivity(intent);
   		    }
   		});
   		ImageButton Calculator = (ImageButton)findViewById(R.id.Calculator);
   		Calculator.setOnClickListener(new View.OnClickListener() 
   		{
   			public void onClick(View v) 
   			{
   		        Intent intent = new Intent(v.getContext(), Calculators.class); //or go to calculator list
   		        startActivity(intent);
   		    }
   		});
   		ImageButton Settings = (ImageButton)findViewById(R.id.imgSettings);
   		Settings.setOnClickListener(new View.OnClickListener() 
   		{
   			public void onClick(View v) 
   			{
   		        Intent intent = new Intent(v.getContext(), EditIngredients.class); //or go to calculator list
   		        startActivity(intent);
   		    }
   		});
   		
   		ImageButton Survey = (ImageButton)findViewById(R.id.imgSurvey);
   		Survey.setOnClickListener(new View.OnClickListener() 
   		{
   			public void onClick(View v) 
   			{
   				//SurveyModule sv = Apptentive.getSurveyModule();
				//sv.fetchSurvey(null);
				//sv.show(getBaseContext());
	
   				Apptentive.getSurveyModule().fetchSurvey(new OnSurveyFetchedListener() {
   					public void onSurveyFetched(final boolean success) {
   						Log.e(LOG_TAG, "onSurveyFetched(" + success + ")");
   						runOnUiThread(new Runnable() {
   							public void run() {
   								//Toast toast = Toast.makeText(Home.this, success ? "Survey fetch successful." : "Survey fetch failed.", Toast.LENGTH_SHORT);
   								//toast.setGravity(Gravity.CENTER, 0, 0);
   								//toast.show();
   								//findViewById(R.id.show_survey_button).setEnabled(success);
   								Apptentive.getSurveyModule().show(getBaseContext(), new OnSurveyCompletedListener() {
   									public void onSurveyCompletedListener() {
   										Log.e(LOG_TAG, "Got a callback from completed survey!");
   									}
   								});
   							}
   						});
   					}
   				});
   				 
   		    } 
   		});
   		
   		
    }
	public void onShowSurveyButtonPressed(View view) {
		
	}
    @Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		if (hasFocus) {
			Apptentive.getRatingModule().run(this);
		}
	}
    
    
	public void onFeedbackButtonPressed(View view) {
		Apptentive.getFeedbackModule().forceShowFeedbackDialog(this);
	}
	
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
    //	MenuInflater inflater = getMenuInflater();
    //	inflater.inflate(R.menu.menu, menu);
       return true;
    }
    

    public void LoadPreferences(boolean clear)
   {
    	 SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

    	 if (clear = true)
    	 {
    		 SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
    		 SharedPreferences.Editor editor = preferences.edit();
    		 editor.clear();
    		 editor.commit();
    	 }
   }
    
public void isFirstTime()
	{
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

		int i = preferences.getInt("FirstTime", 0);
		//i = 0;
		if (i == 0) //if the first time we are loading commit these recipes to file
		{
			  DBAdapter db = new DBAdapter(this);
		       try
		       {
		       db.createDataBase();
		       } catch (IOException ioe) {
		    	   throw new Error("Unable to create database");
		       }
		       
		       try
		       {
		    	   db.openDataBase();		       
		    	} catch (SQLException sqle) {
		    	   throw sqle;
		       }
		       
		   
		      //  db.seedingredients(getBaseContext());
		      //  db.seedrecipies(getBaseContext());
		      //  db.close();
		        preferences.edit().putInt("FirstTime", 1);
		        preferences.edit().commit();
		}
	}            
}


   