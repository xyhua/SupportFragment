# SupportFragment
## 简介
  这是一个Fragment封装库，解决Fragment在使用过程中遇到的常见问题，并封装了Fragment和Activity的基类，并针对Fragment常见使用场景封装了一些常用操作。
帮助项目支持单Activity+多Fragment或者多Activity+多Fragment架构。
### 功能介绍
1、完全解决同级Fragment重叠问题  
2、为Fragment多层嵌套提供支持  
3、为Fragment提供OnBackPressed()监听方法  
4、提供Fragment和Activity基类，封装一系列方法，使用起来更加方便  
5、解决在“内存重启”时候可能发生的一系列异常  

## Demo演示
  Demo为仿照探探做的App，整体架构采用了单Activity+多Fragment，使用MVP+MVVM框架，采用ARouter路由框架进行界面跳转，使用EventBus作为消息总线通知框架，
后续会推出该demo相关的wiki。  
<img src="/gif/auth.gif" width="280px"/>

## 最新版本
模块|supportfragment
---|---
最新版本|![Download](https://api.bintray.com/packages/jkb/maven/supportfragment/images/download.svg)

## 集成
#### Maven集成
```xml
<dependency>
  <groupId>com.justkiddingbaby</groupId>
  <artifactId>supportfragment</artifactId>
  <version>1.0.1</version>
  <type>pom</type>
</dependency>
```
#### JCenter集成
第一步 在项目build.gradle中添加
```gradle
repositories {
    jcenter()
}
```
第一步 在module的build.gradle中添加
```gradle
compile 'com.justkiddingbaby:supportfragment:最新版本'
```
## 用法
#### 为Activity添加支持
Activity需要继承[SupportActivity](/supportFragment/src/main/java/com/jkb/support/ui/SupportActivity.java)并重写int getFragmentContentId()方法，
该方法用于startFragment(SupportFragment)时候作为根视图显示，可让项目的Activity基类继承该类。
##### TestAvtivity.java
```java
public class TestActivity extends SupportActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public int getFragmentContentId() {
        return R.id.main_content;
    }
}
```
##### main_content.xml
```xml
<?xml version="1.0" encoding="utf-8"?>
<FrameLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/mainFrameContent"
    android:layout_width="match_parent"
    android:layout_height="match_parent"/>
```
#### 为Fragment添加支持
Fragment需要继承[SupportFragment](/supportFragment/src/main/java/com/jkb/support/ui/SupportFragment.java)即可，在项目中可让项目的Fragment基类继承该类。
##### TestFragment.java
```java
public class TestFragment extends SupportFragment {

}
```
Fragment中使用时和正常使用Fragment时候一样，只是父类改为了SupportFragment
## 使用说明
返回值|方法|说明|场景
---|---|---|---
|int|[getFragmentContentId()](/supportFragment/src/main/java/com/jkb/support/ui/action/ISupportActivity.java)|返回Fragment的根布局id，在startFragment()时作为显示区域|Activity|
|void|[startFragment(SupportFragment)](/supportFragment/src/main/java/com/jkb/support/ui/action/ISupportAction.java)|隐藏getFragmentContentId()中显示的视图，并显示新的Fragment|Activity/Fragment|
|void|[startFragmentForResult(SupportFragment,int)](/supportFragment/src/main/java/com/jkb/support/ui/action/ISupportAction.java)|和startFragment使用相似，支持返回值|Activity/Fragment|
|void|[startFragmentForResult(SupportFragment,int,Bundle)](/supportFragment/src/main/java/com/jkb/support/ui/action/ISupportAction.java)|和startFragment使用相似，支持返回值|Activity/Fragment|
|void|[setFragmentResult(int,Bundle)](/supportFragment/src/main/java/com/jkb/support/ui/action/ISupportAction.java)|设置Fragment的返回值|Activity/Fragment|
|void|[onFragmentResult(int,int,Bundle)](/supportFragment/src/main/java/com/jkb/support/ui/action/ISupportAction.java)|Fragment的返回值的回调|Activity/Fragment|
|void|[showFragment(SupportFragment,int)](/supportFragment/src/main/java/com/jkb/support/ui/action/ISupportAction.java)|在int位置显示Fragment，并保留该contentId中之前的Fragment，用于Fragment多层嵌套|Activity/Fragment|
|void|[hideFragment(SupportFragment,int)](/supportFragment/src/main/java/com/jkb/support/ui/action/ISupportAction.java)|隐藏Fragment的显示，用于Fragment多层嵌套|Activity/Fragment|
|void|[replaceFragment(SupportFragment,int)](/supportFragment/src/main/java/com/jkb/support/ui/action/ISupportAction.java)|替换int参数的Fragment并销毁之前的Fragment，用于Fragment多层嵌套|Activity/Fragment|
|void|[showPopFragment()](/supportFragment/src/main/java/com/jkb/support/ui/action/ISupportAction.java)|显示栈顶的Fragment|Activity/Fragment|
|void|[closeCurrentAndShowPopFragment()](/supportFragment/src/main/java/com/jkb/support/ui/action/ISupportAction.java)|关闭当前Fragment并显示上级Fragment|Activity/Fragment|
|void|[clearFragment()](/supportFragment/src/main/java/com/jkb/support/ui/action/ISupportAction.java)|清空栈中所有Fragment|Activity/Fragment|
|void|[close()](/supportFragment/src/main/java/com/jkb/support/ui/action/ISupportAction.java)|关闭当前Fragment或者Activity|Activity/Fragment|
|void|[closeFragment(SupportFragment)](/supportFragment/src/main/java/com/jkb/support/ui/action/ISupportAction.java)|关闭指定Fragment|Activity/Fragment|
|void|[String getFragmentTAG()](/supportFragment/src/main/java/com/jkb/support/ui/action/ISupportFragment.java)|返回Fragment的TAG，可以重写，但不建议这样做，自定义可能会有重复的TAG，会发生相关异常|Fragment|

上述的方法，有的被Activity支持，有的被Fragment支持，还有二者均支持的，根据自己的业务逻辑判断在何处使用什么方法，
只要处理好Fragment上下级之间的关系，Fragment还是比较简单的。

## 发布历史
#### v1.1.2(2017/5/17)
1、修复Fragment使用时可能出现Bundle空指针异常。
#### v1.1.1(2017/5/15)
1、修复增加startFragmentForResult系列方法中的onFragmentResult中requestCode参数传递错误。
#### v1.1.0(2017/5/15)
1、增加hideFragment(SupportFragment)方法。  
2、增加startFragmentForResult系列方法，使用参考startActivityForResult。
#### v1.0.2(2017/5/8)
1、修复Support方法中事物保存机制，使用队列对SupportTransaction进行存储及恢复。
#### v1.0.1(2017/5/4)
1、修改Support框架中的showFragment场景，支持added过的Fragment进行showFragment方法的使用。
#### v1.0(2017/5/3)
1、SupportFragment框架分布，封装各种Fragment场景。  
2、封装框架使用场景Demo，仿探探App。