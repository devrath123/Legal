package com.example.devrathrathee.legal.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.devrathrathee.legal.R;
import com.example.devrathrathee.legal.beans.FindInternBean;
import com.example.devrathrathee.legal.views.FindInternActivity;

import java.util.ArrayList;
import java.util.List;

public class FIndInternAdapter extends RecyclerView.Adapter<FIndInternAdapter.InternViewHolder> {

    Context context;
    List<FindInternBean.SearchIntern> findInternBeanList = new ArrayList<>();

    public FIndInternAdapter(Context context, List<FindInternBean.SearchIntern> findInternBeanList) {
        this.context = context;
        this.findInternBeanList.addAll(findInternBeanList);
    }

    @NonNull
    @Override
    public InternViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.find_intern_row, viewGroup, false);
        return new InternViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final InternViewHolder internViewHolder, int i) {
        internViewHolder.applying_as_tv.setText(findInternBeanList.get(i).getApply_as());
        internViewHolder.intern_name_tv.setText(findInternBeanList.get(i).getName());
        internViewHolder.phone_tv.setText(findInternBeanList.get(i).getPhone());
        internViewHolder.email_tv.setText(findInternBeanList.get(i).getEmail());
        internViewHolder.language_known_tv.setText(findInternBeanList.get(i).getLanguage());

        internViewHolder.view_pdf_iv.setTag(i);
        internViewHolder.view_pdf_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (Integer) internViewHolder.view_pdf_iv.getTag();
                ((FindInternActivity)context).viewPdf(findInternBeanList.get(position));
            }
        });

        internViewHolder.send_mail_iv.setTag(i);
        internViewHolder.send_mail_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (Integer) internViewHolder.send_mail_iv.getTag();
                ((FindInternActivity)context).sendMail(findInternBeanList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return findInternBeanList.size();
    }

    public static class InternViewHolder extends RecyclerView.ViewHolder {
        TextView intern_name_tv, applying_as_tv, phone_tv, email_tv, language_known_tv;
        ImageView view_pdf_iv, send_mail_iv;

        public InternViewHolder(@NonNull View itemView) {
            super(itemView);
            intern_name_tv = itemView.findViewById(R.id.intern_name_tv);
            applying_as_tv = itemView.findViewById(R.id.applying_as_tv);
            phone_tv = itemView.findViewById(R.id.phone_tv);
            email_tv = itemView.findViewById(R.id.email_tv);
            language_known_tv = itemView.findViewById(R.id.language_known_tv);
            view_pdf_iv = itemView.findViewById(R.id.view_pdf_iv);
            send_mail_iv = itemView.findViewById(R.id.send_mail_iv);
        }
    }

}
