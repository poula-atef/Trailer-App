// Generated code from Butter Knife. Do not modify!
package com.example.emovieapp.Fragments;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.emovieapp.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SearchFragment_ViewBinding implements Unbinder {
  private SearchFragment target;

  @UiThread
  public SearchFragment_ViewBinding(SearchFragment target, View source) {
    this.target = target;

    target.searchEt = Utils.findRequiredViewAsType(source, R.id.search_et, "field 'searchEt'", EditText.class);
    target.rec = Utils.findRequiredViewAsType(source, R.id.search_rec, "field 'rec'", RecyclerView.class);
    target.back = Utils.findRequiredViewAsType(source, R.id.back_btn, "field 'back'", ImageButton.class);
    target.forward = Utils.findRequiredViewAsType(source, R.id.forward_btn, "field 'forward'", ImageButton.class);
    target.numbers_rec = Utils.findRequiredViewAsType(source, R.id.pages, "field 'numbers_rec'", RecyclerView.class);
    target.notFound = Utils.findRequiredViewAsType(source, R.id.not_found, "field 'notFound'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    SearchFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.searchEt = null;
    target.rec = null;
    target.back = null;
    target.forward = null;
    target.numbers_rec = null;
    target.notFound = null;
  }
}
