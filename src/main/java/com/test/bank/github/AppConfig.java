package com.test.bank.github;

import com.jcabi.github.Coordinates;
import com.jcabi.github.Github;
import com.jcabi.github.Repo;
import com.jcabi.github.RtGithub;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Value("${github.username}")
    private String userName;

    @Value("${github.password}")
    private String password;

    @Value("${github.repoName}")
    private String repoName;

    @Bean
    public Github github() {
        return new RtGithub(userName, password);
    }

    @Bean
    public Repo repo() {
        return github().repos().get(new Coordinates.Simple(repoName));
    }

}
