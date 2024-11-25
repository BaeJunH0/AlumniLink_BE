package com.sparksInTheStep.webBoard.admin.presentation;

import com.sparksInTheStep.webBoard.auth.application.dto.MemberInfo;
import com.sparksInTheStep.webBoard.global.annotation.AuthorizedUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/admin")
public class AdminPageController {
    @GetMapping()
    public String adminMemberPage(){
        return "adminPage.html";
    }

    @GetMapping("/login")
    public String adminLoginPage(){
        return "loginPage.html";
    }
}
