package com.antonytime;


import twitter4j.*;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.List;

public class TwitterCommands {

    private static final String consumerKey="pXX3zrDBM5rnSKTiV4PzGLAGp";
    private static final String consumerSecret="2gvb3CQdQHWESHVa91ov5Anba8VYs39ZgmRain1st0cuyrADME";

    private int count = 1;

    Twitter twitter = TwitterFactory.getSingleton();

    public void logIn() throws Exception {

        Twitter twitter = TwitterFactory.getSingleton();
        twitter.setOAuthConsumer(consumerKey, consumerSecret);
        RequestToken requestToken = twitter.getOAuthRequestToken();
        AccessToken accessToken = null;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (null == accessToken) {
            System.out.println("Open the following URL and grant access to your account:");
            System.out.println(requestToken.getAuthorizationURL());
            Desktop.getDesktop().browse(new URI(requestToken.getAuthorizationURL()));
            System.out.print("Enter the PIN(if aviailable) or just hit enter.[PIN]:");
            String pin = br.readLine();
            try{
                if(pin.length() > 0){
                    accessToken = twitter.getOAuthAccessToken(requestToken, pin);
                    System.out.println("Successfully LogIn!");
                }else{
                    accessToken = twitter.getOAuthAccessToken();
                }
            } catch (TwitterException te) {
                if(401 == te.getStatusCode()){
                    System.out.println("Unable to get the access token.");
                } else {
                    te.printStackTrace();
                }
            }
        }

    }

    public void logOut(){

        twitter.setOAuthAccessToken(null);
        System.out.println("Successfully LogOut!");

    }

    public void postTweet(String latestStatus){

        Status status = null;

        try {

            status = twitter.updateStatus(latestStatus);
        } catch (TwitterException e) {
            e.printStackTrace();
        }

        System.out.println("Successfully updated the status to [" + status.getText() + "].");

    }

    public void getTweets(int numOfTweets){

        List<Status> statuses = null;
        try {

            Paging page = new Paging (1, numOfTweets);
            statuses = twitter.getUserTimeline(page);

        } catch (TwitterException e) {

            e.printStackTrace();
        }

        if (statuses != null) {
            for (Status status : statuses) {

                System.out.println(count + " -------------------------------------------");
                count++;

                System.out.println(status.getUser().getName() + ": " +
                        status.getText());
            }
        }
        count = 0;

    }

    public void searchTweets(String searchString){

        Query query = new Query(searchString);
        QueryResult result = null;
        try {
            result = twitter.search(query);
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        assert result != null;
        for (Status status : result.getTweets()) {
            count++;
            System.out.println(count + " -------------------------------------------");
            System.out.println("@" + status.getUser().getScreenName() + ":" + status.getText());
        }
    }

    public void getHelp(){

        System.out.println("Commands:\n" +
                "logIn - LogIn to Twitter\n" +
                "logOut - LogOut from Twitter\n" +
                "postTweet - Post your tweet\n" +
                "getTweets - Getting your tweet\n" +
                "searchTweets - Search for a word or phrase\n");
    }

}
