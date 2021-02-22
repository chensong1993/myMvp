package com.tongge.mymvp.rx.scheduler;


import com.tongge.mymvp.http.ApiResponse;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.FlowableTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.SingleTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


public class RxUtils {

    public static final String TAG = "RxUtils";


    /**
     * 统一线程处理
     *
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<T, T> rxSchedulerHelper() {    //compose简化线程
        return new FlowableTransformer<T, T>() {
            @Override
            public Flowable<T> apply(Flowable<T> observable) {
                return observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }


    public static <T> SingleTransformer<T, T> rxSchedulerHelperSingle() {    //compose简化线程
        return new SingleTransformer<T, T>() {
            @Override
            public Single<T> apply(Single<T> observable) {
                return observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 统一返回结果处理
     *
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<ApiResponse<T>, T> handleResults() {   //compose判断结果
        return new FlowableTransformer<ApiResponse<T>, T>() {
            @Override
            public Flowable<T> apply(Flowable<ApiResponse<T>> httpResponse) {
                return httpResponse.flatMap(new Function<ApiResponse<T>, Flowable<T>>() {
                    @Override
                    public Flowable<T> apply(ApiResponse<T> tHttpResponse) {
                        if (tHttpResponse.getMsg() == 1) {
                            return createData(tHttpResponse.getData());
                        } else {
                            return Flowable.error(new Exception(tHttpResponse.getMsg() + ""));
                        }
                    }
                });
            }
        };
    }


    public static <T> SingleTransformer<ApiResponse<T>, T> handleResultsSingle() {   //compose判断结果
        return new SingleTransformer<ApiResponse<T>, T>() {
            @Override
            public Single<T> apply(Single<ApiResponse<T>> httpResponse) {
                return httpResponse.flatMap(new Function<ApiResponse<T>, Single<T>>() {
                    @Override
                    public Single<T> apply(ApiResponse<T> tHttpResponse) {
                        if (tHttpResponse.getMsg() == 1) {
                            return createSingle(tHttpResponse.getData());
                        } else {
                            return Single.error(new Exception("" + tHttpResponse.getMsg()));
                        }
                    }
                });
            }
        };
    }


    public static <T> Flowable<T> createData(final T t) {
        return Flowable.create(new FlowableOnSubscribe<T>() {
            @Override
            public void subscribe(FlowableEmitter<T> emitter) throws Exception {
                try {
                    emitter.onNext(t);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        }, BackpressureStrategy.BUFFER);
    }


    public static <T> Observable<T> createObservable(final T t) {
        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(ObservableEmitter<T> emitter) throws Exception {
                try {
                    emitter.onNext(t);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }

        });
    }

    public static <T> Single<T> createSingle(final T t) {
        return Single.create(new SingleOnSubscribe<T>() {
            @Override
            public void subscribe(SingleEmitter<T> emitter) throws Exception {
                try {
                    emitter.onSuccess(t);
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }

        });
    }

}
