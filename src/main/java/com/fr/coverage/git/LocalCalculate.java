package com.fr.coverage.git;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.fr.coverage.bean.CodeLine;
import com.fr.coverage.bean.LocalInfo;
import com.fr.coverage.check.CheckService;
import com.fr.coverage.git.Calculate;
import com.fr.coverage.git.helper.CookbookHelper;
import com.fr.coverage.git.service.DiffService;
import com.fr.coverage.git.service.LocalDiffService;
import com.fr.coverage.git.service.coverage.CoverageService;
import com.fr.coverage.jacoco.parser.FilePathClassNameConverter;
import com.fr.coverage.jacoco.report.JacocoXmlCheckService;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.lib.Repository;

import java.io.File;
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
        Repository repository = CookbookHelper.openLocalRepository(localInfo);
        DiffService diffService = new LocalDiffService(localInfo);
        List<DiffEntry> diffList = diffService.getDiffList(repository);

        CoverageService coverageService = new CoverageService();
        CheckService checkService = new JacocoXmlCheckService(FileUtil.file(StrUtil.format("{}{}build{}jacoco",
                localInfo.getRepoPath(), File.separator, File.separator)));
        List<CodeLine> codeLineBeanList = coverageService.getCodeLineList(diffList, repository);
        List<CodeLine> collect = codeLineBeanList.stream().map(codeLine ->
                new CodeLine(new FilePathClassNameConverter(codeLine.getPath()).convert(), codeLine.getLine())).collect(Collectors.toList());
        return coverageService.calTestCoverage(collect, checkService);
    }
}
