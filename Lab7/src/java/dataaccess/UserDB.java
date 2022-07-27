/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import models.User;
/**
 *
 * @author Jashan Gill
 */
public class UserDB {
    
    public List<User> getAll() throws Exception {
    List<User> users = new ArrayList<>();
    ConnectionPool cp = ConnectionPool.getInstance();
    Connection con = cp.getConnection();
    PreparedStatement ps = null;
    ResultSet rs = null;
    
    String sql = "SELECT * FROM user";
    
    try {
        ps = con.prepareStatement(sql);
        rs = ps.executeQuery();
        while (rs.next()) {
            String email = rs.getString(1);
            String firstName = rs.getString(3);
            String lastName = rs.getString(4);
            int role = rs.getInt(6);
            boolean active = rs.getBoolean(2);
            User User = new User(email, firstName, lastName,"", role, active);
            users.add(User);
        }
    } finally {
        DBUtil.closeResultSet(rs);
        DBUtil.closePreparedStatement(ps);
        cp.freeConnection(con);
    }

    return users;
}

public User get(String email) throws Exception {
    User user = null;
    ConnectionPool cp = ConnectionPool.getInstance();
    Connection con = cp.getConnection();
    PreparedStatement ps = null;
    ResultSet rs = null;
    
    String sql = "SELECT * FROM user WHERE email=?";
    
    try {
        ps = con.prepareStatement(sql);
        ps.setString(1, email);
        rs = ps.executeQuery();
        if (rs.next()) {
            String firstName = rs.getString(3);
            String lastName = rs.getString(4);
            int role = rs.getInt(6);
            boolean active = rs.getBoolean(2);
            user = new User(email, firstName, lastName,"", role, active);
        }
    } finally {
        DBUtil.closeResultSet(rs);
        DBUtil.closePreparedStatement(ps);
        cp.freeConnection(con);
    }
    
    return user;
}

public void insert(User user) throws Exception {
    ConnectionPool cp = ConnectionPool.getInstance();
    Connection con = cp.getConnection();
    PreparedStatement ps = null;
    String sql = "INSERT INTO user (email, active, first_name,last_name, password, role) VALUES (?, ?, ?, ?, ?, ?)";
    
    try {
        ps = con.prepareStatement(sql);
        ps.setString(1, user.getEmail());
        ps.setBoolean(2, user.isActive());
        ps.setString(3, user.getFirstName());
        ps.setString(4, user.getLastName());
        ps.setString(5, user.getPassword());
        ps.setInt(6, user.getRole());
        ps.executeUpdate();
    } finally {
        DBUtil.closePreparedStatement(ps);
        cp.freeConnection(con);
    }
}

public void update(User user) throws Exception {
    ConnectionPool cp = ConnectionPool.getInstance();
    Connection con = cp.getConnection();
    PreparedStatement ps = null;
    String sql = "UPDATE user SET first_name=?, last_name=?, role=? , active=? WHERE email=?";
    
    try {
        ps = con.prepareStatement(sql);
        ps.setString(1, user.getFirstName());
        ps.setString(2, user.getLastName());
        ps.setInt(3, user.getRole());
        ps.setBoolean(4, user.isActive());
        ps.setString(5, user.getEmail());
        ps.executeUpdate();
    } finally {
        DBUtil.closePreparedStatement(ps);
        cp.freeConnection(con);
    }
}

public void delete(User user) throws Exception {
    ConnectionPool cp = ConnectionPool.getInstance();
    Connection con = cp.getConnection();
    PreparedStatement ps = null;
    String sql = "DELETE FROM user WHERE email=?";
    
    try {
        ps = con.prepareStatement(sql);
        ps.setString(1, user.getEmail());
        ps.executeUpdate();
    } finally {
        DBUtil.closePreparedStatement(ps);
        cp.freeConnection(con);
    }
}
    

}
