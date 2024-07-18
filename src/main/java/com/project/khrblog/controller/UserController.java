package com.project.khrblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.khrblog.dao.UserDao;

import jakarta.servlet.http.HttpSession;

import java.util.*;

@Controller
public class UserController {
    @Autowired
    UserDao userDao;
    @GetMapping("signup")   // 회원가입 주소
    public String signup(){
        return "signup";    // 보여줄 html
    }
    @PostMapping("signup/action")   // 회원가입 form 에서 보낸 값
    public String signupAction(@RequestParam String id,
                               @RequestParam String pw,
                               @RequestParam String name,
                               @RequestParam String nick,
                               @RequestParam String phone,
                               @RequestParam String email){
        // signupInsert 메서드로 값 보냄
        userDao.signupInsert(id,pw,name,nick,phone,email);  
        return "redirect:/login"; // 보여줄 html
    }

    @GetMapping("mypage")
    public String mypage(@RequestParam String id,
                         Model model){
        // 내 정보
        Map<String,Object> myInfo = userDao.selectMyInfo(id);
        model.addAttribute("myInfo", myInfo);
        // 내가 쓴 글
        List<Map<String,Object>> myBoard = userDao.selectMyBoard(id);
        model.addAttribute("myBoard", myBoard);
        // 내가 쓴 댓글
        List<Map<String,Object>> myComment = userDao.selectMyComment(id);
        model.addAttribute("myComment", myComment);
        return "mypage";
    }
    @GetMapping("mypage/delete")
    public String userDelete(@RequestParam String id,
                             HttpSession session){
        // 로그인 한 아이디 삭제
        userDao.userDeleteUpdate(id);
        // 세션 종료
        session.invalidate();
        // 메인페이지로 이동
        return "redirect:/main";
    }

}
