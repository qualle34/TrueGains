package com.qualle.shapeup;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.ListFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qualle.shapeup.placeholder.PlaceholderContent;

public class ChartListFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_chart_list, container, false);
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();

        Fragment fragment = ChartFragment.newInstance(45, 100);
        Fragment fragment2 = ChartFragment.newInstance(20, 100);
        Fragment fragment3 = ChartFragment.newInstance(70, 100);

        ft.add(R.id.chart_container_first, fragment, null);
        ft.add(R.id.chart_container_second, fragment2, null);
        ft.add(R.id.chart_container_third, fragment3, null);

//        for (String catalog : mCatalogs) {
//            Fragment fragment = HighlightedProductFragment.newInstance(catalog);
//            ft.add(R.id.container, fragment, catalog);
//        }
        ft.commit();

        return v;
    }
}