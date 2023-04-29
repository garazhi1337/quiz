package com.example.schoolproject.views;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolproject.MainActivity;
import com.example.schoolproject.R;
import com.example.schoolproject.databinding.MyGamesFragmentBinding;
import com.example.schoolproject.models.Game;
import com.example.schoolproject.models.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.GroupieViewHolder;
import com.xwray.groupie.Item;

import java.util.ArrayList;

public class MyGamesFragment extends Fragment {

    //отображение всех созданных игр пользовтеля

    private MyGamesFragmentBinding binding;
    private GroupAdapter<GroupieViewHolder> adapter;
    private ArrayList<Game> games;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = MyGamesFragmentBinding.inflate(inflater, container, false);

        adapter = new GroupAdapter<>();
        games = new ArrayList<>();


        DatabaseReference ref = FirebaseDatabase.getInstance(MainActivity.DATABASE_PATH).getReference("/games/");
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Game game = snapshot.getValue(Game.class);
                if (game.getAuthor().equals(MainActivity.currentUser.getUsername())) {
                    games.add(game);
                    refreshAdapter();
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

        return binding.getRoot();
    }

    //очищает адаптер в котором находится массив
    public void refreshAdapter() {
        adapter.clear();
        for (Game g : games) {
            adapter.add(new MyGameItem(g));
        }
        binding.recyclerView.setAdapter(adapter);
    }

    class MyGameItem extends Item<GroupieViewHolder> {

        private Game game;
        private ArrayList<String> memberNames = new ArrayList<>();

        public MyGameItem(Game game) {
            this.game = game;
        }

        @Override
        public void bind(@NonNull GroupieViewHolder viewHolder, int position) {
            TextView author = (TextView) viewHolder.itemView.findViewById(R.id.authorItem);
            TextView members = (TextView) viewHolder.itemView.findViewById(R.id.playersItem);
            TextView questionsNum = (TextView) viewHolder.itemView.findViewById(R.id.qnumItem);
            TextView pin = (TextView) viewHolder.itemView.findViewById(R.id.pinItem);
            TextView started = (TextView) viewHolder.itemView.findViewById(R.id.startedItem);
            FloatingActionButton start = (FloatingActionButton) viewHolder.itemView.findViewById(R.id.startGame);
            FloatingActionButton delete = (FloatingActionButton) viewHolder.itemView.findViewById(R.id.deleteGame);

            author.setText(getResources().getString(R.string.author) + " " + game.getAuthor());

            //String questionsNumStr = questionsNum.getText().toString();
            if (game.getQuestions() == null) {
                questionsNum.setText(getResources().getString(R.string.qnum) + " 0");
            } else {
                questionsNum.setText(getResources().getString(R.string.qnum) + " " + game.getQuestions().size());
            }

            pin.setText(getResources().getString(R.string.pin) + " " + game.getPin());
            if (game.getStarted()) {
                started.setText(R.string.gamestarted);
                started.setTextColor(getResources().getColor(R.color.temno_salatowy));
            } else {
                started.setText(R.string.gamenotstarted);
                started.setTextColor(getResources().getColor(R.color.red));
            }

            DatabaseReference ref = FirebaseDatabase.getInstance(MainActivity.DATABASE_PATH).getReference("/games/" + game.getPin() + "/");

            start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!game.getStarted()) {
                        game.setStarted(true);
                        ref.setValue(game);
                        refreshAdapter();
                    }
                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ref.removeValue();
                    games.remove(game);
                    Toast.makeText(getContext(), "deleted", Toast.LENGTH_SHORT).show();
                    refreshAdapter();
                }
            });

            DatabaseReference ref2 = FirebaseDatabase.getInstance(MainActivity.DATABASE_PATH).getReference("/games/" + game.getPin() + "/players/");
            ref2.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    //добавляю список участников
                    User user = snapshot.getValue(User.class);
                    //memberNames.add(user.getUsername());
                    members.setText(members.getText().toString().concat(user.getUsername() + ", "));

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

            //for (String username : memberNames) {
            //    members.setText(members.getText() + username);
            //}
        }

        @Override
        public int getLayout() {
            return R.layout.my_games_item;
        }
    }
}
