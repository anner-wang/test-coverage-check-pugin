package com.fr.git;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.log.StaticLog;
import com.fr.bean.CodeLine;
import com.fr.bean.RemoteInfo;
import com.fr.check.CheckService;
import com.fr.check.CheckServiceMocker;
import com.fr.constants.Local;
import com.fr.git.helper.CookbookHelper;
import com.fr.git.service.DiffService;
import com.fr.git.service.RemoteDiffService;
import com.fr.git.service.coverage.CoverageService;
import com.fr.git.sync.CodeSync;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.lib.Repository;

import java.io.File;
import java.util.List;

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
        CheckService checkService = new CheckServiceMocker();
//        CheckService checkService = new JacocoXmlCheckService(TestResourceUtils.getResourceFile("git/simpleCase/build/jacoco"));
        List<CodeLine> codeLineBeanList = coverageService.getCodeLineList(diffEntryList, repository);
        return coverageService.calTestCoverage(codeLineBeanList, checkService);
    }
}
