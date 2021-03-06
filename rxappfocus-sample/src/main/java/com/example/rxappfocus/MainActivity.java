package com.example.rxappfocus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.gramboid.rxappfocus.AppFocusProvider;

import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;

public class MainActivity extends AppCompatActivity {

    private Subscription     sub;
    private AppFocusProvider focusProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        focusProvider = ((App) getApplication()).getFocusProvider();

        findViewById(R.id.button_change_activity).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SecondActivity.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        sub = focusProvider
                .getAppFocus()
                .filter(new Func1<Boolean, Boolean>() {
                    @Override public Boolean call(Boolean visible) {
                        return visible;
                    }
                })
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean visible) {
                        Log.d("MainActivity", "We are visible!");
                    }
                });
    }

    @Override
    protected void onStop() {
        super.onStop();
        sub.unsubscribe();
    }
}
