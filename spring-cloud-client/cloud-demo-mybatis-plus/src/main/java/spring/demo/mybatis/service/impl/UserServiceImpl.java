package spring.demo.mybatis.service.impl;

import spring.demo.mybatis.entity.UserEntity;
import spring.demo.mybatis.mapper.UserMapper;
import spring.demo.mybatis.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author huazai
 * @since 2020-11-20
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements IUserService {

}
