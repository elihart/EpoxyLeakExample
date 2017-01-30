package com.example.audrius.testepoxy;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


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

  private static class MyAdapter extends RecyclerView.Adapter<Holder> {

    private View.OnClickListener listener;

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
      return new Holder(parent);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
      holder.setListener(listener);
    }

    @Override
    public int getItemCount() {
      return 10;
    }

    void setListener(View.OnClickListener listener) {
      this.listener = listener;
      notifyDataSetChanged();
    }
  }

  static class Holder extends RecyclerView.ViewHolder {

    public Holder(ViewGroup parent) {
      super(LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false));
      ((TextView) itemView.findViewById(android.R.id.text1)).setText(String.valueOf("text"));

    }

    void setListener(View.OnClickListener listener) {
      itemView.setOnClickListener(listener);
    }
  }
}
