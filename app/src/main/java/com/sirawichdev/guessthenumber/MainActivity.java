package com.sirawichdev.guessthenumber;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;
import java.util.Random;


public class MainActivity extends Activity {
    Locale myLocale;
    int randomNumber;
    int guesses;
    Spinner spinnerctrl;
    public TextView title;
    public TextView guessCounter;
    String titleText;

    public void guessCommence (View view) {

        EditText guessedNumber = (EditText) findViewById(R.id.guess);

        String guessedNumberString = guessedNumber.getText().toString();

        int guessedNumberInt = Integer.parseInt(guessedNumberString);

        String toastMessage = "";
        title = (TextView) findViewById(R.id.title);
        Context c = MainActivity.this;
        //This is where the magic happens
        if (guessedNumberInt == randomNumber && guesses <= 5) {
            guesses++;
            toastMessage = c.getResources().getString(R.string.right) + randomNumber + c.getResources().getString(R.string.its) + guesses +c.getResources().getString(R.string.time) ;
            titleText = c.getResources().getString(R.string.news);

            Random randomGenerator = new Random();
            randomNumber = randomGenerator.nextInt(50);
            guesses = 0;

        }
        else if (guessedNumberInt == randomNumber+3 || guessedNumberInt == randomNumber-3 && guesses <= 5 ) {
            toastMessage = c.getResources().getString(R.string.near);
            titleText = guessedNumberInt +" "+  c.getResources().getString(R.string.nearest);
            guesses++;
        }
        else if (guessedNumberInt > randomNumber && guesses <= 5) {
            toastMessage =c.getResources().getString(R.string.high) ;
            titleText = guessedNumberInt +" "+  c.getResources().getString(R.string.high);
            guesses++;
        }

        else if (guessedNumberInt < randomNumber && guesses <= 5) {
            toastMessage = c.getResources().getString(R.string.low) ;
            titleText = guessedNumberInt +" "+ c.getResources().getString(R.string.low) ;
            guesses++;

        }

        if(guesses > 5) {
            toastMessage = c.getResources().getString(R.string.more);
            titleText = c.getResources().getString(R.string.trys);

            Random randomGenerator = new Random();
            randomNumber = randomGenerator.nextInt(50);
            guesses = 0;
        }

        Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_LONG).show();
        title.setText(titleText);
        guessedNumber.setText("");
        guessCounter = (TextView) findViewById(R.id.guessCounter);
        guessCounter.setText(c.getResources().getString(R.string.guess) + guesses);

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
        spinnerctrl = (Spinner) findViewById(R.id.spinner1);
        spinnerctrl.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {

                if (pos == 1) {

                    Toast.makeText(parent.getContext(),
                            "You have selected thai", Toast.LENGTH_SHORT)
                            .show();
                    setLocale("th");
                } else if (pos == 2) {

                    Toast.makeText(parent.getContext(),
                            "You have selected english", Toast.LENGTH_SHORT)
                            .show();
                    setLocale("en");
                }

            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }

        });

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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }



}
