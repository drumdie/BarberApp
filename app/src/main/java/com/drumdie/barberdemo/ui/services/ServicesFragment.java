package com.drumdie.barberdemo.ui.services;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.drumdie.barberdemo.R;


public class ServicesFragment extends Fragment {

    public View onCreateView( LayoutInflater inflater,
                              ViewGroup container,
                              Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.services_layout, container, false);

        return root;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
