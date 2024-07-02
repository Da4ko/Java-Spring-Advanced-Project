package com.example.java_spring_advanced_project.model.dto;

import com.example.java_spring_advanced_project.model.dto.MyCars.MyPorscheCarDto;
import com.example.java_spring_advanced_project.model.dto.OthersCars.OthersPorscheCarDto;

import java.util.List;

public class HomePorscheCarsDto {
    private List<MyPorscheCarDto> myPorscheCars;
    private List<OthersPorscheCarDto> othersPorscheCars;

    public HomePorscheCarsDto() {
    }

    public HomePorscheCarsDto(List<MyPorscheCarDto> myPorscheCars, List<OthersPorscheCarDto> othersPorscheCars) {
        this.myPorscheCars = myPorscheCars;
        this.othersPorscheCars = othersPorscheCars;
    }

    public List<MyPorscheCarDto> getMyPorscheCars() {
        return myPorscheCars;
    }

    public void setMyPorscheCars(List<MyPorscheCarDto> myPorscheCars) {
        this.myPorscheCars = myPorscheCars;
    }

    public List<OthersPorscheCarDto> getOthersPorscheCars() {
        return othersPorscheCars;
    }

    public void setOthersPorscheCars(List<OthersPorscheCarDto> othersPorscheCars) {
        this.othersPorscheCars = othersPorscheCars;
    }
}
