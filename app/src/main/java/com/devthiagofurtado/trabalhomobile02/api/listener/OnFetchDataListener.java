package com.devthiagofurtado.trabalhomobile02.api.listener;





import com.devthiagofurtado.trabalhomobile02.api.model.NewsHeadlines;

import java.util.List;

public interface OnFetchDataListener <NewsApiResponse>{
    void onFetchData(List<NewsHeadlines> list, String message);

    void onError(String message);
}
