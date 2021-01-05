package com.example.myapplication.ui.powercard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;

public class PowerCardFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final Button usepc1=requireView().findViewById(R.id.usepc1);
        usepc1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(usepc1.getText().toString().equals("Use")) {
                    usepc1.setText("Used");
                }else{
                }
            }
        });

        final Button usepc2=requireView().findViewById(R.id.usepc1);
        usepc2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(usepc2.getText().toString().equals("Use")) {
                    usepc2.setText("Used");
                }else{
                }
            }
        });

        final Button usepc3=requireView().findViewById(R.id.usepc1);
        usepc3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(usepc3.getText().toString().equals("Use")) {
                    usepc3.setText("Used");
                }else{
                }
            }
        });


    }
}