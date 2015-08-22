package com.udacity.gradle.builditbigger.gce;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.fnp.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

public class JokeEndpointAsyncTask extends AsyncTask<Context, Void, String> {

    public static final String ENDPOINT_INTENT = "com.fnp.backend.intent.endpoint";
    public static final String ENDPOINT_RESULT = "com.fnp.backend.endpoint.response";

    private static MyApi myApiService = null;
    private Context context;

    @Override
    protected String doInBackground(Context ...params) {
        if(myApiService == null) {
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.3.2 is localhost's IP address in Genymotion emulator
                    // - turn off compression when running against local devappserver
                    //.setRootUrl("https://10.0.3.2:8080/_ah/api/")
                    .setRootUrl("https://builditbigger-fnp.appspot.com/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            myApiService = builder.build();
        }

        context = params[0];

        try {
            return myApiService.tellJoke().execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        Intent intent = new Intent(ENDPOINT_INTENT);
        intent.putExtra(ENDPOINT_RESULT, result);
        context.sendBroadcast(intent);
    }
}