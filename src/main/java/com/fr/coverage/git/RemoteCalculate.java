package com.fr.coverage.git;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.log.StaticLog;
import com.fr.coverage.bean.CodeLine;
import com.fr.coverage.bean.RemoteInfo;
import com.fr.coverage.check.CheckService;
import com.fr.coverage.constants.Local;
import com.fr.coverage.git.helper.CookbookHelper;
import com.fr.coverage.git.service.DiffService;
import com.fr.coverage.git.service.RemoteDiffService;
import com.fr.coverage.git.service.coverage.CoverageService;
import com.fr.coverage.git.sync.CodeSync;
import com.fr.coverage.jacoco.parser.FilePathConverter;
import com.fr.coverage.jacoco.report.JacocoXmlCheckService;
import com.fr.coverage.json.bean.JsonInfo;
import com.fr.coverage.utils.FileJsonUtils;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.lib.Repository;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

public class RemoteCalculate implements Calculate {
    private RemoteInfo from;
    private RemoteInfo to;

    public RemoteCalculate(RemoteInfo from, RemoteInfo to) {
        this.from = from;
        this.to = to;
    }


    @Override
    public double calTestCoverage() throws Exception {
        String repoPath = StrUtil.format("{}{}{}", Local.BASE_PATH.getValue(), File.separator, to.getRepoName());
        if (!FileUtil.exist(repoPath)) {
            CodeSync codeSync = new CodeSync();
            codeSync.initRepo(to);
        }
        Repository repository = CookbookHelper.openToBranchRepository(repoPath, to.getBranchName());
        DiffService diffService = new RemoteDiffService(from, to);
        List<DiffEntry> diffEntryList = diffService.getDiffList(repository);

        if (diffEntryList == null) {
            StaticLog.error("The different code obtained is empty, and the program ends");
            return 0;
        }

        CoverageService coverageService = new CoverageService();
        CheckService checkService = new JacocoXmlCheckService(FileUtil.file(StrUtil.format("{}{}build{}jacoco",
                to.getLocalPosition(), File.separator, File.separator)));

        List<CodeLine> codeLineBeanList = coverageService.getCodeLineList(diffEntryList, repository);
        List<CodeLine> collect = codeLineBeanList.stream().map(codeLine -> new CodeLine(new FilePathConverter(codeLine.getPath()).convert(), codeLine.getLine())).collect(Collectors.toList());

        JsonInfo jsonInfo = coverageService.generateCoverageJsonObj(collect, checkService);
        // 保存json对象
        FileJsonUtils.save(jsonInfo);
        return jsonInfo.getCoverage();
    }
}
