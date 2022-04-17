package org.me.gcu.mpdcw_b_nimmo_s1802408;

/**
 * Blair Nimmo
 * S1802408
 */

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // declare variables/buttons and assign values
    private final String incidentsUrl = "https://trafficscotland.org/rss/feeds/currentincidents.aspx";
    private final String roadworksUrl = "https://trafficscotland.org/rss/feeds/roadworks.aspx";
    private final String plannedRoadworksUrl = "https://trafficscotland.org/rss/feeds/plannedroadworks.aspx";
    private LinkedList<Xml> alist;
    private TextView urlInput;
    private Button incidents;
    private Button roadworks;
    private Button plannedRoadworks;
    private EditText itemSearch;
    private String result = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        urlInput = (TextView) findViewById(R.id.urlInput);
        itemSearch = (EditText) findViewById(R.id.search);
        incidents = (Button) findViewById(R.id.incidents);
        incidents.setOnClickListener(this);
        roadworks = (Button) findViewById(R.id.roadworks);
        roadworks.setOnClickListener(this);
        plannedRoadworks = (Button) findViewById(R.id.plannedRoadworks);
        plannedRoadworks.setOnClickListener(this);
    }

    private LinkedList<Xml> parseXml(String xmlToParse) {

        alist = new LinkedList<Xml>();
        Xml xFile = new Xml();

        // set up XmlPullParser to run until end of document
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();

            xpp.setInput(new StringReader(xmlToParse));
            int eventType = xpp.getEventType();

            if (!alist.isEmpty())
            {
                alist.clear();
            } while (eventType != XmlPullParser.END_DOCUMENT){

                if (eventType == XmlPullParser.START_TAG) {
                    System.out.println("Start tag " + xpp.getName());

                    if (xpp.getName().equalsIgnoreCase("channel")) {
                        alist = new LinkedList<Xml>();
                    } else if (xpp.getName().equalsIgnoreCase("item")) {
                        Log.e("MyTag", "Item Start Tag Found");
                        xFile = new Xml();
                    } else if (xpp.getName().equalsIgnoreCase("title")) {

                        String temp = xpp.nextText();

                        Log.e("MyTag", "Title is " + temp);
                        xFile.setTitle(temp);
                    } else if (xpp.getName().equalsIgnoreCase("description")) {

                        String temp = xpp.nextText();

                        Log.e("MyTag", "Description is " + temp);
                        temp = temp.replaceAll("<br />", "\n \n")
                                .replaceAll("Date: ", "Date: \n")
                                .replaceAll("Information: ", "Information: \n")
                                .replaceAll("Traffic Management:", "\n\nTraffic Management: \n")
                                .replaceAll("Works:", "Work Information: \n");
                        xFile.setDescription(temp);
                        xFile.setDescription(temp);
                    } else if (xpp.getName().equalsIgnoreCase("link")) {

                        String temp = xpp.nextText();

                        Log.e("MyTag", "Link is " + temp);
                        xFile.setLink(temp);
                    } else if (xpp.getName().equalsIgnoreCase("Point")) {

                        String temp = xpp.nextText();

                        Log.e("MyTag", "Point is " + temp);
                        xFile.setPoint(temp);
                    } else if (xpp.getName().equalsIgnoreCase("author")) {

                        String temp = xpp.nextText();

                        Log.e("MyTag", "Author is " + temp);
                        xFile.setAuthor(temp);
                    } else if (xpp.getName().equalsIgnoreCase("comment")) {

                        String temp = xpp.nextText();

                        Log.e("MyTag", "Comment is " + temp);
                        xFile.setComment(temp);
                    } else if (xpp.getName().equalsIgnoreCase("pubDate")) {

                        String temp = xpp.nextText();

                        Log.e("MyTag", "Pub Date is " + temp);
                        xFile.setPubDate(temp);
                    }
                } else if(eventType == XmlPullParser.END_TAG) {

                    if (xpp.getName().equalsIgnoreCase("item")) {
                        Log.e("MyTag", "feed is " + xFile.toString());
                        System.out.print("Feed Is: " + xFile.toString());

                        if (xFile.getTitle().contains(itemSearch.getText().toString()) || xFile.getTitle().contains(itemSearch.getText().toString())) {
                            alist.add(xFile);
                        }
                    } else if (xpp.getName().equalsIgnoreCase("channel")) {
                        int size;
                        size = alist.size();
                        Log.e("MyTag", "channel size is " + size);
                    }
                }


                eventType = xpp.next();
            }

            System.out.println("End of Document");

            return alist;
        }
        catch (XmlPullParserException ae1)
        {
            Log.e("MyTag", "Parsing error" + ae1.toString());
        }
        catch (IOException ae1)
        {
            Log.e("MyTag", "IO error during parsing");
        }

        Log.e("MyTag", "End of document");

        return alist;
    }

    // determine which RSS feed to parse by button click
    public void onClick(View aview) {
        startProgress(aview);
    }

    public void startProgress(View btn) {
        if (btn.getId() == R.id.incidents) {
            new Thread(new Task(incidentsUrl)).start();
        } else if (btn.getId() == R.id.roadworks) {
            new Thread(new Task(roadworksUrl)).start();
        } else if (btn.getId() == R.id.plannedRoadworks) {
            new Thread(new Task(plannedRoadworksUrl)).start();
        }

    }

    class Task implements Runnable {

        private String url;
        public Task(String aurl) {
            url = aurl;
        }

        @Override
        public void run() {

            URL aurl;
            URLConnection yc;
            BufferedReader in = null;
            String inputLine = "";

            Log.e("MyTag", "in run");

            try {
                Log.e("MyTag", "in try");
                aurl = new URL(url);
                yc = aurl.openConnection();
                in = new BufferedReader(new InputStreamReader(yc.getInputStream()));

                if (!result.equals("")) { result = ""; }
                while ((inputLine = in.readLine()) != null) {
                    result = result + inputLine;

                }
                in.close();
            } catch (IOException ae) {
                Log.e("MyTag", "ioexception");
            }

            final LinkedList<Xml> alist;

            alist = parseXml(result);

            if (alist != null) {
                Log.e("MyTag", "This list is not null");
                for (Object o : alist)
                {
                    Log.e("MyTag", o.toString());
                }
            } else {
                Log.e("MyTag", "This list is null");
            }

            MainActivity.this.runOnUiThread(new Runnable() {
                public void run() {
                    Log.d("UI thread", "UI thread");

                    if(alist != null) {
                        urlInput.setText("");

                        for (Object o : alist) {
                            urlInput.append(o.toString());
                        }
                    }
                }
            });
        }
    }
}