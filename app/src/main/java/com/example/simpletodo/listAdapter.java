package com.example.simpletodo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class listAdapter extends RecyclerView.Adapter<listAdapter.ViewHolder>{
    public interface onLongClickListener{
        void onItemLongClick(int position);
    }

    List<String> list;
    onLongClickListener longClickListener;
    public listAdapter(List<String> list, onLongClickListener longClickListener) {
        this.list = list;
        this.longClickListener = longClickListener;
    }

    @NonNull
    //@org.jetbrains.annotations.NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //use layout inflator to inflate view
        View todoView = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1,
                parent, false);

        //wrap it inside a view holder and return it
        return new ViewHolder(todoView);
    }

    //responsible for binding data to view holder
    @Override
    public void onBindViewHolder(@NonNull listAdapter.ViewHolder holder, int position) {
        //grab item and postiont and bind it into specified view holder
        String item = list.get(position);
        holder.bind(item);
    }

    @Override
    //# items
    public int getItemCount() {
        return list.size();
    }

    //Container to access views, represented by rows
    class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvItem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(android.R.id.text1);
        }

        //update view inside the viewholder w/ this data
        public void bind(String item) {
            tvItem.setText(item);
            tvItem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    longClickListener.onItemLongClick(getAdapterPosition());
                    return true;
                }
            });
        }
    }
}
