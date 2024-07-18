package com.project.khrblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.annotation.RequestScope;

import com.project.khrblog.dao.AdminDao;

import java.util.*;

@Controller
public class AdminController {
    @Autowired
    AdminDao adminDao;
    @GetMapping("adminpage")
    public String adminpage(Model model){
        // 관리자 페이지에 나타낼 회원 리스트
        List<Map<String,Object>> userList = adminDao.selectUserList();
        model.addAttribute("userList", userList);
        // 글 리스트
        List<Map<String,Object>> boardList = adminDao.selectBoardList();
        model.addAttribute("boardList", boardList);

        return "adminpage";
    }
    @GetMapping("baord/comment")
    public String boardComment(@RequestParam String seq,
                               Model model){
        // seq번째 글에 달린 댓글 리스트
        List<Map<String,Object>> commentList = adminDao.selectCommentList(seq);
        model.addAttribute("commentList", commentList);
        return "admin_comment";
    }
    @GetMapping("user/update")
    public String userUpdate(@RequestParam String id){
        adminDao.userLevUpdate(id);
        return "redirect:/adminpage";
    }
    @GetMapping("user/delete")
    public String userDelete(@RequestParam String id){
        adminDao.userDelete(id);
        return "redirect:/adminpage";
    }
    @GetMapping("board/delete")
    public String boardDelete(@RequestParam String seq){
        adminDao.boardDelete(seq);
        return "redirect:/adminpage";
    }
    @GetMapping("comment/delete")
    public String commentDelete(@RequestParam String seq,
                                @RequestParam String boardSeq){
        adminDao.commentDelete(seq);
        return "redirect:/baord/comment?seq="+boardSeq;
    }
}
