package it.ads.app.city_explorer_sdk.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class City {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("malls")
    @Expose
    private List<Mall> malls = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Mall> getMalls() {
        return malls;
    }

    public void setMalls(List<Mall> malls) {
        this.malls = malls;
    }

}
