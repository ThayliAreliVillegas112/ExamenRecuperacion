package mx.edu.utez.server;

import mx.edu.utez.client.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Handler {

    Connection con;
    PreparedStatement pstm;
    ResultSet rs;
    private mx.edu.utez.database.ConnectionMySQL ConnectionMySQL;

    public int suma(int x, int y){
        return x + y;
    }

    public boolean createUser(String name, String lastname, String email, String password){
        boolean flag = false;
        try{
            con = ConnectionMySQL.getConnection();
            pstm = con.prepareCall("INSERT INTO `user`(name, lastname, email, password, date_registered, status)VALUES(?,?,?,?,NOW(),1);");
            pstm.setString(1, name);
            pstm.setString(2, lastname);
            pstm.setString(3, email);
            pstm.setString(4, password);

            flag = pstm.executeUpdate() == 1;
        }catch(SQLException e){
            System.out.println("Error" + e.getMessage());
        }finally{
            closeConnection();
        }

        return flag;
    }

    public boolean updateUser(int id, String name, String lastname, String email, String password){
        boolean flag = false;
        try{
            con = ConnectionMySQL.getConnection();
            pstm = con.prepareCall("UPDATE `user` SET name = ?, lastname = ?, email = ?, password = ? WHERE id = ?;");
            pstm.setString(1, name);
            pstm.setString(2, lastname);
            pstm.setString(3, email);
            pstm.setString(4, password);
            pstm.setInt(5, id);

            flag = pstm.executeUpdate() == 1;
        }catch(SQLException e){
            System.out.println("Error" + e.getMessage());
        }finally{
            closeConnection();
        }

        return flag;
    }

    public boolean deleteUser(int id){
        boolean flag = false;
        try{
            con = ConnectionMySQL.getConnection();
            pstm = con.prepareCall("DELETE FROM `user` WHERE id = ?;");
            pstm.setInt(1, id);

            flag = pstm.executeUpdate() == 1;
        }catch(SQLException e){
            System.out.println("Error" + e.getMessage());
        }finally{
            closeConnection();
        }

        return flag;
    }

    public List<User> findAll(){
    	List<User> listUser = new ArrayList<>();
    	try{
           con = ConnectionMySQL.getConnection();
           String query = "  SELECT user.id, user.name, user.lastname, user.email, user.password, user.date_registered, user.status FROM user;";
           statement = con.createStatement();
           rs = statement.executeQuery(query);
           while (rs.next()){
              User user = new User();
              user.setId(rs.getInt("id"));
              user.setName(rs.getString("name"));
              user.setLastname(rs.getString("lastname"));
              user.setEmail(rs.getString("email"));
              user.setPassword(rs.getString("password"));
              user.setDate(rs.getString("date_registered"));
              user.setStatus(rs.getInt("status"));
              listUser.add(user);
         }
             }catch (SQLException ex){
                  ex.printStackTrace();
         }finally {
                  closeConnection();
         }
         return listUser;
     }

    public void closeConnection(){
        try{
            if(con != null){
                con.close();
            }
            if(pstm != null){
                pstm.close();
            }
            if(rs != null){
                rs.close();
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }
}



