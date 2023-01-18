package com.example.demo.DOA;

import com.example.demo.model.Comment;
import com.example.demo.model.Post;
import com.example.demo.model.User;
import com.example.demo.utilities.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PostDatabase {
    private   Connection dbConnection;

    public   PostDatabase(Connection connection) {
        this.dbConnection = connection;
    }

    /**
     * This method creates a post
     * @param userId the id of a user that is posting
     * @param post the post of the use
     * @return
     */
    public boolean createPost(int userId, Post post){
        boolean result = false;
        try{
            String query = "insert into posts(title,body,image_name,user_id) " +
                    "values (?,?,?,?)";

            PreparedStatement preparedStatement = this.dbConnection.prepareStatement(query);
            preparedStatement.setString(1, post.getTitle());
            preparedStatement.setString(2, post.getBody());
            preparedStatement.setString(3, post.getImageName());
            preparedStatement.setInt(4, userId);

            preparedStatement.executeUpdate();
            result = true;
        }catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }

    /**
     * Method returns lists of all posts
     * @return list
     * */
    public  List<Post> getPosts(User currentUser){
        Connection con = ConnectionManager.getConnection();
        List<Post> posts = new ArrayList<>();
        try{
            String query = "select p.id, p.title, p.body, p.image_name, u.lastname, u.email from posts p"
                    +"  join user u on p.user_id=u.id order by p.id DESC";

            PreparedStatement preparedStatement = con.prepareStatement(query);
            ResultSet result = preparedStatement.executeQuery();
            //System.out.println(result);
            Post post = null;
            while(result.next()){
                post = new Post();
                post.setId(result.getInt("id"));
                post.setTitle(result.getString("title"));
                post.setBody(result.getString("body"));
                post.setImageName(result.getString("image_name"));
                post.setName(result.getString("lastname"));
                post.setEmail(result.getString("email"));


                String que = "select * from likes where post_id="+post.getId();
                PreparedStatement prepared = this.dbConnection.prepareStatement(que);
                ResultSet res = prepared.executeQuery();
                res.last();
                int noOfLikes = res.getRow();
                post.setNoLikes(noOfLikes);

                //comment counts
                String queri1 = "select * from comment where post_id="+post.getId();
                PreparedStatement prepared1 = this.dbConnection.prepareStatement(queri1);
                ResultSet res1 = prepared1.executeQuery();
                res1.last();
                int noOfComments = res1.getRow();
                post.setNoComments(noOfComments);


                //liked post
                String queri2 = "select * from likes where post_id="+post.getId()+" and user_id="+currentUser.getId();
                PreparedStatement prepared2 = this.dbConnection.prepareStatement(queri2);
                ResultSet res2 = prepared2.executeQuery();
                if(res2.next()) {
                    post.setLikedPost(true);
                }

                posts.add(post);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return posts;
    }

    /**
     * Method returns a post by id
     * @return list
     * */
    public Post getPostById(int postId){
        Post post = null;

        try{
            String query = "select p.id, p.title, p.body, u.email from posts p"
                    +"  join user u on p.user_id=u.id where p.id=?";

            PreparedStatement preparedStatement = this.dbConnection.prepareStatement(query);
            preparedStatement.setInt(1,postId);
            ResultSet result = preparedStatement.executeQuery();

            if(result.next()){
                post = new Post();

                post.setId(result.getInt("id"));
                post.setTitle(result.getString("title"));
                post.setBody(result.getString("body"));
                post.setEmail(result.getString("email"));

                return post;
            }
        }catch (Exception e){
        }

        return post;
    }

    /**
     * This method update a post
     * @param postId
     * @param newPost
     * @return
     */
    public boolean editPost(int postId, Post newPost){
        boolean success = false;

        try {
            //String query = "update posts set title=newPost.getTitle(), body=newPost.getBody() where id=posjjtId";
            String query = "update posts set title=?, body=? where id=?";
            PreparedStatement prepared = this.dbConnection.prepareStatement(query);

            prepared.setString(1, newPost.getTitle());
            prepared.setString(2, newPost.getBody());
            prepared.setInt(3, postId);

            int result = prepared.executeUpdate();

            if(result > 0) {
                success = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }

    /**
     * This method delete a post made by the user identified by the post_id
     * @param postId
     * @param userid
     * @return
     */
    public static boolean deletePost(int userid, int postId){
        Connection con = ConnectionManager.getConnection();
        boolean success =  false;
        try {
//            String query = "delete from posts where id="+postId ;
            String query = "DELETE FROM posts WHERE id=? AND user_id=?";
            PreparedStatement prepared = con.prepareStatement(query);
            prepared.setInt(1,postId);
            prepared.setInt(2,userid);
            int result = prepared.executeUpdate();

            if(result > 0) {
                success = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return success;
    }


    /**
     * This methods allows users to make comment
     * @param userId
     * @param postId
     * @param comment
     * @return
     */
    public boolean makeComment(int userId, int postId, String comment){
        boolean result = false;
        try{
            String query = "insert into comment(post_id,user_id,comment) " +
                    "values (?,?,?)";

            PreparedStatement preparedStatement = this.dbConnection.prepareStatement(query);
            preparedStatement.setInt(1, postId);
            preparedStatement.setInt(2, userId);
            preparedStatement.setString(3, comment);

            preparedStatement.executeUpdate();
            result = true;
        }catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }

    /**
     * This method loads all comment
     * @param postId
     * @return
     */
    public List<Comment> getAllComments(int postId){
        List<Comment> comments = new ArrayList();
        try{
            Comment comment = null;
            String query = "select u.lastName, p.title, p.image_name, c.comment, u.id from comment c"
                    +"  join posts p on c.post_id=p.id join user u on u.id=c.user_id" +
                    " where post_id="+postId;

            PreparedStatement preparedStatement = this.dbConnection.prepareStatement(query);
            ResultSet resultSet =  preparedStatement.executeQuery();

            while (resultSet.next()){
                comment = new Comment();
                comment.setUsername(resultSet.getString("lastName"));
                comment.setUserId(resultSet.getInt("id"));
                comment.setTitle(resultSet.getString("title"));
                comment.setPostImage(resultSet.getString("image_name"));
                comment.setComment(resultSet.getString("comment"));
                comments.add(comment);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return comments;
    }

    public boolean likePost(int userId, int postId, int action){
        boolean success = false;
        try{
            String query = "";
            PreparedStatement preparedStatement = null;

            if(action == 1){
                query = "insert into likes(post_id,user_id) " +
                        "values (?,?)";

                preparedStatement = this.dbConnection.prepareStatement(query);
                preparedStatement.setInt(1, postId);
                preparedStatement.setInt(2, userId);

                preparedStatement.executeUpdate();
                success = true;
            }else{
                query = "delete from likes where user_id="+userId+" and post_id="+postId;
                preparedStatement = this.dbConnection.prepareStatement(query);
                int result = preparedStatement.executeUpdate();

                if(result > 0) {
                    success = true;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return success;
    }

    public boolean editComment(int userId, int postId, String comment){
        boolean status = false;
        try {
            String query = "update comment set comment=? where post_id=? and user_id=?";
            PreparedStatement prepared = this.dbConnection.prepareStatement(query);
            prepared.setString(1, comment);
            prepared.setInt(2, postId);
            prepared.setInt(3,userId);

            int result = prepared.executeUpdate();
            if(result > 0) {
                status = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    public boolean deleteComment(int postId, int userId){
        boolean status =  false;

        try {
            String query = "delete from comment where post_id=? and user_id=?";
            PreparedStatement preparedStatement = this.dbConnection.prepareStatement(query);
            preparedStatement.setInt(1, postId);
            preparedStatement.setInt(2,userId);

            int result = preparedStatement.executeUpdate();
            if(result > 0) {
                status = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }


}