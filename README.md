<p align="center">
  <img src="https://avatars3.githubusercontent.com/u/38806334?s=400&u=b20d7b719e126e45e3d45c0ff04d0597ae3ed703&v=4" width="220" height="220" alt="Banner" />
</p>

# tab6框架集成文档

[![](https://jitpack.io/v/toocms-library/tab6.svg)](https://jitpack.io/#toocms-library/tab6)&#160;&#160;&#160;&#160;&#160;![Support](https://img.shields.io/badge/API-19+-4BC51D.svg)&#160;&#160;&#160;&#160;&#160;![Author](https://img.shields.io/badge/Author-Zero-4BC51D.svg)

## 添加Gradle依赖
- 在项目根目录的build.gradle文件中添加
```
allprojects {
     repositories {
        ...
        maven { url "https://jitpack.io" }
    }
}
```
- 在模块目录下的build.gradle文件的dependencies添加
```
dependencies {
    implementation 'com.github.toocms-library.tab6:tab:6.0.0-alpha' // 核心必须依赖
    implementation 'com.github.toocms-library.tab6:tab-expand:6.0.0-alpha' // 扩展包
}
```
## 集成方法
- 该框架主张用Fragment代替Activity以免去Manifest中注册的麻烦
- 包名下创建BaseFgt抽象类，继承BaseFragment类，该类中可实现一些针对项目通用的方法，其他Fragment类继承BaseFgt类
- 包名下创建config包
- 在config包下新建AppConfig类，实现IAppConfig接口，并实现其方法
- 在values文件夹中创建theme.xml文件，自定义主题继承TooCMS.Compat主题，并在Manifest文件中指定主题
