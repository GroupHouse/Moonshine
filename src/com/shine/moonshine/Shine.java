package com.shine.moonshine;

import java.util.ArrayList;


import android.content.Context;
import android.database.Cursor;


public class Shine {

	// TODO Auto-generated method stub
	
	private int id; //recipe ID
	private String Name; //recipe name
	private String typeOf; //recipe liquor type
	private int MashSize; //recipe liquor type
	private double SG; //recipe liquor type

	private String Instructions; //recipe liquor type
	
	private ArrayList<String> Ingredient = new ArrayList<String>();
	private ArrayList<String> IngredientAmount = new ArrayList<String>();
	private ArrayList<String> IngredientMeasure = new ArrayList<String>();
	private ArrayList<Integer> ingRowID = new ArrayList<Integer>();
	private ArrayList<String> IngredientType = new ArrayList<String>();
private boolean update;

	//private ArrayList<String> GrainNotes = new ArrayList<String>();
	
	private int waterAmount;
	private String Units;
	
	public Shine(Context c)
	{
		 this.Name = "temp";
		 this.typeOf= "Vodka";
		// this.id= Integer.parseInt(cursor.getString(cursor.getColumnIndex("Rid")));
		 this.MashSize=0;
		 this.SG=1.08;
		 this.Units="Metric";
		 
		 this.Instructions="Instructions go here";
		 this.save(c, false);
	}
	

	public Shine(String name, Context c) {
		// TODO Auto-generated constructor stub
		 ArrayList<String> ingName = new ArrayList<String>();
		 ArrayList<String> amount = new ArrayList<String>();
		 ArrayList<String> measure = new ArrayList<String>();
		 ArrayList<Integer> rowid = new ArrayList<Integer>();
		 ArrayList<String> type = new ArrayList<String>();

		DBAdapter db = new DBAdapter(c);
		 db.open();
		 Cursor cursor = db.getRecipe(name);
		 if (cursor.getCount() > 0)
		 {
			 cursor.moveToFirst();
	
			 this.Name= cursor.getString(cursor.getColumnIndex("recipieName"));
			 this.typeOf= cursor.getString(cursor.getColumnIndex("recipieType"));
			 this.Units= cursor.getString(cursor.getColumnIndex("units"));
			 this.id= Integer.parseInt(cursor.getString(cursor.getColumnIndex("Rid")));
			 if (cursor.isNull(cursor.getColumnIndex("recipieMashSize")))
			 {
				this.MashSize = 0; 
			 }
			 else
			 {
			 this.MashSize= Integer.parseInt(cursor.getString(cursor.getColumnIndex("recipieMashSize")));
			 }
			 this.SG= Double.parseDouble(cursor.getString(cursor.getColumnIndex("SG")));
		
			
			 if (cursor.getString(cursor.getColumnIndex("instructions")).length() >0)
			 {
			 this.Instructions= cursor.getString(cursor.getColumnIndex("instructions"));
			 }
		 }	 
			 
			 for(int i=0;i<cursor.getCount();i++)
			 {
				 ingName.add(cursor.getString(cursor.getColumnIndex("ingName")));
				 amount.add(cursor.getString(cursor.getColumnIndex("amount")));
				 measure.add(cursor.getString(cursor.getColumnIndex("measure")));
				 rowid.add(Integer.parseInt(cursor.getString(cursor.getColumnIndex("listingRowID"))));
				 type.add(cursor.getString(cursor.getColumnIndex("IngredientType")));
				 cursor.moveToNext();
			 }
			
		 
		 int numofing = cursor.getCount();
		 cursor.close();
		 db.close();
		 
		 for(int i=0;i<numofing;i++)
			{
				//this.setIngredients(cursor.getString(cursor.getColumnIndex("ingName")), cursor.getString(cursor.getColumnIndex("amount")), cursor.getString(cursor.getColumnIndex("measure")),cursor.getString(cursor.getColumnIndex("IngredientType")),false,c, Integer.parseInt(cursor.getString(cursor.getColumnIndex("listingRowID"))),0);
				//cursor.moveToNext();
				this.setIngredients(ingName.get(i), amount.get(i), measure.get(i),type.get(i),false,c, rowid.get(i),0);
			 
			} 
		
		
	}
	public Shine(int id, Context c) {
		// TODO Auto-generated constructor stub
	     DBAdapter db = new DBAdapter(c);
		 db.open();
		 Cursor cursor = db.getRecipeById(Integer.toString(id));
		cursor.moveToFirst();
		 this.Name= cursor.getString(cursor.getColumnIndex("recipieName"));
		 this.typeOf= cursor.getString(cursor.getColumnIndex("recipieType"));
		 this.Units= cursor.getString(cursor.getColumnIndex("units"));
		 this.id= Integer.parseInt(cursor.getString(cursor.getColumnIndex("Rid")));
		 if (cursor.isNull(cursor.getColumnIndex("recipieMashSize")))
		 {
			this.MashSize = 0; 
		 }
		 else
		 {
		 this.MashSize= Integer.parseInt(cursor.getString(cursor.getColumnIndex("recipieMashSize")));
		 }
		 this.SG= Double.parseDouble(cursor.getString(cursor.getColumnIndex("SG")));
		 if (cursor.getString(cursor.getColumnIndex("instructions")).length() >0)
		 {
		 this.Instructions= cursor.getString(cursor.getColumnIndex("instructions"));
		 }
		 for(int i=0;i<cursor.getCount();i++)
			{
				this.setIngredients(cursor.getString(cursor.getColumnIndex("ingName")), cursor.getString(cursor.getColumnIndex("amount")), cursor.getString(cursor.getColumnIndex("measure")),cursor.getString(cursor.getColumnIndex("IngredientType")),false,c, Integer.parseInt(cursor.getString(cursor.getColumnIndex("listingRowID"))),0);
				cursor.moveToNext();

			} 
		 cursor.close();
		 db.close();
		
	}
	public void setId(int id)
	{
		this.id = id;
	}
	
	public int getId()
	{
		return id;
	}
	
	public void setName(String name)
	{
		this.Name = name;
	}
	
	public String getName()
	{
		return Name;
	}
	public void setMashSize(int name)
	{
		this.MashSize = name;
	}
	
	public int getMashSize()
	{
		return MashSize;
	}
	public void setSG(double sg)
	{
		this.SG = sg;
	}
	
	public double getSG()
	{
		return SG;
	}
	public void settypeOf(String typeOf)
	{
		this.typeOf = typeOf;
	}
	public String gettypeOf()
	{
		return typeOf;
	}

	public void setwaterAmount(int waterAmount)
	{
		this.waterAmount = waterAmount;
	}
	public int getwaterAmount()
	{
		return waterAmount;
	}
	
	public void setUnits(String Units)
	{
		this.Units = Units;
	}
	
	public String getUnits()
	{	
		return Units;
	}
	
	public void setIsUpdate(boolean update)
	{
		this.update = update;
	}
	
	public boolean getIsUpdate()
	{
		return update;
	}
	
	public void setInstructions(String text)
	{
		this.Instructions = text;
	}
	
	public String getInstructions()
	{
		return Instructions;
	}
	public void clearing()
	{
	this.Ingredient.clear();
	this.IngredientAmount.clear();
	this.IngredientType.clear();
	this.ingRowID.clear();
	this.IngredientMeasure.clear();
}
	public void setIngredients(String Ingredient, String Amount, String Measure, String Type ,boolean update, Context cxt, int rowId, int arrayplace)
	{
		if (Ingredient.equals("Water"))
		{
			this.MashSize = Integer.parseInt(Amount);
		}
		if (update)
		{
			this.Ingredient.set(arrayplace,Ingredient);
			this.IngredientAmount.set(arrayplace,Amount);
			this.IngredientType.set(arrayplace,Type);
			this.ingRowID.set(arrayplace,rowId);
			this.IngredientMeasure.set(arrayplace,Measure);
		}
		else
		{
			this.Ingredient.add(Ingredient);
			this.IngredientAmount.add(Amount);
			this.IngredientType.add(Type);
			
			this.IngredientMeasure.add(Measure);
			if (rowId == -1) //-1 when it is a new ingredient added to existing recipe
			{
			  
				DBAdapter db = new DBAdapter(cxt);
		        db.close();
		        db.open();
		       
		        this.ingRowID.add(Integer.parseInt(Long.toString(db.insertNewIngredient(this))));
				db.close();
			}
			else
			{
				this.ingRowID.add(rowId);
			}
		}
	}
	public ArrayList<String> getIngredients()
	{
		
		return this.Ingredient;
	}
	public ArrayList<String> getIngredientAmount()
	{
		
		return this.IngredientAmount;
	}
	public ArrayList<String> getIngredientMeasure()
	{
		
		return this.IngredientMeasure;
	}
	
	public ArrayList<String> getIngredientType()
	{
		
		return this.IngredientType;
	}
	public ArrayList<Integer> getingRowID()
	{
		
		return this.ingRowID;
	}
	public Cursor getIngredientID(String name, DBAdapter db)
	{
		 
	     Cursor c = db.getIngredientID(name);
	   
		 return c;
	}
	public void remove(Context cxt)
	{
		  DBAdapter db = new DBAdapter(cxt);
	        db.open();
	        db.remove(this);
	        db.close();
	        
	}
	public void save(Context cxt, boolean update) {
		
		//FIND THE FILE WITH THE RECIPE WE ARE LOOKING FOR STORED BY NAME
		  DBAdapter db = new DBAdapter(cxt);
	        
	        db.open();
		if (update)
		{
			db.updateRecipie(this);
		}
		else
		{
		
			Long id1 = db.insertRecipe(this);
			this.id = id1.intValue();
		}
		db.close();
		
		
		
	}
}
