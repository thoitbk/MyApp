package tho.myapplication;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArticleListFragment extends Fragment {

    private OnArticleListFragmentListener listener;

    private static List<Article> articleNameList = new ArrayList<Article>();

    static {
        articleNameList.add(new Article("Dan tri", "http://dantri.com.vn"));
        articleNameList.add(new Article("Vietnamnet", "http://vietnamnet.vn"));
        articleNameList.add(new Article("Vnexpress", "http://vnexpress.net"));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_article_list, container, false);
        ListView articleListView = (ListView) view.findViewById(R.id.articleList);
        ArticleListAdapter adapter = new ArticleListAdapter(getActivity(), R.layout.row_article, articleNameList);
        articleListView.setAdapter(adapter);
        articleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                listener.onArticleListItemClicked(articleNameList.get(i).url);
                Toast.makeText(getActivity(), articleNameList.get(i).name, Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnArticleListFragmentListener) {
            listener = (OnArticleListFragmentListener) context;
        } else {
            throw new ClassCastException(context.toString() + " must implement OnArticleListFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public interface OnArticleListFragmentListener {
        public void onArticleListItemClicked(String url);
    }

    public static class Article {
        public String name;
        public String url;

        public Article() {

        }

        public Article(String name, String url) {
            this.name = name;
            this.url = url;
        }
    }

    public static class ArticleListAdapter extends ArrayAdapter<Article> {

        private Context context;
        private int resource;
        private List<Article> articleNames;

        public ArticleListAdapter(Context context, int resource, List<Article> articleNames) {
            super(context, resource, articleNames);
            this.context = context;
            this.resource = resource;
            this.articleNames = articleNames;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(resource, parent, false);
                ViewHolder viewHolder = new ViewHolder();
                viewHolder.textView = (TextView) view.findViewById(R.id.articleName);
                view.setTag(viewHolder);
            }

            ViewHolder viewHolder = (ViewHolder) view.getTag();
            viewHolder.textView.setText(articleNames.get(position).name);

            return view;
        }

        private static class ViewHolder {
            public TextView textView;
        }
    }
}