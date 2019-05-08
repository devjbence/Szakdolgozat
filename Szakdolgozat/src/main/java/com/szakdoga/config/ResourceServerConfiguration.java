package com.szakdoga.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

	@Value("${resource_id}")
	private String RESOURCE_ID;

	
	@Value("${spring.datasource.driverClassName}")
    private String oauthClass;

    @Value("${spring.datasource.url}")
    private String oauthUrl;

    //https://stackoverflow.com/questions/36904178/how-to-persist-oauth-access-tokens-in-spring-security-jdbc/37084761
    @Bean
    public TokenStore tokenStore() {
        DataSource tokenDataSource = DataSourceBuilder.create()
                        .driverClassName(oauthClass)
                        .username("szakdoga")
                        .password("szakdoga1234")
                        .url(oauthUrl)
                        .build();
        return new JdbcTokenStore(tokenDataSource);
    }
	
	
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.resourceId(RESOURCE_ID);
		//.tokenStore(tokenStore());
	}
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.cors().and()
		.authorizeRequests()
		.antMatchers("/user/logout")
		.authenticated()
		.antMatchers(HttpMethod.DELETE,"/user/**")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.DELETE,"/user")
		.hasRole("ADMIN")
		//users
		.antMatchers(HttpMethod.GET,"/users")
		.permitAll()
		.antMatchers(HttpMethod.GET,"/users/?/roles")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.GET,"/users/?/roles/**")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.POST,"/users")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.POST,"/users/**")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.PUT,"/users")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.PUT,"/users/**")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.DELETE,"/users")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.DELETE,"/users/**")
		.hasRole("ADMIN")
		//user
		.antMatchers(HttpMethod.GET,"/user/activation/**")
		.permitAll()
		.antMatchers(HttpMethod.GET,"/user/**")
		.authenticated()
		.antMatchers(HttpMethod.POST,"/user/changePassword")
		.authenticated()
		.antMatchers(HttpMethod.PUT,"/user/**")
		.authenticated()
		.antMatchers(HttpMethod.DELETE,"/user")
		.denyAll()
		.antMatchers(HttpMethod.DELETE,"/user/**")
		.denyAll()
		//buyers
		.antMatchers(HttpMethod.POST,"/buyers")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.POST,"/buyers/**")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.PUT,"/buyers")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.PUT,"/buyers/**")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.DELETE,"/buyers")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.DELETE,"/buyers/**")
		.hasRole("ADMIN")
		//buyer
		.antMatchers(HttpMethod.POST,"/buyer/**")
		.authenticated()
		.antMatchers(HttpMethod.PUT,"/buyer/**")
		.authenticated()
		.antMatchers(HttpMethod.DELETE,"/buyer/**")
		.authenticated()
		//sellers
		.antMatchers(HttpMethod.POST,"/sellers")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.POST,"/sellers/**")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.PUT,"/sellers")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.PUT,"/sellers/**")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.DELETE,"/sellers")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.DELETE,"/sellers/**")
		.hasRole("ADMIN")
		//seller
		.antMatchers(HttpMethod.POST,"/seller/**")
		.authenticated()
		.antMatchers(HttpMethod.PUT,"/seller/**")
		.authenticated()
		.antMatchers(HttpMethod.DELETE,"/seller/**")
		.authenticated()
		//products
		.antMatchers(HttpMethod.POST,"/products")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.POST,"/products/**")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.PUT,"/products")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.PUT,"/products/**")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.DELETE,"/products")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.DELETE,"/products/**")
		.hasRole("ADMIN")
		//product
		.antMatchers(HttpMethod.POST,"/product/**")
		.authenticated()
		.antMatchers(HttpMethod.PUT,"/product/**")
		.authenticated()
		.antMatchers(HttpMethod.DELETE,"/product/**")
		.authenticated()
		//role
		.antMatchers(HttpMethod.GET,"/roles")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.GET,"/roles/**")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.POST,"/roles")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.POST,"/roles/**")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.PUT,"/roles")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.PUT,"/roles/**")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.DELETE,"/roles")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.DELETE,"/roles/**")
		.hasRole("ADMIN")
		
		//useractivation
		.antMatchers(HttpMethod.GET,"/userActivations")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.GET,"/userActivations/**")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.POST,"/userActivations")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.POST,"/userActivations/**")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.PUT,"/userActivations")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.PUT,"/userActivations/**")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.DELETE,"/userActivations")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.DELETE,"/userActivations/**")
		.hasRole("ADMIN")
		//accestokenentity
		.antMatchers(HttpMethod.GET,"/accessTokenEntities")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.GET,"/accessTokenEntities/**")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.POST,"/accessTokenEntities")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.POST,"/accessTokenEntities/**")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.PUT,"/accessTokenEntities")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.PUT,"/accessTokenEntities/**")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.DELETE,"/accessTokenEntities")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.DELETE,"/accessTokenEntities/**")
		.hasRole("ADMIN")
		//refreshtokenentity
		.antMatchers(HttpMethod.GET,"/refreshTokenEntities")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.GET,"/refreshTokenEntities/**")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.POST,"/refreshTokenEntities")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.POST,"/refreshTokenEntities/**")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.PUT,"/refreshTokenEntities")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.PUT,"/refreshTokenEntities/**")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.DELETE,"/refreshTokenEntities")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.DELETE,"/refreshTokenEntities/**")
		.hasRole("ADMIN")
		//productcategory
		.antMatchers(HttpMethod.POST,"/productCategories")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.POST,"/productCategories/**")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.PUT,"/productCategories")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.PUT,"/productCategories/**")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.DELETE,"/productCategories")
		.hasRole("ADMIN")
		.antMatchers(HttpMethod.DELETE,"/productCategories/**")
		.hasRole("ADMIN");
		//.and()
		//.rememberMe();
	}

}
