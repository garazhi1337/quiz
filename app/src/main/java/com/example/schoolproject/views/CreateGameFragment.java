package com.example.schoolproject.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.schoolproject.MainActivity;
import com.example.schoolproject.R;
import com.example.schoolproject.databinding.CreateGameFragmentBinding;
import com.example.schoolproject.models.Question;

import java.util.ArrayList;

public class CreateGameFragment extends Fragment {

    private CreateGameFragmentBinding binding;
    private ArrayList<Question> questions;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = CreateGameFragmentBinding.inflate(inflater, container, false);
        questions = new ArrayList<>();
        //получение списка вопросов из creategamefragment
        Bundle data = new Bundle();
        data = this.getArguments();
        questions = data.getParcelableArrayList("CREATE_Q");

        binding.addQuestionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                CreateQuestionFragment fragment = new CreateQuestionFragment();
                Bundle data = new Bundle();
                data.putParcelableArrayList("CREATE_G", questions);
                fragment.setArguments(data);
                ft.replace(R.id.nav_host_fragment, fragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        return binding.getRoot();
    }
}
