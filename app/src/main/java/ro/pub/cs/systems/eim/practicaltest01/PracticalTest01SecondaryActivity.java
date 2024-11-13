package ro.pub.cs.systems.eim.practicaltest01;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class PracticalTest01SecondaryActivity extends AppCompatActivity {

    Button okButton, cancelButton;
    TextView displaySum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_secondary);

        okButton = findViewById(R.id.ok_button);
        cancelButton = findViewById(R.id.cancel_button);
        displaySum = findViewById(R.id.suma);

        Intent intent = getIntent();
        if (intent != null && Objects.requireNonNull(intent.getExtras()).containsKey(Constants.SUM)) {
            int sum = intent.getIntExtra(Constants.SUM, -1);
            displaySum.setText(String.valueOf(sum));
        }

        okButton.setOnClickListener(view -> {
            Intent intentResult = new Intent();
            intentResult.putExtra(Constants.SUM, Integer.parseInt(displaySum.getText().toString()));
            setResult(RESULT_OK, intentResult);
            finish();
        });

        cancelButton.setOnClickListener(view -> {
            setResult(RESULT_CANCELED, null);
            finish();
        });
    }
}