package com.shine.moonshine;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
 
public class DBAdapter extends SQLiteOpenHelper
{

//Recipie Name Table
private static final String RECIPIE_TABLE= "recipies";
public static final String KEY_RECIPIEID = "_id";
public static final String KEY_RECIPIENAME = "recipieName";
public static final String KEY_RECIPIETYPE = "recipieType";
public static final String KEY_RECIPIESG = "SG";
public static final String KEY_RECIPIEUNITS = "units";
public static final String KEY_RECIPIEMASHSIZE = "recipieMashSize";

//RecipieListing Table
private static final String LISTING_TABLE= "recipielisting";
public static final String KEY_RLISTINGID = "_id";
public static final String KEY_RRECIPIEID = "recipieID";
public static final String KEY_RINSTRUCTIONID = "instructionid";
public static final String KEY_RINGREDIENTID = "ingredientid";
public static final String KEY_RAMOUNT= "amount";
public static final String KEY_RMEASURE= "measure";

//Instruction Table
private static final String INSTRUCTION_TABLE= "instructions";
public static final String KEY_IID = "_id";
public static final String KEY_ITEXT= "instructions";
public static final String KEY_ISTEP= "step";
public static final String KEY_IRECIPIEID= "recipieID";

//Ingredient Table
private static final String INGREDIENT_TABLE= "ingredient";
public static final String KEY_IGID = "_id";
public static final String KEY_IGNAME= "name";
public static final String KEY_IGTYPE= "typeID";
public static final String KEY_IGRANK= "Rank";

//INGREDIENT TYPE
private static final String INGREDIENTTYPE_TABLE= "ingredienttype";
public static final String KEY_ITID = "_id";
public static final String KEY_ITNAME = "type";

private static String DB_PATH = "/data/data/com.shine.moonshine/databases/";
private static final String DATABASE_NAME = "recipes.db";
private static final String TABLE_NAME = "recipies";
private static final int DATABASE_VERSION = 1;
private SQLiteDatabase myDataBase; 

private static final String CREATE_RECIPIE = "CREATE TABLE IF NOT EXISTS recipies (_id integer primary key autoincrement, recipieName text not null, recipieType text not null, recipieMashSize int not null, SG double, units text)";
private static final String CREATE_RECIPIELISTING = "CREATE TABLE IF NOT EXISTS recipielisting (_id integer primary key autoincrement, recipieID int not null, ingredientID int, amount decimal not null, measure text not null, instructionID int)";
private static final String CREATE_INSTRUCTION = "CREATE TABLE IF NOT EXISTS instructions (_id integer primary key autoincrement, instructions text not null, recipieID int not null)";
private static final String CREATE_INGREDIENT = "CREATE TABLE IF NOT EXISTS ingredient (_id integer primary key autoincrement, name text not null, typeID int not null)";
private static final String CREATE_INGREDIENTYPE = "CREATE TABLE IF NOT EXISTS ingredienttype (_id integer primary key autoincrement, type text not null)";
private final Context myContext;


private DatabaseHelper DBHelper;
private SQLiteDatabase db;

public void openDataBase() throws SQLException{   
	//Open the database        
	String myPath = DB_PATH + DATABASE_NAME;    
	
	 myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE); 
	}  

public DBAdapter (Context ctx){  
	
	super(ctx, DATABASE_NAME, null, 1);        
	this.myContext = ctx;
	
	DBHelper = new DatabaseHelper(myContext);
}


public void open() throws SQLException
{
	String myPath = DB_PATH + DATABASE_NAME;    
	
	 myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE); 

	// myDataBase = DBHelper.getWritableDatabase();
	//return this;
}

public void close()
{
	super.close();
	DBHelper.close();
}

//insert instructions
public long instructions(String instruction, long recipieID, int ID)
{
	String id = String.valueOf(recipieID);
	//see if there is already an instruction set in the DB
	final String GET_GRAINS = "select _id from instructions where instructions.recipieID=?"; 
	Cursor c = myDataBase.rawQuery(GET_GRAINS, new String[]{id}); 
	
	
	if (c.getCount() > 0)
	{
		ContentValues args = new ContentValues();
		args.put(KEY_ITEXT, instruction);
		myDataBase.update(INSTRUCTION_TABLE, args,KEY_IRECIPIEID + "=" + recipieID, null);		
	}
	else
	{
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_ITEXT, instruction);
		initialValues.put(KEY_IRECIPIEID, recipieID);
	    Long instructid = myDataBase.insert(INSTRUCTION_TABLE, null, initialValues);
	    
	//	ContentValues args2 = new ContentValues();
	//	args2.put(KEY_RINSTRUCTIONID, instructid);
	//	myDataBase.update(LISTING_TABLE, args2,KEY_IRECIPIEID + "=" + recipieID, null);		
	    
	}
	myDataBase.close();
	return 0;
	
}

public Cursor getRecipe(String Name )
{
	
	final String GET_RECIPIE = "select instructions.instructions, recipielisting.recipieID as Rid,recipielisting._id as listingRowID, ingredientID, ingredient.name as ingName,  amount, measure, ingredienttype.type as IngredientType, recipies.recipieName,recipies.SG,recipies.recipieType,recipies.units ,recipies.recipieMashSize from recipielisting inner join [ingredient] on [ingredient].[_id] = [recipielisting].[ingredientID] inner join [recipies] on [recipies].[_id] = [recipielisting].[recipieID] inner join [ingredientType] on [ingredient].[TypeID] = [ingredientType].[_id] inner join [instructions] on [recipielisting].[recipieID] = [instructions].[recipieID] where recipies.[recipieName]=?"; 
	return myDataBase.rawQuery(GET_RECIPIE, new String[]{Name}); 
	
	
}

public Cursor getRecipeById(String id )
{
	
	final String GET_RECIPIE = "select instructions.instructions, recipielisting.recipieID as Rid,recipielisting._id as listingRowID, ingredientID, ingredient.name as ingName,  amount, measure, ingredienttype.type as IngredientType, recipies.recipieName,recipies.SG,recipies.recipieType,recipies.unitsrecipies.recipieMashSize from recipielisting inner join [ingredient] on [ingredient].[_id] = [recipielisting].[ingredientID] inner join [recipies] on [recipies].[_id] = [recipielisting].[recipieID] inner join [ingredientType] on [ingredient].[TypeID] = [ingredientType].[_id] inner join [instructions] on [recipielisting].[recipieID] = [instructions].[recipieID] where recipielisting.[_id]=?"; 
	return myDataBase.rawQuery(GET_RECIPIE, new String[]{id}); 
	
}

public Cursor getIngredient(String type)
{
	final String GET_GRAINS = "select name from ingredient where typeID=? order by rank"; 
	return myDataBase.rawQuery(GET_GRAINS, new String[]{type}); 
	
}
public Cursor getIngredient(String type, String edit)
{
	final String GET_GRAINS = "select name from ingredient where typeID=? and rank <> '1'"; 
	return myDataBase.rawQuery(GET_GRAINS, new String[]{type}); 
	
}
public Cursor getIngredientID(String type)
{
	
	final String GET_GRAINS = "select _id from ingredient where ingredient.name=?"; 
	return myDataBase.rawQuery(GET_GRAINS, new String[]{type}); 
	
}

public long getRecipieSteps(int id)
{
	
return 0;
}
public long deleteIngFromRec(int recid, int ingid)
{
	String where = "recipieID = '" + recid + "' AND ingredientid = '" + ingid +"'";
	String[] whereArgs = null;
	myDataBase.delete(LISTING_TABLE,  where, whereArgs);
	myDataBase.close();
	return 0;
	
	
}
public long insertoneingredient(String name, int type)
{

	final String GET_GRAINS = "select name from ingredient where name=?"; 
	Cursor c =  myDataBase.rawQuery(GET_GRAINS, new String[]{name}); 
	

	if (c.getCount() > 0)
	{
		c.close();
		return 1;
		
	}
	else
	{
		final String GET_GRAINS1 = "select name from ingredient where typeID=?"; 
		Cursor c1 =  myDataBase.rawQuery(GET_GRAINS1, new String[]{Integer.toString(type)}); 
		
	ContentValues initialValues = new ContentValues();
	initialValues.put(KEY_IGTYPE, type);
	initialValues.put(KEY_IGNAME, name);
	initialValues.put(KEY_IGRANK, c1.getCount()+1);
    Long instructid = myDataBase.insert(INGREDIENT_TABLE, null, initialValues);
    c.close();
    c1.close();
	return 0;
	}
}
public long insertNewIngredient(Shine recipie)
{
	ArrayList<String> ingredients = (ArrayList<String>) recipie.getIngredients();
	ArrayList<String> ingredientAmount = (ArrayList<String>) recipie.getIngredientAmount();
	ArrayList<String> ingredientMeasurement = (ArrayList<String>) recipie.getIngredientMeasure();
	ArrayList<Integer> ingredientRow = (ArrayList<Integer>) recipie.getingRowID();
	
long id;

	ContentValues initialValues2 = new ContentValues();
	
	initialValues2.put(KEY_RRECIPIEID, recipie.getId());
	if (recipie.getName().equals("temp"))
	{
		initialValues2.put(KEY_RINGREDIENTID, ingredients.get(ingredientRow.size()));
		initialValues2.put(KEY_RAMOUNT, ingredientAmount.get(ingredientRow.size()));
		initialValues2.put(KEY_RMEASURE, ingredientMeasurement.get(ingredientRow.size()));
		
	}
	else
	{
		initialValues2.put(KEY_RINGREDIENTID, ingredients.get(ingredientRow.size()));
		initialValues2.put(KEY_RAMOUNT, ingredientAmount.get(ingredientRow.size()));
		initialValues2.put(KEY_RMEASURE, ingredientMeasurement.get(ingredientRow.size()));
	}
	
	return  id = myDataBase.insert(LISTING_TABLE, null, initialValues2);	
	
}

public long insertRecipe(Shine recipie)
{
	
	//Add recipe Name to the recipe table
	ContentValues initialValues = new ContentValues();
	initialValues.put(KEY_RECIPIENAME, recipie.getName());
	initialValues.put(KEY_RECIPIETYPE, recipie.gettypeOf());
	initialValues.put(KEY_RECIPIEMASHSIZE, recipie.getMashSize());
	initialValues.put(KEY_RECIPIESG, recipie.getSG());
	initialValues.put(KEY_RECIPIEUNITS, recipie.getUnits());
	Long id = myDataBase.insert(RECIPIE_TABLE, null, initialValues);
	
	ArrayList<String> ingredients = (ArrayList<String>) recipie.getIngredients();
	ArrayList<String> ingredientAmount = (ArrayList<String>) recipie.getIngredientAmount();
	ArrayList<String> ingredientMeasurement = (ArrayList<String>) recipie.getIngredientMeasure();
	//insert data into recipielisting table
	for (int i = 0; i < ingredients.size(); i++) 
	{
		ContentValues initialValues2 = new ContentValues();
		initialValues2.put(KEY_RRECIPIEID, id);
		initialValues2.put(KEY_RINGREDIENTID, ingredients.get(i));
		initialValues2.put(KEY_RAMOUNT, ingredientAmount.get(i));
		initialValues2.put(KEY_RMEASURE, ingredientMeasurement.get(i));
		myDataBase.insert(LISTING_TABLE, null, initialValues2);	
	}
	
	if (recipie.getInstructions().length() > 0)
	{
		instructions(recipie.getInstructions(),id.intValue(),0);	
	}
	myDataBase.close();
	return id;
}
public long updateRecipie(Shine recipie)
{
	ContentValues initialValues = new ContentValues();
	initialValues.put(KEY_RECIPIENAME, recipie.getName());
	initialValues.put(KEY_RECIPIETYPE, recipie.gettypeOf());
	initialValues.put(KEY_RECIPIEMASHSIZE, recipie.getMashSize());
	initialValues.put(KEY_RECIPIESG, recipie.getSG());
	initialValues.put(KEY_RECIPIEUNITS, recipie.getUnits());
	myDataBase.update(RECIPIE_TABLE, initialValues,  KEY_RECIPIEID + "=" + recipie.getId(),null);

	ArrayList<String> ingredients = (ArrayList<String>) recipie.getIngredients();
	ArrayList<String> ingredientAmount = (ArrayList<String>) recipie.getIngredientAmount();
	ArrayList<String> ingredientMeasurement = (ArrayList<String>) recipie.getIngredientMeasure();
	ArrayList<Integer> ingRow = (ArrayList<Integer>) recipie.getingRowID();

	//insert data into recipielisting table
	for (int i = 0; i < ingredients.size(); i++) 
	{
		ContentValues initialValues2 = new ContentValues();
		//initialValues2.put(KEY_RRECIPIEID, ingRowID.get(i));
		initialValues2.put(KEY_RINGREDIENTID, ingredients.get(i));
		initialValues2.put(KEY_RAMOUNT, ingredientAmount.get(i));
		initialValues2.put(KEY_RMEASURE, ingredientMeasurement.get(i));
		myDataBase.update(LISTING_TABLE, initialValues2, KEY_RLISTINGID + "=" + ingRow.get(i), null);	
	}
	if (recipie.getInstructions().length() > 0)
	{
		instructions(recipie.getInstructions(),recipie.getId(),0);	
	}
	myDataBase.close();
	return 0;
	
}

public long seedrecipies(Context ctx)
{
	String Name = "Birdwatch";
	String Type = "Vodka";
	String Unit = "Standard";
	double SG = 1.08;
	int Mash = 20;
	Shine StartingRecipe = new Shine(ctx);
	
	StartingRecipe.setName(Name);
	StartingRecipe.settypeOf(Type);
	StartingRecipe.setMashSize(Mash);
	StartingRecipe.setUnits(Unit);
	StartingRecipe.setSG(SG);
	StartingRecipe.setInstructions("Be good until your are not");
	StartingRecipe.setIngredients("4", "20", "Lb","Sugar", false, ctx, 0,0);
	StartingRecipe.setIngredients("5", "10", "Oz","Sugar", false, ctx, 0,0);
	StartingRecipe.setInstructions("Carefully mix paste, juice, say 14 kg sugar with 60 liters water at 30C. Measure SG. Carefully add water and sugar to bring mixture to 80 liter, WITH A SG 1.09. Temperature of finished mixture should be 30C-35C to start.\n\nCarefully sprinkle 225 grams of yeast over surface, stirring in. Place cover loosely, to let CO2 escape, keeping flying nasties out. There is so much CO2 coming off; there is no need to worry about oxygen coming in contact. Place bottomless styrofoam box over fermenter. Dangle lit lightbulb through small hole in lid. Bulb must be strong enough to keep the mixture at a steady range of 30C-35C for entire fermentation. Size of bulb depends on room temperature. Stick your digital thermometer through side of box to track inside temperature.");
	StartingRecipe.save(ctx, false);

	return 0;	
}

public Cursor getAllRecipies()
{
	
	return myDataBase.query(TABLE_NAME, new String[] {KEY_RECIPIEID, KEY_RECIPIENAME}, KEY_RECIPIENAME + "<> 'temp'", null, null, null,null);
	
}
public Cursor getAllIngredients()
{
	return myDataBase.query(INGREDIENT_TABLE, new String[] {KEY_IID, KEY_IGNAME, KEY_IGTYPE}, null, null, null, null,KEY_IGTYPE);
}

public int deleteIng(String name)
{
	
	final String GET_GRAINS = "select name from ingredient inner join [recipielisting] on [recipielisting].[ingredientid] = [ingredient].[_id] where name =?"; 
	Cursor c =  myDataBase.rawQuery(GET_GRAINS, new String[]{name}); 
	
	if (c.getCount() > 0)
	{
		return 1;
		
	}
	else
	{
	final String DEL_RECIPIE = "delete from ingredient where name='" + name + "'"; 
	//myDataBase.rawQuery(DEL_RECIPIE, new String[]{Integer.toString((Recipe.getId()))}); 
	myDataBase.execSQL(DEL_RECIPIE);
	}
	return 0;
	
	
}

public void remove(Shine Recipe)
{
	final String DEL_RECIPIE = "delete from recipies where _id='" + Integer.toString((Recipe.getId())) + "'"; 
	//myDataBase.rawQuery(DEL_RECIPIE, new String[]{Integer.toString((Recipe.getId()))}); 
	myDataBase.execSQL(DEL_RECIPIE);
	final String DEL_LISTING = "delete from recipielisting where recipieID='" + Integer.toString((Recipe.getId())) + "'"; 
	//myDataBase.rawQuery(DEL_LISTING, new String[]{Integer.toString((Recipe.getId()))}); 
	myDataBase.execSQL(DEL_LISTING);
	final String DEL_INSTRUCT = "delete from instructions where recipieID='" + Integer.toString((Recipe.getId())) + "'"; 
	//myDataBase.rawQuery(DEL_INSTRUCT, new String[]{Integer.toString((Recipe.getId()))}); 
	myDataBase.execSQL(DEL_INSTRUCT);
	
}


public void createDataBase() throws IOException{     	
	boolean dbExist = checkDataBase();     	
	if(dbExist){    		//do nothing - database already exist    
		}else{     		
			//By calling this method and empty database will be created into the default system path              
			//of your application so we are gonna be able to overwrite that database with our database.   
			this.getReadableDatabase();         	
			try {     			
				copyDataBase();     		
				} 
			catch (IOException e) 
			{         		
				throw new Error("Error copying database");         
				}    	
			}    
	}
	

private boolean checkDataBase(){     	
	SQLiteDatabase checkDB = null;     	
	try{    		
		String myPath = DB_PATH + DATABASE_NAME;    		
		checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);     	
		}catch(SQLiteException e)
		{     		//database does't exist yet.     	
			
		}     	
	if(checkDB != null){     		
		checkDB.close();     
		}     	
	return checkDB != null ? true : false;    
	}

private void copyDataBase() throws IOException{     	
	//Open your local db as the input stream    	
	InputStream myInput = myContext.getAssets().open(DATABASE_NAME);     	
	// Path to the just created empty db    	
	String outFileName = DB_PATH + DATABASE_NAME;     	
	//Open the empty db as the output stream    	
	OutputStream myOutput = new FileOutputStream(outFileName);     	
	//transfer bytes from the inputfile to the outputfile    	
	byte[] buffer = new byte[1024];    
	int length;    
	while ((length = myInput.read(buffer))>0){    		
		myOutput.write(buffer, 0, length);    	
		}     	//Close the streams    	
	myOutput.flush();    	
	myOutput.close();    	
	myInput.close();     
	}
  


public void onCreate(SQLiteDatabase db) 
{ 	
	} 	
public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
{ 	
	}     
// Add your public helper methods to access and get content from the database.       
// You could return cursors by doing "return myDataBase.query(....)" so it'd be easy       
// to you to create adapters for your views. }	
private static class DatabaseHelper extends SQLiteOpenHelper
{
		DatabaseHelper(Context context)
		{
			super(context,DATABASE_NAME, null, DATABASE_VERSION);
			
		}
		@Override
		public void onCreate(SQLiteDatabase db)
		{
	//		db.execSQL(CREATE_RECIPIE);
		//	db.execSQL(CREATE_RECIPIELISTING);
	//		db.execSQL(CREATE_INGREDIENT);
	//		db.execSQL(CREATE_INGREDIENTYPE);
	//		db.execSQL(CREATE_INSTRUCTION);
			
			
		}
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
		{
			Log.w("upgrade db helper", "Upgrading database from version " + oldVersion 
	                  + " to "
	                  + newVersion + ", which will destroy all old data");
	            db.execSQL("DROP TABLE IF EXISTS titles");
	            onCreate(db);
		}
}

}
