# android_sample_yuv
android sample of yuv
developed based on the [chromium/libyuv][1]

### Screenshot
<img src="doc/img/screenshot_1.png" width="200"/> <img src="doc/img/screenshot_2.png" width="200"/> <img src="doc/img/screenshot_3.png" width="200"/>

### Sample
[![APK][2]][3]

### Add Function

* 1-Add `YUV` API with `covert(...)`;
```java
 private static byte[] covert(byte[] src_data, int src_format,
                              int src_width, int src_height,
                              int crop_x, int crop_y,
                              int des_width, int des_height,
                              int des_rotate, int des_flip,
                              int des_filter,int des_format)
```
```java
 private static byte[] covert(File src_file, int src_format,
                              int src_width, int src_height,
                              int crop_x, int crop_y,
                              int des_width, int des_height,
                              int des_rotate, int des_flip,
                              int des_filter,int des_format)
```

### About Build
* NDK
* CMake
* [start][4]
* [formats][5]
* [rotation][6]
* [filter][7]

### About Author:
* Author: shengjunhu
* Date  : 2020/08/05
* E-Mail: shengjunhu@foxmail.com
* GitHub: https://github.com/hushengjun

### About License
```
Copyright (c) 2020 shengjunhu
Please compliance with the libyuv license
```

[1]: https://chromium.googlesource.com/libyuv/libyuv
[2]: doc/img/android_logo.png
[3]: doc/apk/LibYuv_v20092718.apk
[4]: doc/file/getting_started.md
[5]: doc/file/formats.md
[6]: doc/file/rotation.md
[7]: doc/file/filtering.md