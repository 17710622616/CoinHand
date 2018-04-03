package com.bssf.john_li.coinhand.CHAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bssf.john_li.coinhand.R;

import java.util.List;

/**
 * 车辆列表刷新
 * Created by John_Li on 28/11/2017.
 */

public class SmartOrderListRefreshAdapter extends RecyclerView.Adapter<SmartOrderListRefreshAdapter.SmartRefreshViewHolder> implements View.OnClickListener{
    private List<String> orderList;
    private final Context mContext;
    private OnItemClickListener mOnitemClickListener;
    private LayoutInflater mInflater;

    public SmartOrderListRefreshAdapter(Context context, List<String> list) {
        this.orderList = list;
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }
    @Override
    public SmartOrderListRefreshAdapter.SmartRefreshViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_order_list, parent, false);
        SmartRefreshViewHolder vh = new SmartRefreshViewHolder(view);
        vh.orderlistIv = (ImageView) view.findViewById(R.id.item_orderlist_iv);
        vh.orderNum = (TextView) view.findViewById(R.id.item_orderlist_num);
        vh.orderMoney = (TextView) view.findViewById(R.id.item_orderlist_money);
        vh.orderOperationTime = (TextView) view.findViewById(R.id.item_orderlist_operation_time);
        vh.mibiaoNum = (TextView) view.findViewById(R.id.item_orderlist_mibiao_num);
        vh.orderCarNum = (TextView) view.findViewById(R.id.item_orderlist_car_num);
        vh.orderAddress = (TextView) view.findViewById(R.id.item_orderlist_address);
        view.setOnClickListener(this);
        return vh;
    }

    @Override
    public void onBindViewHolder(SmartOrderListRefreshAdapter.SmartRefreshViewHolder holder, int position) {
        holder.orderlistIv.setImageResource(R.mipmap.car_sample);
        holder.orderNum.setText("訂單編號：123456789");
        holder.orderMoney.setText("訂單金額：MOP70");
        holder.orderOperationTime.setText("操作時間：10:30:50 2018/05/01");
        holder.mibiaoNum.setText("咪錶編號：68910");
        holder.orderCarNum.setText("車牌號碼：M69-54");
        holder.orderAddress.setText("地址：未知");
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    @Override
    public void onClick(View v) {
        if (mOnitemClickListener != null) {
            mOnitemClickListener.onItemClick(v, (int)v.getTag());
        }
    }

    public class SmartRefreshViewHolder extends RecyclerView.ViewHolder {
        public ImageView orderlistIv;
        public TextView orderNum;
        public TextView orderMoney;
        public TextView orderOperationTime;
        public TextView mibiaoNum;
        public TextView orderCarNum;
        public TextView orderAddress;
        public SmartRefreshViewHolder(View view){
            super(view);
        }
    }

    public void setOnItemClickListenr(OnItemClickListener listenr) {
        this.mOnitemClickListener = listenr;
    }

    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void refreshListView(List<String> newList){
        this.orderList = newList;
        notifyDataSetChanged();
    }
}
