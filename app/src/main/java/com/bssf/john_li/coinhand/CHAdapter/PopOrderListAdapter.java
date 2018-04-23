package com.bssf.john_li.coinhand.CHAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bssf.john_li.coinhand.R;

import java.util.List;

/**
 * Created by John_Li on 23/4/2018.
 */

public class PopOrderListAdapter extends BaseAdapter {
    private List<String> list;
    private LayoutInflater mInflater;
    private Context mContext;
    public PopOrderListAdapter(List<String> list, Context context) {
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
            convertView = mInflater.inflate(R.layout.item_pop_order_list, null);
            holder = new ViewHolder();
            holder.item_pop_iv = convertView.findViewById(R.id.item_pop_iv);
            holder.item_pop_order_no = convertView.findViewById(R.id.item_pop_order_no);
            holder.item_pop_car_no = convertView.findViewById(R.id.item_pop_car_no);
            holder.item_pop_car_port = convertView.findViewById(R.id.item_pop_car_port);
            holder.item_pop_order_user = convertView.findViewById(R.id.item_pop_order_user);
            holder.item_pop_tel = convertView.findViewById(R.id.item_pop_tel);
            holder.item_pop_order_next_time = convertView.findViewById(R.id.item_pop_order_next_time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.item_pop_order_no.setText("訂單編號：" + list.get(position));
        holder.item_pop_car_no.setText("車牌號碼：M6956");
        holder.item_pop_car_port.setText("車位編號：09");
        holder.item_pop_order_user.setText("車        主：MISS.LI");
        holder.item_pop_tel.setText("電話號碼：65863293");
        holder.item_pop_order_next_time.setText("下次時間：00:00:12");
        return convertView;
    }

    public class ViewHolder {
        public ImageView item_pop_iv;
        public TextView item_pop_order_no, item_pop_car_no, item_pop_car_port, item_pop_order_user, item_pop_tel, item_pop_order_next_time;
    }
}
