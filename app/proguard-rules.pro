# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\soft\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
#指定压缩级别
-optimizationpasses 5

#不跳过非公共的库的类成员
-dontskipnonpubliclibraryclassmembers
# 不做预校验，preverify是proguard的四个步骤之一，Android不需要preverify，去掉这一步能够加快混淆速度。
-dontpreverify
#混淆时采用的算法
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
# 混合时不使用大小写混合，混合后的类名为小写
-dontusemixedcaseclassnames

# 指定不去忽略非公共库的类
-dontskipnonpubliclibraryclasses
#优化时允许访问并修改有修饰符的类和类的成员
-allowaccessmodification
# 这句话能够使我们的项目混淆后产生映射文件
# 包含有类名->混淆后类名的映射关系
-verbose
#将文件来源重命名为“SourceFile”字符串
-renamesourcefileattribute SourceFile
#保留行号
-keepattributes SourceFile,LineNumberTable

# 保留support下的所有类及其内部类
-keep class android.support.** {*;}

# 保留继承的
-keep public class * extends android.support.v4.**
-keep public class * extends android.support.v7.**
-keep public class * extends android.support.annotation.**
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService
-keepclasseswithmembernames class * {
    native <methods>;
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);  #保持自定义控件类不被混淆，指定格式的构造方法不去混淆
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclassmembers class * extends android.app.Activity {
    public void *(android.view.View); #保持指定规则的方法不被混淆（Android layout 布局文件中为控件配置的onClick方法不能混淆）
}
-keep class * implements android.os.Parcelable {  #保持 Parcelable 不被混淆（aidl文件不能去混淆）
    public static final android.os.Parcelable$Creator *;
}
-keepclassmembers enum * {  #保持枚举 enum 不被混淆
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
# Retrolambda
-dontwarn java.lang.invoke.*

-keep public class * extends android.view.View {  #保持自定义控件指定规则的方法不被混淆
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public void set*(...);
}
-keepattributes *Annotation*,InnerClasses  #假如项目中有用到注解，应加入这行配置
-keep class **.R$* { *; }  #保持R文件不被混淆，否则，你的反射是获取不到资源id的
#保持所有实现 Serializable 接口的类成员
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
# 保留Parcelable序列化类不被混淆
-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}
-keepclassmembers class * {
    void *(**On*Event);
    void *(**On*Listener);
}

#Fragment不需要在AndroidManifest.xml中注册，需要额外保护下
-keep public class * extends android.support.v4.app.Fragment
-keep public class * extends android.app.Fragment

-keep public class com.freemudpos.data.dao.** {
     public static <fields>;
}
# webView处理，项目中没有使用到webView忽略即可
-keepclassmembers class fqcn.of.javascript.interface.for.webview {
    public *;
}
-keepclassmembers class * extends android.webkit.webViewClient {
    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public boolean *(android.webkit.WebView, java.lang.String);
}
-keepclassmembers class * extends android.webkit.webViewClient {
    public void *(android.webkit.webView, java.lang.String);
}

-dontwarn android.support.design.**
-keep class android.support.design.** { *; }
-keep interface android.support.design.** { *; }
-keep public class android.support.design.R$* { *; }
#高德相关
    -keep class com.amap.api.maps.**{*;}
    -keep class com.autonavi.**{*;}
    -keep class com.amap.api.trace.**{*;}
    -keep class com.amap.api.location.**{*;}
    -keep class com.amap.api.fence.**{*;}
    -keep class com.autonavi.aps.amapapi.model.**{*;}
    -keep class com.amap.api.services.**{*;}
    -keep class com.amap.api.maps2d.**{*;}
    -keep class com.amap.api.mapcore2d.**{*;}
    -keep class com.amap.api.navi.**{*;}
    -keep class com.autonavi.**{*;}
#greendao3.2.0,此是针对3.2.0，如果是之前的，可能需要更换下包名
 -keep class org.greenrobot.greendao.**{*;}
 -keep public interface org.greenrobot.greendao.**
 -keepclassmembers class * extends org.greenrobot.greendao.AbstractDao {
 public static java.lang.String TABLENAME;
 }
 -keep class **$Properties
 -keep class net.sqlcipher.database.**{*;}
 -keep public interface net.sqlcipher.database.**
 -dontwarn net.sqlcipher.database.**
 -dontwarn org.greenrobot.greendao.**
#retrofit2
-dontwarn retrofit2.**
-keep class retrofit2.** { *;
}
-keep class retrofit2.converter.** { *;
}
-keepattributes Exceptions
-ignorewarnings
-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
    long producerIndex;
    long consumerIndex;
}

-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}

# OkHttp3
-keepattributes Signature
-keepattributes *Annotation*
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-dontwarn okhttp3.**

# ButterKnife
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }
-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}

-keepclassmembers class ** {
    @com.squareup.otto.Subscribe public *;
    @com.squareup.otto.Produce public *;
}
-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}

-keep public class com.dispatching.feima.entity.**{*;}

#Rxjava
-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
 long producerIndex;
 long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
 rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
 rx.internal.util.atomic.LinkedQueueNode consumerNode;
}

-dontwarn dagger.internal.codegen.**
-keepclassmembers,allowobfuscation class * {
    @javax.inject.* *;
    @dagger.* *;
    <init>();
}

-keep class dagger.* { *; }
-keep class javax.inject.* { *; }
-keep class * extends dagger.internal.Binding
-keep class * extends dagger.internal.ModuleAdapter
-keep class * extends dagger.internal.StaticInjection
