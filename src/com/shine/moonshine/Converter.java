package com.shine.moonshine;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuInflater;
import com.apptentive.android.sdk.Apptentive;
import com.actionbarsherlock.app.SherlockActivity;
import com.shine.moonshine.R;
import java.text.DecimalFormat;
import android.os.Bundle;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TabHost.TabSpec;
import android.support.v4.app.NavUtils;

public class Converter extends SherlockActivity  {

    @SuppressWarnings("null")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_converter);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Spinner type = (Spinner) findViewById(R.id.spliqorweigth);
        final Spinner From = (Spinner) findViewById(R.id.spType);
         final Spinner To = (Spinner) findViewById(R.id.spEnd);
 		ArrayAdapter<CharSequence> measureadapter = ArrayAdapter.createFromResource(Converter.this,R.array.conversions_array, android.R.layout.simple_spinner_item);
         
        type.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
            	String selected =type.getItemAtPosition(position).toString();
            	if (selected.equals("Weights"))
            	{
            		ArrayAdapter<CharSequence> measureadapter = ArrayAdapter.createFromResource(Converter.this,R.array.weight_array, android.R.layout.simple_spinner_item);
            		measureadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
               		From.setAdapter(measureadapter);
               		To.setAdapter(measureadapter);	
            	}
            	else
            	{
            		ArrayAdapter<CharSequence> measureadapter = ArrayAdapter.createFromResource(Converter.this,R.array.conversions_array, android.R.layout.simple_spinner_item);
            		measureadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
               		From.setAdapter(measureadapter);
               		To.setAdapter(measureadapter);	
            	}
            }
    

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    	measureadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
   		From.setAdapter(measureadapter);
   		To.setAdapter(measureadapter);	
	
		
        
		
    //	ArrayAdapter<CharSequence> measureadapter = ArrayAdapter.createFromResource(this,R.array.conversions_array, android.R.layout.simple_spinner_item);
   	//	measureadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
   	//	From.setAdapter(measureadapter);
   	//	To.setAdapter(measureadapter);
   		
   		
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
    						Toast.makeText(getBaseContext(), "Enter a number please", Toast.LENGTH_LONG).show();
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
        return meter;

    }
    
    if(from.equals("Liters")&&to.equals("Cups"))
    {
        meter=4.22675284*length;
        return meter;

    }
    
    if(from.equals("Liters")&&to.equals("Oz"))
    {
        meter=33.8140227*length;
        return meter;

    }

    if(from.equals("Gallons")&&to.equals("Liters"))
    {
    	meter=3.7854*length;
        return meter;

    }

    if(from.equals("Gallons")&&to.equals("Cups"))
    {
    	meter=length*16;
        return meter;

    }

    if(from.equals("Gallons")&&to.equals("Oz"))
    {
    	meter=length*128;
        return meter;

    }

    if(from.equals("Cups")&&to.equals("Gallons"))
    {
    	meter=length*0.0625;
        return meter;

    }

    if(from.equals("Cups")&&to.equals("Oz"))
    {
    	meter=length*8;
        return meter;

    }
    
    if(from.equals("Cups")&&to.equals("Liters"))
    {
        meter=length*0.236588237;
        return meter;

    }

    if(from.equals("Oz")&&to.equals("Cups"))
    {
    	meter=length/8;
        return meter;

    }
    
    if(from.equals("Oz")&&to.equals("Gallons"))
    {
        meter=length/128;
        return meter;

    }
    
    if(from.equals("Oz")&&to.equals("Liters"))
    {
        meter=length*0.0295735296;
        return meter;

    }
    
    if(from.equals("Lb")&&to.equals("Kg"))
    {
        meter=length*0.453592;
        return meter;

    }
    
    if(from.equals("Kg")&&to.equals("Lb"))
    {
        meter=length*2.20462;
        return meter;
    }
    if(from.equals("Kg")&&to.equals("g"))
    {
        meter=length*1000;
        return meter;
    }
    if(from.equals("g")&&to.equals("Kg"))
    {
        meter=length/1000;
        return meter;
    }
    if(from.equals("Lb")&&to.equals("g"))
    {
        meter=length*453.592;
        return meter;
    }
    if(from.equals("g")&&to.equals("Lb"))
    {
        meter=length*0.00220462;
        return meter;
    }
    
    return length;
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
