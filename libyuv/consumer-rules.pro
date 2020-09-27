-keep class com.hsj.yuv.* {*;}
-dontwarn com.hsj.yuv.**

-keepclasseswithmembernames class * {
    native <methods>;
}