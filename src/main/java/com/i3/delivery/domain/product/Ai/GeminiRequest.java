package com.i3.delivery.domain.product.Ai;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import static java.lang.System.getenv;

public class GeminiRequest {

    static Map<String, String> env = getenv();
    private static final String GOOGLE_API_KEY = env.get("GOOGLE_API_KEY"); // 여기에 API 키를 입력하세요
    private static final String MODEL_NAME = "gemini-pro"; // 사용할 모델을 선택하세요

    // 선택 가능한 Gemini 모델:
    // "gemini-pro": 일반적인 프로 모델 (유료)
    // "gemini-pro-vision": 멀티모달 입력을 지원하는 프로 모델 (유료) - 이미지 검색 및 텍스트 생성 기능 포함
    // "gemini-ultra": 최고 성능을 자랑하는 울트라 모델 (유료) - 대규모 데이터 처리 및 복잡한 작업에 최적
    // "gemini-nano": 경량화된 모델 (무료) - 기본적인 텍스트 생성 및 간단한 작업에 적합
    // 요금 정보: https://ai.google.dev/pricing

    public String answer(String prompt){

        try {
            return callGeminiApi(prompt);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "생성 실패";
    }

    private static String callGeminiApi(String prompt) throws Exception {
        String urlString = "https://generativelanguage.googleapis.com/v1beta/models/" + MODEL_NAME + ":generateContent?key=" + GOOGLE_API_KEY;
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        // JSON 형식이 올바른지 확인
        String jsonInputString = String.format(
                "{\"contents\": [{\"parts\": [{\"text\": \"%s\"}]}]}",
                prompt.replace("\n", "\\n")
        );

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int responseCode = conn.getResponseCode();
        if (responseCode == 200) { // 성공적인 응답 코드 200을 체크
            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }

                // JSON 응답에서 메시지만 추출
                JSONObject jsonResponse = new JSONObject(response.toString());
                if (jsonResponse.has("candidates")) {
                    JSONArray candidates = jsonResponse.getJSONArray("candidates");
                    StringBuilder message = new StringBuilder();
                    for (int i = 0; i < candidates.length(); i++) {
                        JSONObject content = candidates.getJSONObject(i).getJSONObject("content");
                        JSONArray parts = content.getJSONArray("parts");
                        for (int j = 0; j < parts.length(); j++) {
                            message.append(parts.getJSONObject(j).getString("text")).append("\n");
                        }
                    }
                    return message.toString().trim();
                } else {
                    return "No candidates found in the response.";
                }
            }
        } else if (responseCode == 401) {
            throw new RuntimeException("Failed : HTTP error code : 401 Unauthorized. Please check your API key and permissions.");
        } else if (responseCode == 400) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                throw new RuntimeException("Failed : HTTP error code : 400 Bad Request. Response: " + response.toString());
            }
        } else {
            throw new RuntimeException("Failed : HTTP error code : " + responseCode);
        }
    }
}

