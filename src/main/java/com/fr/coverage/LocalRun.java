package com.fr.coverage;

import com.fr.coverage.bean.LocalInfo;
import com.fr.coverage.git.Calculate;
import com.fr.coverage.git.LocalCalculate;
import com.fr.coverage.git.helper.Converter;
import com.fr.coverage.gradle.GradleProject;

public class LocalRun {
    /**
     * 接收的参数说明：
     * 1. 本地的仓库目录
     * 2. 希望提交的主仓库的remote的名字
     * 3. 希望提交的主仓库的分支
     *
     * @param args
     * @throws Exception
     */

    public static void main(String[] args) throws Exception {
        LocalInfo localInfo = Converter.Args2LocalInfo(args);
        GradleProject gradleProject = new GradleProject(localInfo.getRepoPath());
        gradleProject.runTasks("jacocoTestReport");
        Calculate calculate = new LocalCalculate(localInfo);
        System.out.println("local code test coverage is " + calculate.calTestCoverage());
    }
}
