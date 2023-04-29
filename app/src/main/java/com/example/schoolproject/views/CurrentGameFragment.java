package com.example.schoolproject.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolproject.MainActivity;
import com.example.schoolproject.R;
import com.example.schoolproject.databinding.CurrentGameFragmentBinding;
import com.example.schoolproject.models.Game;
import com.example.schoolproject.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
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

public class CurrentGameFragment extends Fragment {

    //все что происходит в текущей игре
    User currentUser;
    Game currentGame;
    GroupAdapter<GroupieViewHolder> adapter = new GroupAdapter<>();
    ArrayList<User> users = new ArrayList<>();

    private CurrentGameFragmentBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = CurrentGameFragmentBinding.inflate(inflater, container, false);

        //после установки пользователя задается игра в методу ниже и заполняется адаптер
        setCurrentUser();

        return binding.getRoot();
    }

    public void setCurrentUser() {
        DatabaseReference ref = FirebaseDatabase.getInstance(MainActivity.DATABASE_PATH).getReference("/users");
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                User user = snapshot.getValue(User.class);
                //Toast.makeText(MainActivity.this, user.getUsername(), Toast.LENGTH_SHORT).show();

                if (user.getUid().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                    currentUser = user;
                    setCurrentGame();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void setCurrentGame() {
        DatabaseReference ref = FirebaseDatabase.getInstance(MainActivity.DATABASE_PATH).getReference("/games/");
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Game game = snapshot.getValue(Game.class);
                DatabaseReference ref2 = FirebaseDatabase.getInstance(MainActivity.DATABASE_PATH)
                        .getReference("/games/" + game.getPin() + "/players/");
                ref2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            User user = dataSnapshot.getValue(User.class);
                            if (user != null) {
                                if (user.getUsername().equals(currentUser.getUsername())) {
                                    currentGame = game;
                                    fillAdapter();
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void fillAdapter() {
        DatabaseReference ref = FirebaseDatabase.getInstance(MainActivity.DATABASE_PATH).getReference("/games/" + currentGame.getPin() + "/players/");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {
                    User player = data.getValue(User.class);
                    users.add(player);
                    refreshAdapter();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void refreshAdapter() {
        adapter.clear();
        for (User u : users) {
            adapter.add(new UserScoreItem(u));
        }
        binding.currentGameRecyclerView.setAdapter(adapter);
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

            userScore.setText("null");
        }

        @Override
        public int getLayout() {
            return R.layout.user_score_item;
        }
    }


}
