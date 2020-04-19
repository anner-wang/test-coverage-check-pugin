package com.fr.git;

import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.IOException;
import java.net.URISyntaxException;

public interface Calculate {

    double calTestCoverage() throws IOException, GitAPIException, URISyntaxException, Exception;
}
