package com.test.bank.github.service;

import com.test.bank.github.dto.ProjectDTO;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class ProjectService {

    @Value("${repo.path}")
    private String repoPath;

    public File initProject(ProjectDTO projectDTO) {
        File localPath = createProjectFolder(repoPath);
        return cloneRepo(projectDTO.getRemoteUrl(), localPath);
    }

    private File cloneRepo(String remoteUrl, File localPath) {
        File directory;
        try {
            Git result = Git.cloneRepository()
                    .setURI(remoteUrl)
                    .setDirectory(localPath)
                    .call();

            directory = result.getRepository().getDirectory().getAbsoluteFile();
            result.close();
        } catch (GitAPIException e) {
            throw new RuntimeException(e);
        }
        return directory;
    }

    private File createProjectFolder(String path) {
        File localPath = new File(path);

        if (localPath.exists()) {
            try {
                FileUtils.deleteDirectory(localPath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        localPath.mkdirs();

        return localPath;
    }
}
