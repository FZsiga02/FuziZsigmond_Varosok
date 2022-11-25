package hu.petrik.varosok;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class ListActivity extends AppCompatActivity {
    private TextView list;
    private Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_activity);
        init();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                try {
                    list.setText(loadCitiesFromServer());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private String loadCitiesFromServer() throws IOException {
        Response response = RequestHandler.get(MainActivity.URL);
        return response.getContent();
    }

    public void init(){
        back = findViewById(R.id.back);
        list = findViewById(R.id.list);
    }

}
