package com.example.blood_bank.data.api;

import com.example.blood_bank.data.model.NewPassword.Newpassword;
import com.example.blood_bank.data.model.Posts.Posts;
import com.example.blood_bank.data.model.Setting.Setting;
import com.example.blood_bank.data.model.bloodtyps.BloodTyps;
import com.example.blood_bank.data.model.categories.CateGories;
import com.example.blood_bank.data.model.citys.Cities;
import com.example.blood_bank.data.model.create.Create;
import com.example.blood_bank.data.model.create.DonationRequest;
import com.example.blood_bank.data.model.donationDetals.DonationDetails;
import com.example.blood_bank.data.model.donations.DonationData;
import com.example.blood_bank.data.model.donations.Donations;
import com.example.blood_bank.data.model.getnotificationssettings.getNotificationssettings;
import com.example.blood_bank.data.model.governorates.Governorates;
import com.example.blood_bank.data.model.login.Login;
import com.example.blood_bank.data.model.notificationssettings.Notificationssettings;
import com.example.blood_bank.data.model.postsfilter.PostsFilter;
import com.example.blood_bank.data.model.posttogglefavourite.PostToggleFavourite;
import com.example.blood_bank.data.model.restpasswoard.ResetPassword;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    @FormUrlEncoded
    @POST("login")
    Call<Login> userLogin(@Field("phone") String phone,
                          @Field("password") String password);

    @FormUrlEncoded
    @POST("register")
    Call<Login> onRegister(@Field("name") String name,
                           @Field("email") String email,
                           @Field("birth_date") String birth_date,
                           @Field("city_id") int city_id,
                           @Field("phone") String phone,
                           @Field("donation_last_date") String donation_last_date,
                           @Field("password") String password,
                           @Field("password_confirmation") String password_confirmation,
                           @Field("blood_type_id") int blood_type_id);


    @FormUrlEncoded
    @POST("reset-password")
    Call<ResetPassword> resetpassword(@Field("phone") String phone);

    @FormUrlEncoded
    @POST("new-password")
    Call<Newpassword> newPassWord(@Field("password") String password,
                                  @Field("password_confirmation") String password_confirmation,
                                  @Field("pin_code") String pin_code,
                                  @Field("phone") String phone);


    @GET("governorates")
    Call<Governorates> getListGovern();


    @GET("cities")
    Call<Cities> getcity(@Query("governorate_id") int governorate_id);

    @GET("blood-types")
    Call<BloodTyps> getListBloods();

    @GET("posts")
    Call<Posts> posts(@Query("api_token") String api_token,
                      @Query("page") int page);

    @GET("donation-requests")
    Call<Donations> getDonation(@Query("api_token") String api_token,
                                @Query("page") int page);

    @FormUrlEncoded
    @POST("post-toggle-favourite")
    Call<PostToggleFavourite> addfavorit(@Field("api_token") String api_token,
                                         @Field("post_id") int id);

    @GET("posts?")
    Call<PostsFilter> getfilter(@Query("api_token") String api_token,
                                @Query("page") int page,
                                @Query("keyword") String keyword,
                                @Query("category_id") int category_id);

    @GET("categories")
    Call<CateGories> getListcategoris();

    @FormUrlEncoded
    @POST("donation-request/create")
    Call<Create> creatrequest(@Field("api_token") String api_token,
                              @Field("patient_name") String patient_name,
                              @Field("patient_age") String patient_age,
                              @Field("blood_type_id") int blood_type_id,
                              @Field("bags_num") String bags_num,
                              @Field("hospital_name") String hospital_name,
                              @Field("hospital_address") String hospital_address,
                              @Field("city_id") int city_id,
                              @Field("phone") String phone,
                              @Field("notes") String notes,
                              @Field("latitude") String latitude,
                              @Field("longitude") String longitude);


    @GET("settings")
    Call<Setting> getSetting(@Query("api_token") String api_token);

    @FormUrlEncoded
    @POST("profile")
    Call<Login> editprofile(@Field("name") String name,
                            @Field("email") String email,
                            @Field("birth_date") String birth_date,
                            @Field("city_id") int city_id,
                            @Field("phone") String phone,
                            @Field("donation_last_date") String donation_last_date,
                            @Field("password") String password,
                            @Field("password_confirmation") String password_confirmation,
                            @Field("blood_type_id") int blood_type_id);

    @FormUrlEncoded
    @POST("notifications-settings")
    Call<Notificationssettings> notificationSettings(@Field("api_token") String api_token,
                                                     @Field("governorates[]") String governorates,
                                                     @Field("blood_types[]") String blood_types);


    @FormUrlEncoded
    @POST("notifications-settings")
    Call<getNotificationssettings> notification(@Field("api_token") String api_token);


    @GET("donation-request")
    Call<DonationDetails> dontaion_request(@Query("api_token") String api_token,
                                           @Query("donation_id") int donation_id);

}
