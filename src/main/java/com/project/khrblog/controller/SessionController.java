package com.project.khrblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.khrblog.dao.UserDao;

import jakarta.servlet.http.HttpSession;

@Controller
public class SessionController {
    @Autowired
    UserDao userDao;
    @GetMapping("login")    // 로그인 주소
    public String login(){
        return "login";     // 보여줄 html
    }
    @PostMapping("login/action")    // 로그인 form 에서 보낸 값
    public String loginAction(@RequestParam String id,
                              @RequestParam String pw,
                              HttpSession session,
                              Model model){
        // userDao에 로그인 아이디, 비번 보내기
        int usercheck = userDao.loginId(id,pw);
        // 아이디가 데이터베이스에 있으면 로그인 성공
        if(usercheck > 0){
            // form에서 받은 아이디를 세션에 넣어줌
            session.setAttribute("id", id);
            // 세션 유효시간
            // session.setMaxInactiveInterval(600);
            return "redirect:/main";  // 메인 페이지로
        }
        // 가입된 정보가 없으면 로그인 실패 (로그인 페이지에 머무르기)
        else{
            return "redirect:/login";
        }
    }
    @GetMapping("logout")   // 로그아웃 주소
    public String logout(HttpSession session){
        // 세션 종료
        session.invalidate();
        return "redirect:/main"; // 보여줄 html
    }
}
