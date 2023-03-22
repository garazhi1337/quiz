package com.example.schoolproject.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.schoolproject.MainActivity;
import com.example.schoolproject.R;
import com.example.schoolproject.databinding.EnterGameFragmentBinding;
import com.example.schoolproject.models.Game;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EnterGameFragment extends Fragment {

    private EnterGameFragmentBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = EnterGameFragmentBinding.inflate(inflater, container, false);

        binding.enterGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference ref = FirebaseDatabase.getInstance(MainActivity.DATABASE_PATH).getReference("/games/");
                ref.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        Game game = snapshot.getValue(Game.class);
                        if (game.getPin().equals(binding.enterPin.getText().toString().trim())) {
                            DatabaseReference ref2 = FirebaseDatabase.getInstance(MainActivity.DATABASE_PATH)
                                    .getReference("/games/" + game.getPin() + "/players/" + MainActivity.currentUser.getUsername() + "/");
                            ref2.setValue(MainActivity.currentUser)
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        } else {
                            Toast.makeText(getContext(), getResources().getString(R.string.warning2), Toast.LENGTH_SHORT).show();
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
        });

        return binding.getRoot();
    }
}
