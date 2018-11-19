package com.example.healthy.boom.healthy.Post;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.healthy.boom.healthy.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PostAdapter extends ArrayAdapter {
    private ArrayList<JSONObject> postList;
    private Context context;

    public PostAdapter(@NonNull Context context,
                       int resource,
                       @NonNull ArrayList<JSONObject> objects) {
        super(context, resource, objects);
        this.postList = objects;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position,
                        @Nullable View convertView,
                        @NonNull ViewGroup parent) {

        View postItem = LayoutInflater.from(context).inflate(R.layout.fragment_post_item, parent, false);

        JSONObject postObj = postList.get(position);

        TextView idText = postItem.findViewById(R.id.fragment_post_item_id);
        TextView titleText = postItem.findViewById(R.id.fragment_post_item_title);
        TextView bodyText  = postItem.findViewById(R.id.fragment_post_item_body);
        try
        {
            idText.setText(postObj.getString("id"));
            titleText.setText(postObj.getString("title"));
            bodyText.setText(postObj.getString("body"));
        }
        catch (JSONException e)
        {
            Log.d("PostAdapter", "JSONException: " + e.getMessage());
        }
        return postItem;
    }
}
