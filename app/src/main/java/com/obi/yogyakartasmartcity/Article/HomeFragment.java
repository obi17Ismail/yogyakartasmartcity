package com.obi.yogyakartasmartcity.Article;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.obi.yogyakartasmartcity.BaseFragment;
import com.obi.yogyakartasmartcity.Data.ArticleModel;
import com.obi.yogyakartasmartcity.R;
import com.obi.yogyakartasmartcity.Network.ArticleSourcesCallback;
import com.obi.yogyakartasmartcity.Data.Respone.ArticleRespone;
import com.obi.yogyakartasmartcity.Network.DataSources;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment implements ArticleSourcesCallback {
    RecyclerView rv_new;
    ViewPager viewPager;

    SwipeRefreshLayout mSwipeRefreshLayout;
    public static RelativeLayout relativeLayout_pb;

    Timer timer;
    private ArticleAdapter articleAdapter;
    private ArrayList<ArticleModel> articleModels = new ArrayList<>();


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_home, container, false);

        articleAdapter = new ArticleAdapter(getContext(), articleModels);

        relativeLayout_pb = view.findViewById(R.id.relativeLayout_pb_home);
        relativeLayout_pb.setVisibility(View.VISIBLE);

        rv_new = view.findViewById(R.id.rv_new_home);
        rv_new.setHasFixedSize(true);
        rv_new.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_new.setAdapter(articleAdapter);

        viewPager = view.findViewById(R.id.vp_home);

        BannerAdapter bannerAdapter = new BannerAdapter(getContext());
        viewPager.setAdapter(bannerAdapter);

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {

                if (getContext() == null)
                    return;

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (viewPager.getCurrentItem() == 0) {
                            viewPager.setCurrentItem(1);
                        } else if (viewPager.getCurrentItem() == 1) {
                            viewPager.setCurrentItem(2);
                        } else {
                            viewPager.setCurrentItem(0);
                        }
                    }
                });
            }
        }, 2000, 4000);

        getDataSources().getArticle(DataSources.URL_ARTICLE, this);

        mSwipeRefreshLayout = view.findViewById(R.id.swiperefresh_home);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDataSources().getArticle(DataSources.URL_ARTICLE, HomeFragment.this);
            }
        });

        return view;
    }

    @Override
    public void onSuccess(ArticleRespone respone) {
        relativeLayout_pb.setVisibility(View.GONE);
        mSwipeRefreshLayout.setRefreshing(false);

        articleModels = respone.getResults();
        articleAdapter.refill(articleModels);
    }

    @Override
    public void onFailed(String error) {
        relativeLayout_pb.setVisibility(View.GONE);
        mSwipeRefreshLayout.setRefreshing(false);

        Toast.makeText(getContext() , error, Toast.LENGTH_SHORT).show();
    }

}
