package com.example.myapplication.ui.powercard;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.ConnectionHelper;
import com.example.myapplication.News;
import com.example.myapplication.R;
import com.example.myapplication.powercard3;
import com.example.myapplication.ui.home.HomeFragment;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static com.example.myapplication.MainActivity.MyPREFERENCES;

public class PowerCardFragment extends Fragment {
    SharedPreferences sharedPreferences;
    ConnectionHelper con;
    Connection connect;
    String number;
    int status;
    public static int pc2flag;
    public static int pc3flag;

    AnimatorSet front_anim;
    AnimatorSet front_anim1;
    AnimatorSet back_anim;
    AnimatorSet back_anim1;
    boolean isFrontPc2 = true;
    boolean isFrontPc3 = true;

    LinearLayout pc2Front;
    LinearLayout pc2Back;
    LinearLayout pc2;

    LinearLayout pc3Front;
    LinearLayout pc3Back;
    LinearLayout pc3;

    public PowerCardFragment(int status) {
        this.status = status;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        sharedPreferences = this.getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        try {
            con = new ConnectionHelper();
            connect = ConnectionHelper.CONN();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return root;
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pc2 = view.findViewById(R.id.powercard2Layout);
        pc3 = view.findViewById(R.id.powercard3Layout);
        front_anim = (AnimatorSet) AnimatorInflater.loadAnimator(requireContext(),R.animator.front_animator);
        front_anim1 = (AnimatorSet) AnimatorInflater.loadAnimator(requireContext(),R.animator.front_animator);
        back_anim = (AnimatorSet) AnimatorInflater.loadAnimator(requireContext(),R.animator.back_animator);
        back_anim1 = (AnimatorSet) AnimatorInflater.loadAnimator(requireContext(),R.animator.back_animator);

        number = sharedPreferences.getString("number","");

        final Button usepc2=requireView().findViewById(R.id.usepc2);
        usepc2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(usepc2.getText().toString().equals("use")) {
                    pc2flag=1;
                    News.setNewsText();
                    Toast.makeText(requireContext(),"Powercard2 active",Toast.LENGTH_SHORT).show();
                    usepc2.setText("Used");
                    String query="Update powercard set pc2=0 where phoneID="+number+";";
                    if (status==1) {
                        try {
                            Statement st = connect.createStatement();
                            st.executeQuery(query);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
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
                    pc3flag=1;
                    Double cash=powercard3.addcash();
                    String addcash="Update valuation set cash="+cash+" where phoneID="+number+";";
                    Log.i("PC3 QUERY",addcash);
                    if (status==1) {
                        try {
                            Statement st = connect.createStatement();
                            st.executeQuery(addcash);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                    HomeFragment.userAmount.setText(String.valueOf(cash));
                    usepc3.setText("Used");
                    Toast.makeText(requireContext(),"Powercard3 active",Toast.LENGTH_SHORT).show();
                    String query="Update powercard set pc3=0 where phoneID="+number+";";
                    if (status==1) {
                        try {
                            Statement st = connect.createStatement();
                            st.executeQuery(query);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }else{
                    Toast.makeText(requireContext(),"You have used this",Toast.LENGTH_SHORT).show();
                }
            }
        });

        pc2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pc2Front = view.findViewById(R.id.frontpowercard2);
                pc2Back = view.findViewById(R.id.backpowercard2);
                if (isFrontPc2){
                    front_anim1.setTarget(pc2Front);
                    back_anim1.setTarget(pc2Back);
                    front_anim1.start();
                    back_anim1.start();
                    isFrontPc2 = false;
                }else {
                    front_anim1.setTarget(pc2Back);
                    back_anim1.setTarget(pc2Front);
                    front_anim1.start();
                    back_anim1.start();
                    isFrontPc2 = true;
                }
            }
        });

        pc3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pc3Front = view.findViewById(R.id.powercard3front);
                pc3Back = view.findViewById(R.id.powercard3back);
                if (isFrontPc3){
                    front_anim.setTarget(pc3Front);
                    back_anim.setTarget(pc3Back);
                    front_anim.start();
                    back_anim.start();
                    isFrontPc3 = false;
                }else {
                    front_anim.setTarget(pc3Back);
                    back_anim.setTarget(pc3Front);
                    front_anim.start();
                    back_anim.start();
                    isFrontPc3 = true;
                }
            }
        });
    }
}