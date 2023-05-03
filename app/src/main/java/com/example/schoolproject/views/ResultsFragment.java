package com.example.schoolproject.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.schoolproject.MainActivity;
import com.example.schoolproject.R;
import com.example.schoolproject.databinding.ResultsFragmentBinding;
import com.example.schoolproject.models.Game;
import com.example.schoolproject.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.GroupieViewHolder;
import com.xwray.groupie.Item;

import java.util.ArrayList;

public class ResultsFragment extends Fragment {

    private ResultsFragmentBinding binding;
    private Game currentGame;
    private ArrayList<User> players;
    private GroupAdapter<GroupieViewHolder> adapter = new GroupAdapter<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ResultsFragmentBinding.inflate(inflater, container, false);

        currentGame = getArguments().getParcelable("CURR_GAME");
        players = getArguments().getParcelableArrayList("CURR_MEMBERS");

        Toast.makeText(getContext(), "" + players.size(), Toast.LENGTH_SHORT).show();

        /**
        DatabaseReference ref = FirebaseDatabase.getInstance(MainActivity.DATABASE_PATH)
                .getReference("/games/" + currentGame.getPin() + "/");
         */

        for (User u : players) {
            adapter.add(new UserScoreItem(u));
        }

        binding.resultsRecycler.setAdapter(adapter);

        return binding.getRoot();
    }

    class UserScoreItem extends Item<GroupieViewHolder> {

        private User user;

        public UserScoreItem(User user) {
            this.user = user;
        }

        @Override
        public void bind(@NonNull GroupieViewHolder viewHolder, int position) {
            ImageView imageView = (ImageView) viewHolder.itemView.findViewById(R.id.score_image_view);
            TextView username = (TextView) viewHolder.itemView.findViewById(R.id.score_username);
            TextView userScore = (TextView) viewHolder.itemView.findViewById(R.id.score_score);

            Picasso.get()
                    .load(user.getPfpLink())
                    .into(imageView);
            username.setText(user.getUsername());

            DatabaseReference ref = FirebaseDatabase.getInstance(MainActivity.DATABASE_PATH)
                    .getReference("/games/" + currentGame.getPin() + "/scores/" + user.getUsername() + "/");
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Long score = (Long) snapshot.getValue();
                    if (score != null) {
                        userScore.setText(Long.toString(score));
                    } else {
                        userScore.setText("0");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            userScore.setText("null");
        }

        @Override
        public int getLayout() {
            return R.layout.user_score_item;
        }
    }
}
