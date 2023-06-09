package com.highfliers.high_fliers_android_hack2k21.InterestSection;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.highfliers.high_fliers_android_hack2k21.R;
import com.highfliers.high_fliers_android_hack2k21.RoomDetails;

import java.util.ArrayList;
import java.util.List;

public class InterestDetailsAdapter extends RecyclerView.Adapter<InterestDetailsAdapter.ViewHolder> {
    ItemClick activity;
    List<InterestDetails> list;
    Context context;

    public interface ItemClick {
        void onItemClick(int i);
    }

    public InterestDetailsAdapter(Context context, List<InterestDetails> list) {
        this.list = list;
        this.activity = (ItemClick) context;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView interest_icon, dropdown;
        TextView interest_text, active_rooms;
        RecyclerView interest_recycler;
        ProgressBar interests_progress;
        RecyclerView.Adapter adapter;
        RecyclerView.LayoutManager layoutManager;
        boolean isVisible;
        ArrayList<RoomDetails> roomList;

        public ViewHolder(View itemView) {
            super(itemView);
            interest_icon = itemView.findViewById(R.id.icon_interest);
            dropdown = itemView.findViewById(R.id.dropdown);
            interest_text = itemView.findViewById(R.id.interest_text);
            active_rooms = itemView.findViewById(R.id.active_rooms_interest);
            interests_progress = itemView.findViewById(R.id.interest_roomProgress);
            interest_recycler = itemView.findViewById(R.id.recycler_interest_rooms);
            isVisible = false;
            roomList = new ArrayList();
            interest_recycler.setHasFixedSize(true);
            itemView.setOnClickListener(v -> {
                InterestDetailsAdapter.this.activity.onItemClick(InterestDetailsAdapter.this.list.indexOf((InterestDetails) v.getTag()));
                getRoomData();
            });
        }

        public void getRoomData() {
            if (active_rooms.getText().toString().equals("0")) {
                Toast.makeText(context, "There are currently no rooms available!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!isVisible) {
                RotateAnimation rotate = new RotateAnimation(0, 90, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                rotate.setDuration(200);
                rotate.setFillAfter(true);
                rotate.setInterpolator(new LinearInterpolator());
                isVisible = true;
                dropdown.startAnimation(rotate);
                interests_progress.setVisibility(View.GONE);
                interest_recycler.setVisibility(View.VISIBLE);

            } else {
                RotateAnimation rotate = new RotateAnimation(90, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                rotate.setDuration(200);
                rotate.setFillAfter(true);
                rotate.setInterpolator(new LinearInterpolator());
                dropdown.startAnimation(rotate);
                interest_recycler.setVisibility(View.GONE);
                isVisible = false;
            }
        }

        public void setRecyclerData(String interest) {
            adapter = new RoomAdapter(context, roomList);
            layoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
            interest_recycler.setAdapter(adapter);
            interest_recycler.setLayoutManager(layoutManager);
            FirebaseFirestore.getInstance().collection("Interests").document("AllInterests")
                    .collection(interest).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        QuerySnapshot snapshots = task.getResult();

                        assert snapshots != null;
                        for (QueryDocumentSnapshot documentSnapshot : snapshots) {
                            roomList.add(new RoomDetails(
                                    documentSnapshot.get("roomName").toString(), documentSnapshot.get("subCategory").toString(), "interests"
                            ));
                        }
                        adapter.notifyDataSetChanged();
                        String active = String.valueOf(adapter.getItemCount());
                        active_rooms.setText(active);
                    }
                }
            });
        }
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.interest_details, parent, false));
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(this.list.get(position));
        switch (list.get(position).getInterest()) {
            case "Music":
                holder.interest_icon.setImageResource(R.drawable.interests_music);
                holder.interest_text.setText("Music");
                break;
            case "Games":
                holder.interest_icon.setImageResource(R.drawable.interests_games);
                holder.interest_text.setText("Games");
                break;
            case "Poetry":
                holder.interest_icon.setImageResource(R.drawable.interests_book);
                holder.interest_text.setText("Poetry");
                break;
            case "Sports":
                holder.interest_icon.setImageResource(R.drawable.interests_ball);
                holder.interest_text.setText("Sports");
                break;
        }
        holder.setRecyclerData(holder.interest_text.getText().toString());
    }

    public int getItemCount() {
        return this.list.size();
    }
}
