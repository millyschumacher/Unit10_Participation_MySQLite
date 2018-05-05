package com.example.owner.unit10_participation;

/**
 * This class handles the accessing of the comments
 */
public class Comment {
    private long id;
    private String comment;

    /**
     * Comment()
     *
     * A constructor for the Comment() class
     */
    public Comment() {
        id=this.getId();
        comment=this.getComment();
    }

    /**
     * getId()
     *
     * Gets and returns the ID of the comment
     * @return
     */
    public long getId() {
        return id;
    }

    /**
     * setId()
     *
     * Sets the id of the comment
     * @param id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * getComment()
     *
     * This gets and returns the value of comment
     * @return
     */
    public String getComment() {
        return comment;
    }

    /**
     * setComment()
     *
     * This sets the value of the comment
     * @param comment
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * toString()
     *
     * This prints out a string of the comment's value
     * @return
     */
    @Override
    public String toString() {
        return comment;
    }
}
