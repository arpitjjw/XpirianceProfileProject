package com.example.user.xpirianceprofileproject;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import java.util.List;

public class Adapteranswers extends RecyclerView.Adapter<Adapteranswers.postvh>{

    private Context mCtx;
    private List<useranswers> useranswerslist;

    public Adapteranswers(Context mCtx, List<useranswers> useranswerslist) {

        this.mCtx = mCtx;
        this.useranswerslist = useranswerslist;
    }



    @Override
    public postvh onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.user_questions_answers, parent,false);
        return new postvh(view);
    }

    @Override
    public void onBindViewHolder(postvh holder, int position) {
        useranswers userAnswers = useranswerslist.get(position);

        holder.post.setText(String.valueOf(userAnswers.getAnswer()));
        holder.question.setText(String.valueOf(userAnswers.getQuestion()));


    }

    @Override
    public int getItemCount() {
        return useranswerslist.size();
    }
    class postvh extends RecyclerView.ViewHolder {

        TextView post;
        TextView question;


        public postvh(View itemView) {
            super(itemView);


            post=itemView.findViewById(R.id.Answer);
            question=itemView.findViewById(R.id.Question);


        }
    }
}
