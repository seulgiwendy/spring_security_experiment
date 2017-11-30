package com.example.security.configuration;

import com.example.security.model.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.FixedPrincipalExtractor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Component
public class AuthenticationEventListener {

    private static final Logger log = LoggerFactory.getLogger(AuthenticationEventListener.class);

    private final PrincipalExtractor principalExtractor = new FixedPrincipalExtractor();

    @Resource(name = "accountDetailsService")
    private AccountDetailsService accountDetailsService;

    @EventListener
    public void handleAuthenticationSuccess(InteractiveAuthenticationSuccessEvent event) throws IOException {
        if (!(event.getAuthentication() instanceof OAuth2Authentication)) {
            log.error("shit! this is not an OAuth2 Authentication!");
            return ;
        }


        OAuth2Authentication authentication = (OAuth2Authentication)event.getAuthentication();
        authentication.getPrincipal();

        Map<String, Object> map = (Map<String, Object>) authentication.getUserAuthentication().getDetails();
        log.debug("user details are as follows : {}" , map.toString());
        System.out.println(String.format("user details are as follows : %s", map.toString()));

        UserDetails userDetails = getUser(map);

    }

    private UserDetails getUser(Map<String, Object> map) {
        String username = principalExtractor.extractPrincipal(map).toString();
        log.debug("username is : {}" , username);
        System.out.println(String.format("username is : %s" , username));

        UserDetails user = null;
        try {
            user = this.accountDetailsService.loadUserByUsername(username);
        } catch(UsernameNotFoundException e) {
            this.accountDetailsService.saveNewAccountFromGitHub(map);
            log.error("user not found on DB: {}" , username);
        }
        return user;
    }

}
