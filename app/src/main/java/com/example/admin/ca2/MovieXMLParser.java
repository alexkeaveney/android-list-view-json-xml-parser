package com.example.admin.ca2;

/**
 * Created by admin on 13/03/2017.
 */

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class MovieXMLParser {

    public static ArrayList<Movie> parseFeed(String content) {

        try {

            // Establish Variables that you need to keep track of where you are
            // are you in a data item you care about
            boolean inDataItemTag = false;
            //Which XML tag are you current in
            String currentTagName = "";
            // the Flower object you are currently constructing from the XML
            Movie m = null;
            // Full list of flowers as you pull them out of the XML
            ArrayList<Movie> movieList = new ArrayList<>();


            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            // content is the complete XML content that was passed in from the calling program
            parser.setInput(new StringReader(content));

            int eventType = parser.getEventType();

            // XMLPullParser generates events. Once for each start tag, end tag and for text events
            while (eventType != XmlPullParser.END_DOCUMENT) {

                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        currentTagName = parser.getName();

                        // if starting a new product create a new Flower object to start building it up.
                        if (currentTagName.equals("movie")) {
                            inDataItemTag = true;
                            m = new Movie();
                            movieList.add(m);
                        }
                        break;

                    case XmlPullParser.END_TAG:
                        // if leaving current product
                        if (parser.getName().equals("movie")) {
                            inDataItemTag = false;
                        }
                        currentTagName = "";
                        break;

                    case XmlPullParser.TEXT:
                        if (inDataItemTag && m != null) {
                            switch (currentTagName) {
                                case "movieId":
                                    m.setId(Integer.parseInt(parser.getText()));
                                case "name":
                                    m.setTitle(parser.getText());
                                    break;
                                case "releaseDate":
                                    String date = parser.getText();
                                    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                                    Date inputDate = dateFormat.parse(date);
                                    m.setReleaseDate(inputDate);
                                    break;
                                case "description":
                                    m.setDescription(parser.getText());
                                    break;
                                case "category":
                                    m.setCategory(parser.getText());
                                    break;
                                case "director":
                                    m.setDirector(parser.getText());
                                    break;
                                case "actors":
                                    String actors = parser.getText();
                                    String actorArray[] = actors.split(", ");
                                    m.setActors(actorArray);
                                    break;
                                case "photo":
                                    m.setPhotoLink(parser.getText());
                                    break;
                                default:
                                    break;
                            }
                        }
                        break;
                }

                eventType = parser.next();

            }

            // return the complete list that was generated above
            return movieList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }


}

