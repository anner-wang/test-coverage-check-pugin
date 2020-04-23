package com.fr.coverage.git.service.coverage;

import cn.hutool.core.util.StrUtil;
import cn.hutool.log.StaticLog;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.RemoteConfig;
import org.eclipse.jgit.transport.URIish;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class RepositoryService {

    private Repository repository;

    public RepositoryService(Repository repository) {
        this.repository = repository;
    }

    /**
     * 有一类账户的ssh的链接和用户的ssh链接不同,这里第二次尝试一下
     *
     * @param remoteName
     * @param remoteURL
     * @throws GitAPIException
     * @throws URISyntaxException
     */
    public void fetch(String remoteName, String remoteURL) throws GitAPIException, URISyntaxException {
        Git git = new Git(repository);
        try {
            git.fetch()
                    .setRemote(remoteName)
                    .setCheckFetchedObjects(true)
                    .call();
        } catch (GitAPIException e) {
            StaticLog.warn(StrUtil.format("fetch {} fail try to replace ~ ", remoteURL), "INFO");
            addRemoteURL(remoteName, remoteURL.replace("~", ""));
            git.fetch()
                    .setRemote(remoteName)
                    .setCheckFetchedObjects(true)
                    .call();
        }
        StaticLog.info(StrUtil.format("fetch repository {}-{} success", repository.getDirectory(), remoteName), "INFO");
    }

    public void pull(String remoteName, String branchName) throws GitAPIException {
        Git git = new Git(repository);
        git.pull().setRemote(remoteName).setRemoteBranchName(branchName).call();
    }

    public void checkout(String branchName, String remoteName, String remoteBranch) throws GitAPIException {
        Git git = new Git(repository);
        git.branchCreate()
                .setName(branchName)
                .setStartPoint(StrUtil.format("{}/{}", remoteName, remoteBranch))
                .setForce(true)
                .call();
        git.checkout().setName(branchName).call();
    }

    public void addRemoteURL(String remoteKey, String remoteURL) throws URISyntaxException, GitAPIException {
        if (getLocalRemoteNameList().contains(remoteKey)) {
            removeRemoteURL(remoteKey);
        }
        Git git = new Git(repository);
        git.remoteAdd()
                .setName(remoteKey)
                .setUri(new URIish(remoteURL))
                .call();
        StaticLog.info(StrUtil.format("add remote name {} to  {} --> {}", remoteKey, repository.getDirectory(), remoteURL), "INFO");
    }

    public void removeRemoteURL(String remoteKey) throws GitAPIException {
        Git git = new Git(repository);
        git.remoteRemove()
                .setRemoteName(remoteKey)
                .call();
    }

    public List<Ref> getRemoteBranchList() throws GitAPIException {
        Git git = new Git(repository);
        return git.branchList()
                .setListMode(ListBranchCommand.ListMode.REMOTE)
                .call();
    }

    public List<String> getLocalRemoteNameList() throws GitAPIException {
        List<String> ansList = new ArrayList<>();
        Git git = new Git(repository);

        for (RemoteConfig remoteConfig : git.remoteList().call()) {
            ansList.add(remoteConfig.getName());
        }
        return ansList;
    }
}
