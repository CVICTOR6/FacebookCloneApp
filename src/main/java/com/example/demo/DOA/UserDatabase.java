package com.example.demo.DOA;


import com.example.demo.model.User;
import com.example.demo.utilities.ConnectionManager;
import com.example.demo.utilities.Encryption;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDatabase {
    private Connection con;

    public UserDatabase(Connection con) {
        this.con = con;
    }
    //Registration

    /**
     * Allows users to register
     * @param user
     * @return
     */
    public boolean registerUser(User user){
        boolean success = false;
        String query = "INSERT INTO users(firstname, lastname,password, email, dob, gender) VALUES(?,?,?,?,?,?)";

        try {
            PreparedStatement preparedStatement = this.con.prepareStatement(query);
            preparedStatement.setString(1, user.getFirstname());
            preparedStatement.setString(2, user.getLastname());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5,user.getDob());
            preparedStatement.setString(6,user.getGender());
            preparedStatement.executeUpdate();
            success = true;
//
////            if(loadUser(user.getEmail())!=null)
////            {getEmail
//                success = false;
//        //    }
//            else{
//
//
//            }

            preparedStatement.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return success;

    }

    /**
     * This method allows user to login
     * @param data
     * @param password
     * @return
     */
    public User login(String data, String password){
        User user = null;
        try {
           String query = "select * from users where email=?";
           Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = this.con.prepareStatement(query);
            preparedStatement.setString(1, data);
            ResultSet result = preparedStatement.executeQuery();
            if(result.next()){
                user = new User();
                user.setId(result.getInt("id"));
                user.setFirstname(result.getString("firstName"));
                user.setLastname(result.getString("lastName"));
                user.setPassword(result.getString("password"));
                user.setEmail(result.getString("email"));
                user.setDob(result.getString("dob"));
                user.setGender(result.getString("gender"));


                //decrypting password
                String decryptPass = Encryption.decryptPassword(result.getString("password"));
                System.out.print("Decr: "+decryptPass);
                System.out.print("password "+password);
                if(!decryptPass.equals(password)){
                    return null;
                }
            }
        }catch(Exception e){
        }
        return user;
    }


    public User loadUser(String userEmail)
    {
        List<User> userList = new ArrayList<>();
        User user=null;

        try {
             user =  new User();
            String query = "select * from users where email=email";
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            //preparedStatement.setString(1, data);
            ResultSet result = preparedStatement.executeQuery();

           while(result.next())
            {
                user.setFirstname(result.getString("firstName"));
                user.setLastname(result.getString("lastName"));
                user.setPassword(result.getString("password"));
                user.setEmail(result.getString("email"));
                user.setDob(result.getString("dob"));
                user.setGender(result.getString("gender"));
                user.setId(result.getInt("id"));
                user.setPassword(result.getString("password"));
                if(user.getEmail().equals(userEmail)){

                    break;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return user;
    }

    public List<User> getUsers(){
        User user = null;
        List users = new ArrayList();

        try {
            String query = "select * from users";
            PreparedStatement preparedStatement = this.con.prepareStatement(query);

            ResultSet result = preparedStatement.executeQuery();
            while(result.next()){
                user = new User();

                user.setId(result.getInt("id"));
                user.setFirstname(result.getString("firstName"));
                user.setLastname(result.getString("lastName"));
                user.setPassword(result.getString("password"));
                user.setEmail(result.getString("email"));

                users.add(user);
            }
            return users;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return users;
    }




//    public static void main(String[] args) {
//        System.out.println(getUserById(1));
//
//    }

}

