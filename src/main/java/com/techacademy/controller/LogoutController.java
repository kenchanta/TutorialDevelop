package com.techacademy.controller;

import org.springframework.web.bind.annotation.PostMapping;

public class LogoutController {
    /** ログアウト処理を行なう */
    @PostMapping("/logout")
    public String postLogout(){
    return "redirect:/login";
    }
}
