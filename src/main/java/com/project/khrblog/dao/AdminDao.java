package com.project.khrblog.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AdminDao {
    @Autowired
    JdbcTemplate jt;
    public List<Map<String,Object>> selectUserList(){
        String sqlStmt = "select ";
               sqlStmt += "id, ";
               sqlStmt += "name, ";
               sqlStmt += "nick, ";
               sqlStmt += "lev, ";
               sqlStmt += "del_fg as delFg ";
               sqlStmt += "from user ";
               sqlStmt += "where id <> 'admin'";
        return jt.queryForList(sqlStmt);
    }
    public List<Map<String,Object>> selectBoardList(){
        String sqlStmt = "select ";
               sqlStmt += "seq, ";
               sqlStmt += "id, ";
               sqlStmt += "title, ";
               sqlStmt += "like_cnt as likeCnt, ";
               sqlStmt += "dislike_cnt as dislikeCnt, ";
               sqlStmt += "search_cnt as searchCnt ";
               sqlStmt += "from board";
        return jt.queryForList(sqlStmt);
    }
    public List<Map<String,Object>> selectCommentList(String seq){
        String sqlStmt = "select ";
               sqlStmt += "a.seq, ";
               sqlStmt += "a.id, ";
               sqlStmt += "a.content, ";
               sqlStmt += "b.title, ";
               sqlStmt += "a.board_seq as boardSeq ";
               sqlStmt += "from comment a ";
               sqlStmt += "join board b ";
               sqlStmt += "on a.board_seq = b.seq ";
               sqlStmt += "where board_seq = ? ";
        return jt.queryForList(sqlStmt, seq);
    }
    public void userLevUpdate(String id){
        String sqlStmt = "update user set ";
               sqlStmt += "lev = 1 ";
               sqlStmt += "where id = ?";
        jt.update(sqlStmt,id);
    }
    public void userDelete(String id){
        String sqlStmt = "update user set ";
               sqlStmt += "del_fg = 1 ";
               sqlStmt += "where id = ?";
        jt.update(sqlStmt,id);
    }
    public void boardDelete(String seq){
        String sqlStmt = "delete from board ";
               sqlStmt += "where seq = ?";
        String sqlStmt2 = "delete from comment ";
               sqlStmt2 += "where board_seq = ?";
        jt.update(sqlStmt, seq);
        jt.update(sqlStmt2, seq);
    }
    public void commentDelete(String seq){
        String sqlStmt = "delete from comment ";
               sqlStmt += "where seq = ?";
        jt.update(sqlStmt, seq);
    }
}
