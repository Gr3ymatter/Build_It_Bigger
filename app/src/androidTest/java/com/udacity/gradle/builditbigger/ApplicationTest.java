package com.udacity.gradle.builditbigger;

import android.app.Application;
import android.content.Context;
import android.test.ApplicationTestCase;

import java.util.concurrent.CountDownLatch;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    CountDownLatch signal = null;
    String testResult = null;
    @Override
    protected void tearDown() throws Exception {
        signal.countDown();
    }

    @Override
    protected void setUp() throws Exception {
        signal = new CountDownLatch(1);
    }

    public void testAsyncTask()throws InterruptedException{
        EndpointsAsyncTask task = new EndpointsAsyncTask();

        task.setListener(new EndpointsAsyncTask.CallbackListener() {
            @Override
            public void onComplete(String result) {
                testResult = result;
                signal.countDown();
            }
        }).execute(new android.support.v4.util.Pair<Context, String>(getContext(), null));
        signal.await();

        assertFalse(testResult == null);
    }

}