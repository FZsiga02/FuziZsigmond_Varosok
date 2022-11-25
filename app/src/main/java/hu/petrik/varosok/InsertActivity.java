package hu.petrik.varosok;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

public class InsertActivity extends AppCompatActivity {
    private EditText name, country, population;
    private Button add, back2;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        init();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameText = String.valueOf(name.getText());
                String countryText = String.valueOf(country.getText());
                int populationNumber = Integer.parseInt(String.valueOf(population.getText()));
                if(nameText.isEmpty()){
                    Toast.makeText(InsertActivity.this, "A név mező üres",
                            Toast.LENGTH_SHORT).show();
                }
                if(countryText.isEmpty()){
                    Toast.makeText(InsertActivity.this, "Az ország mező üres",
                            Toast.LENGTH_SHORT).show();
                }
                City city = new City(nameText,countryText,populationNumber);
                Gson converter = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
                String json = converter.toJson(city);
            }
           Response response = null;
           try {
                response = RequestHandler.post(MainActivity.URL, json);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (response.getResponseCode() == 201) {
                Toast.makeText(InsertActivity.this, "Város hozzáadva",
                        Toast.LENGTH_SHORT).show();
            } else {
                String content = response.getContent();
                Toast.makeText(InsertActivity.this, content,
                        Toast.LENGTH_SHORT).show();
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

    public void init(){
        name = findViewById(R.id.name);
        country = findViewById(R.id.country);
        population = findViewById(R.id.population);
        add = findViewById(R.id.add);
        back2 = findViewById(R.id.back2);
    }
}
