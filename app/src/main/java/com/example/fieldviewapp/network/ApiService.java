package com.example.fieldviewapp.network;

import com.example.fieldviewapp.models.User;
import com.example.fieldviewapp.models.Report;
import com.example.fieldviewapp.models.Inspection;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {

    // User Registration
    @POST("register")
    Call<User> registerUser(@Body User user);

    // User Login
    @POST("login")
    Call<User> loginUser(@Body User user);

    // Fetch all Reports
    @GET("reports")
    Call<List<Report>> getReports();

    // Fetch all Inspections
    @GET("inspections")
    Call<List<Inspection>> getInspections();

    // Fetch specific Report by ID
    @GET("reports/{id}")
    Call<Report> getReportById(@Path("id") int reportId);

    // Fetch specific Inspection by ID
    @GET("inspections/{id}")
    Call<Inspection> getInspectionById(@Path("id") int inspectionId);
}
