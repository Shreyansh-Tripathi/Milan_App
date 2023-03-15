package com.highfliers.high_fliers_android_hack2k21;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.highfliers.high_fliers_android_hack2k21.TabFragments.AnonymousFragment;
import com.highfliers.high_fliers_android_hack2k21.TabFragments.InterestsFragment;
import com.highfliers.high_fliers_android_hack2k21.TabFragments.RestrictedFragment;

public class PagerAdapter extends FragmentStateAdapter {
    Context context;
    public PagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        context=fragmentActivity;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:

                return new InterestsFragment(context);

            case 1:

                return new AnonymousFragment(context);

            default:

                return new RestrictedFragment(context);
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
