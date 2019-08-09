package com.mygdx.bang;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.io.File;

import javax.imageio.ImageIO;

public class StoryboardScreen extends LevelScreen{
    Music backMusic3, backWin;

    Sound saveS, removeS, shipSo, shipLandSo, winS;

    MenuTools tools;
    Skin skinTools;

    boolean editToolsOn = false;

    ToolItem currentItem;

    Image editT, rotate;
    Image[] sizePoint;

    IdeaTextArea textArea;

    boolean taking = false;
    boolean saving = false;

    Stage stage2;
    FrameBuffer frameBuffer;

    Pixmap screenPixmap;

    Image storyboardBack1, storyboardBack2, storyboardBack3, storyboardBack4, storyboardBack5, storyboardBack6;

    Image winBack, winNeed;
    Label winTitle, winText1, winText2;
    Image rectangle;

    Table editToolsT, editToolsCont;

    Image planet, destello;
    Ship ship;

    private boolean nextScreen= false, nextScreen2 = true;
    private String nextScreenAdap;

    boolean activeShip = false;

    boolean animBarActive = false;
    float newWidthBar = 0;
    boolean activeMove = true;

    StoryboardScreen(MissionScreen level){
        game = level.game;
        camera = game.camera;
        camera.position.set(960, 540, 0);
        viewport = game.viewport;
        batch = game.batch;
        stage = game.stage;
        Gdx.input.setInputProcessor(stage);

        assetManager = game.assetManager;

        dataUser = game.dataUser;
        experiencia = level.experiencia;

        closeBag = level.closeBag;

        musicButt = level.musicButt;
        soundButt = level.soundButt;

        scenes = level.scenes;
        currentScene = level.currentScene;
        currentMaze = level.currentMaze;
        currentNeed = level.currentNeed;

        skinCharacters = level.skinCharacters;

        blackTexture = game.transitionText;
        character = level.character;
        dialogBox = level.dialogBox;
        backButt = level.backButt;
        backButt.clearListeners();
        backButt.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                backScene();
                return true;
            }
        });
        nextButt = level.nextButt;
        nextButt.clearListeners();
        nextButt.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                nextCurrentScene();
                return true;
            }
        });
        dialogT = level.dialogT;
        dialog = level.dialog;
        title = level.title;
        rankNumber = level.rankNumber;

        background = level.background;
        comet = level.comet;
        asteroid = level.asteroid;
        satelite = level.satelite;
        alien = level.alien;
        stars1 = level.stars1;
        stars2 = level.stars2;
        stars3 = level.stars3;
        stars4 = level.stars4;

        menuCard = level.menuCard;
        menuCard.level = this;

        rankBar1 = level.rankBar1;
        rankBar2 = level.rankBar2;
        rankBar3 = level.rankBar3;

        emergentBox = level.emergentBox;

        blackTextureCards = level.blackTextureCards;

        card1 = level.card1;
        card1.clearListeners();
        card2 = level.card2;
        card2.clearListeners();
        card3 = level.card3;
        card3.clearListeners();
        menuCard.slots.get(3).addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                if(!menuCard.block || menuCard.currentTutorial == 3){
                    if(card1.onSlot){
                        card1.onSlot = false;
                        menuCard.close();
                        blackTextureCards.setZIndex(1000);
                        blackTextureCards.setVisible(true);
                        blackTextureCards.addListener(new InputListener(){
                            @Override
                            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                                card1.addAction(Actions.sequence(Actions.parallel(Actions.moveTo(1000, 1800, 0.8f), Actions.scaleTo(0.22f, 0.188f, 0.8f)), Actions.alpha(0), Actions.run(new Runnable(){
                                    @Override
                                    public void run(){
                                        blackTextureCards.setVisible(false);
                                        menuCard.slots.get(3).addActor(card1);
                                        card1.getColor().a = 1;
                                        card1.onSlot = true;
                                        card1.setPosition(0, 0);
                                        if(game.getSound())
                                            closeBag.play();
                                        if(menuCard.toNext){
                                            menuCard.toNext = false;
                                            nextCurrentScene();
                                        }
                                        blackTextureCards.clearListeners();
                                    }
                                })));
                                return true;
                            }
                        });
                        Vector2 posItem = card1.localToStageCoordinates(new Vector2(0, 0));
                        card1.remove();
                        stage.addActor(card1);
                        card1.setPosition(posItem.x, posItem.y);
                        card1.addAction(Actions.parallel(Actions.moveTo(150, 375, 0.7f), Actions.scaleTo(1, 1, 0.7f)));
                    }
                }
                return true;
            }
        });
        menuCard.slots.get(4).addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                if(!menuCard.block && card2.onSlot){
                    card2.onSlot = false;
                    menuCard.close();
                    blackTextureCards.setZIndex(1000);
                    blackTextureCards.setVisible(true);
                    blackTextureCards.addListener(new InputListener(){
                        @Override
                        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                            card2.addAction(Actions.sequence(Actions.parallel(Actions.moveTo(1000, 1800, 0.8f), Actions.scaleTo(0.22f, 0.188f, 0.8f)), Actions.alpha(0), Actions.run(new Runnable(){
                                @Override
                                public void run(){
                                    blackTextureCards.setVisible(false);
                                    blackTextureCards.clearListeners();
                                    menuCard.slots.get(4).addActor(card2);
                                    card2.getColor().a = 1;
                                    card2.onSlot = true;
                                    card2.setPosition(0, 0);
                                    if(game.getSound())
                                        closeBag.play();
                                    if(menuCard.toNext){
                                        menuCard.toNext = false;
                                        nextCurrentScene();
                                    }
                                }
                            })));
                            return true;
                        }
                    });
                    Vector2 posItem = card2.localToStageCoordinates(new Vector2(0, 0));
                    card2.remove();
                    stage.addActor(card2);
                    card2.setPosition(posItem.x, posItem.y);
                    card2.addAction(Actions.parallel(Actions.moveTo(150, 375, 0.7f), Actions.scaleTo(1, 1, 0.7f)));
                }
                return true;
            }
        });
        menuCard.slots.get(5).addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                if(!menuCard.block && card3.onSlot){
                    card3.onSlot = false;
                    menuCard.close();
                    blackTextureCards.setZIndex(1000);
                    blackTextureCards.setVisible(true);
                    blackTextureCards.addListener(new InputListener(){
                        @Override
                        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                            card3.addAction(Actions.sequence(Actions.parallel(Actions.moveTo(1000, 1800, 0.8f), Actions.scaleTo(0.22f, 0.188f, 0.8f)), Actions.alpha(0), Actions.run(new Runnable(){
                                @Override
                                public void run(){
                                    blackTextureCards.setVisible(false);
                                    blackTextureCards.clearListeners();
                                    menuCard.slots.get(5).addActor(card3);
                                    card3.getColor().a = 1;
                                    card3.onSlot = true;
                                    card3.setPosition(0, 0);
                                    if(game.getSound())
                                        closeBag.play();
                                    if(menuCard.toNext){
                                        menuCard.toNext = false;
                                        nextCurrentScene();
                                    }
                                }
                            })));
                            return true;
                        }
                    });
                    Vector2 posItem = card3.localToStageCoordinates(new Vector2(0, 0));
                    card3.remove();
                    stage.addActor(card3);
                    card3.setPosition(posItem.x, posItem.y);
                    card3.addAction(Actions.parallel(Actions.moveTo(150, 375, 0.7f), Actions.scaleTo(1, 1, 0.7f)));
                }
                return true;
            }
        });
        planet = new Image(assetManager.get("planet"+(currentMaze+1)+".png", Texture.class));
        planet.setSize(planet.getWidth()+(planet.getWidth()*0.5f), planet.getHeight()+(planet.getHeight()*0.5f));
        planet.setVisible(false);
        ship = level.ship;
        destello = level.destello;
        destello.setBounds(650, 1450, 300, 300);
        destello.setOrigin(150, 150);
        destello.setScale(0);
        destello.setVisible(false);
        constructScreen();
    }

    StoryboardScreen(GetBangsScreen level, int _indexIdea){
        game = level.game;
        camera = game.camera;
        camera.position.set(960, 540, 0);
        viewport = game.viewport;
        batch = game.batch;
        stage = game.stage;
        Gdx.input.setInputProcessor(stage);

        assetManager = game.assetManager;

        dataUser = game.dataUser;
        experiencia = dataUser.getRank();
        Gdx.app.log("datoR", String.valueOf(experiencia));
        float dato = (experiencia/1500);
        String exp = String.valueOf(dato);
        float currentExp = 0;
        if(exp.contains(".")){
            String[] splitExp = exp.split("\\.");
            exp = splitExp[0];
            String exp2 = "0."+splitExp[1];
            currentExp = Float.valueOf(exp2);
            currentExp = currentExp*1500;
        }
        Label.LabelStyle labelStyle3 = new Label.LabelStyle();
        labelStyle3.font = new BitmapFont(Gdx.files.internal("delius.fnt"));
        labelStyle3.fontColor = Color.WHITE;

        closeBag = Gdx.audio.newSound(Gdx.files.internal("sounds/briefcase.wav"));

        rankNumber = new Label("0", labelStyle3);
        rankNumber.setAlignment(Align.center);
        rankNumber.setWrap(true);
        rankNumber.setBounds(50, 1800, 68, 68);
        rankNumber.setText(exp);

        rankBar1 = new Image(new Texture("levelBar1.png"));
        rankBar1.setPosition(30, 1749);
        rankBar2 = new Image(new Texture("levelBar2.png"));
        rankBar2.setPosition(110, 1811);
        rankBar3 = new Image(new Texture("levelBar3.png"));
        rankBar2.setWidth(0);
        rankBar3.setPosition(110, 1811);
        float newSize = (currentExp*376)/1500;
        rankBar2.setWidth(newSize);
        rankBar3.setX(rankBar2.getX()+rankBar2.getWidth());
        dataUser.setRank(experiencia);

        musicButt = level.musicButt;
        soundButt = level.soundButt;

        scenes = level.scenes;
        currentScene = 26;
        currentMaze = level.currentMaze;
        currentNeed = level.currentNeed;

        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(1, 1, 1, 1);
        pixmap.fill();
        skinCharacters = new Skin();
        skinCharacters.add("Captain", assetManager.get("captain.png", Texture.class));
        skinCharacters.add("Crispi", assetManager.get("crispi.png", Texture.class));
        skinCharacters.add("Cesia", assetManager.get("cesia.png", Texture.class));
        skinCharacters.add("Cori", assetManager.get("cori.png", Texture.class));
        skinCharacters.add("Carmel", assetManager.get("carmel.png", Texture.class));
        skinCharacters.add("Cristal", assetManager.get("cristal.png", Texture.class));
        skinCharacters.add("dialogCaptain", assetManager.get("dialogCaptain.png", Texture.class));
        skinCharacters.add("dialogCesia", assetManager.get("dialogCesia.png", Texture.class));
        skinCharacters.add("dialogCori", assetManager.get("dialogCori.png", Texture.class));
        skinCharacters.add("dialogCarmel", assetManager.get("dialogCarmel.png", Texture.class));
        skinCharacters.add("dialogCristal", assetManager.get("dialogCristal.png", Texture.class));
        skinCharacters.add("Alien", assetManager.get("alienChar.png", Texture.class));
        skinCharacters.add("idea", assetManager.get("ideaBox.png", Texture.class));
        skinCharacters.add("addButt", assetManager.get("addButt.png", Texture.class));
        skinCharacters.add("aceptButt", assetManager.get("aceptButt.png", Texture.class));
        skinCharacters.add("siButt", assetManager.get("siButt.png", Texture.class));
        skinCharacters.add("noButt", assetManager.get("noButt.png", Texture.class));
        skinCharacters.add("hand", assetManager.get("hand.png", Texture.class));
        skinCharacters.add("rocket", assetManager.get("rocket.png", Texture.class));
        skinCharacters.add("head1", assetManager.get("cards/head1.png", Texture.class));
        skinCharacters.add("head2", assetManager.get("cards/head2.png", Texture.class));
        skinCharacters.add("head3", assetManager.get("cards/head3.png", Texture.class));
        skinCharacters.add("head4", assetManager.get("cards/head4.png", Texture.class));
        skinCharacters.add("head5", assetManager.get("cards/head5.png", Texture.class));
        skinCharacters.add("backCards", new Texture(pixmap));
        skinCharacters.add("iconN1", assetManager.get("needs/iconN1.png", Texture.class));
        skinCharacters.add("iconN2", assetManager.get("needs/iconN2.png", Texture.class));
        skinCharacters.add("iconN3", assetManager.get("needs/iconN3.png", Texture.class));
        skinCharacters.add("iconN4", assetManager.get("needs/iconN4.png", Texture.class));
        skinCharacters.add("iconN5", assetManager.get("needs/iconN5.png", Texture.class));
        skinCharacters.add("iconN6", assetManager.get("needs/iconN6.png", Texture.class));
        skinCharacters.add("iconN7", assetManager.get("needs/iconN7.png", Texture.class));
        skinCharacters.add("iconN8", assetManager.get("needs/iconN8.png", Texture.class));
        skinCharacters.add("iconN9", assetManager.get("needs/iconN9.png", Texture.class));
        skinCharacters.add("iconN10", assetManager.get("needs/iconN10.png", Texture.class));
        skinCharacters.add("iconN11", assetManager.get("needs/iconN11.png", Texture.class));
        skinCharacters.add("iconN12", assetManager.get("needs/iconN12.png", Texture.class));
        skinCharacters.add("iconN13", assetManager.get("needs/iconN13.png", Texture.class));
        skinCharacters.add("iconN14", assetManager.get("needs/iconN14.png", Texture.class));
        skinCharacters.add("iconN15", assetManager.get("needs/iconN15.png", Texture.class));

        blackTexture = game.transitionText;
        //dialogos
        character = new Image(skinCharacters.getDrawable("Captain"));
        character.setBounds(78, 1404, 310, 298);
        character.setVisible(false);
        dialogBox = new Image(skinCharacters.getDrawable("dialogCaptain"));
        dialogBox.setPosition(76, 60);
        dialogBox.setVisible(false);
        backButt = new Image();
        nextButt = new Image(assetManager.get("nextButt.png", Texture.class));
        nextButt.setPosition(800, 26);
        nextButt.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                nextCurrentScene();
                return true;
            }
        });
        nextButt.setVisible(false);

        //Escenas
        String[][] scenesData;
        String[][] scenesCloseData;
        scenesData = scenes3;
        scenesCloseData = toClose2;
        scenes = new Scene[scenesData.length];
        for(int i = 0; i < scenesData.length; i++){
            scenes[i] = new Scene(i, scenesData[i][1], Integer.valueOf(scenesData[i][2]), Integer.valueOf(scenesData[i][3]));
            if(scenesData[i][1].equals("Dialog")){
                if(scenesData[i][2].equals("1")){
                    for(int j = 0; j < especials.length; j++){
                        if(especials[j][0][0].equals(scenesData[i][0])){
                            scenes[i].setScene(especials[j]);
                            break;
                        }
                    }
                }else{
                    for(int j = 0; j < dialogs.length; j++){
                        if(dialogs[j][0].equals(scenesData[i][0])){
                            scenes[i].setScene(dialogs[j][1], dialogs[j][2]);
                            break;
                        }
                    }
                }
            }
            if(scenesData[i][3].equals("1")){
                Gdx.app.log("Close", String.valueOf(scenesData[i][0]));
                for(int j = 0; j < scenesCloseData.length; j++){
                    if(scenesCloseData[j][0].equals(scenesData[i][0])){
                        Gdx.app.log("Close", String.valueOf(scenesCloseData[j][0]));
                        scenes[i].setToClose(scenesCloseData[j]);
                        break;
                    }
                }
            }
        }
        currentScene = 26;

        dialog = new Label("", labelStyle3);
        dialog.setAlignment(Align.top, Align.center);
        dialog.setWrap(true);
        dialog.setWidth(842);

        dialogT = new Table();
        dialogT.add(dialog).width(842).padTop(50).top().grow();
        dialogT.setBounds(135, 547, 842, 842);
        dialogT.setVisible(false);

        Label.LabelStyle labelStyle2 = new Label.LabelStyle();
        labelStyle2.font = new BitmapFont(Gdx.files.internal("shadows.fnt"));
        labelStyle2.fontColor = Color.WHITE;
        title = new Label("", labelStyle2);
        title.setAlignment(Align.center);
        title.setWrap(true);
        title.setBounds(320, 1540, 620, 100);
        title.setVisible(false);

        background = level.background;
        comet = level.comet;
        asteroid = level.asteroid;
        satelite = level.satelite;
        alien = level.alien;
        stars1 = level.stars1;
        stars2 = level.stars2;
        stars3 = level.stars3;
        stars4 = level.stars4;

        menuCard = level.menuCard;
        menuCard.level = this;
        menuCard.setProblemaText("[#511577]Recuerda que estas ayudando a "+charactersNames[currentMaze]+" en el tema de [#ff0000]"+auxTexts2[currentNeed-1], skinCharacters.getDrawable("iconN"+(currentNeed)));
        menuCard.setSolucionText("[#511577]"+dataUser.getIdeas().get(_indexIdea)[2]);

        emergentBox = level.emergentBox;

        blackTextureCards = new Image(new Texture(pixmap));
        blackTextureCards.setBounds(0, 0, 1080, 1920);
        blackTextureCards.setColor(0, 0, 0, 0.7f);
        blackTextureCards.setVisible(false);

        card1 = new CardItem(skinCharacters.getDrawable("head1"), skinCharacters.getDrawable("backCards"), labelStyle2);
        card1.setScale(0.2858f, 0.27496f);
        menuCard.slots.get(3).addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                if(!menuCard.block || menuCard.currentTutorial == 3){
                    if(card1.onSlot){
                        card1.onSlot = false;
                        menuCard.close();
                        blackTextureCards.setZIndex(1000);
                        blackTextureCards.setVisible(true);
                        blackTextureCards.addListener(new InputListener(){
                            @Override
                            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                                card1.addAction(Actions.sequence(Actions.parallel(Actions.moveTo(1000, 1800, 0.8f), Actions.scaleTo(0.22f, 0.188f, 0.8f)), Actions.alpha(0), Actions.run(new Runnable(){
                                    @Override
                                    public void run(){
                                        blackTextureCards.setVisible(false);
                                        menuCard.slots.get(3).addActor(card1);
                                        card1.getColor().a = 1;
                                        card1.onSlot = true;
                                        card1.setPosition(0, 0);
                                        if(game.getSound())
                                            closeBag.play();
                                        if(menuCard.toNext){
                                            menuCard.toNext = false;
                                            nextCurrentScene();
                                        }
                                        blackTextureCards.clearListeners();
                                    }
                                })));
                                return true;
                            }
                        });
                        Vector2 posItem = card1.localToStageCoordinates(new Vector2(0, 0));
                        card1.remove();
                        stage.addActor(card1);
                        card1.setPosition(posItem.x, posItem.y);
                        card1.addAction(Actions.parallel(Actions.moveTo(150, 375, 0.7f), Actions.scaleTo(1, 1, 0.7f)));
                    }
                }
                return true;
            }
        });
        card2 = new CardItem(skinCharacters.getDrawable("head1"), skinCharacters.getDrawable("backCards"), labelStyle2);
        card2.setScale(0.22f, 0.188f);
        menuCard.slots.get(4).addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                if(!menuCard.block && card2.onSlot){
                    card2.onSlot = false;
                    menuCard.close();
                    blackTextureCards.setZIndex(1000);
                    blackTextureCards.setVisible(true);
                    blackTextureCards.addListener(new InputListener(){
                        @Override
                        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                            card2.addAction(Actions.sequence(Actions.parallel(Actions.moveTo(1000, 1800, 0.8f), Actions.scaleTo(0.22f, 0.188f, 0.8f)), Actions.alpha(0), Actions.run(new Runnable(){
                                @Override
                                public void run(){
                                    blackTextureCards.setVisible(false);
                                    blackTextureCards.clearListeners();
                                    menuCard.slots.get(4).addActor(card2);
                                    card2.getColor().a = 1;
                                    card2.onSlot = true;
                                    card2.setPosition(0, 0);
                                    if(game.getSound())
                                        closeBag.play();
                                    if(menuCard.toNext){
                                        menuCard.toNext = false;
                                        nextCurrentScene();
                                    }
                                }
                            })));
                            return true;
                        }
                    });
                    Vector2 posItem = card2.localToStageCoordinates(new Vector2(0, 0));
                    card2.remove();
                    stage.addActor(card2);
                    card2.setPosition(posItem.x, posItem.y);
                    card2.addAction(Actions.parallel(Actions.moveTo(150, 375, 0.7f), Actions.scaleTo(1, 1, 0.7f)));
                }
                return true;
            }
        });
        card3 = new CardItem(skinCharacters.getDrawable("head1"), skinCharacters.getDrawable("backCards"), labelStyle2);
        card3.setScale(0.22f, 0.188f);
        menuCard.slots.get(5).addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                if(!menuCard.block && card3.onSlot){
                    card3.onSlot = false;
                    menuCard.close();
                    blackTextureCards.setZIndex(1000);
                    blackTextureCards.setVisible(true);
                    blackTextureCards.addListener(new InputListener(){
                        @Override
                        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                            card3.addAction(Actions.sequence(Actions.parallel(Actions.moveTo(1000, 1800, 0.8f), Actions.scaleTo(0.22f, 0.188f, 0.8f)), Actions.alpha(0), Actions.run(new Runnable(){
                                @Override
                                public void run(){
                                    blackTextureCards.setVisible(false);
                                    blackTextureCards.clearListeners();
                                    menuCard.slots.get(5).addActor(card3);
                                    card3.getColor().a = 1;
                                    card3.onSlot = true;
                                    card3.setPosition(0, 0);
                                    if(game.getSound())
                                        closeBag.play();
                                    if(menuCard.toNext){
                                        menuCard.toNext = false;
                                        nextCurrentScene();
                                    }
                                }
                            })));
                            return true;
                        }
                    });
                    Vector2 posItem = card3.localToStageCoordinates(new Vector2(0, 0));
                    card3.remove();
                    stage.addActor(card3);
                    card3.setPosition(posItem.x, posItem.y);
                    card3.addAction(Actions.parallel(Actions.moveTo(150, 375, 0.7f), Actions.scaleTo(1, 1, 0.7f)));
                }
                return true;
            }
        });

        String[][] currentCardData = dataUser.getCards().get(_indexIdea);
        for(int i = 0; i < currentCardData.length; i++){
            CardItem currentCardItem = card1;
            if(i == 1){
                currentCardItem = card2;
            }else if(i == 2){
                currentCardItem = card3;
            }
            currentCardItem.head.setDrawable(skinCharacters.getDrawable("head"+(Integer.parseInt(currentCardData[i][0])+1)));
            currentCardItem.label.setText(tarjetas[Integer.parseInt(currentCardData[i][0])][Integer.parseInt(currentCardData[i][1])]);
            currentCardItem.label.setColor(colorTextMazes[Integer.parseInt(currentCardData[i][0])]);
            currentCardItem.setColor(colorMazes[Integer.parseInt(currentCardData[i][0])]);
            currentCardItem.setScale(0.22f, 0.188f);
            menuCard.addCard(currentCardItem);
            currentCardItem.cardNum = Integer.parseInt(currentCardData[i][0]);
            currentCardItem.maze = Integer.parseInt(currentCardData[i][1]);
            currentCardItem.onSlot = true;
        }

        planet = new Image(assetManager.get("planet"+(currentMaze+1)+".png", Texture.class));
        planet.setSize(planet.getWidth()+(planet.getWidth()*0.5f), planet.getHeight()+(planet.getHeight()*0.5f));
        planet.setVisible(false);
        TextureRegionDrawable[] texturesShip = new TextureRegionDrawable[9];
        Texture tShip = assetManager.get("fire.png", Texture.class);
        TextureRegion[][] tempShip = TextureRegion.split(tShip, 550/5, 322/2);
        int animIndex = 0;
        for(int i = 0; i < 2; i++){
            for(int j = 0; j < 5; j++){
                texturesShip[animIndex] = new TextureRegionDrawable(tempShip[i][j]);
                if(animIndex == 8){
                    break;
                }
                animIndex++;
            }
        }
        ship = new Ship(skinCharacters.getDrawable("rocket"), texturesShip);
        ship.setOrigin(122, 279.5f);
        ship.setPosition(-244, -559);
        ship.setVisible(false);
        destello = new Image(new Texture("destello.png"));
        destello.setBounds(650, 1450, 300, 300);
        destello.setOrigin(150, 150);
        destello.setScale(0);
        destello.setVisible(false);

        constructScreen();
    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0.094f, 0.078f, 0.266f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.app.log("zoom", String.valueOf(game.stageCamera.position.x));
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        comet.update(delta);
        asteroid.update(delta);
        satelite.update(delta);
        alien.update(delta);

        if(tools.scrolling > 0){
            tools.scrollTo();
        }

        if(activeShip)
            ship.update(delta);

        if(animBarActive)
            animBar(newWidthBar, delta);

        if(nextScreen){
            game.setProgress();
            if(nextScreen2){
                if(assetManager.update()){
                    if(assetManager.getProgress() >= 1){
                        stage.addAction(Actions.sequence(Actions.delay(0.2f), Actions.run(new Runnable(){
                            @Override
                            public void run(){
                                backMusic3.stop();
                                if(nextScreenAdap.equals("Menu"))
                                    game.setScreen(new MenuScreen(game, false));
                            }
                        })));
                        nextScreen2 = false;
                    }
                }
            }
        }

        stage.act(delta);
        stage.draw();
        if(taking){
            taking = false;
            takeScreenShot();
        }

        if(saving){
            saving = false;
            saveScreenshot();
        }
    }

    @Override
    public void show(){
        stage.clear();
        game.backLoad.setVisible(false);
        stage.addActor(game.backLoad);
        game.barLoad.setVisible(false);
        stage.addActor(game.barLoad);
        game.endBarLoad.setVisible(false);
        stage.addActor(game.endBarLoad);

        stage.addActor(background);
        for(int i = 0; i < stars1.size; i++){
            stage.addActor(stars1.get(i));
        }
        for(int i = 0; i < stars2.size; i++){
            stage.addActor(stars2.get(i));
        }
        for(int i = 0; i < stars3.size; i++){
            stage.addActor(stars3.get(i));
        }
        for(int i = 0; i < stars4.size; i++){
            stage.addActor(stars4.get(i));
        }
        stage.addActor(asteroid);
        stage.addActor(comet);
        stage.addActor(satelite);
        stage.addActor(alien);
        stage.addActor(storyboardBack1);
        stage.addActor(storyboardBack2);
        stage.addActor(storyboardBack3);
        stage.addActor(storyboardBack4);
        stage.addActor(storyboardBack5);
        stage.addActor(storyboardBack6);
        stage.addActor(rectangle);
        stage.addActor(tools);
        stage.addActor(character);
        stage.addActor(dialogBox);
        stage.addActor(dialogT);
        stage.addActor(title);
        stage.addActor(backButt);
        stage.addActor(nextButt);
        stage.addActor(menuCard);
        stage.addActor(menuCard.blackTexture);
        stage.addActor(blackTextureCards);
        stage.addActor(rankBar1);
        stage.addActor(rankBar2);
        stage.addActor(rankBar3);
        stage.addActor(rankNumber);
        stage.addActor(winNeed);
        stage.addActor(winBack);
        stage.addActor(winTitle);
        stage.addActor(winText1);
        stage.addActor(winText2);
        stage.addActor(blackTexture);

        stage.addActor(editT);
        stage.addActor(rotate);
        for(int i = 0; i < 8; i++){
            stage.addActor(sizePoint[i]);
        }

        stage.addActor(editToolsT);

        stage.addActor(musicButt);
        musicButt.clearListeners();
        musicButt.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                Gdx.app.log("Musica", "Entre");
                if(game.getMusic()){
                    game.setMusic(false);
                    backMusic3.pause();
                }else{
                    game.setMusic(true);
                    backMusic3.play();
                }
                return true;
            }
        });
        stage.addActor(soundButt);
        stage.addActor(emergentBox);
        stage.addActor(emergentBox.blackTexture);
        stage.addActor(textArea.blackTexture);
        stage.addActor(textArea);

        stage.addActor(planet);
        stage.addActor(ship);
        stage.addActor(destello);

        blackTexture.clearListeners();
        blackTexture.setColor(0, 0, 0, 1);
        blackTexture.setVisible(true);

        stage.addAction(Actions.run(new Runnable(){
            @Override
            public void run(){
                nextCurrentScene();
            }
        }));
    }

    @Override
    public void hide(){
        stage.clear();
        game.stageCamera.zoom = 1;
        game.stageCamera.position.set(new Vector3(540, 960, 0));
    }

    public void resize(int point, float x, float y){
        //currentItem.setOrigin(currentItem.getWidth()/2, currentItem.getHeight()/2);
        float oldW = currentItem.width2;
        float newW = 0;
        float oldH = currentItem.height2;
        float newH = 0;
        float newX = 0, newY = 0;
        if(point == 0 || point == 1 || point == 2){
            //currentItem.setHeight(currentItem.getHeight()+y);
            //currentItem.setY(currentItem.getY()-y);
            newH = oldH+y;
            newY = currentItem.y2-(y/2);
        }else if(point == 4 || point == 5 || point == 6){
            //currentItem.setHeight(currentItem.getHeight()-y);
            newH = oldH-y;
            newY = currentItem.y2+(y/2);
        }
        float newScaleY = (currentItem.getScaleY()*newH)/oldH;
        if(point == 0 || point == 6 || point == 7){
            //currentItem.setWidth(currentItem.getWidth()-x);
            //currentItem.setX(currentItem.getX()+x);
            newW = oldW-x;
            newX = currentItem.x2+(x/2);
        }else if(point == 2 || point == 3 || point == 4){
            //currentItem.setWidth(currentItem.getWidth()+x);
            newW = oldW+x;
            newX = currentItem.x2-(x/2);
        }
        float newScaleX = (currentItem.getScaleX()*newW)/oldW;
        if(newScaleX != 0){
            currentItem.setScaleX(newScaleX);
            currentItem.x2 = newX;
            currentItem.width2 = newW;
        }
        if(newScaleY != 0){
            currentItem.setScaleY(newScaleY);
            currentItem.y2 = newY;
            currentItem.height2 = newH;
        }
        currentItem.pointsRezise(currentItem.getRotation());
        //tools.showEdit();
    }

    public void nextCurrentScene(){
        Gdx.app.log("Next", scenes[currentScene].type);

        menuCard.close();
        emergentBox.setVisible2(false);

        if(scenes[currentScene].close == 1){
            for(int i = 1; i < scenes[currentScene].toClose.length; i++){
                closeScene(scenes[currentScene].toClose[i]);
            }
        }

        boolean next = false;
        if(scenes[currentScene].type.equals("Dialog")){
            blackTexture.setBounds(0, 0, 1080, 1920);
            blackTexture.setColor(0, 0, 0, 0.5f);
            blackTexture.setVisible(true);
            blackTexture.setZIndex(1000);
            dialogBox.setVisible(true);
            dialogBox.setZIndex(1000);
            character.setVisible(true);
            character.setZIndex(1000);
            if(scenes[currentScene].especial == 1){
                int select = currentMaze+1;
                String currentDialog  = "dialogCaptain";
                String name  = "Crispi";
                if(currentMaze == 1){
                    currentDialog = "dialogCesia";
                    name  = "Cesia";
                }else if(currentMaze == 2){
                    currentDialog = "dialogCori";
                    name  = "Cori";
                }else if(currentMaze == 3){
                    currentDialog = "dialogCarmel";
                    name  = "Carmel";
                }else if(currentMaze == 4){
                    currentDialog = "dialogCristal";
                    name  = "Cristal";
                }
                dialogBox.setDrawable(skinCharacters.getDrawable(currentDialog));
                title.setText(name);
                dialog.setText(scenes[currentScene].texts[select][1]);
                character.setDrawable(skinCharacters.getDrawable(scenes[currentScene].texts[select][0]));
            }else{
                if(scenes[currentScene].text.contains("***Nombre***")){
                    Gdx.app.log("NuevoTexto", "Entre");
                    String nombre = "";
                    if(currentMaze == 0){
                        nombre = "Crispi";
                    }else if(currentMaze == 1){
                        nombre = "Cesia";
                    }else if(currentMaze == 2){
                        nombre = "Cori";
                    }else if(currentMaze == 3){
                        nombre = "Carmel";
                    }else if(currentMaze == 4){
                        nombre = "Cristal";
                    }
                    String newText = scenes[currentScene].text.replace("***Nombre***", nombre);
                    if(scenes[currentScene].actor.equals("Captain"))
                        title.setText("Capitán");
                    else
                        title.setText(scenes[currentScene].actor);
                    dialog.setText(newText);
                }else{
                    if(scenes[currentScene].actor.equals("Captain"))
                        title.setText("Capitán");
                    else
                        title.setText(scenes[currentScene].actor);
                    dialog.setText(scenes[currentScene].text);
                }
                if(scenes[currentScene].actor.equals("Crispi"))
                    dialogBox.setDrawable(skinCharacters.getDrawable("dialogCaptain"));
                else if(scenes[currentScene].actor.equals("Alien"))
                    dialogBox.setDrawable(skinCharacters.getDrawable("dialogCristal"));
                else
                    dialogBox.setDrawable(skinCharacters.getDrawable("dialog"+scenes[currentScene].actor));
                character.setDrawable(skinCharacters.getDrawable(scenes[currentScene].actor));
                if(scenes[currentScene].text.contains("***Planet***")){
                    String newText = String.valueOf(dialog.getText().replace("***Planet***", planetNames[currentMaze]));
                    dialog.setText(newText);
                }
            }
            dialogT.setZIndex(1000);
            dialogT.setVisible(true);
            title.setVisible(true);
            title.setZIndex(1000);
            nextButt.setVisible(true);
            nextButt.setZIndex(1000);
            if(currentScene-1 >= 0 && scenes[currentScene-1].type.equals("Dialog")){
                backButt.setVisible(true);
                backButt.setZIndex(1000);
            }
        }else if(scenes[currentScene].type.equals("WinNeed")){
            winNeed.setVisible(true);
            game.stageCamera.zoom = 1;
            game.stageCamera.position.set(new Vector3(540, 960, 0));
        }else if(scenes[currentScene].type.equals("End")){
            planet.setVisible(true);
            planet.setPosition(70, 0);
            ship.setVisible(true);
        }else if(scenes[currentScene].type.equals("EndBegin")){
            animShip();
        }else if(scenes[currentScene].type.equals("Win")){
            showComodinText("");
        }else if(scenes[currentScene].type.equals("Transition")){
            addTransition();
        }else if(scenes[currentScene].type.equals("TransitionIn")){
            addTransitionIn();
        }else if(scenes[currentScene].type.equals("TransitionOut")){
            addTransitionOut();
        }

        int nextScene = currentScene+1;
        if(nextScene < scenes.length && scenes[nextScene].type.equals("Next")){
            Gdx.app.log("Next", "Entre");
            next = true;
        }
        currentScene++;
        if(next){
            Gdx.app.log("Next", "Entre2");
            currentScene++;
            stage.addAction(Actions.sequence(Actions.delay(0.5f), Actions.run(new Runnable(){
                @Override
                public void run(){
                    nextCurrentScene();
                }
            })));
        }
        menuCard.setZIndex(1000);
    }

    public void closeScene(String scene){
        if(scene.equals("Dialog")){
            blackTexture.setVisible(false);
            character.setVisible(false);
            dialogBox.setVisible(false);
            dialogT.setVisible(false);
            title.setVisible(false);
            backButt.setVisible(false);
            nextButt.setVisible(false);
        }else if(scene.equals("Storyboard")){
            tools.setVisible(false);
            storyboardBack1.setVisible(false);
            storyboardBack2.setVisible(false);
            storyboardBack3.setVisible(false);
            storyboardBack4.setVisible(false);
            storyboardBack5.setVisible(false);
            storyboardBack6.setVisible(false);
            editToolsT.setVisible(false);
            rotate.setVisible(false);
            editT.setVisible(false);
            for(int i = 0; i < 8; i++){
                sizePoint[i].setVisible(false);
            }
            for(int i = 0; i < tools.ballon1.size; i++){
                tools.ballon1.get(i).setVisible(false);
            }
        }else if(scene.equals("WinNeed")){
            winNeed.setVisible(false);
        }
    }

    public void showEditBox(){
        Gdx.input.setOnscreenKeyboardVisible(true);
        textArea.blackTexture.setZIndex(1000);
        textArea.blackTexture.setVisible(true);
        textArea.setZIndex(1001);
        textArea.setBounds(76, 1200, 928, 300);
        textArea.textField.setText("");
        textArea.setVisible(true);
        stage.setKeyboardFocus(textArea.textField);
    }

    public void showEditBox2(){
        Gdx.input.setOnscreenKeyboardVisible(true);
        textArea.blackTexture.setZIndex(1000);
        textArea.blackTexture.setVisible(true);
        textArea.setZIndex(1001);
        textArea.setBounds(76, 1200, 928, 300);
        textArea.textField.setText(String.valueOf(currentItem.textLbl.getText()));
        textArea.setVisible(true);
        stage.setKeyboardFocus(textArea.textField);
    }

    public void hideTextArea(){
        Gdx.input.setOnscreenKeyboardVisible(false);
        textArea.setVisible(false);
        textArea.blackTexture.setVisible(false);
    }

    public void takeScreenShot(){
            frameBuffer.bind();
            frameBuffer.begin();
            Gdx.gl20.glClearColor(0, 0, 0, 0);
            Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );
            stage2.getBatch().setProjectionMatrix(camera.combined);
            stage2.draw();
            byte[] pixels2 = ScreenUtils.getFrameBufferPixels(0, 0, 1080, 1920, true);
            frameBuffer.end();
            for(int i = 4; i < pixels2.length; i += 4) {
                pixels2[i - 1] = (byte) 255;
            }
            BufferUtils.copy(pixels2, 0, screenPixmap.getPixels(), pixels2.length);

            stage.addActor(storyboardBack1);
            stage.addActor(storyboardBack2);
            stage.addActor(storyboardBack3);
            stage.addActor(storyboardBack4);
            stage.addActor(storyboardBack5);
            stage.addActor(storyboardBack6);
            for(int i = 0; i < tools.ballon1.size; i++){
                stage.addActor(tools.ballon1.get(i));
            }
            stage.getBatch().setProjectionMatrix(camera.combined);
            stage.getCamera().update();

            saving = true;
    }

    public void saveScreenshot(){
        boolean cantSave = true;
        int currentFile = 1;
        String externalPath = Gdx.files.getExternalStoragePath();
        Gdx.app.log("file", externalPath);
        FileHandle file = Gdx.files.external("Pictures/Bangs/miBang1.png");
        while(cantSave){
            if(!file.exists()){
                cantSave = false;
            }else{
                currentFile++;
                file = Gdx.files.external("Pictures/Bangs/miBang"+currentFile+".png");
            }
        }

        if(!cantSave){
            if(!file.exists()){
                try{
                    PixmapIO.writePNG(file, screenPixmap);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            if(game.getSound())
                saveS.play();
            String idea = menuCard.slots.get(2).completeText;
            idea = idea.replace("[#511577]", "");
            idea = idea.replace("[#ffffff]", "");
            game.uploadImage(idea, "miBang"+currentFile+".png");
            screenPixmap.dispose();
            nextCurrentScene();
        }
    }

    @Override
    public void resize(int width, int height){
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
    }

    public void addScore(float cant){
        experiencia += cant;
        float dato = (experiencia/1500);
        String exp = String.valueOf(dato);
        float currentExp = 0;
        if(exp.contains(".")){
            String[] splitExp = exp.split("\\.");
            exp = splitExp[0];
            String exp2 = "0."+splitExp[1];
            currentExp = Float.valueOf(exp2);
            currentExp = currentExp*1500;
        }
        //experiencia = Integer.valueOf(exp);
        //rankNumber.setText(exp);
        //float newSize = (cant*100)/376;
        float newSize = (currentExp*376)/1500;
        Gdx.app.log("newSize", String.valueOf(newSize));
        /*rankBar2.setWidth(newSize);
        rankBar3.setX(rankBar2.getX()+rankBar2.getWidth());*/
        animBarActive = true;
        if(newSize == 0)
            newWidthBar = 377;
        else
            newWidthBar = newSize;
    }

    public void showEdiTools(){
        if(editToolsOn){
            editToolsT.addAction(Actions.moveTo(-212, 231, 0.3f));
            editToolsOn = false;
        }else{
            editToolsT.addAction(Actions.moveTo(0, 231, 0.3f));
            editToolsOn = true;
        }
    }

    public void setCurrentItem(ToolItem _tool){
        currentItem = _tool;
    }

    public void unfocuItem(){
        currentItem = null;
        tools.activeTool = false;
        editT.setVisible(false);
        rotate.setVisible(false);
        for(int i = 0; i < sizePoint.length; i++){
            sizePoint[i].setVisible(false);
        }
    }

    @Override
    public void showLoad(String asset){
        Gdx.app.log("load", asset);
        nextScreen = true;
        nextScreenAdap = asset;
        game.loadAssets(asset);
        blackTexture.setZIndex(1000);
        blackTexture.setColor(0, 0, 0, 1);
        blackTexture.setVisible(true);
        game.backLoad.setZIndex(1000);
        game.backLoad.setVisible(true);
        game.barLoad.setZIndex(1001);
        game.barLoad.setVisible(true);
        game.endBarLoad.setZIndex(1002);
        game.endBarLoad.setVisible(true);
    }

    public void constructScreen(){
        frameBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, 1080, 1920, false);
        screenPixmap = new Pixmap(1080, 1920, Pixmap.Format.RGBA8888);

        backMusic3 = Gdx.audio.newMusic(Gdx.files.internal("sounds/porcupop.mp3"));
        backMusic3.setVolume(0.5f);
        backMusic3.setLooping(true);
        if(game.getMusic())
            backMusic3.play();

        saveS = Gdx.audio.newSound(Gdx.files.internal("sounds/shortSuccess.wav"));
        removeS = Gdx.audio.newSound(Gdx.files.internal("sounds/doorClosing.wav"));
        shipLandSo = Gdx.audio.newSound(Gdx.files.internal("sounds/shipLanding.wav"));
        shipSo = Gdx.audio.newSound(Gdx.files.internal("sounds/rocketFly.wav"));
        winS = Gdx.audio.newSound(Gdx.files.internal("sounds/winSound.wav"));

        Pixmap p = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        p.setColor(0.9216f, 0.7686f, 0.3176f, 1);
        p.fill();
        skinTools = new Skin();
        skinTools.add("toolsButt", assetManager.get("storyboard/toolsButt.png", Texture.class));
        skinTools.add("editToolsButt", assetManager.get("storyboard/editToolsButt.png", Texture.class));
        skinTools.add("editToolsBox", assetManager.get("storyboard/editToolsBox.png", Texture.class));
        skinTools.add("arrow", assetManager.get("storyboard/arrow.png", Texture.class));
        skinTools.add("addButt", assetManager.get("addButt.png", Texture.class));
        skinTools.add("backScroll", new Texture(p));
        skinTools.add("default1", assetManager.get("storyboard/tools/default/default1.png", Texture.class));
        skinTools.add("default2", assetManager.get("storyboard/tools/default/default2.png", Texture.class));
        skinTools.add("default3", assetManager.get("storyboard/tools/default/default3.png", Texture.class));
        skinTools.add("default4", assetManager.get("storyboard/tools/default/default4.png", Texture.class));
        skinTools.add("default5", assetManager.get("storyboard/tools/default/default5.png", Texture.class));
        skinTools.add("default6", assetManager.get("storyboard/tools/default/default6.png", Texture.class));
        skinTools.add("default7", assetManager.get("storyboard/tools/default/default7.png", Texture.class));
        skinTools.add("default8", assetManager.get("storyboard/tools/default/default8.png", Texture.class));
        skinTools.add("default9", assetManager.get("storyboard/tools/default/default9.png", Texture.class));
        skinTools.add("default10", assetManager.get("storyboard/tools/default/default10.png", Texture.class));
        skinTools.add("default11", assetManager.get("storyboard/tools/default/default11.png", Texture.class));
        skinTools.add("default12", assetManager.get("storyboard/tools/default/default12.png", Texture.class));
        skinTools.add("default13", assetManager.get("storyboard/tools/default/default13.png", Texture.class));
        skinTools.add("default14", assetManager.get("storyboard/tools/default/default14.png", Texture.class));
        skinTools.add("default15", assetManager.get("storyboard/tools/default/default15.png", Texture.class));
        skinTools.add("default16", assetManager.get("storyboard/tools/default/default16.png", Texture.class));
        skinTools.add("default17", assetManager.get("storyboard/tools/default/default17.png", Texture.class));
        skinTools.add("default18", assetManager.get("storyboard/tools/default/default18.png", Texture.class));
        skinTools.add("default19", assetManager.get("storyboard/tools/default/default19.png", Texture.class));
        skinTools.add("save", assetManager.get("storyboard/butts/save.png", Texture.class));
        skinTools.add("zoomIn", assetManager.get("storyboard/butts/zoomIn.png", Texture.class));
        skinTools.add("zoomOut", assetManager.get("storyboard/butts/zoomOut.png", Texture.class));
        skinTools.add("text", assetManager.get("storyboard/butts/text.png", Texture.class));
        skinTools.add("moreZ", assetManager.get("storyboard/butts/moreZ.png", Texture.class));
        skinTools.add("lessZ", assetManager.get("storyboard/butts/lessZ.png", Texture.class));
        skinTools.add("remove", assetManager.get("storyboard/butts/remove.png", Texture.class));

        //objectsByNeed
        skinTools.add("default20", assetManager.get("storyboard/tools/n"+currentNeed+"/obj1.png", Texture.class));
        skinTools.add("default21", assetManager.get("storyboard/tools/n"+currentNeed+"/obj2.png", Texture.class));
        skinTools.add("default22", assetManager.get("storyboard/tools/n"+currentNeed+"/obj3.png", Texture.class));
        skinTools.add("default23", assetManager.get("storyboard/tools/n"+currentNeed+"/obj4.png", Texture.class));
        skinTools.add("default24", assetManager.get("storyboard/tools/n"+currentNeed+"/obj5.png", Texture.class));

        storyboardBack1 = new Image(assetManager.get("storyboard/boxSmall.png", Texture.class));
        storyboardBack1.setPosition(89, 1396);
        storyboardBack2 = new Image(assetManager.get("storyboard/boxSmall.png", Texture.class));
        storyboardBack2.setPosition(541, 1396);
        storyboardBack3 = new Image(assetManager.get("storyboard/boxBig.png", Texture.class));
        storyboardBack3.setPosition(89, 965);
        storyboardBack4 = new Image(assetManager.get("storyboard/boxSmall.png", Texture.class));
        storyboardBack4.setPosition(89, 532);
        storyboardBack5 = new Image(assetManager.get("storyboard/boxSmall.png", Texture.class));
        storyboardBack5.setPosition(541, 532);
        storyboardBack6 = new Image(assetManager.get("storyboard/boxBig.png", Texture.class));
        storyboardBack6.setPosition(89, 98);
        rectangle = new Image(skinTools.getDrawable("backScroll"));
        rectangle.setColor(0, 0, 0, 0);
        rectangle.setBounds(0, 0, 1080, 1920);
        rectangle.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                activeMove = false;
                return true;
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer){
                if(!tools.activeTool){
                    activeMove = true;
                    float deltaX = Gdx.input.getDeltaX();
                    float deltaY = Gdx.input.getDeltaY();
                    float endPosX = (1080*((1-game.stageCamera.zoom)+1));
                    float endPosY = (1920*((1-game.stageCamera.zoom)+1));
                    Vector2 correctPos = new Vector2(((endPosX-1080)/2)+540-(endPosX-1080), ((endPosY-1920)/2)+960-(endPosY-1920));
                    Vector2 correctPosEnd = new Vector2(((endPosX-1080)/2)+540, ((endPosY-1920)/2)+960);

                    Vector2 newCoords = stage.stageToScreenCoordinates(new Vector2(0, 0));
                    Vector2 newCoordsEnd = stage.stageToScreenCoordinates(new Vector2(1080, 1920));
                    if(newCoords.x<0 || newCoordsEnd.x>Gdx.graphics.getWidth()){
                        if(deltaX>0){
                            if((game.stageCamera.position.x -= deltaX) >= correctPos.x)
                                game.stageCamera.position.x -= deltaX;
                            else
                                game.stageCamera.position.x = correctPos.x;
                        }else if(deltaX<0){
                            if((game.stageCamera.position.x -= deltaX) <= correctPosEnd.x)
                                game.stageCamera.position.x -= deltaX;
                            else
                                game.stageCamera.position.x = correctPosEnd.x;
                        }
                    }
                    if(newCoords.y>0 || newCoordsEnd.y<Gdx.graphics.getHeight()){
                        if(deltaY<0){
                            if((game.stageCamera.position.y += deltaY) >= correctPos.y){
                                game.stageCamera.position.y += deltaY;
                            }else{
                                game.stageCamera.position.y = correctPos.y;
                            }
                        }else if(deltaY>0){
                            if((game.stageCamera.position.y += deltaY) <= correctPosEnd.y)
                                game.stageCamera.position.y += deltaY;
                            else
                                game.stageCamera.position.y = correctPosEnd.y;
                        }
                    }
                }
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                if(currentItem != null && !activeMove){
                    unfocuItem();
                }
            }
        });
        tools = new MenuTools(this);
        tools.stage = stage;

        editT = new Image(new Texture("editIcon.png"));
        editT.setSize(70, 70);
        editT.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                showEditBox2();
                return true;
            }
        });
        editT.setVisible(false);
        rotate = new Image(new Texture("rotateIcon.png"));
        rotate.setSize(70, 70);
        rotate.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                return true;
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer){
                currentItem.rotate(x, y);
            }
        });
        rotate.setVisible(false);

        Texture texturePoint = new Texture("point.png");
        sizePoint = new Image[8];
        for(int i = 0; i < 8; i++){
            Image currentImage = new Image(texturePoint);
            currentImage.setSize(40, 40);
            final int finalI = i;
            currentImage.addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                    return true;
                }

                @Override
                public void touchDragged(InputEvent event, float x, float y, int pointer){
                    resize(finalI, Gdx.input.getDeltaX(), Gdx.input.getDeltaY());
                    //tools.currentItem.pointsRezise(tools.currentItem.getRotation());
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                    //tools.currentItem.pointsRezise(tools.currentItem.getRotation());
                }
            });
            currentImage.setVisible(false);
            sizePoint[i] = currentImage;
        }

        final Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = new BitmapFont(Gdx.files.internal("delius.fnt"));
        labelStyle.fontColor = Color.WHITE;

        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.font = new BitmapFont();
        textFieldStyle.fontColor = Color.WHITE;
        textArea = new IdeaTextArea(assetManager.get("ideaBox.png", Texture.class), textFieldStyle);
        textArea.butt1.setDrawable(skinTools.getDrawable("addButt"));
        textArea.butt1.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                if(currentItem != null && currentItem.textLbl != null){
                    //currentItem.textLbl.setText(textArea.textField.getText());
                    currentItem.setTextLbl(textArea.textField.getText());
                    hideTextArea();
                }else{
                    ToolItem label = new ToolItem(tools, textArea.textField.getText());
                    tools.ballon1.add(label);
                    stage.addActor(label);
                    hideTextArea();
                }

                return true;
            }
        });
        textArea.setVisible(false);

        winNeed = new Image(assetManager.get("nC"+currentNeed+".png", Texture.class));
        winNeed.setPosition(0, 0);
        winNeed.setVisible(false);

        winBack = new Image(new Texture("winBack.png"));
        winBack.setPosition(150, 400);
        winBack.setVisible(false);

        winTitle = new Label("BANG COMPLETADO", labelStyle);
        winTitle.setPosition(180, 1380);
        winTitle.setWrap(true);
        winTitle.setWidth(718);
        winTitle.setAlignment(Align.center);
        winTitle.setVisible(false);
        winText1 = new Label("Tema: Lineamientos corporativos", labelStyle);
        winText1.setFontScale(1.24f);
        winText1.setPosition(140, 500);
        winText1.setWidth(780);
        winText1.setWrap(true);
        winText1.setAlignment(Align.top);
        winText1.setVisible(false);
        winText2 = new Label("+500 Experiencia", labelStyle);
        winText2.setPosition(150, 664);
        winText2.setWidth(780);
        winText2.setWrap(true);
        winText2.setAlignment(Align.top);
        winText2.setVisible(false);

        Image editToolsButt = new Image(skinTools.getDrawable("editToolsButt"));
        editToolsButt.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                showEdiTools();
                return true;
            }
        });

        Image saveBangButt = new Image(skinTools.getDrawable("save"));
        saveBangButt.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                stage.addAction(Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        stage2 = new Stage(new FitViewport(1080, 1920));
                        stage2.getBatch().enableBlending();
                        stage2.getBatch().setColor(0, 0, 0, 0);
                        stage2.addActor(storyboardBack1);
                        stage2.addActor(storyboardBack2);
                        stage2.addActor(storyboardBack3);
                        stage2.addActor(storyboardBack4);
                        stage2.addActor(storyboardBack5);
                        stage2.addActor(storyboardBack6);
                        for(int i = 0; i < tools.ballon1.size; i++){
                            stage2.addActor(tools.ballon1.get(i));
                        }
                        taking = true;
                    }
                }));
                return true;
            }
        });
        Image zoomInButt = new Image(skinTools.getDrawable("zoomIn"));
        zoomInButt.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                if(game.stageCamera.zoom > 0.3f){
                    game.stageCamera.zoom -= 0.3f;
                    game.stageCamera.position.x = 540;
                    game.stageCamera.position.y = 960;
                }
                return true;
            }
        });
        Image zoomOutButt = new Image(skinTools.getDrawable("zoomOut"));
        zoomOutButt.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                if(game.stageCamera.zoom < 1){
                    game.stageCamera.zoom += 0.3f;
                    game.stageCamera.position.x = 540;
                    game.stageCamera.position.y = 960;
                }
                return true;
            }
        });
        Image textButt = new Image(skinTools.getDrawable("text"));
        textButt.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                /*if(currentItem != null && currentItem.textLbl != null){
                    showEditBox2();
                }else{*/
                showEditBox();
                //}
                return true;
            }
        });
        Image moreZButt = new Image(skinTools.getDrawable("moreZ"));
        moreZButt.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                if(currentItem != null){
                    currentItem.setZIndex(currentItem.getZIndex()+1);
                }
                return true;
            }
        });
        Image lessZButt = new Image(skinTools.getDrawable("lessZ"));
        lessZButt.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                if(currentItem != null){
                    if(currentItem.getZIndex()-1>rectangle.getZIndex())
                        currentItem.setZIndex(currentItem.getZIndex()-1);
                }
                return true;
            }
        });
        Image removeButt = new Image(skinTools.getDrawable("remove"));
        removeButt.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                if(currentItem != null){
                    currentItem.remove();
                    removeS.play();
                    stage.addAction(Actions.run(new Runnable(){
                        @Override
                        public void run(){
                            tools.ballon1.removeIndex(tools.ballon1.indexOf(currentItem, true));
                            unfocuItem();
                        }
                    }));
                }
                return true;
            }
        });
        editToolsCont = new Table();
        editToolsCont.setBackground(skinTools.getDrawable("editToolsBox"));
        editToolsCont.add(saveBangButt).size(92, 94).padTop(71).padBottom(85).expand().center();
        editToolsCont.row();
        editToolsCont.add(zoomInButt).size(84, 106).padBottom(85).expand().center();
        editToolsCont.row();
        editToolsCont.add(zoomOutButt).size(84, 106).padBottom(101).expand().center();
        editToolsCont.row();
        editToolsCont.add(textButt).size(115, 80).padBottom(115).expand().center();
        editToolsCont.row();
        editToolsCont.add(moreZButt).size(111, 111).padBottom(104).expand().center();
        editToolsCont.row();
        editToolsCont.add(lessZButt).size(104, 104).padBottom(112).expand().center();
        editToolsCont.row();
        editToolsCont.add(removeButt).size(86, 104).padBottom(47).expand().center();
        editToolsCont.setSize(212, 1402);

        editToolsT = new Table();
        editToolsT.add(editToolsCont).size(212, 1402);
        editToolsT.add(editToolsButt).size(92, 368).align(Align.center);
        editToolsT.setPosition(-212, 231);
        editToolsT.setSize(304, 1402);
    }

    public void animShip(){
        activeShip = true;
        final float moveToX = planet.getX()+(planet.getWidth()/2)-ship.getWidth()/2;
        float moveToY1 = planet.getY()+(planet.getHeight()/2)-30-ship.getHeight()/2;
        float moveToY2 = planet.getY()+planet.getHeight()+10-ship.getHeight()/2;
        ship.setOrigin(Align.center);
        ship.addAction(Actions.sequence(Actions.scaleTo(0, 0), Actions.moveTo(moveToX, moveToY1), Actions.parallel(Actions.moveTo(moveToX-48.8f, moveToY2, 2), Actions.scaleTo(0.4f, 0.4f, 2)), Actions.rotateTo(-32, 0.5f), Actions.run(new Runnable() {
                    @Override
                    public void run(){
                        if(game.getSound())
                            shipSo.play(0.7f);
                    }
                }),
                Actions.parallel(Actions.moveTo(670, 1300, 2, Interpolation.exp5Out), Actions.scaleTo(0, 0, 2)), Actions.delay(0.2f), Actions.run(new Runnable() {
            @Override
            public void run(){
                destello.setVisible(true);
                destello.addAction(Actions.sequence(Actions.alpha(0), Actions.parallel(Actions.alpha(1, 0.7f), Actions.scaleTo(0.8f, 0.8f, 0.7f)), Actions.rotateTo(90, 0.5f), Actions.alpha(0, 1), Actions.run(new Runnable() {
                    @Override
                    public void run(){
                        destello.setVisible(false);
                        nextCurrentScene();
                    }
                })));
            }
        })));
        if(game.getSound())
            shipLandSo.play();
    }

    public void showComodinText(String type){
        blackTexture.setColor(0, 0, 0, 0.8f);
        blackTexture.setVisible(true);
        blackTexture.setZIndex(1000);
        textComodin = new Image(assetManager.get("winBack2.png", Texture.class));
        textComodin.setPosition(69.5f, 208);
        stage.addActor(textComodin);
        firework1 = new Image(assetManager.get("firework1.png", Texture.class));
        firework1.setPosition(122.24f, 1070.9f);
        firework1.addAction(Actions.forever(Actions.sequence(Actions.alpha(0.5f, 0.5f), Actions.alpha(1, 0.5f))));
        stage.addActor(firework1);
        firework2 = new Image(assetManager.get("firework2.png", Texture.class));
        firework2.setPosition(918.24f, 837.11f);
        firework2.addAction(Actions.forever(Actions.sequence(Actions.alpha(0.5f, 0.5f), Actions.alpha(1, 0.5f))));
        stage.addActor(firework2);
        firework3 = new Image(assetManager.get("firework1.png", Texture.class));
        firework3.setBounds(170.1f, 681.54f, 145, 29);
        firework3.addAction(Actions.forever(Actions.sequence(Actions.alpha(0.5f, 0.5f), Actions.alpha(1, 0.5f))));
        stage.addActor(firework3);
        firework4 = new Image(assetManager.get("firework2.png", Texture.class));
        firework4.setBounds(196.79f, 627.29f, 118, 130);
        firework4.addAction(Actions.forever(Actions.sequence(Actions.alpha(0.5f, 0.5f), Actions.alpha(1, 0.5f))));
        stage.addActor(firework4);
        firework5 = new Image(assetManager.get("firework1.png", Texture.class));
        firework5.setBounds(217.44f, 330.22f, 199.45f, 218.15f);
        firework5.addAction(Actions.forever(Actions.sequence(Actions.alpha(0.5f, 0.5f), Actions.alpha(1, 0.5f))));
        stage.addActor(firework5);
        firework6 = new Image(assetManager.get("firework2.png", Texture.class));
        firework6.setBounds(550.54f, 546.94f, 107.48f, 130.8f);
        firework6.addAction(Actions.forever(Actions.sequence(Actions.alpha(0.5f, 0.5f), Actions.alpha(1, 0.5f))));
        stage.addActor(firework6);
        gift = new Image(assetManager.get("trophie.png", Texture.class));
        gift.setPosition(300, 870);
        stage.addActor(gift);
        rankBar1.setVisible(true);
        rankBar2.setVisible(true);
        rankBar3.setVisible(true);
        rankNumber.setVisible(true);
        rankBar1.setPosition(300, 555);
        rankBar2.setPosition(375, 616);
        rankBar3.setPosition(375, 616);
        rankNumber.setPosition(320, 606);
        rankBar1.setZIndex(1001);
        rankBar2.setZIndex(1001);
        rankBar3.setZIndex(1001);
        rankNumber.setZIndex(1001);
        winText1.setText("Tema: "+auxTexts2[currentNeed-1]);
        winText1.setZIndex(1001);
        winText1.setVisible(true);
        addScore(500);
        dataUser.setRank(experiencia);
        if(game.getSound())
            winS.play();
        game.addIdeas();
    }

    public void animBar(float toWidth, float delta){
        if(rankBar2.getWidth()<toWidth){
            rankBar2.setWidth(rankBar2.getWidth()+(40*delta));
            rankBar3.setX(rankBar2.getX()+rankBar2.getWidth());
            if(rankBar2.getWidth()>376){
                rankBar2.setWidth(0);
                rankBar3.setX(rankBar2.getX()+rankBar2.getWidth());
                newWidthBar = 0;
                int newNumber = Integer.parseInt(rankNumber.getText().toString());
                rankNumber.setText(String.valueOf(newNumber+1));
                rankNumber.addAction(Actions.sequence(Actions.scaleTo(3, 3, 0.7f), Actions.scaleTo(1, 1, 0.7f)));
            }
        }else{
            final Image mainMenuButt = new Image(assetManager.get("mainMenuButt.png", Texture.class));
            mainMenuButt.setPosition(540-mainMenuButt.getWidth()/2, 150);
            mainMenuButt.addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                    mainMenuButt.setVisible(false);
                    showLoad("Menu");
                    return true;
                }
            });
            stage.addActor(mainMenuButt);
        }
    }
}