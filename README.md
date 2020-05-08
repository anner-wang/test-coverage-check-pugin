## 测试代码覆盖率插件



导入依赖:

> ```groovy
> implementation 'com.fr.coverage:coverage-core:0.1.0-SNAPSHOT'
> ```

使用示例

```java
 @Async("taskExecutor")
    public void execute() throws InterruptedException {
        Task task = manager.take();
        try {
            task.start();
            // 获取到calculte对象
            Calculate calculate = new RemoteCalculate(task.getFrom(), task.getTo());
            // 可以计算出覆盖率、有效行、无效行等细节
            UncoveredJsonInfo uncoveredJsonInfo = calculate.getUncoveredJsonInfo();
            task.setCoverage(uncoveredJsonInfo.getCoverage());
            task.setUncoveredJsonInfo(uncoveredJsonInfo);
        } catch (Exception e) {
            task.fail();
            task.setDetail(e.getMessage());
        } finally {
            task.complete();
        }
    }
```



返回到计算对象为,如何使用看每一个人需要做什么了:

```json
{
    "coverage": 0.42857142857142855,
    "validLineNumber": 7,
    "testedLineNumber": 3,
    "detail": {
        "com.fr.FrClass.java": "9-10,13-14"
    }
}
```

