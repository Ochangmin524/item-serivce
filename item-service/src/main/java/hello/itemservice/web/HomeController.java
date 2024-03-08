package hello.itemservice.web;


import hello.itemservice.domain.member.Member;
import hello.itemservice.web.argumentresolver.Login;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.AttributedString;


@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {



    @GetMapping("/")
    public String homelogin(@Login Member loginMember,
                            Model model) {
        //세션에 회원 데이터가 없으면 home
        if (loginMember == null){
            return "home/home";
        }

        //세션이 유지되면 로그인으로 이동
        model.addAttribute("member", loginMember);
        return "home/loginHome";

    }
}
