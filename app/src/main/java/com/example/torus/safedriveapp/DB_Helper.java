package com.example.torus.safedriveapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amy on 18/9/17.
 */

public class DB_Helper  extends SQLiteOpenHelper {
    public static  DB_Helper db_helper;
    SQLiteDatabase sqLiteDatabase;

    private  DB_Helper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public static DB_Helper getInstance(Context context)
    {
        if(db_helper==null)
        {

            db_helper=new DB_Helper(context,Constant.DBNAME,null,Constant.VERSION);
        }
        return  db_helper;
    }

    @Override
    public void onOpen(SQLiteDatabase sqLiteDatabase) {
        super.onOpen(sqLiteDatabase);
        this.sqLiteDatabase=sqLiteDatabase;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_USER_TABLE = "CREATE TABLE " + Constant.TABLENAME + "("
                + Constant.CALLID + " INTEGER  PRIMARY KEY AUTOINCREMENT ," + Constant.CALLERNAME + " TEXT[100],"
                + Constant.CALLERNUMBER + " TEXT[15],"+Constant.CALLTIME + " TEXT[100],"+Constant.CALLDATE + " TEXT[100]" + ")";
        sqLiteDatabase.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public List<CallDetails>fetch_all_user()
    {
        List<CallDetails> user_detailses=new ArrayList<>();
        try
        {
            sqLiteDatabase=db_helper.getWritableDatabase();
            String select_query="SELECT * from "+Constant.TABLENAME;
            Cursor cursor=sqLiteDatabase.rawQuery(select_query,null);
            if(cursor.moveToFirst())
            {
                do {
                    CallDetails user_details=new CallDetails();
                    user_details.setCaller_name(cursor.getString(1));
                    user_details.setCaller_number(cursor.getString(2));
                    user_details.setCall_time(cursor.getString(3));
                    user_details.setCall_date(cursor.getString(4));
                    user_detailses.add(user_details);
                }while (cursor.moveToNext());
            }
        }
        catch (Exception e)
        {
            Log.i("Fetch Error",e.getMessage());
        }
        return user_detailses;
    }

    public long add_call_details(String caller_name,String caller_number,String call_time,String  call_date)
    {
        sqLiteDatabase=db_helper.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        /*contentValues.put(Constant.USERIDKEY,1);*/
        contentValues.put(Constant.CALLERNAME,caller_name);
        contentValues.put(Constant.CALLERNUMBER,caller_number);
        contentValues.put(Constant.CALLTIME,call_time);
        contentValues.put(Constant.CALLDATE,call_date);
      long id=  sqLiteDatabase.insert(Constant.TABLENAME,null,contentValues);
        sqLiteDatabase.close();
        return  id;
    }
}
