package com.techacademy.controller;

import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.validation.BindingResult; // 追加
import org.springframework.validation.annotation.Validated; // 追加
//@Validated を使ってバリデーションを実行した結果、BindingResult を使ってその結果を取り扱う。両方が揃っている必要がある。

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.techacademy.entity.User;
import com.techacademy.service.UserService;


@Controller
@RequestMapping("user") //クラス全体に対して /user という部分のURLを追加することを意味する
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    /** 一覧画面を表示 */
    @GetMapping("/list") //("/list)へのGETリクエストを処理するメソッドを指定。@GetMapping("/user/list") と書くと、二重のuserがURLに含まれてしまう（/user/user/list）ので避ける。*/
    public String getList(Model model) {
        model.addAttribute("userlist", service.getUserList());
        return "user/list";
    }

    /** User登録画面を表示　*/
     /*
         このメソッドがないと、ユーザーが /user/register にアクセスしても、ユーザー登録フォームが表示されない。
         つまり①、@GetMapping("/register") の役割は、ブラウザからのHTTPリクエストを受け取り、それに対してサーバーがどのページを返すべきかを指示するもの
         つまり②、HTMLは直接HTTPリクエストを受け取ることはできない。HTMLファイルをどのように、どのタイミングで表示するかを決定するのは、サーバー側のロジック（コントローラー）
     */
    @GetMapping("/register") //registerにアクセス→@GetMappingがついたメソッドが実行される
    public String getRegister(@ModelAttribute User user) {
        return "user/register";
    }

    /** User登録処理 */
    @PostMapping("/register") //registerでフォーム入力→@PostMappingがついたメソッドにマッピングされる
    //@Validated バリデーション＝変なことないか？のチェック
    public String postRegister(@Validated User user, BindingResult res, Model model) {
        if(res.hasErrors()) {
            // エラーあり
            return getRegister(user);
        }
        // User登録
        service.saveUser(user);
        // 一覧画面にリダイレクト
        return "redirect:/user/list";
    }

    /** User更新画面を表示 */
    @GetMapping("/update/{id}/")
    public String getUser(@PathVariable("id") Integer Id, Model model) {
        if(Id == null) {
            return "user/update";
        }
        model.addAttribute("user", service.getUser(Id));
        return "user/update";
    }

    //課題のため修正
    /**User更新処理*/
    @PostMapping("/update/{id}/")
    public String postUser(@PathVariable("id") Integer Id, @Validated User user, BindingResult res, Model model) {
        if(res.hasErrors()) {
                Id = null;
                return getUser(Id,model);
        }
        service.saveUser(user);
        return "redirect:/user/list";
    }

    /** User削除処理 */
    @PostMapping(path="list", params="deleteRun")
    public String deleteRun(@RequestParam(name="idck") Set<Integer> idck, Model model) {
        service.deleteUser(idck);
        return "redirect:/user/list";
        //redirectの場合、model への追加は不要で、リダイレクトだけを行えば十分（この場合は削除処理の後に特にビューに渡す情報がない＝model.addAllAttributes() を使って情報をビューに渡す必要はない）
    }

}


