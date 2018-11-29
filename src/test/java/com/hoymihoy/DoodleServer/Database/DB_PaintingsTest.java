package com.hoymihoy.DoodleServer.Database;

import com.hoymihoy.DoodleServer.DTOS.Game;
import com.hoymihoy.DoodleServer.DTOS.Painting;
import com.hoymihoy.DoodleServer.DTOS.User;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.awt.*;
import java.util.ArrayList;

import static org.junit.Assert.*;

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

        p.setGameName("test game");
        p.setImage("image code here");
        p.setOwnerUserID(1);

        Assert.assertEquals(1, DBP.createNewPainting(p));
    }

    @Test
    public void t03CreateNewPaintingSameUserPass() throws Exception {
        Painting p = new Painting();

        p.setGameName("test game2");
        p.setImage("more image code here");
        p.setOwnerUserID(1);

        Assert.assertEquals(1, DBP.createNewPainting(p));
    }

    @Test
    public void t04QueryPaintingIDPass() throws Exception {
        Painting p;

        int paintingID = 2;

        p = DBP.queryPaintingID(paintingID);

        Assert.assertEquals("more image code here", p.getImage());
    }

    @Test
    public void t05QueryPaintingIDFail() throws Exception {
        Painting p;

        int paintingID = 10;

        p = DBP.queryPaintingID(paintingID);

        Assert.assertEquals(0, p.getPaintingID());
        Assert.assertEquals(null, p.getImage());
    }

    @Test
    public void t06UpdatePaintingPass() throws Exception {
        Painting p = new Painting();

        p.setPaintingID(1);
        p.setOwnerUserID(1);
        p.setGameName("updated game name");
        p.setImage("updated image text");

        int result = DBP.updatePainting(p);

        Assert.assertNotEquals(0, result);
        Assert.assertEquals(1, result);

    }

    @Test
    public void t07UpdatePaintingFail() throws Exception {
        Painting p = new Painting();

        p.setPaintingID(3);
        p.setOwnerUserID(1);
        p.setGameName("Nothing to see here");

        Assert.assertEquals(0, DBP.updatePainting(p));

        p.setImage("Move along.");

        Assert.assertEquals(0, DBP.updatePainting(p));
    }

    @Test
    public void t08createGameTestPass() throws Exception {
        Painting p = new Painting();
        Game g = new Game();
        User u = new User();
        ArrayList<User> players = new ArrayList<User>();
        int result;

        p.setImage("game image text");
        p.setOwnerUserID(1);
        p.setGameName("game test");
        g.setPainting(p);

        u.setUserID(1);
        u.setUsername("username1");
        u.setPassword("password");
        players.add(u);

        u = new User();
        u.setUserID(2);
        u.setUsername("username2");
        u.setPassword("password");
        players.add(u);

        u = new User();
        u.setUserID(3);
        u.setUsername("username3");
        u.setPassword("password");
        players.add(u);

        u = new User();
        u.setUserID(4);
        u.setUsername("username4");
        u.setPassword("password");
        players.add(u);

        g.setPlayers(players);

        result = DBP.createNewGame(g);

        Assert.assertEquals(4, result);
    }

    @Test
    public void t09getUserPaintingTestPass() throws Exception {
        User u = new User();
        u.setUserID(1);
        ArrayList<Painting> feed = new ArrayList<>(DBP.getUserPaintings(u));

        //Assert.assertEquals(1, feed.size());

        Game g = new Game();
        Painting p = new Painting();
        ArrayList<User> players = new ArrayList<>();

        p.setOwnerUserID(1);
        players.add(u);
        g.setPainting(p);
        g.setPlayers(players);

        DBP.createNewGame(g);

        ArrayList<Painting> feed2 = new ArrayList<>(DBP.getUserPaintings(u));

        Assert.assertEquals(2, feed2.size());
    }
}
