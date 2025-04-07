package com.yonyou.ucf.provider;

import com.yonyou.ucf.common.model.User;
import com.yonyou.ucf.common.service.UserService;

/**
 * @author Ayin
 * @verison 1.0
 * @date 2025/4/7 下午12:39
 * @description 用户服务实现类
 */
public class UserServiceImpl implements UserService {
    public User getUser(User user) {
        System.out.println("打印用户编码"+user.getCode());
        return user;
    }
}
