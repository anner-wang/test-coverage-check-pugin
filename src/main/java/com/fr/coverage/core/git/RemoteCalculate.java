package com.fr.coverage.core.git;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.fr.coverage.core.bean.CodeLine;
import com.fr.coverage.core.bean.RemoteInfo;
import com.fr.coverage.core.check.CheckService;
import com.fr.coverage.core.constants.Local;
import com.fr.coverage.core.git.helper.CookbookHelper;
import com.fr.coverage.core.git.service.DiffService;
import com.fr.coverage.core.git.service.RemoteDiffService;
import com.fr.coverage.core.git.service.coverage.CoverageService;
import com.fr.coverage.core.git.sync.CodeSync;
import com.fr.coverage.core.jacoco.parser.FilePathConverter;
import com.fr.coverage.core.jacoco.report.JacocoXmlCheckService;
import com.fr.coverage.core.json.bean.UncoveredJsonInfo;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.lib.Repository;

import java.io.File;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class RemoteCalculate implements Calculate {
    private RemoteInfo from;
    private RemoteInfo to;

    public RemoteCalculate(RemoteInfo from, RemoteInfo to) {
        this.from = from;
        this.to = to;
    }


    @Override
    public UncoveredJsonInfo getUncoveredJsonInfo() throws Exception {
        String repoPath = StrUtil.format("{}{}{}", Local.BASE_PATH.getValue(), File.separator, to.getRepoName());
        if (!FileUtil.exist(repoPath)) {
            CodeSync codeSync = new CodeSync();
            codeSync.initRepo(to);
        }
        Repository repository = CookbookHelper.openToBranchRepository(repoPath, to.getBranchName());
        DiffService diffService = new RemoteDiffService(from, to);
        List<DiffEntry> diffEntryList = diffService.getDiffList(repository);

        if (diffEntryList == null) {
            throw new NoSuchElementException("The different code obtained is empty, and the program ends");
        }

        CoverageService coverageService = new CoverageService();
        String localPosition = StrUtil.format("{}{}{}{}{}", Local.BASE_PATH.getValue(), File.separator,
                to.getRepoName(), File.separator, to.getBranchName());
        CheckService checkService = new JacocoXmlCheckService(FileUtil.file(StrUtil.format("{}{}build{}jacoco",
                localPosition, File.separator, File.separator)));

        List<CodeLine> codeLineBeanList = coverageService.getCodeLineList(diffEntryList, repository);
        List<CodeLine> collect = codeLineBeanList.stream().map(codeLine -> new CodeLine(new FilePathConverter(codeLine.getPath()).convert(), codeLine.getLine())).collect(Collectors.toList());
        return coverageService.generateCoverageJsonObj(collect, checkService);
    }
}
