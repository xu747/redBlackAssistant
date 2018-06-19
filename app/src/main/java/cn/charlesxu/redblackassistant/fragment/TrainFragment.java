package cn.charlesxu.redblackassistant.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.charlesxu.redblackassistant.BaseFragment;
import cn.charlesxu.redblackassistant.R;

public class TrainFragment extends BaseFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.train_fragment, container, false);
        return view;
    }
}
