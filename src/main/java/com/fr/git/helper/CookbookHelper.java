package com.fr.git.helper;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.log.StaticLog;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

import java.io.File;
import java.io.IOException;
import java.security.InvalidParameterException;


public class CookbookHelper {

    /**
     * @param localPath    按照传入的仓库的名称而确定的本地的临时仓库的目录
     * @param toBranchName 希望提交的主仓库的分支的名称
     * @return 按照分支的名称，返回该分支目录的仓库对象
     * @throws Exception
     */
    public static Repository openToBranchRepository(String localPath, String toBranchName) throws Exception {
        String gitPath = StrUtil.format("{}{}{}{}.git", localPath, File.separator,
                toBranchName.replace(StrUtil.SLASH, StrUtil.DASHED), File.separator);
        StaticLog.info(StrUtil.format("Open the local temporary branch repository {}...",gitPath));
        return openGitRepo(gitPath);
    }

    /**
     *
     * @param localPath 本地测试中的本地文件夹的位置
     * @return 本地git仓库的对象
     * @throws IOException
     */
    public static Repository openLocalRepository(String localPath) throws IOException {
        if (!FileUtil.exist(localPath)) {
            throw new InvalidParameterException("The local file path does not exist.");
        }
        String gitPath = StrUtil.format("{}{}.gitDir", localPath, File.separator);
        StaticLog.info(StrUtil.format("open local repository {}...",gitPath));
        return openGitRepo(gitPath);
    }

    public static Repository openGitRepo(String gitPath) throws IOException {
        FileRepositoryBuilder builder = new FileRepositoryBuilder();
        builder.setGitDir(new File(gitPath));
        return builder.readEnvironment().build();
    }
}
