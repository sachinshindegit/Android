package sachinshinde.mynotemaker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sachin Shinde on 9/11/2015.
 */
public class DatabaseHandler extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "myNoteMakerDB";
    private static  final String TABLE_MYNOTE = "mynote";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String NOTE = "note";
    private static final String PATH = "path";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_MYNOTE_TABLE = "CREATE TABLE " + TABLE_MYNOTE + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + NOTE + " TEXT,"
                + PATH + " TEXT" + ")";
        db.execSQL(CREATE_MYNOTE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MYNOTE);
        // Create tables again
        onCreate(db);
    }

    // adding new note
    public void addMyNote(MyNote note){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NOTE, note.getNote()); // note
        values.put(PATH, note.getPath()); // path

        // Inserting Row
        db.insert(TABLE_MYNOTE, null, values);
        db.close(); // Closing database connection

    }

    // Getting single contact
    public MyNote getMyNote(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_MYNOTE, new String[] { KEY_ID,
                        NOTE, PATH }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        MyNote myNote = new MyNote(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2));
        // return contact
        return myNote;
    }

    // getting all notes
    // Getting All Contacts
    public List<MyNote> getAllContacts() {
        List<MyNote> contactList = new ArrayList<MyNote>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_MYNOTE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                MyNote myNote = new MyNote();
                myNote.setId(Integer.parseInt(cursor.getString(0)));
                myNote.setNote(cursor.getString(1));
                myNote.setPath(cursor.getString(2));
                // Adding contact to list
                contactList.add(myNote);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }

    // Updating single RECORD
    public int updateMyNote(MyNote myNote) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NOTE, myNote.getNote());
        values.put(PATH, myNote.getPath());

        // updating row
        return db.update(TABLE_MYNOTE, values, KEY_ID + " = ?",
                new String[] { String.valueOf(myNote.getId()) });
    }

    // Deleting single record
    public void deleteMyNote(MyNote myNote) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MYNOTE, KEY_ID + " = ?",
                new String[]{String.valueOf(myNote.getId())});
        db.close();
    }

    // Getting contacts Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_MYNOTE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }
}
