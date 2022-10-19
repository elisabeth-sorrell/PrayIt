package com.shakespeare.solutions.prayit;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

public class PersonListAdapter extends ListAdapter<Person, PersonViewHolder> {

    public PersonListAdapter(@NonNull DiffUtil.ItemCallback<Person> diffCallback) {
        super(diffCallback);
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return PersonViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(PersonViewHolder holder, int position) {
        Person current = getItem(position);
        holder.bind(current.getName());
    }


    static class PersonDiff extends DiffUtil.ItemCallback<Person> {

        @Override
        public boolean areItemsTheSame(@NonNull Person oldItem, @NonNull Person newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Person oldItem, @NonNull Person newItem) {
            return oldItem.getName().equals(newItem.getName());
        }
    }
}
