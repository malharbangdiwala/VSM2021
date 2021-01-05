package com.example.myapplication.ui.powercard;

import android.os.Bundle;
import android.util.Log;
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
                if(usepc1.getText().toString().equals("use")) {
                    usepc1.setText("Used");
                }else{
                    Toast.makeText(requireContext(),"You have used this",Toast.LENGTH_SHORT).show();
                }
            }
        });

        final Button usepc2=requireView().findViewById(R.id.usepc2);
        usepc2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(usepc2.getText().toString().equals("use")) {
                    usepc2.setText("Used");
                }else{
                    Toast.makeText(requireContext(),"You have used this",Toast.LENGTH_SHORT).show();
                }
            }
        });

        final Button usepc3=requireView().findViewById(R.id.usepc3);
        usepc3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(usepc3.getText().toString().equals("use")) {
                    usepc3.setText("Used");
                }else{
                    Toast.makeText(requireContext(),"You have used this",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}