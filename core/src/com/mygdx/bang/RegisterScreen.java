package com.mygdx.bang;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;

import java.util.regex.Pattern;

public class RegisterScreen extends ScreenAdapter{
    BANG game;
    Stage stage;
    OrthographicCamera camera;

    Image background;
    Array<Image> stars1, stars2, stars3, stars4;
    MovingImage comet, asteroid, satelite, alien;

    Image signText, nameBox, mailBox, userBox;
    TextField name, mail, user;
    SelectBox<String> dayDate, monthDate, yearDate, genero, educacion, ocupacion, pregunta;
    ImageButton signUp, backButt;
    private Label onlineMessage;
    int currentData = 1;

    String nameS, userS, mailS, date;

    boolean back = false;
    boolean nextScreen = false, nextScreen2 = true;

    Image blackTexture;

    String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+ "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z" + "A-Z]{2,7}$";

    RegisterScreen(final BANG g, MenuScreen menu){
        game = g;
        camera = new OrthographicCamera(1080, 1920);
        stage = menu.stage;

        blackTexture = game.transitionText;

        background = menu.background;
        stars1 = menu.stars1;
        stars2 = menu.stars2;
        stars3 = menu.stars3;
        stars4 = menu.stars4;
        comet = menu.comet;
        asteroid = menu.asteroid;
        satelite = menu.satelite;
        alien = menu.alien;

        BitmapFont font = new BitmapFont(Gdx.files.internal("delius.fnt"));
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.WHITE);

        onlineMessage = new Label("", labelStyle);
        onlineMessage.setWrap(true);
        onlineMessage.setBounds(0, 1270, 1080, 400);
        onlineMessage.setAlignment(Align.center);

        TextField.TextFieldStyle fieldStyle = new TextField.TextFieldStyle();
        fieldStyle.font = font;
        fieldStyle.fontColor = Color.WHITE;

        nameBox = new Image(new Texture("name.png"));
        nameBox.setPosition(134.534f, 1134);
        name = new TextField("Nombre", fieldStyle);
        name.setBounds(314.534f, 1134, 640, 122);
        name.setMaxLength(15);
        name.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                if(name.getText().equals("Nombre"))
                    name.setText("");
                return  true;
            }
        });
        userBox = new Image(new Texture("userName2.png"));
        userBox.setPosition(134.534f, 962.75f);
        user = new TextField("Usuario", fieldStyle);
        user.setBounds(314.534f, 962.75f, 640, 122);
        user.setMaxLength(15);
        user.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                if(user.getText().equals("Usuario"))
                    user.setText("");
                return  true;
            }
        });
        mailBox = new Image(new Texture("mail.png"));
        mailBox.setPosition(134.534f, 792);
        mail = new TextField("Correo", fieldStyle);
        mail.setBounds(314.534f, 792, 640, 122);
        mail.setMaxLength(20);
        mail.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                if(mail.getText().equals("Correo"))
                    mail.setText("");
                return  true;
            }
        });

        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(0, 0.0745f, 0.10f, 1);
        pixmap.fill();
        Texture texture = new Texture(pixmap);
        pixmap.setColor(0, 0.0745f, 0.26f, 1);
        pixmap.fill();
        Texture texture2 = new Texture(pixmap);
        List.ListStyle listStyle = new List.ListStyle();
        listStyle.background = new TextureRegionDrawable(new TextureRegion(texture));
        listStyle.font = font;
        listStyle.fontColorSelected = Color.WHITE;
        listStyle.fontColorUnselected = Color.WHITE;
        listStyle.selection = new TextureRegionDrawable(new TextureRegion(texture2));
        ScrollPane.ScrollPaneStyle scrollPaneStyle = new ScrollPane.ScrollPaneStyle();
        //scrollPaneStyle.background = new TextureRegionDrawable(new TextureRegion(new Texture("badlogic.jpg")));
        SelectBox.SelectBoxStyle selectBoxStyle = new SelectBox.SelectBoxStyle();
        selectBoxStyle.background = new TextureRegionDrawable(new TextureRegion(new Texture("name.png")));
        selectBoxStyle.backgroundOpen = new TextureRegionDrawable(new TextureRegion(new Texture("name.png")));
        selectBoxStyle.font = font;
        selectBoxStyle.fontColor = Color.WHITE;
        selectBoxStyle.listStyle = listStyle;
        selectBoxStyle.scrollStyle = scrollPaneStyle;

        dayDate = new SelectBox<String>(selectBoxStyle);
        dayDate.setPosition(134.534f, 622.25f);
//        dayDate.getStyle().font.getData().setScale(3);
        dayDate.setItems("---------", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
                "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31");
        dayDate.setSelected("---------");
        dayDate.setSize(210, 132);
        dayDate.getList().setY(700);
        dayDate.setMaxListCount(12);
        dayDate.getStyle().listStyle.selection.setLeftWidth(40);
        dayDate.getStyle().background.setLeftWidth(40);
        dayDate.getStyle().backgroundOpen.setLeftWidth(40);
        monthDate = new SelectBox<String>(selectBoxStyle);
        monthDate.setPosition(358.534f, 622.25f);
        //monthDate.getStyle().font.getData().setScale(3);
        monthDate.setItems("---------", "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre",
                "Noviembre", "Diciembre");
        monthDate.setSelected("---------");
        monthDate.setSize(210, 132);
        yearDate = new SelectBox<String>(selectBoxStyle);
        yearDate.setPosition(588.534f, 622.25f);
        //yearDate.getStyle().font.getData().setScale(3);
        yearDate.setItems("---------", "2001", "2000", "1999", "1998", "1997", "1996", "1995",
                "1994", "1993", "1992", "1991", "1990", "1989", "1988", "1987", "1986", "1985", "1984",
                "1983", "1982", "1981", "1980", "1979", "1978", "1977", "1976", "1975", "1974", "1973",
                "1972", "1971", "1970", "1969", "1968", "1967", "1966", "1965", "1964", "1963", "1962",
                "1961", "1960", "1959", "1958", "1957", "1956", "1955", "1954", "1953", "1952", "1951");
        yearDate.setSelected("---------");
        yearDate.setSize(210, 132);
        yearDate.setMaxListCount(12);

        genero = new SelectBox<String>(selectBoxStyle);
        genero.setPosition(134.534f, 1134);
        //genero.getStyle().font.getData().setScale(3);
        genero.setItems("---------", "Masculino", "Femenino", "No selecciona");
        genero.setSelected("---------");
        genero.setSize(829, 132);
        genero.setMaxListCount(12);
        genero.setVisible(false);
        educacion = new SelectBox<String>(selectBoxStyle);
        educacion.setPosition(134.534f, 962.75f);
        //educacion.getStyle().font.getData().setScale(3);
        educacion.setItems("---------", "Secundaria incompleta", "Secundaria", "Universitaria incompleta", "Universitaria",
                "Licenciatura", "Master", "Doctorado", "Otro");
        educacion.setSelected("---------");
        educacion.setSize(829, 132);
        educacion.setMaxListCount(12);
        educacion.setVisible(false);
        ocupacion = new SelectBox<String>(selectBoxStyle);
        ocupacion.setPosition(134.534f, 792);
        //ocupacion.getStyle().font.getData().setScale(3);
        ocupacion.setItems("---------", "Contabilidad", "Administración", "Agricultura", "Diseño creativo", "Educación", "Ingeniería",
                "Finanzas", "Profesional de la salud", "Recursos humanos", "Tecnología de la información", "Legal", "Mantenimiento",
                "Mercadotecnia", "Seervicio de protección y militancia", "Investigación y desarrollo", "Servicios", "Transporte", "Otro");
        ocupacion.setSelected("---------");
        ocupacion.setSize(829, 132);
        ocupacion.setMaxListCount(12);
        ocupacion.setVisible(false);
        pregunta = new SelectBox<String>(selectBoxStyle);
        pregunta.setPosition(134.534f, 622.25f);
        //pregunta.getStyle().font.getData().setScale(3);
        pregunta.setItems("---------", "Amigos y/o compañeros de trabajo", "App store o play store", "Redes sociales (ej: Facebook)", "Búsqueda (ej: Google)");
        pregunta.setSelected("---------");
        pregunta.setSize(829, 132);
        pregunta.setMaxListCount(12);
        pregunta.setVisible(false);

        //signUp = new ImageButton(signSkin.getDrawable("btnSignUp"), signSkin.getDrawable("btnSignDown"));
        signUp = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("aceptarUp.png"))));
        signUp.setPosition(313.139f, 211.321f);
        signUp.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                return  true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                    if(currentData == 1){
                        userS = user.getText().trim();
                        user.setText(userS);
                        nameS = name.getText().trim();
                        name.setText(nameS);
                        mailS = mail.getText().trim();
                        mail.setText(mailS);
                        if(!userS.equals("Usuario") && !nameS.equals("Nombre") && !userS.isEmpty() && !nameS.isEmpty() && !mailS.isEmpty() && !dayDate.getSelected().equals("---------") && !monthDate.getSelected().equals("---------") && !yearDate.getSelected().equals("---------")){
                            Pattern pat = Pattern.compile(emailRegex);
                            if(pat.matcher(mailS).matches()){
                                game.checkUser(userS);
                            }else{
                                onlineMessage.setText("El email registrado no es valido");
                            }
                        }else{
                            onlineMessage.setText("Todos los campos deben ser llenados");
                        }
                    }else if(currentData == 2){
                        if(!genero.getSelected().equals("---------") && !educacion.getSelected().equals("---------") && !ocupacion.getSelected().equals("---------") && !pregunta.getSelected().equals("---------")){
                            game.addUser(userS, nameS, mailS, date, genero.getSelected(), educacion.getSelected(), ocupacion.getSelected(), pregunta.getSelected());
                        }else{
                            onlineMessage.setText("Todos los campos deben ser llenados");
                        }
                    }
            }
        });
        backButt = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("backUp.png"))));
        backButt.setPosition(313.139f, 40);
        backButt.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                back = true;
                showLoad("Menu");
            }
        });

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0, 0.0745f, 0.13f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();

        comet.update(delta);
        asteroid.update(delta);
        satelite.update(delta);
        alien.update(delta);

        if(game.textOnlineActive == 2){
            if(game.textoOnline.equals("No existe")){
                String month = monthDate.getSelected();
                if(month.equals("Enero"))
                    month = "01";
                else if(month.equals("Febrero"))
                    month = "02";
                else if(month.equals("Marzo"))
                    month = "03";
                else if(month.equals("Abril"))
                    month = "04";
                else if(month.equals("Mayo"))
                    month = "05";
                else if(month.equals("Junio"))
                    month = "06";
                else if(month.equals("Julio"))
                    month = "07";
                else if(month.equals("Agosto"))
                    month = "08";
                else if(month.equals("Septiembre"))
                    month = "09";
                else if(month.equals("Octubre"))
                    month = "10";
                else if(month.equals("Noviembre"))
                    month = "11";
                else if(month.equals("Diciembre"))
                    month = "12";
                date = yearDate.getSelected()+"-"+month+"-"+dayDate.getSelected();
                Gdx.app.log("WebTexto", date);

                educacion.setVisible(true);
                genero.setVisible(true);
                ocupacion.setVisible(true);
                pregunta.setVisible(true);
                nameBox.setVisible(false);
                name.setVisible(false);
                userBox.setVisible(false);
                user.setVisible(false);
                mailBox.setVisible(false);
                mail.setVisible(false);
                dayDate.setVisible(false);
                monthDate.setVisible(false);
                yearDate.setVisible(false);
                currentData++;
            }else if(game.textoOnline.equals("Existe")){
                onlineMessage.setText("El usuario ya existe.");
            }else if(game.textoOnline.equals("Registrado")){
                showLoad("Menu");
            }else{
                onlineMessage.setText("El usuario no pudo registrarse, intentelo de nuevo.");
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
                            if(!back){
                                game.setScreen(new MenuScreen(game, false));
                            }else{
                                game.setScreen(new MenuScreen(game, true));
                            }
                        }
                    })));
                    nextScreen2 = false;
                }
            }
        }

        stage.act(delta);
        stage.draw();
    }

    public void show(){
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

        //stage.addActor(signText);
        stage.addActor(onlineMessage);
        stage.addActor(nameBox);
        stage.addActor(name);
        stage.addActor(mailBox);
        stage.addActor(mail);
        stage.addActor(userBox);
        stage.addActor(user);
        stage.addActor(signUp);
        stage.addActor(backButt);
        stage.addActor(dayDate);
        stage.addActor(monthDate);
        stage.addActor(yearDate);
        stage.addActor(genero);
        stage.addActor(educacion);
        stage.addActor(ocupacion);
        stage.addActor(pregunta);
        RunnableAction run = Actions.run(new Runnable(){
            @Override
            public void run(){
                blackTexture.setVisible(false);
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
        stage.clear();
    }

    public void showLoad(String asset){
        nextScreen = true;
        game.loadAssets(asset);
        blackTexture.setColor(0, 0, 0, 1);
        blackTexture.setZIndex(1000);
        blackTexture.setVisible(true);
        game.backLoad.setZIndex(1001);
        game.backLoad.setVisible(true);
        game.barLoad.setZIndex(1002);
        game.barLoad.setVisible(true);
        game.endBarLoad.setZIndex(1003);
        game.endBarLoad.setVisible(true);
    }
}
