package ir.developer_boy.mstore.model.api;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import ir.developer_boy.mstore.exception.ExceptionMessageFactory;

public abstract class MsSingleObserver<T> implements SingleObserver<T> {

    private CompositeDisposable compositeDisposable;

    public MsSingleObserver(CompositeDisposable compositeDisposable) {
        this.compositeDisposable = compositeDisposable;
    }

    @Override
    public void onSubscribe(Disposable d) {
        compositeDisposable.add(d);
    }

    @Override
    public void onError(Throwable e) {
        EventBus.getDefault().post(ExceptionMessageFactory.getMessage(e));
    }
}
