package com.example.ebayapiproject;
import com.example.ebayapiproject.SearchResultsActivity;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface EbayApiService {

    @GET("services/search/FindingService/v1")
    Call<SearchResultsActivity> searchItems(
            @Query("OPERATION-NAME") String operationName,
            @Query("SERVICE-VERSION") String serviceVersion,
            @Query("SECURITY-APPNAME") String securityAppName,
            @Query("GLOBAL-ID") String globalId,
            @Query("RESPONSE-DATA-FORMAT") String responseDataFormat,
            @Query("REST-PAYLOAD") boolean restPayload,
            @Query("keywords") String keywords
    );
}

