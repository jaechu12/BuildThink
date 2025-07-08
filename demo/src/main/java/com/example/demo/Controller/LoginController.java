package com.example.demo.Controller;

import com.example.demo.Model.Users;
import com.example.demo.Service.LoginService;
import com.example.demo.SessionConst;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("users", new Users());
        return "login";
    }

    @PostMapping("/login")
    public String login(@Valid Users users, BindingResult bindingResult, HttpServletRequest request,  Model model) throws ServletException {

        if (bindingResult.hasErrors()) {
            return "login";
        }

        Users loginMember = loginService.login(users.getEmail(), users.getPassword());

        if (loginMember == null) {
            model.addAttribute("errorMessage", "이메일 또는 비밀번호를 확인해주세요");
            return "login";
        }

        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);

        return "redirect:/";
    }

    @GetMapping("/")
    public String home(HttpSession session, Model model) {
        Users loginMember = (Users) session.getAttribute(SessionConst.LOGIN_MEMBER);

        if (loginMember == null) {
            return "redirect:/login";
        }

        if (loginMember != null) {
            model.addAttribute("username", loginMember.getUsername());
        }

        return "logined";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        return "redirect:/login";
    }
}
