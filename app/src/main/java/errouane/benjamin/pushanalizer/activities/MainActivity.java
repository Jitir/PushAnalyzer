package errouane.benjamin.pushanalizer.activities;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import errouane.benjamin.pushanalizer.Common;
import errouane.benjamin.pushanalizer.R;


public class MainActivity extends Activity {
    private boolean scanning = false;
    private BluetoothAdapter bluetoothAdapter;
    private final static int REQUEST_ENABLE_BT = 1;
    private Handler handler = new Handler();
    private static final long SCAN_PERIOD = 3000;
    private ListView deviceList;
    private ProgressBar bar;
    private StopScanning stopper;
    private Button scanButton, loadDataButton;
    private ArrayAdapter<BluetoothDevice> adapter;
    private List<BluetoothDevice> foundDevices;

    private static final int FILE_CHOOSER_CODE = 0;

    public MainActivity() {
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Ensures Bluetooth is available on the device and it is enabled. If not,
        // displays a dialog requesting user permission to enable Bluetooth.
        if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }

        adapter.clear();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        foundDevices = new ArrayList<BluetoothDevice>();
        adapter = new ArrayAdapter<BluetoothDevice>(this, android.R.layout.simple_list_item_1, foundDevices);
        setContentView(R.layout.activity_main);

        bar = (ProgressBar) findViewById(R.id.progressBar2);
        deviceList = (ListView) findViewById(R.id.listView);
        deviceList.setAdapter(adapter);
        deviceList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                scanning = false;
                bar.setVisibility(ProgressBar.INVISIBLE);
                bluetoothAdapter.stopLeScan(leScanCallback);
                handler.removeCallbacks(stopper);
                scanButton.setText(R.string.start_scan);


                Intent intent = new Intent(MainActivity.this, MainTabbedActivity.class);
                intent.putExtra(BluetoothDevice.EXTRA_DEVICE, foundDevices.get(position));
                startActivity(intent);
            }
        });
        scanButton = (Button) findViewById(R.id.startScanButton);
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scan();
            }
        });

        loadDataButton = (Button) findViewById(R.id.loadData);
        loadDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFile();
            }
        });

        // Use this check to determine whether BLE is supported on the device. Then
        // you can selectively disable BLE-related features.
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, R.string.ble_not_supported, Toast.LENGTH_SHORT).show();
            finish();
        }

        // Initializes Bluetooth adapter.
        final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        bluetoothAdapter = bluetoothManager.getAdapter();
    }

    private void loadFile() {
        if(!Common.isExternalStorageAvailable(this)) {
            return;
        }

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        //intent.addCategory(Intent.CATEGORY_OPENABLE);

        try{
            startActivityForResult(Intent.createChooser(intent, "Select a file to load"), FILE_CHOOSER_CODE);
        } catch(ActivityNotFoundException ex) {
            Toast.makeText(this, "Please install a File Manager.", Toast.LENGTH_SHORT).show();
        }


        //File file;
//
       // Intent intent = new Intent(MainActivity.this, MainTabbedActivity.class);
        //intent.putExtra(String.class.getName(), file);
       // startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case FILE_CHOOSER_CODE:
                if(data == null)
                    return;

                Uri uri = data.getData();

                Intent intent = new Intent(MainActivity.this, MainTabbedActivity.class);
                intent.putExtra(String.class.toString(), uri.toString());
                startActivity(intent);
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void scan() {
        if(!scanning) {
            handler.postDelayed((stopper = new StopScanning()), SCAN_PERIOD);
            scanning = true;
            //foundDevices.clear();
            adapter.clear();
            //adapter.notifyDataSetChanged();
            bar.setVisibility(ProgressBar.VISIBLE);
            bluetoothAdapter.startLeScan(leScanCallback);
            scanButton.setText(R.string.stop_scan);
        } else {
            scanning = false;
            bar.setVisibility(ProgressBar.INVISIBLE);
            bluetoothAdapter.stopLeScan(leScanCallback);
            handler.removeCallbacks(stopper);
            scanButton.setText(R.string.start_scan);
        }
    }

    private BluetoothAdapter.LeScanCallback leScanCallback =
        new BluetoothAdapter.LeScanCallback() {
            @Override
            public void onLeScan(final BluetoothDevice device, int rssi,
                                 byte[] scanRecord) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    adapter.add(device);
                }
            });
            }
        };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up scanButton, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class StopScanning implements Runnable {
        @Override
        public void run() {
            scanning = false;
            bar.setVisibility(ProgressBar.INVISIBLE);
            bluetoothAdapter.stopLeScan(leScanCallback);
            scanButton.setText(R.string.start_scan);
        }
    }
}
