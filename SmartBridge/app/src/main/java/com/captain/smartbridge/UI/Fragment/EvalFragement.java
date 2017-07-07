package com.captain.smartbridge.UI.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.captain.smartbridge.R;

/**
 * Created by Captain on 17/7/8.
 */

public class EvalFragement extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";
    public static final String ID = "";
    private  int mPage;
    private View view;

    public static PageFragement newInstance(int page){
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        PageFragement pageFragement = new PageFragement();
        pageFragement.setArguments(args);
        return pageFragement;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        switch (mPage){
            case 0:
                return gradeView(inflater, container);
            case 1:
                return historyView(inflater, container);
            case 2:
                return lifeView(inflater, container);
            case 3:
                return degeView(inflater, container);
            default:
                return null;
        }
    }

    private View gradeView(LayoutInflater inflater, ViewGroup container){
        view = inflater.inflate(R.layout.fragement_grade, container, false);
        return view;

    }

    private View historyView(LayoutInflater inflater, ViewGroup container){
        view = inflater.inflate(R.layout.fragement_history, container, false);
        return view;
    }

    private View lifeView(LayoutInflater inflater, ViewGroup container){
        view = inflater.inflate(R.layout.fragement_life, container, false);
        return view;
    }
    private View degeView(LayoutInflater inflater, ViewGroup container){
        view = inflater.inflate(R.layout.fragement_dege, container, false);
        return view;
    }
}
