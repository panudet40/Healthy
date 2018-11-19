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
import java.util.List;

public class CommentAdapter extends ArrayAdapter {
    private ArrayList<JSONObject> commentList;
    private Context context;

    public CommentAdapter(@NonNull Context context,
                          int resource,
                          @NonNull ArrayList<JSONObject> objects) {
        super(context, resource, objects);
        this.commentList = objects;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position,
                        @Nullable View convertView,
                        @NonNull ViewGroup parent) {

        View commentItem = LayoutInflater.from(context).inflate(R.layout.fragment_comment_item, parent, false);

        JSONObject commentObj = commentList.get(position);

        TextView commentPostID = commentItem.findViewById(R.id.fragment_comment_item_post_id);
        TextView commentID = commentItem.findViewById(R.id.fragment_comment_item_id);
        TextView commentBody  = commentItem.findViewById(R.id.fragment_comment_item_body);
        TextView commentName = commentItem.findViewById(R.id.fragment_comment_item_name);
        TextView commentEmail  = commentItem.findViewById(R.id.fragment_comment_item_email);

        try
        {
            commentPostID.setText(commentObj.getString("postId"));
            commentID.setText(commentObj.getString("id"));
            commentBody.setText(commentObj.getString("body"));
            commentName.setText("Name: " + commentObj.getString("name"));
            commentEmail.setText("Email: " + commentObj.getString("email"));
        }
        catch (JSONException e)
        {
            Log.d("CommentAdapter", "JSONException: " + e.getMessage());
        }
        return commentItem;
    }
}
