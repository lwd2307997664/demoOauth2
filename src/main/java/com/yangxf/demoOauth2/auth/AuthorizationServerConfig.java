/**
 * FileName: AuthorizationServerConfig
 * Author:   linwd
 * Date:     2021/3/31 12:01
 * Description: 授权服务器配置
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.yangxf.demoOauth2.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * 〈一句话功能简述〉<br>
 * 〈授权服务器配置〉
 *
 * @author linwd
 * @create 2021/3/31
 * @since 1.0.0
 */
@Configuration
@EnableAuthorizationServer //开启授权服务器
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    /**
     * 用于支持password模式
     * 如启动时报AuthenticationManager无法注入的错误，可能是spring security配置类中没有配置这个
     *    @Bean
     *     @Override
     *     public AuthenticationManager authenticationManagerBean() throws Exception {
     *         return super.authenticationManagerBean();
     *     }
     */
    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * 调用redis的，将令牌缓存到redis中，以便微服务之间获取信息
     */
    @Autowired
    RedisConnectionFactory redisConnectionFactory;

    /**
     * 该对象用于刷新token提供支持,
     * 如启动时报UserDetailsService注入错误，可能是spring security配置类中没有配置这个
     *     @Bean
     *     @Override
     *     public UserDetailsService userDetailsService(){
     *         return super.userDetailsService();
     *     }
     */
    @Autowired
    UserDetailsService userDetailsService;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * authorizedGrantTypes--授权模式为password，refresh_token
     * accessTokenValiditySeconds--配置了过期时间
     * resourceIds--配置了资源id
     * secret--配置了加密后的密码
     *
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("password")
                .authorizedGrantTypes("password", "refresh_token")
                .accessTokenValiditySeconds(1800)
                .resourceIds("rids")
                .scopes("all")
                .secret(passwordEncoder().encode("123456"));
    }

    /**
     * 令牌的存储，用于支持password模式以及令牌刷新
     *
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(new RedisTokenStore(redisConnectionFactory))
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService);
    }

    /**
     * 支持client_id和client_secret做登录认证
     *
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.allowFormAuthenticationForClients();
    }

}
