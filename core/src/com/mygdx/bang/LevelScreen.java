package com.mygdx.bang;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class LevelScreen extends ScreenAdapter{
    BANG game;
    FitViewport viewport;
    OrthographicCamera camera;
    SpriteBatch batch;
    Stage stage;

    boolean showTutorials = false;

    AssetManager assetManager;

    DataJson dataUser;
    float experiencia = 0;
    int currentScene = 0;
    int currentMaze = -1;
    int currentNeed = 0;

    Sound closeBag;

    BitmapFont titleDialogFont, dialogFont, levelFont;
    Skin skinCharacters;

    Image blackTexture, blackTextureCards;

    Image musicButt, soundButt;

    //barra de experiencia
    Image rankBar1, rankBar2, rankBar3;
    Label rankNumber;

    //fondo
    Image background;
    MovingImage comet, asteroid, satelite, alien;
    Array<Image> stars1, stars2, stars3, stars4;

    MenuCards menuCard;

    //dialogos
    Image character, dialogBox, nextButt, backButt;
    Label dialog, title;
    Table dialogT;

    CardItem card1, card2, card3;

    //Emergente
    Image helpBox;
    Label helpLbl;
    EmergentBox emergentBox;

    //Ayudas
    HelpImage[] helpsImages;
    Helps[] helps;

    Image textComodin, firework1, firework2, firework3, firework4, firework5, firework6, gift, numberComodin, numberComodin2;

    String[] charactersNames = {"Crispi", "Cesia", "Cori", "Carmel", "Cristal"};
    String[] planetNames = {"Vorann", "Dirvon", "Norali", "Ventio", "Inaria"};
    String[] auxTexts2 = {"Transporte", "Infraestructura", "Equipos",
            "Lineamientos corporativos", "Salario", "Clima organizacional",
            "Articulación", "Cultura Organizacional", "Comunicación",
            "Proyección profesional", "Bienestar", "Reconocimiento",
            "Objetivos personales", "Familia", "Aprendizaje"
    };
    //Cartas
    String[][] tarjetas = {{"El cielo es un vecindario", "Japón", "Humanice cualquier cosa", "Empieza por un garaje", "¿Qué pasaría si…?", "Pon otros usos", "Dale un vuelco a tu rutina", "Lego", "+1", "+2"},
            {"Quita las partes importantes", "Piensa cosas imposibles de hacer", "Cambia de roles", "Great Place To Work?", "Haz trocitos y después júntalos", "¿Qué soñaste ayer?", "Cuantas manos y cabezas sean necesarias", "Modifica", "+1", "Duro como una roca, flexible como…"},
            {"Piensa como un lobo", "Combina lo incombinable", "Corta una conexión", "Elimina, Reduce, Incrementa y crea", "Investiga tres tendencias en el área humana", "Observa de cerca los detalles más vergonzosos y amplíalos", "Sé aburrido", "Adapta", "+1", "+2"},
            {"Haz una lista de diez cosas que podrías hacer y haz lo último en la lista", "Elimina", "¿Qué está haciendo el sector de alimentos?", "Una sencilla solución, dos difíciles soluciones", "Piensa como Walt Disney", "Cambia de roles", "Japón", "Toma un descanso", "+1", "Mira el lado oscuro de la historia"},
            {"Singapur", "Busca un libro y lee su primer párrafo", "Desordénalo todo", "Papel", "Piensa como Steve Jobs", "¿Qué haría tu mejor amigo?", "Juega el juego", "Haz cosas imperfectas", "Déjà vu", "+1"}};

    Color[] colorMazes = {new Color(0.3294f, 0.5569f, 0.5882f, 1), new Color(0.8196f, 0.4471f, 0.4235f, 1), new Color(0.5020f, 0.7137f, 0.5333f, 1), new Color(0.9216f, 0.7686f, 0.3176f, 1), new Color(0.2902f, 0.298f, 0.3961f, 1)};
    Color[] colorTextMazes = {new Color(0.9216f, 0.7686f, 0.3176f, 1), new Color(0.2902f, 0.298f, 0.3961f, 1), new Color(0.8196f, 0.4471f, 0.4235f, 1), new Color(0.3294f, 0.5569f, 0.5882f, 1), new Color(0.5020f, 0.7137f, 0.5333f, 1)};

    //Escenas
    Scene[] scenes;
    //Steve Sanders
    String[][] dialogs = {{"0", "Captain", "Buen día agente Sanders, hemos recibido una transmisión proveniente de la galaxia Novalium.\n"+
            "Ellos requieren de nuestra ayuda como agentes de innovación, pero no sabemos nada más, el mensaje llegó incompleto."},
            {"1", "Captain", "Aunque sé que aún estas en entrenamiento, he decidido enviarte a esta misión.\n"+
                    "Estoy seguro será una excelente oportunidad para que pongas a prueba tus habilidades y acumules experiencia."},
            {"2", "Captain", "Calma, no estarás solo, yo te estaré guiando en tu misión.\n"+
                    "¡INICIEMOS!, toma tu nave y dirígete a la galaxia Novalium."},
            {"5", "Captain", "Ya estás cerca de las coordenadas, por eso es importante que antes de llegar, entiendas de qué se "+
                    "trata tu misión."},
            {"6", "Captain", "Tu deber es completar un Bang. Para ello debes pasar por 3 fases.\nEmpiezas con \"sólido\": allí deberás "+
                    "hallar el planeta que necesita de nuestra ayuda. Ya casi llegas a la galaxia, así que, basta de charlas ¡vamos!"},
            {"13", "Captain", "¡Que bien!, parece que has llegado a la zona donde se originó el mensaje. Al parecer todos estos planetas necesitan"+
                    " de nuestro apoyo, por lo que tendrás que explorar la zona y decidir a cual vas a ayudar."},
            {"17", "Captain", "¡Genial!, así que vas a ayudar a los habitantes del planeta ***Planet***.\n"+
                    "Dada la importancia de esta misión, he decidido que tengas la ayuda de uno de los integrantes de los 5c, "+
                    "el comando de innovación más hábil de todo el universo.\n"},
            {"20", "Alien", "¡Hola!, veo que lograron recibir mi mensaje, estaba preocupado que se perdiera la transmisión, dado que" +
                    " nuestra señal de envío es bastante débil.\nEste tan sólo es uno de nuestros problemas... en fin, ya te contaré más" +
                    " detalles en unos minutos..."},
            {"25", "Alien", "Veras, nuestro planeta está pasando por una crisis y nuestros centros de innovación están teniendo inconvenientes" +
                    " en la generación de nuevas ideas, requerimos de tu ayuda para proponer nuevas soluciones a nuestros problemas."},
            {"26", "Alien", "A continuación te mostraré los 3 principales problemas que nos estan afectando, elige uno para que comencemos a trabajar en su solución."},
            {"29", "Captain", "Veo que ya tienes un tema en el que trabajar; no olvides que ahora entrarás a la segunda fase: "+
                    "\"gaseosa\", donde deberas usar tu creatividad y ayudar a los habitantes del planeta ***Planet*** a pensar en soluciones que se salgan del esquema."},
            {"35", "Captain", "Estás en lo cierto ***Nombre***. ¡Ah, casi se me olvida!, las cartas que elijas se irán guardando a medida que "+
                    "las selecciones. Es importante que estén en tu inventario para que puedas acudir a ellas cuando las necesites."},
            {"39", "Captain", "Si tienes una o varias ideas, utiliza el sistema de ideación para guardarlas todas.\nRecuerda que toda idea"+
                    " es válida, nunca se sabe cuando una aparente pequeña idea, puede generar grandes cambios."},
            {"40", "Captain", "Por cierto, cuando tengas todas tus ideas, selecciona la que más te gusta para continuar con tu misión.\n"+
                    "El sistema de ideación guardará las demás, para que puedas retomarlas en otro momento."},
            {"45", "Captain", "Me complace lo lejos que has llegado, sabía que no me equivocaba contigo. La siguiente es la última fase: "+
                    "\"líquida\", aquí deberás mostrarle a los habitantes del planeta cómo piensas hacer realidad tu solución."},
            {"46", "Captain", "Construye un storyboard que explique tu solución a los habitantes del planeta; podrás apoyarte de"+
                    " las viñetas y las herramientas gráficas.\nDesata tu imaginación y piensa en los detalles, entre más fragmentes" +
                    " la información, será más facil de entender.\n¡Manos a la obra agente!"},
            {"56", "Alien", "No sé como agradecerte agente, mira lo mucho que tú y tus ideas han ayudado a nuestro planeta.\nHas sido" +
                    " fuente de inspiración para nuestro centro de innovación, ellos han aprendido mucho de tí.\n\n¡Muchas gracias!"},
            {"57", "Captain", "¡Vaya!, que buen trabajo sabía que  lo lograrías, has obtenido mucha experiencia; si continuas así llegarás" +
                    " muy lejos.\nEstoy seguro nos veremos en próximas misiones, cambio y fuera."}
    };
    String[][][] especials = {
            {{"18"}, {"Crispi", "¡Gracias capitán!\n Hola agente.... mi nombre es Cris...pi. Yo te estaré guiando durante todo el recorrido.\n"+
                    "Algunas veces soy un poco tímido, lo sé, sin embargo esto no será impedimento para ayudarte en todo lo que necesites."},
                    {"Cesia", "Excelente capitán, hace muy bien su trabajo.\n ¡Buenas, buenas!, yo soy Doña Cesia, "+
                            "no se le olvide el Doña por favor.\nYo le ayudaré en todo lo que necesite. Soy clara y concisa, esto le facilitará el "+
                            "entendimiento de las fases"},
                    {"Cori", "Muchas gracias capitán, es usted muy gentil.\nHola, ¿cómo estas?. Que bien es estar en contacto contigo."+
                            "Estoy aquí para guiarte por todas las fases; gracias por elegir mi planeta. Ya me caes bien agente."},
                    {"Carmel", "Si mi capitán, gracias.\nHola agente, estoy para ayudarte. Soy muy servicial, así que para mí no será problema "+
                            "ser tu guía por todas las fases."},
                    {"Cristal", "Gracias capitán.\nHola agente, soy experta en temas de innovación y creatividad; por esto será para mi un placer"+
                            " enseñarte todo lo que necesites."}},
            {{"34"}, {"Crispi", "Hola, otra vez yo. Con la ayuda de una de las cartas de mi baraja, podrás inspirarte para idear una"+
                    " solución creativa; entre más cartas tengas, más ideas se te ocurrirán. ¡Mucha suerte!"},
                    {"Cesia", "Agente debe escoger una carta de mi mazo, para que pueda... ¿cómo es que le dicen?\n¡Ah, si!,"+
                            " \"inspirarse\". Entre más cartas tenga más ideas se le ocurrirán. ¡Buena suerte!, creo en el fondo, MUY en el"+
                            " fondo, que lo logrará."},
                    {"Cori", "Mi agente preferido, llegó una de las partes más ¡divertidas!\nDebes escoger una de las cartas de mi mazo"+
                            " para que estimules tu creatividad y fluyan TODAS tus ideas. Entre más cartas tengas, más creativa se pone la "+
                            "situación.\n¡Mucha suerte!, confío en ti."},
                    {"Carmel", "Agente, creo que nos estamos entendiendo a la perfección como equipo de trabajo.\nAhora lo que debes hacer "+
                            "es escoger una carta de mi baraja, para que puedas inspirarte e imaginar algo creativo. Entre más cartas tengas,"+
                            " más ideas se te ocurrirán.\n¡Éxitos!"},
                    {"Cristal", "Al parecer vas muy bien agente, pero aún tienes más por dar. Recuerda que aquí, debes escoger una carta de"+
                            " mi baraja para que te inspires y estructures una buena idea. Entre más cartas tengas, más ideas podrás construir.\n"+
                            "No desfallezcas, TIENES que ser el mejor."}}
    };
    String[][] toClose = {{"3", "Dialog"},
            {"4", "Rocket"},
            {"7", "Dialog", "Phase"},
            {"14", "Dialog"},
            {"19", "Dialog"},
            {"22", "Dialog", "Selection1"},
            {"27", "Dialog"},
            {"28", "Dialog", "Selection2"},
            {"31", "Dialog", "Phase"},
            {"36", "Dialog"},
            {"37", "Mazes"},
            {"41", "Dialog"},
            {"44", "Ideas"},
            {"47", "Dialog", "Phase"},
            {"50", "Dialog"},
            {"52", "Storyboard"},
            {"58", "Dialog"},
            {"59", "WinNeed"}};
    String[][] toClose2 = {
            {"1", "Phase"},
            {"4", "Rocket"},
            {"7", "Selection1"},
            {"11", "Selection2"},
            {"12", "Phase"},
            {"18", "Mazes"},
            {"22", "Ideas"},
            {"24", "Phase"},
            {"28", "Storyboard"},
            {"35", "WinNeed"}
    };
    String[][] scenes2 = {{"0", "Dialog", "0", "0"},
            {"1", "Dialog", "0", "0"},
            {"2", "Dialog", "0", "0"},
            {"3", "Rocket", "0", "1"},
            {"4", "Phase", "0", "1"},
            {"5", "Dialog", "0", "0"},
            {"6", "Dialog", "0", "0"},
            {"7", "TransitionIn", "0", "1"},
            {"8", "Selection1", "0", "0"},
            {"9", "Next", "0", "0"},
            {"10", "TransitionOut", "0", "0"},
            {"11", "Close", "0", "0"},
            {"12", "Next", "0", "0"},
            {"13", "Dialog", "0", "0"},
            {"14", "Close", "0", "1"},
            {"15", "Next", "0", "0"},
            {"16", "ShowHelp", "0", "0"},
            {"17", "Dialog", "0", "0"},
            {"18", "Dialog", "1", "0"},
            {"19", "RocketPlanet", "0", "1"},
            {"20", "Dialog", "0", "0"},
            {"21", "TransitionIn", "0", "0"},
            {"22", "Selection2", "0", "1"},
            {"23", "Next", "0", "0"},
            {"24", "TransitionOut", "0", "0"},
            {"25", "Dialog", "0", "0"},
            {"26", "Dialog", "0", "0"},
            {"27","Close", "0", "1"},
            {"28", "Phase", "0", "1"},
            {"29", "Dialog", "0", "0"},
            {"30", "TransitionIn", "0", "0"},
            {"31", "Mazes", "0", "1"},
            {"32", "Next", "0", "0"},
            {"33", "TransitionOut", "0", "0"},
            {"34", "Dialog", "1", "0"},
            {"35", "Dialog", "0", "0"},
            {"36", "Close", "0", "1"},
            {"37", "Ideas", "0", "1"},
            {"38", "Next", "0", "0"},
            {"39", "Dialog", "0", "0"},
            {"40", "Dialog", "0", "0"},
            {"41", "Close", "0", "1"},
            {"42", "Next", "0", "0"},
            {"43", "ShowHelp", "0", "0"},
            {"44", "Phase", "0", "1"},
            {"45", "Dialog", "0", "0"},
            {"46", "Dialog", "0", "0"},
            {"47", "EmergentStory", "0", "1"},
            {"48", "TransitionIn", "0", "0"},
            {"49", "Storyboard", "0", "0"},
            {"50", "TransitionOut", "0", "1"},
            {"51", "Close", "0", "0"},
            {"52", "TransitionIn", "0", "1"},
            {"53", "WinNeed", "0", "0"},
            {"54", "Next", "0", "0"},
            {"55", "TransitionOut", "0", "0"},
            {"56", "Dialog", "0", "0"},
            {"57", "Dialog", "0", "0"},
            {"58", "TransitionIn", "0", "1"},
            {"59", "End", "0", "1"},
            {"60", "Next", "0", "0"},
            {"61", "TransitionOut", "0", "0"},
            {"62", "Close", "0", "0"},
            {"63", "Next", "0", "0"},
            {"64", "EndBegin", "0", "0"},
            {"65", "Win", "0", "0"}
    };

    String[][] scenes3 = {{"0", "Phase", "0", "0"},
            {"1", "TransitionIn", "0", "1"},
            {"2", "TransitionOut", "0", "0"},
            {"3", "Rocket", "0", "0"},
            {"4", "Selection1", "0", "1"},
            {"5", "RocketPlanet", "0", "0"},
            {"6", "TransitionIn", "0", "0"},
            {"7", "Selection2", "0", "1"},
            {"8", "Next", "0", "0"},
            {"9", "TransitionOut", "0", "0"},
            {"10", "Close", "0", "0"},
            {"11", "Phase", "0", "1"},
            {"12", "TransitionIn", "0", "1"},
            {"13", "Mazes", "0", "0"},
            {"14", "Next", "0", "0"},
            {"15", "TransitionOut", "0", "0"},
            {"16", "Close", "0", "0"},
            {"17", "TransitionIn", "0", "0"},
            {"18", "Ideas", "0", "1"},
            {"19", "Next", "0", "0"},
            {"20", "TransitionOut", "0", "0"},
            {"21", "Close", "0", "0"},
            {"22", "Phase", "0", "1"},
            {"23", "EmergentStory", "0", "0"},
            {"24", "TransitionIn", "0", "1"},
            {"25", "Storyboard", "0", "0"},
            {"26", "TransitionOut", "0", "0"},
            {"27", "Close", "0", "0"},
            {"28", "TransitionIn", "0", "1"},
            {"29", "WinNeed", "0", "0"},
            {"30", "Next", "0", "0"},
            {"31", "TransitionOut", "0", "0"},
            {"32", "Close", "0", "0"},
            {"33", "Next", "0", "0"},
            {"34", "TransitionIn", "0", "0"},
            {"35", "End", "0", "1"},
            {"36", "Next", "0", "0"},
            {"37", "TransitionOut", "0", "0"},
            {"38", "EndBegin", "0", "0"},
            {"39", "Win", "0", "0"}
    };

    public void nextCurrentScene(){
        Gdx.app.log("Next", scenes[currentScene].type);

        menuCard.close();
        emergentBox.setVisible2(false);
    }

    public void backScene(){
        Gdx.app.log("Back", String.valueOf(currentScene));
        currentScene -= 2;
        Gdx.app.log("Back", String.valueOf(currentScene));
        closeScene("Dialog");
        nextCurrentScene();
    }

    public void closeScene(String scene){

    }

    public void getHelps(String id){
        int helpI = 0;
        for(int i = 0; i < helps.length; i++){
            if(helps[i].id.equals(id)){

                helpsImages[helpI].setHelp(helps[i].text, helps[i].bounds);
                helpsImages[helpI].help.clearActions();
                helpsImages[helpI].help.addAction(Actions.forever(Actions.sequence(Actions.parallel(Actions.sizeTo(100, 100, 0.7f), Actions.alpha(1, 0.7f)), Actions.delay(0.3f), Actions.parallel(Actions.sizeTo(90, 90, 0.7f), Actions.alpha(0.5f, 0.7f)))));
                //helpsImages[helpI].help.addAction(Actions.forever(Actions.sequence(Actions.alpha(1, 0.7f), Actions.delay(0.3f), Actions.alpha(0.5f, 0.7f))));
                helpsImages[helpI].help.addAction(Actions.forever(Actions.sequence(Actions.moveTo(helpsImages[helpI].help.getX(), helpsImages[helpI].help.getY()+30, 0.2f), Actions.moveTo(helpsImages[helpI].help.getX(), helpsImages[helpI].help.getY()-30, 0.2f))));
                helpsImages[helpI].showHelp(true);
                helpI++;
            }
        }
    }

    public void showEmergent(String help){
        menuCard.close();
        emergentBox.textLabel.setText(help);
        emergentBox.setVisible2(true);
    }

    public void addTransition(){
        RunnableAction run = Actions.run(new Runnable(){
            @Override
            public void run(){
                blackTexture.setVisible(false);
                nextCurrentScene();
            }
        });
        blackTexture.setVisible(true);
        blackTexture.setZIndex(1000);
        blackTexture.setColor(0, 0, 0, 0);
        blackTexture.addAction(Actions.sequence(Actions.fadeIn(1), Actions.delay(0.5f), Actions.fadeOut(1), run));
    }

    public void addTransitionIn(){
        RunnableAction run = Actions.run(new Runnable(){
            @Override
            public void run(){
                nextCurrentScene();
            }
        });
        blackTexture.setVisible(true);
        blackTexture.setZIndex(1000);
        blackTexture.setColor(0, 0, 0, 0);
        blackTexture.addAction(Actions.sequence(Actions.fadeIn(1), run));
    }

    public void addTransitionOut(){
        Gdx.app.log("Out", "Entre");
        RunnableAction run2 = Actions.run(new Runnable(){
            @Override
            public void run(){
                blackTexture.setVisible(false);
                nextCurrentScene();
            }
        });
        blackTexture.setVisible(true);
        blackTexture.setZIndex(1000);
        blackTexture.setColor(0, 0, 0, 1);
        blackTexture.addAction(Actions.sequence(Actions.fadeOut(1), run2));
    }

    public void showLoad(String asset){

    }
}