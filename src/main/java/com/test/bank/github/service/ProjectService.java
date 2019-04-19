package com.test.bank.github.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jcabi.github.Coordinates;
import com.jcabi.github.Github;
import com.jcabi.github.Repo;
import com.jcabi.github.Repos;
import com.test.bank.github.dto.Committer;
import com.test.bank.github.dto.GitHubTestCase;
import com.test.bank.github.dto.RepoDTO;
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
    private Repo repo;

    @Value("${github.baseBranch}")
    private String baseBranch;

    @Value("${github.repoName}")
    private String repoName;

    @Value("${github.cases}")
    private String casesFolder;

    public Repo getProject() {
        return github.repos().get(new Coordinates.Simple(repoName));
    }

    public JsonObject initProject(RepoDTO repoDTO) {
        Repos.RepoCreate settings = new Repos.RepoCreate(repoName.split("/")[1], repoDTO.isPrivate())
                .withAutoInit(true)
                .withDescription(repoDTO.getDescription());
        try {
            JsonObject json =  github.repos().create(settings).json();
            GitHubTestCase testCaseGitHub = new GitHubTestCase();
            testCaseGitHub.setMessage("Init cases repo");
            testCaseGitHub.setBranch(baseBranch);
            testCaseGitHub.setPath(casesFolder + "README.md");

            Committer committer = new Committer();
            committer.setName("Test bank");
            committer.setEmail("test@email.com");

            testCaseGitHub.setCommitter(committer);

            ObjectMapper mapper = new ObjectMapper();

            testCaseGitHub.setContent(Base64.getEncoder().encodeToString("Test case storage folder".getBytes()));

            JsonReader jsonReader = Json.createReader(new StringReader(mapper.writeValueAsString(testCaseGitHub)));
            JsonObject jsonObject = jsonReader.readObject();
            jsonReader.close();

            repo.contents().create(jsonObject);
//            TODO change return type because of to many info in response
            return json;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
