LOCAL_PATH:= $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE_TAGS := test

LOCAL_SRC_FILES := $(call all-java-files-under, src)

#LOCAL_JAVA_LIBRARIES := framework
LOCAL_STATIC_JAVA_LIBRARIES := android-support-v4

LOCAL_PACKAGE_NAME := AlarmStresstest

LOCAL_CERTIFICATE := platform

#LOCAL_PRIVILEGED_MODULE := true

include $(BUILD_PACKAGE)

