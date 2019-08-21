package com.example.firebase;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class GalleryActivity extends AppCompatActivity {
    EditText addname,addphone,addaddress;
    Button savebtn;
    String name,phone,address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        addname = (EditText)findViewById(R.id.add_name);
        addphone = (EditText)findViewById(R.id.add_phone);
        addaddress = (EditText)findViewById(R.id.add_address);

        savebtn = (Button)findViewById(R.id.save_btn);

        savebtn.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {

                name = addname.getText().toString();
                phone = addphone.getText().toString();
                address = addaddress.getText().toString();
                String url = "http://192.168.100.122/Apis/AddRecord.php";
                RequestQueue requestQueue = Volley.newRequestQueue(GalleryActivity.this);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new
                        Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response) {

                                Toast.makeText(GalleryActivity.this, "" + response,
                                        Toast.LENGTH_SHORT).show();
                            }
                        }, new Response.ErrorListener()
                        {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(GalleryActivity.this, "Error in Login"
                                        + error.toString(),
                                Toast.LENGTH_SHORT).show();

                    }
                })
                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError
                    {
                        Map<String, String> params = new HashMap<>();
                        params.put("name", name);
                        params.put("contact_no", phone);
                        params.put("address", address);
                        return params;
                    }
                };
                requestQueue.add(stringRequest);
            }
        });

    }
}
