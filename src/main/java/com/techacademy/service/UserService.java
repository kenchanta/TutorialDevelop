package com.techacademy.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.techacademy.entity.User;
import com.techacademy.repository.UserRepository;
import jakarta.transaction.Transactional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository repository) {
        this.userRepository = repository;
    }

    /** 全件を検索して返す */
    public List<User> getUserList() {
        return userRepository.findAll();
    }

    // ----- 追加:ここから -----
    /** Userを一件検索して返す */
    public User getUser(Integer id) { /*なぜInteger?：findById メソッドは、ID を Integer 型として受け取るため*/
        return userRepository.findById(id).get();
    }
    // ----- 追加:ここまで -----

    /** フォーム入力情報をDBに登録 */
    @Transactional /*エラーの際に戻るなど、基本的にフォーム登録の際は必要*/
    public User saveUser(User user) {
        /*インターフェイス(userRepository)の定義済みメソッド save を呼び出しています。
         * save メソッドは、引数で渡したエンティティインスタンスのデータをテーブルに保存します。*/
        return userRepository.save(user);
    }

}