package ro.pub.cs.systems.eim.practicaltest01;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ProcessThread extends Thread {

        private int sum;
        private int mean;
        private int geometricMean;
        private int input1, input2;
        private boolean isRunning = true;

        Context context;

        public ProcessThread(Context context, int input1, int input2) {
            this.input1 = input1;
            this.input2 = input2;
            this.context = context;
            sum = input1 + input2;
            mean = (input1 + input2) / 2;
            geometricMean = (int) Math.sqrt(input1 * input2);
        }

        @Override
        public void run() {
            while (isRunning) {
                sleep();
                sendMessage();
            }
        }

        private void sleep() {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }

        private void sendMessage() {
            Intent intent = new Intent();
            intent.setAction(Constants.ACTION_STRING);
            intent.putExtra(Constants.MEAN, mean);
            intent.putExtra(Constants.GEOMETRIC_MEAN, geometricMean);
            context.sendBroadcast(intent);

            Log.d(Constants.SERVICE_TAG, "Message sent: " + mean + " " + geometricMean);
        }

        public void stopThread() {
            isRunning = false;
        }
}
