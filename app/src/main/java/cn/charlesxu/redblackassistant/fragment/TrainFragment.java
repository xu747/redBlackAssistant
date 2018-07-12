package cn.charlesxu.redblackassistant.fragment;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cn.charlesxu.redblackassistant.BaseFragment;
import cn.charlesxu.redblackassistant.R;
import cn.charlesxu.redblackassistant.adapter.SuperviseTicketAdapter;
import cn.charlesxu.redblackassistant.model.SuperviseTicket;

public class TrainFragment extends BaseFragment {
    private List<SuperviseTicket> superviseTicketList = new ArrayList<>();
    private RecyclerView superviseRecyclerView;
    private SuperviseTicketAdapter superviseTicketAdapter;
    private Handler handler = new Handler();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.train_fragment, container, false);

        getAllSuperviseTicket();

        superviseRecyclerView = view.findViewById(R.id.supervise_recyclerView);
        superviseRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        superviseTicketAdapter = new SuperviseTicketAdapter(superviseTicketList,getActivity());
        superviseRecyclerView.setAdapter(superviseTicketAdapter);

        supervisingTicket();


        return view;
    }


    void getAllSuperviseTicket() {
        List<SuperviseTicket> resultSuperviseTicketList = DataSupport.findAll(SuperviseTicket.class);
        if(superviseTicketList != null){
            superviseTicketList.clear();
        }
        superviseTicketList.addAll(resultSuperviseTicketList);
        System.out.println("监控订单个数:" + superviseTicketList.size());
        for (SuperviseTicket superviseTicket : superviseTicketList){
            System.out.println(superviseTicket.getStationTrainCode());
        }

    }

    void refreshAllSuperviseTicket(){
        getAllSuperviseTicket();
        superviseTicketAdapter.notifyDataSetChanged();
    }


    boolean isCreated = true;

    /**
     * 此方法目前仅适用于标示ViewPager中的Fragment是否真实可见
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (!isCreated) {
            return;
        }

        if (isVisibleToUser) {
            refreshAllSuperviseTicket();
        }
    }


    public void supervisingTicket(){
        final Timer timer = new Timer();
        final TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                getAllSuperviseTicket();
                for(SuperviseTicket superviseTicket: superviseTicketList){
                    if(!superviseTicket.isPause()){
                        superviseTicket.setRequestCount(superviseTicket.getRequestCount() + 1);
                    }
                    superviseTicket.save();
                }
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        superviseTicketAdapter.notifyDataSetChanged();
                    }
                };
                handler.post(runnable);
            }
        };

        timer.schedule(timerTask, 3000, 3000);
    }


}
