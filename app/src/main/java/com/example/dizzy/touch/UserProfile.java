package com.example.dizzy.touch;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class UserProfile implements Serializable {
    private static String[] fields = {"uid", "name", "birthday", "phone", "email",
            "facebook", "twitter", "github"};
    Map<String, String> featureMap;

    private UserProfile() {
        featureMap = new HashMap<>();
    }

    UserProfile(String featureString) {
        if (featureString.charAt(featureString.length() - 1) == ';') {
            featureString = featureString.substring(0, featureString.length() - 1);
        }
        featureMap = new HashMap<>();
        String[] tokens = featureString.split(",");
        for (String token : tokens) {
            String keyValue[] = token.split(":");
            String key, value;
            if (keyValue.length > 1) {
                key = keyValue[0];
                value = keyValue[1];
            } else {
                key = keyValue[0];
                value = "";
            }

            featureMap.put(key, value);
        }
    }

    public static UserProfile createWithID(String uid) {
        UserProfile newProfile = new UserProfile();
        Map<String, String> newMap = new HashMap<>();
        for (String key : fields) {
            newMap.put(key, "");
        }
        newMap.put("uid", uid);
        newProfile.setFeatureMap(newMap);
        return newProfile;
    }

    public Map<String, String> getFeatureMap() {
        return featureMap;
    }

    public void setFeatureMap(Map<String, String> newfeatureMap) {
        for (String key : newfeatureMap.keySet()) {
            featureMap.put(key, newfeatureMap.get(key));
        }
    }

    public String getFullParamStr() {
        StringBuilder params = new StringBuilder();
        for (String key : featureMap.keySet())
            params.append("&").append(key).append("=").append(featureMap.get(key));
        return params.substring(1);
    }

    public String getName() {
        return featureMap.get("name");
    }

    public String getUid() {
        return featureMap.get("uid");
    }
}
