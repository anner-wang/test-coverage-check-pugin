package com.fr.coverage.git;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.fr.coverage.core.bean.CodeLine;
import com.fr.coverage.core.bean.LocalInfo;
import com.fr.coverage.core.check.CheckService;
import com.fr.coverage.core.git.LocalCalculate;
import com.fr.coverage.core.git.helper.CookbookHelper;
import com.fr.coverage.core.git.service.DiffService;
import com.fr.coverage.core.git.service.LocalDiffService;
import com.fr.coverage.core.git.service.coverage.CoverageService;
import com.fr.coverage.core.jacoco.parser.FilePathConverter;
import com.fr.coverage.core.jacoco.report.JacocoXmlCheckService;
import com.fr.coverage.core.utils.TestResourceUtils;
import junit.framework.TestCase;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.lib.Repository;
import org.junit.Assert;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;


public class LocalCalculateTest extends TestCase {

    public void testCalTestCoverage() throws GitAPIException, IOException, URISyntaxException {
        String gitPath = TestResourceUtils.getResourceFile("git/simpleCase").getAbsolutePath();
        String remoteName = "origin";
        String branchName = "master_case1";
        LocalInfo localInfo = new LocalInfo(gitPath, remoteName, branchName, "gitDir");
        LocalCalculate calculate = new LocalCalculate(localInfo);

        Repository repository = CookbookHelper.openLocalRepository(localInfo);
        Assert.assertNotNull(repository);
        Assert.assertNotNull(repository.getBranch());
        Assert.assertEquals(repository.getDirectory().getParent(), gitPath);

        DiffService diffService = new LocalDiffService(localInfo);
        List<DiffEntry> diffList = diffService.getDiffList(repository);

        CoverageService coverageService = new CoverageService();
        CheckService checkService = new JacocoXmlCheckService(FileUtil.file(StrUtil.format("{}{}build{}jacoco",
                localInfo.getRepoPath(), File.separator, File.separator)));
        List<CodeLine> codeLineBeanList = coverageService.getCodeLineList(diffList, repository);
        List<CodeLine> collect = codeLineBeanList.stream().map(codeLine ->
                new CodeLine(new FilePathConverter(codeLine.getPath()).convert(), codeLine.getLine())).collect(Collectors.toList());

        Assert.assertTrue(codeLineBeanList.size() >= diffList.size());

        double res = calculate.getUncoveredJsonInfo().getCoverage();
        Assert.assertTrue(res >= 0 && res <= 1);
    }
}