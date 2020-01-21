package com.example.sound;

import android.content.ClipData;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.view.LayoutInflater;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CuostomAdapter extends BaseAdapter {
    public List<File> arrayList = null;
    private LayoutInflater thisInflater; //to acess clistview.xml file


    private Context mContext;

    public CuostomAdapter(Context context, List<File> arrayList) {
        super();
        thisInflater = (LayoutInflater.from(context));
        this.arrayList = arrayList;
        this.mContext = context;

    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = thisInflater.inflate(R.layout.clistview, parent, false);//acess the clistview.xml file
            holder = new ViewHolder();

            holder.name = (TextView) convertView.findViewById(R.id.txtview);

            int imageResource = R.mipmap.music2;
          Drawable drawable = ContextCompat.getDrawable(mContext, imageResource);

       int pixelDrawableSize = (int) Math.round(holder.name.getLineHeight() * 2.5); // Or the percentage you like (0.8, 0.9, etc.)
            drawable.setBounds(0, 0, pixelDrawableSize, pixelDrawableSize); // setBounds(int left, int top, int right, int bottom), in this case, drawable is a square image

            holder.name.setCompoundDrawables(
                    drawable, //left
                    null, //top
                    null, //right
                    null //bottom

            );
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
holder.name.setText(arrayList.get(position).getName());
        return convertView;
    }

    static class ViewHolder {
        private TextView name;

    }
}
