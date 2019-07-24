package com.example.retrofit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerHolder>
{
    private List<Post> adapterPosts;
    private Post postPosition;
    private LayoutInflater layoutInflater;
    private Context context;

    public RecyclerAdapter(List<Post> adapterPosts, Context context)
    {
        this.adapterPosts = adapterPosts;
        layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cards, parent, false);
        return new RecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerHolder recyclerHolder, int position)
    {
        postPosition = adapterPosts.get(position);

        recyclerHolder.userId.setText(String.valueOf(postPosition.getUserId()));
        recyclerHolder.id.setText(String.valueOf(postPosition.getId()));
        recyclerHolder.title.setText(postPosition.getTitle());
        recyclerHolder.text.setText(postPosition.getText());

        View view = layoutInflater.inflate(R.layout.item_cards, null, false);
    }

    @Override
    public int getItemCount()
    {
        return adapterPosts.size();
    }

    public class RecyclerHolder extends RecyclerView.ViewHolder
    {
        TextView userId;
        TextView id;
        TextView title;
        TextView text;

        public RecyclerHolder(@NonNull View itemView)
        {
            super(itemView);

            userId = (TextView) itemView.findViewById(R.id.useridxml);
            id = itemView.findViewById(R.id.idxml);
            title = itemView.findViewById(R.id.titlexml);
            text = itemView.findViewById(R.id.textxml);
        }
    }
}
