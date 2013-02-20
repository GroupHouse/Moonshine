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
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class YieldActivity extends SherlockActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_yield);
		// Show the Up button in the action bar.
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    	Apptentive.onCreate(this, savedInstanceState);

		TabHost tabHost=(TabHost)findViewById(R.id.tabhost1);
		tabHost.setup();

		TabSpec spec1=tabHost.newTabSpec("Tab 1");
	    
		spec1.setContent(R.id.tab1);
		spec1.setIndicator("Distilled");
		
		TabSpec spec2=tabHost.newTabSpec("Tab 2");
		spec2.setIndicator("Starting");
		spec2.setContent(R.id.tab2);
		
		tabHost.addTab(spec1);
		tabHost.addTab(spec2);
        final Spinner sugar = (Spinner) findViewById(R.id.spStartMeasure);

		
        sugar.setOnItemSelectedListener(new OnItemSelectedListener() {
	            @Override
	            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
	                // your code here
	            	String selected =sugar.getItemAtPosition(position).toString();
	            	if (selected.equals("Lb"))
	            	{
	            	//StartMeasure = "Lb";
	            	}
	            	else
	            	{
	            	//StartMeasure = "kg";
	            	}
	            }
	    

	            @Override
	            public void onNothingSelected(AdapterView<?> parentView) {
	                // your code here
	            }

	        });

  final Spinner amount = (Spinner) findViewById(R.id.Spinner01);

		
        amount.setOnItemSelectedListener(new OnItemSelectedListener() {
	            @Override
	            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
	                // your code here
	            	String selected =sugar.getItemAtPosition(position).toString();
	            	if (selected.equals("Weights"))
	            	{
	            	
	            	}
	            	else
	            	{
	            			
	            	}
	            }
	    

	            @Override
	            public void onNothingSelected(AdapterView<?> parentView) {
	                // your code here
	            }

	        });
        
		Button button1 = (Button)findViewById(R.id.button1);
		button1.setOnClickListener(new View.OnClickListener() 
		{
			public void onClick(View v) 
				{
				EditText washsize = (EditText)findViewById(R.id.washsize);
				EditText percent = (EditText)findViewById(R.id.percent);
				EditText use = (EditText)findViewById(R.id.totalamountofsugar);
				if (washsize.length() == 0)
				{
					washsize.setText("0");
				}
				if (percent.length() == 0)
				{
					percent.setText("0");
				}
				
				use.setText(sugartouse(Double.parseDouble(washsize.getText().toString()), Double.parseDouble(percent.getText().toString())));
		  		}
	
		});

		
		Button calcbtn = (Button)findViewById(R.id.btCalculate);
		calcbtn.setOnClickListener(new View.OnClickListener() 
		{
			public void onClick(View v) 
				{
				EditText etTargetPercentage = (EditText)findViewById(R.id.etTargetPercentage);
				Spinner End = (Spinner)findViewById(R.id.Spinner01);
				Spinner Start = (Spinner)findViewById(R.id.spStartMeasure);
				EditText startsugar = (EditText)findViewById(R.id.etStartingAmount);
				EditText stillmakes = (EditText)findViewById(R.id.etStartPercent);
				if (startsugar.getText().length() == 0)
				{
					startsugar.setText("0");
				}
				if (stillmakes.getText().length() == 0)
				{
					stillmakes.setText("0");
				}
				if (etTargetPercentage.getText().length() == 0)
				{
					etTargetPercentage.setText("0");
				}
				etTargetPercentage.setText(calcyield(Double.parseDouble(startsugar.getText().toString()), Double.parseDouble(stillmakes.getText().toString()), Start.getSelectedItem().toString(), End.getSelectedItem().toString()));
		  		}
	
		});
		
		
	}
	public String sugartouse(double washsize, double percent)
	{
		double s;
		 s = washsize * percent * 17;
		return Double.toString(s/1000);
		
	}
public String calcyield(double sugaramount, double still, String Start, String end)
{
	Converter CV = new Converter();
	if (Start.equals("Lb"))
	{
		sugaramount = CV.convert(sugaramount, "Lb", "Kg");	
	}
	
	double yield = 0;
	double a = .55;
	double total;
	total = a * sugaramount;
	yield = total / (still/100);
	if (end.equals("Gallons"))
	{
		return Double.toString(Converter.convert(yield, "Liters", "Gallons"));
	}
	else
	{
		return Double.toString(yield);
	}
}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
	//	getMenuInflater().inflate(R.menu.activity_yield, menu);
		return true;
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
		}
		return super.onOptionsItemSelected(item);
	}

}
