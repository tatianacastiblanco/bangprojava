package com.mygdx.bang;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.viewport.FitViewport;

import org.w3c.dom.Text;

public class MenuScreen extends ScreenAdapter{
    BANG game;
    OrthographicCamera camera;
    FitViewport viewport;
    SpriteBatch batch;
    Stage stage;

    DataJson dataUser;

    Image blackText;
    Image musicButt, soundButt;
    Image bangLogo, newGame, continueGame, characteres;
    MovingImage comet, asteroid, satelite, alien;
    Array<Image> stars1, stars2, stars3, stars4, stars5;

    Music menuMusic;
    Sound spaceShip;

    String toScreen;
    boolean onOut = false;
    boolean nextScreen = false, nextScreen2 = true;

    Image background;

    AssetManager assetManager;

    EmergentBox emergentBox;
    boolean withTutorials = true;
    Skin skinMenu;
    boolean onCredits = false;
    ScrollPane scrollPane;
    Table creditsT;
    Label credits1, credits2, verCreditos;
    Image aceptButt;

    boolean onRegister = false;
    Image signIn, signUp;
    TextField user;
    Label errorText;

    MenuScreen(BANG _game, boolean registerOn){
        game = _game;
        camera = new OrthographicCamera(1080, 1920);
        camera.setToOrtho(true, 1080, 1920);
        batch = new SpriteBatch();
        stage = game.stage;

        assetManager = game.assetManager;

        if(game.dataUser == null){
            game.dataUser = new DataJson();
            dataUser = game.dataUser;
            FileHandle file1 = Gdx.files.local("levelData/data.json");
            boolean fileExist = file1.exists();
            if(fileExist){
                JsonValue oldData = new JsonReader().parse(file1);
                dataUser.setUser(oldData.get(3).asString());
                for(int i = 0; i < oldData.get(1).size; i++){
                    dataUser.addIdea(oldData.get(1).get(i).asStringArray());
                }
                dataUser.setRank(oldData.get(2).asFloat());
                for(int i = 0; i < oldData.get(0).size; i++){
                    String[][] toSave = new String[oldData.get(0).get(i).size][2];
                    for(int j = 0; j < toSave.length; j++){
                        String[] currentCardData = oldData.get(0).get(i).get(j).asStringArray();
                        toSave[j] = currentCardData;
                    }
                    dataUser.addCard(toSave);
                }
                /*for(int i = dataUser.getIdeas().size-1; i < dataUser.getIdeas().size; i++){
                    Gdx.app.log("DataE","Mazo: "+dataUser.getIdeas().get(i)[0]+" Need: "+dataUser.getIdeas().get(i)[1]+" Idea: "+dataUser.getIdeas().get(i)[2]);
                }
                for(int i = dataUser.getCards().size-1; i < dataUser.getCards().size; i++){
                    Gdx.app.log("DataE","Mazo: "+dataUser.getCards().get(i)[0]+" Carta: "+dataUser.getCards().get(i)[1]);
                }*/
                Json json = new Json();
                System.out.println(json.prettyPrint(dataUser));
            }else{
                dataUser.setUser("CUNNY");
                dataUser.setRank(0f);
            }
        }else{
            dataUser = game.dataUser;
        }

        skinMenu = new Skin();
        skinMenu.add("cebiac", assetManager.get("cebiac.png", Texture.class));
        skinMenu.add("siButt", assetManager.get("siButt.png", Texture.class));
        skinMenu.add("noButt", assetManager.get("noButt.png", Texture.class));
        skinMenu.add("acept", assetManager.get("aceptButt.png", Texture.class));
        skinMenu.add("userName", assetManager.get("userName.png", Texture.class));

        if(registerOn){
            onRegister = true;
            Label.LabelStyle labelStyle = new Label.LabelStyle();
            labelStyle.font = new BitmapFont(Gdx.files.internal("delius.fnt"));
            labelStyle.fontColor = Color.WHITE;
            errorText = new Label("", labelStyle);
            errorText.setBounds(40, 1700, 1000, 200);
            errorText.setWrap(true);
            errorText.setAlignment(Align.center);
            TextField.TextFieldStyle fieldStyle = new TextField.TextFieldStyle();
            fieldStyle.font = new BitmapFont(Gdx.files.internal("delius.fnt"));
            fieldStyle.fontColor = Color.WHITE;
            fieldStyle.background = skinMenu.getDrawable("userName");
            user = new TextField("Usuario", fieldStyle);
            user.setPosition(540, 800);
            user.setMaxLength(15);
            user.addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                    if(user.getText().equals("Usuario"))
                        user.setText("");
                    return  true;
                }
            });
            signIn = new Image(assetManager.get("signIn.png", Texture.class));
            signIn.setPosition(540, 650);
            signIn.addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                    game.checkUser(user.getText());
                    return true;
                }
            });
            signUp = new Image(assetManager.get("signUp.png", Texture.class));
            signUp.setPosition(540, 500);
            signUp.addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                    game.setScreen(new RegisterScreen(game, MenuScreen.this));
                    return true;
                }
            });
        }

        emergentBox = new EmergentBox(new BitmapFont(Gdx.files.internal("delius.fnt")));
        emergentBox.setVisible2(false);

        menuMusic = assetManager.get("sounds/happyCave.wav", Music.class);
        menuMusic.setVolume(0.5f);
        menuMusic.setLooping(true);
        if(game.getMusic())
            menuMusic.play();

        spaceShip = assetManager.get("sounds/spaceShip.wav", Sound.class);

        blackText = game.transitionText;
        blackText.clear();
        blackText.setColor(0, 0, 0 , 1);
        blackText.setVisible(true);

        musicButt = new Image(new Texture("musicOnButt.png"));
        musicButt.setPosition(57, 197);
        musicButt.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                if(game.getMusic()){
                    game.setMusic(false);
                    menuMusic.pause();
                }else{
                    game.setMusic(true);
                    menuMusic.play();
                }
                return true;
            }
        });
        soundButt = new Image(new Texture("soundOnButt.png"));
        soundButt.setPosition(57, 86);
        soundButt.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                if(game.getSound())
                    game.setSound(false);
                else
                    game.setSound(true);
                return true;
            }
        });

        bangLogo = new Image(assetManager.get("bangLogo.png", Texture.class));
        bangLogo.setPosition(229, 1227);

        FileHandle file1 = Gdx.files.local("levelData/data.json");
        final boolean fileExist = file1.exists();

        newGame = new Image(assetManager.get("newBangButt.png", Texture.class));
        newGame.setPosition(game.width/2-newGame.getWidth()/2, 2231);
        newGame.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                if(fileExist){
                    setTutorials();
                }else{
                    nextScreen("Mission");
                }
                return true;
            }
        });
        continueGame = new Image(assetManager.get("oldBangButt.png", Texture.class));
        continueGame.setPosition(game.width/2-continueGame.getWidth()/2, 1921);
        if(!fileExist){
            continueGame.setColor(Color.GRAY);
        }
        continueGame.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                if(dataUser.getIdeas().size>0)
                    nextScreen("getBangs");
                return true;
            }
        });
        characteres = new Image(assetManager.get("characteres.png", Texture.class));
        characteres.setPosition(0, 0);

        Texture t = assetManager.get("background.jpg", Texture.class);
        background = new Image(t);
        background.setPosition(0, 0);
        universeBack();
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = new BitmapFont(Gdx.files.internal("delius.fnt"));
        labelStyle.font.getData().markupEnabled = true;
        labelStyle.fontColor = Color.WHITE;
        verCreditos = new Label("Ver Créditos", labelStyle);
        verCreditos.setFontScale(1.5f);
        verCreditos.layout();
        verCreditos.setPosition(540-verCreditos.getGlyphLayout().width/2, 20);
        verCreditos.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                showCredits();
                return true;
            }
        });
        credits1 = new Label("UNA PRODUCCIÓN DE", labelStyle);
        credits1.setWrap(true);
        credits1.setAlignment(Align.center);
        credits1.setWidth(1040);
        credits2 = new Label("\n[#FFBF00]DESARROLLADO POR\n"+
                "[#FFFFFF]Jonathan Hernando Sierra Hernández\n\n"+
                "[#FFBF00]BASADO EN LA METODOLOGIA BANG CREADA POR\n"+
                "[#FFFFFF]Jeimy Lorena Rojas Cardenas\n\n"+
                "[#FFBF00]PLANEACIÓN DE DISEÑO\n"+
                "[#FFFFFF]Jeimy Lorena Rojas Cardenas\n"+
                "Jonathan Hernando Sierra Hernández\n\n"+
                "[#FFBF00]DISEÑOS Y PIEZAS GRÁFICAS\n"+
                "[#FFFFFF]Jeimmy Carolina Barreto Gomez\n"+
                "Angie Katherine Gutiérrez Barreto\n"+
                "Valeria Osorio Arboledo\n\n"+
                "CEBIAC\n"+
                "2019\n\n"+
                "[#FFBF00]MÚSICA Y SONIDOS\n\n"+
                "[#FFBF00]SFX - various briefcase latch sounds\n" +
                "[#FFFFFF]por Blouhond\n" +
                "CC BY 3.0.\n" +
                "Tomado de freesound.org\n"+
                "[#FFBF00]Card Shuffle\n" +
                "[#FFFFFF]por empraetorius\n" +
                "CC BY 3.0.\n" +
                "Tomado de freesound.org\n"+
                "[#FFBF00]Front Door » Door, Front, Closing, A.wav\n" +
                "[#FFFFFF]por InspectorJ\n" +
                "CC BY 3.0.\n" +
                "Tomado de freesound.org\n"+
                "[#FFBF00]happy cave\n" +
                "[#FFFFFF]por ADnova\n" +
                "Tomado de freesound.org\n"+
                "[#FFBF00]Happy Guitar\n" +
                "[#FFFFFF]por edtijo\n" +
                "Tomado de freesound.org\n"+
                "[#FFBF00]Music Box, Happy Birthday.wav\n" +
                "[#FFFFFF]por InspectorJ\n" +
                "CC BY 3.0.\n" +
                "Tomado de freesound.org\n"+
                "[#FFBF00]Laser sounds » Laser01rev.wav\n" +
                "[#FFFFFF]por Chipfork\n" +
                "CC BY 3.0.\n" +
                "Tomado de freesound.org\n"+
                "[#FFBF00]News Ting\n" +
                "[#FFFFFF]por robni7\n" +
                "Tomado de freesound.org\n"+
                "[#FFBF00]Media Samples » Nylon string Guitar with steel string bass.mp3\n" +
                "[#FFFFFF]por 4barrelcarb\n" +
                "Tomado de freesound.org\n"+
                "[#FFBF00]Theme music from Porcupop\n" +
                "[#FFFFFF]por Kerri Coombs, Living Midnight Design \n" +
                "CC BY SA 3.0.\n" +
                "opengameart.org\n" +
                "[#FFBF00]Guns and War Sound Effects » Rocket Fly By.wav\n" +
                "[#FFFFFF]por 18hiltc\n" +
                "CC BY 3.0.\n" +
                "Tomado de freesound.org\n"+
                "[#FFBF00]Modular SFX » ship landing 3\n" +
                "[#FFFFFF]por dggrunzweig\n" +
                "Tomado de freesound.org\n"+
                "[#FFBF00]Short Success Sound Glockenspiel Treasure Video Game.mp3\n" +
                "[#FFFFFF]por FunWithSound\n" +
                "Tomado de freesound.org\n"+
                "[#FFBF00]Sound Design » Weird Spaceship.wav\n" +
                "[#FFFFFF]por Suburbanwizard\n" +
                "Tomado de freesound.org\n"+
                "[#FFBF00]Happy Sounds by Mativve » Electro win sound\n" +
                "[#FFFFFF]por Mativve\n" +
                "CC BY 3.0.\n" +
                "Tomado de freesound.org\n"+
                "[#FFBF00]Happy Quirky Loop\n" +
                "[#FFFFFF]por Ispeakwaves\n" +
                "CC BY 3.0.\n" +
                "Tomado de freesound.org\n", labelStyle);
        credits2.setWrap(true);
        credits2.setAlignment(Align.center);
        credits2.setPosition(20, 1800);
        credits2.setWidth(1040);
        creditsT = new Table();
        creditsT.add(credits1).width(1040);
        creditsT.row();
        creditsT.add(new Image(skinMenu.getDrawable("cebiac"))).size(416, 354.73f).center().expandX();
        creditsT.row();
        creditsT.add(credits2).width(1040);
        ScrollPane.ScrollPaneStyle scrollPaneStyle = new ScrollPane.ScrollPaneStyle();
        scrollPane = new ScrollPane(creditsT, scrollPaneStyle);
        scrollPane.setSize(1040, 1680);
        scrollPane.setPosition(20, 120);
        aceptButt = new Image(skinMenu.getDrawable("acept"));
        aceptButt.setOrigin(Align.bottomLeft);
        aceptButt.setScale(0.6f, 0.75f);
        aceptButt.setPosition(800, 20);
        aceptButt.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                scrollPane.setVisible(false);
                scrollPane.remove();
                blackText.setVisible(false);
                aceptButt.setVisible(false);
                aceptButt.remove();
                scrollPane.clearActions();
                onCredits = false;
                return true;
            }
        });
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta){
        Gdx.gl20.glClearColor(0, 0, 0, 1);
        Gdx.gl20.glClear(GL30.GL_COLOR_BUFFER_BIT | GL30.GL_DEPTH_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.end();

        comet.update(delta);
        asteroid.update(delta);
        satelite.update(delta);
        alien.update(delta);

        stage.act(delta);
        stage.draw();

        if(onOut)
            menuMusic.setVolume(menuMusic.getVolume()-0.1f);

        if(onCredits)
            animCredits();

        if(game.textOnlineActive == 2){
            Gdx.app.log("Existe", game.textoOnline);
            if(game.textoOnline.equals("Existe")){
                dataUser.setUser(user.getText());
                closeRegister();
            }else{
                errorText.setText(game.textoOnline);
            }
            game.textOnlineActive = 0;
        }

        if(nextScreen){
            game.setProgress();
            if(nextScreen2){
                if(game.assetManager.update()){
                    stage.addAction(Actions.sequence(Actions.delay(0.2f), Actions.run(new Runnable(){
                        @Override
                        public void run(){
                            if(toScreen.equals("Mission")){
                                if(withTutorials)
                                    game.setScreen(new MissionScreen(game, MenuScreen.this, true));
                                else
                                    game.setScreen(new MissionScreen(game, MenuScreen.this, false));
                            }else{
                                game.setScreen(new GetBangsScreen(game, MenuScreen.this));
                            }
                        }
                    })));
                    nextScreen2 = false;
                }
            }
        }

    }

    @Override
    public void show(){
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
        stage.addActor(bangLogo);
        stage.addActor(newGame);
        stage.addActor(continueGame);
        stage.addActor(characteres);

        stage.addActor(musicButt);
        stage.addActor(soundButt);

        stage.addActor(emergentBox);
        stage.addActor(emergentBox.blackTexture);

        stage.addActor(verCreditos);

        stage.addActor(blackText);
        RunnableAction run = Actions.run(new Runnable() {
            @Override
            public void run(){
                newGame.addAction(Actions.moveTo(newGame.getX(), game.height / 2 + 10, 1));
                continueGame.addAction(Actions.moveTo(continueGame.getX(), game.height / 2 - continueGame.getHeight() / 2 - 10, 1));
                blackText.setVisible(false);
                if(onRegister)
                    showRegister();
            }
        });
        blackText.clear();
        blackText.setZIndex(1000);
        blackText.setVisible(true);
        blackText.addAction(Actions.sequence(Actions.color(Color.BLACK), Actions.alpha(1), Actions.alpha(0, 1), run));
    }

    @Override
    public void hide(){
        stage.clear();
        unloadAssets();
        musicButt.clearListeners();
    }

    @Override
    public void dispose(){
        menuMusic.dispose();
    }

    public void universeBack(){
        Texture starTexture1 = assetManager.get("star1.png", Texture.class);
        Texture starTexture2 = assetManager.get("star2.png", Texture.class);
        Texture starTexture3 = assetManager.get("star3.png", Texture.class);
        Texture starTexture4 = assetManager.get("star4.png", Texture.class);
        float[][] stars1Pos = {{432, 54, 18, 18, 0}, {311, 178, 18, 18, 0}, {783, 254, 13, 13, 45}, {914, 288, 13, 13, 45}, {610, 297, 18, 18, 0},
                {391, 397, 18, 18, 0}, {618, 413, 13, 13, 45}, {918, 460, 13, 13, 45}, {820, 586, 9, 9, 45}, {977, 613, 18, 18, 0}, {354, 618, 13, 13, 0},
                {266, 708, 13, 13, 0}, {483, 794, 13, 13, 0}, {978, 1134, 9, 9, 45}, {204, 1156, 13, 13, 45}, {133, 1614, 18, 18, 0}, {12, 1739, 18, 18, 0},
                {122, 1863, 13, 13, 45}, {689, 1786, 18, 18, 0}};
        float[][] stars2Pos = {{197, 126, 32, 32, 0}, {357, 71, 54, 53, 0}, {695, 125, 39, 39, 45}, {1017, 240, 23, 23, 45}, {729, 319, 23, 23, 0},
                {468, 374, 32, 32, 0}, {87, 426, 32, 32, 0}, {902, 634, 23, 23, 0}, {183, 671, 17, 17, 0}, {799, 676, 54, 53, 45}, {160, 884, 39, 39, 0},
                {424, 1018, 23, 23, 0}, {1048, 974, 23, 23, 45}, {728, 1242, 17, 17, 0}, {981, 1269, 32, 32, 0}, {58, 1636, 54, 53, 0}, {798, 1713, 32, 32, 0},
                {675, 1851, 54, 53, 45}};
        float[][] stars3Pos = {{34, 61, 54, 53, 0}, {962, 17, 23, 23, 45}, {917, 380, 39, 39, 45}, {340, 508, 54, 53, 0}, {65, 623, 39, 39, 0},
                {609, 757, 28, 28, 45}, {978, 1076, 28, 28, 45}, {174, 1281, 23, 23, 45}};
        float[][] stars4Pos = {{350, 35, 18, 17, 0}, {787, 87, 15, 15, 0}, {841, 414, 15, 15, 0}, {162, 410, 18, 17, 0}, {74, 559, 18, 17, 0},
                {115, 668, 11, 11, 0}, {335, 651, 11, 11, 0}, {147, 789, 11, 11, 0}, {889, 865, 11, 11, 0}, {824, 973, 11, 11, 0}, {1065, 1147, 18, 17, 0},
                {632, 1283, 15, 15, 0}, {328, 1304, 15, 15, 0}, {989, 1506, 18, 17, 0}};
        stars1 = new Array<Image>();
        stars2 = new Array<Image>();
        stars3 = new Array<Image>();
        stars4 = new Array<Image>();
        for(int i = 0; i < stars1Pos.length; i++){
            Image image = new Image(starTexture1);
            image.setBounds(stars1Pos[i][0], stars1Pos[i][1], stars1Pos[i][2], stars1Pos[i][3]);
            image.setRotation(stars1Pos[i][4]);
            stars1.add(image);
            RepeatAction act = Actions.forever(Actions.sequence(Actions.alpha(0.5f, 1), Actions.alpha(1, 1)));
            image.addAction(act);
        }
        for(int i = 0; i < stars2Pos.length; i++){
            Image image = new Image(starTexture2);
            image.setBounds(stars2Pos[i][0], stars2Pos[i][1], stars2Pos[i][2], stars2Pos[i][3]);
            image.setRotation(stars2Pos[i][4]);
            stars2.add(image);
            RepeatAction act = Actions.forever(Actions.sequence(Actions.alpha(0.5f, 1), Actions.alpha(1, 1)));
            image.addAction(act);
        }
        for(int i = 0; i < stars3Pos.length; i++){
            Image image = new Image(starTexture3);
            image.setBounds(stars3Pos[i][0], stars3Pos[i][1], stars3Pos[i][2], stars3Pos[i][3]);
            image.setRotation(stars3Pos[i][4]);
            stars3.add(image);
            RepeatAction act = Actions.forever(Actions.sequence(Actions.alpha(0.5f, 1), Actions.alpha(1, 1)));
            image.addAction(act);
        }
        for(int i = 0; i < stars4Pos.length; i++){
            Image image = new Image(starTexture4);
            image.setBounds(stars4Pos[i][0], stars4Pos[i][1], stars4Pos[i][2], stars4Pos[i][3]);
            image.setRotation(stars4Pos[i][4]);
            stars4.add(image);
            RepeatAction act = Actions.forever(Actions.sequence(Actions.alpha(0.5f, 1), Actions.alpha(1, 1)));
            image.addAction(act);
        }
        asteroid = new MovingImage(game, assetManager.get("meteorit.png", Texture.class), 0, 700, 2, 2);
        comet = new MovingImage(game, assetManager.get("comet.png", Texture.class), 0, 500, 2, 2);
        satelite = new MovingImage(game, assetManager.get("satelite.png", Texture.class), 2, 300, 0, 0);
        satelite.setOrigin(71.5f, 23);
        satelite.addAction(Actions.forever(Actions.sequence(Actions.rotateTo(45, 1.3f), Actions.rotateTo(-45, 1.3f))));
        satelite.setSize(205, 66);
        alien = new MovingImage(game, assetManager.get("alien.png", Texture.class), 1, 400, 0, 0);
        alien.setSize(136, 69);
        if(game.getSound())
            alien.setSound(spaceShip);
    }

    public void unloadAssets(){
        assetManager.unload("bangLogo.png");
        assetManager.unload("newBangButt.png");
        assetManager.unload("oldBangButt.png");
        assetManager.unload("characteres.png");
    }

    public void setTutorials(){
        emergentBox.textLabel.setText("[#511577]¿Deseas activar los dialogos de guia?");
        emergentBox.setVisible2(true);
        emergentBox.showButt2();
        emergentBox.closeButt.setVisible(false);
        emergentBox.auxButt1.setVisible(true);
        emergentBox.auxButt1.setDrawable(skinMenu.getDrawable("siButt"));
        emergentBox.auxButt1.clearListeners();
        emergentBox.auxButt1.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                emergentBox.auxButt1.setVisible(false);
                emergentBox.setVisible(false);
                emergentBox.setVisible(false);
                emergentBox.hideButt2();
                withTutorials = true;
                nextScreen("Mission");
                return true;
            }
        });
        emergentBox.auxButt2.setVisible(true);
        emergentBox.auxButt2.clearListeners();
        emergentBox.auxButt2.setDrawable(skinMenu.getDrawable("noButt"));
        emergentBox.auxButt2.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                emergentBox.auxButt1.setVisible(false);
                emergentBox.auxButt2.setVisible(false);
                emergentBox.setVisible(false);
                emergentBox.hideButt2();
                withTutorials = false;
                nextScreen("Mission");
                return true;
            }
        });
    }

    public void nextScreen(final String _toScreen){
        RunnableAction run = Actions.run(new Runnable(){
            @Override
            public void run(){
                nextScreen = true;
                game.loadAssets(_toScreen);
                game.backLoad.setZIndex(1000);
                game.backLoad.setVisible(true);
                game.barLoad.setZIndex(1001);
                game.barLoad.setVisible(true);
                game.endBarLoad.setZIndex(1002);
                game.endBarLoad.setVisible(true);
            }
        });
        toScreen = _toScreen;
        onOut = true;
        blackText.setColor(0, 0, 0, 0);
        blackText.setVisible(true);
        blackText.addAction(Actions.sequence(Actions.fadeIn(1), run));
    }

    public void showCredits(){
        blackText.setColor(0, 0,0 ,0.8f);
        blackText.setVisible(true);
        blackText.setZIndex(1000);
        scrollPane.setVisible(true);
        stage.addActor(scrollPane);
        scrollPane.setScrollY(0);
        scrollPane.layout();
        scrollPane.updateVisualScroll();
        scrollPane.cancel();
        scrollPane.clearListeners();
        scrollPane.addAction(Actions.sequence(Actions.delay(1f), Actions.run(new Runnable(){
            @Override
            public void run(){
                onCredits = true;
            }
        })));
        aceptButt.setVisible(true);
        stage.addActor(aceptButt);
    }

    public void animCredits(){
        Gdx.app.log("scroll", "p: "+scrollPane.getScrollPercentY()+" y: "+scrollPane.getScrollY());
        if(scrollPane.getScrollY()>=scrollPane.getMaxY()){
            scrollPane.addAction(Actions.sequence(Actions.delay(1f), Actions.run(new Runnable(){
                @Override
                public void run(){
                    scrollPane.setVisible(false);
                    scrollPane.remove();
                    blackText.setVisible(false);
                    aceptButt.setVisible(false);
                    aceptButt.remove();
                    scrollPane.clearActions();
                }
            })));
            onCredits = false;
        }else{
            scrollPane.setScrollY(scrollPane.getScrollY()+7);
        }
    }

    public void showRegister(){
        blackText.setColor(0, 0, 0, 0.7f);
        blackText.setZIndex(1000);
        blackText.setVisible(true);
        stage.addActor(user);
        stage.addActor(signIn);
        stage.addActor(signUp);
        stage.addActor(errorText);
    }

    public void closeRegister(){
        blackText.setVisible(false);
        user.setVisible(false);
        signIn.setVisible(false);
        signUp.setVisible(false);
        errorText.setVisible(false);
    }
}
