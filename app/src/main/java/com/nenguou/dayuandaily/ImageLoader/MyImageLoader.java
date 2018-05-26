package com.nenguou.dayuandaily.ImageLoader;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.youth.banner.loader.ImageLoader;

public class MyImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Picasso.with(context).load((Uri) path).into(imageView);
        //Uri uri = Uri.parse((String) path);
        //imageView.setImageURI(uri);
    }
}
