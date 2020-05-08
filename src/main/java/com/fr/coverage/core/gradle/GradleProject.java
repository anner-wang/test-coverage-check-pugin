package com.fr.coverage.core.gradle;

import org.gradle.tooling.GradleConnector;
import org.gradle.tooling.ProjectConnection;

import java.io.File;

/**
 * @author Hiram
 * @version 5.1.2
 * Created by Hiram on 2020/4/21
 */
public class GradleProject {

    private String projectPath;

    public GradleProject(String projectPath) {
        this.projectPath = projectPath;
    }


    public void runTasks(String... tasks) {
        ProjectConnection connection = openConnect(projectPath);
        try {
            connection.newBuild().forTasks(tasks).run();
        } finally {
            connection.close();
        }
    }

    private ProjectConnection openConnect(String projectPath) {
        return GradleConnector.newConnector()
                .forProjectDirectory(new File(projectPath))
                .connect();
    }

}

