package com.project.khrblog.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class BoardDao {
    @Autowired
    JdbcTemplate jt;
    public List<Map<String,Object>> categoryList(){
        String sqlStmt = "select "; 
        sqlStmt += "code_id as codeId, ";
        sqlStmt += "code_nm as codeNm ";
        sqlStmt += "from category";
        return jt.queryForList(sqlStmt);
    }
    public void boardInsert(String title,
                            String content,
                            String category,
                            String userId){
        String sqlStmt = "insert into board";
        sqlStmt += "(title, content, category, id) ";
        sqlStmt += "values (?,?,?,?)";
        jt.update(sqlStmt, title,content,category,userId);
    }
    public List<Map<String,Object>> selectBoard(){
        String sqlStmt = "select ";
        sqlStmt += "a.seq, ";
        sqlStmt += "c.code_nm as codeNm, ";
        sqlStmt += "b.nick, ";
        sqlStmt += "a.title, ";
        sqlStmt += "a.search_cnt as searchCnt , ";
        sqlStmt += "a.reg_dt as regDt ";
        sqlStmt += "from board a, user b, category c ";
        sqlStmt += "where a.id = b.id ";
        sqlStmt += "and a.category = c.code_id ";
        sqlStmt += "order by a.seq asc";
        return jt.queryForList(sqlStmt);
    }
    public Map<String,Object> selectBoardDetail(String seq){
        String sqlStmt = "select ";
        sqlStmt += "a.seq, ";
        sqlStmt += "c.code_nm as codeNm, ";
        sqlStmt += "b.nick, ";
        sqlStmt += "a.title, ";
        sqlStmt += "a.content,";
        sqlStmt += "a.search_cnt as searchCnt , ";
        sqlStmt += "a.like_cnt as likeCnt,";
        sqlStmt += "a.dislike_cnt as dislikeCnt,";
        sqlStmt += "a.reg_dt as regDt , ";
        sqlStmt += "a.mod_dt as modDt ";
        sqlStmt += "from board a, user b, category c ";
        sqlStmt += "where a.id = b.id ";
        sqlStmt += "and a.category = c.code_id ";
        sqlStmt += "and a.seq = ?";
        return jt.queryForMap(sqlStmt, seq);
    }
    public void increaseSearchCnt(String seq){
        String sqlStmt = "update board set ";
        sqlStmt += "search_cnt = search_cnt+1 ";
        sqlStmt += "where seq = ?";
        jt.update(sqlStmt, seq);
    }
    public List<Map<String,Object>> selectComment(String seq){
        String sqlStmt = "select ";
               sqlStmt += "c.nick , ";
               sqlStmt += "a.content, ";
               sqlStmt += "a.reg_dt as regDt ";
               sqlStmt += "from comment a, board b, user c ";
               sqlStmt += "where a.board_seq = b.seq ";
               sqlStmt += "and a.id = c.id ";
               sqlStmt += "and board_seq = ?;";
        return jt.queryForList(sqlStmt,seq);
    }
    public void commentInsert(String boardSeq, String commenter, String content){
        String sqlStmt = "insert into comment";
        sqlStmt += "(board_seq,id,content) ";
        sqlStmt += "values(?,?,?)";
        jt.update(sqlStmt, boardSeq,commenter,content);
    }
}
