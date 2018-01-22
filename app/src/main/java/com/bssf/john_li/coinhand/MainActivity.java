package com.bssf.john_li.coinhand;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bssf.john_li.coinhand.CHFragment.InsertCoinsFragment;
import com.bssf.john_li.coinhand.CHFragment.MineFragment;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by John_Li on 20/1/2018.
 */

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {
    private RadioButton inset_rb,mine_rb;
    private RadioGroup bottom_group;
    private FragmentManager fm;
    private Fragment cacheFragment;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setListener();
        initData();
    }

    @Override
    public void initView() {
        bottom_group = (RadioGroup)findViewById(R.id.bottom_main_group);
        inset_rb = (RadioButton) findViewById(R.id.bottom_main_inset);
        mine_rb = (RadioButton) findViewById(R.id.bottom_main_mine);
    }

    @Override
    public void setListener() {
        bottom_group.setOnCheckedChangeListener(this);
    }

    @Override
    public void initData() {
        fm = getSupportFragmentManager();
        FragmentTransaction traslation = fm.beginTransaction();
        cacheFragment = new InsertCoinsFragment();
        traslation.add(R.id.main_containor,cacheFragment,InsertCoinsFragment.TAG);
        traslation.commit();
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
        switch (i){
            case R.id.bottom_main_inset:
                inset_rb.setTextColor(getResources().getColor(R.color.base_color));
                mine_rb.setTextColor(getResources().getColor(R.color.colorDrakGray));
                switchPages(InsertCoinsFragment.class,InsertCoinsFragment.TAG);
                break;
            case R.id.bottom_main_mine:
                inset_rb.setTextColor(getResources().getColor(R.color.colorDrakGray));
                mine_rb.setTextColor(getResources().getColor(R.color.base_color));
                switchPages(MineFragment.class,MineFragment.TAG);
                break;
        }
    }

    private void switchPages(Class<?> cls, String tag){
        FragmentTransaction transaction = fm.beginTransaction();
        if (cacheFragment != null){
            transaction.hide(cacheFragment);
        }
        cacheFragment = fm.findFragmentByTag(tag);
        if (cacheFragment != null){
            transaction.show(cacheFragment);
        } else {
            try{
                cacheFragment = (Fragment) cls.getConstructor().newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (Exception e){
                e.printStackTrace();
            }
            transaction.add(R.id.main_containor, cacheFragment, tag);
        }
        transaction.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
