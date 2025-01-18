package com.example.appproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.airbnb.lottie.LottieAnimationView;

public class Animation extends AppCompatActivity {
    public String client_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        //
        client_id = getIntent().getStringExtra("clientId");
        // Find the LottieAnimationView in the layout
        LottieAnimationView lottieAnimationView = findViewById(R.id.lottieAnimationView);

        // Play the animation
        lottieAnimationView.playAnimation();

        // Set a delay before starting the RequesterActivity (e.g., 3 seconds)
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Start RequesterActivity after animation
                Intent intent = new Intent(Animation.this, RequesterActivity.class);
                startActivity(intent);
                finish(); // Close the AnimationActivity
            }
        }, 2500); // 3000 milliseconds = 3 seconds
    }
}
