## 代码测试覆盖率检测插件

#### 1. 背景

众所周知，TDD原则是软件开发的基本原则，对于业务代码，每一个开发者都需要写单元测试，以检测逻辑和驱动自己重构自己的代码结构。所以，代码的测试覆盖率的自动检测可以提升自己和公司的业务代码的质量。

故，对于公司内部的仓库，可以结合bitbucket的hook对接本插件的接口，实现代码的测试覆盖率检测，继而可以拒绝不合格的代码的合并。

#### 2. 基本的检测流程

* jgit 完成代码仓库的分布对比，获取commit的新增的代码
* jcoco 完成代码的模拟测试，获取到有效代码行和被测试覆盖到的代码
* hook 对接企业的仓库，注意需要有代码仓库的权限，才可以保证fetch最新的HEAD
* springboot 异步检测和提供远程检测的接口

本插件支持本地代码分支和远程仓库的检测

本地检测：

` java -jar lcoalRun.jar LOCAL_GIT_PATH TARGET_REMOTE_NAME TARGET_REMOTE_BRANCH`

远程监测：

`HTTP://SERVER_IP:PORT/hook/coverage?from={FROM_PULL_REQUEST}&to={TO_PULL_REQUEST}`

#### 3. 长期更新

后期会对现在的代码进行集成测试，所以对于细节，会继续的更新，最终会提供最后的可执行文件