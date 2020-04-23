package com.fr.coverage.git.service.merge;

import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.revwalk.filter.RevFilter;

import java.io.IOException;

public class MergeBaseCommand {
    private Repository repository;
    private AnyObjectId oldObjectId;
    private AnyObjectId newObjectId;

    public MergeBaseCommand(Repository repository, AnyObjectId oldObjectId, AnyObjectId newObjectId) {
        this.repository = repository;
        this.oldObjectId = oldObjectId;
        this.newObjectId = newObjectId;
    }


    public RevCommit getMergeBaseCommitId() {
        RevWalk walk = new RevWalk(repository);
        walk.setRevFilter(RevFilter.MERGE_BASE);
        try {
            walk.markStart(walk.parseCommit(oldObjectId));
            walk.markStart(walk.parseCommit(newObjectId));
            RevCommit mergeBase = walk.next();
            return mergeBase;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
