package com.esprit.jobhunter.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.esprit.jobhunter.Entity.Internship;

import java.util.ArrayList;

public class Database1Helper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "bookmark_internship_db";


    public Database1Helper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // create notes table
        db.execSQL(Internship.CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Internship.TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    public void insertInternship(Internship job) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(Internship.COLUMN_ID, job.getId());
        values.put(Internship.COLUMN_NAME, job.getName());
        values.put(Internship.COLUMN_LABEL, job.getLabel());
        values.put(Internship.COLUMN_DESCRIPTION, job.getDescription());
        values.put(Internship.COLUMN_START_DATE, job.getStart_date());
        values.put(Internship.COLUMN_DURATION, job.getDuration());
        values.put(Internship.COLUMN_EDUC_REC, job.getEduc_req());
        values.put(Internship.COLUMN_USER_ID, job.getUser_id());
        values.put(Internship.COLUMN_SKILLS, job.getSkills());
        values.put(Internship.COLUMN_COMPANY_NAME, job.getCompany_name());
        values.put(Internship.COLUMN_COMPANY_PIC, job.getCompany_pic());
        // insert row
        long id = db.insert(Internship.TABLE_NAME, null, values);
        // close db connection
        db.close();
    }

    public Internship getInternship(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Internship.TABLE_NAME,
                new String[]{Internship.COLUMN_ID, Internship.COLUMN_NAME, Internship.COLUMN_LABEL, Internship.COLUMN_DESCRIPTION, Internship.COLUMN_START_DATE, Internship.COLUMN_DURATION, Internship.COLUMN_EDUC_REC, Internship.COLUMN_USER_ID,Internship.COLUMN_SKILLS, Internship.COLUMN_COMPANY_NAME, Internship.COLUMN_COMPANY_PIC },
                Internship.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare note object
        Internship job = new Internship(
                cursor.getInt(cursor.getColumnIndex(Internship.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(Internship.COLUMN_NAME)),
                cursor.getString(cursor.getColumnIndex(Internship.COLUMN_LABEL)),
                cursor.getString(cursor.getColumnIndex(Internship.COLUMN_DESCRIPTION)),
                cursor.getString(cursor.getColumnIndex(Internship.COLUMN_START_DATE)),
                cursor.getString(cursor.getColumnIndex(Internship.COLUMN_DURATION)),
                cursor.getString(cursor.getColumnIndex(Internship.COLUMN_EDUC_REC)),
                cursor.getString(cursor.getColumnIndex(Internship.COLUMN_USER_ID)),
                cursor.getString(cursor.getColumnIndex(Internship.COLUMN_SKILLS)),
                cursor.getString(cursor.getColumnIndex(Internship.COLUMN_COMPANY_NAME)),
                cursor.getString(cursor.getColumnIndex(Internship.COLUMN_COMPANY_PIC)),
                cursor.getString(cursor.getColumnIndex(Internship.COLUMN_TIMESTAMP))
        );

        // close the db connection
        cursor.close();

        return job;
    }

    public ArrayList<Internship> getAllInternships() {
        ArrayList<Internship> jobs = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Internship.TABLE_NAME + " ORDER BY " +
                Internship.COLUMN_TIMESTAMP + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Internship job = new Internship();
                job.setId(cursor.getInt(cursor.getColumnIndex(Internship.COLUMN_ID)));
                job.setName(cursor.getString(cursor.getColumnIndex(Internship.COLUMN_NAME)));
                job.setLabel(cursor.getString(cursor.getColumnIndex(Internship.COLUMN_LABEL)));
                job.setDescription(cursor.getString(cursor.getColumnIndex(Internship.COLUMN_DESCRIPTION)));
                job.setStart_date(cursor.getString(cursor.getColumnIndex(Internship.COLUMN_START_DATE)));
                job.setDuration(cursor.getString(cursor.getColumnIndex(Internship.COLUMN_DURATION)));
                job.setEduc_req(cursor.getString(cursor.getColumnIndex(Internship.COLUMN_EDUC_REC)));
                job.setUser_id(cursor.getString(cursor.getColumnIndex(Internship.COLUMN_USER_ID)));
                job.setSkills(cursor.getString(cursor.getColumnIndex(Internship.COLUMN_SKILLS)));
                job.setCompany_name(cursor.getString(cursor.getColumnIndex(Internship.COLUMN_COMPANY_NAME)));
                job.setCompany_pic(cursor.getString(cursor.getColumnIndex(Internship.COLUMN_COMPANY_PIC)));
                job.setInserted_sq_date(cursor.getString(cursor.getColumnIndex(Internship.COLUMN_TIMESTAMP)));

                jobs.add(job);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return jobs;
    }

    public int getInternshipsCount() {
        String countQuery = "SELECT  * FROM " + Internship.TABLE_NAME;
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

    public void deleteInternship(Internship job) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Internship.TABLE_NAME, Internship.COLUMN_ID + " = ?",
                new String[]{String.valueOf(job.getId())});
        db.close();
    }
}
