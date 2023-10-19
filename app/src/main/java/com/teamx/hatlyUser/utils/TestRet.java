package com.teamx.hatlyUser.utils;

import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TestRet {

    public static void hello(List<File> files) {

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();


        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);


        for (int i = 0; i < files.size(); i++) {
            File file = files.get(i);

            // Add form data part for each image
            builder.addFormDataPart("images", file.getName(),
                    RequestBody.create(MediaType.parse("application/octet-stream"), file));
        }

        RequestBody body = builder.build();

        Request request = new Request.Builder()
                .url("http://192.168.100.33:8000/api/v1/upload/uploadMultiple")
                .method("POST", body)
                .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZGVudGlmaWNhdGlvbiI6eyJpdiI6IjZiNjQ3NTMzNjkzODM3NjM2ODMyNmIzOTM1MzczODY0IiwiZW5jcnlwdGVkRGF0YSI6ImRjMGQzNjUxNjk1ZDIwMzE2OGQ3NDY3MjEzZDQ4YWVlNzlhYjEzZTdiZjNmNjJhOGFjYzUwYTY1ODc3NGZiZDUifSwidW5pcXVlSWQiOiI2OWViODMzMzUzNzI2NDEwNmQ2OTk3NTMzYmU1MGQiLCJpYXQiOjE2OTc3MzMxOTMsImV4cCI6MTAzMzc3MzMxOTN9.XeoCaHgwljxTT6wu1ML-6jeUg9IGj8tSFwb4Qaa9zTk")
                .build();
        try {
            Response response = client.newCall(request).execute();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    // Handle the response on the background thread

                    if (response.isSuccessful()) {
                        // Do something with the successful response
                        String responseBody = response.body().string();
                        Log.d("sdsdsdds", "success: "+responseBody);
                        // Process responseBody as needed
                    } else {
                        // Handle the error response
                        // For example, you can get the error message from the response body
                        String errorBody = response.body().string();
                        Log.d("sdsdsdds", "errorBody: "+errorBody);
                        // Handle the error as needed
                    }
                }

                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    // Handle the failure, such as network issues
                    e.printStackTrace();
                    Log.d("sdsdsdds", "failure: ");
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
//            throw new RuntimeException(e);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
