package errouane.benjamin.pushanalizer.activities;

import android.app.AlertDialog;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothProfile;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;
import java.util.UUID;

import errouane.benjamin.pushanalizer.Common;
import errouane.benjamin.pushanalizer.ViewPagerFragment;
import errouane.benjamin.pushanalizer.adapters.MyPagerAdapter;
import errouane.benjamin.pushanalizer.R;
import errouane.benjamin.pushanalizer.dataListener.RotationDataEvent;
import errouane.benjamin.pushanalizer.fragments.CurrentValuesFragment;
import errouane.benjamin.pushanalizer.fragments.GraphsFragment;
import errouane.benjamin.pushanalizer.fragments.MoreStatsFragment;


public class MainTabbedActivity extends FragmentActivity {
    private FragmentPagerAdapter adapter;
    private BluetoothDevice device;
    private BluetoothGatt gatt;
    private float diameter;
    private UUID PushAnalyzerUuid = UUID.fromString("00002000-0000-1000-8000-00805f9b34fb");

    private ViewPagerFragment[] fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tabbed);

        fragments = new ViewPagerFragment[3];
        fragments[0] = new MoreStatsFragment();
        fragments[1] = new CurrentValuesFragment();
        fragments[2] = new GraphsFragment();

        device = getIntent().getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
        adapter = new MyPagerAdapter(getSupportFragmentManager(), fragments);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);


        if(device != null) {
            gatt = device.connectGatt(this, false, new BluetoothGattCallback() {


                @Override
                public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
                    super.onConnectionStateChange(gatt, status, newState);
                    if(newState == BluetoothProfile.STATE_CONNECTED) {
                        gatt.discoverServices();
                    } else if(newState == BluetoothProfile.STATE_DISCONNECTED) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                new AlertDialog.Builder(MainTabbedActivity.this)
                                        .setTitle("Warning")
                                        .setMessage("Lost connection to device!")
                                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int whichButton) {
                                                closeConnection();
                                                MainTabbedActivity.this.finish();
                                            }
                                        }).show();
                            }
                        });
                    }
                }

                @Override
                public void onServicesDiscovered(BluetoothGatt gatt, int status) {
                    super.onServicesDiscovered(gatt, status);
                    List<BluetoothGattService> services = gatt.getServices();
                    BluetoothGattCharacteristic characteristic = null;
                    for(BluetoothGattService s : services) {
                        if(s.getUuid().equals(PushAnalyzerUuid)) {
                            characteristic = s.getCharacteristics().get(0);
                            break;
                        }
                    }
                    if(characteristic != null) {
                        Log.e("PushAnalyzer", "Setting notification!");
                        gatt.setCharacteristicNotification(characteristic, true);
                        BluetoothGattDescriptor descriptor = characteristic.getDescriptors().get(0);
                        descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                        gatt.writeDescriptor(descriptor);
                    }
                }

                @Override
                public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
                    int[] accelerometer = new int[3];
                    accelerometer[0] = characteristic.getIntValue(BluetoothGattCharacteristic.FORMAT_SINT16, 0);
                    accelerometer[1] = characteristic.getIntValue(BluetoothGattCharacteristic.FORMAT_SINT16, 2);
                    accelerometer[2] = characteristic.getIntValue(BluetoothGattCharacteristic.FORMAT_SINT16, 4);
                    int data = characteristic.getIntValue(BluetoothGattCharacteristic.FORMAT_UINT16, 6);
                    updateSpeed(data);
                }
            });
            gatt.discoverServices();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_tabbed, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent i = new Intent(this, SettingsActivity.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void closeConnection() {
        if(gatt != null) {
            gatt.close();
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Warning")
                .setMessage("Going back will close the connection to the Bluetooth-Device!")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        closeConnection();
                        MainTabbedActivity.this.finish();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Do nothing.
                    }
                }).show();

    }

    @Override
    protected void onResume() {
        super.onResume();
        diameter = Float.parseFloat(PreferenceManager.getDefaultSharedPreferences(this).getString("wheelDiameter", "60"));
    }

    private void updateSpeed(float speed) {
        speed = (float) Common.rotationalSpeedToSpeed(speed, diameter);
        speed = (speed * 10) / 10f;

        for(ViewPagerFragment f : fragments) {
            f.newRotationData(new RotationDataEvent(0f, speed));
        }
    }
}