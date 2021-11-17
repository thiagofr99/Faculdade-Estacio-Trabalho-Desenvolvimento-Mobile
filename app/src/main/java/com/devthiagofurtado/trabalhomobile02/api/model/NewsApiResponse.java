package com.devthiagofurtado.trabalhomobile02.api.model;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewsApiResponse implements Serializable {

    String status;

    int totalResults;

    List<NewsHeadlines> articles;

}
