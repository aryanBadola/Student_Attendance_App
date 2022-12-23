package com.example.android.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.example.android.myapplication.Adapter.ClassNamesAdapter;
import com.example.android.myapplication.Model.ClassNamesModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class ClassActivity extends AppCompatActivity {


    Toolbar toolbar;
    RecyclerView recyclerView;
    ArrayList<ClassNamesModel> arrClassNames = new ArrayList<>();
    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class);


        toolbar=(Toolbar) findViewById(R.id.toolbar);
        recyclerView=(RecyclerView) findViewById(R.id.class_names_recycler_view);
        fab=(FloatingActionButton)findViewById(R.id.floating_action_button_class_names);


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayout.VERTICAL));

        arrClassNames.add(new ClassNamesModel(R.drawable.computer_science,"Engineering Mathematics","5th","CSE"));
        arrClassNames.add(new ClassNamesModel(R.drawable.computer_science,"Engineering Mathematics","5th","CSE"));
        arrClassNames.add(new ClassNamesModel(R.drawable.computer_science,"Engineering Mathematics","5th","CSE"));

        ClassNamesAdapter adapter=new ClassNamesAdapter(this,arrClassNames);
        recyclerView.setAdapter(adapter);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id= item.getItemId();
                if(id == R.id.action_profile){
                    startActivity(new Intent(ClassActivity.this,UserProfileActivity.class));
                }
                return true;
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ClassActivity.this, InsertClassActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        toolbar.inflateMenu(R.menu.user_profile);
        return true;
    }


}