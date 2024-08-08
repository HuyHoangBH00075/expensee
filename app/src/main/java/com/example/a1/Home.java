package com.example.a1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class Home extends AppCompatActivity {

    private ImageView profileImageView;
    private ImageView logoutImageView; // Khai báo đúng ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        profileImageView = findViewById(R.id.profile);
        logoutImageView = findViewById(R.id.logoutImageView); // Tham chiếu đúng ID

        // Nhận thông tin email từ Intent
        String userEmail = getIntent().getStringExtra("USER_EMAIL");

        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Profile.class);
                intent.putExtra("USER_EMAIL", userEmail); // Chuyển email đến trang Profile
                startActivity(intent);
            }
        });

        logoutImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, LoginScreen.class);
                startActivity(intent);
                finish(); // Kết thúc hoạt động hiện tại để quay lại trang đăng nhập
            }
        });
    }
}
