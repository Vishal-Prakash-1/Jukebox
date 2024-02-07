package org.example;
import org.example.DBConnection;
import org.example.Song;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.sql.ResultSetMetaData;
public class Jukebox {
    static Jukebox jukebox = new Jukebox();
    static User user = new User();
    static Play play = new Play();
    static Scanner scanner = new Scanner(System.in);
    Connection connection;


    public static void existingUserOptions(String userName, String userId, String userPassword)
    {

        System.out.println("Hi "+userName+"\nWelcome to Jukebox");
        //System.out.println(userId);
        System.out.println("Press 1 to show all songs.");
        System.out.println("Press 2 to show yours playlist.");
        System.out.println("Press 3 to create new Playlist.");
        int choice = scanner.nextInt();
        switch (choice){
            case 1: jukebox.printSongsListTable(userName, userId, userPassword);
            break;
            case 2: jukebox.showYourPlaylists(userId,userName);
            break;
            case 3: jukebox.createPlaylist(userId, userName);
            break;
        }
        // select playlist to play song
    }
    public void printSongsListTable(String userName, String userId, String userPassword)
    {
        try {
            connection = DBConnection.getConnection();
            Statement st = connection.createStatement();
            String str = "select * from songslist";
            ResultSet rs = st.executeQuery(str);

            ResultSetMetaData rsmd = rs.getMetaData();
            int column = rsmd.getColumnCount();
            System.out.print("Sr.No.|");
            for (int i=1; i<=column;i++){
                System.out.print(rsmd.getColumnName(i)+" | ");
            }
            System.out.println();
            System.out.println("--------------------------------------------------------------------------------------------------");

            int z = 1;
            while (rs.next())
            {
                System.out.println("  "+z+"   "+rs.getString(1)+" | "+rs.getString(2)+" | "+rs.getString(3)+" | "+rs.getString(4)+" | "+rs.getString(5)+" | "+rs.getString(6));
                z++;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        String songslist = "songslist";
        System.out.println("Press 1 to play all songs.");
        System.out.println("Press 2 to go existing user options.");
        int z = scanner.nextInt();
        switch (z){
            case 1: playSongsFromPlaylist(songslist,userId,userName);
            break;
            case 2: existingUserOptions(userName, userId, userPassword);
            break;
        }
    }
    List<Song> allSongsList = new ArrayList<>();
    public List<Song> getAllSongs()
    {
        connection = DBConnection.getConnection();
        try {
            connection = DBConnection.getConnection();
            PreparedStatement prs = connection.prepareStatement("select * from songslist");
            ResultSet rs = prs.executeQuery();
            while (rs.next()){
                allSongsList.add(new Song(rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6)));
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }

        return allSongsList;
    }


    public void createPlaylist(String userId, String userName)
    {
        System.out.println("Enter your playlist name, Note: No gap in names");
        String newPlaylistName = scanner.next();
        String newPlaylistId = null;
        String confirmedNewPlaylistId = null;
        boolean flag = true;
        while (flag != false) {
            System.out.println("Enter unique PlaylistId for account");
            newPlaylistId = scanner.next();
            System.out.println(newPlaylistId);
            try {
                boolean checkPLId = checkPlaylistId(newPlaylistId);
                System.out.println(checkPLId);
                if (checkPLId == false) {
                    confirmedNewPlaylistId = newPlaylistId;
                    flag = false;
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //System.out.println(newPlaylistName+" Playlist is created with playlist id "+confirmedNewPlaylistId+" for user "+userName);
        // Creating playlist table to Store songs using playlistId
        // songid is already set by us so no need to check duplicate songid
        try {
            Statement statement = connection.createStatement();
            String str = "Create table "+confirmedNewPlaylistId+"(songid varchar(5),foreign key(songid) references songslist(songid), songname varchar(30), genre varchar(20), artist varchar (20), duration varchar(6), songpath varchar (1000))";
            int rs1 = statement.executeUpdate(str);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //Inserting created playlist details in table usersplaylists table
        insertPlaylistInUsersPlaylistsTable(confirmedNewPlaylistId,newPlaylistName,userId,userName);//usersplaylists

    }


    public boolean checkPlaylistId(String newPlaylistId) {
        boolean t = false;
        try {
            connection = DBConnection.getConnection();
            Statement statement = connection.createStatement();
            String str = "select * from usersplaylists";
            ResultSet resultSet = statement.executeQuery(str);
            while (resultSet.next()) {
                String checkPlaylistId = resultSet.getString(1);
                //System.out.println(checkPlaylistId);
                if (checkPlaylistId.equals(newPlaylistId)) {
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

    // naming: from createPlaylistInDB, we change confirmedNewPlaylistId to playlistId;
    private void insertPlaylistInUsersPlaylistsTable(String PlaylistId, String PlaylistName, String userId, String userName) //usersplaylists
    {
        System.out.println("Inserting new created playlist in usersplaylist table.");
        try {
            connection = DBConnection.getConnection();
            String str = "insert into usersplaylists (playlistid, playlistname, userid)"+"values(?,?,?)";
            PreparedStatement prs = connection.prepareStatement(str);
            prs.setString(1,PlaylistId);
            prs.setString(2,PlaylistName);
            prs.setString(3,userId);
            prs.execute();
            connection.close();
            System.out.println(PlaylistName+" is added in "+userName+" account.");
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        System.out.println("Kindly add the songs in playlist.");
        addSongInPlaylist(PlaylistId,PlaylistName,userId,userName);
    }

    public void addSongInPlaylist(String confirmedNewPlaylistId, String newPlaylistName, String userId,String userName) {
        System.out.println(userName+" your Playlist is created with name "+newPlaylistName);
        System.out.println("Adding songs in playlist table "+confirmedNewPlaylistId);
        Statement statement;
        ResultSet rs;
        String str ="";
        try {
            int i = 0;
            while ((i!=1)&&(i!=2)&&(i!=3)){
                System.out.println("Select options from where you have to select songs");
                System.out.println("1. All songs list\n2. Genre\n3. Artist");
                int option = scanner.nextInt();
                int q =0;
                switch (option){
                    case 1:{
                        String chooseSongId = "-1";
                        while (!chooseSongId.equals("0")) {
                            connection = DBConnection.getConnection();
                            statement = connection.createStatement();
                            str = "select * from songslist";
                            rs = statement.executeQuery(str);
                            while (rs.next()) {
                                System.out.println(rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4) + " " + rs.getString(5) + " " + rs.getString(6));
                            }
                            System.out.println("Choose songId for song to be inserted in playlist and press 0 if you are done.");
                            chooseSongId = scanner.next();
                            if (!chooseSongId.equals("0")){
                                statement = connection.createStatement();
                                //str = "select * from songslist where songid = '"+chooseSongId+"'";
                                //System.out.println(str);
                                String str1 = "select * from songslist where songid = '"+chooseSongId+"'";
                                System.out.println(str1);
                                rs = statement.executeQuery(str1);
                                while (rs.next())
                                {
                                    String songid = rs.getString(1);
                                    String songname = rs.getString(2);
                                    String genre = rs.getString(3);
                                    String artist = rs.getString(4);
                                    String duration = rs.getString(5);
                                    String songpath = rs.getString(6);

                                    statement = connection.createStatement();
                                    str = "insert into "+confirmedNewPlaylistId+" values('"+songid+"','"+songname+"','"+genre+"','"+artist+"','"+duration+"','"+songpath+"')";
                                    //int rs1 = statement.executeUpdate(str);
                                    statement.executeUpdate(str);
                                }
                            }

                        }if (chooseSongId.equals("0")){
                            System.out.println("Press 1. to see yours playlists");
                            System.out.println("Press 2. to create another playlists");
                            System.out.println("Press 3. to exit jukebox");
                            int choice1 = scanner.nextInt();
                            if (choice1==1){
                                showYourPlaylists(userId, userName);
                            } else if (choice1 == 2) {
                                createPlaylist(userId,userName);
                            }
                            else {
                                user.exitOption(userName);
                            }
                        }
                        i = 1;
                    }
                    break;

                    case 2:{
                        System.out.println("Select songs on basis of genre");
                        int z=0;
                        int y=0;
                        while ((z!=1)&&(z!=2)&&(z!=3)){
                            System.out.println("Press 1 for pop songs\nPress 2 for rock songs");
                            y = scanner.nextInt();
                            z = y;
                        }
                        //String choice2 = "-1";
                        String chooseSongId = "-1";
                        while (!chooseSongId.equals("0")){
                            //connection = DBConnection.getConnection();
                            if (y == 1){
                                str = "select * from songslist where genre = 'pop'";
                            } else if (y==2) {
                                str = "select * from songslist where genre = 'rock'";
                            }
                            connection = DBConnection.getConnection();
                            statement = connection.createStatement();
                            rs = statement.executeQuery(str);
                            while (rs.next()){
                                System.out.println(rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4) + " " + rs.getString(5) + " " + rs.getString(6));
                            }
                            //System.out.println("");
                            System.out.println("Choose songId for song to be inserted in playlist and press 0 if you are done.");
                            chooseSongId = scanner.next();
                            System.out.println("choosesongid="+chooseSongId);

                            if (!chooseSongId.equals("0")){
                                String str1 = "select * from songslist where songid = '"+chooseSongId+"'";
                                System.out.println(str1);
                                rs = statement.executeQuery(str1);
                                while (rs.next())
                                {
                                    String songid = rs.getString(1);
                                    String songname = rs.getString(2);
                                    String genre = rs.getString(3);
                                    String artist = rs.getString(4);
                                    String duration = rs.getString(5);
                                    String songpath = rs.getString(6);

                                    statement = connection.createStatement();
                                    str = "insert into "+confirmedNewPlaylistId+" values('"+songid+"','"+songname+"','"+genre+"','"+artist+"','"+duration+"','"+songpath+"')";
                                    //int rs1 = statement.executeUpdate(str);
                                    statement.executeUpdate(str);
                                }
                            }
                        }
                        if (chooseSongId.equals("0")){
                            System.out.println("Press 1. to see yours playlists");
                            System.out.println("Press 2. to create another playlists");
                            System.out.println("Press 3. to exit jukebox");
                            int choice1 = scanner.nextInt();
                            if (choice1==1){
                                showYourPlaylists(userId, userName);
                            }
                            else if (choice1 == 2) {
                                createPlaylist(userId,userName);
                            }
                            else {
                                user.exitOption(userName);
                            }
                        }
                        i = 1;
                    }break;
                    case 3:{
                        System.out.println("Select songs on basis of artist");
                        String chooseSongId = "-1";
                        while (!chooseSongId.equals("0")){
                            System.out.println("Press 1 Ed Sheeran, Press 2 for Queen");
                            System.out.println("Press 0 if you are done.");
                            String  y = scanner.next();
                            if (y.equals("0"))
                            {
                                chooseSongId = "0";
                                break;
                            }
                            switch (y)
                            {
                                case "1": str = "select * from songslist where artist = 'Ed Sheeran'";break;
                                case "2": str = "select * from songslist where artist = 'Queen'";break;

                                default: str = "select * from songslist";break;
                            }
                            System.out.println(str);
                            connection = DBConnection.getConnection();
                            statement = connection.createStatement();
                            rs = statement.executeQuery(str);
                            while (rs.next()){
                                System.out.println(rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4) + " " + rs.getString(5) + " " + rs.getString(6));
                            }
                            //System.out.println("");
                            System.out.println("Choose songId for song to be inserted in playlist.");
                            chooseSongId = scanner.next();
                            System.out.println("choosesongid="+chooseSongId);

                            if (!chooseSongId.equals("0")){
                                String str1 = "select * from songslist where songid = '"+chooseSongId+"'";
                                System.out.println(str1);
                                rs = statement.executeQuery(str1);
                                while (rs.next())
                                {
                                    String songid = rs.getString(1);
                                    String songname = rs.getString(2);
                                    String genre = rs.getString(3);
                                    String artist = rs.getString(4);
                                    String duration = rs.getString(5);
                                    String songpath = rs.getString(6);

                                    statement = connection.createStatement();
                                    str = "insert into "+confirmedNewPlaylistId+" values('"+songid+"','"+songname+"','"+genre+"','"+artist+"','"+duration+"','"+songpath+"')";
                                    //int rs1 = statement.executeUpdate(str);
                                    statement.executeUpdate(str);
                                }
                            }

                        }

                        if (chooseSongId.equals("0")){
                            System.out.println("Press 1. to see yours playlists");
                            System.out.println("Press 2. to create another playlists");
                            System.out.println("Press 3. to exit jukebox");
                            int choice1 = scanner.nextInt();
                            if (choice1==1){
                                showYourPlaylists(userId, userName);
                            }
                            else if (choice1 == 2) {
                                createPlaylist(userId,userName);
                            }
                            else {
                                user.exitOption(userName);
                            }
                        }
                        i = 1;
                    }
                    break;
                }
            }
        } catch (SQLException e) {e.printStackTrace();}

    }

    public void showYourPlaylists(String userId, String userName){
        System.out.println("My all Playlists");
        try {
            connection = DBConnection.getConnection();
            Statement statement = connection.createStatement();
            String str= "Select * from usersplaylists where userid = '"+userId+"'";
            ResultSet rs = statement.executeQuery(str);

            ResultSetMetaData rsmd = rs.getMetaData();
            int column = rsmd.getColumnCount();
            System.out.print("Sr.No.|");
            for (int i=1; i<=column;i++){
                System.out.print(rsmd.getColumnName(i)+" | ");
            }
            System.out.println();
            System.out.println("------------------------------------------");
            int z = 1;
            while (rs.next()){
                System.out.println("  "+z+"    "+rs.getString(1)+"   "+rs.getString(2)+"   "+rs.getString(3));
                z++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("Enter playlistId to play songs from that playlist.");
        String playlistIdToPlay = scanner.next();
        playSongsFromPlaylist(playlistIdToPlay,userId, userName);
        // play song of playlist method
    }

    public void playSongsFromPlaylist(String playlistId, String userId, String userName){
        connection = DBConnection.getConnection();
        String choosesongId = "-1";
        while (!choosesongId.equals("0"))
        {
            try {
                Statement st = connection.createStatement();
                String str = "select * from "+playlistId;
                ResultSet rs=st.executeQuery(str);
                while (rs.next()){
                    System.out.println(rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4) + " " + rs.getString(5) + " " + rs.getString(6));
                }

                System.out.println("Enter song name to play song");
                System.out.println("Enter 0 to exit");
                choosesongId = scanner.nextLine();
                if (!choosesongId.equals("0")){
                    play.playSong(choosesongId);
                }
                //play.playSong(choosesongId);

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (UnsupportedAudioFileException e) {
                e.printStackTrace();
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        int z=0;
        System.out.println("Press 1 to exit.");
        System.out.println("Press 2 to select playlist");
        System.out.println("Press 3 to create playlist");
        z = scanner.nextInt();
        if (z==1){
            user.exitOption();
        }
        else if (z==2){
            showYourPlaylists(userId, userName);
        } else if (z==3) {
            createPlaylist(userId,userName);
        }
        else {
            System.out.println("invalid options");
            playSongsFromPlaylist(playlistId, userId, userName);
        }

    }

}
