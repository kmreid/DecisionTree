package net.kreid.decisiontree;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class GuessWrongActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_wrong);
    }

    @Override
    protected void onResume() {
        super.onResume();

        MainActivity.sounds.PlaySound(Sounds.SoundType.WRONG);

        final Button submitNewAnswerBtn = (Button) findViewById(R.id.submitNewAnswerBtn);
        submitNewAnswerBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                MainActivity.sounds.PlaySound(Sounds.SoundType.BUTTON_CLICK);

                EditText ansText = (EditText)findViewById(R.id.newAnswerText);
                String newA = ansText.getText().toString();

                EditText qText = (EditText)findViewById(R.id.newQuestionText);
                String newQ = qText.getText().toString();


                if(newA.isEmpty() || newQ.isEmpty())
                {
                    TextView tv = (TextView)findViewById(R.id.validateErrorText);
                    tv.setVisibility(View.VISIBLE);
                }
                else
                {
                    // Insert new answer
                    MainActivity.game.insertNewLeafNode(newQ, newA);
                    MainActivity.game.SaveState(getApplicationContext());

                    Intent intent = new Intent(getApplicationContext(), PlayAgainActivity.class);
                    startActivityForResult(intent, 0);
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(resultCode)
        {
            case MainActivity.RESULT_CLOSE_ALL:
                setResult(MainActivity.RESULT_CLOSE_ALL);
                finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_guess_wrong, menu);
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
        }

        return super.onOptionsItemSelected(item);
    }
}
