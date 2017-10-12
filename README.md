# FloatingSettingView
仿网易新闻兴趣选择页面  如果觉得有点意思的话，给个star，谢谢！
效果如图(实际效果比gif流畅很多)：

![](https://github.com/ChaserSheng/FloatingSettingView/blob/master/FloatingSettingView/floating.png)

[demo](https://fir.im/7t15)

![](https://github.com/ChaserSheng/FloatingSettingView/blob/master/FloatingSettingView/floating1.gif)

![](https://github.com/ChaserSheng/FloatingSettingView/blob/master/FloatingSettingView/floating2.gif)

首先要说明的是，我的这个实现和网易的完全不一样

打开开发者选项中的显示布局边界，可以看到网易的是用的组合控件实现的，这种实现其实也不难，自定义组合控件view，排版好每个小模块的布局大小，添加自定义动画，
位移、缩放、颜色的改变等。这种方式这里就不讨论了，主要说说我的实现，自定义SurfaceView，启用一个线程不断的去改变每个要绘制的对象的位置，然后重新绘制，
这种实现的好处是你可以随心所欲的控制你想要绘制的图形，千变万化，但是缺点是，绘制起来相对比较麻烦，本例子目前还是demo阶段，还有未完善的地方，时间允许的情况下，
我会优化之。。。

