# android_sample_yuv
android sample of yuv
developed based on the [chromium/libyuv][1]

### Screenshot
<img src="doc/img/screenshot_1.png" width="200px"/> <img src="doc/img/screenshot_2.png" width="200px"/> <img src="doc/img/screenshot_3.png" width="200px"/>

### Sample
| <img src="/preview/icon/apk-qr.png" width="260px" /> |
| :--------:             |
| 扫码 or [点击下载][3]]  |

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
* [doc_start][4]
* [doc_formats][5]
* [doc_rotation][6]
* [doc_filter][7]

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
[3]: https://github.com/shengjunhu/android_sample_yuv/raw/master/doc/apk/sample_yuv_v20092718.apk
[4]: doc/file/getting_started.md
[5]: doc/file/formats.md
[6]: doc/file/rotation.md
[7]: doc/file/filtering.md