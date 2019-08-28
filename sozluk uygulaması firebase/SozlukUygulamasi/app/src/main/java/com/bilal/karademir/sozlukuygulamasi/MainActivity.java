package com.bilal.karademir.sozlukuygulamasi;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private ArrayList<Kelimeler> kelimelerArrayList;
    private KelimeAdapter kelimeAdapter;
     Context context = this;
    FirebaseDatabase database;
    DatabaseReference myRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.rec);
        toolbar.setTitle("Sözlük Uygulaması");
        setSupportActionBar(toolbar);


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("kelimeler");
        kelimelerArrayList = new ArrayList<>();
        kelimeAdapter = new KelimeAdapter(context,kelimelerArrayList);
        recyclerView.setAdapter(kelimeAdapter);

        tumKelimeler();






    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        MenuItem menuItem = menu.findItem(R.id.action_ara);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(this);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {

        Log.e("onQueryTextSubmit",s);
        //aramaYap(s);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        Log.e("onQueryTextChange",s);
        kelimeAra(s);


        return false;
    }

    public void tumKelimeler(){

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                kelimelerArrayList.clear();

                for (DataSnapshot d: dataSnapshot.getChildren()){

                    Kelimeler kelime = d.getValue(Kelimeler.class);
                    kelime.setKelime_id(d.getKey());

                    kelimelerArrayList.add(kelime);
                }

                kelimeAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void kelimeAra(final String arananKelime){

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                kelimelerArrayList.clear();

                for (DataSnapshot d: dataSnapshot.getChildren()){

                    Kelimeler kelime = d.getValue(Kelimeler.class);

                    if(kelime.getIngilizce().contains(arananKelime)){

                        kelime.setKelime_id(d.getKey());

                        kelimelerArrayList.add(kelime);
                    }

                }

                kelimeAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
