package ir.developer_boy.mstore.details.addcomment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import ir.developer_boy.mstore.R;
import ir.developer_boy.mstore.model.Comment;
import ir.developer_boy.mstore.model.api.MsSingleObserver;

public class AddCommentDialog extends DialogFragment {
    private static final String EXTRA_KEY_PRODUCT_ID = "product_Id";
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private AddCommentViewModel viewModel;
    private int ProductId;
    private EditText commentTitle;
    private EditText commentDesc;
    private Button submitNewCommentBtn;
    private Button cancelNewCommentSubmittionBtn;
    private View view;
    private ProgressBar progressBar;

    public static AddCommentDialog newInstance(int productId) {

        Bundle args = new Bundle();
        args.putInt(EXTRA_KEY_PRODUCT_ID, productId);
        AddCommentDialog fragment = new AddCommentDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProductId = getArguments().getInt(EXTRA_KEY_PRODUCT_ID);
        if (ProductId == 0) {
            dismiss();
        }
        viewModel = new AddCommentViewModel();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        setUpViews();
        cancelNewCommentSubmittionBtn.setOnClickListener(v -> {
            dismiss();
        });

        submitNewCommentBtn.setOnClickListener(v -> {
            if (commentTitle.length() > 0 && commentDesc.length() > 0) {
                viewModel.submitNewComment(commentTitle.getText().toString(), commentDesc.getText().toString(), ProductId)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new MsSingleObserver<Comment>(compositeDisposable) {
                            @Override
                            public void onSuccess(Comment comment) {
                                EventBus.getDefault().post("نظر شما با موفقیت ثبت شد پس از تایید مدیر نمایش داده خواهد شد");
                                dismiss();
                            }
                        });
            } else {
                if (commentTitle.length() == 0) {
                    commentTitle.setError("عنوان نظر نمی تواند خالی باشد");
                } else {
                    commentDesc.setError("محتوای نظر نمی تواند خالی باشد");
                }
            }
        });
        compositeDisposable.add(viewModel.getProgressBarStatus()
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((progressbarstat) -> {
                    progressBar.setVisibility((progressbarstat ? View.VISIBLE : View.GONE));
                    submitNewCommentBtn.setVisibility(progressbarstat ? View.INVISIBLE : View.VISIBLE);
                }));

        cancelNewCommentSubmittionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        builder.setView(view);
        return builder.create();
    }

    private void setUpViews() {
        view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_add_comment, null);
        commentTitle = view.findViewById(R.id.ed_commentDialog_commentTitle);
        commentDesc = view.findViewById(R.id.ed_commentDialog_commentContent);
        submitNewCommentBtn = view.findViewById(R.id.btn_commentDialog_submitComment);
        cancelNewCommentSubmittionBtn = view.findViewById(R.id.btn_commentDialog_cancel);
        progressBar = view.findViewById(R.id.pb_comments_addComments);
    }

    @Override
    public void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }


}
