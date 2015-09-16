package sachinshinde.mynotemaker;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private ArrayList temp  = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            //String[] cars = {"dodge","toyota","bmw","merc"};
        ArrayList<String> noteslist = new ArrayList<>();
        DatabaseHandler db = new DatabaseHandler(this);

        /**
         * CRUD Operations
         * */
        // Inserting Contacts
        Log.d("Insert: ", "Inserting ..");
        /*db.addMyNote(new MyNote("note1", "asdfsa/asfdsdf/"));
        db.addMyNote(new MyNote("note2", "hgjghj/ewqrwe/"));
        db.addMyNote(new MyNote("note3", "weqtrewt/erwert/"));
        db.addMyNote(new MyNote("note4", "nbbv/dfgfj/"));*/


        // Reading all contacts
        Log.d("Reading: ", "Reading all contacts..");
        List<MyNote> notes = db.getAllContacts();

        for (MyNote note : notes) {
            String log = "Id: "+note.getId()+" ,Note: " + note.getNote() + " ,Path: " + note.getPath();
            // Writing notes to log
            temp.add(note.getId());
            noteslist.add(note.getNote());
            Log.d("Note: ", log);
        }

        ListView listView = (ListView)findViewById(R.id.noteslist);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,R.layout.list_item,R.id.txt,noteslist);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new ItemList());




    }
    public class ItemList implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            ViewGroup vg = (ViewGroup)view;
            TextView textView = (TextView)vg.findViewById(R.id.txt);
            //Toast.makeText(MainActivity.this, textView.getText().toString(), Toast.LENGTH_SHORT).show();

           Intent intent = new Intent("mynotemaker.NoteInfo");

            Bundle extras = new Bundle();
            extras.putString("mynote", textView.getText().toString());
            extras.putString("noteId", temp.get(position).toString());
            intent.putExtras(extras);
            startActivityForResult(intent, 1001);



        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    public void openMyCam(MenuItem item) {
        Intent intent = new Intent("mynotemaker.addnote");

        startActivityForResult(intent, 1002);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == NoteInfoActivity.DELETE_NOTE_RESULT_CODE){
            finish();
            startActivity(new Intent(this,MainActivity.class));

        }
        if(requestCode == 1002){
            finish();
            startActivity(new Intent(this,MainActivity.class));

        }
    }
}
