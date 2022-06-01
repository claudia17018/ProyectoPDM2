package com.example.proyectopdm.ui.proyectos;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProyectosViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ProyectosViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}