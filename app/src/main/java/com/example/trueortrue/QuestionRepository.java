package com.example.trueortrue;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class QuestionRepository {

    private final Context context;

    public QuestionRepository(Context context) {
        this.context = context;
    }

    public List<Question> getQuestions() {
        List<Question> questions = new ArrayList<>();

        try {
            InputStream inputStream = context.getResources().openRawResource(R.raw.questions);

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuilder json = new StringBuilder();

            String line;

            while ((line = reader.readLine()) != null) {
                json.append(line);
            }

            reader.close();

            JSONArray jsonArray = new JSONArray(json.toString());

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String text = jsonObject.getString("text");
                questions.add(new Question(text));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return questions;
    }
}