package com.shine.moonshine;

import java.text.DecimalFormat;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuInflater;
import com.apptentive.android.sdk.Apptentive;
import com.shine.moonshine.R;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.support.v4.app.NavUtils;

public class DilutionActivity extends SherlockActivity  {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dilution);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        

        	ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(this,R.array.dilutionType, android.R.layout.simple_spinner_item);
            typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        
        
        Button edit = (Button) findViewById(R.id.btCalculate);
		edit.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				TableRow TR = (TableRow) findViewById(R.id.tableRow4);
				TR.setVisibility(View.VISIBLE); 
				EditText StartingAmount = (EditText) findViewById(R.id.etStartingAmount);
				Spinner WhichWay = (Spinner) findViewById(R.id.txtStartingAmount);
				EditText StartingPercent = (EditText) findViewById(R.id.etStartPercent);
				EditText TargetPercent = (EditText) findViewById(R.id.etTargetPercentage);
				TextView Result = (TextView) findViewById(R.id.etAmountToAdd);
				Spinner StartMeasure = (Spinner) findViewById(R.id.spStartMeasure);
			//	Spinner EndMeasure = (Spinner) findViewById(R.id.spEndMeasure);
			
				
				
				StartMeasure.getSelectedItem();
		//		if (Measure.equals("Metric"))
		//		{
				double Amount;
				double SA = Double.parseDouble(StartingAmount.getText().toString());
				double SP = Double.parseDouble(StartingPercent.getText().toString());
				double TP = Double.parseDouble(TargetPercent.getText().toString());
				String SM = StartMeasure.getSelectedItem().toString();
				double multiplier;
				String amt = "";
				if (StartMeasure.getSelectedItem().equals("L"))
				{
					amt = "L";
					multiplier = 1;
				}
				else if (StartMeasure.getSelectedItem().equals("Cups"))
				{
					amt = "Cups";
					multiplier = 0.2365882365;
				}
				else if (StartMeasure.getSelectedItem().equals("Pints"))
				{
					amt = "Pints";
					multiplier = 0.473176473;
				}
				else if (StartMeasure.getSelectedItem().equals("Quarts"))
				{
					amt = "Quarts";
					multiplier = 0.946352946;
				}
				else if (StartMeasure.getSelectedItem().equals("Gallons"))
				{
					amt = "Gallons";
					multiplier = 3.7854118;
				}
				else if (StartMeasure.getSelectedItem().equals("mL"))
				{
					amt = "mL";
					multiplier = 100;
				}
				
		if (WhichWay.getSelectedItem().equals("Starting Amount"))
		{
				DecimalFormat fmt = new DecimalFormat("0.0'0'"); 
				Amount = SA * ((SP / TP)-1);
				Result.setText("Add " + String.valueOf(fmt.format(Amount)) + " " + StartMeasure.getSelectedItem().toString() + System.getProperty ("line.separator") + "For a total of " + String.valueOf(fmt.format(Amount + SA)) + " " + StartMeasure.getSelectedItem().toString());
		}
		else
		{
			DecimalFormat fmt = new DecimalFormat("0.0'0'");
			//Amount = SA * ((SP / TP)-1);
			//Result.setText("Add " + String.valueOf(fmt.format(Amount)) + " " + StartMeasure.getSelectedItem().toString() + System.getProperty ("line.separator") + "For a total of " + String.valueOf(fmt.format(Amount + SA)) + " " + StartMeasure.getSelectedItem().toString());
			
			double UseThisMuchAlc = SA *(TP/SP);
					
			//alk1.use_alc.value=alk1.amount.value *(alk1.weak.value / alk1.strong.value);
			double UseThisMuchWater = SA - UseThisMuchAlc;
			 //alk1.use_water.value=alk1.amount.value - alk1.use_alc.value;
			Result.setText("Combine " + String.valueOf(fmt.format(UseThisMuchAlc)) + " " + StartMeasure.getSelectedItem().toString() + " Alcohol "+ System.getProperty ("line.separator") + "And " + String.valueOf(fmt.format(UseThisMuchWater)) + " " + StartMeasure.getSelectedItem().toString() + " of Water");
			//double x = SA * multiplier;
			//x=alk1.amount.value*alk1.amountm.value;						
			
			//double a = x * (TP/SP);
			//a=x *(alk1.weak.value / alk1.strong.value);					

	//		String AlcValue = Math.round(a*alk1.use_alcm.value*1000)/1000;
	//		alk1.use_water.value=Math.round((x - a)*alk1.use_waterm.value*1000)/1000;
		}
			
				
			//	}
				
			//	else
			//	{
	/*		     <item>L</item>
			        <item>Oz</item>
			        <item>Cups</item>
			        <item>Pints</item>
			        <item>Quarts</item>
			        <item>Gallon</item>
				0.0295735295625">US fl oz</option>
		 		<option value="0.2365882365">US cups</option>			 
				<option value="0.473176473">US pint</option>
		   		<option value="0.946352946">US qt</option>
		   		<option value="3.7854118">US gal</option>

				//	dilute.startamount.value*((dilute.startstrength.value / dilute.endstrength.value)-1)*1000)/1000"	
			//	}
		*/		
			}
		});
        
        
        
        
        
    }
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       MenuInflater inflater = getSupportMenuInflater();
       inflater.inflate(R.menu.activity_dilution, menu);
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

