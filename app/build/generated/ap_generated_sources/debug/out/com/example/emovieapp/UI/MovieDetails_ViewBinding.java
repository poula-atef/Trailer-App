// Generated code from Butter Knife. Do not modify!
package com.example.emovieapp.UI;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.emovieapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MovieDetails_ViewBinding implements Unbinder {
  private MovieDetails target;

  @UiThread
  public MovieDetails_ViewBinding(MovieDetails target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public MovieDetails_ViewBinding(MovieDetails target, View source) {
    this.target = target;

    target.fab = Utils.findRequiredViewAsType(source, R.id.play_movie, "field 'fab'", FloatingActionButton.class);
    target.front = Utils.findRequiredViewAsType(source, R.id.front_img, "field 'front'", ImageView.class);
    target.front_card = Utils.findRequiredViewAsType(source, R.id.front_card, "field 'front_card'", CardView.class);
    target.title = Utils.findRequiredViewAsType(source, R.id.title, "field 'title'", TextView.class);
    target.cast = Utils.findRequiredViewAsType(source, R.id.cast, "field 'cast'", RecyclerView.class);
    target.sim_tv = Utils.findRequiredViewAsType(source, R.id.sim_tv, "field 'sim_tv'", TextView.class);
    target.similar = Utils.findRequiredViewAsType(source, R.id.similar, "field 'similar'", RecyclerView.class);
    target.recc_tv = Utils.findRequiredViewAsType(source, R.id.recc_tv, "field 'recc_tv'", TextView.class);
    target.recc = Utils.findRequiredViewAsType(source, R.id.recc, "field 'recc'", RecyclerView.class);
    target.sv = Utils.findRequiredViewAsType(source, R.id.sv, "field 'sv'", ScrollView.class);
    target.rate_pb = Utils.findRequiredViewAsType(source, R.id.rating_pb_detail, "field 'rate_pb'", ProgressBar.class);
    target.rate_tv = Utils.findRequiredViewAsType(source, R.id.rating_tv, "field 'rate_tv'", TextView.class);
    target.description = Utils.findRequiredViewAsType(source, R.id.desc_tv, "field 'description'", TextView.class);
    target.back = Utils.findRequiredViewAsType(source, R.id.back_img, "field 'back'", ImageView.class);
    target.like = Utils.findRequiredViewAsType(source, R.id.like_btn, "field 'like'", ImageButton.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    MovieDetails target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.fab = null;
    target.front = null;
    target.front_card = null;
    target.title = null;
    target.cast = null;
    target.sim_tv = null;
    target.similar = null;
    target.recc_tv = null;
    target.recc = null;
    target.sv = null;
    target.rate_pb = null;
    target.rate_tv = null;
    target.description = null;
    target.back = null;
    target.like = null;
  }
}
