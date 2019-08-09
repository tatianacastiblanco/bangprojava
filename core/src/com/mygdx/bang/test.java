package com.mygdx.bang;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.Random;

public class test extends ScreenAdapter{
    FitViewport viewport;
    OrthographicCamera camera;
    SpriteBatch batch;
    Stage stage;

    Sprite roulette;
    Sprite arrow2;

    boolean spining = false;
    boolean spining2 = false;
    float rotation = 0;
    float speedRotation = 500;
    float timeSpin = 5;
    float randomTime;
    boolean begin = false;

    Image spinButt;

    Table selectionsBox;
    Label title, selec1, selec2, selec3;
    float sourceY, targetY;
    Label[] targets;

    String[] tarjetas1 = {"a", "b", "c", "d", "e", "f", "g", "h", "+1", "+2"};
    String[] tarjetas2 = {"a", "b", "c", "d", "e", "f", "g", "h", "+1", "+2"};
    String[] tarjetas3 = {"a", "b", "c", "d", "e", "f", "g", "h", "+1", "+2"};
    String[] tarjetas4 = {"a", "b", "c", "d", "e", "f", "g", "h", "+1", "+2"};
    String[] tarjetas5 = {"a", "b", "c", "d", "e", "f", "g", "h", "+1", "+2"};
    //String[] tarjetas1 = {"Singapur", "Busca un libro y lee su primer párrafo", "Desordénalo todo", "Papel", "Piensa como Steve Jobs", "¿Qué haría tu mejor amigo?", "Juega el juego", "+1", "Haz cosas imperfectas", "Déjà vu", "+2"};
    /*String[] tarjetas2 = {"Haz una lista de diez cosas que podrías hacer y haz lo último en la lista", "Elimina", "¿Qué está haciendo el sector de alimentos?", "Una sencilla solución, dos dificiles soluciones", "Piensa como Walt Disney", "Cambia de roles", "Japón", "Comodín", "Toma un descanso", "Mira el lado oscuro de la historia", "Personaje"};
    String[] tarjetas3 = {"Comodin", "Piensa como un lobo", "+2", "Combina lo incombinable", "Corta una conexión", "Elimina, reduce, incrementa y crea", "Investiga tres tendencias en el área humana", "Observa de cerca los detalles más vergonzosos y amplíalos", "Sé aburrido", "Adapta", "Personaje"};
    String[] tarjetas4 = {"Quita las partes importantes", "Comodin", "Piensa cosas imposibles de hacer", "Cambia de roles", "Great place to work?", "Haz trocitos y después juntalos", "¿Qué soñaste ayer?", "Cuantas manos y cabezas sean necesarias", "Modifica", "Duro como una roca, flexible como....", "Personaje"};
    String[] tarjetas5 = {"El cielo es un vecindario", "Japón", "Comodin", "+2", "Humanice cualquier cosa", "Empieza por un garaje", "¿Qué pasaría si...?", "Pon otros usos", "Dale un vuelco a tu rutina", "Lego", "Personaje"};*/

    boolean onComodin = false;
    int limitNewSelection = 0;
    Image[] mazes;
    Array<int[]> mazesCards;

    Sprite backPhases, backPhases2, solido, gas, liquido;
    boolean changingPhase = false;
    float maxRotation = 120;

    Image blackScreen;

    int screen = 0;

    Image nextButton;

    float timer = 2;

    Sprite card;

    boolean storingCard = false;

    int color = -1;

    Image[] randCards;

    Image[] selectedCards;

    int currentCard = 0;

    Drawable randCardDrawable;

    boolean cardOn = false;

    float[] oldPosition = {0, 0};

    Skin cardsSkin;

    Label title1, title2;

    Image arrow;

    Image[] roulette2;
    float timeRoulette2 = 0.2f;
    float currentTimeRoulette2 = 0.2f;
    float speedRoulette2 = 40;
    int indexRoulette = 0;
    Image current;
    Image hand;

    test(){
        camera = new OrthographicCamera(1920, 1080);
        camera.position.set(960, 540, 0);
        viewport = new FitViewport(1920, 1080, camera);
        batch = new SpriteBatch();
        stage = new Stage(new FitViewport(1920, 1080));

        cardsSkin = new Skin();
        for(int i = 0; i < tarjetas1.length; i++){
            cardsSkin.add(tarjetas1[i], new Texture(tarjetas1[i]+".png"));
        }

        mazesCards = new Array<int[]>();

        roulette = new Sprite(new Texture("ruleta.png"));
        roulette.setBounds(298.5f, 228.5f, 797, 797);
        roulette.setOrigin(398.5f, 398.5f);
        arrow2 = new Sprite(new Texture("flecha.png"), 0, 0, 834, 834);
        arrow2.setBounds(280, 210, 834, 834);

        speedRotation = MathUtils.random(400, 650);
        timeSpin = MathUtils.random(4, 6);

        spinButt = new Image(new Texture("badlogic.jpg"));
        spinButt.setBounds(572, 15, 250, 180);
        spinButt.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                if(screen == 2){
                    spining = true;
                    speedRotation = MathUtils.random(400, 650);
                    timeSpin = MathUtils.random(4, 6);
                    begin = true;
                }else{
                    spining2 = true;
                }
                return true;
            }
        });

        nextButton = new Image(new Texture("badlogic.jpg"));
        nextButton.setBounds(1500, 15, 350, 200);
        nextButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                setTransition();
                return true;
            }
        });
        nextButton.setVisible(false);

        stage.addActor(nextButton);

        targets = new Label[2];

        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();

        selectionsBox = new Table();
        selectionsBox.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(pixmap))));
        selectionsBox.setBounds(1510, 570, 400, 500);
        selectionsBox.setDebug(true);
        Label.LabelStyle labelStyle = new Label.LabelStyle(new BitmapFont(), Color.WHITE);
        selec1 = new Label("Valor1", labelStyle);
        selec1.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                sourceY = selec1.getY();
                return  true;
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                float dragY = Gdx.input.getDeltaY();
                selec1.setY(selec1.getY()-dragY);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                targets[0] = selec2;
                targets[1] = selec3;
                changeOrder(selec1);
            }
        });
        selec1.setBounds(0, 0, 380, 100);
        selec1.setFontScale(4);
        selec2 = new Label("Valor2", labelStyle);
        selec2.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                sourceY = selec2.getY();
                return  true;
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                float dragY = Gdx.input.getDeltaY();
                selec2.setY(selec2.getY()-dragY);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                targets[0] = selec1;
                targets[1] = selec3;
                changeOrder(selec2);
            }
        });
        selec2.setBounds(0, 100, 380, 100);
        selec2.setFontScale(4);
        selec3 = new Label("Valor3", labelStyle);
        selec3.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                sourceY = selec3.getY();
                return  true;
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                float dragY = Gdx.input.getDeltaY();
                selec3.setY(selec3.getY()-dragY);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                targets[0] = selec1;
                targets[1] = selec2;
                changeOrder(selec3);
            }
        });
        selec3.setBounds(0, 200, 380, 100);
        selec3.setFontScale(4);
        selectionsBox.addActor(selec1);
        selectionsBox.row();
        selectionsBox.addActor(selec2);
        selectionsBox.row();
        selectionsBox.addActor(selec3);

        mazes = new Image[5];
        float buttX = 170;
        float buttY = 573.33f;
        final String[] colors = {"Morado", "Amarillo", "Verde", "Rojo", "Azul"};
        for(int i = 0; i < 5; i++){
            mazes[i] = new Image(new Texture("badlogic.jpg"));
            mazes[i].setBounds(buttX, buttY, 300, 420);
            final int finalI = i;
            mazes[i].addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    showRandCards(finalI);
                    if(onComodin)
                        getRandomCard(finalI, 2);
                    else
                        getRandomCard(finalI, 1);
                    return true;
                }
            });
            mazes[i].setVisible(false);
            buttX += 470;
            if(i == 2){
                buttY -= 506.66f;
                buttX = 405;
            }
        }

        stage.addActor(selectionsBox);
        stage.addActor(spinButt);
        selec1.setY(400);
        selec2.setY(300);
        selec3.setY(200);
        for(int i = 0; i < 5; i++){
            stage.addActor(mazes[i]);
        }

        backPhases2 = new Sprite(new Texture("backPhases2.png"));
        backPhases2.setBounds(583, 94, 740, 740);
        backPhases = new Sprite(new Texture("backPhases.png"));
        backPhases.setBounds(621, 244, 678, 592);
        backPhases.setOrigin(339, 222f);
        solido = new Sprite(new Texture("solido.png"));
        solido.setPosition(862, 736);
        gas = new Sprite(new Texture("gas.png"));
        gas.setPosition(1041, 400);
        liquido = new Sprite(new Texture("liquido.png"));
        liquido.setPosition(702, 400);

        card = new Sprite(new Texture("card.png"));
        card.setBounds(627.75f, 82.5f, 664.5f, 915);

        randCardDrawable = new TextureRegionDrawable(new TextureRegion(new Texture("card2.png")));

        randCards = new Image[10];
        float randCardsX = 860;
        float randCardsY = -250;
        for(int i = 0; i < 10; i++){
            randCards[i] = new Image(randCardDrawable);
            randCards[i].setBounds(randCardsX, randCardsY, 200, 250);
            final int finalI = i;
            randCards[i].addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                    showRandCard(finalI);
                    return true;
                }
            });
            randCards[i].setVisible(false);
            stage.addActor(randCards[i]);
        }

        pixmap.setColor(Color.BLACK);
        pixmap.fill();
        blackScreen = new Image(new Texture(pixmap));
        blackScreen.setBounds(0, 0, 1920, 1080);
        blackScreen.setColor(0, 0, 0,  0);
        blackScreen.setVisible(false);
        stage.addActor(blackScreen);

        title1 = new Label("Gira la ruleta", labelStyle);
        title1.setBounds(-1920, 440, 1920, 200);
        title1.setFontScale(5);
        title1.setWrap(true);
        title1.setAlignment(Align.center);
        title2 = new Label("Solido", labelStyle);
        title2.setBounds(0, -1080, 1920, 200);
        title2.setFontScale(5);
        //title2.setSize(1920, 1080);
        title2.setWrap(true);
        title2.setAlignment(Align.center);
        stage.addActor(title1);
        stage.addActor(title2);

        arrow = new Image(new Texture("arrow.png"));
        arrow.setBounds(0, 0, 100, 100);
        stage.addActor(arrow);

        roulette2 = new Image[5];
        float roulette2X = 200;
        Color[] rouletteColors = new Color[]{Color.BLUE, Color.YELLOW, Color.PURPLE, Color.GREEN, Color.RED};
        for(int i = 0; i < 5; i++){
            pixmap.setColor(rouletteColors[i]);
            pixmap.fill();
            roulette2[i] = new Image(new Texture(pixmap));
            roulette2[i].setBounds(roulette2X, 280, 304, 500);
            roulette2X += 304;
            roulette2[i].setVisible(false);
            stage.addActor(roulette2[i]);
        }
        current = roulette2[4];

        hand = new Image(new Texture("pointingHand.png"));
        hand.setPosition(432, 280);
        hand.setVisible(false);
        stage.addActor(hand);

        setTransition();
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        if(spining){
            spin(delta);
        }
        if(spining2){
            spinRoulette2(delta);
        }
        if(screen == 2){
            roulette.draw(batch);
            arrow2.draw(batch);
        }
        if(changingPhase)
            changePhase();
        if(screen == 1 || screen == 3 || screen == 5){
            backPhases.draw(batch);
            gas.draw(batch);
            liquido.draw(batch);
            solido.draw(batch);
        }
        batch.end();
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    public void spin(float delta){
        if(timeSpin > 0){
            timeSpin -= delta;
            rotation += delta*speedRotation;
            if(rotation > 360){
                rotation -= 360;
            }
            roulette.setRotation(rotation);
        }else if(speedRotation > 0){
            if(begin){
                Random rand = new Random();
                randomTime = rand.nextInt(2)+rand.nextFloat();
                rotation += delta*speedRotation;
                if(rotation > 360){
                    rotation -= 360;
                }
                roulette.setRotation(rotation);
                begin = false;
            }
            if(speedRotation < 20){
                if(randomTime>0){
                    randomTime -= delta;
                    rotation += delta*speedRotation;
                    if(rotation > 360){
                        rotation -= 360;
                    }
                    roulette.setRotation(rotation);
                }else{
                    speedRotation = 0;
                }
            }else{
                speedRotation -= delta*40;
                rotation += delta*speedRotation;
                if(rotation > 360){
                    rotation -= 360;
                }
                roulette.setRotation(rotation);
            }
        }else{
            spining = false;
            float currentRotation = roulette.getRotation();
            if(currentRotation <= 36 || currentRotation > 324){
                Gdx.app.log("Selection2", "Morado");
                selec1.setText("Familia");
                selec2.setText("Aprendizaje");
                selec3.setText("Objetivos Personales");
                color = 0;
                selectionsBox.setColor(0.282f, 0.294f, 0.4f, 1);
                //getRandomCard("Morado", 1);
            }else if(currentRotation <= 108){
                Gdx.app.log("Selection2", "Amarillo");
                selec1.setText("Proyeccion");
                selec2.setText("Salario");
                selec3.setText("Reconocimiento");
                color = 1;
                selectionsBox.setColor(0.98f, 0.776f, 0.27f, 1);
                //getRandomCard("Amarillo", 1);
            }else if(currentRotation <= 180){
                Gdx.app.log("Selection2", "Azul");
                selec1.setText("Equipos");
                selec2.setText("Infrastructura");
                selec3.setText("Transporte");
                selectionsBox.setColor(0.102f, 0.565f, 0.6f, 1);
                color = 2;
                //getRandomCard("Azul", 1);
            }else if(currentRotation <= 252){
                Gdx.app.log("Selection2", "Verde");
                selec1.setText("Trabajo Colaborativo");
                selec2.setText("Articulación");
                selec3.setText("Comunicación");
                selectionsBox.setColor(0.38f, 0.722f, 0.533f, 1);
                color = 3;
                //getRandomCard("Verde", 1);
            }else{
                Gdx.app.log("Selection2", "Rojo");
                selec1.setText("Bienestar");
                selec2.setText("Estabilidad");
                selec3.setText("Liderazgo");
                selectionsBox.setColor(0.929f, 0.765f, 0.424f, 1);
                color = 4;
                //getRandomCard("Rojo", 1);
            }
        }
    }

    public void changeOrder(Label source){
        boolean changed = false;
        for(int i = 0; i < 2; i++){
            if(!changed){
                if(source.getY()+source.getHeight()>targets[i].getY() && source.getY()<targets[i].getY()+targets[i].getHeight()){
                    targetY = targets[i].getY();
                    targets[i].setY(sourceY);
                    source.setY(targetY);
                    changed = true;
                }
            }
        }
        if(!changed){
            source.setY(sourceY);
        }
    }

    public void getRandomCard(int mazo, int type){
        Random rand = new Random();
        String card = "";
        int randCard = rand.nextInt(10);
        int[] selected = {mazo, randCard};
        if(mazo == 0){
            card = tarjetas1[randCard];
            currentCard = randCard;
            Gdx.app.log("Tarjeta", tarjetas1[randCard]);
        }else if(mazo == 1){
            card = tarjetas2[randCard];
            currentCard = randCard;
            Gdx.app.log("Tarjeta", tarjetas2[randCard]);
        }else if(mazo == 3){
            card = tarjetas3[randCard];
            currentCard = randCard;
            Gdx.app.log("Tarjeta", tarjetas3[randCard]);
        }else if(mazo == 4){
            card = tarjetas4[randCard];
            currentCard = randCard;
            Gdx.app.log("Tarjeta", tarjetas4[randCard]);
        }else if(mazo == 2){
            card = tarjetas5[randCard];
            currentCard = randCard;
            Gdx.app.log("Tarjeta", tarjetas5[randCard]);
        }
        Gdx.app.log("Tarjeta2", String.valueOf(type));
        if(card.equals("+2")){
            if(type == 2){
                getRandomCard(mazo, type);
            }else{
                limitNewSelection = 2;
                selectedCards = new Image[3];
                mazesCards.add(selected);
            }
        }else if(card.equals("+1")){
            if(type == 2){
                getRandomCard(mazo, type);
            }else{
                limitNewSelection = 1;
                selectedCards = new Image[2];
                mazesCards.add(selected);
            }
        }else{
            if(type == 1){
                selectedCards = new Image[1];
            }
            mazesCards.add(selected);
        }
    }

    public void showMazes(){
        for(int i = 0; i < mazes.length; i++){
            mazes[i].setVisible(true);
            if(i != color){
                mazes[i].setColor(Color.GRAY);
                mazes[i].clearListeners();
            }
        }
    }

    public void showRandCards(int maze){
        float cardX = 70;
        float cardY = 26.66f;
        float sizeRand = 10;
        for(int i = 0; i < mazesCards.size; i++){
            if(maze == mazesCards.get(i)[0]){
                sizeRand--;
            }
        }
        for(int i = 0; i < sizeRand; i++){
            randCards[i].setColor(1, 1, 1, 1);
            randCards[i].setVisible(true);
            randCards[i].addAction(Actions.sequence(Actions.parallel(Actions.moveTo(cardX, cardY, 0.7f), Actions.sizeTo(300, 500, 0.7f))));
            cardX += 370;
            if(i == 4){
                cardX = 70;
                cardY += 526.66f;
            }
        }
    }

    public void showRandCard(final int card){
        randCards[card].setZIndex(1000);
        RunnableAction run = Actions.run(new Runnable(){
            @Override
            public void run() {
                //randCards[card].setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture("card.png"))));
                randCards[card].setDrawable(cardsSkin.getDrawable(tarjetas1[currentCard]));
            }
        });
        RunnableAction run2 = Actions.run(new Runnable(){
            @Override
            public void run() {
                storeCard(card);
            }
        });
        randCards[card].addAction(Actions.sequence(run, Actions.parallel(Actions.moveTo(627.75f, 82.5f, 0.7f), Actions.sizeTo(664.5f, 915, 0.7f)), Actions.delay(0.5f), run2));
    }

    public void showCard(final int card){
        Gdx.app.log("index", String.valueOf(card));
        RunnableAction run = Actions.run(new Runnable() {
            @Override
            public void run(){
                if(cardOn)
                    cardOn = false;
                else
                    cardOn = true;
            }
        });
        selectedCards[card].setZIndex(1000);
        if(!cardOn){
            oldPosition = new float[]{selectedCards[card].getX(), selectedCards[card].getY()};
            selectedCards[card].addAction(Actions.sequence(Actions.parallel(Actions.moveTo(627.75f, 82.5f, 0.7f), Actions.sizeTo(664.5f, 915, 0.7f)), run));
        }else{
            selectedCards[card].addAction(Actions.sequence(Actions.parallel(Actions.moveTo(oldPosition[0], oldPosition[1], 0.7f), Actions.sizeTo(200, 250, 0.7f)), run));
        }
    }

    public void closeMazes(){
        for(int i = 0; i < mazes.length; i++){
            mazes[i].setVisible(false);
        }
    }

    public void changePhase(){
        if(timer > 1){
            timer -= Gdx.graphics.getDeltaTime();
        }else{
            if(maxRotation > 0){
                float newRotation = backPhases.getRotation()+(40*Gdx.graphics.getDeltaTime());
                if(newRotation>360)
                    newRotation-=360;
                backPhases.setRotation(newRotation);
                maxRotation -= 40*Gdx.graphics.getDeltaTime();
            }else{
                if(timer > 0){
                    timer -= Gdx.graphics.getDeltaTime();
                }else{
                    maxRotation = 120;
                    changingPhase = false;
                    setTransition();
                    timer = 2;
                }
            }
            float angle = backPhases.getRotation()+90;
            float newX = (float)(370*Math.cos(Math.toRadians(angle)))+771+98;
            float newY = (float)(370*Math.sin(Math.toRadians(angle)))+267+100;
            //Gdx.app.log("Angulo", "("+newX+", "+String.valueOf(newY)+")");
            float angle2 = backPhases.getRotation()+330;
            float newX2 = (float)(370*Math.cos(Math.toRadians(angle2)))+771+98;
            float newY2 = (float)(370*Math.sin(Math.toRadians(angle2)))+267+100;
            float angle3 = backPhases.getRotation()+210;
            float newX3 = (float)(370*Math.cos(Math.toRadians(angle3)))+771+98;
            float newY3 = (float)(370*Math.sin(Math.toRadians(angle3)))+267+100;
            solido.setX(newX);
            solido.setY(newY);
            gas.setX(newX2);
            gas.setY(newY2);
            liquido.setX(newX3);
            liquido.setY(newY3);
        }
    }

    public void setTransition(){
        blackScreen.setVisible(true);
        RunnableAction run = Actions.run(new Runnable() {
            @Override
            public void run(){
                changeScreen();
                screen++;
            }
        });
        RunnableAction run2 = Actions.run(new Runnable(){
            @Override
            public void run(){
                blackScreen.setVisible(false);
            }
        });
        blackScreen.addAction(Actions.sequence(Actions.fadeIn(0.8f), run, Actions.delay(0.5f), Actions.fadeOut(0.8f), run2));
    }

    public void changeScreen(){
        if(screen == 0 || screen == 2 || screen == 4){
            changingPhase = true;
            Array<Actor> actors = stage.getActors();
            for(int i = 0; i < actors.size-1; i++)
                if(!actors.get(i).equals(blackScreen))
                    actors.get(i).setVisible(false);
        }else if(screen == 1){
            spinButt.setVisible(true);
            nextButton.setVisible(true);
            selectionsBox.setVisible(true);
            showText();
        }else if(screen == 3){
            showMazes();
            selectionsBox.setVisible(true);
            nextButton.setVisible(true);
        }else if(screen == 5){
            selectionsBox.setVisible(true);
            for(int i = 0; i < roulette2.length; i++)
                roulette2[i].setVisible(true);
            hand.setVisible(true);
            spinButt.setVisible(true);
            nextButton.setVisible(true);
        }
    }

    public void showComodin(){

    }

    public void storeCard(final int cardI){
        final Image randCard = randCards[cardI];
        final RunnableAction run2 = Actions.run(new Runnable(){
            @Override
            public void run(){
                int selectedCardI = 0;
                if(onComodin && limitNewSelection > 0){
                    if(selectedCards.length == 2 && selectedCards[1] == null){
                        selectedCardI = 1;
                        limitNewSelection--;
                    }else if(selectedCards.length == 3){
                        if(selectedCards[1] == null)
                            selectedCardI = 1;
                        else
                            selectedCardI = 2;
                        limitNewSelection--;
                    }
                }
                selectedCards[selectedCardI] = new Image(randCard.getDrawable());
                selectedCards[selectedCardI].setBounds(randCard.getX(), randCard.getY(), randCard.getWidth(), randCard.getHeight());
                final int finalSelectedCardI = selectedCardI;
                Gdx.app.log("Valores", String.valueOf(selectedCardI)+"->"+finalSelectedCardI);
                selectedCards[selectedCardI].addListener(new InputListener(){
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        showCard(finalSelectedCardI);
                        return true;
                    }
                });
                randCard.setBounds(860, -250, 200, 250);
                randCard.setDrawable(randCardDrawable);
                stage.addActor(selectedCards[selectedCardI]);
                if(limitNewSelection>0 && !onComodin){
                    onComodin = true;
                    showComodin();
                    for(int i = 0; i < mazes.length; i++)
                        mazes[i].setColor(1, 1,1, 1);
                }
            }
        });
        RunnableAction run = Actions.run(new Runnable() {
            @Override
            public void run() {
                randCard.setColor(Color.GRAY);
                for(int i = 0; i < 10; i++){
                    if(i != cardI)
                        randCards[i].addAction(Actions.sequence(Actions.parallel(Actions.moveTo(860, -250, 0.8f), Actions.sizeTo(200, 250, 0.8f))));
                }
            }
        });
        float cardX = 1710;
        if(onComodin && limitNewSelection>0){
            Gdx.app.log("LimitSelection", "Entre");
            if(selectedCards.length == 2){
                cardX = 1500;
            }else if(selectedCards.length == 3){
                if(selectedCards[1] == null)
                    cardX = 1500;
                else
                    cardX = 1290;
            }
        }
        randCard.addAction(Actions.sequence(Actions.parallel(Actions.moveTo(cardX, 10, 0.8f), Actions.sizeTo(200, 250, 0.8f)), run, run2));
    }

    public void showText(){
        final RunnableAction run2 = Actions.run(new Runnable() {
            @Override
            public void run(){
                title1.addAction(Actions.moveTo(-1920, 440, 1));
                title2.addAction(Actions.moveTo(0, -title2.getHeight(), 1));
            }
        });
        RunnableAction run = Actions.run(new Runnable() {
            @Override
            public void run(){
                title2.addAction(Actions.sequence(Actions.moveTo(960-title2.getWidth()/2, title1.getY()-title2.getHeight(), 1), Actions.delay(0.5f), run2));
            }
        });
        title1.addAction(Actions.sequence(Actions.moveTo(0, 440, 1), Actions.delay(0.5f), run));
    }

    public void spinRoulette2(float delta){
        if(currentTimeRoulette2 < 10){
            if(timeRoulette2 > 0){
                timeRoulette2 -= speedRoulette2*delta;
            }else{
                current.setBounds(current.getX()+20, current.getY()+20,304, 500);
                currentTimeRoulette2 += 0.1f;
                timeRoulette2 = currentTimeRoulette2;
                if(indexRoulette < 4)
                    indexRoulette++;
                else
                    indexRoulette = 0;
                current = roulette2[indexRoulette];
                current.setBounds(current.getX()-20, current.getY()-20,344, 540);
                hand.setPosition(current.getX()+172, current.getY()-200);
                current.setZIndex(1000);
                hand.setZIndex(1001);
            }
        }else{
            Gdx.app.log("Ruleta", String.valueOf(indexRoulette));
            current = roulette2[4];
            spining2 = false;
        }
    }

    @Override
    public void show() {

    }
}

