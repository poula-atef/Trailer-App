// Generated code from Butter Knife. Do not modify!
package com.example.emovieapp.UI;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.emovieapp.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ActorProfile_ViewBinding implements Unbinder {
  private ActorProfile target;

  @UiThread
  public ActorProfile_ViewBinding(ActorProfile target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ActorProfile_ViewBinding(ActorProfile target, View source) {
    this.target = target;

    target.actorNameTitle = Utils.findRequiredViewAsType(source, R.id.name_title, "field 'actorNameTitle'", TextView.class);
    target.actorName = Utils.findRequiredViewAsType(source, R.id.actor_name, "field 'actorName'", TextView.class);
    target.actorAgeTitle = Utils.findRequiredViewAsType(source, R.id.age_title, "field 'actorAgeTitle'", TextView.class);
    target.actorAge = Utils.findRequiredViewAsType(source, R.id.actor_age, "field 'actorAge'", TextView.class);
    target.actorIntroTitle = Utils.findRequiredViewAsType(source, R.id.actor_intro_title, "field 'actorIntroTitle'", TextView.class);
    target.actorIntro = Utils.findRequiredViewAsType(source, R.id.actor_intro, "field 'actorIntro'", TextView.class);
    target.movieTitle = Utils.findRequiredViewAsType(source, R.id.movie_title, "field 'movieTitle'", TextView.class);
    target.imageView4 = Utils.findRequiredViewAsType(source, R.id.imageView4, "field 'imageView4'", ImageView.class);
    target.actorImage = Utils.findRequiredViewAsType(source, R.id.front_img, "field 'actorImage'", ImageView.class);
    target.image = Utils.findRequiredViewAsType(source, R.id.front_card, "field 'image'", CardView.class);
    target.movies = Utils.findRequiredViewAsType(source, R.id.rec_movies, "field 'movies'", RecyclerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ActorProfile target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.actorNameTitle = null;
    target.actorName = null;
    target.actorAgeTitle = null;
    target.actorAge = null;
    target.actorIntroTitle = null;
    target.actorIntro = null;
    target.movieTitle = null;
    target.imageView4 = null;
    target.actorImage = null;
    target.image = null;
    target.movies = null;
  }
}
