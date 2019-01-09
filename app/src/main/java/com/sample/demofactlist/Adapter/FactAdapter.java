package com.sample.demofactlist.Adapter;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.sample.demofactlist.Model.Row;
import com.sample.demofactlist.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

import okhttp3.Cache;
import okhttp3.OkHttpClient;

public class FactAdapter extends RecyclerView.Adapter<FactAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<Row> mRowList;
    private Resources mResources;

    private Picasso.Builder picassoBuilder;
    private Picasso picasso;

    public FactAdapter(Context context, ArrayList<Row> rows) {
        mContext = context;
        mRowList = rows;
        mResources = context.getResources();

        // 10 mb for cache
        File httpCacheDirectory = new File(mContext.getCacheDir(), "demofact-cache");
        Cache cache = new Cache(httpCacheDirectory, 10 * 1024 * 1024);

        OkHttpClient.Builder okHttpClientBuilder= new OkHttpClient.Builder().cache(cache);
        picassoBuilder = new Picasso.Builder(mContext.getApplicationContext());

        picassoBuilder.listener(new Picasso.Listener() {
            @Override
            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                exception.printStackTrace();
            }
        });

        picassoBuilder.downloader(new OkHttp3Downloader(okHttpClientBuilder.build())).build();
        picasso = picassoBuilder.build();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.fact_row_item,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return mRowList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {

        Row item = mRowList.get(position);
        String title = item.getTitle();
        String description = item.getDescription();

            myViewHolder.title.setText(title != null ? title : mResources.getString(R.string.not_available));
            myViewHolder.desc.setText(description != null ? description : mResources.getString(R.string.not_available));

            final String url = item.getImageHref();
            picasso.load(url).placeholder(R.drawable.img_image_placeholder).resize(150, 105).centerCrop().into(myViewHolder.imageV);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, desc;
        public ImageView imageV;

        public MyViewHolder(View view) {
            super(view);
            title  = view.findViewById(R.id.title);
            desc   = view.findViewById(R.id.description);
            imageV = view.findViewById(R.id.imageView);
        }

    }
}
