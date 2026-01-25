package com.DhrubaStudio.journalApp.service;
import com.DhrubaStudio.journalApp.api.response.QuotesResponse;
import com.DhrubaStudio.journalApp.cache.AppCache;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Service
public class QuotesService {

    private final String ninjasApiKey;
    private final AppCache appCache;
    private final RestTemplate restTemplate;
    private final String API_URL;

    @Autowired
    public QuotesService(@Value("${api.ninjas.key}") String ninjasApiKey,
                         AppCache appCache,
                         RestTemplate restTemplate) {
        this.ninjasApiKey = ninjasApiKey;
        this.appCache = appCache;
        this.restTemplate = restTemplate;
        this.API_URL = appCache.apiData.get("API_Ninjas_Quotes");
    }

    private String formatQuoteToString(QuotesResponse quote) {
        StringBuilder sb = new StringBuilder();
        sb.append("“").append(quote.getQuote()).append("”");
        sb.append("\n— ").append(quote.getAuthor());
        return sb.toString();
    }

    public String getQuote() {

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Api-Key", ninjasApiKey);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<List<QuotesResponse>> response = restTemplate.exchange(API_URL, HttpMethod.GET, entity,
                    new ParameterizedTypeReference<List<QuotesResponse>>() {});

            if (!response.getStatusCode().is2xxSuccessful()) {
                return "API returned non-success status: " + response.getStatusCode();
            }

            List<QuotesResponse> quotesList = response.getBody();

            if (quotesList == null || quotesList.isEmpty()) {
                return "Success response, but no quotes found.";
            }

            QuotesResponse quote = quotesList.get(0);
            return formatQuoteToString(quote);

        } catch (HttpStatusCodeException e) {
            String errorBody = e.getResponseBodyAsString();
            return "API error " + e.getStatusCode() + ":\n" + errorBody;
        } catch (Exception e) {
            e.printStackTrace();
            return "Error while fetching quote: " + e.getMessage();
        }
    }
}