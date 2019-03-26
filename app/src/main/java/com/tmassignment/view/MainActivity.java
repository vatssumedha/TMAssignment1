package com.tmassignment.view;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.tmassignment.R;
import com.tmassignment.model.InformationResponse;
import com.tmassignment.remote.APIService;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
/**
 * Created by Sumedha Vats on 26-03-2019.
 */
public class MainActivity extends AppCompatActivity {
    @BindView(R.id.viewPager)
    ViewPager viewPagerSlider;
    @BindView(R.id.tabLayoutDot)
    TabLayout tabLayoutDot;
    ArrayList<String> pictureList = new ArrayList<>();
    int cacheSize = 10 * 1024 * 1024; // 10 MiB

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        loadJSON();

    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void loadJSON() {

        try {
            Cache cache = new Cache(getCacheDir(), cacheSize);

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .cache(cache)
                    .addInterceptor(new Interceptor() {
                        @Override
                        public okhttp3.Response intercept(Interceptor.Chain chain)
                                throws IOException {
                            Request request = chain.request();
                            if (!isNetworkAvailable()) {
                                int maxStale = 60 * 60 * 24 * 28;
                                request = request
                                        .newBuilder()
                                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                                        .build();
                            }
                            return chain.proceed(request);
                        }
                    })
                    .build();

            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl("https://app.deltaapp.in")
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create());

            Retrofit retrofit = builder.build();
            APIService apiService = retrofit.create(APIService.class);

            Call<InformationResponse> call = apiService.getApiInformation();


            call.enqueue(new Callback<InformationResponse>() {
                @Override
                public void onResponse(Call<InformationResponse> call, Response<InformationResponse> response) {
                    InformationResponse informationResponse = response.body();
                    initView(informationResponse);
                }

                @Override
                public void onFailure(Call<InformationResponse> call, Throwable t) {

                }
            });
        } catch (Exception e) {
            Log.d("Error", e.getMessage());
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void initView(InformationResponse informationResponse) {
        for (int i = 0; i < informationResponse.getCompatibility_questions().size(); i++) {
            pictureList.add(informationResponse.getCompatibility_questions().get(i).getStyle().getOriginal());
        }
        viewPagerSlider.setAdapter(new SliderAdapter(this, pictureList));
        tabLayoutDot.setupWithViewPager(viewPagerSlider);
    }

    class SliderAdapter extends PagerAdapter {
        private ArrayList<String> images;
        private Context mContext;

        private SliderAdapter(Context context, ArrayList<String> images) {
            mContext = context;
            this.images = images;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup collection, int position) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.slider_view, collection, false);
            ImageView imgView = layout.findViewById(R.id.imgView);
            Picasso.with(mContext).load(images.get(position)).into(imgView);
            collection.addView(layout);
            return layout;
        }

        @Override
        public void destroyItem(ViewGroup collection, int position, Object view) {
            collection.removeView((View) view);
        }

        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }


}
