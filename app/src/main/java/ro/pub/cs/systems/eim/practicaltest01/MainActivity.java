package ro.pub.cs.systems.eim.practicaltest01;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button pressMe, pressMeToo, navigateToSecondaryActivity;
    EditText input1, input2;

    ColocviuBroadcastReceiver broadcastReceiver;

    ActivityResultLauncher<Intent> activityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pressMe = findViewById(R.id.press_me_button);
        pressMeToo = findViewById(R.id.press_me_too_button);
        navigateToSecondaryActivity = findViewById(R.id.navigate_to_second_activity);
        input1 = findViewById(R.id.input1);
        input2 = findViewById(R.id.input2);

        input1.setText("0");
        input2.setText("0");

        pressMe.setOnClickListener(view -> {
            int value1 = Integer.parseInt(input1.getText().toString());
            input1.setText(String.valueOf(value1 + 1));
        });

        pressMeToo.setOnClickListener(view -> {
            int value2 = Integer.parseInt(input2.getText().toString());
            input2.setText(String.valueOf(value2 + 1));
        });

        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {
                assert result.getData() != null;
                result.getData().getIntExtra(Constants.SUM, -1);
                Toast.makeText(getApplicationContext(), "The activity returned sum " + result.getData().getIntExtra(Constants.SUM, -1), Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "The activity returned with result CANCELED", Toast.LENGTH_LONG).show();
            }
        });

        navigateToSecondaryActivity.setOnClickListener(view -> {
            int value1 = Integer.parseInt(input1.getText().toString());
            int value2 = Integer.parseInt(input2.getText().toString());
            int sum = value1 + value2;

            Intent intent = new Intent(getApplicationContext(), PracticalTest01SecondaryActivity.class);
            intent.putExtra(Constants.SUM, sum);

            activityResultLauncher.launch(intent);
        });

        Intent intent = new Intent(getApplicationContext(), PracticalTest01Service.class);
        intent.putExtra(Constants.INPUT1, input1.getText().toString());
        intent.putExtra(Constants.INPUT2, input2.getText().toString());
        startForegroundService(intent);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.ACTION_STRING);
        broadcastReceiver = new ColocviuBroadcastReceiver();
        registerReceiver(broadcastReceiver, intentFilter, Context.RECEIVER_EXPORTED);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(Constants.INPUT1, input1.getText().toString());
        outState.putString(Constants.INPUT2, input2.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState.containsKey(Constants.INPUT1)) {
            input1.setText(savedInstanceState.getString(Constants.INPUT1));
        } else {
            input1.setText("0");
        }

        if (savedInstanceState.containsKey(Constants.INPUT2)) {
            input2.setText(savedInstanceState.getString(Constants.INPUT2));
        } else {
            input2.setText("0");
        }
    }
}
