package com.highfliers.high_fliers_android_hack2k21.InterestSection;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.highfliers.high_fliers_android_hack2k21.R;
import com.highfliers.high_fliers_android_hack2k21.RoomDetails;

import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.ViewHolder> {

    ItemClick activity;
    List<RoomDetails> list;

    public interface ItemClick {
        void onItemClickRoom(String roomName);
    }

    public RoomAdapter(Context context, List<RoomDetails> list) {
        this.list = list;
        this.activity = (ItemClick) context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView roomName,sub_categoryName;

        public ViewHolder(@NonNull  View itemView) {
            super(itemView);
            roomName=itemView.findViewById(R.id.room_text);
            sub_categoryName=itemView.findViewById(R.id.sub_category);
            itemView.setOnClickListener(v -> {
                String roomCode=roomName.getText().toString().trim();
                if(!sub_categoryName.equals("N/A"))
                    roomCode+="-"+sub_categoryName.getText().toString().trim();

                RoomAdapter.this.activity.onItemClickRoom
                        (roomCode);
            });
        }
    }

    @NonNull
    @Override
    public RoomAdapter.ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.all_rooms_details, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull  RoomAdapter.ViewHolder holder, int position) {
        holder.itemView.setTag(list.get(position));
        holder.roomName.setText(list.get(position).getRoomName());
        if(!list.get(position).getCategory().equals("N/A"))
            holder.sub_categoryName.setText(list.get(position).getCategory());
        else
            holder.sub_categoryName.setVisibility(View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

