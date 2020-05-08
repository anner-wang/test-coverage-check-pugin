package com.fr.coverage.core.git.sync;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.log.StaticLog;
import com.fr.coverage.core.bean.RemoteInfo;
import com.fr.coverage.core.constants.Local;
import com.fr.coverage.core.git.service.coverage.RepositoryService;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

import java.io.File;
import java.io.IOException;

public class CodeSync {


    /**
     * 在获取到远程的主仓库名称后，开始新建一个目录，目录内按照不同分支创建子目录
     *
     * @param to 封装好的希望提交的主仓库的信息
     * @throws Exception
     */
    public void initRepo(RemoteInfo to) throws Exception {
        // 初始化目录结构
        String baseRepoPath = StrUtil.format("{}{}{}", Local.BASE_PATH.getValue(), File.separator, to.getRepoName());
        Repository temp = createNewRepository(baseRepoPath);

        StaticLog.info(StrUtil.format("Create a temporary git directory :{} success",temp.getDirectory()));

        RepositoryService service = new RepositoryService(temp);
        service.addRemoteURL(to.getRemoteName(), to.getRemoteURL());
        service.fetch(to.getRemoteName(),to.getRemoteURL());

        // 获取到远程仓库到所有的分支
        for (Ref ref : service.getRemoteBranchList()) {
            // 创建每一个分支的目录
            String name = ref.getName().substring(
                    ref.getName().lastIndexOf(to.getRemoteName()) + to.getRemoteName().length() + 1)
                    .replace(StrUtil.SLASH, StrUtil.DASHED);
            String path = baseRepoPath+ File.separator + name;
            FileUtil.mkdir(path);
            // 初始化每一个分支的git
            Repository branchRepo = createNewRepository(path);
            RepositoryService branchRepoService=new RepositoryService(branchRepo);
            branchRepoService.addRemoteURL(to.getRemoteName(), to.getRemoteURL());
        }
    }

    private Repository createNewRepository(String path) throws IOException {
        if (!FileUtil.exist(path)) {
            FileUtil.mkdir(path);
        }
        Repository repository = FileRepositoryBuilder.create(new File(path, ".git"));
        repository.create();
        return repository;
    }
}
