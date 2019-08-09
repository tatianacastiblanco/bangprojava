package com.mygdx.bang;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;

import java.util.Random;

public class MissionScreen extends  LevelScreen{
    Music backMusic, backMusic2;
    Sound phaseS, spaceShip, randS, cardShuffle, messageS, shipS, shipLandS, winS;

    String[] auxTexts = {"Necesitas desplazarte a tu lugar de trabajo para cumplir con tus responsabilidades. Por eso el transporte debe estar en optimas condiciones para hacerlo. ¿Te gustaría ayudar a los habitantes de este planeta a solucionar sus problemas respecto a este tema?\n¡Selecciónalo!",
            "Como necesitas un techo para dormir, también lo necesitas para trabajar. Los habitantes de este planeta no se sienten satisfechos con su lugar de trabajo. ¿Te gustaría ayudarlos a solucionar sus problemas con la infraestructura?\n¡Selecciónalo!",
            "Para desempeñar tu labor en el trabajo, necesitas usar diferentes equipos, desde computadores hasta herramientas básicas como papel y lápiz. En este planeta existen problemas respecto a sus equipos. ¿Te gustaría ayudar a sus habitantes?\n¡Selecciónalo!",
            "En tu lugar de trabajo es indispensable que tengas las normas claras, para evitar confusiones, en tus funciones, etc.\nEsta es una necesidad insatisfecha en este planeta. ¿Tomarás el reto de ayudar a sus habitantes con temas de lineamientos corporativos?\n¡Selecciónalo!",
            "Necesitas sentirte seguro en una compañía, y el salario indudablemente te brinda estabilidad. En este planeta las cosas no andan muy bien con este tema, pues sus habitantes se han quejado demasiado. ¿Tomarás el reto de ayudarlos con los temas de salario?\n¡Selecciónalo!",
            "Si no sientes que tus emociones estén alineados con la compañía, probablemente es porque no te sientes feliz de estar trabajando donde lo haces. Esto es muy común normal en este planeta, la gente no está realmente motivada. ¿Tomarás el reto de ayudarlos con temas de clima organizacional?\n¡Selecciónalo!",
            "Necesitas entender la forma en que se conectan las áreas dentro de la empresa, para dirigirte siempre al lugar indicado.\nDesafortunadamente en este planeta los procesos no son claros y esta es la razón de que pidan tu ayuda, ¿los ayudarás?\n¡Selecciónalo!",
            "Cuando haces parte de una organización, lo identificas fácilmente; porque ya tienes las mismas actitudes creencias y valores de todos. Resulta que en este planeta nadie siente que pertenezca a nada, por eso requieren de tu ayuda, ¿los ayudarás?\n¡Selecciónalo!",
            "El ser humano necesita comunicarse por naturaleza, pero también debe hacerlo asertivamente para evitar conflictos o malos entendidos. En este planeta ya han habido bastantes problemas por esta razón. Por eso requieren de tu ayuda, ¿los ayudarás?\n¡Selecciónalo!",
            "Cuando sabes que tienes posibilidad de crecimiento profesional dentro de una compañía, te auto motivas, porque de conseguir un ascenso tu autoestima estará en aumento. Esto no parece saberlo este planeta; por eso buscan agentes que los ayuden. ¿Te animas?\n¡Selecciónalo!",
            "Necesitas sentirte bien en tu jornada laboral. Te gusta tener actividades de esparcimiento y sentirte querido por la compañía. En este planeta, no tienen claro que un colaborador feliz y saludable, es a la vez clientes felices. Razón por la cual buscan agentes que les ayuden. ¿Te animas?\n¡Selecciónalo!",
            "Si sientes que valoran tu trabajo y te agradecen por ello; te sentirás motivado y tu rendimiento irá en aumento. Lastimosamente en este planeta, no entienden lo importante que es la gratitud. ¿Te animas a ayudarlos con su problema?\n¡Selecciónalo!",
            "La empresa en la que trabajas debe alinearse con tus objetivos personales. ¿qué sería de la vida de las personas sin sus planes de viajes, de estudios complementarios o de independencia? Este planeta poco ha pensado en esto, por eso contacta a agentes para que lo ayuden, ¿te apuntas?\n¡Selecciónalo!",
            "Siempre y cuando sientas que tu familia le importa a la compañía en la que estas, te vas a sentir importante, pues tienen en cuenta una gran parte de tu vida. Este planeta necesita demostrar más interés por la familia de sus habitantes, por eso contacta agentes para que lo ayuden, ¿te animas?\n¡Selecciónalo!",
            "La mayoría de las veces para sentirte motivado en la empresa, debes sentir que todos los días aprendes algo nuevo. Es por esta razón que este planeta contacto a agentes para que los ayuden, ¿te animas?\n¡Selecciónalo!"
    };

    //Fases
    float timer = 2;
    boolean changingPhase = false;
    boolean beginPhase = true;
    float maxRotation = 120;
    Image backPhases, solido, gas, liquido;
    int currentFase = 0;

    //Planetas
    int spinning = 0;
    Image current;
    float timeRoulette = 0.2f;
    float currentTimeRoulette2 = -1;
    float timeRouletteCont = 0.2f;
    float speedRoulette2 = 40;
    int indexRoulette = 0;
    Image[] planets;
    Image planet1;
    Image planet2;
    Image planet3;
    Image planet4;
    Image planet5;
    Image sun;
    Image galaxy;
    Ship ship;
    boolean activeShip = true;
    Image destello;
    Image randButt;

    //Necesidades
    Image[] rectangles;
    Image caso1, caso2, caso3;

    Skin cardsSkin;
    boolean comodin = false;
    int comodinCard = 0;
    boolean getRandom = false;
    boolean showMaze = true;
    int endAnim = 0;
    int currentCard;
    int numCards = 0;
    TextureRegionDrawable randCardDrawable;
    Image[] randCards;
    Array<Integer> usedCards[];
    private final Image[] mazes;
    //CardItem card1, card2, card3;
    CardItem currentCardItem;

    //Ideas
    IdeasScroll scroll;
    Image addIdeaButt, ideaTitle;

    boolean nextScreen = false, nextScreen2 = true;
    String nextScreenAdap;

    String helpsActive = "";

    MissionScreen(BANG _game, MenuScreen menu, boolean tutorials){
        game = _game;
        camera = game.camera;
        camera.position.set(960, 540, 0);
        viewport = game.viewport;
        batch = game.batch;
        stage = game.stage;

        assetManager = game.assetManager;

        showTutorials = tutorials;
        
        musicButt = menu.musicButt;
        soundButt = menu.soundButt;

        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(0, 0, 0, 0.7f);
        pixmap.fill();

        //fuentes
        dialogFont = assetManager.get("delius.fnt", BitmapFont.class);
        //dialogFont = new BitmapFont(Gdx.files.internal("delius.fnt"));
        dialogFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        //dialogFont.getData().padTop = 200;
        titleDialogFont = new BitmapFont(Gdx.files.internal("shadows.fnt"));
        titleDialogFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        levelFont = new BitmapFont(Gdx.files.internal("shadows2.fnt"));
        levelFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = dialogFont;
        labelStyle.fontColor = Color.WHITE;

        //rango
        rankBar1 = new Image(new Texture("levelBar1.png"));
        rankBar1.setPosition(30, 1749);
        rankBar2 = new Image(new Texture("levelBar2.png"));
        rankBar2.setPosition(110, 1811);
        rankBar3 = new Image(new Texture("levelBar3.png"));
        rankBar2.setWidth(0);
        rankBar3.setPosition(110, 1811);
        rankNumber = new Label("0", labelStyle);
        rankNumber.setAlignment(Align.center);
        rankNumber.setWrap(true);
        rankNumber.setBounds(50, 1800, 68, 68);

        dataUser = game.dataUser;

        blackTexture = game.transitionText;
        blackTexture.clear();
        blackTexture.setColor(0, 0, 0 , 1);
        blackTexture.setVisible(true);

        blackTextureCards = new Image(new Texture(pixmap));
        blackTextureCards.setBounds(0, 0, 1080, 1920);
        blackTextureCards.setColor(0, 0, 0, 1);
        blackTextureCards.setVisible(false);

        backMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/happyGuitar.mp3"));
        backMusic.setVolume(0.5f);
        backMusic.setLooping(true);
        if(game.getMusic())
            backMusic.play();

        backMusic2 = Gdx.audio.newMusic(Gdx.files.internal("sounds/happyQuirky.mp3"));
        backMusic2.setVolume(0.9f);
        backMusic2.setLooping(true);

        phaseS = Gdx.audio.newSound(Gdx.files.internal("sounds/laserRev.wav"));
        randS = Gdx.audio.newSound(Gdx.files.internal("sounds/happyRand.wav"));
        cardShuffle = Gdx.audio.newSound(Gdx.files.internal("sounds/cardShuffle.wav"));
        messageS = Gdx.audio.newSound(Gdx.files.internal("sounds/newsTing.wav"));
        closeBag = Gdx.audio.newSound(Gdx.files.internal("sounds/briefcase.wav"));
        shipS = Gdx.audio.newSound(Gdx.files.internal("sounds/rocketFly.wav"));
        shipLandS = Gdx.audio.newSound(Gdx.files.internal("sounds/shipLanding.wav"));
        winS = Gdx.audio.newSound(Gdx.files.internal("sounds/winSound.wav"));

        //fondo
        background = menu.background;
        stars1 = menu.stars1;
        stars2 = menu.stars2;
        stars3 = menu.stars3;
        stars4 = menu.stars4;
        comet = menu.comet;
        asteroid = menu.asteroid;
        satelite = menu.satelite;
        alien = menu.alien;

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
        skinCharacters.add("n1", assetManager.get("n1.png", Texture.class));
        skinCharacters.add("n2", assetManager.get("n2.png", Texture.class));
        skinCharacters.add("n3", assetManager.get("n3.png", Texture.class));
        skinCharacters.add("n4", assetManager.get("n4.png", Texture.class));
        skinCharacters.add("n5", assetManager.get("n5.png", Texture.class));
        skinCharacters.add("n6", assetManager.get("n6.png", Texture.class));
        skinCharacters.add("n7", assetManager.get("n7.png", Texture.class));
        skinCharacters.add("n8", assetManager.get("n8.png", Texture.class));
        skinCharacters.add("n9", assetManager.get("n9.png", Texture.class));
        skinCharacters.add("n10", assetManager.get("n10.png", Texture.class));
        skinCharacters.add("n11", assetManager.get("n11.png", Texture.class));
        skinCharacters.add("n12", assetManager.get("n12.png", Texture.class));
        skinCharacters.add("n13", assetManager.get("n13.png", Texture.class));
        skinCharacters.add("n14", assetManager.get("n14.png", Texture.class));
        skinCharacters.add("n15", assetManager.get("n15.png", Texture.class));
        skinCharacters.add("maze1", assetManager.get("cards/maze1.png", Texture.class));
        skinCharacters.add("maze2", assetManager.get("cards/maze2.png", Texture.class));
        skinCharacters.add("maze3", assetManager.get("cards/maze3.png", Texture.class));
        skinCharacters.add("maze4", assetManager.get("cards/maze4.png", Texture.class));
        skinCharacters.add("maze5", assetManager.get("cards/maze5.png", Texture.class));
        skinCharacters.add("rand1", assetManager.get("cards/rand1.png", Texture.class));
        skinCharacters.add("rand2", assetManager.get("cards/rand2.png", Texture.class));
        skinCharacters.add("rand3", assetManager.get("cards/rand3.png", Texture.class));
        skinCharacters.add("rand4", assetManager.get("cards/rand4.png", Texture.class));
        skinCharacters.add("rand5", assetManager.get("cards/rand5.png", Texture.class));
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

        menuCard = new MenuCards(this);

        String[][] scenesData;
        String[][] scenesCloseData;
        if(showTutorials){
             scenesData = scenes2;
             scenesCloseData = toClose;
        }else{
            scenesData = scenes3;
            scenesCloseData = toClose2;
        }

        //Escenas
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

        //dialogos
        character = new Image(skinCharacters.getDrawable("Captain"));
        character.setBounds(78, 1404, 310, 298);
        character.setVisible(false);
        dialogBox = new Image(skinCharacters.getDrawable("dialogCaptain"));
        dialogBox.setPosition(76, 60);
        dialogBox.setVisible(false);
        nextButt = new Image(assetManager.get("nextButt.png", Texture.class));
        nextButt.setPosition(800, 26);
        nextButt.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                /*if(currentScene == 32){
                    //game.addIdeas();
                    setData();
                }*/
                //game.addIdeasOnline("", new String[][]{});
                nextCurrentScene();
                return true;
            }
        });
        nextButt.setVisible(false);
        backButt = new Image(assetManager.get("nextButt.png", Texture.class));
        backButt.setOrigin(155, 133.5f);
        backButt.setRotation(180);
        backButt.setPosition(20, 26);
        backButt.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                backScene();
                return true;
            }
        });
        backButt.setVisible(false);

        Label.LabelStyle labelStyle3 = new Label.LabelStyle();
        labelStyle3.font = new BitmapFont(Gdx.files.internal("delius.fnt"));
        labelStyle3.fontColor = Color.WHITE;
        dialog = new Label("", labelStyle3);
        dialog.setAlignment(Align.top, Align.center);
        dialog.setWrap(true);
        dialog.setWidth(842);

        dialogT = new Table();
        dialogT.add(dialog).width(842).padTop(50).top().grow();
        dialogT.setBounds(135, 547, 842, 842);
        dialogT.setVisible(false);

        Label.LabelStyle labelStyle2 = new Label.LabelStyle();
        labelStyle2.font = titleDialogFont;
        labelStyle2.fontColor = Color.WHITE;
        title = new Label("", labelStyle2);
        title.setAlignment(Align.center);
        title.setWrap(true);
        title.setBounds(320, 1540, 620, 100);
        title.setVisible(false);

        //fases
        backPhases = new Image(new Texture("lineas3.png"));
        //backPhases.setPosition(55, 475);
        backPhases.setPosition(146, 566);
        backPhases.setOrigin(394, 394);
        //backPhases.setOrigin(485, 485);
        backPhases.setVisible(false);
        solido = new Image(new Texture("solido.png"));
        //solido.setOrigin(596, 570);
        solido.setOrigin(155, 148.5f);
        solido.setColor(Color.GRAY);
        float newX = (390*MathUtils.cosDeg(330))+540-155;
        float newY = (390*MathUtils.sinDeg(330))+960-148.5f;
        solido.setPosition(newX, newY);
        solido.setVisible(false);
        gas = new Image(new Texture("gas.png"));
        //gas.setOrigin(421, 418);
        gas.setOrigin(173.5f, 133);
        gas.setColor(Color.GRAY);
        float newX2 = (390*MathUtils.cosDeg(210))+540-173.5f;
        float newY2 = (390*MathUtils.sinDeg(210))+960-133;
        gas.setPosition(newX2, newY2);
        gas.setVisible(false);
        liquido = new Image(new Texture("liquido.png"));
        //liquido.setOrigin(472, 480);
        liquido.setOrigin(197, 153.5f);
        liquido.setColor(Color.GRAY);
        float newX3 = (390*MathUtils.cosDeg(90))+540-197;
        float newY3 = (390*MathUtils.sinDeg(90))+960-153.5f;
        liquido.setPosition(newX3, newY3);
        liquido.setVisible(false);

        //planetas
        planet1 = new Image(assetManager.get("planet1.png", Texture.class));
        planet1.setPosition(253, 1483);
        planet1.setOrigin(119, 128.5f);
        //planet1.addAction(Actions.forever(Actions.sequence(Actions.rotateTo(45, 1), Actions.rotateTo(-45, 1))));
        planet1.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                /*currentMaze = 0;
                current = planet1;
                nextCurrentScene();
                randButt.clearListeners();*/
                beginPlanet(0);
                return true;
            }
        });
        planet1.setVisible(false);
        planet2 = new Image(assetManager.get("planet2.png", Texture.class));
        planet2.setPosition(455, 1160);
        planet2.setOrigin(201, 105.5f);
        //planet2.addAction(Actions.forever(Actions.sequence(Actions.rotateTo(45, 0.7f), Actions.rotateTo(-45, 0.7f))));
        planet2.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                /*currentMaze = 1;
                current = planet2;
                //animRocket("planet");
                nextCurrentScene();*/
                beginPlanet(1);
                return true;
            }
        });
        planet2.setVisible(false);
        planet3 = new Image(assetManager.get("planet3.png", Texture.class));
        planet3.setPosition(560, 770);
        planet3.setOrigin(157.5f, 126.5f);
        //planet3.addAction(Actions.forever(Actions.sequence(Actions.rotateTo(45, 0.9f), Actions.rotateTo(-45, 0.9f))));
        planet3.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                /*currentMaze = 2;
                current = planet3;
                //animRocket("planet");
                nextCurrentScene();*/
                beginPlanet(2);
                return true;
            }
        });
        planet3.setVisible(false);
        planet4 = new Image(assetManager.get("planet4.png", Texture.class));
        planet4.setPosition(537, 363);
        planet4.setOrigin(119, 116);
        //planet4.addAction(Actions.forever(Actions.sequence(Actions.rotateTo(45, 0.8f), Actions.rotateTo(-45, 0.8f))));
        planet4.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                /*currentMaze = 3;
                current = planet4;
                //animRocket("planet");
                nextCurrentScene();*/
                beginPlanet(3);
                return true;
            }
        });
        planet4.setVisible(false);
        planet5 = new Image(assetManager.get("planet5.png", Texture.class));
        planet5.setPosition(217, 81);
        planet5.setOrigin(147.5f, 98.5f);
        //planet5.addAction(Actions.forever(Actions.sequence(Actions.rotateTo(45, 0.95f), Actions.rotateTo(-45, 0.95f))));
        planet5.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                /*currentMaze = 4;
                current = planet5;
                //animRocket("planet");
                nextCurrentScene();*/
                beginPlanet(4);
                return true;
            }
        });
        planet5.setVisible(false);
        planets = new Image[]{planet1, planet2, planet3, planet4, planet5};
        sun = new Image(assetManager.get("sun.png", Texture.class));
        sun.setPosition(0, 960-458.5f);
        sun.setVisible(false);

        galaxy = new Image(assetManager.get("galaxy.png", Texture.class));
        galaxy.setPosition(134, 1074);
        galaxy.setVisible(false);

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
        //ship.setBounds(800, 20, 250, 100);
        ship.setOrigin(122, 279.5f);
        //ship.setBounds(-119, -273.5f, 166.6f, 382.9f);
        //ship.setPosition(-119, -273.5f);
        ship.setPosition(-244, -559);
        ship.setVisible(false);
        destello = new Image(new Texture("destello.png"));
        destello.setBounds(570, 1350, 300, 300);
        destello.setOrigin(150, 150);
        //destello.setPosition(738, 1726);
        destello.setVisible(false);

        randButt = new Image(assetManager.get("randButt.png", Texture.class));
        randButt.setBounds(901, 26, 143, 136);
        randButt.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                current = planet1;
                spinning = 1;
                randButt.clearListeners();
                for(int i = 0; i < 5; i++)
                    planets[i].clearListeners();
                return true;
            }
        });
        randButt.setVisible(false);

        pixmap.setColor(0, 0, 0, 0);
        pixmap.fill();
        Texture rectText = new Texture(pixmap);
        rectangles = new Image[3];
        float rectPos = 1213;
        for(int i = 0; i < 3; i++){
            rectangles[i] = new Image(rectText);
            rectangles[i].setBounds(0, rectPos, 100, 480);
            rectangles[i].setVisible(false);
            rectPos -= 570;
        }

        caso1 = new Image(skinCharacters.getDrawable("n1"));
        caso1.setBounds(0, 1213, 1080, 480);
        caso1.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                /*menuCard.setProblemaText("[#511577]Recuerda que estas ayudando a "+charactersNames[currentMaze]+" en el tema de [#ff0000]"+auxTexts2[((currentMaze+1)*3)-3]);
                nextCurrentScene();*/
                animNecesidad("n1");
                return true;
            }
        });
        caso1.setVisible(false);
        caso2 = new Image(skinCharacters.getDrawable("n2"));
        caso2.setBounds(0, 643, 1080, 480);
        caso2.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                animNecesidad("n2");
                return true;
            }
        });
        caso2.setVisible(false);
        caso3 = new Image(skinCharacters.getDrawable("n3"));
        caso3.setBounds(0, 73, 1080, 480);
        caso3.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                animNecesidad("n3");
                return true;
            }
        });
        caso3.setVisible(false);

        //Cartas
        mazes = new Image[5];
        float buttX = 356;
        float buttY = 160;
        //final Texture[] backMazes = {new Texture("back1.png"), new Texture("back2.png"), new Texture("back3.png"), new Texture("back4.png"), new Texture("back5.png")};
        for(int i = 0; i < 5; i++){
            mazes[i] = new Image(skinCharacters.getDrawable("maze"+(i+1)));
            //mazes[i].setBounds(buttX, buttY, 450, 600);
            mazes[i].setPosition(buttX, buttY);
            final int finalI = i;
            mazes[i].addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                    if(showMaze){
                        getRandom = true;
                        showMaze = false;
                        endAnim = 0;
                        showRandCards(finalI);
                    }
                    return true;
                }
            });
            mazes[i].setVisible(false);
            buttX += 395;
            if(i == 0 || i == 2){
                buttY += 544;
                buttX = 161;
            }
        }

        /*cardsSkin = new Skin();
        for(int i = 0; i < tarjetas.length; i++){
            for(int j = 0; j < tarjetas[i].length; j++){
                cardsSkin.add(tarjetas[i][j], new Texture(tarjetas[i][j]+".png"));
            }
        }*/
        //cardsSkin.add("randBack", new Texture("card2.png"));

        randCards = new Image[10];
        float randCardsX = 860;
        float randCardsY = -250;
        for(int i = 0; i < 10; i++){
            randCards[i] = new Image(skinCharacters.getDrawable("rand1"));
            //randCards[i].setBounds(randCardsX, randCardsY, 200, 250);
            randCards[i].setPosition(randCardsX, randCardsY);
            final int finalI = i;
            randCards[i].addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                    if(getRandom){
                        showRandCard(finalI);
                        getRandom = false;
                    }
                    return true;
                }
            });
            randCards[i].setVisible(false);
        }
        usedCards = new Array[]{new Array<Integer>(), new Array<Integer>(), new Array<Integer>(), new Array<Integer>(), new Array<Integer>()};

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
        currentCardItem = card1;
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

        //help
        pixmap.setColor(Color.WHITE);
        pixmap.fill();

        //emergente
        helps = new Helps[]{new Helps("Selection1", "A medida que completes un BANG, vas a ir adquiriendo experiencia. Esta te servirá para que logres certificarte como agente de innovación.", new float[]{570, 1785, 84, 1445, 776, 315}),
                new Helps("Selection1", "Este botón permitirá que el capitán pueda elegir por ti qué planeta debes ayudar en esta misión.", new float[]{775, 50, 292, 161, 664, 314}),
                new Helps("Selection2", "paospdfopaskdfopasdofijaisdjfoasijsdfa", new float[]{3, 1600, 107, 1213, 972, 478}),
                new Helps("Selection2", "paospdfopaskdfopasdofijaisdjfoasijsdfa", new float[]{3, 1036, 107, 640, 972, 478}),
                new Helps("Selection2", "paospdfopaskdfopasdofijaisdjfoasijsdfa", new float[]{3, 460, 107, 73, 972, 478}),
                new Helps("Ideas", "Oprime el botón + para abrir un cuadro de diálogo y escribir tus ideas.\nÚsalo cuantas veces quieras.", new float[]{765, 50, 30, 200, 768, 300})

        };

        Texture help = assetManager.get("question.png", Texture.class);
        Texture backHelp = new Texture(pixmap);
        helpsImages = new HelpImage[]{new HelpImage(help, backHelp, labelStyle), new HelpImage(help, backHelp, labelStyle), new HelpImage(help, backHelp, labelStyle)};
        /*BitmapFont fontEmergent = new BitmapFont(Gdx.files.internal("delius.fnt"));
        fontEmergent.setColor(Color.WHITE);*/
        emergentBox = menu.emergentBox;
        emergentBox.setVisible2(false);

        //Ideas
        scroll = new IdeasScroll(this, labelStyle);
        scroll.setPosition(71, 282);
        scroll.setVisible(false);

        ideaTitle = new Image(assetManager.get("ideasTitle.png", Texture.class));
        ideaTitle.setPosition(319, 1603);
        ideaTitle.setVisible(false);
        addIdeaButt = new Image(assetManager.get("addIdeaButt.png", Texture.class));
        randButt.setBounds(890, 26, 143, 136);
        addIdeaButt.setPosition(871, 26);
        addIdeaButt.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                scroll.showTextArea(stage);
                return true;
            }
        });
        addIdeaButt.setVisible(false);
    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0.094f, 0.078f, 0.266f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        comet.update(delta);
        asteroid.update(delta);
        satelite.update(delta);
        alien.update(delta);

        if(changingPhase)
            changePhase();

        if(activeShip)
            ship.update(delta);

        if(spinning > 0){
            selectRand(spinning);
        }

        if(nextScreen){
            game.setProgress();
            if(nextScreen2){
                if(assetManager.update()){
                    if(assetManager.getProgress() >= 1){
                        stage.addAction(Actions.sequence(Actions.delay(0.2f), Actions.run(new Runnable(){
                            @Override
                            public void run(){
                                if(nextScreenAdap.equals("Menu")){
                                    game.setScreen(new MenuScreen(game, false));
                                }else if(nextScreenAdap.equals("Tutorial")){
                                    game.setScreen(new TutorialScreen(game, MissionScreen.this));
                                }else{
                                    game.setScreen(new StoryboardScreen(MissionScreen.this));
                                }
                            }
                        })));
                        nextScreen2 = false;
                    }
                }
            }
        }

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void show(){
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
        rankNumber.setText(exp);
        float newSize = (currentExp*376)/1500;
        rankBar2.setWidth(newSize);
        rankBar3.setX(rankBar2.getX()+rankBar2.getWidth());
        dataUser.setRank(experiencia);
        game.backLoad.setVisible(false);
        stage.addActor(game.backLoad);
        game.barLoad.setVisible(false);
        stage.addActor(game.barLoad);
        game.endBarLoad.setVisible(false);
        stage.addActor(game.endBarLoad);
        //fondo
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

        //menu
        stage.addActor(menuCard);
        stage.addActor(menuCard.blackTexture);
        if(showTutorials)
            stage.addActor(menuCard.arrow);

        //fases
        stage.addActor(backPhases);
        stage.addActor(solido);
        stage.addActor(gas);
        stage.addActor(liquido);

        //dialogo
        stage.addActor(character);
        stage.addActor(dialogBox);
        stage.addActor(dialogT);
        stage.addActor(title);
        stage.addActor(nextButt);
        stage.addActor(backButt);

        //ayudas
        for(int i = 0; i < helpsImages.length; i++){
            helpsImages[i].showHelp(false);
            helpsImages[i].add(stage);
        }
        /*stage.addActor(helpBox);
        stage.addActor(helpLbl);*/

        //planetas
        stage.addActor(planet1);
        stage.addActor(planet2);
        stage.addActor(planet3);
        stage.addActor(planet4);
        stage.addActor(planet5);
        stage.addActor(sun);
        stage.addActor(galaxy);
        stage.addActor(ship);
        stage.addActor(destello);

        //necesidades
        stage.addActor(caso1);
        stage.addActor(caso2);
        stage.addActor(caso3);
        for(int i = 0; i < 3; i++){
            stage.addActor(rectangles[i]);
        }
        stage.addActor(randButt);

        //emergente
        stage.addActor(emergentBox.blackTexture);
        stage.addActor(emergentBox);

        //ideas
        stage.addActor(scroll);
        stage.addActor(scroll.textArea.blackTexture);
        stage.addActor(scroll.textArea);
        stage.addActor(ideaTitle);
        stage.addActor(addIdeaButt);

        stage.addActor(musicButt);
        musicButt.setVisible(false);
        musicButt.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                if(game.getMusic()){
                    game.setMusic(false);
                    backMusic.pause();
                    backMusic2.pause();
                }else{
                    game.setMusic(true);
                    backMusic.play();
                    backMusic2.play();
                }
                return true;
            }
        });
        stage.addActor(soundButt);
        soundButt.setVisible(false);

        stage.addActor(blackTextureCards);

        RunnableAction run = Actions.run(new Runnable(){
            @Override
            public void run(){
                blackTexture.setVisible(false);
                nextCurrentScene();
            }
        });
        stage.addActor(blackTexture);
        blackTexture.clear();
        blackTexture.setZIndex(1000);
        blackTexture.setVisible(true);
        blackTexture.addAction(Actions.sequence(Actions.color(Color.BLACK), Actions.alpha(1), Actions.alpha(0, 1), run));
    }

    @Override
    public void hide(){
        unloadAssets();
        stage.clear();
    }

    @Override
    public void dispose(){

    }

    @Override
    public void nextCurrentScene() {
        super.nextCurrentScene();
        boolean next = false;
        if(scenes[currentScene].close == 1){
            for(int i = 1; i < scenes[currentScene].toClose.length; i++){
                closeScene(scenes[currentScene].toClose[i]);
            }
        }
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
                        title.setText(scenes[currentScene].actor);                    dialog.setText(newText);
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
        }else if(scenes[currentScene].type.equals("Phase")){
            changingPhase = true;
            backPhases.setVisible(true);
            solido.setVisible(true);
            gas.setVisible(true);
            liquido.setVisible(true);
        }else if(scenes[currentScene].type.equals("Rocket")){
            animRocket("begin");
            galaxy.setVisible(true);
        }else if(scenes[currentScene].type.equals("Selection1")){
            planet1.setVisible(true);
            planet2.setVisible(true);
            planet3.setVisible(true);
            planet4.setVisible(true);
            planet5.setVisible(true);
            sun.setVisible(true);
            //ship.setVisible(true);
            randButt.setVisible(true);
            stage.addActor(rankBar1);
            stage.addActor(rankBar2);
            stage.addActor(rankBar3);
            stage.addActor(rankNumber);
            getHelps("Selection1");
            /*blackTexture.setZIndex(1000);
            blackTexture.setVisible(true);*/
            helpsActive = "Selection1";
        }else if(scenes[currentScene].type.equals("RocketPlanet")){
            animRocket("planet");
        }else if(scenes[currentScene].type.equals("Selection2")){
            if(currentMaze == 0){
                caso1.setDrawable(skinCharacters.getDrawable("n1"));
                caso2.setDrawable(skinCharacters.getDrawable("n2"));
                caso3.setDrawable(skinCharacters.getDrawable("n3"));
            }else if(currentMaze == 1){
                caso1.setDrawable(skinCharacters.getDrawable("n4"));
                caso2.setDrawable(skinCharacters.getDrawable("n5"));
                caso3.setDrawable(skinCharacters.getDrawable("n6"));
            }else if(currentMaze == 2){
                caso1.setDrawable(skinCharacters.getDrawable("n7"));
                caso2.setDrawable(skinCharacters.getDrawable("n8"));
                caso3.setDrawable(skinCharacters.getDrawable("n9"));
            }else if(currentMaze == 3){
                caso1.setDrawable(skinCharacters.getDrawable("n10"));
                caso2.setDrawable(skinCharacters.getDrawable("n11"));
                caso3.setDrawable(skinCharacters.getDrawable("n12"));
            }else if(currentMaze == 4){
                caso1.setDrawable(skinCharacters.getDrawable("n13"));
                caso2.setDrawable(skinCharacters.getDrawable("n14"));
                caso3.setDrawable(skinCharacters.getDrawable("n15"));
            }
            for(int i = 0; i < 3; i++){
                rectangles[i].setVisible(true);
            }
            caso1.setVisible(true);
            caso2.setVisible(true);
            caso3.setVisible(true);
            getHelps("Selection2");
            helpsImages[0].setText(auxTexts[((currentMaze+1)*3)-3]);
            helpsImages[1].setText(auxTexts[((currentMaze+1)*3)-2]);
            helpsImages[2].setText(auxTexts[((currentMaze+1)*3)-1]);
            blackTexture.setZIndex(1000);
            blackTexture.setVisible(true);
        }else if(scenes[currentScene].type.equals("Mazes")){
            backMusic.stop();
            if(game.getMusic())
                backMusic2.play();
            for(int i = 0; i < 5; i++){
                if(currentMaze != i){
                    mazes[i].setColor(Color.GRAY);
                    mazes[i].clearListeners();
                }
                mazes[i].setVisible(true);
                stage.addActor(mazes[i]);
            }
            blackTexture.setZIndex(1000);
            blackTexture.setVisible(true);
        }else if(scenes[currentScene].type.equals("Ideas")){
            scroll.setVisible(true);
            stage.addActor(scroll);
            ideaTitle.setVisible(true);
            addIdeaButt.setVisible(true);
            stage.addActor(addIdeaButt);
            getHelps("Ideas");
            helpsActive = "Ideas";
        }else if(scenes[currentScene].type.equals("ShowHelp")){
            blackTexture.setColor(0, 0, 0, 0.7f);
            blackTexture.setZIndex(1000);
            blackTexture.setVisible(true);
            if(helpsActive.equals("Selection1")){
                helpsImages[0].help.setZIndex(1001);
                rankBar1.setZIndex(1001);
                rankBar2.setZIndex(1001);
                rankBar3.setZIndex(1001);
                rankNumber.setZIndex(1001);
                final InputListener active2 = new InputListener(){
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                        if(!helpsImages[1].show){
                            blackTexture.setVisible(false);
                            helpsImages[1].resetListener();
                            randButt.addListener(new InputListener(){
                                @Override
                                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                                    current = planet1;
                                    spinning = 1;
                                    randButt.clearListeners();
                                    for(int i = 0; i < 5; i++)
                                        planets[i].clearListeners();
                                    return true;
                                }
                            });
                        }
                        return true;
                    }
                };
                final InputListener active = new InputListener(){
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                        if(!helpsImages[0].show){
                            blackTexture.setZIndex(1000);
                            helpsImages[1].help.setZIndex(1001);
                            helpsImages[1].help.addListener(active2);
                            randButt.setZIndex(10001);
                            randButt.clearListeners();
                            helpsImages[0].resetListener();
                        }
                        return true;
                    }
                };
                helpsImages[0].help.addListener(active);
            }else if(helpsActive.equals("Ideas")){
                helpsImages[0].help.setZIndex(1001);
                addIdeaButt.setZIndex(1001);
                addIdeaButt.clearListeners();
                final InputListener active = new InputListener(){
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                        if(!helpsImages[0].show){
                            blackTexture.setVisible(false);
                            helpsImages[0].resetListener();
                            addIdeaButt.addListener(new InputListener(){
                                @Override
                                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                                    scroll.showTextArea(stage);
                                    return true;
                                }
                            });
                        }
                        return true;
                    }
                };
                helpsImages[0].help.addListener(active);
            }
        }else if(scenes[currentScene].type.equals("EmergentStory")){
            showEmergent("[#511577]¿Deseas ver como el equipo de los 5C le da vida a su idea a través de un Storyboard Digital?");
            emergentBox.showButt2();
            emergentBox.closeButt.setVisible(false);
            emergentBox.auxButt1.setVisible(true);
            emergentBox.auxButt1.setDrawable(skinCharacters.getDrawable("siButt"));
            emergentBox.auxButt1.clearListeners();
            emergentBox.auxButt1.addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                    emergentBox.auxButt1.setVisible(false);
                    emergentBox.auxButt2.setVisible(false);
                    emergentBox.setVisible(false);
                    emergentBox.hideButt2();
                    //nextCurrentScene();
                    showLoad("Tutorial");
                    return true;
                }
            });
            emergentBox.auxButt2.setVisible(true);
            emergentBox.auxButt2.clearListeners();
            emergentBox.auxButt2.setDrawable(skinCharacters.getDrawable("noButt"));
            emergentBox.auxButt2.addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                    emergentBox.auxButt1.setVisible(false);
                    emergentBox.auxButt2.setVisible(false);
                    emergentBox.setVisible(false);
                    emergentBox.hideButt2();
                    nextCurrentScene();
                    return true;
                }
            });
        }else if(scenes[currentScene].type.equals("Storyboard")){
            backMusic2.stop();
            nextScreen = true;
            assetManager.load("nC"+currentNeed+".png", Texture.class);
            //objectsByNeed
            assetManager.load("storyboard/tools/n"+currentNeed+"/obj1.png", Texture.class);
            assetManager.load("storyboard/tools/n"+currentNeed+"/obj2.png", Texture.class);
            assetManager.load("storyboard/tools/n"+currentNeed+"/obj3.png", Texture.class);
            assetManager.load("storyboard/tools/n"+currentNeed+"/obj4.png", Texture.class);
            assetManager.load("storyboard/tools/n"+currentNeed+"/obj5.png", Texture.class);
            game.loadAssets("Storyboard");
            nextScreenAdap = "Storyboard";
            blackTexture.setColor(0, 0, 0, 1);

            blackTexture.setZIndex(1000);
            blackTexture.setVisible(true);
            game.backLoad.setZIndex(1000);
            game.backLoad.setVisible(true);
            game.barLoad.setZIndex(1001);
            game.barLoad.setVisible(true);
            game.endBarLoad.setZIndex(1002);
            game.endBarLoad.setVisible(true);
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

        if(!blackTexture.isVisible() || scenes[currentScene].type.equals("Dialog")){
            menuCard.setZIndex(1000);
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
    }

    public void closeScene(String scene){
        if(scene.equals("Dialog")){
            blackTexture.setVisible(false);
            character.setVisible(false);
            dialogBox.setVisible(false);
            dialogT.setVisible(false);
            title.setVisible(false);
            nextButt.setVisible(false);
            backButt.setVisible(false);
        }else if(scene.equals("Phase")){
            backPhases.setVisible(false);
            solido.setVisible(false);
            gas.setVisible(false);
            liquido.setVisible(false);
        }else if(scene.equals("Rocket")){
            ship.setVisible(false);
            galaxy.setVisible(false);
            activeShip = false;
        }else if(scene.equals("Selection1")){
            planet1.setVisible(false);
            planet2.setVisible(false);
            planet3.setVisible(false);
            planet4.setVisible(false);
            planet5.setVisible(false);
            sun.setVisible(false);
            randButt.setVisible(false);
            for(int i = 0; i < helpsImages.length; i++){
                helpsImages[i].showHelp(false);
            }
        }else if(scene.equals("Selection2")){
            for(int i = 0; i < 3; i++){
                rectangles[i].setVisible(false);
            }
            caso1.setVisible(false);
            caso2.setVisible(false);
            caso3.setVisible(false);
        }else if(scene.equals("Mazes")){
            for(int i = 0; i < 5; i++){
                mazes[i].setVisible(false);
            }
        }else if(scene.equals("Ideas")){
            setData();
            //game.addIdeasOnline("", new String[][]{});
            scroll.setVisible(false);
            ideaTitle.setVisible(false);
            addIdeaButt.setVisible(false);
            for(int i = 0; i < helpsImages.length; i++){
                helpsImages[i].showHelp(false);
            }
        }
    }

    public void changePhase(){
        if(timer > 1){
            timer -= Gdx.graphics.getDeltaTime();
        }else{
            if(beginPhase){
                if(game.soundOn)
                    phaseS.play();
                beginPhase = false;
                timer = 1;
            }
            if(maxRotation > 0){
                float newRotation = backPhases.getRotation()+(55*Gdx.graphics.getDeltaTime());
                if(newRotation>360)
                    newRotation-=360;
                maxRotation -= 55*Gdx.graphics.getDeltaTime();
                if(maxRotation < 0){
                    newRotation -= (maxRotation*-1);
                }
                backPhases.setRotation(newRotation);
            }else{
                if(timer > 0){
                    Gdx.app.log("Timer", String.valueOf(timer));
                    if(timer == 1){
                        currentFase++;
                        if(currentFase == 3)
                            liquido.setColor(1, 1, 1, 1);
                        else if(currentFase == 2)
                            gas.setColor(1, 1, 1, 1);
                        else if(currentFase == 1)
                            solido.setColor(1, 1, 1, 1);
                    }
                    timer -= Gdx.graphics.getDeltaTime();
                }else{
                    maxRotation = 120;
                    changingPhase = false;
                    beginPhase = true;
                    timer = 2;
                    nextCurrentScene();
                }
            }
            float angle = backPhases.getRotation()+330;
            float newX = (390*MathUtils.cosDeg(angle))+540-155;
            float newY = (390*MathUtils.sinDeg(angle))+960-148.5f;
            float angle2 = backPhases.getRotation()+210;
            float newX2 = (390*MathUtils.cosDeg(angle2))+540-197;
            float newY2 = (390*MathUtils.sinDeg(angle2))+960-153.5f;
            float angle3 = backPhases.getRotation()+90;
            float newX3 = (390*MathUtils.cosDeg(angle3))+540-173.5f;
            float newY3 = (390*MathUtils.sinDeg(angle3))+960-133;
            solido.setX(newX);
            solido.setY(newY);
            gas.setX(newX2);
            gas.setY(newY2);
            liquido.setX(newX3);
            liquido.setY(newY3);
        }
    }

    public void selectRand(int type){
        if(type == 1){
            if(currentTimeRoulette2 == -1){
                Random rand = new Random();
                float randomTime = MathUtils.random(3, 7);
                currentTimeRoulette2 = randomTime+rand.nextFloat();
            }else if(currentTimeRoulette2 > 0){
                if(timeRoulette > 0){
                    timeRoulette -= speedRoulette2*Gdx.graphics.getDeltaTime();
                }else{
                    if(game.getSound())
                        randS.play(0.5f);
                    current.setBounds(current.getX()+20, current.getY()+20,current.getWidth()-40, current.getHeight()-40);
                    current.setColor(1, 1, 1, 1);
                    currentTimeRoulette2 -= 0.1f;
                    timeRouletteCont += 0.1f;
                    timeRoulette = timeRouletteCont;
                    if(indexRoulette < 4)
                        indexRoulette++;
                    else
                        indexRoulette = 0;
                    current = planets[indexRoulette];
                    current.setBounds(current.getX()-20, current.getY()-20,current.getWidth()+40, current.getHeight()+40);
                    current.setColor(Color.GRAY);
                    current.setZIndex(1000);
                    currentMaze = indexRoulette;
                }
            }else{
                //ship.addAction(Actions.parallel(Actions.moveTo(current.getX()+140, current.getY()+170, 1), Actions.sizeTo(20, 10, 1.2f)));
                spinning = 0;
                current.setBounds(current.getX()+20, current.getY()+20,current.getWidth()-40, current.getHeight()-40);
                //current = planet1;
                currentTimeRoulette2 = -1;
                timeRoulette = 0.2f;
                timeRouletteCont = 0.2f;
                indexRoulette = 0;
                nextCurrentScene();
            }
        }
    }

    public void setComodin(){
        for(int i = 0; i < mazes.length; i++){
            mazes[i].setColor(1, 1, 1, 1);
            final int finalI = i;
            mazes[i].addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                    if(showMaze){
                        getRandom = true;
                        showMaze = false;
                        endAnim = 0;
                        showRandCards(finalI);
                    }
                    return true;
                }
            });
        }
    }

    public void storeCard(CardItem cardI, final int cardIndex){
        final CardItem randCard = cardI;
        RunnableAction run = Actions.run(new Runnable(){
            @Override
            public void run(){
                randCards[cardIndex].setVisible(false);
                for(int i = 0; i < 10; i++){
                    if(i != cardIndex){
                        randCards[i].addAction(Actions.sequence(Actions.parallel(Actions.moveTo(860, -250, 0.8f), Actions.sizeTo(200, 250, 0.8f)), Actions.run(new Runnable(){
                            @Override
                            public void run(){
                                endAnim += 1;
                                if(endAnim==10-usedCards[currentMaze].size){
                                    showMaze = true;
                                    if(comodin){
                                        if(numCards==1){
                                            //card2.setBounds(900, 10, 200, 250);
                                            card2.remove();
                                            /*card2.setScale(0.22f, 0.188f);
                                            card2.label.setText(tarjetas[currentMaze][currentCard]);
                                            card2.label.setColor(colorTextMazes[currentMaze]);
                                            card2.setColor(colorMazes[currentMaze]);*/
                                            menuCard.addCard(card2);
                                            card2.cardNum = currentCard;
                                            card2.maze = currentMaze;
                                            card2.onSlot = true;
                                        }else{
                                            //card3.setBounds(900, 10, 200, 250);
                                            card3.remove();
                                            /*card3.setScale(0.22f, 0.188f);
                                            card3.label.setText(tarjetas[currentMaze][currentCard]);
                                            card3.label.setColor(colorTextMazes[currentMaze]);
                                            card3.setColor(colorMazes[currentMaze]);*/
                                            menuCard.addCard(card3);
                                            card3.cardNum = currentCard;
                                            card3.maze = currentMaze;
                                            card3.onSlot = true;
                                        }
                                    }else{
                                        //card1.setBounds(900, 10, 200, 250);
                                        card1.remove();
                                        /*card1.setScale(0.22f, 0.188f);
                                        card1.head.setDrawable(skinCharacters.getDrawable("head"+(currentMaze+1)));
                                        card1.label.setText(tarjetas[currentMaze][currentCard]);
                                        card1.label.setColor(colorTextMazes[currentMaze]);
                                        card1.setColor(colorMazes[currentMaze]);*/
                                        menuCard.addCard(card1);
                                        card1.cardNum = currentCard;
                                        card1.maze = currentMaze;
                                        card1.onSlot = true;

                                        if(tarjetas[currentMaze][currentCard].equals("+1") || tarjetas[currentMaze][currentCard].equals("+2")){
                                            comodin = true;
                                            if(tarjetas[currentMaze][currentCard].equals("+1")){
                                                comodinCard = 1;
                                                //usedCards[currentMaze].add(currentCard);
                                            }else if(tarjetas[currentMaze][currentCard].equals("+2")){
                                                comodinCard = 2;
                                                //usedCards[currentMaze].add(currentCard);
                                            }
                                            for(int i = 0; i < 5; i++){
                                                //if(i != currentMaze){
                                                    for(int j = 0; j < 10; j++){
                                                        if(tarjetas[i][j].equals("+1") || tarjetas[i][j].equals("+2")){
                                                            if(i == currentMaze){
                                                                if(currentCard != j)
                                                                    usedCards[i].add(j);
                                                            }else{
                                                                usedCards[i].add(j);
                                                            }
                                                        }
                                                    }
                                                //}
                                            }
                                            setComodin();
                                        }else{
                                            //mazes[currentMaze].clear();
                                            showMaze = false;
                                            mazes[currentMaze].setColor(Color.GRAY);
                                        }
                                    }
                                    numCards++;
                                    if(comodin){
                                        if((numCards == 2 && comodinCard == 1) || (numCards == 3 && comodinCard == 2)){
                                            for(int i = 0; i < mazes.length; i++){
                                                mazes[i].setColor(Color.GRAY);
                                            }
                                            showMaze = false;
                                            if(showTutorials)
                                                menuCard.showWithArrow("Card1");
                                            else
                                                nextCurrentScene();
                                        }
                                    }else{
                                        if(showTutorials)
                                            menuCard.showWithArrow("Card1");
                                        else
                                            nextCurrentScene();
                                    }
                                }
                            }
                        })));
                    }
                }
            }
        });
        randCard.addAction(Actions.sequence(Actions.parallel(Actions.moveTo(1000, 1800, 0.8f), Actions.scaleTo(0.22f, 0.188f, 0.8f)), Actions.alpha(0), run));
    }

    public void showRandCard(final int card){
        Random rand = new Random();
        int[] posibleCards;
        int randC;
        if(usedCards[currentMaze].size>0){
            posibleCards = new int[10-usedCards[currentMaze].size];
            int current = 0;
            for(int i = 0; i < 10; i++){
                Gdx.app.log("Current", "Index:"+i);
                Gdx.app.log("Current", String.valueOf(usedCards[currentMaze].size));
                boolean used = false;
                for(int j = 0; j < usedCards[currentMaze].size; j++){
                    if(i == usedCards[currentMaze].get(j)){
                        Gdx.app.log("CurrentCard", String.valueOf(usedCards[currentMaze].get(j)));
                        used = true;
                        //break;
                    }
                }
                if(!used){
                    posibleCards[current] = i;
                    current++;
                }
            }
            randC = posibleCards[rand.nextInt(posibleCards.length)];
        }else{
            randC = rand.nextInt(10);
        }
        final int randCard = randC;
        /*final int randCard;
        if(!comodin)
            randCard = 9;
        else
            randCard = randC;*/
        currentCard = randCard;
        randCards[card].setZIndex(1000);
        usedCards[currentMaze].add(randCard);
        RunnableAction run2 = Actions.run(new Runnable(){
            @Override
            public void run(){
                if(game.getSound())
                    closeBag.play();
                if(tarjetas[currentMaze][randCard].equals("+1"))
                    showComodinText("+1", currentCardItem, card);
                else if(tarjetas[currentMaze][randCard].equals("+2"))
                    showComodinText("+2", currentCardItem, card);
                else
                    storeCard(currentCardItem, card);
            }
        });
        //randCards[card].addAction(Actions.sequence(run, Actions.parallel(Actions.moveTo(207.75f, 502.5f, 0.7f), Actions.sizeTo(664.5f, 915, 0.7f)), Actions.delay(0.5f), run2));

        randCards[card].setVisible(false);
        if(comodin){
            if(numCards==1){
                //card2.setDrawable(cardsSkin.getDrawable(tarjetas[currentMaze][randCard]));
                currentCardItem = card2;
                stage.addActor(card2);
                card2.setPosition(randCards[card].getX(), randCards[card].getY());
                card2.head.setDrawable(skinCharacters.getDrawable("head"+(currentMaze+1)));
                card2.label.setText(tarjetas[currentMaze][currentCard]);
                card2.label.setColor(colorTextMazes[currentMaze]);
                card2.setColor(colorMazes[currentMaze]);
                card2.addAction(Actions.sequence(Actions.parallel(Actions.moveTo(150, 375, 0.7f), Actions.scaleTo(1, 1, 0.7f)), Actions.delay(0.5f), run2));
            }else{
                //card3.setDrawable(cardsSkin.getDrawable(tarjetas[currentMaze][randCard]));
                currentCardItem = card3;
                stage.addActor(card3);
                card3.setPosition(randCards[card].getX(), randCards[card].getY());
                card3.head.setDrawable(skinCharacters.getDrawable("head"+(currentMaze+1)));
                card3.label.setText(tarjetas[currentMaze][currentCard]);
                card3.label.setColor(colorTextMazes[currentMaze]);
                card3.setColor(colorMazes[currentMaze]);
                card3.addAction(Actions.sequence(Actions.parallel(Actions.moveTo(150, 375, 0.7f), Actions.scaleTo(1, 1, 0.7f)), Actions.delay(0.5f), run2));
            }
        }else{
            currentCardItem = card1;
            stage.addActor(card1);
            card1.setPosition(randCards[card].getX(), randCards[card].getY());
            card1.head.setDrawable(skinCharacters.getDrawable("head"+(currentMaze+1)));
            card1.label.setText(tarjetas[currentMaze][currentCard]);
            card1.label.setColor(colorTextMazes[currentMaze]);
            card1.setColor(colorMazes[currentMaze]);
            card1.addAction(Actions.sequence(Actions.parallel(Actions.moveTo(150, 375, 0.7f), Actions.scaleTo(1, 1, 0.7f)), Actions.delay(0.5f), run2));
        }

        menuCard.setZIndex(1000);
    }

    public void showRandCards(int maze){
        float cardX = 132;
        float cardY = 1387;
        float sizeRand = 10;

        currentMaze = maze;
        sizeRand -= usedCards[currentMaze].size;

        if(game.getSound())
            cardShuffle.play();
        for(int i = 0; i < sizeRand; i++){
            randCards[i].setPosition(860, -250);
            randCards[i].setColor(1, 1, 1, 1);
            randCards[i].setDrawable(skinCharacters.getDrawable("rand"+(currentMaze+1)));
            stage.addActor(randCards[i]);
            randCards[i].setVisible(true);
            randCards[i].addAction(Actions.sequence(Actions.parallel(Actions.moveTo(cardX, cardY, 0.7f), Actions.sizeTo(226, 336, 0.7f))));
            cardX += 300;
            if(cardX > 915){
                if(i != 8){
                    cardX = 132;
                }else{
                    cardX = 432;
                }
                cardY -= 395;
            }
        }
    }

    public void setData(){
        int size = scroll.ideaBoxes.size;
        String[][] toSave = new String[menuCard.cards.size][2];
        for(int c = 0; c < menuCard.cards.size; c++){
            toSave[c] = new String[]{String.valueOf(menuCard.cards.get(c).maze), String.valueOf(menuCard.cards.get(c).cardNum)};
        }
        if(size > 1){
            String[][] ideas = new String[size][2];
            String ideasS = "{\n\"ideas\":[\n";
            for(int i = 0; i < size; i++){
                ideas[i] = new String[]{String.valueOf(card1.maze), String.valueOf(currentNeed), scroll.ideaBoxes.get(i).label.getText().toString()};
                ideasS += "[\""+scroll.ideaBoxes.get(i).label.getText().toString()+"\", \""+charactersNames[card1.maze]+"\", \""+auxTexts2[currentNeed-1]+"\", ";
                for(int j = 0; j < 3; j++){
                    if(j <= toSave.length-1)
                        ideasS += "\""+tarjetas[menuCard.cards.get(j).maze][menuCard.cards.get(j).cardNum]+"\", ";
                    else
                        ideasS += "\"null\", ";
                }
                ideasS = ideasS.substring(0, ideasS.length()-2);
                ideasS += "], ";
                dataUser.addCard(toSave);
            }
            ideasS = ideasS.substring(0, ideasS.length()-2);
            ideasS += "\n]\n}";
            dataUser.addIdeas(ideas);
            game.addIdeasOnline(ideasS);
        }else{
            String[] idea = new String[]{String.valueOf(card1.maze), String.valueOf(currentNeed), scroll.ideaBoxes.get(0).label.getText().toString()};
            String ideasS = "{\n\"ideas\":[\n[\""+scroll.ideaBoxes.get(0).label.getText().toString()+"\", \""+charactersNames[card1.maze]+"\", \""+auxTexts2[currentNeed-1]+"\", ";
            for(int i = 0; i < 3; i ++){
                if(i <= toSave.length-1)
                    ideasS += "\""+tarjetas[menuCard.cards.get(i).maze][menuCard.cards.get(i).cardNum]+"\", ";
                else
                    ideasS += "\"null\", ";
            }
            ideasS = ideasS.substring(0, ideasS.length()-2);
            ideasS += "]\n]\n}";
            dataUser.addIdea(idea);
            dataUser.addCard(toSave);
            game.addIdeasOnline(ideasS);
        }
        game.addIdeas();

        /*String cartasS = "{\n\"ideas\":[\n";
        String[][] cards = new String[dataUser.getCards().size][];
        for(int c = 0; c < dataUser.getCards().size; c++){
            cards[c] = new String[dataUser.getCards().get(c).length];
            cartasS += "[";
            for(int c2 = 0; c2 < dataUser.getCards().get(c).length; c2++){
                cards[c][c2] = tarjetas[Integer.parseInt(dataUser.getCards().get(c)[c2][0])][Integer.parseInt(dataUser.getCards().get(c)[c2][1])];
                Gdx.app.log("Tarjeta", cards[c][c2]);
                cartasS += "\""+tarjetas[Integer.parseInt(dataUser.getCards().get(c)[c2][0])][Integer.parseInt(dataUser.getCards().get(c)[c2][1])]+"\", ";
            }
            cartasS = cartasS.substring(0, cartasS.length()-2);
            cartasS += "], ";
        }
        cartasS = cartasS.substring(0, cartasS.length()-2);
        cartasS += "\n]\n}";
        game.checkUser("", cartasS);*/

        Json json = new Json();
        json.prettyPrint(dataUser);
    }

    public void unloadAssets(){
        assetManager.unload("n1.png");
        assetManager.unload("n2.png");
        assetManager.unload("n3.png");
    }

    public void animNecesidad(String necesidad){
        if(necesidad.equals("n1")){
            caso1.clearListeners();
            caso2.clearListeners();
            caso3.clearListeners();
            caso2.addAction(Actions.sequence(Actions.moveTo(-1080, 643, 1)));
            helpsImages[1].help.addAction(Actions.sequence(Actions.moveTo(-1080, helpsImages[1].help.getY(), 1)));
            caso3.addAction(Actions.sequence(Actions.moveTo(-1080, 73, 1), Actions.run(new Runnable() {
                @Override
                public void run(){
                    helpsImages[0].help.setVisible(false);
                    helpsImages[1].help.setVisible(false);
                    helpsImages[2].help.setVisible(false);
                    caso1.addAction(Actions.sequence(Actions.parallel(Actions.moveTo(1000, 1800, 1), Actions.sizeTo(150, 100, 1)), Actions.run(new Runnable() {
                        @Override
                        public void run(){
                            caso1.setVisible(false);
                            helpsImages[0].help.setVisible(false);
                            menuCard.setProblemaText("[#511577]Recuerda que estas ayudando a "+charactersNames[currentMaze]+" en el tema de [#ff0000]"+auxTexts2[((currentMaze+1)*3)-3], skinCharacters.getDrawable("iconN"+(((currentMaze+1)*3)-2)));
                            currentNeed = ((currentMaze+1)*3)-2;
                            //nextCurrentScene();
                            if(showTutorials){
                                menuCard.showWithArrow("Problematica");
                                emergentBox.closeButt.addListener(new InputListener(){
                                    @Override
                                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                                        if(menuCard.toNext){
                                            menuCard.toNext = false;
                                            emergentBox.closeButt.removeListener(this);
                                            nextCurrentScene();
                                        }
                                        return true;
                                    }
                                });
                            }else{
                                nextCurrentScene();
                            }
                        }
                    })));
                }
            })));
            helpsImages[2].help.addAction(Actions.sequence(Actions.moveTo(-1080, helpsImages[2].help.getY(), 1)));
        }else if(necesidad.equals("n2")){
            caso1.clearListeners();
            caso2.clearListeners();
            caso3.clearListeners();
            caso1.addAction(Actions.sequence(Actions.moveTo(-1080, 1213, 1)));
            helpsImages[0].help.addAction(Actions.sequence(Actions.moveTo(-1080, helpsImages[0].help.getY(), 1)));
            caso3.addAction(Actions.sequence(Actions.moveTo(-1080, 73, 1), Actions.run(new Runnable() {
                @Override
                public void run(){
                    helpsImages[0].help.setVisible(false);
                    helpsImages[1].help.setVisible(false);
                    helpsImages[2].help.setVisible(false);
                    caso2.addAction(Actions.sequence(Actions.parallel(Actions.moveTo(1000, 1800, 1), Actions.sizeTo(150, 100, 1)), Actions.run(new Runnable() {
                        @Override
                        public void run(){
                            caso2.setVisible(false);
                            helpsImages[1].help.setVisible(false);
                            menuCard.setProblemaText("[#511577]Recuerda que estas ayudando a "+charactersNames[currentMaze]+" en el tema de [#ff0000]"+auxTexts2[((currentMaze+1)*3)-2], skinCharacters.getDrawable("iconN"+(((currentMaze+1)*3)-1)));
                            currentNeed = ((currentMaze+1)*3)-1;
                            //nextCurrentScene();
                            if(showTutorials){
                                menuCard.showWithArrow("Problematica");
                                emergentBox.closeButt.addListener(new InputListener(){
                                    @Override
                                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                                        if(menuCard.toNext){
                                            menuCard.toNext = false;
                                            emergentBox.closeButt.removeListener(this);
                                            nextCurrentScene();
                                        }
                                        return true;
                                    }
                                });
                            }else{
                                nextCurrentScene();
                            }
                        }
                    })));
                }
            })));
            helpsImages[2].help.addAction(Actions.sequence(Actions.moveTo(-1080, helpsImages[2].help.getY(), 1)));
        }else if(necesidad.equals("n3")){
            caso1.clearListeners();
            caso2.clearListeners();
            caso3.clearListeners();
            caso1.addAction(Actions.sequence(Actions.moveTo(-1080, 1213, 1)));
            helpsImages[0].help.addAction(Actions.sequence(Actions.moveTo(-1080, helpsImages[0].help.getY(), 1)));
            caso2.addAction(Actions.sequence(Actions.moveTo(-1080, 643, 1), Actions.run(new Runnable() {
                @Override
                public void run(){
                    helpsImages[0].help.setVisible(false);
                    helpsImages[1].help.setVisible(false);
                    helpsImages[2].help.setVisible(false);
                    caso3.addAction(Actions.sequence(Actions.parallel(Actions.moveTo(1000, 1800, 1), Actions.sizeTo(150, 100, 1)), Actions.run(new Runnable() {
                        @Override
                        public void run(){
                            caso3.setVisible(false);
                            helpsImages[2].help.setVisible(false);
                            menuCard.setProblemaText("[#511577]Recuerda que estas ayudando a "+charactersNames[currentMaze]+" en el tema de [#ff0000]"+auxTexts2[((currentMaze+1)*3)-1], skinCharacters.getDrawable("iconN"+(((currentMaze+1)*3))));
                            currentNeed = ((currentMaze+1)*3);
                            //nextCurrentScene();
                            if(showTutorials){
                                menuCard.showWithArrow("Problematica");
                                emergentBox.closeButt.addListener(new InputListener(){
                                    @Override
                                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                                        if(menuCard.toNext){
                                            menuCard.toNext = false;
                                            emergentBox.closeButt.removeListener(this);
                                            nextCurrentScene();
                                        }
                                        return true;
                                    }
                                });
                            }else{
                                nextCurrentScene();
                            }
                        }
                    })));
                }
            })));
            helpsImages[1].help.addAction(Actions.sequence(Actions.moveTo(-1080, helpsImages[1].help.getY(), 1)));
        }
        for(int i = 0; i < helpsImages.length; i++){
            helpsImages[i].showHelp(false);
        }
    }

    public void showComodinText(String type, final CardItem _cardI, final int _cardIndex){
        blackTexture.setColor(0, 0, 0, 0.8f);
        blackTexture.setVisible(true);
        blackTexture.setZIndex(1000);
        textComodin = new Image(assetManager.get("winBack.png", Texture.class));
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
        gift = new Image(assetManager.get("gift.png", Texture.class));
        gift.setPosition(184.72f, 651.37f);
        stage.addActor(gift);
        if(type.equals("+1")){
            numberComodin = new Image(assetManager.get("comodin1G.png", Texture.class));
            numberComodin2 = new Image(assetManager.get("comodin1.png", Texture.class));
        }else{
            numberComodin = new Image(assetManager.get("comodin2G.png", Texture.class));
            numberComodin2 = new Image(assetManager.get("comodin2.png", Texture.class));
        }
        numberComodin2.setPosition(749.5f, 484);
        stage.addActor(numberComodin2);
        numberComodin.setOrigin(Align.center);
        numberComodin.setScale(0);
        stage.addActor(numberComodin);
        numberComodin.setPosition(540-141, 800);
        numberComodin.setVisible(true);
        numberComodin.addAction(Actions.sequence(Actions.parallel(Actions.scaleTo(1, 1, 1), Actions.moveTo(399, 1100, 1)), Actions.delay(1), Actions.run(new Runnable(){
            @Override
            public void run(){
                hideComodinText();
                storeCard(_cardI, _cardIndex);
            }
        })));
        if(game.getSound())
            winS.play();
    }

    public void hideComodinText(){
        blackTexture.setVisible(false);
        textComodin.setVisible(false);
        firework1.setVisible(false);
        firework2.setVisible(false);
        firework3.setVisible(false);
        firework4.setVisible(false);
        firework5.setVisible(false);
        firework6.setVisible(false);
        gift.setVisible(false);
        numberComodin.setVisible(false);
        numberComodin2.setVisible(false);
    }

    public void animRocket(String type){
        activeShip = true;
        ship.setScale(1, 1);
        ship.setPosition(-244, -559);
        ship.setZIndex(1000);
        ship.setVisible(true);
        if(type.equals("begin")){
            galaxy.setVisible(true);
            ship.setRotation(-19);
            ship.setOrigin(Align.bottomLeft);
            if(game.getSound())
                shipS.play(0.7f);
            ship.addAction(Actions.sequence(Actions.parallel(Actions.moveTo(768, 1556, 2), Actions.scaleTo(0, 0, 2)), Actions.run(new Runnable(){
                @Override
                public void run(){
                    ship.clearActions();
                    ship.setVisible(false);
                    destello.setVisible(true);
                    destello.addAction(Actions.sequence(Actions.alpha(0), Actions.parallel(Actions.alpha(1, 0.7f), Actions.scaleTo(0.5f, 0.5f, 0.7f)), Actions.rotateTo(90, 0.5f), Actions.alpha(0, 1), Actions.run(new Runnable() {
                        @Override
                        public void run(){
                            destello.setVisible(false);
                            ship.clearActions();
                            nextCurrentScene();
                        }
                    })));
                }
            })));
        }else if(type.equals("planet")){
            final float moveToX = current.getX()+(current.getWidth()/2)-60;
            float moveToY1 = current.getY()+current.getHeight()+10;
            float moveToY2 = current.getY()+(current.getHeight()/2);
            float rotation1 = (float) Math.toDegrees(MathUtils.atan2((moveToY1-273.5f), (moveToX-119)));
            ship.setRotation(-90+rotation1);
            ship.setOrigin(Align.bottomLeft);
            Gdx.app.log("Rotation", String.valueOf(rotation1));
            float timeAnim = 2;
            if(current == planet5){
                timeAnim = 1;
            }
            if(game.getSound())
                shipLandS.play();
            ship.addAction(Actions.sequence(Actions.parallel(Actions.moveTo(moveToX, moveToY1, timeAnim, Interpolation.exp5Out), Actions.scaleTo(0.4f, 0.4f, timeAnim)), Actions.parallel(Actions.rotateTo(0, 0.5f), Actions.moveTo(moveToX+30, moveToY1, 0.5f)), Actions.moveTo(moveToX+30, moveToY2, 1), Actions.parallel(Actions.scaleTo(0, 0, 2), Actions.moveTo(moveToX+78.8f, moveToY2-30, 2)), Actions.run(new Runnable(){
                @Override
                public void run(){
                    ship.clearActions();
                    nextCurrentScene();
                }
            })));
        }
    }

    @Override
    public void showLoad(String asset){
        backMusic.stop();
        backMusic2.stop();
        nextScreen = true;
        /*if(asset.equals("Menu"))
            nextScreenAdap = asset;
        if(asset.equals("Storyboard"))*/
        nextScreenAdap = asset;
        game.loadAssets(asset);
        blackTexture.setColor(0, 0, 0, 1);
        blackTexture.setZIndex(1000);
        blackTexture.setVisible(true);
        game.backLoad.setZIndex(1000);
        game.backLoad.setVisible(true);
        game.barLoad.setZIndex(1001);
        game.barLoad.setVisible(true);
        game.endBarLoad.setZIndex(1002);
        game.endBarLoad.setVisible(true);
    }

    public void beginPlanet(int planet){
        currentMaze = planet;
        current = planets[planet];
        randButt.clearListeners();
        nextCurrentScene();
        for(int i = 0; i < planets.length; i++){
            planets[i].clearListeners();
        }
    }
}
