package com.fr.coverage.core.git.service.merge;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;

import java.io.IOException;
import java.util.List;

public class ShowBranchDiff {

    private Repository repository;
    private ObjectId oldBranch;
    private ObjectId newBranch;


    public ShowBranchDiff(Repository repository, ObjectId oldBranch, ObjectId newBranch) {
        this.repository = repository;
        this.oldBranch = oldBranch;
        this.newBranch = newBranch;
    }

    public List<DiffEntry> getDiffEntry() {
        try (Git git = new Git(repository)) {

            AbstractTreeIterator newTreeParser = prepareTreeParser(repository, newBranch);
            AbstractTreeIterator oldTreeParser = prepareTreeParser(repository, oldBranch);

            try {
                return git.diff().setOldTree(oldTreeParser).setNewTree(newTreeParser).call();
            } catch (GitAPIException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static AbstractTreeIterator prepareTreeParser(Repository repository, ObjectId objectId){
        // from the commit we can build the tree which allows us to construct the TreeParser
        try (RevWalk walk = new RevWalk(repository)) {
            RevCommit commit = walk.parseCommit(objectId);
            RevTree tree = walk.parseTree(commit.getTree().getId());

            CanonicalTreeParser treeParser = new CanonicalTreeParser();
            try (ObjectReader reader = repository.newObjectReader()) {
                treeParser.reset(reader, tree.getId());
            }

            walk.dispose();

            return treeParser;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
