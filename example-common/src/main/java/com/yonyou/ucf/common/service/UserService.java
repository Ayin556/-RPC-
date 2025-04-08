package com.yonyou.ucf.common.service;

import com.yonyou.ucf.common.model.User;

/**
 * @author Ayin
 * @verison 1.0
 * @date 2025/4/7 下午12:34
 * @description 用户服务
 */
public interface UserService {
    /**
     * 获取用户
     * */
    User getUser(User user);

    /**
     * 获取 字符串
     */
    default short getnumber() {
        return  99;
    }
}
