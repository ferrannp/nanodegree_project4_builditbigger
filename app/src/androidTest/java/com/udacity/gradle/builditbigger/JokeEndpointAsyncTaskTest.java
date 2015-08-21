package com.udacity.gradle.builditbigger;

import android.test.AndroidTestCase;
import android.test.mock.MockContext;
import com.udacity.gradle.builditbigger.gce.JokeEndpointAsyncTask;
import java.util.concurrent.CountDownLatch;

public class JokeEndpointAsyncTaskTest extends AndroidTestCase {

    private final CountDownLatch signal = new CountDownLatch(1);

    private class TestJokeAsyncTask extends JokeEndpointAsyncTask{
        @Override
        protected void onPostExecute(String result) {
            assertTrue("Result should not be empty", result != null && result.length() > 0);
            signal.countDown(); //Release the signal and let the test end
        }
    }

    public void testAsyncTaskResponse() throws Throwable {

        final TestJokeAsyncTask testJokeAsyncTask = new TestJokeAsyncTask();
        testJokeAsyncTask.execute(new MockContext());
        signal.await(); //Wait till AsyncTask finishes
    }
}
