package com.example.StudyMate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class WalkThrough extends AppCompatActivity {

    private OnboardingAdapter onboardingAdapter;
    private LinearLayout layoutOnboardingIndicators;
    private MaterialButton buttonOnboardAction;

    @SuppressLint("ApplySharedPref")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walk_through);

        layoutOnboardingIndicators=findViewById(R.id.layoutOnboardingIndicators);
        buttonOnboardAction=findViewById(R.id.buttonOnboardAction);

        setupOnboardingItems();
        ViewPager2 onboardingViewPager=findViewById(R.id.onboardingViewPager);
        onboardingViewPager.setAdapter(onboardingAdapter);
        setupOnboardingIndicators();
        setCurrentOnboardingIndicators(0);

        if (isOpenAlready()) {
            Intent intent = new Intent(WalkThrough.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {
            SharedPreferences.Editor editor = getSharedPreferences("slide", MODE_PRIVATE).edit();
            editor.putBoolean("slide", true);
            editor.commit();
        }

        onboardingViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentOnboardingIndicators(position);
            }
        });


        buttonOnboardAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onboardingViewPager.getCurrentItem()+1<onboardingAdapter.getItemCount()) {
                    onboardingViewPager.setCurrentItem(onboardingViewPager.getCurrentItem()+1);
                }
                startActivity(new Intent(WalkThrough.this,LoginActivity.class));
                finish();
            }
        });
    }

    private boolean isOpenAlready() {
        SharedPreferences sharedPreferences=getSharedPreferences("slide",MODE_PRIVATE);
        boolean result=sharedPreferences.getBoolean("slide",false);
        return result;
    }

    private void setupOnboardingItems() {
        List<OnboardingItem> onboardingItems =new ArrayList<>();
        OnboardingItem itemSearch = new OnboardingItem();
        itemSearch.setTitle("Looking for Last minute notes ?");
        itemSearch.setDescription("Don't worry we got handy notes, formula sheets and many more for you.");
        itemSearch.setImage(R.drawable.lastminnotes);

        OnboardingItem itemNature = new OnboardingItem();
        itemNature.setTitle("Scan and Upload");
        itemNature.setDescription("Be a contributer and upload notes as well.");
        itemNature.setImage(R.drawable.scanandupload);

        OnboardingItem itemPay = new OnboardingItem();
        itemPay.setTitle("Get Access");
        itemPay.setDescription("You can get access to thousands of scanned notes,\nmindmaps, ppts and many more.");
        itemPay.setImage(R.drawable.getaccess);



        onboardingItems.add(itemSearch);
        onboardingItems.add(itemNature);
        onboardingItems.add(itemPay);

        onboardingAdapter = new OnboardingAdapter(onboardingItems);
    }
    private void setupOnboardingIndicators() {
        ImageView[] indicators = new ImageView[onboardingAdapter.getItemCount()];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(8,0,8,0);
        for(int i=0;i<indicators.length;i++) {
            indicators[i]=new ImageView(getApplicationContext());
            indicators[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.onboarding_indicator_inactive));
            indicators[i].setLayoutParams(layoutParams);
            layoutOnboardingIndicators.addView(indicators[i]);
        }
    }
    @SuppressLint("SetTextI18n")
    private void setCurrentOnboardingIndicators(int index) {
        int childCount = layoutOnboardingIndicators.getChildCount();
        for(int i=0;i<childCount;i++) {
            ImageView imageView=(ImageView) layoutOnboardingIndicators.getChildAt(i);
            if(i == index) {
                imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.onboarding_indicator_active));

            }
            else {
                imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.onboarding_indicator_inactive));

            }
        }
        if(index==onboardingAdapter.getItemCount()-1) {
            buttonOnboardAction.setText("Start");
        }
        else {
            buttonOnboardAction.setText("Skip");
        }

    }
}