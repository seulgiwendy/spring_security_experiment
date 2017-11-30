package com.example.security.configuration;

import com.example.security.model.SecurityAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.Filter;

@Configuration
@EnableOAuth2Client
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);

    @Autowired
    AccountDetailsService accountDetailsService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Qualifier("oauth2ClientContext")
    @Autowired
    OAuth2ClientContext oAuth2ClientContext;

//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//                .inMemoryAuthentication()
//                    .withUser("wheejuni").password("1234").roles("ADMIN");
//    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(accountDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                .authorizeRequests()
                    .antMatchers("/admin/**").hasAuthority("ROLE_MANAGER")
                    .antMatchers("/userp/**").hasAuthority("ROLE_USER")
                    .antMatchers("/**").permitAll()
                    .antMatchers("/login/**").permitAll()
                    .and()
                .formLogin()
                    .loginPage("/loginForm")
                    .loginProcessingUrl("/login")
                    .successHandler(successHandler())
                    .permitAll()
                .and()
                    .addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class);


        httpSecurity
                .csrf().disable();

        httpSecurity
                .headers().frameOptions().disable();

        httpSecurity
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/");

    }

    @Bean
    public FilterRegistrationBean oauth2ClientFilterRegistration(OAuth2ClientContextFilter filter) {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(filter);
        registration.setOrder(-100);
        return registration;
    }

    @Bean
    public AuthenticationSuccessHandler successHandler()
    {
        return new CustomLoginSuccessHandler("/");
    }

    private Filter ssoFilter() {
        OAuth2ClientAuthenticationProcessingFilter githubFilter = new OAuth2ClientAuthenticationProcessingFilter("/login/github");
        OAuth2RestTemplate githubTemplate = new OAuth2RestTemplate(github(), oAuth2ClientContext);
        githubFilter.setRestTemplate(githubTemplate);
        log.error(githubResources().getUserInfoUri());
        UserInfoTokenServices tokenServices = new UserInfoTokenServices(githubResources().getUserInfoUri(), github().getClientId());
        tokenServices.setRestTemplate(githubTemplate);
        githubFilter.setTokenServices(tokenServices);
        githubFilter.setApplicationEventPublisher(this.applicationEventPublisher);
        return githubFilter;
    }


    @Bean
    @ConfigurationProperties("github.client")
    public AuthorizationCodeResourceDetails github() {
        return new AuthorizationCodeResourceDetails();
    }

    @Bean
    @ConfigurationProperties("github.resource")
    public ResourceServerProperties githubResources() {
        return new ResourceServerProperties();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
