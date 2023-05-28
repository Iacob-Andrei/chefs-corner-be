package com.chefscorner.user.util;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Base64;

public class JwtUtil {

    public static String getSubjectFromToken(String bearerToken) throws JSONException {
        String payloadChunk = bearerToken.split(" ")[1].split("\\.")[1];
        String payload = new String(Base64.getUrlDecoder().decode(payloadChunk));

        return (String) new JSONObject(payload).get("sub");
    }
}
