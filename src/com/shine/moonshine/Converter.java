package com.shine.moonshine;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuInflater;
import com.apptentive.android.sdk.Apptentive;
import com.apptentive.android.sdk.ApptentiveActivity;
import com.actionbarsherlock.app.SherlockActivity;
import com.shine.moonshine.R;

import java.text.DecimalFormat;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

public class Converter extends SherlockActivity  {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_converter);
       getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        Spinner From = (Spinner) findViewById(R.id.spType);
        Spinner To = (Spinner) findViewById(R.id.spEnd);
		
    	ArrayAdapter<CharSequence> measureadapter = ArrayAdapter.createFromResource(this,R.array.conversions_array, android.R.layout.simple_spinner_item);
   		measureadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
   		From.setAdapter(measureadapter);
   		To.setAdapter(measureadapter);
   		
   		
      	final Button savebtn = (Button)findViewById(R.id.btnCalculateCon);
    		savebtn.setOnClickListener(new View.OnClickListener() 
    		{
    			public void onClick(View v) 
    				{
    				//Grab the informational text needed for edit or new
    		        EditText Amount = (EditText) findViewById(R.id.etStart);
    		        TextView Answer = (TextView) findViewById(R.id.txtAnswer);
    		        Spinner From = (Spinner) findViewById(R.id.spType);
    		        Spinner To = (Spinner) findViewById(R.id.spEnd);
    				Double Result;
    				DecimalFormat fmt = new DecimalFormat("0.0'0'"); 
    				 
    				getBaseContext();
					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    				imm.hideSoftInputFromWindow(savebtn.getWindowToken(), 0);
    				if (Amount.getText().toString().length() > 0)
    				{
    				Result = convert(Double.parseDouble(Amount.getText().toString()),From.getSelectedItem().toString(), To.getSelectedItem().toString());
    				Answer.setText(fmt.format(Result) + " " + To.getSelectedItem().toString());
    				
    				}
    				else
    				{
    					Toast.makeText(getBaseContext(), "Enter a number please", Toast.LENGTH_LONG);
    				}
    				}
    		  		
    	
    		});
        
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

  //add this after the main method

    public static double convert(double length,String from,String to){

    double meter = 0.00;

    if(from.equals("Liters")&&to.equals("Gallons"))
    {
    	meter=length/3.7854;
    }
    
    if(from.equals("Liters")&&to.equals("Cups"))
    {
        meter=4.22675284*length;
    }
    
    if(from.equals("Liters")&&to.equals("Oz"))
    {
        meter=33.8140227*length;
    }

    if(from.equals("Gallons")&&to.equals("Liters"))
    {
    	meter=3.7854*length;
    }

    if(from.equals("Gallons")&&to.equals("Cups"))
    {
    	meter=length*16;
    }

    if(from.equals("Gallons")&&to.equals("Oz"))
    {
    	meter=length*128;
    }

    if(from.equals("Cups")&&to.equals("Gallons"))
    {
    	meter=length*0.0625;
    }

    if(from.equals("Cups")&&to.equals("Oz"))
    {
    	meter=length*8;
    }
    
    if(from.equals("Cups")&&to.equals("Liters"))
    {
        meter=length*0.236588237;
    }

    if(from.equals("Oz")&&to.equals("Cups"))
    {
    	meter=length/8;
    }
    
    if(from.equals("Oz")&&to.equals("Gallons"))
    {
        meter=length/128;
    }
    
    if(from.equals("Oz")&&to.equals("Liters"))
    {
        meter=length*0.0295735296;
    }
    return meter;
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
