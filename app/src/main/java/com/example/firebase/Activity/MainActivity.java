package com.example.firebase.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.firebase.Adapter.Adapter;
import com.example.firebase.Model;
import com.example.firebase.R;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView postsRv;
    private Button loadMoreBtn;
    private EditText search;
    private ImageButton searchBtn;
    private String url="";
    private String href="";
    private ArrayList<Model> postArrayList;
    private Adapter adapter;
    private ProgressDialog progressDialog;
    private static final String TAG = "MAIN_TAG";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Food");
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        toolbar.setTitleTextAppearance(this, R.style.CustomToolbarTitleStyle);
        setSupportActionBar(toolbar);

        loadMoreBtn = findViewById(R.id.loadMoreBtn);
        postsRv = findViewById(R.id.postsRv);
        search = findViewById(R.id.search);
        searchBtn = findViewById(R.id.searchBtn);


        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait...");

        postArrayList = new ArrayList<>();

        loadPosts();

        loadMoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = search.getText().toString().trim();
                if(TextUtils.isEmpty(query)){
                    loadPosts();
                }
                else {
                    searchPost(query);
                }
            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                href="";
                url="";

                postArrayList = new ArrayList<>();

                String query = search.getText().toString().trim();
                if(TextUtils.isEmpty(query)){
                    loadPosts();
                    Toast.makeText(MainActivity.this,"Search data is empty",Toast.LENGTH_SHORT).show();
                }
                else {
                    searchPost(query);
                }
            }
        });
    }

    private void searchPost(String query) {
        progressDialog.show();
        if(href.equals("")){
            url = "https://api.edamam.com/api/food-database/v2/parser?app_id=d3560df2&app_key=e4510b3ee676a353abcf1c290f3bef64&ingr="+query;
        }else{
            Log.d(TAG, "search: Next page:"+href);
            url=href;
        }
        Log.d(TAG,"search: URL:"+url);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Log.d(TAG, "onReponse" + response);

                try{
                    JSONObject jsonObject =new JSONObject(response);
                    href = jsonObject.getJSONObject("_links").getJSONObject("next").getString("href");

                    JSONArray jsonArray = jsonObject.getJSONArray("hints");
                    for(int i=0;i<jsonArray.length();i++){
                        try {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            String foodId = jsonObject1.getJSONObject("food").getString("foodId");
                            String label = jsonObject1.getJSONObject("food").getString("label");
                            String category = jsonObject1.getJSONObject("food").getString("category");
                            String categoryLabel = jsonObject1.getJSONObject("food").getString("categoryLabel");
                            String image = jsonObject1.getJSONObject("food").getString("image");

                            Model model = new Model(""+foodId,
                                    ""+label,
                                    ""+category,
                                    ""+image,
                                    ""+categoryLabel);

                            postArrayList.add(model);

                        }catch (Exception e){
                            Log.d(TAG,"onResponse: 1:"+e.getMessage());
                        }
                    }
                    adapter = new Adapter(MainActivity.this,postArrayList);
                    postsRv.setAdapter(adapter);
                    progressDialog.dismiss();

                }catch (Exception e){

                    Log.d(TAG,"onReponse: 2:"+e.getMessage());
                    Toast.makeText(MainActivity.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d(TAG,"onErrorReponse:"+error.getMessage());
                Toast.makeText(MainActivity.this,""+error.getMessage(),Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void loadPosts() {
        progressDialog.show();
        if(href.equals("")){
            url = "https://api.edamam.com/api/food-database/v2/parser?app_id=d3560df2&app_key=e4510b3ee676a353abcf1c290f3bef64";
        }else{
            Log.d(TAG, "load: Next page:"+href);
            url=href;
        }
        Log.d(TAG,"load: URL:"+url);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Log.d(TAG, "onReponse" + response);

                try{
                    JSONObject jsonObject =new JSONObject(response);
                    href = jsonObject.getJSONObject("_links").getJSONObject("next").getString("href");

                    JSONArray jsonArray = jsonObject.getJSONArray("hints");
                    for(int i=0;i<jsonArray.length();i++){
                        try {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            String foodId = jsonObject1.getJSONObject("food").getString("foodId");
                            String label = jsonObject1.getJSONObject("food").getString("label");
                            String category = jsonObject1.getJSONObject("food").getString("category");
                            String categoryLabel = jsonObject1.getJSONObject("food").getString("categoryLabel");
                            String image = jsonObject1.getJSONObject("food").getString("image");

                            Model model = new Model(""+foodId,
                                    ""+label,
                                    ""+category,
                                    ""+image,
                                    ""+categoryLabel);

                            postArrayList.add(model);

                        }catch (Exception e){
                            Log.d(TAG,"onResponse: 1:"+e.getMessage());
                        }
                    }
                    adapter = new Adapter(MainActivity.this,postArrayList);
                    postsRv.setAdapter(adapter);
                    progressDialog.dismiss();

                }catch (Exception e){

                    Log.d(TAG,"onReponse: 2:"+e.getMessage());
                    Toast.makeText(MainActivity.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d(TAG,"onErrorReponse:"+error.getMessage());
                Toast.makeText(MainActivity.this,""+error.getMessage(),Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);

        MenuItem item1 = menu.findItem(R.id.item1);
        MenuItem item2 = menu.findItem(R.id.item2);
        item1.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                Intent intent = new Intent(MainActivity.this, FoodFavoriteActivity.class);
                startActivity(intent);
                return true;
            }
        });

        item2.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                confirm();
                return true;
            }
        });
        return false;
    }
    void confirm() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure you want to logout?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing or perform any desired action
            }
        });
        builder.create().show();
    }
}