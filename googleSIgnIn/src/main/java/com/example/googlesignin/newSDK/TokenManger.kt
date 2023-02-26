package com.example.googlesignin.newSDK
import org.json.JSONObject
import android.util.Base64

class TokenManger(){
    fun isTokenExpired(token: String?): Boolean {
        val parts = token?.split(".")
        if (parts?.size != 3) {
            return true
        }
        val payload = parts[1]
        val payloadBytes = Base64.decode(payload, Base64.DEFAULT)
        val jsonObject = JSONObject(String(payloadBytes))
        val exp: Long = jsonObject.getLong("exp")
        val currentTime = System.currentTimeMillis() / 1000
        return currentTime > exp
    }
}