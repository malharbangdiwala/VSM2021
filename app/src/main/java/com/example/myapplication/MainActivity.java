package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity implements View.OnKeyListener {
    ConnectionHelper con;
    Connection connect;
    EditText nameEditText;
    EditText numberEditText;
    EditText passwordEditText;
    SharedPreferences sharedPreferences;
    public static final String MyPREFERENCES = "MyPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        try {
            con = new ConnectionHelper();
            connect = ConnectionHelper.CONN();
        } catch (Exception e) {
            e.printStackTrace();
        }
        nameEditText=findViewById(R.id.nameEditText);
        numberEditText=findViewById(R.id.numberEditText);
        passwordEditText=findViewById(R.id.passwordEditText);
        passwordEditText.setOnKeyListener(this);

    }
    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent){
        if(i==KeyEvent.KEYCODE_ENTER && keyEvent.getAction()==KeyEvent.ACTION_DOWN){
            login(view);
        }
        return false;
    }
    public void login(View view){
        String name=nameEditText.getText().toString().toUpperCase();
        String number=numberEditText.getText().toString();
        String password=passwordEditText.getText().toString();
        Log.i("null check",name+"..."+number);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if(name.equals("") || number.equals("")||password.equals("")){
            Toast.makeText(this, "Please fill all the fields!", Toast.LENGTH_SHORT).show();
        }
        else {
            editor.putString("number", number);
            editor.putString("name",name.toLowerCase());
            editor.apply();
            String query = "Select * from login where phoneID= " + number+"and upper(nameID)= '"+name +"' and password="+password+" ;";
            try {
                Statement st = connect.createStatement();
                ResultSet rs = st.executeQuery(query);
                if (rs.next()) {
                    if (rs.getInt("day") == 0) {
                        Toast.makeText(this, "Your registered slot is not today!", Toast.LENGTH_SHORT).show();
                    } else {
                        if (rs.getInt("loginflag") == 1) {
                            String updateLoginFlag = "Update login set loginflag = 1 where phoneID= " + number + ";";
                            try {
                                Statement st2 = connect.createStatement();
                                ResultSet rs2 = st2.executeQuery(updateLoginFlag);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, SelectorActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(this, "This user has already logged in!", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(this, "Login Unsuccessful. Try Again!", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}