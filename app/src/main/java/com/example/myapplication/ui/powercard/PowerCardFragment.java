package com.example.myapplication.ui.powercard;

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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final Button usepc1=requireView().findViewById(R.id.usepc1);
        number = sharedPreferences.getString("number","");

        usepc1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(usepc1.getText().toString().equals("use")) {
                    //TODO Call powercard1 function
                    Toast.makeText(requireContext(),"Powercard1 active",Toast.LENGTH_SHORT).show();
                    usepc1.setText("Used");
                    String query="Update powercard set pc1=0 where phoneID="+number+";";
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


        final Button pc1=requireView().findViewById(R.id.powercard1);
        pc1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(requireContext())
                        .setPositiveButton("Powercard1 info", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).show();
            }
        });

        final Button pc2=requireView().findViewById(R.id.powercard2);
        pc2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(requireContext())
                        .setPositiveButton("Powercard2 info", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).show();
            }
        });

        final Button pc3=requireView().findViewById(R.id.powercard3);
        pc3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(requireContext())
                        .setPositiveButton("Powercard3 info", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).show();
            }
        });
    }
}