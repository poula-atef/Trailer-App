// Generated code from Butter Knife. Do not modify!
package com.example.emovieapp.UI;

import android.view.View;
import android.widget.ImageView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.emovieapp.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MovieImage_ViewBinding implements Unbinder {
  private MovieImage target;

  @UiThread
  public MovieImage_ViewBinding(MovieImage target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public MovieImage_ViewBinding(MovieImage target, View source) {
    this.target = target;

    target.img = Utils.findRequiredViewAsType(source, R.id.front_img, "field 'img'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    MovieImage target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.img = null;
  }
}
