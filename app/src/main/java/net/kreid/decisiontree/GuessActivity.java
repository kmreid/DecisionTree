package net.kreid.decisiontree;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class GuessActivity extends ActionBarActivity {

    private Game.Direction GuessedDirection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess);

//        Bundle extras = getIntent().getExtras();
//        if (extras != null) {
//            GuessedDirection = Game.Direction.valueOf(extras.getString("direction"));
//
//
//            if(GuessedDirection == Game.Direction.YES)
//            {
//
//            }
//            else
//            {
//
//            }
//
//        }

        Node next = MainActivity.game.getCurrentNode();
        String guess = next.getText();
        final TextView guessText = (TextView) findViewById(R.id.guessText);
        guessText.setText(guess);
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
    protected void onResume() {
        super.onResume();

        final Button correctBtn = (Button) findViewById(R.id.correctBtn);
        correctBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Guessed right
                // Show computer win view
                Intent intent = new Intent(getApplicationContext(), GuessRightActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        final Button wrongBtn = (Button) findViewById(R.id.wrongBtn);
        wrongBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Guessed wrong
                // Show computer fail view
                Intent intent = new Intent(getApplicationContext(), GuessWrongActivity.class);
                startActivityForResult(intent, 0);
            }
        });
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();

//        if(GuessedDirection == Game.Direction.YES)
//        {
//            overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
//        }
//        else
//        {
//            overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_guess, menu);
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
