package com.fr.coverage.bean;

import com.fr.stable.AssistUtils;

public class LocalInfo {
    private String repoPath;
    private String remoteName;
    private String remoteBranch;
    private String gitGitFileName;

    public LocalInfo() {
    }

    public LocalInfo(String repoPath, String remoteName, String remoteBranch) {
        this(repoPath, remoteName, remoteBranch, "git");
    }

    public LocalInfo(String repoPath, String remoteName, String remoteBranch, String gitGitFileName) {
        this.repoPath = repoPath;
        this.remoteName = remoteName;
        this.remoteBranch = remoteBranch;
        this.gitGitFileName = gitGitFileName;
    }

    public String getRepoPath() {
        return repoPath;
    }

    public void setRepoPath(String repoPath) {
        this.repoPath = repoPath;
    }

    public String getRemoteName() {
        return remoteName;
    }

    public void setRemoteName(String remoteName) {
        this.remoteName = remoteName;
    }

    public String getRemoteBranch() {
        return remoteBranch;
    }

    public void setRemoteBranch(String remoteBranch) {
        this.remoteBranch = remoteBranch;
    }

    public String getGitGitFileName() {
        return gitGitFileName;
    }

    public void setGitGitFileName(String gitGitFileName) {
        this.gitGitFileName = gitGitFileName;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof LocalInfo
                && AssistUtils.equals(this.repoPath, ((LocalInfo) obj).repoPath)
                && AssistUtils.equals(this.remoteBranch, ((LocalInfo) obj).remoteBranch)
                && AssistUtils.equals(this.remoteName, ((LocalInfo) obj).remoteName)
                &&AssistUtils.equals(this.gitGitFileName, ((LocalInfo) obj).gitGitFileName);
    }

    @Override
    public int hashCode() {
        return AssistUtils.hashCode(repoPath, remoteBranch, remoteBranch, gitGitFileName);
    }

    @Override
    public String toString() {
        return "LocalInfo{" +
                "repoPath='" + repoPath + '\'' +
                ", remoteName='" + remoteName + '\'' +
                ", remoteBranch='" + remoteBranch + '\'' +
                '}';
    }
}
