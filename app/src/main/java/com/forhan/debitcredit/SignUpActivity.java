package com.forhan.debitcredit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    public static final String regURL = "https://www.thecakeshopbd.com/appsapi/login/saveRegistration";

    int role;
    EditText nameET, usernameET, emailET, phoneNoET, passwordET;

    RadioGroup radioGroup;
    Button signUpBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        nameET = findViewById(R.id.reg_name_et_id);
        usernameET = findViewById(R.id.reg_user_name_et_id);
        emailET = findViewById(R.id.reg_email_et_id);
        phoneNoET = findViewById(R.id.reg_phone_no_et_id);
        passwordET = findViewById(R.id.reg_password_et_id);

        radioGroup = findViewById(R.id.radio_group_id);
        signUpBtn = findViewById(R.id.btn_sign_up_id);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Submit();
                Log.i("Role", String.valueOf(role));
            }
        });
    }

    public void selectedRadio(){
        int selectedId = radioGroup.getCheckedRadioButtonId();

        if (selectedId == R.id.radio_admin_id){
            role = 1;
        }
        else if (selectedId == R.id.radio_user_id){
            role = 2;
        }
        else{
            Toast.makeText(this,"No role selected", Toast.LENGTH_SHORT).show();
        }

    }

    public static final String getmd5ofstring(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte[] messageDigest = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString().toUpperCase(Locale.US); // return md5

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    private void Submit() {
        final String name = nameET.getText().toString();
        final String username = usernameET.getText().toString();
        final String email = emailET.getText().toString();
        final String phoneNo = phoneNoET.getText().toString();
        final String pass = passwordET.getText().toString();
        final String password = getmd5ofstring(pass).toUpperCase(Locale.US); // get md5
        selectedRadio();
        final int roleType = role;
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());


        StringRequest stringRequest = new StringRequest(Request.Method.POST, regURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Volley Result", "" + response); //the response contains the result from the server, a json string or any other object returned by your server

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace(); //log the error resulting from the request for diagnosis/debugging

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> postMap = new HashMap<>();
                postMap.put("name", name);
                postMap.put("username", username);
                postMap.put("email", email);
                postMap.put("phone", phoneNo);
                postMap.put("password", password);
                postMap.put("role", String.valueOf(roleType));
                postMap.put("status", String.valueOf(1));
                return postMap;
            }
        };

        requestQueue.add(stringRequest);
    }
}