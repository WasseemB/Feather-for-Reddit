package com.wasseemb.featherforreddit;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

/**
 * Created by Wasseem on 14/08/2017.
 */

public class ImageViewActivity extends AppCompatActivity {

  @BindView(R.id.imageView) ImageView imageView;
  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.imageview_activity);
    ButterKnife.bind(this);
    String imageUrl = getIntent().getAction();
    imageView.setTransitionName("robot_trans");
    Picasso.with(this)
        .load(imageUrl)
        .noFade()
        .into(imageView, new Callback() {
          @Override
          public void onSuccess() {
            supportStartPostponedEnterTransition();
          }

          @Override
          public void onError() {
            supportStartPostponedEnterTransition();
          }
        });

  }
}
