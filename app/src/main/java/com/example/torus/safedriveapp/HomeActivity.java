package com.example.torus.safedriveapp;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by TORUS on 10/7/2017.
 */

public class HomeActivity extends AppCompatActivity {
    boolean mbound=false;
   // MonitorSpeed monitorSpeed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_layout);
        Button button=(Button)findViewById(R.id.button_download);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // startService(new Intent(UnboundService.this,UnboundDownload.class));

                if(mbound)
                {
                   // monitorSpeed.monitor_speed();
                    //Toast.makeText(getApplicationContext(),"RandomNumber="+num,Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Not Bound",Toast.LENGTH_SHORT).show();

                }
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent i=new Intent(HomeActivity.this,MonitorSpeed.class);
        startService(new Intent(HomeActivity.this,MonitorSpeed.class));
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.stopservice,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.stop_service)
        {

            Toast.makeText(getApplicationContext(), "Inside Menu", Toast.LENGTH_SHORT).show();

            stopService(new Intent(HomeActivity.this,MonitorSpeed.class));

        }
        return super.onOptionsItemSelected(item);
    }
}
