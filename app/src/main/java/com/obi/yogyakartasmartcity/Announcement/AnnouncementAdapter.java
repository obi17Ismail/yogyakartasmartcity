package com.obi.yogyakartasmartcity.Announcement;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.obi.yogyakartasmartcity.Data.AnnouncementModel;
import com.obi.yogyakartasmartcity.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class AnnouncementAdapter extends RecyclerView.Adapter<AnnouncementAdapter.DestinasiViewHolder> {
    private Context context;
    private ArrayList<AnnouncementModel> announcementModels;

    public AnnouncementAdapter(Context context, ArrayList<AnnouncementModel> items) {
        this.context = context;
        this.announcementModels = items;
    }

    public void refill(ArrayList<AnnouncementModel> items) {
        this.announcementModels = new ArrayList<>();
        this.announcementModels.addAll(items);

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AnnouncementAdapter.DestinasiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_announcement, parent, false);
        return new AnnouncementAdapter.DestinasiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnnouncementAdapter.DestinasiViewHolder holder, int i) {
        holder.onBind(announcementModels.get(i));
    }

    @Override
    public int getItemCount() {
        return announcementModels.size();
    }

    class DestinasiViewHolder extends RecyclerView.ViewHolder {
        TextView title, announcement, author, txt_date;

        DestinasiViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.txt_title_list_announcement);
            announcement = itemView.findViewById(R.id.txt_list_announcement);
            author = itemView.findViewById(R.id.txt_author_list_announcement);
            txt_date = itemView.findViewById(R.id.txt_date_list_announcement);
        }

        void onBind(final AnnouncementModel item) {
            
            title.setText(item.getTitle());
            announcement.setText(item.getAnnouncement());
            author.setText(item.getAuthor());

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
        }
    }
}
