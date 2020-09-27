//
// Created by Hsj on 2020/9/27.
//

#include "native_yuv.h"
#include "include/libyuv.h"
using namespace libyuv;

/*
 * covert
 */
uint8_t *yuvCovert(const uint8_t *src_data, int src_format,
                   int src_width, int src_height,
                   int crop_x, int crop_y,
                   int des_width, int des_height,
                   int des_rotate, int des_flip,
                   int des_filter, int des_format) {
    uint8_t *des_data = NULL;
    //TODO implements 怎么指定输出格式
    return des_data;
}

/*
 * nativeCovert
 */
jbyteArray nativeCovert(JNIEnv *env, jclass clazz,
                        jbyteArray src_data, int src_format,
                        int src_width, int src_height,
                        int crop_x, int crop_y,
                        int des_width, int des_height,
                        int des_rotate, int des_flip,
                        int des_filter, int des_format) {
    auto *yuv = env->GetByteArrayElements(src_data, JNI_FALSE);
    uint8_t *des_data = yuvCovert((const uint8_t *) yuv, src_format,
                                   src_width, src_height,
                                   crop_x, crop_y,
                                   des_width, des_height,
                                   des_rotate, des_flip,
                                   des_filter, des_format);
    env->ReleaseByteArrayElements(src_data, yuv, JNI_ABORT);
    if (des_data) {
        jbyteArray data = env->NewByteArray(sizeof(des_data));
        env->SetByteArrayRegion(data, 0, sizeof(des_data), (jbyte *) des_data);
        free(des_data);
        return data;
    } else {
        free(des_data);
        return nullptr;
    }
}

//==================================================================================================

/*
 * Java<->C++ Method
 */
static JNINativeMethod METHODS[] = {
        {"nativeCovert", "([BIIIIIIIIIII)[B", (void *) nativeCovert},
};

/*
 * JNI_OnLoad
 */
JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM *vm, void *reserved) {
    JNIEnv *env;
    if (vm->GetEnv(reinterpret_cast<void **>(&env), JNI_VERSION_1_6) != JNI_OK) return JNI_ERR;
    jclass clazz = env->FindClass(CLASS_NAME);
    if (clazz == nullptr) return JNI_ERR;
    jint r = env->RegisterNatives(clazz, METHODS, sizeof(METHODS) / sizeof(JNINativeMethod));
    if (r != JNI_OK) return r;
    return JNI_VERSION_1_6;
}