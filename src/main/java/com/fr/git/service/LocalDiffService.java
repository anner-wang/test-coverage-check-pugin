package com.fr.git.service;

import com.fr.bean.LocalInfo;
import com.fr.git.service.merge.ShowMergeBaseBranchDiff;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.lib.Repository;

import java.io.IOException;
import java.util.List;

public class LocalDiffService implements DiffService {
    private LocalInfo localInfo;

    public LocalDiffService(LocalInfo localInfo) {
        this.localInfo = localInfo;
    }

    @Override
    public List<DiffEntry> getDiffList(Repository repository) throws IOException {
        // 获取到当前分支的名字
        String currentBranchName = repository.getBranch();

        ShowMergeBaseBranchDiff showMergeBaseBranchDiff = new ShowMergeBaseBranchDiff(repository,
                localInfo.getRemoteName(), null, localInfo.getRemoteBranch(), currentBranchName);
        return showMergeBaseBranchDiff.getLocalDiffEntry();
    }
}
