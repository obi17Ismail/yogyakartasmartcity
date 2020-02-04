package com.obi.yogyakartasmartcity.Announcement;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.obi.yogyakartasmartcity.BaseFragment;
import com.obi.yogyakartasmartcity.Data.AnnouncementModel;
import com.obi.yogyakartasmartcity.R;
import com.obi.yogyakartasmartcity.Data.Respone.AnnouncementRespone;
import com.obi.yogyakartasmartcity.Network.AnnouncementSourcesCallback;
import com.obi.yogyakartasmartcity.Network.DataSources;

import java.util.ArrayList;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 */
public class AnnouncementFragment extends BaseFragment implements AnnouncementSourcesCallback {
    RecyclerView rv_announcement;

    public static RelativeLayout relativeLayout_pb;

    private AnnouncementAdapter announcementAdapter;
    private ArrayList<AnnouncementModel> announcementModels = new ArrayList<>();

    public AnnouncementFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_announcement, container, false);

        announcementAdapter = new AnnouncementAdapter(getContext(), announcementModels);

        relativeLayout_pb = view.findViewById(R.id.relativeLayout_pb_announcement);
        relativeLayout_pb.setVisibility(View.VISIBLE);

        rv_announcement = view.findViewById(R.id.rv_announcement);
        rv_announcement.setHasFixedSize(true);
        rv_announcement.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_announcement.setAdapter(announcementAdapter);

        getDataSources().getAnnouncement(DataSources.URL_ANNOUNCEMENT, this);

        FloatingActionButton fab = view.findViewById(R.id.fab_add_announcement);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectivityManager cm = (ConnectivityManager) getApplicationContext()
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                if (null != activeNetwork) {
                    Intent intent = new Intent(getContext(), AddAnnouncement.class);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                } else {
                    Snackbar.make(view, "Internet kamu tidak terhubung!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });

        return view;
    }

    @Override
    public void onSuccess(AnnouncementRespone respone) {
        relativeLayout_pb.setVisibility(View.GONE);

        announcementModels = respone.getResults();
        announcementAdapter.refill(announcementModels);
    }

    @Override
    public void onFailed(String error) {
        relativeLayout_pb.setVisibility(View.GONE);

        Toast.makeText(getContext() , error, Toast.LENGTH_SHORT).show();
    }

}
