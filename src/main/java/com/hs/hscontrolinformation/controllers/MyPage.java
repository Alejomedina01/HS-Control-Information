package com.hs.hscontrolinformation.controllers;

import java.util.List;

import lombok.Data;

@Data
public class MyPage <T>{
    private int numberPages;
    private long totalItems;
    private List<T> content;
}
