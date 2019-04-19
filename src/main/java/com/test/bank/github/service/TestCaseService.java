package com.test.bank.github.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jcabi.github.Coordinates;
import com.jcabi.github.Github;
import com.jcabi.github.Repo;
import com.jcabi.github.RtGithub;
import com.test.bank.github.dto.Branch;
import com.test.bank.github.dto.Committer;
import com.test.bank.github.dto.GitHubTestCase;
import com.test.bank.github.dto.TestCaseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Base64;

@Service
public class TestCaseService {

    @Value("${github.username}")
    private String userName;

    @Value("${github.password}")
    private String password;

    @Value("${github.baseBranch}")
    private String baseBranch;

    @Value("${github.repoName}")
    private String repoName;

    public void createTestCase(TestCaseDTO testCaseDTO) {
        Github github = new RtGithub(userName, password);
        Repo gitHubRepo = github.repos().get(new Coordinates.Simple(repoName));

        ObjectMapper mapper = new ObjectMapper();

        try {
            String refJson = gitHubRepo.git().references().get("refs/heads/" + baseBranch).json().toString();
            Branch branch = mapper.readValue(refJson, Branch.class);

            String createBranchRes = gitHubRepo.git().references().create("refs/heads/" + testCaseDTO.getBranch(),
                    branch.getObject().getSha()).json().toString();
            System.out.println(createBranchRes);

            GitHubTestCase testCaseGitHub = new GitHubTestCase();
            testCaseGitHub.setMessage(testCaseDTO.getTitle());
            testCaseGitHub.setBranch(testCaseDTO.getBranch());
            testCaseGitHub.setPath(testCaseDTO.getFileName() + ".json");

            Committer committer = new Committer();
            committer.setName(testCaseDTO.getUserName());
            committer.setEmail(testCaseDTO.getEmail());

            testCaseGitHub.setCommitter(committer);

            testCaseGitHub.setContent(Base64.getEncoder().encodeToString(mapper.writeValueAsString(testCaseDTO).getBytes()));

            JsonReader jsonReader = Json.createReader(new StringReader(mapper.writeValueAsString(testCaseGitHub)));
            JsonObject jsonObject = jsonReader.readObject();
            jsonReader.close();

            gitHubRepo.contents().create(jsonObject);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
