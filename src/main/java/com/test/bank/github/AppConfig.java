package com.test.bank.github;

import com.jcabi.github.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Value("${github.username}")
    private String userName;

    @Value("${github.password}")
    private String password;

    @Bean
    public Github github() {
        return new RtGithub(userName, password);
    }

    @Bean
    public User user() {
        return github().users().self();
    }

}
