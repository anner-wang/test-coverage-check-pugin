package com.fr.coverage.git.service;


import cn.hutool.core.util.StrUtil;
import com.fr.coverage.core.bean.CodeLine;
import com.fr.coverage.core.bean.LocalInfo;
import com.fr.coverage.core.check.CheckService;
import com.fr.coverage.core.check.CheckServiceMocker;
import com.fr.coverage.core.check.MemoryMapCheckService;
import com.fr.coverage.core.git.helper.CookbookHelper;
import com.fr.coverage.core.git.service.DiffService;
import com.fr.coverage.core.git.service.LocalDiffService;
import com.fr.coverage.core.git.service.coverage.CoverageService;
import com.fr.coverage.core.json.bean.UncoveredJsonInfo;
import com.fr.coverage.core.utils.TestResourceUtils;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.lib.Repository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(JUnit4.class)
public class CoverageServiceTest {

    @Test
    public void getCodeLineList() throws Exception {
        // 获取到resource仓库的git仓库对象
        String repoPath = TestResourceUtils.getResourceFile("git-repo-test").getAbsolutePath();
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
        Assert.assertEquals(1.0, diffService.generateCoverageJsonObj(codeLineList, checkService).getCoverage(), 0);

        codeLineList.add(c2);
        Assert.assertEquals(1.0, diffService.generateCoverageJsonObj(codeLineList, checkService).getCoverage(), 0);

        codeLineList.add(c3);
        Assert.assertEquals(1.0, diffService.generateCoverageJsonObj(codeLineList, checkService).getCoverage(), 0);

        codeLineList.add(c4);
        Assert.assertEquals(0.666, diffService.generateCoverageJsonObj(codeLineList, checkService).getCoverage(), 0.1);

        codeLineList.add(c5);
        Assert.assertEquals(0.666, diffService.generateCoverageJsonObj(codeLineList, checkService).getCoverage(), 0.1);

        codeLineList.add(c6);
        Assert.assertEquals(0.5, diffService.generateCoverageJsonObj(codeLineList, checkService).getCoverage(), 0);

        codeLineList.clear();
        Assert.assertEquals(0, diffService.generateCoverageJsonObj(codeLineList, checkService).getCoverage(), 0);
    }

    @Test
    public void testJsonInfo() {


        CodeLine c1 = new CodeLine("com.fr.test1.java", 6);
        CodeLine c2 = new CodeLine("com.fr.test1.java", 7);
        CodeLine c3 = new CodeLine("com.fr.test1.java", 8);

        CodeLine c4 = new CodeLine("com.fr.test2.java", 10);

        CodeLine c5 = new CodeLine("com.fr.test3.java", 25);

        CodeLine c6 = new CodeLine("com.fr.test4.java", 28);
        CodeLine c7 = new CodeLine("com.fr.test4.java", 33);
        CodeLine c8 = new CodeLine("com.fr.test4.java", 34);
        CodeLine c9 = new CodeLine("com.fr.test4.java", 35);

        List<CodeLine> codeLineList = new ArrayList<>();

        Map<CodeLine, Boolean> map = new HashMap<CodeLine, Boolean>() {{
            put(c1, true);
            put(c2, false);
            put(c3, false);

            put(c4, false);

            put(c5, true);

            put(c6, false);
            put(c7, false);
            put(c8, false);
            put(c9, false);
        }};
        CheckService checkService = new MemoryMapCheckService(map);

        codeLineList.add(c1);
        codeLineList.add(c2);
        codeLineList.add(c3);
        codeLineList.add(c4);
        CoverageService coverageService = new CoverageService();
        UncoveredJsonInfo uncoveredJsonInfo = coverageService.generateCoverageJsonObj(codeLineList, checkService);
        Assert.assertEquals(2, uncoveredJsonInfo.getDetail().size());
        Assert.assertEquals("7-8", uncoveredJsonInfo.getDetail().get("com.fr.test1.java"));
        Assert.assertEquals("10", uncoveredJsonInfo.getDetail().get("com.fr.test2.java"));
        codeLineList.add(c5);
        codeLineList.add(c6);
        codeLineList.add(c7);
        codeLineList.add(c8);
        codeLineList.add(c9);
        coverageService = new CoverageService();
        uncoveredJsonInfo = coverageService.generateCoverageJsonObj(codeLineList, checkService);
        Assert.assertEquals(3, uncoveredJsonInfo.getDetail().size());
        Assert.assertEquals("7-8", uncoveredJsonInfo.getDetail().get("com.fr.test1.java"));
        Assert.assertEquals("10", uncoveredJsonInfo.getDetail().get("com.fr.test2.java"));
        Assert.assertEquals("28,33-35", uncoveredJsonInfo.getDetail().get("com.fr.test4.java"));
    }
}