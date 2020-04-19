package com.fr.git.service;

import com.fr.bean.RemoteInfo;
import com.fr.git.service.coverage.RepositoryService;
import com.fr.git.service.merge.ShowMergeBaseBranchDiff;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.lib.Repository;

import java.net.URISyntaxException;
import java.util.List;

public class RemoteDiffService implements DiffService {
    private RemoteInfo from;
    private RemoteInfo to;

    public RemoteDiffService(RemoteInfo from, RemoteInfo to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public List<DiffEntry> getDiffList(Repository repository) throws GitAPIException, URISyntaxException {
        RepositoryService repositoryService = new RepositoryService(repository);

        repositoryService.addRemoteURL(from.getRemoteName(), from.getRemoteURL());

        String fromBranchName = from.getBranchName();
        String toBranchName = to.getBranchName();

        repositoryService.fetch(from.getRemoteName(), from.getRemoteURL());
        repositoryService.fetch(to.getRemoteName(), to.getRemoteURL());

        ShowMergeBaseBranchDiff showMergeBaseBranchDiff = new ShowMergeBaseBranchDiff(repository,
                to.getRemoteName(), from.getRemoteName(), toBranchName, fromBranchName);
        return showMergeBaseBranchDiff.getRemoteDiffEntry();
    }
}
