package com.wasseemb.featherforreddit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

/**
 * Created by Wasseem on 16/06/2017.
 */

public class ImageActivity extends AppCompatActivity {
  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.popup_layout_image);
    ImageView mImageView = (ImageView) findViewById(R.id.popup_detail_img);
  }
}
