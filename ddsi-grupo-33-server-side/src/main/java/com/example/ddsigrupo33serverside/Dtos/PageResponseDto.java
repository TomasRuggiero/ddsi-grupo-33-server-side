package com.example.ddsigrupo33serverside.Dtos;

import java.util.List;

import lombok.Data;

@Data
public class PageResponseDto<T> {
  private List<T> content;
  private int totalPages;
  private long totalElements;
  private int size;
  private int number;
}
