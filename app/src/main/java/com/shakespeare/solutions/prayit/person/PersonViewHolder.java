package com.shakespeare.solutions.prayit.person;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.shakespeare.solutions.prayit.R;

import org.w3c.dom.Text;

public class PersonViewHolder extends RecyclerView.ViewHolder {
    private final TextView personItemView;
    private final TextView explanationTextView;

    private PersonViewHolder(View itemView) {
        super(itemView);
        personItemView = itemView.findViewById(R.id.textView);
        explanationTextView = itemView.findViewById(R.id.explanationTextView);
    }

    public void bind(String text) {
        personItemView.setText(text);
        explanationTextView.setText("Sed ut perspiciatis, unde omnis iste natus error sit voluptatem accusantium..");
    }

    static PersonViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.person_item, parent, false);
        return new PersonViewHolder(view);
    }

}
