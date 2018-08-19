package com.bssf.john_li.coinhand.CHAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bssf.john_li.coinhand.R;
import com.squareup.picasso.Picasso;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.List;

/**
 * Created by John_Li on 13/10/2017.
 */

public class PhotoAdapter extends BaseAdapter {
    private List<String> photoList;
    private LayoutInflater inflater;
    private Context mContext;
    private ImageOptions options = new ImageOptions.Builder().setSize(0, 0).setFailureDrawableId(R.mipmap.load_img_fail).build();

    public PhotoAdapter(Context context, List<String> photoList) {
        this.photoList = photoList;
        inflater = LayoutInflater.from(context);
        mContext = context;
    }
    @Override
    public int getCount() {
        return photoList.size();
    }

    @Override
    public Object getItem(int i) {
        return photoList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View convertView = view;
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_opinion, null);
            holder.imgPhoto = convertView.findViewById(R.id.item_opinion_iv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (photoList.size() < 1) {
            holder.imgPhoto.setImageResource(R.mipmap.car_sample);
        } else {
            //x.image().bind(holder.imgPhoto, photoList.get(i), options);
            Picasso.with(mContext).load(photoList.get(i)).placeholder(R.mipmap.load_img_fail).into(holder.imgPhoto);
        }
        return convertView;
    }

    class ViewHolder {
        private ImageView imgPhoto;
    }

    public void refreshData(List<String> newList) {
        this.photoList = newList;
        notifyDataSetChanged();
    }
}
