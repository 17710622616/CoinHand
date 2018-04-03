package com.bssf.john_li.coinhand.CHFragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bssf.john_li.coinhand.OrderListActivity;
import com.bssf.john_li.coinhand.R;

/**
 * Created by John_Li on 20/1/2018.
 */

public class MineFragment extends LazyLoadFragment implements View.OnClickListener{
    public static String TAG = MineFragment.class.getName();
    private TextView loginOutTv, didOrderTv;

    public static MineFragment newInstance(){
        return new MineFragment();
    }

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.fragment_mine);
        initView();
        setlistener();
    }

    private void initView() {
        loginOutTv = (TextView) findViewById(R.id.mine_login_out);
        didOrderTv = (TextView) findViewById(R.id.mine_did_order);
    }

    private void setlistener() {
        loginOutTv.setOnClickListener(this);
        didOrderTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mine_login_out:
                new AlertDialog.Builder(getActivity()).setTitle("提醒")
                        .setIconAttribute(android.R.attr.alertDialogIcon)
                        .setMessage("確認要登出賬戶？")
                        .setPositiveButton("確認", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }})
                        .setNegativeButton("取消", null)
                        .create().show();
                break;
            case R.id.mine_did_order:
                startActivity(new Intent(getActivity(), OrderListActivity.class));
                break;
        }
    }
}
