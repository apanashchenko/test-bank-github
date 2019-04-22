package com.test.bank.github.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jcabi.github.*;
import com.test.bank.github.dto.github.Branch;
import com.test.bank.github.dto.github.GitHubTestCaseDTO;
import com.test.bank.github.response.TestCaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TestCaseService {

    @Autowired
    private ProjectService projectService;

    @Value("${github.baseBranch}")
    private String baseBranch;

    @Value("${github.cases}")
    private String casesFolder;

    @Autowired
    private ObjectMapper objectMapper;

    public String createTestCase(GitHubTestCaseDTO gitHubTestCaseDTO) {
        try {
            String path = gitHubTestCaseDTO.getPath();
            gitHubTestCaseDTO.setPath(casesFolder + path);

            Repo repo = projectService.getRepo(gitHubTestCaseDTO.getRepoName());
            String refJson = repo.git().references().get("refs/heads/" + baseBranch).json().toString();

            Branch branch = objectMapper.readValue(refJson, Branch.class);

            String createBranchRes = repo.git().references()
                    .create("refs/heads/" + gitHubTestCaseDTO.getBranch(),
                            branch.getObject().getSha()).json().toString();

            System.out.println(createBranchRes);

            JsonReader jsonReader = Json.createReader(new StringReader(objectMapper.writeValueAsString(gitHubTestCaseDTO)));
            JsonObject jsonObject = jsonReader.readObject();
            jsonReader.close();

            return repo.contents().create(jsonObject).path();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<TestCaseResponse> getAllCases(String repoName) {
        try {
            List<TestCaseResponse> list = new ArrayList<>();

            projectService.getRepo(repoName).contents()
                    .iterate("/" + casesFolder, baseBranch)
                    .iterator()
                    .forEachRemaining(it -> list.add(extractValue(it)));

            return list.stream().filter(it -> it.getName().endsWith(".yml")).collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private TestCaseResponse extractValue(Content jsonObject) {
        JsonObject json;
        try {
            json = jsonObject.json();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        TestCaseResponse caseResponse = new TestCaseResponse();
        caseResponse.setName(json.getString("name"));
        caseResponse.setContent(json.getString("content"));
        return caseResponse;
    }
}
