package com.fnp.androidjokes;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A placeholder fragment containing a simple view.
 */
public class JokerActivityFragment extends Fragment {

    public JokerActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.joker_fragment_main, container, false);

        Bundle bundle = getActivity().getIntent().getExtras();
        String joke = bundle.getString(JokerActivity.JOKE_EXTRA);

        ((TextView)view.findViewById(R.id.joke_text)).setText(joke);

        return view;
    }
}
