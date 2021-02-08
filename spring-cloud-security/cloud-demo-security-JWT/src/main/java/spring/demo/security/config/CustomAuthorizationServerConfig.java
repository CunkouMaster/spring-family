package spring.demo.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import spring.demo.security.service.CustomUserDetailsService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 授权认证服务器配置
 * 配置客户端、token存储方式等
 * @author huazai
 * @date 2021/1/28 13:55
 */
@Configuration
@EnableAuthorizationServer
public class CustomAuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Resource(name = "CustomBCryptPasswordEncoder")
    private PasswordEncoder passwordEncoder;

    @Resource
    private CustomUserDetailsService customUserDetailsService;

    @Resource
    private TokenStore customJwtTokenStore;
    @Resource
    private JwtAccessTokenConverter customJwtAccessTokenConverter;
    @Resource
    private CustomJwtTokenEnhancer customTokenEnhancer;

    @Resource
    private AuthenticationManager authenticationManager;   //认证方式

    /**
     * 用来配置令牌端点(Token Endpoint)的安全约束
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        super.configure(security);
    }

    /**
     * http://127.0.0.1:9012/oauth/authorize?response_type=code&client_id=custom_root&redirect_uri=https://www.baidu.com/
     *
     * 用来配置客户端详情服务（ClientDetailsService），客户端详情信息在这里进行初始化，
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients
                //内存形式，方便测试
                .inMemory()
                //clientId
                .withClient("custom_root")
                //client密码
                .secret(passwordEncoder.encode("root"))
                //令牌失效时间
                .accessTokenValiditySeconds(36000)
                //重定向地址
                .redirectUris("https://www.baidu.com/")
                //授权范围
                .scopes("all")
                //授权类型
                .authorizedGrantTypes("password","refresh_token")
                //数据库对应表 oauth_client_details
//                .withClientDetails(clientDetailsService)
        ;
    }

    /**
     * 配置授权（authorization）以及令牌（token）的访问端点和令牌服务(token services)
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

        // - - - - - 配置JWT自定义申明增强 Starter - - - - -
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        List<TokenEnhancer> tokenEnhancerList = new ArrayList<>();

        tokenEnhancerList.add(customTokenEnhancer);
        //jwt token转化器
        tokenEnhancerList.add(customJwtAccessTokenConverter);

        tokenEnhancerChain.setTokenEnhancers(tokenEnhancerList);
        // - - - - - 配置JWT自定义申明增强 End - - - - -

        endpoints
                //自定义登录逻辑
                .userDetailsService(customUserDetailsService)
                .authenticationManager(authenticationManager)
                //token存储redis
                .tokenStore(customJwtTokenStore)
                .accessTokenConverter(customJwtAccessTokenConverter)
                //token增强链
                .tokenEnhancer(tokenEnhancerChain)
        ;
    }
}
