package com.shine.moonshine;

import com.apptentive.android.sdk.Apptentive;

import java.text.DecimalFormat;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuInflater;
import com.shine.moonshine.R;
import android.os.Bundle;
import android.content.Context;
import android.support.v4.app.NavUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


public class Alcohol_content extends SherlockActivity  {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alcohol_content);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	    Apptentive.onCreate(this, savedInstanceState);

	    
        Spinner MeasureType = (Spinner) findViewById(R.id.spMeasure);
    	ArrayAdapter<CharSequence> measureadapter = ArrayAdapter.createFromResource(this,R.array.dilutionType, android.R.layout.simple_spinner_item);
   		measureadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
   		MeasureType.setAdapter(measureadapter);
        final Button Calc = (Button) findViewById(R.id.btnSGCalculate);
        
		Calc.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				
				EditText StartingAmount = (EditText) findViewById(R.id.etStartingSG);
				EditText EndingAmount = (EditText) findViewById(R.id.etEndingSG);
				EditText MashSize = (EditText) findViewById(R.id.etMashSize);
				TextView PotAlc = (TextView) findViewById(R.id.tvPotentialAlc);
				double SA;
				double EA; 
				double MS; 
				//double Amount;
				if (StartingAmount.getText().toString().length() > 0 )
				{
					SA = Double.parseDouble(StartingAmount.getText().toString());
				}
				else
				{
					SA = 0;
					StartingAmount.setText("0");
				}
				if (EndingAmount.getText().toString().length() > 0)
				{
					EA = Double.parseDouble(EndingAmount.getText().toString());
				}
				else
				{
					EA = 0;
					EndingAmount.setText("0");
				}
				if (MashSize.getText().toString().length() > 0)
				{
					MS = Double.parseDouble(MashSize.getText().toString());
				}
				else
				{
					MS = 0;
					MashSize.setText("0");
				}
			
				
				PotAlc.setText(calcPotentialAlc(SA,EA,MS));
				
			}
		});
    }

    public String calcPotentialAlc(double SA, double EA, double MS)
    {
    	String text;
    	double Potential;
		double mashsize;
        final Button Calc = (Button) findViewById(R.id.btnSGCalculate);
	    Spinner MeasureType = (Spinner) findViewById(R.id.spMeasure);

		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(Calc.getWindowToken(), 0);
		
		DecimalFormat fmt = new DecimalFormat("0.0'0'"); 
				
		Potential = ((SA-EA)*1000 / 7.5);
		mashsize = MS * (Potential/90);
		
		text = String.valueOf("Mash is " + fmt.format(Potential) + "% Alcohol") + String.valueOf("\n90% ABV = " + fmt.format(mashsize) + " " + MeasureType.getSelectedItem().toString() + "'s of Alcohol\n\n\n");
		
    	return text;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       MenuInflater inflater = getSupportMenuInflater();
       inflater.inflate(R.menu.activity_alcohol_content, menu);
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

