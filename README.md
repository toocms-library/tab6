<p align="center">
  <img src="https://avatars3.githubusercontent.com/u/38806334?s=400&u=b20d7b719e126e45e3d45c0ff04d0597ae3ed703&v=4" width="220" height="220" alt="Banner" />
</p>

# 集成文档

[![](https://jitpack.io/v/toocms-library/tab6.svg)](https://jitpack.io/#toocms-library/tab6)&#160;&#160;&#160;![Support](https://img.shields.io/badge/API-19+-4BC51D.svg)&#160;&#160;&#160;![Author](https://img.shields.io/badge/Author-Zero-4BC51D.svg)

## 添加Gradle依赖
- 在项目根目录的build.gradle文件中添加
```
allprojects {
     repositories {
        maven { url "https://jitpack.io" }
    }
}
```
- 在模块目录下的build.gradle文件的dependencies添加
```
android {
    defaultConfig {
        dataBinding { enabled = true }  // 启动DataBinding
    }
    compileOptions {    
        targetCompatibility JavaVersion.VERSION_1_8    // Java1.8
        sourceCompatibility JavaVersion.VERSION_1_8 
    }
}

dependencies {
    implementation 'com.github.toocms-library.tab6:tab:6.0.0-alpha03' // 核心必须依赖
    implementation 'com.github.toocms-library.tab6:tab-expand:6.0.0-alpha03' // 扩展包
}
```
## 集成方法
该框架主张用Fragment代替Activity以免去Manifest中注册的麻烦
1. 包名下创建BaseFgt抽象类，继承BaseFragment类，该类中可实现一些针对项目通用的方法，其他Fragment类继承BaseFgt类
2. 包名下创建config包，在config包下新建AppConfig类，实现IAppConfig接口，并按项目需求实现其方法即可
3. 在values文件夹中创建theme.xml文件，自定义主题继承TooCMS.Compat主题
```xml
<style name="Sample" parent="TooCMS.Compat">
   <!-- 主题色 -->
   <item name="app_primary_color">@color/clr_main</item>
</style>
```
4. 在Manifest文件中指定该自定义主题
```xml
<application
   android:allowBackup="true"
   android:icon="@mipmap/ic_launcher"
   android:label="@string/app_name"
   android:roundIcon="@mipmap/ic_launcher_round"
   android:supportsRtl="true"
   android:theme="@style/Sample">
</application>
```
## 思想概述
该版本秉承MVVM开发模式，代表类：[BaseModel](https://github.com/toocms-library/tab6/blob/master/tab/src/main/java/com/toocms/tab/base/BaseModel.java)（Model基类）、[BaseFragment](https://github.com/toocms-library/tab6/blob/master/tab/src/main/java/com/toocms/tab/base/BaseFragment.java)（View基类）、[BaseViewModel](https://github.com/toocms-library/tab6/blob/master/tab/src/main/java/com/toocms/tab/base/BaseViewModel.java)（ViewModel基类）
### 各类作用域：
-  BaseModel：主要负责本地数据的存储，基本很少用到
-  BaseFragment：主要负责对View的初始化以及刷新操作
-  BaseViewModel：主要负责网络数据请求、收发事件、逻辑处理等操作，并且该类已绑定BaseFragment的生命周期以及动作，例如：onResume、startFragment等
## 使用方法
### BaseActivity使用方法
创建Activity类继承BaseActivity类，添加注解@DefaultFirstFragment，参数为项目首页的Fragment类
```java
@DefaultFirstFragment(MainFgt.class)
public class MainActivity extends BaseActivity {
}
```
### BaseModel使用方法
1. 创建类继承BaseModel，如需在页面退出时清除数据可重写onCleared回调方法
2. 在该类中可借助SPUtils或数据库框架进行本地数据的存储
3. 如若依赖了tab-expand可继承LoginStatusRepository类，该类实现了记录用户登录状态的逻辑
4. 存储用户信息的Model可参考[UserRepository](https://github.com/toocms-library/tab6/blob/master/app/src/main/java/com/toocms/sample/data/UserRepository.java)类
### BaseFragment使用方法
1. 创建Fragment类继承BaseFragment类，并实现其抽象方法
```java
public class HttpFgt extends BaseFgt<FgtToolHttpBinding, HttpViewModel> {

    @Override
    protected void onFragmentCreated() {    // Fragment创建完毕回调
        topBar.setTitle("网络请求"); // topBar为标题栏View
    }

    @Override
    protected int getLayoutResId() { // 指定布局文件
        return R.layout.fgt_tool_http;
    }

    @Override
    public int getVariableId() { // 指定ViewModel的变量ID
        return BR.httpViewModel;
    }

    @Override
    protected void viewObserver() { // 观察者更新UI，通过观察ViewModel中定义的SingleLiveEvent变量的数据改变更新UI
        viewModel.getUc().setText.observe(this, s -> binding.httpTextview.setText(s));
    }
}
```
2. ViewModel的ID创建
```xml
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android">
    <data>
        <!-- 通过variable标签的name属性定义ViewModel在布局文件中的变量名，type属性引用ViewModel的类型 -->
        <variable
            name="httpViewModel"
            type="com.toocms.sample.ui.tool.http.HttpViewModel" />
    </data>
    <androidx.constraintlayout.widget.ConstraintLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent">
    </androidx.constraintlayout.widget.ConstraintLayout>
</layout>
```
3. BaseFragment类必须传入两个泛型参数，ViewDataBinding和BaseViewModel
4. ViewDataBinding为布局文件自动生成的数据绑定类，[查看具体使用方法](https://www.jianshu.com/p/bd9016418af2)，传入后即可使用binding变量
5. BaseViewModel可直接传入该类（简单页面，避免再创建一个空类）或其子类即可，传入后即可使用viewModel变量
6. getViewModel回调，该方法是为Fragment指定ViewModel（当不重写该回调时，只能自动处理ViewModel构造函数只有一个Application的情况），一般用于在ViewModel的构造函数不仅仅只有Application时使用，工厂类参考[AppViewModelFactory](https://github.com/toocms-library/tab6/blob/master/app/src/main/java/com/toocms/sample/data/AppViewModelFactory.java)
```java
@Override
protected UserViewModel getViewModel() {
    AppViewModelFactory factory = AppViewModelFactory.getInstance(TooCMSApplication.getInstance());
    return new ViewModelProvider(this, factory).get(UserViewModel.class);
}
```
### BaseViewModel使用方法
- 创建类继承[BaseViewModel](https://github.com/toocms-library/tab6/blob/master/tab/src/main/java/com/toocms/tab/base/BaseViewModel.java)并注入Model类，如不需要Model类可注入BaseModel类或不注入即可
```java
public class UserViewModel extends BaseViewModel<UserRepository> {

    // 注入UserRepository类需用到带Model的构造函数
    public UserViewModel(@NonNull Application application, UserRepository model) {
        super(application, model);
    }
}
```
```java
public class MD5ViewModel extends BaseViewModel<BaseModel> { // 注入BaseModel类

    // 注入BaseModel类则可用不带Model的构造函数
    public MD5ViewModel(@NonNull Application application) {
        super(application);
    }
}
```
```java
public class MD5ViewModel extends BaseViewModel { // 不注入

    // 不注入和注入BaseModel用的构造函数一样
    public MD5ViewModel(@NonNull Application application) {
        super(application);
    }
}
```
- 已注入BaseFragment的生命周期，所以可重写onResume、onDestroy等回调方法（由于是Fragment的生命周期所以onCreate方法不太好用，尽量避免用该方法）
- 可调用[IBaseAction](https://github.com/toocms-library/tab6/blob/master/tab/src/main/java/com/toocms/tab/base/IBaseAction.java)中的所有方法，如showToast、startFragment等方法，会触发BaseFragment中的方法
- View事件要用到BindingCommand类，如点击、长按等，在ViewModel中定义该类对象（可多个），然后在布局文件中需要注册点击事件的View中指定该对象即可
```java
public BindingCommand copy = new BindingCommand(() -> {
        ClipboardUtils.copyText(uc.setText.getValue());
        showToast("已复制");
    });
```
- Event发送要用到SingleLiveEvent类，可定义成public类型供Fragment类直接调用
```java
public SingleLiveEvent<String> setText = new SingleLiveEvent<>();

public BindingCommand md5 = new BindingCommand(() ->
     // 点击事件触发后调用setText.setValue来给事件设置新的数据
     setText.setValue(EncryptUtils.encryptMD5ToString("com.toocms." + pack.get())));
```
```java
@Override
protected void viewObserver() {
    // Fragment类中调用setText.observe来观察数据的改变，当数据改变时更新UI
    viewModel.setText.observe(this, s -> binding.tvMd5.setText(s));
}
```
### DataBinding使用方法
- 首先绑定ViewModel，然后通过ViewModel获取其变量/方法，[更多绑定方法](https://github.com/toocms-library/tab6/tree/master/tab/src/main/java/com/toocms/tab/binding/viewadapter)
```xml
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android">
    <data>
        <variable
            name="userViewModel"
            type="com.toocms.sample.ui.tool.user.UserViewModel" />
    </data>

        <!-- 为TextView控件的指定显示的文字，注意：showText的类型为ObservableField -->
        <TextView
            android:id="@+id/http_textview"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="@{userViewModel.showText}" />

        <!-- 为Button绑定点击事件 -->
        <com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton
            onClickCommand="@{userViewModel.login}"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginBottom="20dp"
            android:text="登录" />
</layout>
```
```xml
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android">

    <data>

        <import type="com.toocms.sample.R" />

        <variable
            name="imageViewModel"
            type="com.toocms.sample.ui.tool.image.ImageViewModel" />

    </data>

        <!-- 异步加载网络图片 -->
        <ImageView
            android:id="@+id/image_center"
            placeholderRes="@{R.drawable.ic_launcher_background}"
            url="@{imageViewModel.urlCenter}"
            android:layout_width="100dp"
            android:layout_height="100dp" />
</layout>
```
## ApiTool网络请求
- 常用的写法
```java
ApiTool.get("Index/index") // url可与AppConfig类中的BaseUrl拼接使用，也可以指定完整的url
   .add("m_id", 7)
   .asTooCMSResponse(Index.class)
   .withViewModel(this)
   .request(index -> {
      
   });
```
- get方式请求
```java
ApiTool.get("Index/index") // get方式请求
   .add("m_id", 7) // 添加参数
   .asTooCMSResponse(Index.class) // 指定要返回的响应对象的类型（二选一，根据接口的返回数据决定）
   .asTooCMSResponseList(User.class) // 指定要返回的响应列表的对象类型（二选一，根据接口的返回数据决定）
   .observeOn(AndroidSchedulers.mainThread()) // 切换到主线程，如onStart和onFinally中代码不需要在主线程回调时可以不调用
   .onStart(disposable -> {
      // 当请求开始之前的回调，不需要可以不调用该方法
   })
   .onFinally(() -> {
      // 当请求结束时的回调，不需要可以不调用该方法                
   })
   .withViewModel(this) // 绑定ViewModel，绑定后将自动绑定生命周期，Fragment销毁后请求自动取消
   .showLoading(false) // 是否显示加载条，绑定了ViewModel后默认显示，可通过该项设置为不显示
   .request(index -> {
      // 调用请求方法，获取响应结果
   }, (OnError) errorInfo -> {
      if (errorInfo.isLogicException()) {
        // 逻辑异常，即为flag为error的情况会回调该方法
      }
   });
```
- post方式请求，将get换成post即可
```java
ApiTool.post("Index/index") // post方式请求
   .add("m_id", 7) // 添加参数
   .asTooCMSResponse(Index.class) // 指定要返回的响应对象的类型（二选一，根据接口的返回数据决定）
   .asTooCMSResponseList(User.class) // 指定要返回的响应列表的对象类型（二选一，根据接口的返回数据决定）
   .observeOn(AndroidSchedulers.mainThread()) // 切换到主线程，如onStart和onFinally中代码不需要在主线程回调时可以不调用
   .onStart(disposable -> {
      // 当请求开始之前的回调，不需要可以不调用该方法
   })
   .onFinally(() -> {
      // 当请求结束时的回调，不需要可以不调用该方法                
   })
   .withViewModel(this) // 绑定ViewModel，绑定后将自动绑定生命周期，Fragment销毁后请求自动取消
   .showLoading(false) // 是否显示加载条，绑定了ViewModel后默认显示，可通过该项设置为不显示
   .request(index -> {
      // 调用请求方法，获取响应结果
   }, (OnError) errorInfo -> {
      if (errorInfo.isLogicException()) {
        // 逻辑异常，即为flag为error的情况会回调该方法
      }
   });
```
- 上传，写法与post请求无异只是多了一个addFile和asUpload方法
```java
ApiTool.post("http://xlg-api.uuudoo.com/System/upload")
   .add("folder", "1")
   .addFile("head", result.get(0).getCutPath())
   .asUpload(progress -> { // 上传进度
       progress.getProgress(); // 进度值0-100
   })
   .asTooCMSResponse(ImageUrl.class)
   .request(imageUrl -> {
       
   });
```
- 下载
```java
ApiTool.get("http://update.9158.com/miaolive/Miaolive.apk")
   .asDownload(FileManager.getDownloadPath() + File.separator + System.currentTimeMillis() + ".apk", // 指定下载路径
       progress -> { // 下载进度回调
           progress.getProgress(); // 进度值0-100
       })
   .request(fileName -> {
       // 返回下载的文件的绝对路径
   })
```
- 传参的另一种方式
```java
HttpParams params = new HttpParams();
params.put("m_id", 7);
params.put("adr_id", 5);
ApiTool.post("http://hotpotshop-api.uuudoo.com/Center/setDefault")
  .params(params)
  .asTooCMSResponse(String.class)
  .request(s -> ToastUtils.showShort(s));
```
- [更多用法](https://github.com/toocms-library/tab6/blob/master/app/src/main/java/com/toocms/sample/ui/tool/http/HttpViewModel.java)
## ImageLoader图片加载
- DataBinding方式（推荐）
```xml
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android">
    <data>
        <import type="com.toocms.sample.R" />
        <variable
            name="imageViewModel"
            type="com.toocms.sample.ui.tool.image.ImageViewModel" />
    </data>
    
    <!-- url为加载图片的url，placeholderRes为占位图的id -->
    <ImageView
        android:id="@+id/image_center"
        placeholderRes="@{R.drawable.ic_launcher_background}"
        url="@{imageViewModel.urlCenter}"
        android:layout_width="100dp"
        android:layout_height="100dp" />
</layout>
```
- 代码方式，[更多加载方式](https://github.com/toocms-library/tab6/blob/master/tab/src/main/java/com/toocms/tab/imageload/ImageLoader.java)
```java
ImageLoader.loadUrl2Image("url", imageview, R.drawable.ic_crash_image);
```
## [更多用法](https://github.com/toocms-library/tab6/wiki)