package spring.demo.mybatis.controller;

import org.springframework.web.bind.annotation.*;
import spring.demo.mybatis.entity.UserEntity;
import spring.demo.mybatis.service.IUserService;

import javax.annotation.Resource;

/**
 * @author huazai
 * @date 2020/11/25 14:08
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private IUserService userService;

    @GetMapping("/{id}/get")
    public UserEntity getUserById(@PathVariable int id) {
        return userService.getById(id);
    }

    @GetMapping("/{id}/delete")
    public boolean deleteUserById(@PathVariable int id){
        return userService.removeById(id);
    }

    @GetMapping("/{id}/updatePassword")
    public boolean updatePassword(@PathVariable int id,
                                  @RequestParam String password){
        UserEntity updateUser = new UserEntity();
        updateUser.setId(id);
        updateUser.setPassword(password);
        return userService.updateById(updateUser);
    }

    @GetMapping("/create")
    public boolean updatePassword(@RequestParam String name,
                                  @RequestParam String password){
        UserEntity user = new UserEntity();
        user.setUserName(name);
        user.setPassword(password);
        return userService.save(user);
    }
}
