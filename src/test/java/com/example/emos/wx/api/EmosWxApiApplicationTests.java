package com.example.emos.wx.api;

import com.baidu.aip.face.AipFace;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class EmosWxApiApplicationTests {

    public static final String APP_ID = "";
    public static final String API_KEY = "";
    public static final String SECRET_KEY = "";
    @Test
    void faceDistinguish() {
        // 初始化一个AipFace
        AipFace client = new AipFace(APP_ID, API_KEY, SECRET_KEY);
        String image = "https://keen-1311891599.cos.ap-chengdu.myqcloud.com/img/face/wxfile%3A//tmp_059afdc6924fdfbb7c867d5c7ad4d6aa41914b09b9474468.jpg";
        String imageType = "URL";

        // 传入可选参数调用接口
        HashMap<String, Object> options = new HashMap<>();
        // 人脸检测
        JSONObject res = client.detect(image, imageType,options);
        try {
            System.out.println(res.toString(2));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Test
    void faceContrast() {
        AipFace client = new AipFace(APP_ID, API_KEY, SECRET_KEY);
        HashMap<String, Object> options = new HashMap();
        options.put("match_threshold", "70");
        options.put("quality_control", "NORMAL");
        options.put("liveness_control", "LOW");
        options.put("user_id", "7");
        options.put("max_user_num", "3");

        String image = "https://keen-1311891599.cos.ap-chengdu.myqcloud.com/img/face/wxfile%3A//tmp_059afdc6924fdfbb7c867d5c7ad4d6aa41914b09b9474468.jpg";
        String imageType = "URL";
        String groupIdList = "emos_face";

        // 人脸搜索
        JSONObject res = client.search(image, imageType, groupIdList, options);


        try {
            System.out.println(res.toString(2));
            JSONObject r = res.getJSONObject("result");
            JSONArray user_list = r.getJSONArray("user_list");
            JSONObject us = user_list.getJSONObject(0);
            String score =us.getString("score");
            System.out.println(score);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Test
    void faceRegistration() {
        AipFace client = new AipFace(APP_ID, API_KEY, SECRET_KEY);
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<>();
        options.put("user_info", "user's info");
//        options.put("quality_control", "NORMAL");
//        options.put("liveness_control", "LOW");
        options.put("action_type", "REPLACE");//操作方式 APPEND: 当user_id在库中已经存在时，对此user_id重复注册时，新注册的图片默认会追加到该user_id下,REPLACE : 当对此user_id重复注册时,则会用新图替换库中该user_id下所有图片,默认使用APPEND

        String image = "https://keen-1311891599.cos.ap-chengdu.myqcloud.com/img/face/wxfile%3A//tmp_059afdc6924fdfbb7c867d5c7ad4d6aa41914b09b9474468.jpg";
        String imageType = "URL";
        String groupId = "emos_face";
        String userId = "7";

        // 人脸注册
        JSONObject res = client.addUser(image, imageType, groupId, userId, options);
        try {
            System.out.println(res.toString(2));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
