package com.fr.bean;

import com.fr.stable.AssistUtils;
import com.fr.stable.version.ProductVersion;

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
    public boolean equals(Object obj) {
        return obj instanceof LocalInfo
                && AssistUtils.equals(this.repoPath, ((LocalInfo)obj).repoPath)
                && AssistUtils.equals(this.remoteBranch, ((LocalInfo) obj).remoteBranch)
                && AssistUtils.equals(this.remoteName, ((LocalInfo) obj).remoteName);
    }

    @Override
    public int hashCode() {
        return AssistUtils.hashCode(repoPath,remoteBranch,remoteBranch);
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
