package it.jaschke.alexandria;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.google.zxing.integration.android.IntentIntegrator;

/**
 * Created by ranjeevmahtani on 10/8/15.
 */
public class FragmentIntentIntegrator extends IntentIntegrator {

    private static final String LOG_TAG = FragmentIntentIntegrator.class.getSimpleName();

    private final Fragment fragment;

    public FragmentIntentIntegrator(Fragment fragment) {
        super(fragment.getActivity());
        this.fragment = fragment;
        Log.d(LOG_TAG, "constructor passed. fragment refers to " + fragment.toString());
    }

    @Override
    protected void startActivityForResult(Intent intent, int code) {
        Log.d(LOG_TAG, "in startActivityForResult, fragment refers to " + fragment.toString());
        fragment.startActivityForResult(intent, code);
    }

}
