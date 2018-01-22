package com.bssf.john_li.coinhand.CHFragment;

import android.os.Bundle;

import com.bssf.john_li.coinhand.R;

/**
 * Created by John_Li on 20/1/2018.
 */

public class MineFragment extends LazyLoadFragment {
    public static String TAG = MineFragment.class.getName();
    public static MineFragment newInstance(){
        return new MineFragment();
    }

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.fragment_mine);
    }
}
