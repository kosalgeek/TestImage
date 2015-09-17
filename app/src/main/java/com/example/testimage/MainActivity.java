package com.example.testimage;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageLoader.getInstance().init(UILConfig());

        ImageView imageView;
        imageView = (ImageView)findViewById(R.id.imageView);

        String imageName = "sunset";
        imageView.setImageResource(getImageFromDrawable(imageName));

        ImageView imageView2;
        imageView2 = (ImageView)findViewById(R.id.imageView2);
        try {
            imageView2.setImageBitmap(getBitmapFromAssets("cub cake.jpg"));
        } catch (IOException e) {
            Toast.makeText(this, "Error Image Path", Toast.LENGTH_SHORT).show();
        }

        ImageView imageView3;
        imageView3 = (ImageView)findViewById(R.id.imageView3);
        String imageUrl = "http://img06.deviantart.net/3659/i/2004/05/2/e/pink_flower.jpg";
        ImageLoader.getInstance().displayImage(imageUrl, imageView3);

    }

    private int getImageFromDrawable(String imageName){
        int imageResource = getResources().getIdentifier(imageName, "drawable", getPackageName());
        return imageResource;
    }

    private Bitmap getBitmapFromAssets(String fileName) throws IOException {
        AssetManager assetManager = getAssets();

        InputStream istr = assetManager.open(fileName);
        Bitmap bitmap = BitmapFactory.decodeStream(istr);

        return bitmap;
    }

    private Bitmap getBitmapfromURL(String imageUrl) throws IOException {
        URL url = new URL(imageUrl);
        Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        return bmp;
    }

    private ImageLoaderConfiguration UILConfig(){
        //        /** To make the image fill the width and keep height ratio.**/
        //        <ImageView
        //        android:layout_width="fill_parent" //fill_width #1
        //        android:layout_height="wrap_content" //fill_width #2
        //        android:id="@+id/ivImage"
        //        android:src="@android:drawable/gallery_thumb"
        //        android:scaleType="fitCenter"   //fill_width #3
        //        android:adjustViewBounds="true" //fill_width #4
        //                />
        final DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)  //cache #1
                .cacheOnDisk(true) //cache #2
                .showImageOnLoading(android.R.drawable.stat_sys_download)
                .showImageForEmptyUri(android.R.drawable.ic_dialog_alert)
                .showImageOnFail(android.R.drawable.stat_notify_error)
                .considerExifParams(true) //cache #3
                .bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED) //fill_width #5
                .build();

        ////cache #4
        //add <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/> to manifest
        ImageLoaderConfiguration config = new ImageLoaderConfiguration
                .Builder(getApplicationContext())
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .defaultDisplayImageOptions(defaultOptions)
                .build();
        //end of cache 4
        return config;
    }

}
