package com.shine.moonshine;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuInflater;
import com.apptentive.android.sdk.Apptentive;
import com.apptentive.android.sdk.ApptentiveActivity;
import com.shine.moonshine.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.support.v4.app.NavUtils;

public class Calculators extends SherlockActivity  {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculators);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

   /* 	Button savebtn = (Button)findViewById(R.id.btDilution);
		savebtn.setOnClickListener(new View.OnClickListener() 
		{
			public void onClick(View v) 
				{
				//Grab the informational text needed for edit or new
			      Intent intent = new Intent(v.getContext(), DilutionActivity.class);
	   		        startActivity(intent);
		  		}
	
		});
	*/
		ImageButton saveibtn = (ImageButton)findViewById(R.id.imgDilution);
		saveibtn.setOnClickListener(new View.OnClickListener() 
		{
			public void onClick(View v) 
				{
				//Grab the informational text needed for edit or new
			      Intent intent = new Intent(v.getContext(), DilutionActivity.class);
	   		        startActivity(intent);
		  		}
	
		});
		
	/*	Button PA = (Button)findViewById(R.id.SGCount);
		PA.setOnClickListener(new View.OnClickListener() 
		{
			public void onClick(View v) 
				{
				//Grab the informational text needed for edit or new
			      Intent intent = new Intent(v.getContext(), Alcohol_content.class);
	   		        startActivity(intent);
		  		}
	
		});
		*/
		ImageButton PAi = (ImageButton)findViewById(R.id.imgPotential);
		PAi.setOnClickListener(new View.OnClickListener() 
		{
			public void onClick(View v) 
				{
				//Grab the informational text needed for edit or new
			      Intent intent = new Intent(v.getContext(), Alcohol_content.class);
	   		        startActivity(intent);
		  		}
	
		});
		
		/*Button convert = (Button)findViewById(R.id.btConvert);
		convert.setOnClickListener(new View.OnClickListener() 
		{
			public void onClick(View v) 
				{
				//Grab the informational text needed for edit or new
			      Intent intent = new Intent(v.getContext(), Converter.class);
	   		        startActivity(intent);
		  		}
	
		});	
		*/
		ImageButton convertimg = (ImageButton)findViewById(R.id.imgConversions);
		convertimg.setOnClickListener(new View.OnClickListener() 
		{
			public void onClick(View v) 
				{
				//Grab the informational text needed for edit or new
			      Intent intent = new Intent(v.getContext(), Converter.class);
	   		        startActivity(intent);
		  		}
	
		});	
		
    }

    public boolean onCreateOptionsMenu(Menu menu) {
       MenuInflater inflater = getSupportMenuInflater();
       inflater.inflate(R.menu.activity_calculators, menu);
       return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
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
