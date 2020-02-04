package com.obi.yogyakartasmartcity;


import androidx.fragment.app.Fragment;

import com.obi.yogyakartasmartcity.Network.DataSources;


public class BaseFragment extends Fragment {

    public static final String KEY_ARTICLE = "article";

    public DataSources getDataSources() {
        return new DataSources();
    }
}
