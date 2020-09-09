package com.heyyou;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.animation.Animator;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ScaleXSpan;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.heyyou.WebviewPack.GetParticipateID;
import com.heyyou.WebviewPack.Variables.Variables;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Splash extends AppCompatActivity {

    private ConstraintLayout background, goBtnWrapper, joinBtn;
    private Button toCompany;
    private int transitionTime = 5000;
    private ImageView b1, b2, b3, text1, text2, text3, text4, text5, text6;

    private int dotCount = 0;
    private TextView joinDetail;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        background = (ConstraintLayout) findViewById(R.id.backgroundLayout);

        goBtnWrapper = (ConstraintLayout) findViewById(R.id.goBtnWrapper);
        joinBtn = (ConstraintLayout) findViewById(R.id.joinBtn);
        toCompany = (Button) findViewById(R.id.toCompany);

         textView = (TextView) findViewById(R.id.textView);
        TextView textView2 = (TextView) findViewById(R.id.textView2);
        TextView joinText = (TextView) findViewById(R.id.joinText);
        joinDetail = (TextView) findViewById(R.id.joinDetail);

        b1 = (ImageView) findViewById(R.id.background_first);
        b2 = (ImageView) findViewById(R.id.background_second);
        b3 = (ImageView) findViewById(R.id.background_third);

        text1 = (ImageView) findViewById(R.id.bubble1);
        text2 = (ImageView) findViewById(R.id.bubble2);
        text3 = (ImageView) findViewById(R.id.bubble3);
        text4 = (ImageView) findViewById(R.id.bubble4);
        text5 = (ImageView) findViewById(R.id.bubble5);
        text6 = (ImageView) findViewById(R.id.bubble6);

        SpannableStringBuilder enterSpan = new SpannableStringBuilder("헤이유 입장하기");
        enterSpan.setSpan(new ScaleXSpan(0.9f), 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView2.setText(enterSpan);

        SpannableStringBuilder joinTextSpan = new SpannableStringBuilder("신규 회원가입");
        joinTextSpan.setSpan(new ScaleXSpan(0.9f), 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        joinText.setText(joinTextSpan);


        SpannableStringBuilder joinDetailSpan = new SpannableStringBuilder("당신이 상상해왔던 그만남, 헤이유에서 시작하세요.");
        joinDetailSpan.setSpan(new ScaleXSpan(0.9f), 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        joinDetail.setText(joinDetailSpan);

        final SpannableStringBuilder joinBtnSpan = new SpannableStringBuilder(getResources().getString(R.string.splash_text));
        joinBtnSpan.setSpan(new ScaleXSpan(0.9f), 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView.setText(joinBtnSpan);
            }
        });

        showFirst();

        goBtnWrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("enterType", "enter");
                startActivity(intent);
            }
        });

        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("enterType", "join");
                startActivity(intent);
            }
        });

        toCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                //intent.putExtra("enterType", "info");
                //startActivity(intent);

                AlertDialog.Builder alert = new AlertDialog.Builder(Splash.this);

                alert.setTitle("리투비컴퍼니")
                        .setMessage("\n" +
                        "상호: 리투비컴퍼니 | 대표자: 박종선 |\n주소: 경기도 고양시 일산동구 중앙로1261번길 77, 한신메트로폴리스 403-1(A-09)\n" +
                        "대표번호: 070-7632-0122 |\n개인정보 책임관리자: 박종선 |\n이메일: ritobe0718@gmail.com\n" +
                        "사업자번호: 642-65-00195 |\n통신판매업 신고번호: 2019-고양일산동-2226")

                        .setNegativeButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });

                AlertDialog aa = alert.create();
                aa.show();



            }
        });


        Funple funple = new Funple(this);
        funple.determineAdvertisingInfo();


    }

    private void showFirst() {
        setBackground(b1, 500);
        showTextBallon(text1, text2);
        hideTextBallon(text1, text2);
        hideBackground(b1,R.drawable.bubble3, R.drawable.bubble4);
        showSecond();

    }

    private void showSecond() {
        setBackground(b2, 500);
        showTextBallon(text3, text4);
        hideTextBallon(text3, text4);
        hideBackground(b2,R.drawable.bubble5, R.drawable.bubble6);
        showThird();


    }

    private void showThird() {
        setBackground(b3, 500);
        showTextBallon(text5, text6);
        hideTextBallon(text5, text6);
        //hideBackground(b3);
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    b3.animate().alpha(0.0f).setDuration(1000).setListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {
                            showFirst();

                        }

                        @Override
                        public void onAnimationCancel(Animator animator) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animator) {

                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setBackground(final ImageView bb, final int du) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(du);
                    bb.animate().alpha(1.0f).setDuration(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void hideBackground(final ImageView iv, final int drw1, final int drw2) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    iv.animate().alpha(0.0f).setDuration(1000).setListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    text1.setImageResource(drw1);
                                    text2.setImageResource(drw2);

                                }
                            });
                        }

                        @Override
                        public void onAnimationCancel(Animator animator) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animator) {

                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void showTextBallon(final ImageView iv1, final ImageView iv2) {

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    iv1.animate().alpha(1.0f);
                    Thread.sleep(1000);
                    iv2.animate().alpha(1.0f);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });


    }


    private void hideTextBallon(final ImageView iv1, final ImageView iv2) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    iv1.animate().alpha(0.0f);
                    Thread.sleep(500);
                    iv2.animate().alpha(0.0f);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }



}