// Generated code from Butter Knife. Do not modify!
package com.example.emovieapp.Fragments;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.emovieapp.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FavoriteFragment_ViewBinding implements Unbinder {
  private FavoriteFragment target;

  @UiThread
  public FavoriteFragment_ViewBinding(FavoriteFragment target, View source) {
    this.target = target;

    target.favRecc = Utils.findRequiredViewAsType(source, R.id.fav_recc, "field 'favRecc'", RecyclerView.class);
    target.noFav = Utils.findRequiredViewAsType(source, R.id.no_fav, "field 'noFav'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    FavoriteFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.favRecc = null;
    target.noFav = null;
  }
}
