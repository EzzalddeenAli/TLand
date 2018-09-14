/*
Copyright 2014 David Morrissey

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package ir.touristland.Interfaces;

import java.util.List;
import java.util.Map;

import ir.touristland.Models.CenterItem;
import ir.touristland.Models.CenterTypeItem;
import ir.touristland.Models.FlightItem;
import ir.touristland.Models.HotelDetails.HotelDetail;
import ir.touristland.Models.HotelDetails.RoomsItem;
import ir.touristland.Models.HotelList.HotelListResponse;
import ir.touristland.Models.HotelPossibilitieItem;
import ir.touristland.Models.HotelPrices.Response;
import ir.touristland.Realm.HotelCities;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("api/FlightReservationApi/SearchFlightData")
    Call<List<FlightItem>> getPosts(@FieldMap Map<String, String> data);

    @FormUrlEncoded
    @POST("api/flights/ravisFlightList")
    Call<List<ir.touristland.Models.FlightList.Response>> getCharter(@FieldMap Map<String, String> data);

    @FormUrlEncoded
    @POST("api/FlightReservationApi/SearchFlightDataSystemi")
    Call<List<ir.touristland.Models.FlightList.Response>> getSystemic(@FieldMap Map<String, String> data);

    @FormUrlEncoded
    @POST("api/flights/ravisReserve")
    Call<ResponseBody> ReserveFlight(@FieldMap Map<String, String> data);

    @FormUrlEncoded
    @POST("api-v2/abd81N5286bja10a6Md1fb3y/json/hotelLoad")
    Call<HotelListResponse> GetHotelList(@FieldMap Map<String, String> data);

    @FormUrlEncoded
    @POST("api-v2/abd81N5286bja10a6Md1fb3y/json/hotelLoad")
    Call<HotelDetail> GetHotelLoad2(@FieldMap Map<String, String> data);

    @FormUrlEncoded
    @POST("api/hotel/prereserv")
    Call<ResponseBody> PreReserv(@FieldMap Map<String, String> data);

    @GET("api/updateapp/")
    Call<ResponseBody> GetUpdate();


    @GET
    Call<ResponseBody> fetchImage(@Url String url);

    @POST("api/center/types")
    Call<List<CenterTypeItem>> KishgardiTypes();

    @POST("ApiSelectPlaces")
    Call<ResponseBody> PlaceHolders();

    @POST("api/hotel/types")
    Call<List<CenterTypeItem>> HotelTypes();

    @FormUrlEncoded
    @POST("api-v2/abd81N5286bja10a6Md1fb3y/json/hotelPrice")
    Call<List<RoomsItem>> GetRooms(@FieldMap Map<String, String> data);

    @FormUrlEncoded
    @POST("api-v2/abd81N5286bja10a6Md1fb3y/json/hotelPrice")
    Call<Response> GetRoomPrice(@FieldMap Map<String, String> data);

    @FormUrlEncoded
    @POST("api/hotel/possibilitie")
    Call<List<HotelPossibilitieItem>> GetHotelPossibilities(@FieldMap Map<String, String> data);

    @FormUrlEncoded
    @POST("api/center/find")
    Call<List<CenterItem>> KishgardiCenters(@FieldMap Map<String, String> data);

    //https://accounts.google.com/o/oauth2/revoke?token=
    @GET("api-v2/abd81N5286bja10a6Md1fb3y/json/cityLoad")
    Call<HotelCities> loadCity();
}