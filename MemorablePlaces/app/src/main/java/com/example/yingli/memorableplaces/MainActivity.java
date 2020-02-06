package com.example.yingli.memorableplaces;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

class DataHolder {
    final ArrayList<String> locations = new ArrayList<>();

    private DataHolder() {}

    static DataHolder getInstance() {
        if( instance == null ) {
            instance = new DataHolder();
        }
        return instance;
    }

    private static DataHolder instance;
}




public class MainActivity extends AppCompatActivity {



    static SharedPreferences sharedPreferences;
    static ArrayList<String> locations = new ArrayList<>();

    public void toMaps(View view){
        Intent intent = new Intent(getApplicationContext(), MapsActivity.class);

        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        sharedPreferences = this.getSharedPreferences("com.example.yingli.memorableplaces", Context.MODE_PRIVATE);


        ListView listView = findViewById(R.id.listView);

//        Intent intent = getIntent();
//
//
//
//        String name = intent.getStringExtra("name");



        //Toast.makeText(this, name, Toast.LENGTH_SHORT);


        //ArrayList<String> newFriends = new ArrayList<>();
        try {
            locations = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("friends", ObjectSerializer.serialize(new ArrayList<String>())));
        }catch(Exception e){
            e.printStackTrace();
        }

        //List<String> curLoc = DataHolder.getInstance().locations;


        //if(curLoc.get(0) != null) {
        //    Toast.makeText(this, curLoc.get(0), Toast.LENGTH_SHORT).show();
        //}
            //fam.add(name);

            //family = intent.getStringArrayListExtra("family");


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, locations);


        listView.setAdapter(arrayAdapter);


    }

    @Override
    protected void onRestart() {
// TODO Auto-generated method stub
        super.onRestart();

        ListView listView = findViewById(R.id.listView);
        //List<String> curLoc = locations
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, locations);


        listView.setAdapter(arrayAdapter);

        //Do your code here
    }
}
