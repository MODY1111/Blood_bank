
package com.example.blood_bank.data.model.login;

import com.example.blood_bank.data.model.bloodtyps.BloodTypsData;
import com.example.blood_bank.data.model.citys.CityData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Client {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("birth_date")
    @Expose
    private String birthDate;
    @SerializedName("city_id")
    @Expose
    private String cityId;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("donation_last_date")
    @Expose
    private String donationLastDate;
    @SerializedName("blood_type_id")
    @Expose
    private String bloodTypeId;
    @SerializedName("is_active")
    @Expose
    private String isActive;
    @SerializedName("pin_code")
    @Expose
    private String pinCode;
    @SerializedName("city")
    @Expose
    private CityData city;
    @SerializedName("blood_type")
    @Expose
    private BloodTypsData bloodType;
    private String ApiToken;

    public Client() {
    }

    public Client(Integer id, String name, String email, String birthDate, String cityId, String phone
            , String donationLastDate, String bloodTypeId, CityData city, BloodTypsData bloodType, String apiToken) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.birthDate = birthDate;
        this.cityId = cityId;
        this.phone = phone;
        this.donationLastDate = donationLastDate;
        this.bloodTypeId = bloodTypeId;
        this.city = city;
        this.bloodType = bloodType;
        ApiToken = apiToken;
    }


    public String getApiToken() {
        return ApiToken;
    }

    public void setApiToken(String apiToken) {
        ApiToken = apiToken;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDonationLastDate() {
        return donationLastDate;
    }

    public void setDonationLastDate(String donationLastDate) {
        this.donationLastDate = donationLastDate;
    }

    public String getBloodTypeId() {
        return bloodTypeId;
    }

    public void setBloodTypeId(String bloodTypeId) {
        this.bloodTypeId = bloodTypeId;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public CityData getCity() {
        return city;
    }

    public void setCity(CityData city) {
        this.city = city;
    }

    public BloodTypsData getBloodType() {
        return bloodType;
    }

    public void setBloodType(BloodTypsData bloodType) {
        this.bloodType = bloodType;
    }

}
