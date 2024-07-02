package com.example.java_spring_advanced_project.model.dto;

import com.example.java_spring_advanced_project.model.dto.MyCars.MyBmwCarDto;
import com.example.java_spring_advanced_project.model.dto.OthersCars.OthersBmwCarDto;

import java.util.List;

public class HomeBmwCarsDto {
    private List<MyBmwCarDto> myBmwCars;
    private List<OthersBmwCarDto> othersBmwCars;

    public HomeBmwCarsDto() {
    }

    public HomeBmwCarsDto(List<MyBmwCarDto> myBmwCars, List<OthersBmwCarDto> othersBmwCars) {
        this.myBmwCars = myBmwCars;
        this.othersBmwCars = othersBmwCars;
    }

    public List<MyBmwCarDto> getMyBmwCars() {
        return myBmwCars;
    }

    public void setMyBmwCars(List<MyBmwCarDto> myBmwCars) {
        this.myBmwCars = myBmwCars;
    }

    public List<OthersBmwCarDto> getOthersBmwCars() {
        return othersBmwCars;
    }

    public void setOthersBmwCars(List<OthersBmwCarDto> othersBmwCars) {
        this.othersBmwCars = othersBmwCars;
    }
}
