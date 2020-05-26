package com.hoymihoy.DoodleServer.Database;

import com.hoymihoy.DoodleServer.DTOS.Painting;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.ArrayList;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DB_PaintingsTest {

    DB_Paintings DBP = new DB_Paintings();

    @Test
    public void t00dropAllTablesTest() throws Exception {
        DBConnector DBC = new DBConnector();
        Assert.assertEquals(1, DBC.dropAllTables());
    }

    @Test
    public void t01CreateTablesTestPass() throws Exception {
        DBConnector DBC = new DBConnector();
        Assert.assertEquals(1, DBC.createTables());
    }

    @Test
    public void t02CreateNewPaintingTestPass() throws Exception {
        Painting p = new Painting();

        ArrayList<String> players = new ArrayList<>();

        players.add("dhg5054");
        players.add("username1");
        players.add("username2");

        p.setPlayers(players);

//        p.setGameName("test game");
//        Image img = new Image();
//        img.setName("path");
//        img.setData("data");
//        p.setImage(img);
//        p.setOwnerUserName("dhg5054");


        Assert.assertEquals(1, DBP.createNewPainting(p));
    }

    @Test
    public void t03CreateNewPaintingSameUserPass() throws Exception {
        Painting p = new Painting();

        ArrayList<String> players = new ArrayList<>();

        players.add("dhg5054");
        players.add("username1");
        players.add("username2");

        p.setPlayers(players);

//        p.setGameName("test game2");
//        Image img = new Image();
//        img.setName("path");
//        img.setData("data");
//        p.setImage(img);
//        p.setOwnerUserName("dhg5054");

        Assert.assertEquals(2, DBP.createNewPainting(p));
    }

    @Test
    public void t04QueryPaintingIDPass() throws Exception {
        Painting p;

        int paintingID = 2;

        p = DBP.queryPaintingID(paintingID);

        Assert.assertEquals(2, p.getPaintingID());
    }

    @Test
    public void t05QueryPaintingIDFail() throws Exception {
        Painting p;

        int paintingID = 10;

        p = DBP.queryPaintingID(paintingID);

        Assert.assertEquals(0, p.getPaintingID());
        //Assert.assertEquals(null, p.getImage());
    }

    @Test
    public void t06UpdatePaintingPass() throws Exception {
        Painting p = new Painting();

        p.setPaintingID(1);
        p.setOwnerUserName("dhg5054");
        p.setGameName("updated game name");
//        Image img = new Image();
//        img.setName("path");
//        img.setData("data");
//        p.setImage(img);

        int result = DBP.updatePainting(p);

        Assert.assertNotEquals(0, result);
        Assert.assertEquals(1, result);

    }

    @Test
    public void t07UpdatePaintingFail() throws Exception {
        Painting p = new Painting();

        p.setPaintingID(3);
        p.setOwnerUserName("dhg5054");
        p.setGameName("Nothing to see here");

        Assert.assertEquals(0, DBP.updatePainting(p));

//        Image img = new Image();
//        img.setName("path");
//        img.setData("data");
//        p.setImage(img);

        Assert.assertEquals(0, DBP.updatePainting(p));
    }

    @Test
    public void t08setUserPaintingsTestPass() throws Exception {
        Painting p = new Painting();

        ArrayList<String> players = new ArrayList<>();


        //p.setImage("game image text");
        p.setOwnerUserName("dhg5054");
        p.setGameName("game test");

        players.add("dhg5054");

        players.add("username2");

        players.add("username3");

        players.add("username4");

        p.setPlayers(players);

        int result = DBP.createNewPainting(p);

        Assert.assertEquals(3, result);
    }

    @Test
    public void t09getUserPaintingTestPass() throws Exception {
        String userName = "dhg5054";
        ArrayList<Painting> feed = new ArrayList<>(DBP.getUserPaintings(userName));

        Assert.assertEquals(3, feed.size());


        Painting p = new Painting();
        ArrayList<String> players = new ArrayList<>();

        p.setOwnerUserName("dhg5054");
//        Image img = new Image();
//        img.setName("path");
//        img.setData("data");
//        p.setImage(img);
        players.add(userName);
        p.setPlayers(players);

        DBP.createNewPainting(p);

        ArrayList<Painting> feed2 = new ArrayList<>(DBP.getUserPaintings("dhg5054"));

        Assert.assertEquals(4, feed2.size());
    }
}
