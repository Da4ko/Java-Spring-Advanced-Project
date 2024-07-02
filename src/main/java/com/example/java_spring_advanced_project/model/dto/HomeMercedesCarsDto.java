package com.example.java_spring_advanced_project.model.dto;

import com.example.java_spring_advanced_project.model.dto.MyCars.MyMercedesCarDto;
import com.example.java_spring_advanced_project.model.dto.OthersCars.OthersMercedesCarDto;

import java.util.List;

public class HomeMercedesCarsDto {
    private List<MyMercedesCarDto> myMercedesCars;
    private List<OthersMercedesCarDto> othersMercedesCars;

    public HomeMercedesCarsDto() {
    }

    public HomeMercedesCarsDto(List<MyMercedesCarDto> myMercedesCars, List<OthersMercedesCarDto> othersMercedesCars) {
        this.myMercedesCars = myMercedesCars;
        this.othersMercedesCars = othersMercedesCars;
    }

    public List<MyMercedesCarDto> getMyMercedesCars() {
        return myMercedesCars;
    }

    public void setMyMercedesCars(List<MyMercedesCarDto> myMercedesCars) {
        this.myMercedesCars = myMercedesCars;
    }

    public List<OthersMercedesCarDto> getOthersMercedesCars() {
        return othersMercedesCars;
    }

    public void setOthersMercedesCars(List<OthersMercedesCarDto> othersMercedesCars) {
        this.othersMercedesCars = othersMercedesCars;
    }
}
