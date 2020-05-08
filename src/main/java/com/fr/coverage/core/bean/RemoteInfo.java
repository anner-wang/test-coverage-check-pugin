package com.fr.coverage.core.bean;

import com.fr.stable.AssistUtils;

public class RemoteInfo {
    String userName;
    String repoName;
    String branchName;
    String remoteName;
    String remoteURL;
    String localPosition;

    public RemoteInfo() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRepoName() {
        return repoName;
    }

    public void setRepoName(String repoName) {
        this.repoName = repoName;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getRemoteName() {
        return remoteName;
    }

    public void setRemoteName(String remoteName) {
        this.remoteName = remoteName;
    }

    public String getRemoteURL() {
        return remoteURL;
    }

    public void setRemoteURL(String remoteURL) {
        this.remoteURL = remoteURL;
    }

    public String getLocalPosition() {
        return localPosition;
    }

    public void setLocalPosition(String localPosition) {
        this.localPosition = localPosition;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof RemoteInfo
                && AssistUtils.equals(this.userName, ((RemoteInfo) obj).userName)
                && AssistUtils.equals(this.repoName, ((RemoteInfo) obj).repoName)
                && AssistUtils.equals(this.branchName, ((RemoteInfo) obj).branchName)
                && AssistUtils.equals(this.remoteName, ((RemoteInfo) obj).remoteName)
                && AssistUtils.equals(this.remoteURL, ((RemoteInfo) obj).remoteURL)
                && AssistUtils.equals(this.localPosition, ((RemoteInfo) obj).localPosition);
    }

    @Override
    public int hashCode() {
        return AssistUtils.hashCode(userName, repoName, branchName, remoteName, remoteURL, localPosition);
    }

    @Override
    public String toString() {
        return "RemoteInfo{" +
                "userName='" + userName + '\'' +
                ", repoName='" + repoName + '\'' +
                ", branchName='" + branchName + '\'' +
                ", remoteName='" + remoteName + '\'' +
                ", remoteURL='" + remoteURL + '\'' +
                ", localPosition='" + localPosition + '\'' +
                '}';
    }
}
