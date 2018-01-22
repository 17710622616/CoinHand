package com.bssf.john_li.coinhand.CHFragment;

import android.os.Bundle;

import com.bssf.john_li.coinhand.R;

/**
 * Created by John_Li on 20/1/2018.
 */

public class InsertCoinsFragment extends LazyLoadFragment {
    public static String TAG = InsertCoinsFragment.class.getName();
    public static InsertCoinsFragment newInstance(){
        return new InsertCoinsFragment();
    }
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.fragment_insert_coins);
    }
}
