package com.project.khrblog.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.khrblog.dao.BoardDao;
import com.project.khrblog.dao.UserDao;

import jakarta.servlet.http.HttpSession;

@Controller
public class BoardController {
    @Autowired
    BoardDao boardDao;
    @Autowired
    UserDao userDao;

    @GetMapping("board/insert")
    public String boardInsert(Model model,
                              HttpSession session){
        // 정회원, 관리자만 접속 가능하게 하기
        int userLev = userDao.loginLevel(session.getAttribute("id").toString());
        if(userLev == 1 || userLev == 999){
            // 세션 아이디 받아오기
            String userId = session.getAttribute("id").toString();
            model.addAttribute("userId", userId);
            // 카테고리 리스트 불러오기
            List<Map<String,Object>> categoryList = boardDao.categoryList();
            model.addAttribute("categoryList", categoryList);
            return "board/board_insert";
        }
        else{
            return "redirect:/main";
        } 
    }

    @GetMapping("board/insert/action")
    public String boardInsertAction(@RequestParam String title,
                                    @RequestParam String content,
                                    @RequestParam String category,
                                    @RequestParam String userId) {
        boardDao.boardInsert(title, content, category, userId);
        return "redirect:/main";
    }

    @GetMapping("board/detail")
    public String boardDetail(@RequestParam String seq,
                              Model model,
                              HttpSession session) {
        model.addAttribute("userId", session.getAttribute("id"));
        Map<String, Object> boardDetail = boardDao.selectBoardDetail(seq);
        model.addAttribute("boardDetail", boardDetail);
        boardDao.increaseSearchCnt(seq);
        List<Map<String, Object>> commentList = boardDao.selectComment(seq);
        model.addAttribute("commentList", commentList);
        return "board/board_detail";
    }

    @GetMapping("comment/insert")
    public String commentInsert(@RequestParam String boardSeq,
                                @RequestParam String commenter,
                                @RequestParam String content) {
        boardDao.commentInsert(boardSeq, commenter, content);
        return "redirect:/board/detail?seq=" + boardSeq;
    }
}
