package com.tiandi.logistics;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
        Page<User> page = new Page<>(1, 3);
        Page<User> userList = userService.getBaseMapper().selectPage(page, null);
        userList.getRecords().forEach(System.out::println);
        System.out.println("size: " + userList.getSize());
        System.out.println("total: " + userList.getTotal());
    }

    @Test
    public void test2() {
        String line = "123456789";
        CharSequence sequence = line.subSequence(line.length() - 6, line.length());
        System.out.println(sequence.toString());
    }
}
