package com.bssf.john_li.coinhand;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bssf.john_li.coinhand.CHAdapter.SmartOrderListRefreshAdapter;
import com.bssf.john_li.coinhand.CHUtils.CHCommonUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by John_Li on 26/1/2018.
 */

public class OrderListActivity extends BaseActivity {

    private LinearLayout noOrderLL;
    private RefreshLayout mRefreshLayout;
    private RecyclerView mRecycleView;

    private List<String> orderModelList;
    private SmartOrderListRefreshAdapter mSmartOrderListRefreshAdapter;
    //private CarListAdapter mCarListAdapter;
    // 每頁加載數量
    private int pageSize = 10;
    // 頁數
    private int pageNo = 1;
    // 車輛總數
    private long totolCarCount;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        initView();
        setListener();
        initData();
    }

    @Override
    public void initView() {
        mRecycleView = findViewById(R.id.order_list_lv);
        mRefreshLayout = findViewById(R.id.order_list_expand_swipe);
        noOrderLL = findViewById(R.id.no_order_ll);

        mRefreshLayout.setEnableAutoLoadmore(false);//是否启用列表惯性滑动到底部时自动加载更多
        mRefreshLayout.setDisableContentWhenRefresh(true);//是否在刷新的时候禁止列表的操作
        mRefreshLayout.setDisableContentWhenLoading(true);//是否在加载的时候禁止列表的操作
        // 设置header的高度
        mRefreshLayout.setHeaderHeightPx((int)(CHCommonUtils.getDeviceWitdh(this) / 4.05));//Header标准高度（显示下拉高度>=标准高度 触发刷新）
    }

    @Override
    public void setListener() {
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                orderModelList.clear();
                pageNo = 1;
                callNetGetCarList();
            }
        });
        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                //和最大的数据比较
                if (pageSize * (pageNo + 1) > totolCarCount){
                    Toast.makeText(OrderListActivity.this, "沒有更多數據了誒~", Toast.LENGTH_SHORT).show();
                    mRefreshLayout.finishRefresh();
                    mRefreshLayout.finishLoadmore();
                } else {
                    pageNo ++;
                    callNetGetCarList();
                }
            }
        });

        noOrderLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                orderModelList.clear();
                pageNo = 1;
                callNetGetCarList();
            }
        });
    }

    @Override
    public void initData() {
        orderModelList = new ArrayList<>();
        mSmartOrderListRefreshAdapter = new SmartOrderListRefreshAdapter(this, orderModelList);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
        mRecycleView.setAdapter(mSmartOrderListRefreshAdapter);
        mSmartOrderListRefreshAdapter.setOnItemClickListenr(new SmartOrderListRefreshAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }
        });
        mRefreshLayout.autoRefresh();

        totolCarCount = 100;
    }

    private void callNetGetCarList() {
        for (int i = 0; i < 10; i++) {
            orderModelList.add("新增数据" + i);
        }

        mSmartOrderListRefreshAdapter.notifyDataSetChanged();
        mRefreshLayout.finishRefresh();
        mRefreshLayout.finishLoadmore();
    }
}
