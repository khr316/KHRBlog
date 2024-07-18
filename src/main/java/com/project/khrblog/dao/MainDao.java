package com.project.khrblog.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MainDao {
    @Autowired
    JdbcTemplate jt;

    public List<Map<String, Object>> selectSearchBoard(String searchBoardContent) {
        String sqlStmt = "select ";
        sqlStmt += "a.seq, ";
        sqlStmt += "c.code_nm as codeNm, ";
        sqlStmt += "b.nick, ";
        sqlStmt += "a.title, ";
        sqlStmt += "a.search_cnt as searchCnt, ";
        sqlStmt += "a.reg_dt as regDt ";
        sqlStmt += "from board a, user b, category c ";
        sqlStmt += "where a.id = b.id ";
        sqlStmt += "and a.category = c.code_id ";
        sqlStmt += "and title like '%" + searchBoardContent + "%' ";
        sqlStmt += "or content like '%" + searchBoardContent + "%' ";
        return jt.queryForList(sqlStmt);
    }

    public List<Map<String, Object>> selectBoardDesc() {
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
        sqlStmt += "order by a.reg_dt desc ";
        return jt.queryForList(sqlStmt);
    }

    public List<Map<String, Object>> selectBoardAsc() {
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
        sqlStmt += "order by a.reg_dt asc ";
        return jt.queryForList(sqlStmt);
    }
}