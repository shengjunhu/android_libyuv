package com.hsj.yuv;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * @Author:hsj
 * @Date:2020-08-10
 * @Class:YUV
 * @Desc:YUV图像工具类
 */
public final class YUV {

    static {
        System.loadLibrary("yuv");
    }

    //YUV format: video_common.h #FourCC
    public @interface FORMAT {
        int FOURCC_I420 = 808596553;//FOURCC('I', '4', '2', '0')
        int FOURCC_I422 = 842150985;//FOURCC('I', '4', '2', '2')
        int FOURCC_I444 = 875836489;//FOURCC('I', '4', '4', '4')
        int FOURCC_I400 = 808465481;//FOURCC('I', '4', '0', '0')
        int FOURCC_NV21 = 825382478;//FOURCC('N', 'V', '2', '1')
        int FOURCC_NV12 = 842094158;//FOURCC('N', 'V', '1', '2')
        int FOURCC_YUY2 = 844715353;//FOURCC('Y', 'U', 'Y', '2')
        int FOURCC_UYVY = 1498831189;//FOURCC('U', 'Y', 'V', 'Y')
        int FOURCC_I010 = 808529993;//FOURCC('I', '0', '1', '0')  // bt.601 10 bit 420
        int FOURCC_I210 = 808529993;//FOURCC('I', '0', '1', '0')  // bt.601 10 bit 422

        // 1 Secondary YUV format: row biplanar.  deprecated.
        int FOURCC_M420 = 808596557;//FOURCC('M', '4', '2', '0')

        // 11 Primary RGB formats: 4 32 bpp, 2 24 bpp, 3 16 bpp, 1 10 bpc
        int FOURCC_ARGB = 1111970369;//FOURCC('A', 'R', 'G', 'B')
        int FOURCC_BGRA = 1095911234;//FOURCC('B', 'G', 'R', 'A')
        int FOURCC_ABGR = 1380401729;//FOURCC('A', 'B', 'G', 'R')
        int FOURCC_AR30 = 808669761;//FOURCC('A', 'R', '3', '0')  // 10 bit per channel. 2101010.
        int FOURCC_AB30 = 808665665;//FOURCC('A', 'B', '3', '0')  // ABGR version of 10 bit
        int FOURCC_24BG = 1195521074;//FOURCC('2', '4', 'B', 'G')
        int FOURCC_RAW = 544694642;//FOURCC('r', 'a', 'w', ' ')
        int FOURCC_RGBA = 1094862674;//FOURCC('R', 'G', 'B', 'A')
        int FOURCC_RGBP = 1346520914;//FOURCC('R', 'G', 'B', 'P')  // rgb565 LE.
        int FOURCC_RGBO = 1329743698;//FOURCC('R', 'G', 'B', 'O')  // argb1555 LE.
        int FOURCC_R444 = 875836498;//FOURCC('R', '4', '4', '4')   // argb4444 LE.

        // 1 Primary Compressed YUV format.
        int FOURCC_MJPG = 1196444237;                              //FOURCC('M', 'J', 'P', 'G')

        // 14 Auxiliary YUV variations: 3 with U and V planes are swapped, 1 Alias.
        int FOURCC_YV12 = 842094169;//FOURCC('Y', 'V', '1', '2')
        int FOURCC_YV16 = 909203033;//FOURCC('Y', 'V', '1', '6')
        int FOURCC_YV24 = 875714137;//FOURCC('Y', 'V', '2', '4')
        int FOURCC_YU12 = 842093913;//FOURCC('Y', 'U', '1', '2')  // Linux version of I420.
        int FOURCC_J420 = 808596554;//FOURCC('J', '4', '2', '0')  // jpeg (bt.601 full), unofficial fourcc
        int FOURCC_J422 = 842150986;//FOURCC('J', '4', '2', '2')  // jpeg (bt.601 full), unofficial fourcc
        int FOURCC_J444 = 875836490;//FOURCC('J', '4', '4', '4')  // jpeg (bt.601 full), unofficial fourcc
        int FOURCC_J400 = 808465482;//FOURCC('J', '4', '0', '0')  // jpeg (bt.601 full), unofficial fourcc
        int FOURCC_H420 = 808596552;//FOURCC('H', '4', '2', '0')  // bt.709, unofficial fourcc
        int FOURCC_H422 = 842150984;//FOURCC('H', '4', '2', '2')  // bt.709, unofficial fourcc
        int FOURCC_H444 = 875836488;//FOURCC('H', '4', '4', '4')  // bt.709, unofficial fourcc
        int FOURCC_U420 = 808596565;//FOURCC('U', '4', '2', '0')  // bt.2020, unofficial fourcc
        int FOURCC_U422 = 842150997;//FOURCC('U', '4', '2', '2')  // bt.2020, unofficial fourcc
        int FOURCC_U444 = 875836501;//FOURCC('U', '4', '4', '4')  // bt.2020, unofficial fourcc
        int FOURCC_H010 = 808529992;//FOURCC('H', '0', '1', '0')  // bt.709 10 bit 420
        int FOURCC_U010 = 808530005;//FOURCC('U', '0', '1', '0')  // bt.2020 10 bit 420
        int FOURCC_H210 = 808529992;//FOURCC('H', '0', '1', '0')  // bt.709 10 bit 422
        int FOURCC_U210 = 808530005;//FOURCC('U', '0', '1', '0')  // bt.2020 10 bit 422

        // 14 Auxiliary aliases.  CanonicalFourCC() maps these to canonical fourcc.
        int FOURCC_IYUV = 1448433993;//FOURCC('I', 'Y', 'U', 'V') // Alias for I420.
        int FOURCC_YU16 = 909202777;//FOURCC('Y', 'U', '1', '6')  // Alias for I422.
        int FOURCC_YU24 = 875713881;//FOURCC('Y', 'U', '2', '4')  // Alias for I444.
        int FOURCC_YUYV = 1448695129;//FOURCC('Y', 'U', 'Y', 'V') // Alias for YUY2.
        int FOURCC_YUVS = 1937143161;//FOURCC('y', 'u', 'v', 's') // Alias for YUY2 on Mac.
        int FOURCC_HDYC = 1129923656;//FOURCC('H', 'D', 'Y', 'C') // Alias for UYVY.
        int FOURCC_2VUY = 2037741106;//FOURCC('2', 'v', 'u', 'y') // Alias for UYVY on Mac.
        int FOURCC_JPEG = 1195724874;//FOURCC('J', 'P', 'E', 'G') // Alias for MJPG.
        int FOURCC_DMB1 = 828534116;//FOURCC('d', 'm', 'b', '1')  // Alias for MJPG on Mac.
        int FOURCC_BA81 = 825770306;//FOURCC('B', 'A', '8', '1')  // Alias for BGGR.
        int FOURCC_RGB3 = 859981650;//FOURCC('R', 'G', 'B', '3')  // Alias for RAW.
        int FOURCC_BGR3 = 861030210;//FOURCC('B', 'G', 'R', '3')  // Alias for 24BG.
        int FOURCC_CM32 = 536870912;//FOURCC(0, 0, 0, 32)         // Alias for BGRA kCMPixelFormat_32ARGB
        int FOURCC_CM24 = 402653184;//FOURCC(0, 0, 0, 24)         // Alias for RAW kCMPixelFormat_24RGB
        int FOURCC_L555 = 892679500;//FOURCC('L', '5', '5', '5')  // Alias for RGBO.
        int FOURCC_L565 = 892745036;//FOURCC('L', '5', '6', '5')  // Alias for RGBP.
        int FOURCC_5551 = 825570613;//FOURCC('5', '5', '5', '1')  // Alias for RGBO.

        // deprecated formats.  Not supported, but defined for backward compatibility.
        int FOURCC_I411 = 825308233;//FOURCC('I', '4', '1', '1')
        int FOURCC_Q420 = 808596561;//FOURCC('Q', '4', '2', '0')
        int FOURCC_RGGB = 1111967570;//FOURCC('R', 'G', 'G', 'B')
        int FOURCC_BGGR = 1380403010;//FOURCC('B', 'G', 'G', 'R')
        int FOURCC_GRBG = 1195528775;//FOURCC('G', 'R', 'B', 'G')
        int FOURCC_GBRG = 1196573255;//FOURCC('G', 'B', 'R', 'G')
        int FOURCC_H264 = 875967048;//FOURCC('H', '2', '6', '4')

        // Match any fourcc.
        int FOURCC_ANY = -1;
    }

    //YUV rotate: rotate.h #RotationMode
    public @interface ROTATE {
        int ROTATE_0 = 0;      // No rotation.
        int ROTATE_90 = 90;    // Rotate 90 degrees clockwise.
        int ROTATE_180 = 180;  // Rotate 180 degrees.
        int ROTATE_270 = 270;  // Rotate 270 degrees clockwise.
    }

    //YUV flip:
    public @interface FLIP {
        int FLIP_H = 1;
        int FLIP_DEFAULT = 0;
        int FLIP_V = -1;
    }

    //YUV filter: scale.h #FilterMode
    public @interface FILTER {
        int FILTER_NONE = 0;      // Point sample; Fastest.
        int FILTER_LINEAR = 1;    // Filter horizontally only.
        int FILTER_BILINEAR = 2;  // Faster than box, but lower quality scaling down.
        int FILTER_BOX = 3;       // Highest quality.
    }

    private YUV() throws IllegalAccessException {
        throw new IllegalAccessException("YUV can't be instance");
    }

    /**
     * covert
     * @param src_File     src yuv file
     * @param src_format   src yuv format
     * @param src_width    src yuv width
     * @param src_height   src yuv height
     * @param crop_x       des yuv crop at horizontal coordinate start position，if not crop set 0
     * @param crop_y       des yuv crop at vertical coordinate start position，if not crop set 0
     * @param des_width    des yuv width, if des_rotate=90/270 ,notice change width to src height
     * @param des_height   des yuv height, if des_rotate=90/270 ,notice change height to src width
     * @param des_rotate   rotate src yuv,ROTATE_0 isn't rotate
     * @param des_flip     flip src yuv, FLIP_DEFAULT isn't flip
     * @param des_filter   scale src yuv by des_with and src_with rate
     * @param des_format   des yuv format
     * @return             des yuv data
     */
    public static byte[] covert(File src_file, @FORMAT int src_format,
                                int src_width, int src_height,
                                int crop_x, int crop_y,
                                int des_width, int des_height,
                                @ROTATE int des_rotate, @FLIP int des_flip,
                                @FILTER int des_filter, @FORMAT int des_format) {
        byte[] src_data = readFile(src_file);
        return covert(src_data, src_format,
                src_width, src_height,
                crop_x, crop_y,
                des_width, des_height,
                des_rotate, des_flip,
                des_filter, des_format);
    }

    /**
     * covert
     * @param src_data     src yuv data
     * @param src_format   src yuv format
     * @param src_width    src yuv width
     * @param src_height   src yuv height
     * @param crop_x       des yuv crop at horizontal coordinate start position，if not crop set 0
     * @param crop_y       des yuv crop at vertical coordinate start position，if not crop set 0
     * @param des_width    des yuv width, if des_rotate=90/270 ,notice change width to src height
     * @param des_height   des yuv height, if des_rotate=90/270 ,notice change height to src width
     * @param des_rotate   rotate src yuv,ROTATE_0 isn't rotate
     * @param des_flip     flip src yuv, FLIP_DEFAULT isn't flip
     * @param des_filter   scale src yuv by des_with and src_with rate
     * @param des_format   des yuv format
     * @return             des yuv data
     */
    public static byte[] covert(byte[] src_data, @FORMAT int src_format,
                                int src_width, int src_height,
                                int crop_x, int crop_y,
                                int des_width, int des_height,
                                @ROTATE int des_rotate, @FLIP int des_flip,
                                @FILTER int des_filter, @FORMAT int des_format) {
        if (src_data == null) return null;
        return nativeCovert(src_data, src_format,
                src_width, src_height,
                crop_x, crop_y,
                des_width, des_height,
                des_rotate, des_flip,
                des_filter, des_format);
    }

    private static native byte[] nativeCovert(byte[] src_data, int src_format,
                                              int src_width, int src_height,
                                              int crop_x, int crop_y,
                                              int des_width, int des_height,
                                              int des_rotate, int des_flip,
                                              int des_filter,int des_format);

    private static byte[] readFile(File desFile) {
        if (desFile == null || desFile.isDirectory()) return null;
        byte[] data = null;
        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(desFile, "r");
            data = new byte[(int) raf.length()];
            raf.readFully(data);
            raf.close();
            raf = null;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (raf != null) {
                try {
                    raf.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return data;
    }

}
