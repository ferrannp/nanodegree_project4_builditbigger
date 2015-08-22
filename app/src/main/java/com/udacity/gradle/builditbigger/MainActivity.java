package com.udacity.gradle.builditbigger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.fnp.androidjokes.JokerActivity;
import com.udacity.gradle.builditbigger.gce.JokeEndpointAsyncTask;


public class MainActivity extends ActionBarActivity {

    private JokeEndpointAsyncTask jokeEndpointAsyncTask;
    private MainReceiver mainReceiver;
    private ProgressBar loadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainReceiver = new MainReceiver();

        loadingIndicator = (ProgressBar) findViewById(R.id.loading_indicator);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(JokeEndpointAsyncTask.ENDPOINT_INTENT);
        intentFilter.addAction(FlavorsInterface.INTERSTITIAL_CLOSED);
        registerReceiver(mainReceiver, intentFilter);
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

    @Override
    protected void onDestroy() {
        unregisterReceiver(mainReceiver);

        super.onDestroy();
        if(jokeEndpointAsyncTask != null &&
                jokeEndpointAsyncTask.getStatus() == AsyncTask.Status.RUNNING) {
            jokeEndpointAsyncTask.cancel(true);
            jokeEndpointAsyncTask = null;
        }
    }

    public void tellJoke(View view){
        MainActivityFragment fragment =
                (MainActivityFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        fragment.showInterstitialAd();
    }

    private class MainReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent != null) {
                if (intent.getAction().equals(JokeEndpointAsyncTask.ENDPOINT_INTENT)) {
                    loadingIndicator.setVisibility(View.GONE);

                    Intent activityIntent = new Intent(MainActivity.this, JokerActivity.class);
                    activityIntent.putExtra(JokerActivity.JOKE_ACTIVITY_NAME, getString(R.string.jokes));
                    activityIntent.putExtra(JokerActivity.JOKE_EXTRA,
                            intent.getStringExtra(JokeEndpointAsyncTask.ENDPOINT_RESULT));
                    startActivity(activityIntent);
                }
                else if(intent.getAction().equals(FlavorsInterface.INTERSTITIAL_CLOSED)){
                    loadingIndicator.setVisibility(View.VISIBLE);
                    jokeEndpointAsyncTask = new JokeEndpointAsyncTask();
                    jokeEndpointAsyncTask.execute(MainActivity.this);
                }
            }
        }
    }
}
