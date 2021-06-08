package com.example.sqlitequiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class StudentsRecordDB {
    public static final String KEY_STUDENTNAME = "Student_Name";
    public static final String KEY_ROLLNUMBER = "Roll_Number";
    public static final String KEY_SEMESTER = "_Semester";
    public static final String KEY_DEGREE = "_Degree";
    public static final String KEY_CONTACTNUMBER = "Contact_Number";
    public static final String KEY_EMAILADDRESS = "Email_Address";

    private final String DATABASE_NAME = "StudentsRecordDB";
    private final String DATABASE_TABLE = "StudentsTable";
    private final int DATABASE_VERSION = 2;

    private DBHelper ourHelper;
    private final Context ourContext;
    private SQLiteDatabase ourDatabase;

    public StudentsRecordDB(Context context)
    {
        ourContext = context;
    }

    private class DBHelper extends SQLiteOpenHelper
    {
        public DBHelper(Context context)
        {
            super(context, DATABASE_NAME,null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
                String sqlCode = " CREATE TABLE "+DATABASE_TABLE+"("+KEY_STUDENTNAME+" TEXT NOT NULL,"+KEY_ROLLNUMBER+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                        +KEY_SEMESTER+" TEXT NOT NULL,"
                        +KEY_DEGREE+" TEXT NOT NULL,"
                        +KEY_CONTACTNUMBER+" TEXT NOT NULL,"
                        +KEY_EMAILADDRESS+" TEXT NOT NULL);";
                db.execSQL(sqlCode);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                db.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE);
                onCreate(db);
        }
    }
    public StudentsRecordDB open()
    {
        ourHelper = new DBHelper(ourContext);
        ourDatabase = ourHelper.getWritableDatabase();
        return this;
    }

    public void close()
    {
        ourHelper.close();
    }

    public long createEntry(String studentName, String semester, String degree, String contactNumber, String emailAddress)
    {
        ContentValues cv = new ContentValues();
        cv.put(KEY_STUDENTNAME, studentName);
        cv.put(KEY_SEMESTER, semester);
        cv.put(KEY_DEGREE, degree);
        cv.put(KEY_CONTACTNUMBER, contactNumber);
        cv.put(KEY_EMAILADDRESS, emailAddress);
        return ourDatabase.insert(DATABASE_TABLE, null, cv);
    }

    public String getData()
    {
        String []columns = new String[]{KEY_STUDENTNAME, KEY_ROLLNUMBER, KEY_SEMESTER, KEY_DEGREE, KEY_CONTACTNUMBER, KEY_EMAILADDRESS};
        Cursor c = ourDatabase.query(DATABASE_TABLE, columns, null, null, null, null, null);
        String result = "";
        int iStudentName = c.getColumnIndex(KEY_STUDENTNAME);
        int iRollNumber = c.getColumnIndex(KEY_ROLLNUMBER);
        int iSemester = c.getColumnIndex(KEY_SEMESTER);
        int iDegree = c.getColumnIndex(KEY_DEGREE);
        int iContactNumber = c.getColumnIndex(KEY_CONTACTNUMBER);
        int iEmailAddress = c.getColumnIndex(KEY_EMAILADDRESS);
        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                result = result + c.getString(iStudentName) + "  " + c.getString(iRollNumber) + "  "+c.getString(iSemester) + "  " + c.getString(iDegree)
                + "  " + c.getString(iContactNumber) + "  " + c.getString(iEmailAddress) + "\n";
        }

        c.close();
        return result;
    }
    public long deleteEntry(String rollNumber)
    {
        return ourDatabase.delete(DATABASE_TABLE, KEY_ROLLNUMBER+"=?", new String[]{rollNumber});
    }

    public long updateEntry(String name, String rollNumber, String semester, String degree, String contactNumber, String emailAddress)
    {
        ContentValues cv = new ContentValues();
        cv.put(KEY_STUDENTNAME, name);
        cv.put(KEY_ROLLNUMBER, rollNumber);
        cv.put(KEY_SEMESTER, semester);
        cv.put(KEY_DEGREE, semester);
        cv.put(KEY_CONTACTNUMBER, semester);
        cv.put(KEY_EMAILADDRESS, emailAddress);
        return ourDatabase.update(DATABASE_TABLE, cv, KEY_ROLLNUMBER+"=?", new String[]{rollNumber});
    }
}
