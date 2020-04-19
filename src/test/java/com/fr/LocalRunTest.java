package com.fr;

import com.fr.utils.TestResourceUtils;
import junit.framework.TestCase;

/**
 * @author Hiram
 * @version 5.1.2
 * Created by Hiram on 2020/4/17
 */
public class LocalRunTest extends TestCase {

    public void testRun() throws Exception {
        String gitPath = TestResourceUtils.getResourceFile("git/simpleCase").getAbsolutePath();
        String remoteName = "origin";
        String branchName = "master_case1";
        String[] args = new String[]{gitPath, remoteName, branchName};
        new LocalRun().main(args);
    }
}
