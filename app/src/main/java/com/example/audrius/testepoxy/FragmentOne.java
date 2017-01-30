package com.example.audrius.testepoxy;


import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.airbnb.epoxy.EpoxyAdapter;
import com.airbnb.epoxy.EpoxyModel;

public class FragmentOne extends Fragment {

    private MyAdapter adapter;
    private ViewGroup layoutas;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layoutas = (ViewGroup) inflater.inflate(R.layout.fragment_one, container, false);
        RecyclerView recyclerView = (RecyclerView) layoutas.findViewById(R.id.recycler);
        LinearLayoutManager layout = new LinearLayoutManager(getContext());
        layout.setRecycleChildrenOnDetach(true);
        recyclerView.setLayoutManager(layout);
        adapter = new MyAdapter();
        adapter.setListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction()
                        .replace(android.R.id.content, new FragmentTwo())
                        .addToBackStack(null)
                        .commit();
            }
        });
        adapter.fillData();
        recyclerView.setAdapter(adapter);
        return layoutas;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        adapter.setListener(null);
        ((MainActivity) getActivity()).refWatcher.watch(layoutas);
        layoutas = null;
    }

    private static class MyAdapter extends EpoxyAdapter {

        private View.OnClickListener listener;

        MyAdapter() {
            super();
        }

        void fillData() {
            for (int i = 0; i < 10; i++) {
                MyModel model = new MyModel(i);
                model.setListener(listener);
                addModel(model);
            }
        }

        void setListener(View.OnClickListener listener) {
            this.listener = listener;
            for (EpoxyModel<?> model : models) {
                MyModel item = (MyModel) model;
                item.setListener(listener);
            }
        }
    }

    private static class MyModel extends EpoxyModel<View> {

        View.OnClickListener listener;

        MyModel(long id) {
            super(id);
        }

        void setListener(View.OnClickListener listener) {
            this.listener = listener;
        }

        @Override
        protected int getDefaultLayout() {
            return android.R.layout.simple_list_item_1;
        }

        @Override
        public void bind(View view) {
            ((TextView) view.findViewById(android.R.id.text1)).setText(String.valueOf(id()));
            view.setOnClickListener(listener);
        }


    }

}
