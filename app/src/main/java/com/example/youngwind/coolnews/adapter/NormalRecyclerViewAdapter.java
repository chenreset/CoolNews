package com.example.youngwind.coolnews.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.youngwind.coolnews.R;
import com.example.youngwind.coolnews.activity.ItemDetail;
import com.example.youngwind.coolnews.model.Newslist;

/**
 * Created by youngwind on 16/7/12.
 */
public class NormalRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final LayoutInflater mLayoutInflater;
    private final Context context;
    private Newslist.Contentlist[] contentlist;

    public NormalRecyclerViewAdapter(Context context, Newslist.Contentlist[] contentlist) {
        this.context = context;
        this.contentlist = contentlist;
        mLayoutInflater = LayoutInflater.from(context);

    }


    @Override
    public int getItemCount() {
        if (contentlist == null) {
            return 0;
        } else {
            return contentlist.length;
        }

    }

    public void insert(Newslist.Contentlist[] contentlist, String requestType) {
        if (requestType.equals("REFRESH_REQUEST")) {
            this.contentlist = null;
        }
        int preLength;
        if (this.contentlist == null) {
            this.contentlist = contentlist.clone();
            preLength = 0;
        } else {
            preLength = this.contentlist.length;
            Newslist.Contentlist[] temp = new Newslist.Contentlist[this.contentlist.length + contentlist.length];
            System.arraycopy(this.contentlist, 0, temp, 0, this.contentlist.length);
            System.arraycopy(contentlist, 0, temp, this.contentlist.length, contentlist.length);
            this.contentlist = temp;
        }
        notifyDataSetChanged();
    }

    // 导入布局文件
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NormalTextViewHolder(mLayoutInflater.inflate(R.layout.item, parent, false));
    }

    // 绑定数据
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((NormalTextViewHolder) holder).title.setText(position + 1 + ". " + contentlist[position].title);
        ((NormalTextViewHolder) holder).pubDate.setText(contentlist[position].pubDate);
        ((NormalTextViewHolder) holder).source.setText("来源: " + contentlist[position].source);
        ((NormalTextViewHolder) holder).layout.setTag(contentlist[position].link);
    }

    public class NormalTextViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView pubDate;
        private TextView source;
        private LinearLayout layout;

        public NormalTextViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            pubDate = (TextView) itemView.findViewById(R.id.pubDate);
            source = (TextView) itemView.findViewById(R.id.source);
            layout = (LinearLayout) itemView.findViewById(R.id.layout);
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String link = view.getTag().toString();
                    Activity CurrentActivity = (Activity) view.getContext();
                    Intent intent = new Intent(CurrentActivity, ItemDetail.class);
                    Bundle b = new Bundle();
                    b.putString("link", link);
                    intent.putExtras(b);

                    CurrentActivity.startActivity(intent);
                }
            });

        }
    }
}
