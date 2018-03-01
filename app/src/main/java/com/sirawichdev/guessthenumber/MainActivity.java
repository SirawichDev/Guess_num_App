package com.sirawichdev.guessthenumber;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;
import java.util.Random;


public class MainActivity extends Activity {
    Locale myLocale;
    int randomNumber;
    int guesses;
    public TextView title;
    public TextView guessCounter;
    String titleText;

    public void guessCommence (View view) {

        EditText guessedNumber = (EditText) findViewById(R.id.guess);

        String guessedNumberString = guessedNumber.getText().toString();

        int guessedNumberInt = Integer.parseInt(guessedNumberString);

        String toastMessage = "";
        title = (TextView) findViewById(R.id.title);

        //This is where the magic happens
        if (guessedNumberInt == randomNumber && guesses <= 5) {
            guesses++;
            toastMessage = "You're right! I was thinking of " + randomNumber + ". It took you " + guesses + " guesses!";
            titleText = "New Game. Enter Your Guess Below!";

            Random randomGenerator = new Random();
            randomNumber = randomGenerator.nextInt(50);
            guesses = 0;

        }
        else if (guessedNumberInt == randomNumber+3 || guessedNumberInt == randomNumber-3 && guesses <= 5 ) {
            toastMessage = "Nearest!. Guess More";
            titleText = guessedNumberInt + " Nearest!.";
            guesses++;
        }
        else if (guessedNumberInt > randomNumber && guesses <= 5) {
            toastMessage = "Too High. Guess Lower.";
            titleText = guessedNumberInt + " is too High. Guess Lower.";
            guesses++;
        }

        else if (guessedNumberInt < randomNumber && guesses <= 5) {
            toastMessage = "Too Low. Guess Higher.";
            titleText = guessedNumberInt + " is too Low. Guess Higher.";
            guesses++;

        }

        if(guesses > 5) {
            toastMessage = "You're guesses More than 5 Time It's Over :3 ";
            titleText = "It's Game Over Les't try New Game.";

            Random randomGenerator = new Random();
            randomNumber = randomGenerator.nextInt(50);
            guesses = 0;
        }
        Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_LONG).show();
        title.setText(titleText);
        guessedNumber.setText("");
        guessCounter = (TextView) findViewById(R.id.guessCounter);
        guessCounter.setText("Guesses: " + guesses);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Random randomGenerator = new Random();
        randomNumber = randomGenerator.nextInt(50);
        guesses = 0;
        Button bt = (Button)findViewById(R.id.guessButton);
        bt.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                guessCommence(v);
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
            setLocale("en");
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
    public void setLocale(String lang) {

        myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(this, MainActivity.class);
        startActivity(refresh);

    }
}
