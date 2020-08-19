// Generated code from Butter Knife. Do not modify!
package com.example.emovieapp.Fragments;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.emovieapp.R;
import com.google.android.material.tabs.TabLayout;
import java.lang.IllegalStateException;
import java.lang.Override;

public class HomeFragment_ViewBinding implements Unbinder {
  private HomeFragment target;

  @UiThread
  public HomeFragment_ViewBinding(HomeFragment target, View source) {
    this.target = target;

    target.pager = Utils.findRequiredViewAsType(source, R.id.pager, "field 'pager'", ViewPager.class);
    target.pb = Utils.findRequiredViewAsType(source, R.id.pb, "field 'pb'", ProgressBar.class);
    target.tab = Utils.findRequiredViewAsType(source, R.id.tab, "field 'tab'", TabLayout.class);
    target.popular_title = Utils.findRequiredViewAsType(source, R.id.popular_title, "field 'popular_title'", TextView.class);
    target.popular = Utils.findRequiredViewAsType(source, R.id.rec_popular, "field 'popular'", RecyclerView.class);
    target.top_title = Utils.findRequiredViewAsType(source, R.id.top_title, "field 'top_title'", TextView.class);
    target.top = Utils.findRequiredViewAsType(source, R.id.rec_top, "field 'top'", RecyclerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    HomeFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.pager = null;
    target.pb = null;
    target.tab = null;
    target.popular_title = null;
    target.popular = null;
    target.top_title = null;
    target.top = null;
  }
}
