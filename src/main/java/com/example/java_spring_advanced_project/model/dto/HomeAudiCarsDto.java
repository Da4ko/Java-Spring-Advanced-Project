package com.example.java_spring_advanced_project.model.dto;

import com.example.java_spring_advanced_project.model.dto.MyCars.MyAudiCarDto;
import com.example.java_spring_advanced_project.model.dto.OthersCars.OthersAudiCarDto;

import java.util.List;

public class HomeAudiCarsDto {
  private List<MyAudiCarDto> myAudiCars;
   private List<OthersAudiCarDto> othersAudiCars;

    public HomeAudiCarsDto() {
    }

    public HomeAudiCarsDto(List<MyAudiCarDto> myAudiCars, List<OthersAudiCarDto> othersAudiCars) {
        this.myAudiCars = myAudiCars;
        this.othersAudiCars = othersAudiCars;
    }

    public List<MyAudiCarDto> getMyAudiCars() {
        return myAudiCars;
    }

    public void setMyAudiCars(List<MyAudiCarDto> myAudiCars) {
        this.myAudiCars = myAudiCars;
    }

    public List<OthersAudiCarDto> getOthersAudiCars() {
        return othersAudiCars;
    }

    public void setOthersAudiCars(List<OthersAudiCarDto> othersAudiCars) {
        this.othersAudiCars = othersAudiCars;
    }
}
