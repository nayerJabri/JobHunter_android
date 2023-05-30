package com.esprit.jobhunter.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.esprit.jobhunter.Entity.Job;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "bookmark_jo_db";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        // create notes table
        db.execSQL(Job.CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Job.TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    public void deleteDB(){
        this.getWritableDatabase().execSQL("DROP TABLE jobs");
    }
    public void addDB(){
        this.getWritableDatabase().execSQL(Job.CREATE_TABLE);
    }

    public void insertJob(Job job) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(Job.COLUMN_ID, job.getId());
        values.put(Job.COLUMN_NAME, job.getName());
        values.put(Job.COLUMN_LABEL, job.getLabel());
        values.put(Job.COLUMN_DESCRIPTION, job.getDescription());
        values.put(Job.COLUMN_START_DATE, job.getStart_date());
        values.put(Job.COLUMN_CONTRACT_TYPE, job.getContract_type());
        values.put(Job.COLUMN_CAREER_REQ, job.getCareer_req());
        values.put(Job.COLUMN_USER_ID, job.getUser_id());
        values.put(Job.COLUMN_SKILLS, job.getSkills());
        values.put(Job.COLUMN_COMPANY_NAME, job.getCompany_name());
        values.put(Job.COLUMN_COMPANY_PIC, job.getCompany_pic());
        values.put(Job.COLUMN_TYPE, job.getType());
        // insert row
        long id = db.insert(Job.TABLE_NAME, null, values);
        // close db connection
        db.close();
    }

    public Job getJob(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Job.TABLE_NAME,
                new String[]{Job.COLUMN_ID, Job.COLUMN_NAME, Job.COLUMN_LABEL, Job.COLUMN_DESCRIPTION, Job.COLUMN_START_DATE, Job.COLUMN_CONTRACT_TYPE, Job.COLUMN_CAREER_REQ, Job.COLUMN_USER_ID, Job.COLUMN_SKILLS, Job.COLUMN_COMPANY_NAME, Job.COLUMN_COMPANY_PIC, Job.COLUMN_TYPE },
                Job.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare note object
        Job job = new Job(
                cursor.getInt(cursor.getColumnIndex(Job.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(Job.COLUMN_NAME)),
                cursor.getString(cursor.getColumnIndex(Job.COLUMN_LABEL)),
                cursor.getString(cursor.getColumnIndex(Job.COLUMN_DESCRIPTION)),
                cursor.getString(cursor.getColumnIndex(Job.COLUMN_START_DATE)),
                cursor.getString(cursor.getColumnIndex(Job.COLUMN_CONTRACT_TYPE)),
                cursor.getString(cursor.getColumnIndex(Job.COLUMN_CAREER_REQ)),
                cursor.getString(cursor.getColumnIndex(Job.COLUMN_USER_ID)),
                cursor.getString(cursor.getColumnIndex(Job.COLUMN_SKILLS)),
                cursor.getString(cursor.getColumnIndex(Job.COLUMN_COMPANY_NAME)),
                cursor.getString(cursor.getColumnIndex(Job.COLUMN_COMPANY_PIC)),
                cursor.getString(cursor.getColumnIndex(Job.COLUMN_TIMESTAMP))
        );

        // close the db connection
        cursor.close();

        return job;
    }

    public ArrayList<Job> getAllJobs() {
        ArrayList<Job> jobs = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Job.TABLE_NAME + " ORDER BY " +
                Job.COLUMN_TIMESTAMP + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Job job = new Job();
                job.setId(cursor.getInt(cursor.getColumnIndex(Job.COLUMN_ID)));
                job.setName(cursor.getString(cursor.getColumnIndex(Job.COLUMN_NAME)));
                job.setLabel(cursor.getString(cursor.getColumnIndex(Job.COLUMN_LABEL)));
                job.setDescription(cursor.getString(cursor.getColumnIndex(Job.COLUMN_DESCRIPTION)));
                job.setStart_date(cursor.getString(cursor.getColumnIndex(Job.COLUMN_START_DATE)));
                job.setContract_type(cursor.getString(cursor.getColumnIndex(Job.COLUMN_CONTRACT_TYPE)));
                job.setCareer_req(cursor.getString(cursor.getColumnIndex(Job.COLUMN_CAREER_REQ)));
                job.setUser_id(cursor.getString(cursor.getColumnIndex(Job.COLUMN_USER_ID)));
                job.setSkills(cursor.getString(cursor.getColumnIndex(Job.COLUMN_SKILLS)));
                job.setCompany_name(cursor.getString(cursor.getColumnIndex(Job.COLUMN_COMPANY_NAME)));
                job.setCompany_pic(cursor.getString(cursor.getColumnIndex(Job.COLUMN_COMPANY_PIC)));
                job.setInserted_sq_date(cursor.getString(cursor.getColumnIndex(Job.COLUMN_TIMESTAMP)));

                jobs.add(job);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return jobs;
    }

    public int getJobsCount() {
        String countQuery = "SELECT  * FROM " + Job.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        // return count
        return count;
    }

    /*public int updateNote(Job note) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Job.COLUMN_NOTE, note.getNote());

        // updating row
        return db.update(Job.TABLE_NAME, values, Job.COLUMN_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
    }*/

    public void deleteJob(Job job) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Job.TABLE_NAME, Job.COLUMN_ID + " = ?",
                new String[]{String.valueOf(job.getId())});
        db.close();
    }
}
