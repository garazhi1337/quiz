package com.example.schoolproject.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.schoolproject.databinding.ResultsFragmentBinding;

public class ResultsFragment extends Fragment {

    private ResultsFragmentBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ResultsFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}
