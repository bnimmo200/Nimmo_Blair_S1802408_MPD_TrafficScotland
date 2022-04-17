package org.me.gcu.mpdcw_b_nimmo_s1802408;

/**
 * Blair Nimmo
 * S1802408
 */

public class Xml {
    private String title;
    private String description;
    private String point;
    private String author;
    private String comment;
    private String link;
    private String pubDate;

    public Xml()
    {
        title = "";
        description = "";
        link = "";
        point = "";
        author = "";
        comment = "";
        pubDate = "";
    }

    public Xml(String atitle, String adescription, String alink, String apoint, String aauthor, String acomment, String apubDate)
    {
        title = atitle;
        description = adescription;
        link = alink;
        point = apoint;
        author = aauthor;
        comment = acomment;
        pubDate = apubDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String geoPoint) {
        this.point = geoPoint;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }


    @Override
    public String toString()
    {
        String temp;
        temp = title  + "\n" +
                description + " \n" +
                link + " \n" +
                point + "\n" +
                author + " \n" +
                comment + " \n" +
                pubDate+ " \n" + " \n" + " \n" +
                "---------------------------------------------------------"+ " \n";
        return temp;
    }




}

