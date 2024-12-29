package com.sparksInTheStep.webBoard.admin.presentation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
