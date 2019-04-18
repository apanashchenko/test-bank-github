package com.test.bank.github.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.bank.github.dto.TestCaseDTO;
import com.test.bank.github.payload.CreateTestCasePayload;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.*;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.transport.PushResult;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class TestCaseService {

    @Value("${repo.path}")
    private String repoPath;

    @Value("${github.username}")
    private String userName;

    @Value("${github.password}")
    private String password;

    @Value("${github.baseBranch}")
    private String baseBranch;

    public void createTestCase(CreateTestCasePayload createTestCasePayload) {
        final File repo = new File(repoPath + "/.git");

        final String fileName = createTestCasePayload.getFileName();

        try {
            Git git = Git.open(repo);

            git.checkout()
                    .setName(baseBranch)
                    .call();

            git.checkout()
                    .setCreateBranch(true)
                    .setName(createTestCasePayload.getBranchName())
                    .call();


            ObjectMapper mapper = new ObjectMapper();

            File jsonFile = new File(git.getRepository().getDirectory().getParent(), fileName);
            mapper.writeValue(jsonFile, createTestCasePayload.getTestCaseDTO());

            git.add().addFilepattern(fileName).call();

            git.commit().setMessage(fileName + " added").call();

            Iterable<PushResult> results = git.push().setCredentialsProvider(new UsernamePasswordCredentialsProvider(userName, password)).call();

            results.forEach(System.out::println);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
