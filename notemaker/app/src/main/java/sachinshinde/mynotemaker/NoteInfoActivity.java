package sachinshinde.mynotemaker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class NoteInfoActivity extends AppCompatActivity {
    public static final int DELETE_NOTE_RESULT_CODE = 1001;
    DatabaseHandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_note_info);
        db = new DatabaseHandler(this);
        TextView t1 = (TextView)findViewById(R.id.notedetails);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        t1.setText(bundle.getString("mynote"));
        final String noteId = bundle.get("noteId").toString();

        ImageView imageView  = (ImageView)findViewById(R.id.noteImage);
        imageView.setImageResource(R.drawable.dontfrgetimg);

        final Button button = (Button) findViewById(R.id.deleteNoteButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                EditText text = (EditText) findViewById(R.id.myNote);
                db.deleteMyNote(new MyNote(Integer.parseInt(noteId)));
                finish();
                setResult(DELETE_NOTE_RESULT_CODE);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_note_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }else if(id == android.R.id.home){
            finish();     // This means when clicked in the arrow, close this activity and return to previos activity
        }

        return super.onOptionsItemSelected(item);
    }
}
