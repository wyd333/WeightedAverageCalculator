# WeightedAverageCalculator
成绩加权平均分计算程序，带UI界面和输入保存功能。

因为本人对成绩的加权均分有所关注，但学校的教务系统查分时往往又不显示个人的加权均分，加之每次手动敲计算器计算很麻烦就花了点时间写了一个加权均分计算程序自用，顺便开源。

UI界面通过Java的Swing类实现。

git仓库里有3个文件：Config.java、WeightedAverageCalculatorGUI.java和WeightedAverageCalculator.jar。

如果需要要对代码进行操作，直接拷贝Config.java和WeightedAverageCalculatorGUI.java文件到Project的src目录下（这两个文件最好放在同一目录下），运行WeightedAverageCalculatorGUI.java即可。

如果只是使用功能而无需代码操作，运行WeightedAverageCalculator.jar即可。确定配置过Java环境变量后，直接cmd打开命令行窗口，执行命令：

```
java -jar WeightedAverageCalculator.jar的绝对路径
```

具体的页面展示见README.pdf文件。
