LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE := app
LOCAL_LDFLAGS := -Wl,--build-id
LOCAL_SRC_FILES := \
	/Users/Fridge/hook/EagleEye/app/src/main/jni/Android.mk \
	/Users/Fridge/hook/EagleEye/app/src/main/jni/entry.c \
	/Users/Fridge/hook/EagleEye/app/src/main/jni/base/hook.c \
	/Users/Fridge/hook/EagleEye/app/src/main/jni/base/util.c \
	/Users/Fridge/hook/EagleEye/app/src/main/jni/find_file_path/fd2path.c \
	/Users/Fridge/hook/EagleEye/app/src/main/jni/hooks/hook_apis.c \
	/Users/Fridge/hook/EagleEye/app/src/main/jni/hooks/util.c \

LOCAL_C_INCLUDES += /Users/Fridge/hook/EagleEye/app/src/main/jni
LOCAL_C_INCLUDES += /Users/Fridge/hook/EagleEye/app/src/debug/jni

include $(BUILD_SHARED_LIBRARY)
