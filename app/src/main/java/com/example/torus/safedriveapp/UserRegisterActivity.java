package com.example.torus.safedriveapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by TORUS on 10/5/2017.
 */

public class UserRegisterActivity extends AppCompatActivity implements View.OnClickListener{
    Button button_save;
    EditText editText_name,editText_number;
    CheckBox checkBox_sms;
    PreferenceDetails preferenceDetails;
    Context context;
    boolean sms_flag=false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_register_layout);
        init();

    }
    public  void  init()
    {
        context=this;
        preferenceDetails=new PreferenceDetails(context);
        editText_name=(EditText)findViewById(R.id.editText_guardian);
        editText_number=(EditText)findViewById(R.id.editText_phone);
        checkBox_sms=(CheckBox)findViewById(R.id.checkBox_sms);
        button_save=(Button)findViewById(R.id.button_save);
        button_save.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        String guardian_name=editText_name.getText().toString();
        String guardian_phone=editText_number.getText().toString();
        sms_flag=checkBox_sms.isChecked();
        Toast.makeText(getApplicationContext(),"checkbox="+sms_flag,Toast.LENGTH_LONG).show();
        if(validate(guardian_name,guardian_phone,sms_flag))
        {
            preferenceDetails.setGuardian_name(guardian_name);
            preferenceDetails.setGuardian_phone(guardian_phone);
            preferenceDetails.setSpeed("30");
            preferenceDetails.setCheck_safety(true);

            Toast.makeText(getApplicationContext(),"Details="+preferenceDetails.getGuardian_name()+"\t"+preferenceDetails.getGuardian_phone(),Toast.LENGTH_LONG).show();
            startActivity(new Intent(UserRegisterActivity.this,HomeActivity.class));
            finish();
        }

    }

    public boolean validate(String name,String number,boolean checkbox)
    {
        if(name.isEmpty())
        {
            editText_name.setError("Name can t be null");
            return  false;
        }
        if(number.isEmpty())
        {
            editText_number.setError("Phone-Number can t be null");
            return  false;
        }
        if(number.length()!=10)
        {
            editText_number.setError("Invalid Phone Number");
            return  false;
        }
        if(!sms_flag)
        {
            AlertDialog.Builder aBuilder=new AlertDialog.Builder(context);
            aBuilder.setMessage("Alert...!!!");
            aBuilder.setMessage("Are you sure ");
            aBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                 sms_flag=true;
                }
            });
            aBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    sms_flag=false;
                }
            });
            aBuilder.show();

            
        }
        return  true;
    }
}
