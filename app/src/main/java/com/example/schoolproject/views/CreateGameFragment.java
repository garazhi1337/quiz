package com.example.schoolproject.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.schoolproject.databinding.CreateGameFragmentBinding;

public class CreateGameFragment extends Fragment {

    private CreateGameFragmentBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = CreateGameFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}
