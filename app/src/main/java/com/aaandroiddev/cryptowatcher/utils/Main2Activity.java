package com.aaandroiddev.cryptowatcher.utils;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.aaandroiddev.cryptowatcher.CryptoWatcherApp;
import com.aaandroiddev.cryptowatcher.R;
import com.aaandroiddev.cryptowatcher.model.CoinsController;
import com.aaandroiddev.cryptowatcher.model.classes.Coin;

import javax.inject.Inject;

public class Main2Activity extends AppCompatActivity {

    @Inject
    CoinsController coinsController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.p);
        final Coin coin = new Coin("from","to","","",false,"",
                "","","",0.0f,"",0.0f,"",0.0f,
                "",0.0f,0.0f,"",0.0f,"",
                0.0f,"",0.0f,"",0.0f,"",
                0.0f,"","",0.0f,"",
                0.0f,"",0.0f,"",0.0f,"");
        final Handler handler = new Handler();

        final Runnable r = new Runnable() {
            public void run() {
                coinsController.saveCoin(coin);

            }
        };

        handler.postDelayed(r, 1000);
    }
}
