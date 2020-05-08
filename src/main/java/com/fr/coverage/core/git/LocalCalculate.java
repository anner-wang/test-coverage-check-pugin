package com.fr.coverage.core.git;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.fr.coverage.core.bean.CodeLine;
import com.fr.coverage.core.bean.LocalInfo;
import com.fr.coverage.core.check.CheckService;
import com.fr.coverage.core.git.helper.CookbookHelper;
import com.fr.coverage.core.git.service.DiffService;
import com.fr.coverage.core.git.service.LocalDiffService;
import com.fr.coverage.core.git.service.coverage.CoverageService;
import com.fr.coverage.core.jacoco.parser.FilePathConverter;
import com.fr.coverage.core.jacoco.report.JacocoXmlCheckService;
import com.fr.coverage.core.json.bean.UncoveredJsonInfo;
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
    public UncoveredJsonInfo getUncoveredJsonInfo() throws IOException, GitAPIException, URISyntaxException {
        Repository repository = CookbookHelper.openLocalRepository(localInfo);
        DiffService diffService = new LocalDiffService(localInfo);
        List<DiffEntry> diffList = diffService.getDiffList(repository);

        CoverageService coverageService = new CoverageService();
        CheckService checkService = new JacocoXmlCheckService(FileUtil.file(StrUtil.format("{}{}build{}jacoco",
                localInfo.getRepoPath(), File.separator, File.separator)));
        List<CodeLine> codeLineBeanList = coverageService.getCodeLineList(diffList, repository);
        List<CodeLine> collect = codeLineBeanList.stream().map(codeLine ->
                new CodeLine(new FilePathConverter(codeLine.getPath()).convert(), codeLine.getLine())).collect(Collectors.toList());
        return coverageService.generateCoverageJsonObj(collect, checkService);
    }
}
