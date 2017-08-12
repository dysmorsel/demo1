package com.example.demo1.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MessageRepository {
    private static final String INSERT_SQL  = "INSERT INTO message VALUES(?,?,?,?)";
    private static final String QUERY_ALL_SQL = "SELECT * FROM message";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void insert(Message message1){
        jdbcTemplate.update(INSERT_SQL, message1.getName(),message1.getEmail(),
                message1.getQuestion(),message1.getMessage());
    }

    public List<Message> queryAll(){
        List<Message> messageList;
       messageList= jdbcTemplate.query(QUERY_ALL_SQL, (resultSet, i) -> new Message(resultSet.getString("name"),resultSet.getString("email"),
               resultSet.getString("question"),resultSet.getString("message")));
       return messageList;
    }
}
