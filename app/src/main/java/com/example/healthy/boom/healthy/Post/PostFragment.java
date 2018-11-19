package com.example.healthy.boom.healthy.Post;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.healthy.boom.healthy.MenuFragment;
import com.example.healthy.boom.healthy.R;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class PostFragment extends Fragment {

    private String result;
    private JSONArray jsonArray;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_post, container, false);
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
                    Request request = new Request.Builder().url("https://jsonplaceholder.typicode.com/posts").build();
                    Response response = client.newCall(request).execute();
                    result = response.body().string();
                    jsonArray = new JSONArray(result);
                }
                catch (IOException e)
                {
                    Log.d("Post", "IOException: " + e.getMessage());
                }
                catch (JSONException e)
                {
                    Log.d("Post", "JSONException: " + e.getMessage());
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                try
                {
                    final ArrayList<JSONObject> postList = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        postList.add(obj);
                    }

                    ProgressBar progressBar = getView().findViewById(R.id.fragment_post_progress_bar);
                    progressBar.setVisibility(View.GONE);

                    ListView postListView = getView().findViewById(R.id.fragment_post_list);
                    PostAdapter postAdapter = new PostAdapter(getContext(), R.layout.fragment_post_item, postList);
                    postListView.setAdapter(postAdapter);

                    postListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Bundle bundle = new Bundle();

                            try{
                                bundle.putInt("postId", postList.get(position).getInt("id"));
                            }
                            catch (JSONException e)
                            {
                                Log.d("Post", "JSONException: " + e.getMessage());
                            }

                            Fragment fragment = new CommentFragment();
                            fragment.setArguments(bundle);
                            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                            ft.replace(R.id.main_view, fragment).addToBackStack(null).commit();
                        }
                    });
                }
                catch (JSONException e)
                {
                    Log.d("Post", "JSONException: " + e.getMessage());
                }
            }
        };
        task.execute();
    }

    private void initBackBtn() {
        Button backBtn = getView().findViewById(R.id.fragment_post_back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new MenuFragment())
                        .addToBackStack(null)
                        .commit()
                ;
            }
        });
    }
}
