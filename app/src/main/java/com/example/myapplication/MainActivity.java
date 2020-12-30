package com.example.myapplication;

import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        try {
            con = new ConnectionHelper();
            connect = ConnectionHelper.CONN();
        } catch (Exception e) {
            e.printStackTrace();
        }
        nameEditText=findViewById(R.id.nameEditText);
        numberEditText=findViewById(R.id.numberEditText);
        numberEditText.setOnKeyListener(this);
    }
    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent){
        if(i==KeyEvent.KEYCODE_ENTER && keyEvent.getAction()==KeyEvent.ACTION_DOWN){
            login(view);
        }
        return false;
    }
    public void login(View view){
        String name=nameEditText.getText().toString();
        String number=numberEditText.getText().toString();
        Log.i("Name",name);
        Log.i("Number",number);
        String query="Select * from login where phoneID= "+number+";";
        try {
            Statement st = connect.createStatement();
            ResultSet rs = st.executeQuery(query);
            if (rs.next()){
                Log.i("TAG",String.valueOf(rs.getString("nameID").equals(name)));
                //Log.i("DB Name",rs.getString("nameID")) ;
                //Log.i("DB Number",rs.getString("phoneID"));
                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
                //Transition to next Activity
                Intent intent = new Intent(MainActivity.this,SelectorActivity.class);
                startActivity(intent);
                finish();
            }else{
                Toast.makeText(this, "Login Unsuccessful. Try Again!", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}