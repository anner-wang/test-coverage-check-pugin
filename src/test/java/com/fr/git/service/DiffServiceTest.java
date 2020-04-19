package com.fr.git.service;


import cn.hutool.core.util.StrUtil;
import com.fr.bean.CodeLine;
import com.fr.bean.LocalInfo;
import com.fr.git.service.coverage.CoverageService;
import com.fr.git.service.coverage.RepositoryService;
import com.fr.git.helper.CookbookHelper;
import com.fr.check.CheckService;
import com.fr.check.CheckServiceMocker;
import com.google.common.io.Resources;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.lib.Repository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@RunWith(JUnit4.class)
public class DiffServiceTest {

    @Test
    public void getCodeLineList() throws Exception {
        // 获取到resource仓库的git仓库对象
        String repoPath = Resources.getResource("git-repo-test").getPath();
        String targetRemoteName = "origin";
        String targetBranchName = "master";
        LocalInfo localInfo = new LocalInfo(repoPath, targetRemoteName, targetBranchName);
        Repository repository = CookbookHelper.openGitRepo(StrUtil.format("{}{}.gitDir", repoPath, File.separator));

        DiffService diffService = new LocalDiffService(localInfo);

        List<DiffEntry> diffList = diffService.getDiffList(repository);
        Assert.assertEquals(3, diffList.size());

        CoverageService coverageService = new CoverageService();
        List<CodeLine> codeLineBeanList = coverageService.getCodeLineList(diffList, repository);
        Assert.assertEquals(9, codeLineBeanList.size());

        // http://www.anner.wang:30000/anner-wang/resource/raw/master/image/Snipaste_2020-04-19_10-23-01.png

        Assert.assertEquals(4, codeLineBeanList.get(0).getLine());
        Assert.assertEquals(5, codeLineBeanList.get(1).getLine());
        Assert.assertEquals(6, codeLineBeanList.get(2).getLine());
        Assert.assertEquals(10, codeLineBeanList.get(3).getLine());
        Assert.assertEquals(11, codeLineBeanList.get(4).getLine());
        Assert.assertEquals(12, codeLineBeanList.get(5).getLine());

        Assert.assertEquals(1, codeLineBeanList.get(6).getLine());
        Assert.assertEquals(2, codeLineBeanList.get(7).getLine());

        Assert.assertEquals(1, codeLineBeanList.get(8).getLine());


    }

    @Test
    public void calTestCoverage() {
        CodeLine c1 = new CodeLine("com.fr.test1.java", 6); // 有效 测试到
        CodeLine c2 = new CodeLine("com.fr.test1.java", 7); // 无效 未测试到
        CodeLine c3 = new CodeLine("com.fr.test1.java", 12); // 有效 测试到
        CodeLine c4 = new CodeLine("com.fr.test2.java", 10); // 有效 未测试到
        CodeLine c5 = new CodeLine("com.fr.test3.java", 25); // 无效 未测试到
        CodeLine c6 = new CodeLine("com.fr.test4.java", 28); // 有效 未测试到

        List<CodeLine> codeLineList = new ArrayList<>();

        CheckService checkService = new CheckServiceMocker();
        CoverageService diffService = new CoverageService();

        codeLineList.add(c1);
        Assert.assertEquals(1.0, diffService.calTestCoverage(codeLineList, checkService), 0);

        codeLineList.add(c2);
        Assert.assertEquals(1.0, diffService.calTestCoverage(codeLineList, checkService), 0);

        codeLineList.add(c3);
        Assert.assertEquals(1.0, diffService.calTestCoverage(codeLineList, checkService), 0);

        codeLineList.add(c4);
        Assert.assertEquals(0.666, diffService.calTestCoverage(codeLineList, checkService), 0.1);

        codeLineList.add(c5);
        Assert.assertEquals(0.666, diffService.calTestCoverage(codeLineList, checkService), 0.1);

        codeLineList.add(c6);
        Assert.assertEquals(0.5, diffService.calTestCoverage(codeLineList, checkService), 0);

        codeLineList.clear();
        Assert.assertEquals(0, diffService.calTestCoverage(codeLineList, checkService), 0);
    }
}