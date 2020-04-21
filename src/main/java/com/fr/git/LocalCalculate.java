package com.fr.git;

import com.fr.bean.CodeLine;
import com.fr.bean.LocalInfo;
import com.fr.check.CheckService;
import com.fr.git.helper.CookbookHelper;
import com.fr.git.service.DiffService;
import com.fr.git.service.LocalDiffService;
import com.fr.git.service.coverage.CoverageService;
import com.fr.jacoco.parser.FilePathClassNameConverter;
import com.fr.jacoco.report.JacocoXmlCheckService;
import com.fr.utils.TestResourceUtils;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.lib.Repository;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

public class LocalCalculate implements Calculate {

    private LocalInfo localInfo;

    public LocalCalculate(LocalInfo localInfo) {
        this.localInfo = localInfo;
    }

    @Override
    public double calTestCoverage() throws IOException, GitAPIException, URISyntaxException {
        Repository repository = CookbookHelper.openLocalRepository(localInfo.getRepoPath());
        DiffService diffService = new LocalDiffService(localInfo);
        List<DiffEntry> diffList = diffService.getDiffList(repository);

        CoverageService coverageService = new CoverageService();
        CheckService checkService = new JacocoXmlCheckService(TestResourceUtils.getResourceFile("git/simpleCase/build/jacoco"));
        List<CodeLine> codeLineBeanList = coverageService.getCodeLineList(diffList, repository);
        List<CodeLine> collect = codeLineBeanList.stream().map(codeLine -> new CodeLine(new FilePathClassNameConverter(codeLine.getPath()).convert(), codeLine.getLine())).collect(Collectors.toList());
        return coverageService.calTestCoverage(collect, checkService);
    }
}
