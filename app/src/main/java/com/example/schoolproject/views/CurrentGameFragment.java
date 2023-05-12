package com.example.schoolproject.views;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.database.DatabaseUtilsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolproject.MainActivity;
import com.example.schoolproject.R;
import com.example.schoolproject.databinding.CurrentGameFragmentBinding;
import com.example.schoolproject.models.Game;
import com.example.schoolproject.models.Question;
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

import java.lang.reflect.Array;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class CurrentGameFragment extends Fragment {

    //все что происходит в текущей игре

    private User currentUser;
    private Game currentGame;
    private GroupAdapter<GroupieViewHolder> adapter = new GroupAdapter<>();
    private ArrayList<User> totalPlayers = new ArrayList<>();
    private ArrayList<Question> questions = new ArrayList<>();
    private Question currentQuestion;
    private int k = 0;

    private CurrentGameFragmentBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = CurrentGameFragmentBinding.inflate(inflater, container, false);

        //после установки пользователя задается опрос в методе ниже и заполняется адаптер с очками пользователей
        setCurrentUser();

        binding.answer1Tw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getContext(), "1", Toast.LENGTH_SHORT).show();

                //проверяет правильно ли ответил участник
                DatabaseReference ref = FirebaseDatabase.getInstance(MainActivity.DATABASE_PATH)
                        .getReference("/games/" + currentGame.getPin() + "/questions/ID" + currentQuestion.getId() + "/answers/" + currentUser.getUsername() + "/");
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Long userAnswer = (Long) snapshot.getValue();

                        if (userAnswer == null) {
                            ref.setValue(1);

                            DatabaseReference ref2 = FirebaseDatabase.getInstance(MainActivity.DATABASE_PATH)
                                    .getReference("/games/" + currentGame.getPin() + "/scores/" + currentUser.getUsername() + "/");
                            ref2.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    Long score = (Long) snapshot.getValue();
                                    if (currentQuestion.getAnswerOne().containsValue(true)) {
                                        if (score == null) {
                                            ref2.setValue(127);
                                        } else {
                                            ref2.setValue(score + 149);
                                        }
                                    }
                                    ref2.removeEventListener(this);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }

                        ref.removeEventListener(this);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

        binding.answer2Tw.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //проверяет правильно ли ответил участник
                DatabaseReference ref = FirebaseDatabase.getInstance(MainActivity.DATABASE_PATH)
                        .getReference("/games/" + currentGame.getPin() + "/questions/ID" + currentQuestion.getId() + "/answers/" + currentUser.getUsername() + "/");
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Long userAnswer = (Long) snapshot.getValue();

                        if (userAnswer == null) {
                            ref.setValue(1);

                            DatabaseReference ref2 = FirebaseDatabase.getInstance(MainActivity.DATABASE_PATH)
                                    .getReference("/games/" + currentGame.getPin() + "/scores/" + currentUser.getUsername() + "/");
                            ref2.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    Long score = (Long) snapshot.getValue();
                                    if (currentQuestion.getAnswerTwo().containsValue(true)) {
                                        if (score == null) {
                                            ref2.setValue(127);
                                        } else {
                                            ref2.setValue(score + 149);
                                        }
                                    }
                                    ref2.removeEventListener(this);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }

                        ref.removeEventListener(this);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        binding.answer3Tw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //проверяет правильно ли ответил участник
                DatabaseReference ref = FirebaseDatabase.getInstance(MainActivity.DATABASE_PATH)
                        .getReference("/games/" + currentGame.getPin() + "/questions/ID" + currentQuestion.getId() + "/answers/" + currentUser.getUsername() + "/");
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Long userAnswer = (Long) snapshot.getValue();

                        if (userAnswer == null) {
                            ref.setValue(1);

                            DatabaseReference ref2 = FirebaseDatabase.getInstance(MainActivity.DATABASE_PATH)
                                    .getReference("/games/" + currentGame.getPin() + "/scores/" + currentUser.getUsername() + "/");
                            ref2.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    Long score = (Long) snapshot.getValue();
                                    if (currentQuestion.getAnswerThree().containsValue(true)) {
                                        if (score == null) {
                                            ref2.setValue(127);
                                        } else {
                                            ref2.setValue(score + 149);
                                        }
                                    }
                                    ref2.removeEventListener(this);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }

                        ref.removeEventListener(this);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        binding.answer4Tw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //проверяет правильно ли ответил участник
                DatabaseReference ref = FirebaseDatabase.getInstance(MainActivity.DATABASE_PATH)
                        .getReference("/games/" + currentGame.getPin() + "/questions/ID" + currentQuestion.getId() + "/answers/" + currentUser.getUsername() + "/");
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Long userAnswer = (Long) snapshot.getValue();

                        if (userAnswer == null) {
                            ref.setValue(1);

                            DatabaseReference ref2 = FirebaseDatabase.getInstance(MainActivity.DATABASE_PATH)
                                    .getReference("/games/" + currentGame.getPin() + "/scores/" + currentUser.getUsername() + "/");
                            ref2.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    Long score = (Long) snapshot.getValue();
                                    if (currentQuestion.getAnswerFour().containsValue(true)) {
                                        if (score == null) {
                                            ref2.setValue(127);
                                        } else {
                                            ref2.setValue(score + 149);
                                        }
                                    }
                                    ref2.removeEventListener(this);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }

                        ref.removeEventListener(this);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        //поток нужен чтобы обновить ui после 2 секунд после захода на фрагмент
        new Thread(new Runnable() {
            boolean fl = false;
            @Override
            public void run() {
                while (!fl) {

                    k++;
                    System.out.println(k);

                    if (k == 2) {
                        setCurrentQuestion(currentGame);
                    } else if (k > 2) {
                        fl = true;
                    }

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }).start();

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

                                    DatabaseReference isStartedRef = FirebaseDatabase.getInstance(MainActivity.DATABASE_PATH)
                                            .getReference("/games/" + currentGame.getPin());
                                    isStartedRef.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            HashMap<String, Boolean> isStarted = (HashMap<String, Boolean>) snapshot.getValue();
                                            if (isStarted != null) {
                                                if (!isStarted.containsValue(Boolean.TRUE)) {
                                                    binding.upperLayout.setVisibility(View.INVISIBLE);
                                                    binding.scrollView2.setVisibility(View.INVISIBLE);

                                                    binding.innerlayout.setVisibility(View.VISIBLE);
                                                } else {
                                                    binding.upperLayout.setVisibility(View.VISIBLE);
                                                    binding.scrollView2.setVisibility(View.VISIBLE);

                                                    binding.innerlayout.setVisibility(View.INVISIBLE);
                                                }
                                            } else {
                                                FragmentManager fm = getActivity().getSupportFragmentManager();
                                                FragmentTransaction ft = fm.beginTransaction();
                                                ft.replace(R.id.nav_host_fragment, new EnterGameFragment());
                                                ft.addToBackStack(null);
                                                ft.commit();
                                            }

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });

                                    fillAdapter();
                                    binding.currentPin.setText("ID: " + currentGame.getPin());
                                    getCurrentGameQuestions(currentGame);
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
        try {
            DatabaseReference ref = FirebaseDatabase.getInstance(MainActivity.DATABASE_PATH)
                    .getReference("/games/" + currentGame.getPin() + "/players/");
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    totalPlayers.clear();
                    for (DataSnapshot data : snapshot.getChildren()) {
                        User player = data.getValue(User.class);
                        totalPlayers.add(player);
                        refreshAdapter();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        } catch (Exception e) {

        }
    }

    public void refreshAdapter() {
        adapter.clear();

        for (User u : totalPlayers) {
            adapter.add(new UserScoreItem(u));

        }

        binding.currentGameRecyclerView.setAdapter(adapter);
        //sortScores(adapter);
    }

    public void setCurrentQuestion(Game game) {
        /**
         * если колво ответов на вопрос i в цикле меньше колва пользователей,
         * то этот вопрос устанавливается как вопрос в данный момент (i-1) у всех пользователей
         * Если колво ответов на вопрос совпадает с колвом пользователей то вопросом в данный момент
         * будет вопрос i (отнимаю единицу потому что цикл начитается с 1)
         */
        for (int i = 1; i < questions.size()+1; i++) {
            DatabaseReference ref = FirebaseDatabase.getInstance(MainActivity.DATABASE_PATH)
                    .getReference("/games/" + game.getPin() + "/questions/ID" + i + "/answers/");
            binding.answers.setText(getResources().getString(R.string.answers) + " " + 0);
            int finalI = i;
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    long answersCount = 0;
                    for (DataSnapshot snap : snapshot.getChildren()) {
                        Long k = (Long) snap.getValue();
                        answersCount += k;
                        if (answersCount == questions.size()) {
                            binding.answers.setText(getResources().getString(R.string.answers) + " " + 0);
                        } else {
                            binding.answers.setText(getResources().getString(R.string.answers) + " " + answersCount);
                        }

                    }


                    if ((answersCount == 0 || answersCount < totalPlayers.size()) && (finalI < questions.size())) {

                        //Toast.makeText(getContext(), "ostalsya v etom je", Toast.LENGTH_SHORT).show();
                        if (currentQuestion == null) {
                            currentQuestion = questions.get(finalI-1);
                            refreshUi(currentQuestion);
                        } else {
                            refreshUi(currentQuestion);
                        }

                        //flag[0] = true;
                        //ref.removeEventListener(this);
                    } else if (answersCount != 0 && answersCount >= totalPlayers.size() && finalI < questions.size()) {
                        if ((currentQuestion != null) && (!currentQuestion.equals(questions.get(finalI)))) {
                            //Toast.makeText(getContext(), finalI + "perehod na sleduyushii" + questions.size(), Toast.LENGTH_SHORT).show();
                            currentQuestion = questions.get(finalI);
                            refreshUi(currentQuestion);
                        }

                        currentQuestion = questions.get(finalI);

                    } else if (answersCount != 0 && answersCount >= totalPlayers.size() && finalI == questions.size()) {
                        currentQuestion = questions.get(questions.size()-1);
                        refreshUi(currentQuestion);
                        //Toast.makeText(getContext(), finalI + "final" + questions.size(), Toast.LENGTH_SHORT).show();
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        ResultsFragment fragment = new ResultsFragment();

                        Bundle data = new Bundle();
                        data.putParcelable("CURR_GAME", currentGame);
                        data.putParcelableArrayList("CURR_MEMBERS", totalPlayers);
                        fragment.setArguments(data);

                        ft.replace(R.id.nav_host_fragment, fragment);
                        ft.addToBackStack(null);
                        ft.commit();

                    } else {
                        refreshUi(currentQuestion);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    public void refreshUi(Question question) {
        binding.currentQuestionText.setText(question.getQuestionText());
        binding.quizTitle.setText(currentGame.getTitle());
        Picasso.get()
                .load(question.getPhotoUrl())
                .into(binding.imageView);
        binding.answer1Tw.setText(question.getAnswerOne().keySet().toArray()[0].toString());
        binding.answer2Tw.setText(question.getAnswerTwo().keySet().toArray()[0].toString());
        binding.answer3Tw.setText(question.getAnswerThree().keySet().toArray()[0].toString());
        binding.answer4Tw.setText(question.getAnswerFour().keySet().toArray()[0].toString());

        binding.currentQuestionNum.setText(question.getId() + " " + getResources().getString(R.string.of) + " " + questions.size());
    }

    //обавляет вопросы в массив
    public void getCurrentGameQuestions(Game game) {
        DatabaseReference ref = FirebaseDatabase.getInstance(MainActivity.DATABASE_PATH)
                .getReference("/games/" + game.getPin() + "/questions/");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                questions.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                    Question question = data.getValue(Question.class);
                    questions.add(question);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



    class UserScoreItem extends Item<GroupieViewHolder> {

        private User user;
        public Long score = Long.valueOf(0);

        public UserScoreItem(User user) {
            this.user = user;
        }

        @Override
        public void bind(@NonNull GroupieViewHolder viewHolder, int position) {

            if (user.getUsername().equals(currentUser.getUsername())) {
                viewHolder.itemView.findViewById(R.id.userScoreLayout)
                        .setBackground(getResources().getDrawable(R.drawable.shape6));
            }

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

                    if ((Long) snapshot.getValue() != null) {
                        score = (Long) snapshot.getValue();
                        userScore.setText(Long.toString(score));
                    } else {
                        userScore.setText("0");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }

        @Override
        public int getLayout() {
            return R.layout.user_score_item;
        }
    }
}