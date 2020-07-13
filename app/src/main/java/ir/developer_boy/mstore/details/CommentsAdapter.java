package ir.developer_boy.mstore.details;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ir.developer_boy.mstore.R;
import ir.developer_boy.mstore.model.Comment;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentsViewHolder> {

    private List<Comment> commentList = new ArrayList<>();

    @NonNull
    @Override
    public CommentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CommentsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CommentsViewHolder holder, int position) {
        holder.bindComments(commentList.get(position));
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
        notifyDataSetChanged();
    }

    public class CommentsViewHolder extends RecyclerView.ViewHolder {


        private TextView commentTitle;
        private TextView commentAuthor;
        private TextView commentDate;
        private TextView commentContent;

        public CommentsViewHolder(View itemView) {
            super(itemView);
            commentTitle = itemView.findViewById(R.id.tv_comments_title);
            commentAuthor = itemView.findViewById(R.id.tv_comments_author);
            commentDate = itemView.findViewById(R.id.tv_comments_date);
            commentContent = itemView.findViewById(R.id.tv_comments_content);
        }


        public void bindComments(Comment comment) {
            commentTitle.setText(comment.getTitle());
            commentAuthor.setText(comment.getAuthor().getEmail());
            commentDate.setText(comment.getDate());
            commentContent.setText(comment.getContent());
        }
    }
}
