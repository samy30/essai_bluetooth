package com.example.user.essai_bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Set;

public class MainActivity extends AppCompatActivity {

    Button OnOf = (Button)findViewById(R.id.OnOf);
    Button envoyer =(Button)findViewById(R.id.envoyer);
    EditText text=(EditText)findViewById(R.id.myText);
    BluetoothAdapter bluetoothAdapter;
    private Set<BluetoothDevice> devices;
    private  BroadcastReceiver bluetoothReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        OnOf = (Button)findViewById(R.id.OnOf);
        envoyer =(Button)findViewById(R.id.envoyer);
        text=(EditText)findViewById(R.id.myText);

        OnOf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                if (bluetoothAdapter == null)
                    Toast.makeText(getApplicationContext(), "Pas de Bluetooth", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(), "Avec Bluetooth", Toast.LENGTH_SHORT).show();



                if (!bluetoothAdapter.isEnabled()) {
                    bluetoothAdapter.enable();
                }
                devices = bluetoothAdapter.getBondedDevices();
                for (BluetoothDevice blueDevice : devices) {
                    Toast.makeText(getApplicationContext(), "Device = " + blueDevice.getName(), Toast.LENGTH_SHORT).show();
                }

                bluetoothReceiver = new BroadcastReceiver() {
                    public void onReceive(Context context, Intent intent) {
                        String action = intent.getAction();
                        if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                            Toast.makeText(getApplicationContext(), "New Device = " + device.getName(), Toast.LENGTH_SHORT).show();
                        }
                    }
                };
                IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
                registerReceiver(bluetoothReceiver, filter);
                bluetoothAdapter.startDiscovery();

                Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
                startActivity(discoverableIntent);
            }
        });


    }
}
