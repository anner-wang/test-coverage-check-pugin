package com.fr.coverage.git.service.coverage;

import cn.hutool.core.util.StrUtil;
import cn.hutool.log.StaticLog;
import com.fr.coverage.git.helper.CookbookHelper;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.RemoteConfig;
import org.eclipse.jgit.transport.URIish;

import java.io.IOException;
import java.net.CookieHandler;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RepositoryService {

    private Git git;

    public RepositoryService(Repository repository) {
        this.git = new Git(repository);
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
        StaticLog.info(StrUtil.format("fetch repository {}-{} success", git.getRepository().getDirectory(), remoteName), "INFO");
    }

    public void pull(String remoteName, String branchName) throws GitAPIException {
        git.pull().setRemote(remoteName).setRemoteBranchName(branchName).call();
    }

    public void checkout(String branchName, String remoteName, String remoteBranch) throws GitAPIException {
        // 这里不可以强制创建，否则无法获取最新的代码
        if (!getLocalBranchList().contains(branchName)) {
            git.branchCreate()
                    .setName(branchName)
                    .setStartPoint(StrUtil.format("{}/{}", remoteName, remoteBranch))
                    .setForce(true)
                    .call();
        }
        git.checkout().setName(branchName).call();
    }

    public void addRemoteURL(String remoteKey, String remoteURL) throws URISyntaxException, GitAPIException {
        if (getLocalRemoteNameList().contains(remoteKey)) {
            removeRemoteURL(remoteKey);
        }
        git.remoteAdd()
                .setName(remoteKey)
                .setUri(new URIish(remoteURL))
                .call();
        StaticLog.info(StrUtil.format("add remote name {} to  {} --> {}", remoteKey, git.getRepository().getDirectory(), remoteURL), "INFO");
    }

    public void removeRemoteURL(String remoteKey) throws GitAPIException {
        git.remoteRemove()
                .setRemoteName(remoteKey)
                .call();
    }

    public List<Ref> getRemoteBranchList() throws GitAPIException {
        return git.branchList()
                .setListMode(ListBranchCommand.ListMode.REMOTE)
                .call();
    }

    public List<String> getLocalBranchList() throws GitAPIException {
        List<String> localBranchList = git.branchList().setListMode(ListBranchCommand.ListMode.ALL).call()
                .stream().filter(ref -> ref.getName().startsWith(Constants.R_HEADS)).map(Ref::getName)
                .collect(Collectors.toList());
        List<String> resList = new ArrayList<>();
        for (String branchName : localBranchList) {
            resList.add(branchName.substring(branchName.lastIndexOf(StrUtil.SLASH) + 1));
        }
        return resList;
    }

    public List<String> getLocalRemoteNameList() throws GitAPIException {
        List<String> ansList = new ArrayList<>();

        for (RemoteConfig remoteConfig : git.remoteList().call()) {
            ansList.add(remoteConfig.getName());
        }
        return ansList;
    }

    public static void main(String[] args) throws IOException, GitAPIException, URISyntaxException {
        Repository repository = CookbookHelper.openGitRepo("/Users/anner/temp/simple-case/master/.git");
        RepositoryService service = new RepositoryService(repository);
//        service.fetch("user","ssh://git@localhost:7999/~user/simple-case.git");
//        service.checkout("user","user","master");
        service.pull("user", "master");
    }
}
