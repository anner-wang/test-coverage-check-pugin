package com.fr.coverage.core.git;

import com.fr.coverage.core.json.bean.UncoveredJsonInfo;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.IOException;
import java.net.URISyntaxException;

public interface Calculate {

    UncoveredJsonInfo getUncoveredJsonInfo() throws IOException, GitAPIException, URISyntaxException, Exception;
}
