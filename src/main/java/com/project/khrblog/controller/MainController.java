package com.project.khrblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.khrblog.dao.BoardDao;
import com.project.khrblog.dao.MainDao;
import com.project.khrblog.dao.UserDao;

import jakarta.servlet.http.HttpSession;
import java.util.*;

@Controller
public class MainController {
    @Autowired
    UserDao userDao;
    @Autowired
    BoardDao boardDao;
    @Autowired
    MainDao mainDao;

    @GetMapping("main") // 메인 페이지 주소
    public String main(HttpSession session, // 세션 아이디 사용
                       Model model) { // model에 담아 html에서 사용
        // 회원이 아닌 경우 session null
        // 준회원일 경우 lev 0
        // 정회원일 경우 lev 1
        // 관리자일 경우 lev 999
        // 다 다른 메인 페이지가 필요함

        // 세션에 저장된 아이디가 있다면(not null)
        if (session.getAttribute("id") != null) {
            // 세션 아이디를 userDao로 보내 로그인 한 회원의 등급을 받아온다.
            int userLev = userDao.loginLevel(session.getAttribute("id").toString());
            // 탈퇴 유무 받아오기
            int userDel = userDao.loginDel(session.getAttribute("id").toString());
            // model에 등급 담기
            model.addAttribute("userLev", userLev);
            model.addAttribute("userDelFg", userDel);
            // model에 아이디 담기
            model.addAttribute("id", session.getAttribute("id"));
        } else {
            // 로그인 하지 않은 경우 = 세션이 없는 경우
            // model에 임의로 회원 등급이 -1을 담는다
            model.addAttribute("userLev", "-1");
        }

        // 전체 글 목록 가져오기
        List<Map<String, Object>> boardList = boardDao.selectBoard();
        
        model.addAttribute("boardList", boardList);

        return "main";
    }

    @GetMapping("board/search")
    public String boardSearch(@RequestParam String searchBoardContent,
            Model model) {
        List<Map<String, Object>> searchBoardList = mainDao.selectSearchBoard(searchBoardContent);
        model.addAttribute("searchBoardList", searchBoardList);

        return "main_search";
    }

    @GetMapping("board/desc")
    public String boardDesc(Model model,
                            HttpSession session) {

        // 전체 글 목록 가져오기
        List<Map<String, Object>> boardList = mainDao.selectBoardDesc();

        model.addAttribute("boardList", boardList);

        if (session.getAttribute("id") != null) {
            int userLev = userDao.loginLevel(session.getAttribute("id").toString());
            int userDel = userDao.loginDel(session.getAttribute("id").toString());
            model.addAttribute("userLev", userLev);
            model.addAttribute("userDelFg", userDel);
        } else {
            model.addAttribute("userLev", "-1");
        }

        return "main";
    }

    @GetMapping("board/asc")
    public String boardAsc(Model model,
                            HttpSession session) {

        // 전체 글 목록 가져오기
        List<Map<String, Object>> BoardList = mainDao.selectBoardAsc();

        model.addAttribute("boardList", BoardList);

        if (session.getAttribute("id") != null) {
            int userLev = userDao.loginLevel(session.getAttribute("id").toString());
            int userDel = userDao.loginDel(session.getAttribute("id").toString());
            model.addAttribute("userLev", userLev);
            model.addAttribute("userDelFg", userDel);
        } else {
            model.addAttribute("userLev", "-1");
        }

        return "main";
    }
}
