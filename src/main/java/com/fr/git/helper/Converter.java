package com.fr.git.helper;

import cn.hutool.core.util.StrUtil;
import com.fr.bean.LocalInfo;
import com.fr.bean.RemoteInfo;
import com.fr.constants.Local;
import com.fr.constants.Server;

import java.io.File;
import java.security.InvalidParameterException;

public class Converter {
    /**
     * get请求的参数信息转化为RemoteInfo
     *
     * @param refStr "~ANNER/check-test-coverage:refs/heads/master"
     * @return from.setRepoName(" check - test - coverage ");
     * from.setRemoteURL("ssh://git@code.fineres.com:7999/~anner/check-test-coverage.git");
     * from.setBranchName("master");
     * from.setRemoteName("anner");
     * from.setUserName("anner");
     */
    public static RemoteInfo RefStr2RemoteInfo(String refStr, boolean isFromDirection) {
        RemoteInfo gitInfo = new RemoteInfo();

        String[] splitArr = refStr.split(StrUtil.SLASH);
        String userName = splitArr[0].substring(1).toLowerCase();
        String repoName = splitArr[1].split(StrUtil.COLON)[0];

        StringBuffer sb = new StringBuffer();
        for (int i = 3; i < splitArr.length; i++) {
            sb.append(splitArr[i] + StrUtil.SLASH);
        }
        String branchName = sb.toString().substring(0, sb.length() - 1);

        gitInfo.setRepoName(repoName);
        gitInfo.setUserName(userName);
        gitInfo.setBranchName(branchName);

        if (isFromDirection) {
            gitInfo.setRemoteName(userName);
        } else {
            gitInfo.setRemoteName(Local.DEFAULT_REMOTE_NAME.getValue());
        }

        String remoteURL = StrUtil.format("{}://git@{}:{}/~{}/{}.git", Server.PROTOCOL.getValue(), Server.IP.getValue(),
                Server.PORT.getValue(), userName, repoName);
        gitInfo.setRemoteURL(remoteURL);

        gitInfo.setLocalPosition(StrUtil.format("{}{}{}{}{}", Local.BASE_PATH, File.separator,
                gitInfo.getRepoName(), File.separator, gitInfo.getBranchName()));

        return gitInfo;
    }

    /**
     * 本地调用的参数信息转化为LocalInfo
     *
     * @param args 接收的参数说明：
     *             1. 本地的仓库目录
     *             2. 希望提交的主仓库的remote的名字
     *             3. 希望提交的主仓库的分支
     * @return
     */
    public static LocalInfo Args2LocalInfo(String[] args) {
        if (args.length < 3) {
            throw new InvalidParameterException("Incorrect number of parameters");
        }

        LocalInfo localInfo=new LocalInfo();
        localInfo.setRepoPath(args[0]);
        localInfo.setRemoteName(args[1]);
        localInfo.setRemoteBranch(args[2]);

        return localInfo;
    }
}
