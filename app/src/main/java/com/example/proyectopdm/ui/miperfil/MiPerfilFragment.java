package com.example.proyectopdm.ui.miperfil;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.proyectopdm.databinding.FragmentMiperfilBinding;

public class MiPerfilFragment extends Fragment {

    private FragmentMiperfilBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MyPerfilViewModel myPerfilViewModel =
                new ViewModelProvider(this).get(MyPerfilViewModel.class);

        binding = FragmentMiperfilBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textMiperfil;
        myPerfilViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}