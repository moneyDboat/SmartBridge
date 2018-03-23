package com.captain.smartbridge.UI.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by captain on 18-3-23.
 */

public class fourgFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";
    public static final String ID = "";
    private int mPage;
    private View view;

    public static fourgFragment newInstance(int page){
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        fourgFragment fragment = new fourgFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        switch (mPage) {
            case 0:
                return firstView(inflater, container);
            case 1:
                return secView(inflater, container);
            case 2:
                return thirdView(inflater, container);
            default:
                return null;
        }
    }

    private View firstView(LayoutInflater inflater, ViewGroup container){
        return null;
    }

    private View secView(LayoutInflater inflater, ViewGroup container){
        return null;
    }

    private View thirdView(LayoutInflater inflater, ViewGroup container){
        return null;
    }
}
