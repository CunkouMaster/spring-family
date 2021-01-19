package spring.demo.security.entity;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author huazai
 * @since 2020-11-20
 */
public class UserEntity{

    private static final long serialVersionUID = 1L;

    private Integer id;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机
     */
    private String phone;

    /**
     * 是否是管理员 0：不是，1：是
     */
    private Integer adminFlag;

    /**
     * 状态 0：正常，1：冻结
     */
    private Integer state;


}
