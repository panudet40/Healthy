package com.example.healthy.boom.healthy.Post;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.healthy.boom.healthy.R;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class CommentFragment extends Fragment {

    private int postId;
    private String result;
    private JSONArray jsonArray;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        postId = bundle.getInt("postId");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_comment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initBackBtn();
        initRestAPI();

    }

    private void initRestAPI() {
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                OkHttpClient client = new OkHttpClient();

                try {
                    String url = "https://jsonplaceholder.typicode.com/posts/" + postId + "/comments";
                    Request request = new Request.Builder().url(url).build();

                    Response response = client.newCall(request).execute();
                    result = response.body().string();
                    jsonArray = new JSONArray(result);
                }
                catch (IOException e)
                {
                    Log.d("Comment", "IOException: " + e.getMessage());
                }
                catch (JSONException e)
                {
                    Log.d("Comment", "JSONException: " + e.getMessage());
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                try
                {
                    final ArrayList<JSONObject> commentList = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        commentList.add(obj);
                    }

                    ProgressBar progressBar = getView().findViewById(R.id.fragment_comment_progress_bar);
                    progressBar.setVisibility(View.GONE);

                    ListView commentListView = getView().findViewById(R.id.fragment_comment_list);
                    CommentAdapter commentAdapter = new CommentAdapter(getContext(), R.layout.fragment_comment_item, commentList);
                    commentListView.setAdapter(commentAdapter);
                }
                catch (JSONException e)
                {
                    Log.d("Comment", "JSONException: " + e.getMessage());
                }
            }
        };
        task.execute();
    }

    private void initBackBtn() {
        Button backBtn = getView().findViewById(R.id.fragment_comment_back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new PostFragment())
                        .addToBackStack(null)
                        .commit()
                ;
            }
        });
    }
}
