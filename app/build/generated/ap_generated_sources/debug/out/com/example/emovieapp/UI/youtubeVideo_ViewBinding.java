// Generated code from Butter Knife. Do not modify!
package com.example.emovieapp.UI;

import android.view.View;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.emovieapp.R;
import com.google.android.youtube.player.YouTubePlayerView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class youtubeVideo_ViewBinding implements Unbinder {
  private youtubeVideo target;

  @UiThread
  public youtubeVideo_ViewBinding(youtubeVideo target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public youtubeVideo_ViewBinding(youtubeVideo target, View source) {
    this.target = target;

    target.trailer = Utils.findRequiredViewAsType(source, R.id.myPlayer, "field 'trailer'", YouTubePlayerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    youtubeVideo target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.trailer = null;
  }
}
