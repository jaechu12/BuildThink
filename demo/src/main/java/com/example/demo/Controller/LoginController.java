package com.example.demo.Controller;

import com.example.demo.DTO.RequestDTO.UserLoginDTO;
import com.example.demo.DTO.RequestDTO.UserRegisterDTO;
import com.example.demo.Model.Users;
import com.example.demo.Repository.BuildingStatRepository;
import com.example.demo.Repository.UsersRepository;
import com.example.demo.Service.LoginService;
import com.example.demo.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.DayOfWeek;
import java.time.LocalDate;

@Controller
public class LoginController {

    private final LoginService loginService;
    private final UsersRepository usersRepository;
    private BuildingStatRepository buildingStatRepository;

    public LoginController(LoginService loginService, UsersRepository usersRepository, BuildingStatRepository buildingStatRepository) {
        this.loginService = loginService;
        this.usersRepository = usersRepository;
        this.buildingStatRepository = buildingStatRepository;
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("userLoginDTO", new UserLoginDTO());
        return "login";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute UserLoginDTO userLoginDTO, BindingResult bindingResult,
                        HttpServletRequest request, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "login";
        }

        Users loginMember = loginService.login(userLoginDTO);

        if (loginMember == null) {
            redirectAttributes.addFlashAttribute("Error", "이메일 또는 비밀번호를 다시 확인해주세요!");
            return "redirect:/login";
        }

        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);

        return "redirect:/";
    }

    @GetMapping("/")
    public String home(HttpSession session, Model model) {
        Users loginMember = (Users) session.getAttribute(SessionConst.LOGIN_MEMBER);

        LocalDate today = LocalDate.now();
        DayOfWeek day = today.getDayOfWeek();
        boolean isSunday = (day == DayOfWeek.SUNDAY);
        model.addAttribute("isSunday", isSunday);

        if (loginMember == null) {
            return "redirect:/login";
        }

        model.addAttribute("username", loginMember.getUsername());

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

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("userRegisterDTO", new UserRegisterDTO());
        return "register";
    }

    @PostMapping("/register")
    public String registMember(@Valid @ModelAttribute UserRegisterDTO userRegisterDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "register";
        }

        loginService.registUsers(userRegisterDTO);
        return "redirect:/login";
    }

}
