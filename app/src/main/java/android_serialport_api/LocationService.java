package android_serialport_api;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LocationService extends Service {

    private final static String TAG = LocationService.class.getSimpleName();
    public static Object LocationCallback;

    private LocationManager mLocationManager;
    public MyBinder mybinder = new MyBinder();
    private final static int MIN_TIME = 0;
    private final static int MIN_DISTANCE = 0;
    private LocationCallback mLocationCallback;
    private LocationListener locationListener = new LocationListener();

    @Override
    public void onCreate() {
        super.onCreate();
//        NotificationChannel channel = new NotificationChannel("0", "background gps service",
//                NotificationManager.IMPORTANCE_HIGH);
//        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        manager.createNotificationChannel(channel);
//
//        Notification notification = new Notification.Builder(getApplicationContext(), "0").build();
//        startForeground(1, notification);
        Notification notification = new Notification.Builder(this).getNotification();
        this.startForeground(1, notification);
    }

    public interface LocationCallback {
        void onLocation(Location location);
    }

    public void setCallback(LocationCallback mLocationCallback) {
        this.mLocationCallback = mLocationCallback;
    }

    private class LocationListener implements android.location.LocationListener {
        @Override
        public void onLocationChanged(Location location) {
            Log.e(TAG, "onLocationChanged: lat: " + location.getLatitude() +
                    ", long: " + location.getLongitude());
            if (mLocationCallback != null) {
                mLocationCallback.onLocation(location);
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.d(TAG, "onStatusChanged: ");
        }

        @Override
        public void onProviderEnabled(String provider) {
            Log.d(TAG, "onProviderEnabled: ");
        }

        @Override
        public void onProviderDisabled(String provider) {
            Log.d(TAG, "onProviderDisabled: ");
        }
    }

    private String getTime() {
        long timeStamp = System.currentTimeMillis();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        Date date = new Date(timeStamp);
        return simpleDateFormat.format(date);
    }

    public class MyBinder extends Binder {
        public LocationService getService() {
            return LocationService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mybinder;
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @SuppressLint("MissingPermission")
    public void startLocation() {
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (isTurnOn()) {
            String gpsProvider = LocationManager.GPS_PROVIDER;
            mLocationManager.requestLocationUpdates(gpsProvider, MIN_TIME, MIN_DISTANCE, locationListener);
            String networkProvider = LocationManager.NETWORK_PROVIDER;
            mLocationManager.requestLocationUpdates(networkProvider, MIN_TIME, MIN_DISTANCE, locationListener);
        } else {
            Toast.makeText(this, "Please turn on gps or network provider", Toast.LENGTH_LONG)
                    .show();
        }
    }

    private boolean isTurnOn() {
        boolean gps = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean network = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        Log.d(TAG, "isTurnOn: gps = " + gps + ", network = " + network);
        return gps || network;
    }

    public void stopLocation() {
        if (mLocationManager != null) {
            mLocationManager.removeUpdates(locationListener);
        }
    }

    @Override
    public void onDestroy() {
        stopForeground(true);
        stopSelf();
        super.onDestroy();
    }
}