package com.fr.coverage.git.service.merge;

import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;

import java.io.IOException;

/**
 * @author Hiram
 * @version 5.1.2
 * Created by Hiram on 2020/4/1
 */
public class ObjectIdFinder {
    private Repository repository;

    public ObjectIdFinder(Repository repository) {
        this.repository = repository;
    }


    /**
     * @param branchName 远程分支的名字
     * @param remoteName 远程仓库的名字
     * @return 对应分支的objectID
     */
    public ObjectId findRemoteHeadByName(String branchName, String remoteName) {
        try {
            return repository.exactRef(Constants.R_REMOTES + remoteName + "/" + branchName).getObjectId();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ObjectId findLocalHeadByName(String branchName) {
        try {
            return repository.exactRef(Constants.R_HEADS + branchName).getObjectId();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
