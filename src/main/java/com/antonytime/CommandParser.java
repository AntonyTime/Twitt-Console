package com.antonytime;

import java.util.Scanner;

public class CommandParser {

TwitterCommands twitterCommands = new TwitterCommands();

    Scanner sc = new Scanner(System.in);

    public void parser() throws Exception {

        System.out.println("Welcome to Twitt Console!");

            for (; ; ) {

                try {

                    System.out.print("$twit ");

                    String a = sc.next();

                    if (a.equals("login")) {

                        twitterCommands.logIn();

                    }
                    if (a.equals("logout")) {

                        twitterCommands.logOut();

                    }
                    if (a.equals("post")) {

                        System.out.print("Compose new Tweet: ");
                        twitterCommands.postTweet(sc.next());

                    }
                    if (a.equals("tweets")) {

                        System.out.print("Enter number Of Tweets: ");
                        twitterCommands.getTweets(sc.nextInt());

                    }
                    if (a.equals("search")) {

                        System.out.print("Search Twitter: ");
                        twitterCommands.searchTweets(sc.next());

                    }
                    if (a.equals("help")) {

                        twitterCommands.getHelp();

                    }
                    if (a.equals("exit")) {

                        System.exit(0);

//                    } else {
//
//                        System.err.println("command not found, see 'help'");
                    }

                } catch (Exception e) {

                    System.out.println(e);

                }
            }
    }
}








