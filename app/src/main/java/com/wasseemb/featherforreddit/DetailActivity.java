package com.wasseemb.featherforreddit;

import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

/**
 * Created by Wasseem on 14/08/2017.
 */

public class DetailActivity extends AppCompatActivity {

  @BindView(R.id.info_image) RoundedImageView imageView;
  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.card_view) CardView cardView;
  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.detail_activity);
    ButterKnife.bind(this);

    supportPostponeEnterTransition();


    setSupportActionBar(toolbar);
    if (getSupportActionBar() != null) {
      getSupportActionBar().setDisplayShowHomeEnabled(true);
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    String imageUrl = getIntent().getAction();
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      cardView.setTransitionName("robot_trans");
    }
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
