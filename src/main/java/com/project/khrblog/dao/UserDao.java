package com.project.khrblog.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class UserDao {
    @Autowired
    JdbcTemplate jt;
    public void signupInsert(String id,
                             String pw,
                             String name,
                             String nick,
                             String phone,
                             String email){
        String sqlStmt = "insert into ";
               sqlStmt += "user(id,pw,name,nick,phone,email) ";
               sqlStmt += "values (?,?,?,?,?,?)";
        jt.update(sqlStmt, id,pw,name,nick,phone,email);
    }
    public int loginId(String id,
                       String pw){
        String sqlStmt = "select ";
               sqlStmt += "count(*) ";
               sqlStmt += "from user ";
               sqlStmt += "where id = ? ";
               sqlStmt += "and pw = ?";
        return jt.queryForObject(sqlStmt,int.class, id,pw);
    }
    public int loginLevel(String id){
        String sqlStmt = "select ";
               sqlStmt += "lev ";
               sqlStmt += "from user ";
               sqlStmt += "where id = ? ";
        return jt.queryForObject(sqlStmt,int.class, id);
    }
    public int loginDel(String id){
        String sqlStmt = "select ";
               sqlStmt += "del_fg as delFg ";
               sqlStmt += "from user ";
               sqlStmt += "where id = ?";
        return jt.queryForObject(sqlStmt, int.class, id);
    }

    public Map<String,Object> selectMyInfo(String id){
        String sqlStmt = "select ";
               sqlStmt += "id,";
               sqlStmt += "name,";
               sqlStmt += "nick,";
               sqlStmt += "phone,";
               sqlStmt += "email,";
               sqlStmt += "lev,";
               sqlStmt += "reg_dt as regDt ";
               sqlStmt += "from `user` ";
               sqlStmt += "where id = ?";
        return jt.queryForMap(sqlStmt,id);
    }
    public List<Map<String,Object>> selectMyBoard(String id){
        String sqlStmt = "select seq,title from board where id = ?";
        return jt.queryForList(sqlStmt, id);
    }
    public List<Map<String,Object>> selectMyComment(String id){
        String sqlStmt = "select ";
               sqlStmt += "a.board_seq as boardSeq, ";
               sqlStmt += "a.content as content, ";
               sqlStmt += "b.title as title ";
               sqlStmt += "from comment a, board b ";
               sqlStmt += "where a.board_seq = b.seq ";
               sqlStmt += "and a.id = ?";
        return jt.queryForList(sqlStmt, id);
    }
    public void userDeleteUpdate(String userId){
        String sqlStmt = "update user set del_fg = 1 where id = ?";
        jt.update(sqlStmt, userId);
    }
}
