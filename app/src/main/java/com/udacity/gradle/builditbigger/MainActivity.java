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

import com.fnp.androidjokes.JokerActivity;
import com.udacity.gradle.builditbigger.gce.JokeEndpointAsyncTask;


public class MainActivity extends ActionBarActivity {

    private JokeEndpointAsyncTask jokeEndpointAsyncTask;
    private EndpointReceiver endpointReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        endpointReceiver = new EndpointReceiver();
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

    public void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter(JokeEndpointAsyncTask.INTENT);
        registerReceiver(endpointReceiver, filter);
    }

    public void onPause() {
        super.onPause();
        unregisterReceiver(endpointReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(jokeEndpointAsyncTask.getStatus() == AsyncTask.Status.RUNNING) {
            jokeEndpointAsyncTask.cancel(true);
            jokeEndpointAsyncTask = null;
        }
    }

    public void tellJoke(View view){
        jokeEndpointAsyncTask = new JokeEndpointAsyncTask();
        jokeEndpointAsyncTask.execute(this);
    }

    private class EndpointReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent != null && intent.hasExtra(JokeEndpointAsyncTask.ENDPOINT_RESULT)){
                Intent activityIntent = new Intent(MainActivity.this, JokerActivity.class);
                activityIntent.putExtra(JokerActivity.JOKE_ACTIVITY_NAME, getString(R.string.jokes));
                activityIntent.putExtra(JokerActivity.JOKE_EXTRA,
                        intent.getStringExtra(JokeEndpointAsyncTask.ENDPOINT_RESULT));
                startActivity(activityIntent);
            }
        }
    }
}
