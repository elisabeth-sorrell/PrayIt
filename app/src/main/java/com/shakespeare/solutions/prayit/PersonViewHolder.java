package com.shakespeare.solutions.prayit;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class PersonViewHolder extends RecyclerView.ViewHolder {
    private final TextView personItemView;

    private PersonViewHolder(View itemView) {
        super(itemView);
        personItemView = itemView.findViewById(R.id.textView);
    }

    public void bind(String text) {
        personItemView.setText(text);
    }

    static PersonViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item, parent, false);
        return new PersonViewHolder(view);
    }

}
