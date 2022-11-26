package hu.petrik.varosok;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.os.AsyncTask;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

public class InsertActivity extends AppCompatActivity {
    private EditText name, country, population;
    private Button add, back2;
    private static final String baseUrl = "https://retoolapi.dev/PKzqLP/varosok";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insert_activity);
        init();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText().toString().equals("")) {
                    Toast.makeText(InsertActivity.this, "A város mező üres", Toast.LENGTH_SHORT).show();
                } else if (country.getText().toString().equals("")) {
                    Toast.makeText(InsertActivity.this, "Az ország mező üres", Toast.LENGTH_SHORT).show();
                } else if (population.getText().toString().equals("")) {
                    Toast.makeText(InsertActivity.this, "A lakosság mező üres", Toast.LENGTH_SHORT).show();
                }else if(Integer.parseInt(population.getText().toString()) < 0){
                    Toast.makeText(InsertActivity.this, "A lakosság mező nem lehet kisebb 0-nál", Toast.LENGTH_SHORT).show();
                }else {
                    try {
                        String json = createJsonFromFormdata();
                        RequestTask task = new RequestTask(baseUrl, "POST", json);
                        task.execute();
                    } catch (IllegalArgumentException e) {
                        Toast.makeText(InsertActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        back2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InsertActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private String createJsonFromFormdata() {
        String cityName = name.getText().toString().trim();
        String countryName = country.getText().toString().trim();
        int people = Integer.parseInt(population.getText().toString().trim());
        City city = new City(cityName, countryName, people);
        Gson converter = new Gson();
        return converter.toJson(city);
    }

    private class RequestTask extends AsyncTask<Void, Void, Response> {
        private String requestUrl;
        private String requestMethod;
        private String requestBody;

        public RequestTask(String requestUrl) {
            this.requestUrl = requestUrl;
            this.requestMethod = "GET";
        }

        public RequestTask(String requestUrl, String requestMethod) {
            this.requestUrl = requestUrl;
            this.requestMethod = requestMethod;
        }

        public RequestTask(String requestUrl, String requestMethod, String requestBody) {
            this.requestUrl = requestUrl;
            this.requestMethod = requestMethod;
            this.requestBody = requestBody;
        }

        @Override
        protected Response doInBackground(Void... voids) {
            Response response = null;
            try {
                switch (requestMethod) {
                    case "GET":
                        response = RequestHandler.get(baseUrl);
                        break;
                    case "POST":
                        response = RequestHandler.post(requestUrl, requestBody);
                        break;
                    case "PUT":
                        response = RequestHandler.put(requestUrl, requestBody);
                        break;
                    case "DELETE":
                        response = RequestHandler.delete(requestUrl);
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }
    }
    public void init() {
        name = findViewById(R.id.name);
        country = findViewById(R.id.country);
        population = findViewById(R.id.population);
        add = findViewById(R.id.add);
        back2 = findViewById(R.id.back2);
    }
}
