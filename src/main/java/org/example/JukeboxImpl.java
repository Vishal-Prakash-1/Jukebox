package org.example;

import org.example.DBConnection;
import java.sql.*;
import java.sql.Connection;
import java.util.Scanner;

public class JukeboxImpl {
    public static void intialstatement()
    {
        System.out.println("  Welcome to JukeBox  ");
        System.out.println("======================");
    }
    static User user = new User();
    public static void main(String[] args) {
        JukeboxImpl.intialstatement();
        Scanner scanner = new Scanner(System.in);
        int Choice = 0;
        while ((Choice!=1)&&(Choice!=2)&&(Choice!=3))
        {
            System.out.println("Press 1: For Creating New Account");
            System.out.println("Press 2: For Login");
            System.out.println("Press 3: To Exit");
            Choice=scanner.nextInt();

            switch (Choice)
            {
                case 1: user.createAccount();
                    break;
                case 2: user.login();
                    break;
                case 3: user.exitOption();
                    break;
                default:
                    System.out.println("Invalid Option");
                    break;
            }
        }
    }
}
