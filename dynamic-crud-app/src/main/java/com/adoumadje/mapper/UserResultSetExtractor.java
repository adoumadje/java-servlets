package com.adoumadje.mapper;

import com.adoumadje.interfaces.ResultSetExtractor;
import com.adoumadje.model.User;
import com.adoumadje.utils.Cryptographer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserResultSetExtractor implements ResultSetExtractor<User> {

    @Override
    public User extract(ResultSet res) throws SQLException {
        if(res.next()) {
            User user = new User();
            user.setId(res.getLong("id"));
            user.setFirstName(res.getString("firstname"));
            user.setLastName(res.getString("lastname"));
            user.setEmail(res.getString("email"));
            user.setPassword(Cryptographer.decode(res.getString("password")));
            return user;
        }
        return null;
    }

    @Override
    public List<User> extractList(ResultSet res) throws SQLException {
        List<User> users = new ArrayList<>();
        while (res.next()) {
            User user = new User();
            user.setId(res.getLong("id"));
            user.setFirstName(res.getString("firstname"));
            user.setLastName(res.getString("lastname"));
            user.setEmail(res.getString("email"));
            user.setPassword(res.getString("password"));
            users.add(user);
        }
        return users;
    }
}
