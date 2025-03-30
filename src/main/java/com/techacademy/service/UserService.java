package com.techacademy.service;

import java.util.List;
import java.util.Set;

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

    /** Userを一件検索して返す */
    public User getUser(Integer id) { /*なぜInteger?：findById メソッドは、ID を Integer 型として受け取るため*/
        return userRepository.findById(id).get();
    }

    /** フォーム入力情報をDBに登録 */
    @Transactional /*エラーの際に戻るなど、基本的にフォーム登録の際は必要。またDB操作（読み取りや検索ではなく、削除や登録など）を扱う際に必要*/
    public User saveUser(User user) {
        /*インターフェイス(userRepository)の定義済みメソッド save を呼び出しています。
         * save メソッドは、引数で渡したエンティティインスタンスのデータをテーブルに保存します。*/
        return userRepository.save(user);
    }

    /** Userの削除*/
    @Transactional
    public void deleteUser(Set<Integer> idck){ //SETの利用により、複数データを選択肢て削除が可能。また複数データの取り扱いなので@Transactional利用が保守性の観点で必要
        for(Integer id : idck) {
            userRepository.deleteById(id);
        }
    }
}




