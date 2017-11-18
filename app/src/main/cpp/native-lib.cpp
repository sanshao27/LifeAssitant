#include <jni.h>
#include <string.h>

extern "C"
{
    char * signHash = "45b2bfaff1b792ba99105261d3a16ce7";
    JNIEXPORT jboolean JNICALL
    Java_com_ydscience_lifeassistant_utils_LifeApplication_check(JNIEnv *env, jobject instance,
                                                                 jstring signMsg_) {
        const char *signMsg = env->GetStringUTFChars(signMsg_, 0);

        if(strcmp(signMsg,signHash) == 0){
            return true;
        } else{
            return false;
        }
    }

}