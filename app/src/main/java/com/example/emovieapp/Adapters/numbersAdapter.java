package com.example.emovieapp.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.emovieapp.R;

import java.util.List;

public class numbersAdapter extends RecyclerView.Adapter<numbersAdapter.numberViewHolder> {
    List<Integer>numbers;
    onNumberClickListener listener;
    Context context;
    public numbersAdapter(List<Integer> numbers,onNumberClickListener listener,Context context) {
        this.numbers = numbers;
        this.listener = listener;
        this.context = context;
    }

    public numbersAdapter() {
    }

    @NonNull
    @Override
    public numberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new numberViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.number_element,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull numberViewHolder holder, int position) {
        if (numbers.get(position) > 0) {
            holder.number.setText(String.valueOf(numbers.get(position)));
            holder.number.setTextSize(30);
        } else {
            holder.number.setTypeface(holder.number.getTypeface(), Typeface.BOLD);
            holder.number.setTextColor(context.getResources().getColor(R.color.blueColor));
            holder.number.setText(String.valueOf(-1 * numbers.get(position)));
        }
    }

    @Override
    public int getItemCount() {
        if(numbers == null)
            return 0;
        return numbers.size();
    }

    public interface onNumberClickListener{
        void onClickListener(int number);
    }

    public class numberViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView number;
        public numberViewHolder(@NonNull View itemView) {
            super(itemView);
            number = itemView.findViewById(R.id.number);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(numbers.get(getLayoutPosition()) > 0){
            listener.onClickListener(getLayoutPosition());
        }
        }
    }
}
