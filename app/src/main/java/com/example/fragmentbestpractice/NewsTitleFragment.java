package com.example.fragmentbestpractice;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NewsTitleFragment extends Fragment {
    private boolean isTowPane;
    private MyDatabaseHelper dbHelper;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        MainActivity mainActivity = (MainActivity) getActivity();
        dbHelper = new MyDatabaseHelper(mainActivity,"Vocabulary_Book.db",null,1);

        View view = inflater.inflate(R.layout.news_title_frag,container,false);
        RecyclerView newsTitleRecyclerView = (RecyclerView) view.findViewById(R.id.news_title_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        newsTitleRecyclerView.setLayoutManager(layoutManager);
        NewsAdapter adapter = new NewsAdapter(getNews());
        newsTitleRecyclerView.setAdapter(adapter);

        return view;
    }
    private List<News> getNews(){
        List<News> newsList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("Vocabulary", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                News news = new News();
                String user2 = cursor.getString(cursor.getColumnIndex("Word"));
                news.setTitle(user2);
                String user1 = cursor.getString(cursor.getColumnIndex("Mean"));
                news.setContent(user1);
                newsList.add(news);

            } while (cursor.moveToNext()) ;
        }
        cursor.close();
        return newsList;
    }
   /* private String getRandomLengthContent(String content){
        Random random = new Random();
        int length = random.nextInt(20)+1;
        StringBuilder builder =new StringBuilder();
        for(int i=0;i<length ;i++){
            builder.append(content);
        }
        return  builder.toString();
    }*/
    @Override
    public  void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        if(getActivity().findViewById(R.id.news_content_layout)!=null){
            isTowPane=true;
        }
        else isTowPane = false;

    }

    class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

        private List<News> mNewsList;
        class ViewHolder extends RecyclerView.ViewHolder{ //定义内部类 ViewHolder
            TextView newsTitleText;
            public  ViewHolder(View view){  //内部类的构造函数
                super(view);
                newsTitleText = (TextView) view.findViewById(R.id.news_title);
            }
        }
        public  NewsAdapter(List<News> newsList){  //类的构造函数
            mNewsList = newsList;
        }
        @Override
        public  ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){  //重写onCreateViewHolder方法，创建ViewHolder实例
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item,parent,false);
            final ViewHolder holder = new ViewHolder(view);
           view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                News news = mNewsList.get(holder.getAdapterPosition());
                if(isTowPane){
                    NewsContentFragment newsContentFragment = (NewsContentFragment) getFragmentManager().findFragmentById(R.id.news_content_fragment);
                    newsContentFragment.refresh(news.getTitle(),news.getContent());
                }
                else{
                    NewsContentActivity.actionStart(getActivity(),news.getTitle(),news.getContent());
                }
            }
        });
        return holder;
        }
        @Override
        public void onBindViewHolder(ViewHolder holder, int position){ //重写onBindViewHolder方法，当屏幕滚动时，得到news实例，将数据设置到ViewHolder的TextView
            News news = mNewsList.get(position);
            holder.newsTitleText.setText(news.getTitle());
        }
        @Override
        public int getItemCount(){
            return mNewsList.size();
        }
    }

}
