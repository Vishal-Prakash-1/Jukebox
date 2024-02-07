package org.example;
import org.example.DBConnection;

import java.sql.*;
import java.util.Scanner;

public class User{

    Scanner scanner = new Scanner(System.in);
    Connection connection;

    public void login()
    {
        String presentName = null;
        String presentUserId= null;
        String presentPassword= null;
        boolean flag = false;
        while (flag != true) {
            System.out.println("Enter yours userId");
            presentUserId = scanner.next();
            //setUserId(presentUserId);
            System.out.println(("Enter your Password"));
            presentPassword = scanner.next();
            //setUserPassword(presentPassword);
            try {
                connection = DBConnection.getConnection();
                Statement statement = connection.createStatement();
                String str = "select * from userlist";
                ResultSet resultSet = statement.executeQuery(str);

                while (resultSet.next()) {
                    if (presentUserId.equals(resultSet.getString(2))){
                        if (presentPassword.equals(resultSet.getString(3))){
                            presentName=resultSet.getString(1);
                            flag = true;
                            break;
                        }
                    }
                }
                if (flag == false) {
                    System.out.println("Entered UserId and Password is wrong, Please try again");
                    System.out.println("Press 1 to try login.");
                    System.out.println("Press 2 to create new account.");
                    System.out.println("Press 3 to exit.");
                    int c = scanner.nextInt();
                    if (c == 2) {
                        createAccount();
                        break;
                    } else if (c==3) {
                        exitOption();
                        break;
                    }
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        //Jukebox.existingUserOptions(getUserId(), getUserPassword());
        Jukebox.existingUserOptions(presentName,presentUserId,presentPassword);
    }


        public void createAccount() {
            System.out.println("Enter Your Name");
            String newName = scanner.next();
            String newId = null;
            String confirmedNewUserId = null;

            boolean flag = true;
            while (flag != false) {
                System.out.println("Enter unique UserId for account");
                newId = scanner.next();
                try {
                    boolean checkId = checkUserId(newId);
                    System.out.println(checkId);
                    if (checkId == false) {
                        confirmedNewUserId = newId;
                        flag = false;
                        break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //Password
            String newPassword = null;
            boolean passwordFlag = true;
            while (passwordFlag != false) {
                System.out.println("Password must contain Alphabets and numbers combination.\nPassword length should be in between 6 to 10 letters.");
                System.out.println("Enter the password for your account: ");
                newPassword = scanner.nextLine();
                boolean b1 = newPassword.matches("[a-zA-Z0-9]{6,10}");
                if (b1) {
                    passwordFlag = false;
                }
            }
            addUser(newName, confirmedNewUserId, newPassword);
            //System.out.println("Last line of createAccount method");
        }

    public boolean checkUserId(String userId) {
        boolean t = false;
        try {
            connection = DBConnection.getConnection();
            Statement statement = connection.createStatement();
            String str = "select * from userlist";
            ResultSet resultSet = statement.executeQuery(str);
            while (resultSet.next()) {
                String checkUserId = resultSet.getString(2);
                //System.out.println(checkUserId);
                if (checkUserId.equals(userId)) {
                    t = true;
                    break;
                }
                else
                {
                    t= false;
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return t;
    }

    private void addUser(String newName, String confirmedNewUserId, String newPassword)
    {
        try {
            connection = DBConnection.getConnection();
            String str = "insert into userlist (username, userid, userpassword)"+"values(?,?,?)";
            PreparedStatement prs = connection.prepareStatement(str);
            prs.setString(1,newName);
            prs.setString(2,confirmedNewUserId);
            prs.setString(3,newPassword);
            prs.execute();
            connection.close();
            System.out.println("user added.");
        }catch (SQLException e){
            e.printStackTrace();
        }
        System.out.println(newName);
        System.out.println(confirmedNewUserId);
        System.out.println(newPassword);
        System.out.println("Pres 1. To Login");
        System.out.println("Press 2. To Exit");

        int c = scanner.nextInt();
        switch (c){
            case 1: login();
            break;
            case 2: exitOption();
            break;
        }
    }

    public void exitOption()
    {
        System.out.println("Sayonara...");
    }
    public void exitOption(String username)
    {
        System.out.println("Bye "+username);
        System.out.println("Jukebox :) entertainment on click!");
    }
}
