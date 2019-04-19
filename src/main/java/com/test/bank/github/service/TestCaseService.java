package com.test.bank.github.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jcabi.github.*;
import com.test.bank.github.dto.Branch;
import com.test.bank.github.dto.Committer;
import com.test.bank.github.dto.GitHubTestCase;
import com.test.bank.github.dto.TestCaseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;

@Service
public class TestCaseService {

    @Autowired
    private Github github;

    @Autowired
    private Repo gitHubRepo;

    @Value("${github.baseBranch}")
    private String baseBranch;

    @Value("${github.repoName}")
    private String repoName;

    @Value("${github.cases}")
    private String casesFolder;


    public Content createTestCase(TestCaseDTO testCaseDTO) {
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
            testCaseGitHub.setPath(casesFolder + testCaseDTO.getFileName() + ".json");

            Committer committer = new Committer();
            committer.setName(testCaseDTO.getUserName());
            committer.setEmail(testCaseDTO.getEmail());

            testCaseGitHub.setCommitter(committer);

            testCaseGitHub.setContent(Base64.getEncoder().encodeToString(mapper.writeValueAsString(testCaseDTO).getBytes()));

            JsonReader jsonReader = Json.createReader(new StringReader(mapper.writeValueAsString(testCaseGitHub)));
            JsonObject jsonObject = jsonReader.readObject();
            jsonReader.close();

            return gitHubRepo.contents().create(jsonObject);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<JsonObject> getAllCases() {
        try {
            List<JsonObject> jsonObjectList = new ArrayList<>();

            Iterator<Content> iterator = gitHubRepo.contents()
                    .iterate("/" + casesFolder, "master").iterator();

            iterator.forEachRemaining(it -> {
                try {
                    jsonObjectList.add(it.json());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            return jsonObjectList;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
