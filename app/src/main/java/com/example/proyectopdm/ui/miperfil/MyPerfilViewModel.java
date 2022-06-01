package com.example.proyectopdm.ui.miperfil;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyPerfilViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public MyPerfilViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}