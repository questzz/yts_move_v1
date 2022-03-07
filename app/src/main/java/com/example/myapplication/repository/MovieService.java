package com.example.myapplication.repository;

import com.example.myapplication.models.YtsData;
import com.example.myapplication.utils.Define;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

// https://yts.lt/api/v2/list_movies.json?limit=20&page=1&sort_by=rating
public interface MovieService {

    // public static final
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Define.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    // dto 만들어주기
    @GET("list_movies.json")
    Call<YtsData> repoContributors(@Query("sort_by") String sortBy,
                                   @Query("page") int page,
                                   @Query("limit") int limit);

}
