package com.bilal.karademir.sozlukuygulamasi;

import android.content.Context;
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



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private List<Kelimeler> list;
    private KelimelerAdapter kelimeAdapter;
     Context context = this;
     private KelimelerDaoInterface kelimelerDIF;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.rec);
        toolbar.setTitle("Sözlük Uygulaması");
        setSupportActionBar(toolbar);

        kelimelerDIF = ApiUtils.getKisilerDaoInterfeace();


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        tumKelimeleriGetir();






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

        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        Log.e("onQueryTextChange",s);
        kelimeAra(s);


        return false;
    }


    public void tumKelimeleriGetir(){


        kelimelerDIF.tumKelimeler().enqueue(new Callback<KelimelerCevap>() {
            @Override
            public void onResponse(Call<KelimelerCevap> call, Response<KelimelerCevap> response) {


                list = new ArrayList<>();


               list = response.body().getKelimeler();


                kelimeAdapter = new KelimelerAdapter(context,list);
                recyclerView.setAdapter(kelimeAdapter);

            }

            @Override
            public void onFailure(Call<KelimelerCevap> call, Throwable t) {

            }
        });
    }

    public void kelimeAra(String arananKelime){


      kelimelerDIF.kelimeAra(arananKelime).enqueue(new Callback<KelimelerCevap>() {
          @Override
          public void onResponse(Call<KelimelerCevap> call, Response<KelimelerCevap> response) {

              List<Kelimeler> listTemp = response.body().getKelimeler();
              kelimeAdapter = new KelimelerAdapter(context,listTemp);
              recyclerView.setAdapter(kelimeAdapter);

          }

          @Override
          public void onFailure(Call<KelimelerCevap> call, Throwable t) {

          }
      });
    }




}
