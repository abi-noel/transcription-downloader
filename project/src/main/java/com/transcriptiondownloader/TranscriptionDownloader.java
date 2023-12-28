package com.transcriptiondownloader;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Caption;
import com.google.api.services.youtube.model.CaptionListResponse;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

public class TranscriptionDownloader {
    private static final String API_KEY = "AIzaSyAzuKQQeiCJBgxgHeu1AqbvyERkgARlAAw"; // Replace with your actual API key

    public static void main(String[] args) throws GeneralSecurityException, IOException {
        YouTube youtubeService = getService();

        // TODO: Replace "VIDEO_ID" with the ID of the YouTube video you want to download captions for
        String videoId = "CcG9boSS04s";
        listCaptions(youtubeService, videoId);
    }

    public static YouTube getService() throws GeneralSecurityException, IOException {
        return new YouTube.Builder(
                new NetHttpTransport(),
                JacksonFactory.getDefaultInstance(),
                null)
                .setApplicationName("API code samples")
                .build();
    }

    public static void listCaptions(YouTube youtubeService, String videoId) throws IOException {
        YouTube.Captions.List request = youtubeService.captions().list("snippet", videoId);
        request.setKey(API_KEY); // Set the API key here

        CaptionListResponse response = request.execute();
        List<Caption> captions = response.getItems();

        if (captions.isEmpty()) {
            System.out.println("No captions found.");
        } else {
            System.out.println("Captions:");
            for (Caption caption : captions) {
                System.out.println(caption.getSnippet().getName());
            }
        }
    }
}