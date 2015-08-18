package com.fnp.androidjokes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class JokerActivity extends AppCompatActivity {

    public static final String JOKE_ACTIVITY_NAME = "com.fnp.androidjokes.activity.name";
    public static final String JOKE_EXTRA = "com.fnp.androidjokes.extra.joke";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.joker_activity_main);

        Bundle bundle = getIntent().getExtras();
        String name = bundle.getString(JOKE_ACTIVITY_NAME);
        if(name != null){
            getSupportActionBar().setTitle(name);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
