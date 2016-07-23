package tho.myapplication;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.webkit.WebView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements ArticleListFragment.OnArticleListFragmentListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public void onArticleListItemClicked(String url) {
        boolean dual = getResources().getBoolean(R.bool.dual);
        if (dual) {
            FragmentManager fragmentManager = getFragmentManager();
            ArticleDetailFragment articleDetailFragment = (ArticleDetailFragment) fragmentManager.findFragmentById(R.id.articleDetailFragment);
            articleDetailFragment.loadArticleDetail(url);
        } else {
            Intent intent = new Intent(this, ArticleDetailActivity.class);
            intent.putExtra(ArticleDetailActivity.EXTRA_URL, url);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        boolean dual = getResources().getBoolean(R.bool.dual);
        if (dual) {
            FragmentManager fragmentManager = getFragmentManager();
            ArticleDetailFragment articleDetailFragment = (ArticleDetailFragment) fragmentManager.findFragmentById(R.id.articleDetailFragment);
            WebView articleDetail = (WebView) articleDetailFragment.getView().findViewById(R.id.articleDetail);
            if (articleDetail.canGoBack()) {
                articleDetail.goBack();
            }
        }
        super.onBackPressed();
    }
}
