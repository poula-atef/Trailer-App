package com.example.emovieapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.emovieapp.Classes.CastClass;
import com.example.emovieapp.R;

import java.util.List;

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.CastViewHolder> {

    private final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w600_and_h900_bestv2";
    List<CastClass.CastBean> cast;
    Context context;
    OnActorClickListener actorClickListener;

    public CastAdapter(List<CastClass.CastBean> cast, Context context, OnActorClickListener actorClickListener) {
        this.cast = cast;
        this.context = context;
        this.actorClickListener = actorClickListener;
    }

    public CastAdapter() {
     }

    @NonNull
    @Override
    public CastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CastViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_element,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CastViewHolder holder, int position) {
        holder.actor.setVisibility(View.VISIBLE);
        holder.actor.setText(cast.get(position).getName());
        if(cast.get(position).getCharacter().equals(""))
            holder.tv.setVisibility(View.GONE);
        else
            holder.tv.setText("(" + cast.get(position).getCharacter() + ")");
        Glide.with(context).load(IMAGE_BASE_URL+cast.get(position).getProfile_path()).into(holder.im);
    }

    @Override
    public int getItemCount() {
        if(cast == null)
            return 0;
        return cast.size();
    }


    public interface OnActorClickListener{
        public void onActorClick(CastClass.CastBean actor,ImageView image);
    }


    public class CastViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv,actor;
        ImageView im;
        CardView rec_card;
        public CastViewHolder(@NonNull View view) {
            super(view);
            tv = view.findViewById(R.id.tv);
            actor = view.findViewById(R.id.actor);
            im = view.findViewById(R.id.im);
            rec_card = view.findViewById(R.id.rec_card);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            actorClickListener.onActorClick(cast.get(getLayoutPosition()),im);
        }
    }
}
