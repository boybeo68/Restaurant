package vn.myclass.restaurant.View;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import vn.myclass.restaurant.R;

public class Resetpass_Activity extends AppCompatActivity {
    EditText edEmailRS;
    TextView txtDangKyRS;
    Button btnGuiEmail;
    FirebaseAuth auth ;
    String TAG="ResetPass";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_resetpass);

        auth=FirebaseAuth.getInstance();
        edEmailRS= (EditText) findViewById(R.id.edEmailReset);
        txtDangKyRS= (TextView) findViewById(R.id.txtDangKyRS);
        btnGuiEmail= (Button) findViewById(R.id.btnGuiEmail);
        btnGuiEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=edEmailRS.getText().toString();
                boolean kiemtraEmail=kiemTraEmail(email);
                if (kiemtraEmail){
                    auth.sendPasswordResetEmail(email)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(Resetpass_Activity.this,getString(R.string.VuiLongKiemTraEmail),Toast.LENGTH_SHORT).show();
                                        Intent intent=new Intent(Resetpass_Activity.this,DangNhapActivity.class);
                                        startActivity(intent);
                                        Log.d(TAG, "Email sent.");
                                    }
                                }
                            });
                }else {
                    Toast.makeText(Resetpass_Activity.this,getString(R.string.EmailKhongHopLe),Toast.LENGTH_SHORT).show();
                }
            }
        });
        txtDangKyRS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Resetpass_Activity.this,DangKy_Activity.class);
                startActivity(intent);
            }
        });
    }
    public boolean kiemTraEmail(String email){
       return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
