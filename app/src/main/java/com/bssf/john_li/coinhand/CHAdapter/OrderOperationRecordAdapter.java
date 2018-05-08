package com.bssf.john_li.coinhand.CHAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bssf.john_li.coinhand.CHModel.OrderDetialOutModel;
import com.bssf.john_li.coinhand.CHUtils.CHCommonUtils;
import com.bssf.john_li.coinhand.R;

import java.util.List;

/**
 * Created by John_Li on 8/5/2018.
 */

public class OrderOperationRecordAdapter extends BaseAdapter{
    private List<OrderDetialOutModel.DataBean.ToushouRecordListBean> list;
    private LayoutInflater mInflater;
    private Context mContext;
    public OrderOperationRecordAdapter(List<OrderDetialOutModel.DataBean.ToushouRecordListBean> list, Context context) {
        this.list = list;
        mInflater = LayoutInflater.from(context);
        mContext = context;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_order_operation_record, null);
            holder = new ViewHolder();
            holder.usernameTv = convertView.findViewById(R.id.item_order_operation_record_username);
            holder.amountTv = convertView.findViewById(R.id.item_order_operation_record_amount);
            holder.statusTv = convertView.findViewById(R.id.item_order_operation_record_status);
            holder.createTimeTv = convertView.findViewById(R.id.item_order_operation_record_createTime);
            holder.finishTimeTv = convertView.findViewById(R.id.item_order_operation_record_finishTime);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.usernameTv.setText("操作人員名稱：" + list.get(position).getUsername());
        holder.amountTv.setText("提交金額：" + list.get(position).getAmount());
        holder.statusTv.setText("操作狀態：" + list.get(position).getStatus());
        holder.createTimeTv.setText("操作開始時間：" + CHCommonUtils.stampToDate(String.valueOf(list.get(position).getCreateTime())));
        holder.finishTimeTv.setText("操作完成時間：" + CHCommonUtils.stampToDate(String.valueOf(list.get(position).getFinishTime())));
        return convertView;
    }

    public class ViewHolder {
        public TextView usernameTv, amountTv, statusTv, createTimeTv, finishTimeTv;
    }
}
