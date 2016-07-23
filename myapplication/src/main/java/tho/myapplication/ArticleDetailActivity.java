package tho.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class ArticleDetailActivity extends AppCompatActivity {

    public static final String EXTRA_URL = "url";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getResources().getBoolean(R.bool.dual)) {
            finish();
            return;
        }
        setContentView(R.layout.activity_article_detail);
        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            String url = extra.getString(EXTRA_URL);
            Log.i("url -----", url);
            ArticleDetailFragment articleDetailFragment = (ArticleDetailFragment) getFragmentManager().findFragmentById(R.id.webviewFragment);
            articleDetailFragment.loadArticleDetail(url);
        } else {
            Log.i("------", "null");
        }
    }
}
