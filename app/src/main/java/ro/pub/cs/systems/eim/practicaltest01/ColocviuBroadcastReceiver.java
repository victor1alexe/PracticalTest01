package ro.pub.cs.systems.eim.practicaltest01;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ColocviuBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action != null) {
            Log.d(Constants.BROADCAST_RECEIVER_TAG, action);
            if (action.equals(Constants.ACTION_STRING)) {
                int mean = intent.getIntExtra(Constants.MEAN, -1);
                int geometricMean = intent.getIntExtra(Constants.GEOMETRIC_MEAN, -1);
                Log.d(Constants.BROADCAST_RECEIVER_TAG, "onReceive: " + mean + " " + geometricMean);
            }
        }
    }
}
