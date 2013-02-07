package com.shine.moonshine;


import com.shine.moonshine.R;

import android.os.Bundle;
import android.preference.PreferenceFragment;

public class Preferences extends PreferenceFragment {

	 
	  @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        //--load the preferences from an XML file---
	        addPreferencesFromResource(R.xml.pref);

	    }
	  
	
	  
	  
}
