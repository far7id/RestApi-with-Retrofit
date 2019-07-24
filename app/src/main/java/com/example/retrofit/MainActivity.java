package com.example.retrofit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
{
    private Api api;
    private RecyclerView recyclerView;
    private NestedScrollView scroll;
    private TextView txtRes;
    private List<Post> recyclerPostsList;
    private RecyclerAdapter recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerxml);

        scroll = findViewById(R.id.scroll_xml);
        txtRes = findViewById(R.id.txt_res_xml);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);

        api = RetrofitBuilder.getRetrofitClient().create(Api.class);

        getPosts();
        //getComments();
        //createPost();
        //updatePost();
        //deletePost();
    }

    private void getPosts()
    {
        recyclerView.setVisibility(View.VISIBLE);

        recyclerPostsList = new ArrayList<>();

        //Call<List<Post>> call = api.getPosts();

        Call<List<Post>> call = api.getPosts(1, null, null);

        call.enqueue(new Callback<List<Post>>()
        {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response)
            {
                if (!response.isSuccessful())
                {
                    Log.e("Code: ", String.valueOf(response.code()));
                    return;
                }

                recyclerPostsList = response.body();
                recyclerAdapter = new RecyclerAdapter(recyclerPostsList, getApplicationContext());
                recyclerView.setAdapter(recyclerAdapter);
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t)
            {
                Log.e("onFailure: ", t.getMessage());
            }
        });
    }

    private void getComments()
    {
        scroll.setVisibility(View.VISIBLE);
        txtRes.setVisibility(View.VISIBLE);
        Call<List<Comment>> call = api.getComments(3);

        call.enqueue(new Callback<List<Comment>>()
        {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response)
            {
                if (!response.isSuccessful())
                {
                    txtRes.setText("Code: " + response.code());
                    return;
                }

                List<Comment> comments = response.body();

                for (Comment comment : comments)
                {
                    String content = "";
                    content += "ID: " + comment.getId() + "\n";
                    content += "Post Id: " + comment.getPostId() + "\n";
                    content += "Name: " + comment.getName() + "\n";
                    content += "Email: " + comment.getEmail() + "\n";
                    content += "Text: " + comment.getText() + "\n";

                    txtRes.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t)
            {
                txtRes.setText(t.getMessage());
            }
        });
    }

    private void createPost()
    {
        scroll.setVisibility(View.VISIBLE);
        txtRes.setVisibility(View.VISIBLE);

        Post post = new Post(23, "new title", "new text");

        Call<Post> call = api.createPost(post);

        call.enqueue(new Callback<Post>()
        {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response)
            {
                if (!response.isSuccessful())
                {
                    txtRes.setText("Code: " + response.code());
                    return;
                }

                Post postResponse = response.body();

                String content = "";
                content += "Code: " + response.code() + "\n";
                content += "ID: " + postResponse.getId() + "\n";
                content += "User Id: " + postResponse.getUserId() + "\n";
                content += "Title: " + postResponse.getTitle() + "\n";
                content += "Text: " + postResponse.getText() + "\n";
                txtRes.setText(content);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t)
            {
                txtRes.setText(t.getMessage());
            }
        });
    }

    private void updatePost()
    {
        scroll.setVisibility(View.VISIBLE);
        txtRes.setVisibility(View.VISIBLE);

        /*Map<String, String> map = new HashMap<>();
        map.put("header1", "def");
        map.put("header2", "def2");*/

        Post post = new Post(12, null, "new text");

        Call<Post> call = api.putPost("Test Header", 5, post);

        call.enqueue(new Callback<Post>()
        {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response)
            {
                if (!response.isSuccessful())
                {
                    txtRes.setText("Code: " + response.code());
                    return;
                }

                Post postResponse = response.body();

                String content = "";
                content += "Code: " + response.code() + "\n";
                content += "ID: " + postResponse.getId() + "\n";
                content += "User Id: " + postResponse.getUserId() + "\n";
                content += "Title: " + postResponse.getTitle() + "\n";
                content += "Text: " + postResponse.getText() + "\n";
                txtRes.setText(content);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t)
            {
                txtRes.setText(t.getMessage());
            }
        });
    }

    private void deletePost()
    {
        scroll.setVisibility(View.VISIBLE);
        txtRes.setVisibility(View.VISIBLE);

        Call<Void> call = api.deletePost(5);

        call.enqueue(new Callback<Void>()
        {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response)
            {
                txtRes.setText("Code: " + response.code());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t)
            {
                txtRes.setText(t.getMessage());
            }
        });
    }
}
