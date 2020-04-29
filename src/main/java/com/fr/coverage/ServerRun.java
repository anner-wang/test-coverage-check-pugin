package com.fr.coverage;

import com.fr.coverage.constants.Local;
import com.fr.coverage.constants.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import java.security.InvalidParameterException;

@SpringBootApplication
@EnableAsync
public class ServerRun {
    /**
     * 接收参数
     * 1 用户希望存储代码的文件位置
     * 2 用户希望存储json文件的地址
     * 3 远程仓库的地址
     * 4 远程仓库的端口
     * @param args
     */
    public static void main(String[] args) {
        if (args.length < 4) {
            throw new InvalidParameterException("Incorrect number of parameters");
        }
        Local.BASE_PATH.setValue(args[0]);
        Local.JSON_PATH.setValue(args[1]);
        Server.IP.setValue(args[2]);
        Server.PORT.setValue(args[3]);
        SpringApplication.run(ServerRun.class, args);
    }
}
