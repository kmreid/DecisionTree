package net.kreid.decisiontree;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity{

    public static Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState == null){
            game = new Game();
            game.LoadState(getApplicationContext());
        }

        setContentView(R.layout.activity_main);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
     public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Parcel up tree
//        outState.putParcelable("root", game.getRoot());
//
//        // Parcel up steps history
//        List<String> histStrList = new ArrayList<String>();
//        for (Game.Direction dir : game.getStepsHistory())
//        {
//            histStrList.add(dir.toString());
//        }
//        outState.putStringArray("steps", (String[]) histStrList.toArray());
//
//        // Parcel up current text
//        outState.putString("text", game.getCurrentNode().getText());
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    protected void onResume()
    {
        super.onResume();

        final TextView questionText = (TextView) findViewById(R.id.question_text);
        questionText.setText(game.getCurrentNode().getText());

        final Button yesButton = (Button) findViewById(R.id.button_yes);
        yesButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                game.goYes();
                if(game.atLeaf())
                {
                    // Show computer guessing view
                    Intent intent = new Intent(getApplicationContext(), GuessActivity.class);
                    intent.putExtra("direction", Game.Direction.YES.toString());
                    startActivity(intent);
                    overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                }
                else
                {

                    questionText.setText(game.getCurrentNode().getText());
                }
            }
        });

        final Button noButton = (Button) findViewById(R.id.button_no);
        noButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                game.goNo();
                if(game.atLeaf())
                {
                    Intent intent = new Intent(getApplicationContext(), GuessActivity.class);
                    intent.putExtra("direction", Game.Direction.NO.toString());
                    startActivity(intent);
                    overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                }
                else
                {

                    questionText.setText(game.getCurrentNode().getText());
                }
            }
        });

        final Button exitButton = (Button) findViewById(R.id.button_exit);
        exitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                game.SaveState(getApplicationContext());
                finish(); // TODO - call finish on all activities
                //System.exit(0);
            }
        });
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
