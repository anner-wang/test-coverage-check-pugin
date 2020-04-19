package com.fr.git.service;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.lib.Repository;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public interface DiffService {
    List<DiffEntry> getDiffList(Repository repository) throws GitAPIException, URISyntaxException, IOException;
}
