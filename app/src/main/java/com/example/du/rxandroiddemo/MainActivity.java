package com.example.du.rxandroiddemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func0;

public class MainActivity extends AppCompatActivity {

    private final static String LOG_TAG = "log_tag";

    private Button mButton;
    private TextView mTextView;
    String test = "old data";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = (TextView) findViewById(R.id.textView);
        mButton = (Button) findViewById(R.id.button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                rxAndroidJust();
//                rxAndroidCreate();
//                rxAndroidFrom();
//                simpleRxAndroidJust();
//                complexRxAndroidJust();
//                rxAndroidRange();
//                rxAndroidTimerAndRepet();
//                rxAndroidInterval();
                rxAndroidDefer();
            }
        });
    }

    // operator of defer
    private void rxAndroidDefer() {
//        String test = "old data";
//        Observable<String> observable = Observable.just(test);
//        Subscriber<String> subscriber = new Subscriber<String>() {
//            @Override
//            public void onCompleted() {
//                Log.i(LOG_TAG, "onCompleted");
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.i(LOG_TAG, "onError");
//            }
//
//            @Override
//            public void onNext(String s) {
//                Log.i(LOG_TAG, "onNext " + s);
//            }
//        };
//        test = "new data";
//        observable.subscribe(subscriber);
        Observable<String> observable = Observable.defer(new Func0<Observable<String>>() {
            @Override
            public Observable<String> call() {
                return Observable.just(test);
            }
        });
        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Log.i(LOG_TAG, "onComplete");
            }

            @Override
            public void onError(Throwable e) {
                Log.i(LOG_TAG, "onError");
            }

            @Override
            public void onNext(String s) {
                Log.i(LOG_TAG, "onNext " + s);
            }
        };
        test = "new data";
        observable.subscribe(subscriber);
    }

    // operator of Interval
    private void rxAndroidInterval() {
        Subscription subscription = Observable.interval(5, TimeUnit.SECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<Long>() {
                        @Override
                        public void call(Long aLong) {
                            mTextView.setText("value is " + aLong);
                        }
                    });
//        subscription.unsubscribe();
    }


    // operator of Timer and repeat
    private void rxAndroidTimerAndRepet() {
        Observable.timer(2, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .repeat(5)
                .subscribe(new Subscriber<Long>() {
                    int i = 0;
                    @Override
                    public void onCompleted() {
                        Log.i("linus", "onComplete");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("linus", "onError");
                    }

                    @Override
                    public void onNext(Long aLong) {
                        i++;
                        mTextView.setText(i + "");
                        Log.i("linus", "onNext " + i);
                    }
                });
    }

    // operator of Range
    private void rxAndroidRange() {
        Observable.range(20, 10)
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        Log.i("linus", "onComplete");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("linus", "onError");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.i("linus", "onNext " + integer);
                    }
                });
    }

    //operator for Just
    private void complexRxAndroidJust() {
        Observable.just("hello", 1, "world", 2, "fine")
                .subscribe(new Subscriber<Object>() {
                    @Override
                    public void onCompleted() {
                        Log.i("linus", "onComplete");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("linus", "onError");
                    }

                    @Override
                    public void onNext(Object obj) {
                        Log.i("linus", "onNext " + obj.toString());
                    }
                });
    }

    //operator for Just
    private void simpleRxAndroidJust() {
        Observable.just(1, 2, 3, 4, 5)
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        Log.i("linus", "onComplete");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("linus", "onError");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.i("linus", "onNext " + integer.toString());
                    }
                });
    }

    // operator from
    private void rxAndroidFrom() {
        String[] s = new String[] {"hello", "this", "is", "operator", "of", "from"};
        Observable<String> observable = Observable.from(s);
        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Log.i("linus", "onComplete");
            }

            @Override
            public void onError(Throwable e) {
                Log.i("linus", "onError");
            }

            @Override
            public void onNext(String s) {
                Log.i("linus", "onNext " + s);
            }
        };
        observable.subscribe(subscriber);
    }

    // operator CREATE
    private void rxAndroidCreate() {
        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("hello");
                subscriber.onNext("this is operate CREAT");
                subscriber.onNext("end");
                subscriber.onError(new NullPointerException("null pointer exception"));
                subscriber.onCompleted();
            }
        });
        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Log.i("linus", "onCompelete");
            }

            @Override
            public void onError(Throwable e) {
                Log.i("linus", "onError");
                Log.i("linus", e.getMessage());
            }

            @Override
            public void onNext(String s) {
                Log.i("linus", "onNext " + s);
            }
        };
        observable.subscribe(subscriber);
    }

    // operator Just
    private void rxAndroidJust() {
        Observable observable = Observable.just("Hello", "I am XXX", "From China");
        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Log.i("linus", "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.i("linus", "onError");
                Log.i("linus", e.getMessage());
            }

            @Override
            public void onNext(String s) {
                Log.i("linus", s);
            }
        };
        observable.subscribe(subscriber);
    }
}
