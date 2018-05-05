package com.example.owner.unit10_participation;

import java.util.List;
import java.util.Random;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

/**
 * @author Amelia Schumacher
 * @version 5/4/18
 *
 *
 */
public class MainActivity extends ListActivity {
    private CommentsDataSource datasource;

    /**
     * onCreate()
     * This method goes through and sets up the view and creates a connection with the datasource
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        datasource = new CommentsDataSource(this);
        datasource.open();

        List<Comment> values = datasource.getAllComments();

        // use the SimpleCursorAdapter to show the
        // elements in a ListView
        ArrayAdapter<Comment> adapter = new ArrayAdapter<Comment>(this,
                android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);
    }

    /**
     * onClick()
     * Will be called via the onClick attribute of the buttons in activity_main.xml
     * @param view
     */
    public void onClick(View view) {
        @SuppressWarnings("unchecked")
        ArrayAdapter<Comment> adapter = (ArrayAdapter<Comment>) getListAdapter();
        Comment comment = null;
        //Switch case for the buttons
        switch (view.getId()) {
            //A comment is added after the button is clicked
            case R.id.add:
                String[] comments = new String[] { "Cool", "Very nice", "Hate it" };
                int nextInt = new Random().nextInt(3);
                // save the new comment to the database
                comment = datasource.createComment(comments[nextInt]);
                adapter.add(comment);
                break;
            //Delete the first comment when the button is clicked
            case R.id.delete:
                if (getListAdapter().getCount() > 0) {
                    comment = (Comment) getListAdapter().getItem(0);
                    datasource.deleteComment(comment);
                    adapter.remove(comment);
                }
                break;
        }
        adapter.notifyDataSetChanged();
    }

    /**
     * onResume()
     *
     * This calls the activity display method from the superclass
     */
    @Override
    protected void onResume() {
        datasource.open();
        super.onResume();
    }

    /**
     * onPause()
     *
     * This calls the superclass method to keep the activity in a persistent state during editing
     */
    @Override
    protected void onPause() {
        datasource.close();
        super.onPause();
    }

}
