package com.test.bank.github.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Iterables;
import com.jcabi.github.*;
import com.test.bank.github.dto.*;
import com.test.bank.github.dto.github.Committer;
import com.test.bank.github.dto.github.GitHubTestCase;
import com.test.bank.github.dto.github.GitHubUser;
import com.test.bank.github.response.InitProjectResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Base64;


@Service
public class ProjectService {

    @Autowired
    private Github github;

    @Autowired
    private User user;

    @Value("${github.baseBranch}")
    private String baseBranch;

    @Value("${github.cases}")
    private String casesFolder;

    @Autowired
    private ObjectMapper objectMapper;

    public Repo getRepo(String repoName) {
        try {
            return github.repos().get(new Coordinates.Simple(user.login() + "/" +  repoName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public InitProjectResponse initProject(ProjectDTO projectDTO) {
        try {
            String repoName = projectDTO.getName().toLowerCase().replace(" ", "-");
            Repos.RepoCreate settings = new Repos.RepoCreate(repoName, projectDTO.isPrivate())
                    .withAutoInit(true)
                    .withDescription(projectDTO.getDescription());

            JsonObject json = github.repos().create(settings).json();
            GitHubTestCase testCaseGitHub = new GitHubTestCase();
            testCaseGitHub.setMessage("Init cases repo");
            testCaseGitHub.setBranch(baseBranch);
            testCaseGitHub.setPath(casesFolder + "README.md");

            GitHubUser gitHubUser = getGitHubUser();

            Committer committer = new Committer();
            committer.setName(gitHubUser.getName());
            committer.setEmail(gitHubUser.getEmail());

            testCaseGitHub.setCommitter(committer);
            testCaseGitHub.setContent(Base64.getEncoder().encodeToString("Test case storage folder".getBytes()));

            JsonReader jsonReader = Json.createReader(new StringReader(objectMapper.writeValueAsString(testCaseGitHub)));
            JsonObject jsonObject = jsonReader.readObject();
            jsonReader.close();

            JsonObject response = getRepo(repoName).contents().create(jsonObject).json();

            InitProjectResponse initProjectResponse = new InitProjectResponse();
            initProjectResponse.setName(json.getString("name"));

            return initProjectResponse;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private GitHubUser getGitHubUser() throws IOException {
        GitHubUser gitHubUser = objectMapper.readValue(user.json().toString(), GitHubUser.class);
        UserEmails emails = user.emails();
        String activeEmail = Iterables.getFirst(emails.iterate(), "");
        gitHubUser.setEmail(activeEmail);
        return gitHubUser;
    }

}
