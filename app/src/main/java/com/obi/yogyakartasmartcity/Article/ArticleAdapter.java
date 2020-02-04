package com.obi.yogyakartasmartcity.Article;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.obi.yogyakartasmartcity.Data.ArticleModel;
import com.obi.yogyakartasmartcity.R;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.DestinasiViewHolder> {
    private Context context;
    private ArrayList<ArticleModel> articleModels;

    public ArticleAdapter(Context context, ArrayList<ArticleModel> items) {
        this.context = context;
        this.articleModels = items;
    }

    public void refill(ArrayList<ArticleModel> items) {
        this.articleModels = new ArrayList<>();
        this.articleModels.addAll(items);

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ArticleAdapter.DestinasiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_article, parent, false);
        return new ArticleAdapter.DestinasiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleAdapter.DestinasiViewHolder holder, int i) {
        holder.onBind(articleModels.get(i));
    }

    @Override
    public int getItemCount() {
        return articleModels.size();
    }

    class DestinasiViewHolder extends RecyclerView.ViewHolder {
        TextView title, author, txt_date;
        ImageView image;
        RelativeLayout relativeLayout;

        DestinasiViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.txt_title_list_article);
            author = itemView.findViewById(R.id.txt_author_list_article);
            txt_date = itemView.findViewById(R.id.txt_date_list_article);
            image = itemView.findViewById(R.id.img_list_article);
            relativeLayout = itemView.findViewById(R.id.relativeLayout_list_article);
        }

        void onBind(final ArticleModel item) {
            
            title.setText(item.getTitle());
            author.setText("by: "+item.getAuthor());

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            
            try {
                Date date = format.parse(item.getDate());
                Date nowTime = new Date();

                long dateDiff = nowTime.getTime() - date.getTime();

                long detik = TimeUnit.MILLISECONDS.toSeconds(dateDiff);
                long menit = TimeUnit.MILLISECONDS.toMinutes(dateDiff);
                long jam   = TimeUnit.MILLISECONDS.toHours(dateDiff);
                long hari  = TimeUnit.MILLISECONDS.toDays(dateDiff);

                Log.d("Waktu", "Waktu: "+detik+", "+menit+", "+jam+", "+hari);

                if (detik < 60) {
                    txt_date.setText(detik+" detik yang lalu");
                } else if (menit < 60) {
                    txt_date.setText(menit+" menit yang lalu");
                } else if (jam < 24) {
                    txt_date.setText(jam+" jam yang lalu");
                } else if (hari >= 7) {
                    if (hari > 30) {
                        txt_date.setText((hari / 30)+" bulan lalu");
                    } else if (hari > 360) {
                        txt_date.setText((hari / 360)+" tahun lalu");
                    } else {
                        txt_date.setText((hari / 7) + " minggu lalu");
                    }
                } else if (hari < 7) {
                    txt_date.setText(hari+" hari lalu");
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

//            image.setImageDrawable(context.getResources().getDrawable(Integer.parseInt(item.getImage())));

            relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), ArticleDetailActivity.class);
                    intent.putExtra("ARTICLE_ID", item.getId());
                    view.getContext().startActivity(intent);
                }
            });

            Picasso.get().load(item.getImage()).networkPolicy(NetworkPolicy.NO_CACHE).into(image);
        }
    }
}
