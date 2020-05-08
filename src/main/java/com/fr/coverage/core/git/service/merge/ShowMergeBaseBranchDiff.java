package com.fr.coverage.core.git.service.merge;

import cn.hutool.core.util.StrUtil;
import cn.hutool.log.StaticLog;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;

import java.util.List;

public class ShowMergeBaseBranchDiff {
    private Repository repository;

    private String oldRemoteName;
    private String newRemoteName;

    private String oldBranchName;
    private String newBranchName;

    public ShowMergeBaseBranchDiff(Repository repository, String oldRemoteName, String newRemoteName, String oldBranchName, String newBranchName) {
        this.repository = repository;

        this.oldRemoteName = oldRemoteName;
        this.newRemoteName = newRemoteName;

        this.oldBranchName = oldBranchName;
        this.newBranchName = newBranchName;
    }

    public List<DiffEntry> getRemoteDiffEntry() {
        ObjectId oldBranch = new ObjectIdFinder(repository).findRemoteHeadByName(this.oldBranchName, oldRemoteName);
        ObjectId newBranch = new ObjectIdFinder(repository).findRemoteHeadByName(this.newBranchName, newRemoteName);

        StaticLog.info(StrUtil.format("Start comparing branches (old - new) {} - {}", oldBranch.getName(), newBranch.getName()));

        RevCommit mergeBaseCommitId = new MergeBaseCommand(repository, oldBranch, newBranch).getMergeBaseCommitId();
        if (mergeBaseCommitId == null) {
            StaticLog.error("There is a problem with the merge code structure. Check the code branch");
            return null;
        }
        return new ShowBranchDiff(repository, mergeBaseCommitId, newBranch).getDiffEntry();
    }

    public List<DiffEntry> getLocalDiffEntry() {
        ObjectId oldBranch = new ObjectIdFinder(repository).findRemoteHeadByName(this.oldBranchName, oldRemoteName);
        ObjectId newBranch = new ObjectIdFinder(repository).findLocalHeadByName(this.newBranchName);

        StaticLog.info(StrUtil.format("Start comparing branches (old - new) {} - {}", oldBranch.getName(), newBranch.getName()));

        RevCommit mergeBaseCommitId = new MergeBaseCommand(repository, oldBranch, newBranch).getMergeBaseCommitId();
        return new ShowBranchDiff(repository, mergeBaseCommitId, newBranch).getDiffEntry();
    }
}
