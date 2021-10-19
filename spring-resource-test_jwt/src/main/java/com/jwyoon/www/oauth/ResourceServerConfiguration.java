package com.jwyoon.www.oauth;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.jwyoon.www.common.util.BCUtils;

/**
 * @author user API Server
 */
@Configuration // API �꽌踰� �씤利�, 沅뚰�? �꽕�젙
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    private String[] auths = new String[]{"ROLE_USER", "ROLE_ADMIN", "ROLE_SECOND_ON", "ROLE_SECOND_OFF", "ROLE_PHONE", "ROLE_OTP", "ROLE_EMAIL", "ROLE_ACCOUNT"};

    private AccessDeniedHandler accessDeniedHandler;

    @Resource(name = "dataSource")
    private DataSource dataSource;

    @Bean
    public JwtTokenStore tokenStore() {
        return new MyJwtTokenStore(accessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
    	JwtAccessTokenConverter jwt = new JwtAccessTokenConverter();
    	jwt.setSigningKey("jwt");
    	return jwt;
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        List<String> allowOrigin = new ArrayList<>();
        allowOrigin.add("*");
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(allowOrigin);
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public DefaultTokenServices tokenService() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        defaultTokenServices.setAccessTokenValiditySeconds(1000000);
        defaultTokenServices.setSupportRefreshToken(true);
        return defaultTokenServices;
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.tokenServices(tokenService());
        resources.tokenStore(tokenStore());
    }

    @Bean
    public TokenStore JdbcTokenStore(DataSource dataSource) {
        return new JdbcTokenStore(dataSource);
    }

	/*
	 * private static class OAuthRequestedMatcher implements RequestMatcher { public
	 * boolean matches(HttpServletRequest request) {
	 * System.out.println(BCUtils.nowTime() + "matches"); String auth =
	 * request.getHeader("Authorization"); // Determine if the client request
	 * contained an OAuth Authorization boolean haveOauth2Token = (auth != null) &&
	 * auth.startsWith("Bearer"); boolean haveAccessToken =
	 * request.getParameter("access_token") != null; return haveOauth2Token ||
	 * haveAccessToken; } }
	 */

    @Override
    public void configure(HttpSecurity http) throws Exception {

        System.out.println(BCUtils.nowTime() + "configure(http)");

        http.headers().frameOptions().disable().and().cors().and().csrf().disable().authorizeRequests()
                .antMatchers("/secured/**").permitAll()
                .antMatchers("/public/**").permitAll()
                .antMatchers("/private/**").hasAnyAuthority(auths)
                .antMatchers("/403").permitAll()
                .anyRequest().authenticated();

    }
}