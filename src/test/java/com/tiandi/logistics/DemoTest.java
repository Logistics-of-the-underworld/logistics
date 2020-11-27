package com.tiandi.logistics;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tiandi.logistics.entity.pojo.User;
import com.tiandi.logistics.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Yue Wu
 * @version 1.0
 * @since 2020/11/27 16:36
 */
@SpringBootTest
public class DemoTest {

    @Autowired
    UserService userService;

    @Test
    public void test() {
        boolean remove = userService.remove(new QueryWrapper<User>().eq("username", "root"));
        System.out.println(remove);
    }
}
