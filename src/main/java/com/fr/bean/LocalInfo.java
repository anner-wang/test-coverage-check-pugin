package com.fr.bean;

import java.util.Objects;

public class LocalInfo {
    private String repoPath;
    private String remoteName;
    private String remoteBranch;

    public LocalInfo() {
    }

    public LocalInfo(String repoPath, String remoteName, String remoteBranch) {
        this.repoPath = repoPath;
        this.remoteName = remoteName;
        this.remoteBranch = remoteBranch;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocalInfo localInfo = (LocalInfo) o;
        return Objects.equals(repoPath, localInfo.repoPath) &&
                Objects.equals(remoteName, localInfo.remoteName) &&
                Objects.equals(remoteBranch, localInfo.remoteBranch);
    }

    @Override
    public int hashCode() {
        return Objects.hash(repoPath, remoteName, remoteBranch);
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
