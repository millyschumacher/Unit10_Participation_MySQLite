package com.example.owner.unit10_participation;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * CommentsDataSource()
 *
 * This class is responsible for handling the data actions with the comments
 */
public class CommentsDataSource {

    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_COMMENT };

    /**
     * CommentsDataSource()
     *
     * This method creates a helper variable called from the helper class
     * @param context
     */
    public CommentsDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    /**
     * open()
     *
     * The method throws an exception if the SQL doesn't work
     * @throws SQLException
     */
    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    /**
     * close()
     *
     * This method closes the connection to the helper
     */
    public void close() {
        dbHelper.close();
    }

    /**
     * createComment()
     *
     * The method goes through the task of creating and inserting the comments into the database
     * SQL:
     * INSERT INTO TABLE_COMMENTS(comments)
     * VALUES('yo');
     *
     * @param comment
     * @return
     */
    public Comment createComment(String comment) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_COMMENT, comment);
        long insertId = database.insert(MySQLiteHelper.TABLE_COMMENTS, null,
                values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_COMMENTS,
                allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Comment newComment = cursorToComment(cursor);
        cursor.close();
        return newComment;
    }

    /**
     * deleteComment()
     *
     * SQL:
     * DELETE FROM TABLE_COMMENTS(comments)
     * WHERE id=3;
     *
     * The method deletes a comment from the database
     * @param comment
     */
    public void deleteComment(Comment comment) {
        long id = comment.getId();
        System.out.println("Comment deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_COMMENTS, MySQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }

    /**
     * getAllComments()
     *
     * SQL:
     * SELECT * FROM TABLE_COMMENTS;
     *
     * The method gets all of the comments in the database
     * @return
     */
    public List<Comment> getAllComments() {
        List<Comment> comments = new ArrayList<Comment>();

        //Gets all of the comments from the table
        Cursor cursor = database.query(MySQLiteHelper.TABLE_COMMENTS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        //A while loop is used to go through a potentially unknown number of comments
        //As long as the cursor isn't the last column, this will go through
        while (!cursor.isAfterLast()) {
            Comment comment = cursorToComment(cursor);
            comments.add(comment);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return comments;
    }

    /**
     * cursorToComment()
     *
     * This method determines where to put the next comment
     * @param cursor
     * @return
     */
    private Comment cursorToComment(Cursor cursor) {
        Comment comment = new Comment();
        comment.setId(cursor.getLong(cursor.getColumnIndex(MySQLiteHelper.COLUMN_ID)));
        comment.setComment(cursor.getString(cursor.getColumnIndex(MySQLiteHelper.COLUMN_COMMENT)));
        return comment;
    }
}
