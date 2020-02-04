package com.obi.yogyakartasmartcity.Article;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.obi.yogyakartasmartcity.R;
import com.obi.yogyakartasmartcity.Network.DataSources;
import com.obi.yogyakartasmartcity.Network.ObjectSourcesCallback;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class ArticleDetailActivity extends AppCompatActivity implements ObjectSourcesCallback {
    TextView txt_title, txt_date, txt_view, txt_author, txt_content;
    ImageView image;
    Button btn_share;
    public static RelativeLayout relativeLayout_pb;

    private String TAG = "ArticleDetail";

    public static String article_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);

        Toolbar toolbar = findViewById(R.id.toolbar_detail);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_white);

        article_id = getIntent().getStringExtra("ARTICLE_ID");

        relativeLayout_pb = findViewById(R.id.relativeLayout_pb_detail);
        relativeLayout_pb.setVisibility(View.VISIBLE);

        txt_title = findViewById(R.id.txt_title_detail);
        txt_date = findViewById(R.id.txt_date_detail);
        txt_view = findViewById(R.id.txt_view_detail);
        txt_author = findViewById(R.id.txt_author_detail);
        txt_content = findViewById(R.id.txt_content_detail);

        image = findViewById(R.id.img_detail);

        btn_share = findViewById(R.id.btn_share_detail);

        getDataSources().postArticleDetail(DataSources.URL_DETAIL_ARTICLE, article_id, this);
    }

    public DataSources getDataSources() {
        return new DataSources();
    }

    @Override
    public void onSuccess(JSONObject respone) {
        relativeLayout_pb.setVisibility(View.GONE);
        Log.d(TAG, String.valueOf(respone));

        try {
            JSONObject jsonObj = respone.getJSONObject("data");

            final String j_article_id = jsonObj.getString("id");
            final String j_title = jsonObj.getString("title");
            final String j_slug = jsonObj.getString("slug");
            final String j_image = jsonObj.getString("image");
            final String j_author_id = jsonObj.getString("author_id");
            final String j_author_name = jsonObj.getString("author_name");
            final String j_content = jsonObj.getString("content");
            final String j_view = jsonObj.getString("view");
            final String j_created_at = jsonObj.getString("created_at");
            final String j_updated_at = jsonObj.getString("updated_at");

            txt_title.setText(j_title);
            txt_author.setText("by "+j_author_name);
            txt_view.setText(j_view+"x dilihat");
            txt_date.setText(j_created_at);
            txt_content.setText(j_content);
            Picasso.get()
                    .load(j_image)
                    .into(image);

            btn_share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
                    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, j_title+" via Yogyakarta Smart City\n");
                    startActivity(Intent.createChooser(sharingIntent, "Bagikan Artikel"));
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailed(String error) {
        relativeLayout_pb.setVisibility(View.GONE);
        Toast.makeText(ArticleDetailActivity.this , error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
