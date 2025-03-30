package com.techacademy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.techacademy.entity.User;
import com.techacademy.service.UserService;

@Controller
@RequestMapping("user") /*クラス全体に対して /user という部分のURLを追加することを意味する*/
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    /** 一覧画面を表示 */
    @GetMapping("/list") /*("/list)へのGETリクエストを処理するメソッドを指定。@GetMapping("/user/list") と書くと、二重のuserがURLに含まれてしまう（/user/user/list）ので避ける。*/
    public String getList(Model model) {
        model.addAttribute("userlist", service.getUserList());
        return "user/list";
    }

    // ----- 追加:ここから -----
    /** User登録画面を表示 */
    @GetMapping("/register") //registerにアクセス→@GetMappingがついたメソッドが実行される
    public String getRegister(@ModelAttribute User user) {
        return "user/register";
    }

    /** User登録処理 */
    @PostMapping("/register") //registerでフォーム入力→@PostMappingがついたメソッドにマッピングされる
    public String postRegister(User user) {
        //User登録
        service.saveUser(user);
        return "redirect:/user/list";
    }
}


