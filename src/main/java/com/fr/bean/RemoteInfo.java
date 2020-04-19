package com.fr.bean;

import java.util.Objects;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RemoteInfo gitInfo = (RemoteInfo) o;
        return Objects.equals(userName, gitInfo.userName) &&
                Objects.equals(repoName, gitInfo.repoName) &&
                Objects.equals(branchName, gitInfo.branchName) &&
                Objects.equals(remoteName, gitInfo.remoteName) &&
                Objects.equals(remoteURL, gitInfo.remoteURL) &&
                Objects.equals(localPosition, gitInfo.localPosition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, repoName, branchName, remoteName, remoteURL, localPosition);
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
