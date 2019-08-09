package com.mygdx.bang;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.Random;

public class LevelScreen2 extends ScreenAdapter{
    BANG game;
    FitViewport viewport;
    OrthographicCamera camera;
    SpriteBatch batch;
    Stage stage;

    float experiencia = 0;
    int levelUser = 0;

    int currentScene = 0;

    Music backMusic, backMusic2, backMusic3, backWin;
    Sound phaseS, spaceShip, randS, cardShuffle, messageS, closeBag;

    Image blackTexture;
    Image character, dialogBox, nextButt;
    Label dialog, title, level;

    Image background;
    MovingImage comet, asteroid, satelite, alien;
    Array<Image> stars1, stars2, stars3, stars4;

    float timer = 2;
    Image backPhases, solido, gas, liquido;
    boolean changingPhase = false;
    boolean beginPhase = true;
    float maxRotation = 120;

    int spinning = 0;
    Image current;
    float timeRoulette = 0.2f;
    float currentTimeRoulette2 = -1;
    float timeRouletteCont = 0.2f;
    float speedRoulette2 = 40;
    int indexRoulette = 0;

    MenuCards menuCard;

    Image levelBar1, levelBar2, levelBar3;

    Image planet1, planet2, planet3, planet4, planet5, sun, ship;
    Image[] planets;

    Image auxButt;

    Image[] rectangles;
    Image caso1, caso2, caso3;

    boolean comodin = false;
    int comodinCard = 0;
    boolean getRandom = false;
    boolean showMaze = true;
    int endAnim = 0;
    int currentCard;
    int currentMaze = -1;
    private final Image[] mazes;
    TextureRegionDrawable randCardDrawable;
    Skin cardsSkin;
    Image[] randCards;
    int numCards = 0;
    Array<Integer> usedCards[];
    Image card1, card2, card3;

    Image helpBox;
    Label helpLbl;
    EmergentBox emergentBox;

    IdeasScroll scroll;

    /*String[][] tarjetas = {{"+1", "+2", "+1", "+2", "+1", "+2", "+1", "+2", "+1", "+2"},
            {"+1", "+2", "+1", "+2", "+1", "+2", "+1", "+2", "+1", "+2"},
            {"+1", "+2", "+1", "+2", "+1", "+2", "+1", "+2", "+1", "+2"},
            {"+1", "+2", "+1", "+2", "+1", "+2", "+1", "+2", "+1", "+2"},
            {"+1", "+2", "+1", "+2", "+1", "+2", "+1", "+2", "+1", "+2"}};*/
    String[][] tarjetas = {{"a", "b", "c", "d", "e", "f", "g", "h", "+1", "+2"},
    {"a", "b", "c", "d", "e", "f", "g", "h", "+1", "+2"},
    {"a", "b", "c", "d", "e", "f", "g", "h", "+1", "+2"},
    {"a", "b", "c", "d", "e", "f", "g", "h", "+1", "+2"},
    {"a", "b", "c", "d", "e", "f", "g", "h", "+1", "+2"}};
    /*String[][] tarjetas = {{"Singapur", "Busca un libro y lee su primer párrafo", "Desordénalo todo", "Papel", "Piensa como Steve Jobs", "¿Qué haría tu mejor amigo?", "Juega el juego", "+1", "Haz cosas imperfectas", "Déjà vu", "+2"},
    {"Haz una lista de diez cosas que podrías hacer y haz lo último en la lista", "Elimina", "¿Qué está haciendo el sector de alimentos?", "Una sencilla solución, dos dificiles soluciones", "Piensa como Walt Disney", "Cambia de roles", "Japón", "Comodín", "Toma un descanso", "Mira el lado oscuro de la historia", "Personaje"},
    {"Comodin", "Piensa como un lobo", "+2", "Combina lo incombinable", "Corta una conexión", "Elimina, reduce, incrementa y crea", "Investiga tres tendencias en el área humana", "Observa de cerca los detalles más vergonzosos y amplíalos", "Sé aburrido", "Adapta", "Personaje"},
    {"Quita las partes importantes", "Comodin", "Piensa cosas imposibles de hacer", "Cambia de roles", "Great place to work?", "Haz trocitos y después juntalos", "¿Qué soñaste ayer?", "Cuantas manos y cabezas sean necesarias", "Modifica", "Duro como una roca, flexible como....", "Personaje"},
    {"El cielo es un vecindario", "Japón", "Comodin", "+2", "Humanice cualquier cosa", "Empieza por un garaje", "¿Qué pasaría si...?", "Pon otros usos", "Dale un vuelco a tu rutina", "Lego", "Personaje"}};*/

    String[] auxTexts = {"Necesitas desplazarte a tu lugar de trabajo para cumplir con tus responsabilidades. Por eso el transporte debe estar en optimas condiciones para hacerlo. ¿Te gustaría ayudar a los habitantes de este planeta a solucionar sus problemas respecto a este tema?\n¡Selecciónalo!",
            "Como necesitas un techo para dormir, también lo necesitas para trabajar. Los habitantes de este planeta no se sienten satisfechos con su lugar de trabajo. Te gustaría ayudarlos a solucionar sus problemas con la infraestructura?\n¡Selecciónalo!",
            "Para desempeñar tu labor en el trabajo, necesitas usar diferentes equipos, desde computadores hasta herramientas básicas como papel y lápiz. En este planeta existen problemas respecto a sus equipos: te gustaría ayudar a sus habitantes?\n¡Selecciónalo!",
            "En tu lugar de trabajo es indispensable que tengas las normas claras, para evitar confusiones, en tus funciones, etc.\nEsta es una necesidad insatisfecha en este planeta. Tomarás el reto de ayudar a sus habitantes con temas de lineamientos corporativos?\n¡Selecciónalo!",
            "Necesitas sentirte seguro en una compañía, y el salario indudablemente te brinda estabilidad. En este planeta las cosas no andan muy bien con este tema, pues sus habitantes se han quejado demasiado. Tomarás el reto de ayudarlos con los temas de salario?\n¡Selecciónalo!",
            "Si no sientes que tus emociones están alineados con la compañía, probablemente es porque no te sientes feliz de estar trabajando donde lo haces. Esto es más común de lo normal en este planeta: la gente no está realmente motivada. Tomarás el reto de ayudar a sus habitantes con temas de clima organizacional?\n¡Selecciónalo!",
            "Necesitas entender la forma en que se conectan las áreas dentro de la empresa, para dirigirte siempre al lugar indicado.\nDesafortunadamente en este planeta los procesos no son claros y esta es la razón de que pidan tu ayuda, los ayudarás?\n¡Selecciónalo!",
            "Cuando haces parte de un grupo social, de una organización, etc, lo identificas facilmente; porque ya tienes las mismas actitudes creencias y valores. Resulta que este planeta nadie siente que pertenezca a nada, por eso requieren de tu ayuda, los ayudarás?\n¡Selecciónalo!",
            "El ser humano necesita comunicarse por naturaleza, pero también debe hacerlo asertivamente para evitar conflictos o malos entendidos. En este planeta ya han habido bastantes problemas por esta razón. Por eso requieren de tu ayuda, los ayudarás?\n¡Selecciónalo!",
            "Cuando sabes que tienes posibilidad de crecimiento profesional dentro de una compañía, te automotivas porque sabes que de conseguir un ascenso tu autoestima aumenta. Esto no parece saberlo este planeta; por eso buscan agentes que los ayuden. ¿Te animas?\n¡Selecciónalo!",
            "Necesitas sentirte bien en tu jornada laboral. Te gusta tener actividades de esparcimiento y sentirte querido por la compañía. En el planeta de Carmel, no tienen claro que un colaborador feliz y saludable, es a la vez clientes felices. Razón por la cual buscan agentes que les ayuden. ¿Te animas?\n¡Selecciónalo!",
            "Si sientes que valoran tu trabajo y te agradecen por ello; te sentirás motivado y tu rendimiento ira en aumento. Lastimosamente en este planeta, no entienden lo importante que es la gratitud. Te animas a ayudarlos con su problema?\n¡Selecciónalo!",
            "La empresa en la que trabajas debe alinearse con tus objetivos personales. ¿qué sería de la vida de las personas sin sus planes de viajes, de estudios complementarios o de independencia? Este planeta poco ha pensado en esto, por eso contacta a agentes para que lo ayuden, te apuntas?\n¡Selecciónalo!",
            "Siempre y cuando sientas que tu familia le importa a la compañía en la que estas, te vas a sentir importante, pues tienen en cuenta una gran parte de tu vida. Este planeta necesita demostrar más interés por la familia de sus habitantes, por eso contacta agentes para que lo ayuden, te animas?\n¡Selecciónalo!",
            "La mayoría de las veces para sentirte motivado en la empresa, debes sentir que todos los días aprendes algo nuevo. Es por esta razón que el planeta de Cristal contacto a agentes para que la ayuden, te animas?\n¡Selecciónalo!"
    };
    String[] charctersNames = {"Crispi", "Cesia", "Cori", "Carmel", "Cristal"};
    String[] auxTexts2 = {"Equipos", "Transporte", "Infraestructura",
            "Liderazgo", "Estabilidad", "Bienestar",
            "Trabajo colaborativo", "Articulación", "Comunicación",
            "Proyección", "Salario", "Reconocimiento",
            "Objetivos Personales", "Aprendizaje", "Familia"
    };
    Label auxText;

    int caseToSolve = 0;

    Skin skinCharacters;

    String currentIdea = "";
    Image addIdeaButt;
    //Image texture1;
    Scene[] scenes;
    String[][] dialogs = {{"0", "Captain", "Buen día agente \", hemos recibido una transmisión proveniente de la galaxia XXXX.\n"+
            "Ellos requieren de nuestra ayuda como agentes de innovación, pero no sabemos nada más, el mensaje llego incompleto."},
            {"1", "Captain", "Se que aún estas en entrenamiento, sin embargo he decidido enviarte a esta misión.\n"+
                    "Estoy seguro de que es una excelente oportunidad para que pongas a prueba tus habilidades y acumules experiencia."},
            {"2", "Captain", "Calma, no estarás solo, yo te estaré guiando en tu misión.\n"+
                    "¡INICIEMOS!, toma tu nave y dirígete a la galaxia XXXX."},
            {"4", "Captain", "Ya estás cerca de las coordenadas, por eso es importante que antes de llegar, entiendas de qué se " +
                    "trata tu misión."},
            {"5", "Captain", "Tu deber es completar un Bang, y para ello debes pasar por 3 fases.\nEmpiezas con el \"solido\", donde deberas "+
                    "hallar al planeta que necesita de nuestra ayuda. Ya casi llegas a la galaxia, así que, basta de charlas ¡vamos!"},
            {"9", "Captain", "!Que bien!, parece que has llegado a la zona donde se origino el mensaje. Al parecer todos estos planetas necesitan"+
                    " de nuestra ayuda, por lo que tendras que explorar la zona y decidir a que planeta deseas ayudar primero."},
            {"11", "Captain", "!Genial¡, asi que vas a ayudar a los habitantes del planeta XXXX. "+
                    "Dada la importancia de esta misión, he decidido que tengas la ayuda de uno de los integrantes de los 5c, " +
                    "el comando de innovación más habil de todo el universo.\n"},
            {"16", "Alien", "¡Hola!, veo que lograron recibir mi mensaje, estaba preocupado de que se perdiera la transmisión, dado que" +
                    " nuestra señal de envío es bastante débil.\nÉse tan sólo es uno de nuestros problemas... en fin, ya te contaré más" +
                    " detalles en unos minutos..."},
            {"17", "Alien", "Veras, dado que nuestro planeta está pasando por una crisis y nuestros centros de innovación están teniendo inconvenientes" +
                    " en la generación de nuevas ideas, requerimos de tu ayuda para proponer nuevas soluciones a nuestros problemas."},
            {"18", "Alien", "A continuación podras ver los 3 principales problemas de nuestro planeta, elige uno para que comenzemos a trabajar en su solución."},
            {"22", "Captain", "Veo que ya tienes un tema en el que trabajar; no olvides que ahora entrarás a la segunda fase:"+
                    "\"liquida\", donde usaras tu creatividad y ayudarás a los habitantes del planeta XXXX a pensar en soluciones que se salgan del esquema."},
            {"27", "Captain", "Estás en lo cierto ***Nombre***. ¡Ah!, se me olvida, las cartas que elijas se irán guardando a medida que "+
                    "las selecciones. Es importante que estén en tu inventario para que puedas acudir a ellas cuando las necesites."},
            {"31", "Captain", "Si tienes una o varias ideas, utiliza el sistema de ideación para guardarlas todas.\nRecuerda que toda idea"+
                    " es válida, nunca se sabe cuando una aparente pequeña idea, puede generar grandes cambios."},
            {"32", "Captain", "Por cierto, cuando tengas todas tus ideas, selecciona la que más te gusta para continuar con tu misión.\n"+
                    "El sistema de ideación guardará las demás, para que puedas retomarlas en otro momento."},
            {"35", "Captain", "Me complace lo lejos que has llegado, sabía que no me equivocaba contigo. La siguiente es la ultima fase: "+
                    "\"gaseosa\", aquí debes mostrarle a los habitantes del planeta cómo piensas hacer realidad tu solución."},
            {"36", "Captain", "Ahora debes realizar un storyboard, así le explicaras tu solución a los habitantes del planeta con ayuda de"+
                    " las viñetas y las herramientas gráficas.\nDesata tu imaginación y piensa en los detalles, entre más fragmentes" +
                    " la información, será más facil de entender.\n¡Manos a la obra agente!"},
            {"45", "Alien", "No se como agradecerte agente, mira lo mucho que tú y tus ideas han ayudado a nuestro planeta.\nAdemás has sido" +
                    " fuente de inspiración para nuestro centro de innovación, ellos han aprendido mucho de tí.\n\n¡Muchas gracias!"},
            {"46", "Captain", "¡Vaya!, que buen trabajo, sabía que  lo lograrías; has obtenido mucha experiencia y si continuas así llegaras" +
                    " muy lejos.\nYa nos veremos en próximas misiones, cambio y fuera."}
};
    String[][][] especials = {
            {{"12"}, {"Crispi", "!Gracias capitán¡\n Hola agente.... mi nombre es Cris...pi. Yo te estaré guiando durante todo el recorrido.\n"+
            "Algunas veces soy un poco tímido, pero eso no será impedimento para ayudarte en todo lo que necesites."},
            {"Cesia", "Excelente capitán, hace muy bien su trabajo.\n Buenas, buenas, yo soy Doña Cesia, recuerde Doña Cesia, "+
                    "no se le olvide el Doña por favor.\nYo le ayudaré en todo lo que necesite. Soy clara y concisa, lo que le facilitará el"+
                    "entendimiento de las fases"},
            {"Cori", "Muchas gracias capitán, es usted muy gentil.\nHola, ¿cómo estas?. Que bien es estar en contacto contigo."+
                    "Estoy aquí para guiarte por todas las fases; gracias por elegir mi planeta para ayudarlo. Ya me caes bien agente."},
            {"Carmel", "Si mi capitán, gracias.\nHola agente, estoy para ayudarte. Soy muy servicial, así que para mí no será problema "+
                    "ser tu guía por todas las fases."},
            {"Cristal", "Gracias capitán.\nHola agente, soy experta en temas de innovación y creatividad; por esto será para mi un placer"+
                    " enseñarte todo lo que necesites."}},
            {{"26"}, {"Crispi", "Hola, otra vez yo. Con la ayuda de una de las cartas de nuestra baraja, podrás inspirarte para idear una"+
                    " solución creativa; entre más cartas tengas, más ideas se te ocurrirán. ¡Mucha suerte!"},
            {"Cesia", "Agente debe escoger una carta de nuestro mazo, para que pueda... ¿cómo es que le dicen?\n¡Ah, si!,"+
                    " \"inspirarse\". Entre más cartas tenga más ideas se le ocurrirán. ¡Buena suerte!, creo en el fondo, MUY en el"+
                    " fondo, que lo logrará."},
            {"Cori", "Mi agente preferido, llegó una de las partes más ¡divertidas!\nDebes escoger una de las cartas de nuestro mazo"+
                    " para que estimules tu creatividad y fluyan TODAS tus ideas. Entre más cartas tengas, más creativa se pone la"+
                    "situación.\n¡Mucha suerte!, confio en ti."},
            {"Carmel", "Agente, creo que nos estamos entendiendo a la perfección como equipo de trabajo.\nAhora lo que debes hacer "+
                    "es escoger una carta de nuestra baraja, para que puedas inspirarte e imaginar algo creativo. Entre más cartas tengas,"+
                    " más ideas se te ocurrirán.\n¡Éxitos!"},
            {"Cristal", "Al parecer vas muy bien agente, pero aún tienes más por dar. Recuerda que aquí, debes escoger una carta de"+
                    " nuestra baraja para que te inspires y estructures una buena idea. Entre más cartas saques, más ideas podrás construir.\n"+
                    "No desfallezcas, TIENES que ser el mejor."}}
    };
    String[][] toClose = {{"3", "Dialog"},
            {"6", "Dialog", "Phase"},
            {"7", "Dialog"},
            {"10", "Dialog"},
            {"13", "Dialog", "Selection1"},
            {"19", "Dialog"},
            {"20", "Dialog", "Selection2"},
            {"23", "Dialog", "Phase"},
            {"24", "Dialog"},
            {"28", "Dialog"},
            {"29", "Mazes"},
            {"33", "Dialog"},
            {"34", "Ideas"},
            {"37", "Dialog", "Phase"},
            {"41", "Storyboard"},
            {"47", "Dialog"}};
    String[][] scenes2 = {{"0", "Dialog", "0", "0"},
            {"1", "Dialog", "0", "0"},
            {"2", "Dialog", "0", "0"},
            {"3", "Phase", "0", "1"},
            {"4", "Dialog", "0", "0"},
            {"5", "Dialog", "0", "0"},
            {"6", "Transition", "0", "1"},
            {"7", "Selection1", "0", "1"},
            {"8", "Next", "0", "0"},
            {"9", "Dialog", "0", "0"},
            {"10", "Close", "0", "1"},
            {"11", "Dialog", "0", "0"},
            {"12", "Dialog", "1", "0"},
            {"13", "Transition", "2", "1"},
            {"14", "Selection2", "0", "0"},
            {"15", "Next", "0", "0"},
            {"16", "Dialog", "0", "0"},
            {"17", "Dialog", "0", "0"},
            {"18", "Dialog", "0", "0"},
            {"19","Close", "0", "1"},
            {"20", "Transition", "0", "1"},
            {"21", "Phase", "0", "0"},
            {"22", "Dialog", "0", "0"},
            {"23", "Transition", "0", "1"},
            {"24", "Mazes", "0", "1"},
            {"25", "Next", "0", "0"},
            {"26", "Dialog", "1", "0"},
            {"27", "Dialog", "0", "0"},
            {"28", "Close", "0", "1"},
            {"29", "Ideas", "0", "1"},
            {"30", "Next", "0", "0"},
            {"31", "Dialog", "0", "0"},
            {"32", "Dialog", "0", "0"},
            {"33", "Close", "0", "1"},
            {"34", "Phase", "0", "1"},
            {"35", "Dialog", "0", "0"},
            {"36", "Dialog", "0", "0"},
            {"37", "Transition", "0", "1"},
            {"38", "Storyboard", "0", "0"},
            {"39", "TransitionOut", "0", "0"},
            {"40", "Close", "0", "0"},
            {"41", "TransitionIn", "0", "1"},
            {"42", "Close", "0", "0"},
            {"43", "Next", "0", "0"},
            {"44", "TransitionOut", "0", "0"},
            {"45", "Dialog", "0", "0"},
            {"46", "Dialog", "0", "0"},
            {"47", "Win", "0", "1"}
    };

    HelpImage[] helpsImages;
    Helps[] helps;

    DataJson dataUser;

    MenuTools tools;

    BitmapFont titleDialogFont, dialogFont, levelFont;

    LevelScreen2(BANG _game, MenuScreen menu){
        game = _game;
        camera = new OrthographicCamera(1080, 1920);
        camera.position.set(960, 540, 0);
        viewport = new FitViewport(1080, 1920, camera);
        batch = new SpriteBatch();
        stage = game.stage;

        backMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/happyGuitar.mp3"));
        backMusic.setVolume(0.5f);
        backMusic.setLooping(true);
        backMusic.play();

        backMusic2 = Gdx.audio.newMusic(Gdx.files.internal("sounds/voyager.wav"));
        backMusic2.setVolume(0.3f);
        backMusic2.setLooping(true);
        phaseS = Gdx.audio.newSound(Gdx.files.internal("sounds/changePhase.wav"));
        randS = Gdx.audio.newSound(Gdx.files.internal("sounds/happyRand.wav"));
        cardShuffle = Gdx.audio.newSound(Gdx.files.internal("sounds/cardShuffle.wav"));
        messageS = Gdx.audio.newSound(Gdx.files.internal("sounds/newsTing.wav"));
        closeBag = Gdx.audio.newSound(Gdx.files.internal("sounds/briefcase.wav"));
        spaceShip = Gdx.audio.newSound(Gdx.files.internal("sounds/spaceShip.wav"));

        dialogFont = new BitmapFont(Gdx.files.internal("delius.fnt"));
        dialogFont.getData().padTop = 200;
        titleDialogFont = new BitmapFont(Gdx.files.internal("shadows.fnt"));
        levelFont = new BitmapFont(Gdx.files.internal("shadows2.fnt"));

        //game.dataUser = new DataJson();
        dataUser = game.dataUser;
        /*FileHandle file1 = Gdx.files.local("levelData/data.json");
        boolean fileExist = file1.exists();
        if(fileExist){
            JsonValue oldData = new JsonReader().parse(file1);
            dataUser.setUser(oldData.get(1).asString());
            for(int i = 0; i < oldData.get(0).size; i++){
                dataUser.addIdea(oldData.get(0).get(i).asStringArray());
            }
            for(int i = 0; i < dataUser.getIdeas().size; i++){
                Gdx.app.log("EXMP", "Maze: "+dataUser.getIdeas().get(i)[0]+" Idea: "+dataUser.getIdeas().get(i)[1]);
            }
        }else{
            dataUser.setUser("CUNY");
        }*/

        blackTexture = game.transitionText;
        blackTexture.setColor(0, 0, 0, 1);

        //universeBack();
        //blackTexture = menu.blackTexture;

        background = menu.background;
        stars1 = menu.stars1;
        stars2 = menu.stars2;
        stars3 = menu.stars3;
        stars4 = menu.stars4;
        comet = menu.comet;
        asteroid = menu.asteroid;
        satelite = menu.satelite;
        alien = menu.alien;

        levelBar1 = new Image(new Texture("levelBar1.png"));
        levelBar1.setPosition(30, 1749);
        levelBar2 = new Image(new Texture("levelBar2.png"));
        levelBar2.setPosition(110, 1811);
        levelBar2.setWidth(0);
        levelBar3 = new Image(new Texture("levelBar3.png"));
        levelBar3.setPosition(110, 1811);

        skinCharacters = new Skin();
        skinCharacters.add("Captain", new Texture("captain.png"));
        skinCharacters.add("Crispi", new Texture("crispi.png"));
        skinCharacters.add("Cesia", new Texture("cesia.png"));
        skinCharacters.add("Cori", new Texture("cori.png"));
        skinCharacters.add("Carmel", new Texture("carmel.png"));
        skinCharacters.add("Cristal", new Texture("cristal.png"));
        skinCharacters.add("dialogCaptain", new Texture("dialogCaptain.png"));
        skinCharacters.add("dialogCesia", new Texture("dialogCesia.png"));
        skinCharacters.add("dialogCori", new Texture("dialogCori.png"));
        skinCharacters.add("dialogCarmel", new Texture("dialogCarmel.png"));
        skinCharacters.add("dialogCristal", new Texture("dialogCristal.png"));
        skinCharacters.add("Alien", new Texture("alienChar.png"));
        skinCharacters.add("idea", new Texture("ideaBox.png"));
        skinCharacters.add("n1", new Texture("n1.png"));
        skinCharacters.add("n2", new Texture("n2.png"));
        skinCharacters.add("n3", new Texture("n3.png"));
        skinCharacters.add("n4", new Texture("n1.png"));
        skinCharacters.add("n5", new Texture("n2.png"));
        skinCharacters.add("n6", new Texture("n3.png"));
        skinCharacters.add("n7", new Texture("n1.png"));
        skinCharacters.add("n8", new Texture("n2.png"));
        skinCharacters.add("n9", new Texture("n3.png"));
        skinCharacters.add("n10", new Texture("n1.png"));
        skinCharacters.add("n11", new Texture("n2.png"));
        skinCharacters.add("n12", new Texture("n3.png"));
        skinCharacters.add("n13", new Texture("n1.png"));
        skinCharacters.add("n14", new Texture("n2.png"));
        skinCharacters.add("n15", new Texture("n3.png"));

        scenes = new Scene[scenes2.length];
        for(int i = 0; i < scenes2.length; i++){
            scenes[i] = new Scene(i, scenes2[i][1], Integer.valueOf(scenes2[i][2]), Integer.valueOf(scenes2[i][3]));
            if(scenes2[i][1].equals("Dialog")){
                if(scenes2[i][2].equals("1")){
                    for(int j = 0; j < especials.length; j++){
                        if(especials[j][0][0].equals(scenes2[i][0])){
                            scenes[i].setScene(especials[j]);
                            break;
                        }
                    }
                }else{
                    for(int j = 0; j < dialogs.length; j++){
                        if(dialogs[j][0].equals(scenes2[i][0])){
                            scenes[i].setScene(dialogs[j][1], dialogs[j][2]);
                            break;
                        }
                    }
                }
            }
            if(scenes2[i][3].equals("1")){
                Gdx.app.log("Close", String.valueOf(scenes2[i][0]));
                for(int j = 0; j < toClose.length; j++){
                    if(toClose[j][0].equals(scenes2[i][0])){
                        Gdx.app.log("Close", String.valueOf(toClose[j][0]));
                        scenes[i].setToClose(toClose[j]);
                        break;
                    }
                }
            }
        }

        //menuCard = new MenuCards(this);

        character = new Image(new Texture("captain.png"));
        character.setBounds(78, 1404, 310, 298);
        dialogBox = new Image(new Texture("dialogCaptain.png"));
        dialogBox.setPosition(76, 60);
        nextButt = new Image(new Texture("nextButt.png"));
        nextButt.setPosition(800, 26);
        nextButt.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                if(currentScene == 31){
                    game.addIdeas();
                    setData();
                }
                //game.addIdeasOnline("", new String[][]{});
                nextCurrentScene();
                return true;
            }
        });
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = dialogFont;
        labelStyle.fontColor = Color.WHITE;
        dialog = new Label("Bienvenido a BANG....................", labelStyle);
        dialog.setAlignment(Align.top);
        dialog.setWrap(true);
        dialog.setBounds(135, 547, 842, 842);
        Label.LabelStyle labelStyle2 = new Label.LabelStyle();
        labelStyle2.font = titleDialogFont;
        labelStyle2.fontColor = Color.WHITE;
        title = new Label("Bienvenido a BANG....................", labelStyle2);
        title.setAlignment(Align.center);
        title.setWrap(true);
        title.setBounds(320, 1540, 620, 100);
        level = new Label("0", labelStyle);
        level.setAlignment(Align.center);
        level.setWrap(true);
        level.setBounds(50, 1800, 68, 68);

        backPhases = new Image(new Texture("lineas.png"));
        backPhases.setPosition(55, 475);
        backPhases.setOrigin(485, 485);
        backPhases.setVisible(false);
        solido = new Image(new Texture("solido.png"));
        solido.setOrigin(596, 570);
        float newX = (457*MathUtils.cosDeg(330))+540-498;
        float newY = (457*MathUtils.sinDeg(330))+960-506;
        solido.setPosition(newX, newY);
        solido.setVisible(false);
        gas = new Image(new Texture("gas.png"));
        gas.setOrigin(421, 418);
        float newX2 = (457*MathUtils.cosDeg(90))+540-421;
        float newY2 = (457*MathUtils.sinDeg(90))+960-418;
        gas.setPosition(newX2, newY2);
        gas.setVisible(false);
        liquido = new Image(new Texture("liquido.png"));
        liquido.setOrigin(472, 480);
        float newX3 = (457*MathUtils.cosDeg(210))+540-472;
        float newY3 = (457*MathUtils.sinDeg(210))+960-480;
        liquido.setPosition(newX3, newY3);
        liquido.setVisible(false);

        planet1 = new Image(new Texture("planet1.png"));
        planet1.setPosition(253, 1483);
        planet1.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                currentMaze = 0;
                nextCurrentScene();
                return true;
            }
        });
        planet1.setColor(Color.GRAY);
        planet1.setVisible(false);
        planet2 = new Image(new Texture("planet2.png"));
        planet2.setPosition(455, 1160);
        planet2.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                currentMaze = 1;
                nextCurrentScene();
                return true;
            }
        });
        planet2.setColor(Color.GRAY);
        planet2.setVisible(false);
        planet3 = new Image(new Texture("planet3.png"));
        planet3.setPosition(560, 770);
        planet3.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                currentMaze = 2;
                nextCurrentScene();
                return true;
            }
        });
        planet3.setColor(Color.GRAY);
        planet3.setVisible(false);
        planet4 = new Image(new Texture("planet4.png"));
        planet4.setPosition(537, 363);
        planet4.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                currentMaze = 3;
                nextCurrentScene();
                return true;
            }
        });
        planet4.setColor(Color.GRAY);
        planet4.setVisible(false);
        planet5 = new Image(new Texture("planet5.png"));
        planet5.setPosition(198, 71);
        planet5.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                currentMaze = 4;
                nextCurrentScene();
                return true;
            }
        });
        planet5.setColor(Color.GRAY);
        planet5.setVisible(false);
        planets = new Image[]{planet1, planet2, planet3, planet4, planet5};
        sun = new Image(new Texture("sun.png"));
        sun.setPosition(0, 434);
        sun.setVisible(false);
        ship = new Image(new Texture("ship.png"));
        ship.setBounds(800, 20, 250, 100);
        ship.setVisible(false);

        auxButt = new Image(new Texture("randButt.png"));
        auxButt.setBounds(901, 26, 143, 136);
        auxButt.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                if(currentScene<13){
                    current = planet1;
                    spinning = 1;
                }else{
                    nextCurrentScene();
                }
                return true;
            }
        });
        auxButt.setVisible(false);

        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
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

        caso1 = new Image(new Texture("n1.png"));
        caso1.setBounds(0, 1213, 1080, 480);
        caso1.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                //showAuxText(((currentMaze+1)*3)-2);
                //menuCard.setProblemaText("[#511577]Recuerda que estas ayudando a "+charctersNames[currentMaze]+" en el tema de [#ff0000]"+auxTexts2[((currentMaze+1)*3)-3]);
                nextCurrentScene();
                return true;
            }
        });
        caso1.setVisible(false);
        caso2 = new Image(new Texture("n2.png"));
        caso2.setBounds(0, 643, 1080, 480);
        caso2.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                //showAuxText(((currentMaze+1)*3)-1);
                //menuCard.setProblemaText("[#511577]Recuerda que estas ayudando a "+charctersNames[currentMaze]+" en el tema de [#ff0000]"+auxTexts2[((currentMaze+1)*3)-2]);
                nextCurrentScene();
                return true;
            }
        });
        caso2.setVisible(false);
        caso3 = new Image(new Texture("n3.png"));
        caso3.setBounds(0, 73, 1080, 480);
        caso3.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                //showAuxText(((currentMaze+1)*3));
                //menuCard.setProblemaText("[#511577]Recuerda que estas ayudando a "+charctersNames[currentMaze]+" en el tema de [#ff0000]"+auxTexts2[((currentMaze+1)*3)-1]);
                nextCurrentScene();
                return true;
            }
        });
        caso3.setVisible(false);

        Label.LabelStyle labelStyle3 = new Label.LabelStyle();
        labelStyle3.font = new BitmapFont();
        labelStyle3.fontColor = Color.WHITE;
        auxText = new Label("", labelStyle3);
        auxText.setSize(360, 1920);
        auxText.setFontScale(3.5f);
        auxText.setWrap(true);
        auxText.setAlignment(Align.center);
        auxText.setVisible(false);

        mazes = new Image[5];
        float buttX = 315;
        float buttY = 30;
        final Color[] backsMazes = {Color.PURPLE, Color.YELLOW, Color.GREEN, Color.RED, Color.BLUE};
        final Texture[] backMazes = {new Texture("back1.png"), new Texture("back2.png"), new Texture("back3.png"), new Texture("back4.png"), new Texture("back5.png")};
        for(int i = 0; i < 5; i++){
            /*pixmap.setColor(backsMazes[i]);
            pixmap.fill();*/
            mazes[i] = new Image(backMazes[i]);
            mazes[i].setBounds(buttX, buttY, 450, 600);
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
                    /*if(onComodin)
                        getRandomCard(finalI, 2);
                    else
                        getRandomCard(finalI, 1);*/
                    return true;
                }
            });
            mazes[i].setVisible(false);
            buttX += 470;
            if(i == 0 || i == 2){
                buttY += 630;
                buttX = 60;
            }
        }

        cardsSkin = new Skin();
        for(int i = 0; i < tarjetas.length; i++){
            for(int j = 0; j < tarjetas[i].length; j++){
                cardsSkin.add(tarjetas[i][j], new Texture(tarjetas[i][j]+".png"));
            }
        }
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

        card1 = new Image();
        card1.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                if(card1.getWidth()<664){
                    Vector2 posItem = card1.localToStageCoordinates(new Vector2(0, 0));
                    card1.remove();
                    stage.addActor(card1);
                    card1.setPosition(posItem.x, posItem.y);
                    card1.addAction(Actions.parallel(Actions.moveTo(207.75f, 502.5f, 0.7f), Actions.sizeTo(664.5f, 915, 0.7f)));
                }else{
                    card1.addAction(Actions.sequence(Actions.parallel(Actions.moveTo(1000, 1800, 0.8f), Actions.sizeTo(200, 250, 0.8f)), Actions.run(new Runnable(){
                        @Override
                        public void run(){
                            menuCard.slots.get(3).addActor(card1);
                            card1.setPosition(0, 0);
                            closeBag.play();
                        }
                    })));
                }
                return true;
            }
        });
        card2 = new Image();
        card2.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                if(card2.getWidth()<664){
                    Vector2 posItem = card2.localToStageCoordinates(new Vector2(0, 0));
                    card2.remove();
                    stage.addActor(card2);
                    card2.setPosition(posItem.x, posItem.y);
                    card2.addAction(Actions.parallel(Actions.moveTo(207.75f, 502.5f, 0.7f), Actions.sizeTo(664.5f, 915, 0.7f)));
                }else{
                    card2.addAction(Actions.sequence(Actions.parallel(Actions.moveTo(1000, 1800, 0.8f), Actions.sizeTo(200, 250, 0.8f)), Actions.run(new Runnable(){
                        @Override
                        public void run(){
                            menuCard.slots.get(4).addActor(card2);
                            card2.setPosition(0, 0);
                            closeBag.play();
                        }
                    })));
                }
                return true;
            }
        });
        card3 = new Image();
        card3.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                if(card3.getWidth()<664){
                    Vector2 posItem = card3.localToStageCoordinates(new Vector2(0, 0));
                    card3.remove();
                    stage.addActor(card3);
                    card3.setPosition(posItem.x, posItem.y);
                    card3.addAction(Actions.parallel(Actions.moveTo(207.75f, 502.5f, 0.7f), Actions.sizeTo(664.5f, 915, 0.7f)));
                }else{
                    card3.addAction(Actions.sequence(Actions.parallel(Actions.moveTo(1000, 1800, 0.8f), Actions.sizeTo(200, 250, 0.8f)), Actions.run(new Runnable(){
                        @Override
                        public void run(){
                            menuCard.slots.get(5).addActor(card3);
                            card3.setPosition(0, 0);
                            closeBag.play();
                        }
                    })));
                }
                return true;
            }
        });

        pixmap.setColor(Color.BLACK);
        pixmap.fill();
        helpBox = new Image(new Texture(pixmap));
        helpBox.setBounds(120, 710, 800, 500);
        helpBox.setVisible(false);
        helpLbl = new Label("", labelStyle2);
        helpLbl.setBounds(120, 710, 800, 500);
        helpLbl.setAlignment(Align.center);
        helpLbl.setWrap(true);
        helpLbl.setVisible(false);

        BitmapFont fontEmergent = new BitmapFont(Gdx.files.internal("delius.fnt"));
        fontEmergent.setColor(Color.WHITE);
        emergentBox = new EmergentBox(fontEmergent);
        emergentBox.setVisible2(false);

        //scroll = new IdeasScroll(this);
        scroll.setPosition(100, 210);
        scroll.setVisible(false);

        addIdeaButt = new Image(new Texture("addIdeaButt.png"));
        addIdeaButt.setBounds(760, 10, 300, 300);
        addIdeaButt.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                scroll.showTextArea(stage);
                return true;
            }
        });
        addIdeaButt.setVisible(false);
        FileHandle file = Gdx.files.local("levelData/data.json");
        if(file.exists())
            Gdx.app.log("TextoFile", file.readString());

        helps = new Helps[]{new Helps("Selection1", "Este botón permitira que el capitán pueda elegir por ti, qué planeta debes ayudar en esta misión.", new float[]{775, 50, 292, 161, 664, 314}),
                new Helps("Selection1", "A medida que completes tu BANG, vas a adquirir experiencia, que luego te servira para que apruebes niveles y te certifiques como agente de innovación.", new float[]{570, 1785, 84, 1445, 776, 315}),
                new Helps("Selection2", "paospdfopaskdfopasdofijaisdjfoasijsdfa", new float[]{3, 1600, 107, 1213, 972, 478}),
                new Helps("Selection2", "paospdfopaskdfopasdofijaisdjfoasijsdfa", new float[]{3, 1036, 107, 640, 972, 478}),
                new Helps("Selection2", "paospdfopaskdfopasdofijaisdjfoasijsdfa", new float[]{3, 460, 107, 73, 972, 478}),
                new Helps("Ideas", "Abre un recuadro para que escribas una idea. Ten en cuenta el número de caracteres máximo, esto te ayudará a ser concreto en tus ideas.", new float[]{630, 20, 720, 20, 300, 500})

        };

        Texture help = new Texture("question.png");
        Texture backHelp = new Texture(pixmap);
        helpsImages = new HelpImage[]{new HelpImage(help, backHelp, labelStyle), new HelpImage(help, backHelp, labelStyle), new HelpImage(help, backHelp, labelStyle)};
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
        batch.begin();
        batch.end();

        if(spinning > 0){
            selectRand(spinning);
        }

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void show(){
        stage.addActor(background);
        stage.addActor(menuCard);
        stage.addActor(menuCard.blackTexture);
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
        stage.addActor(backPhases);
        stage.addActor(solido);
        stage.addActor(gas);
        stage.addActor(liquido);
        stage.addActor(character);
        stage.addActor(dialogBox);
        stage.addActor(dialog);
        stage.addActor(title);
        stage.addActor(nextButt);
        stage.addActor(blackTexture);
        for(int i = 0; i < helpsImages.length; i++){
            helpsImages[i].showHelp(false);
            helpsImages[i].add(stage);
        }
        stage.addActor(planet1);
        stage.addActor(planet2);
        stage.addActor(planet3);
        stage.addActor(planet4);
        stage.addActor(planet5);
        stage.addActor(sun);
        stage.addActor(ship);
        stage.addActor(caso1);
        stage.addActor(caso2);
        stage.addActor(caso3);
        for(int i = 0; i < 3; i++){
            stage.addActor(rectangles[i]);
        }
        stage.addActor(auxButt);

        stage.addActor(helpBox);
        stage.addActor(helpLbl);
        stage.addActor(emergentBox.blackTexture);
        stage.addActor(emergentBox);

        stage.addActor(scroll);
        stage.addActor(addIdeaButt);
        //stage.addActor(texture1);

        blackTexture.setVisible(true);
        RunnableAction run = Actions.run(new Runnable(){
            @Override
            public void run(){
                blackTexture.setVisible(false);
                nextCurrentScene();
            }
        });
        blackTexture.addAction(Actions.sequence(Actions.fadeOut(1), run));
    }

    @Override
    public void resize(int width, int height){
        //viewport.update(width, height, true);
    }

    public void nextCurrentScene(){
        Gdx.app.log("Next", scenes[currentScene].type);

        menuCard.close();
        emergentBox.setVisible2(false);

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
                    title.setText(scenes[currentScene].actor);
                    dialog.setText(newText);
                }else{
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
            }
            dialog.setZIndex(1000);
            dialog.setVisible(true);
            title.setVisible(true);
            title.setZIndex(1000);
            nextButt.setVisible(true);
            nextButt.setZIndex(1000);
        }else if(scenes[currentScene].type.equals("Phase")){
            changingPhase = true;
            backPhases.setVisible(true);
            solido.setVisible(true);
            gas.setVisible(true);
            liquido.setVisible(true);
        }else if(scenes[currentScene].type.equals("Selection1")){
            planet1.setVisible(true);
            planet2.setVisible(true);
            planet3.setVisible(true);
            planet4.setVisible(true);
            planet5.setVisible(true);
            sun.setVisible(true);
            ship.setVisible(true);
            auxButt.setVisible(true);
            stage.addActor(levelBar1);
            stage.addActor(levelBar2);
            stage.addActor(levelBar3);
            stage.addActor(level);
            getHelps("Selection1");
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
            stage.addActor(auxText);
            nextButt.setZIndex(1000);
            getHelps("Selection2");
            helpsImages[0].setText(auxTexts[((currentMaze+1)*3)-3]);
            helpsImages[1].setText(auxTexts[((currentMaze+1)*3)-2]);
            helpsImages[2].setText(auxTexts[((currentMaze+1)*3)-1]);
        }else if(scenes[currentScene].type.equals("Mazes")){
            backMusic.stop();
            backMusic2.play();
            for(int i = 0; i < 5; i++){
                if(currentMaze != i){
                    mazes[i].setColor(Color.GRAY);
                    mazes[i].clearListeners();
                }
                mazes[i].setVisible(true);
                stage.addActor(mazes[i]);
            }
        }else if(scenes[currentScene].type.equals("Ideas")){
            scroll.setVisible(true);
            stage.addActor(scroll);
            addIdeaButt.setVisible(true);
            stage.addActor(addIdeaButt);
            getHelps("Ideas");
        }else if(scenes[currentScene].type.equals("CloseSelection2")){
            blackTexture.setBounds(0, 0, 1080, 1920);
            blackTexture.setColor(0, 0, 0, 0);
            blackTexture.setVisible(false);
            auxButt.setVisible(false);
            auxText.setVisible(false);
        }else if(scenes[currentScene].type.equals("Storyboard")){
            backMusic2.stop();
            blackTexture.setColor(0, 0, 0, 0);
            blackTexture.setVisible(true);
            stage.addAction(Actions.run(new Runnable(){
                @Override
                public void run(){
                    blackTexture.setZIndex(1000);
                    blackTexture.addAction(Actions.sequence(Actions.fadeIn(1), Actions.run(new Runnable(){
                        @Override
                        public void run(){
                            //game.setScreen(new StoryboardScreen(game, LevelScreen.this));
                        }
                    })));
                }
            }));
        }else if(scenes[currentScene].type.equals("Transition")){
            addTransition();
        }

        int nextScene = currentScene+1;
        if(nextScene < scenes.length && scenes[nextScene].type.equals("Next")){
            Gdx.app.log("Next", "Entre");
            next = true;
        }

        if(scenes[currentScene].close == 1){
            for(int i = 1; i < scenes[currentScene].toClose.length; i++){
                closeScene(scenes[currentScene].toClose[i]);
            }
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
            dialog.setVisible(false);
            title.setVisible(false);
            nextButt.setVisible(false);
        }else if(scene.equals("Phase")){
            backPhases.setVisible(false);
            solido.setVisible(false);
            gas.setVisible(false);
            liquido.setVisible(false);
        }else if(scene.equals("Selection1")){
            planet1.setVisible(false);
            planet2.setVisible(false);
            planet3.setVisible(false);
            planet4.setVisible(false);
            planet5.setVisible(false);
            sun.setVisible(false);
            ship.setVisible(false);
            auxButt.setVisible(false);
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
            for(int i = 0; i < helpsImages.length; i++){
                helpsImages[i].showHelp(false);
            }
        }else if(scene.equals("Mazes")){
            for(int i = 0; i < 5; i++){
                mazes[i].setVisible(false);
            }
        }else if(scene.equals("Ideas")){
            scroll.setVisible(false);
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
                phaseS.play(0.5f);
                beginPhase = false;
            }
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
                    beginPhase = true;
                    timer = 2;
                    nextCurrentScene();
                }
            }
            float angle = backPhases.getRotation()+330;
            float newX = (457*MathUtils.cosDeg(angle))+540-498;
            float newY = (457*MathUtils.sinDeg(angle))+960-506;
            float angle2 = backPhases.getRotation()+90;
            float newX2 = (457*MathUtils.cosDeg(angle2))+540-421;
            float newY2 = (457*MathUtils.sinDeg(angle2))+960-418;
            float angle3 = backPhases.getRotation()+210;
            float newX3 = (457*MathUtils.cosDeg(angle3))+540-472;
            float newY3 = (457*MathUtils.sinDeg(angle3))+960-480;
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
                float randomTime = MathUtils.random(6, 10);
                currentTimeRoulette2 = randomTime+rand.nextFloat();
            }else if(currentTimeRoulette2 > 0){
                if(timeRoulette > 0){
                    timeRoulette -= speedRoulette2*Gdx.graphics.getDeltaTime();
                }else{
                    randS.play(0.5f);
                    current.setBounds(current.getX()+20, current.getY()+20,current.getWidth()-40, current.getHeight()-40);
                    current.setColor(Color.GRAY);
                    currentTimeRoulette2 -= 0.1f;
                    timeRouletteCont += 0.1f;
                    timeRoulette = timeRouletteCont;
                    if(indexRoulette < 4)
                        indexRoulette++;
                    else
                        indexRoulette = 0;
                    current = planets[indexRoulette];
                    current.setBounds(current.getX()-20, current.getY()-20,current.getWidth()+40, current.getHeight()+40);
                    current.setColor(1, 1, 1, 1);
                    current.setZIndex(1000);
                    currentMaze = indexRoulette;
                }
            }else{
                ship.addAction(Actions.parallel(Actions.moveTo(current.getX()+140, current.getY()+170, 1), Actions.sizeTo(20, 10, 1.2f)));
                spinning = 0;
                current.setBounds(current.getX()+20, current.getY()+20,current.getWidth()-40, current.getHeight()-40);
                current = planet1;
                currentTimeRoulette2 = -1;
                timeRoulette = 0.2f;
                timeRouletteCont = 0.2f;
                indexRoulette = 0;
                nextCurrentScene();
            }
        }
    }

    public void addTransition(){
        RunnableAction run = Actions.run(new Runnable(){
            @Override
            public void run(){
                backPhases.setVisible(false);
                solido.setVisible(false);
                gas.setVisible(false);
                liquido.setVisible(false);
            }
        });
        RunnableAction run2 = Actions.run(new Runnable(){
            @Override
            public void run(){
                blackTexture.setVisible(false);
                nextCurrentScene();
            }
        });
        blackTexture.setColor(0, 0, 0, 0);
        blackTexture.setVisible(true);
        blackTexture.setZIndex(1000);
        blackTexture.addAction(Actions.sequence(Actions.fadeIn(1), Actions.delay(0.5f), run, Actions.fadeOut(1), run2));
    }

    public void showAuxText(final int text){
        if(text == 1 || text == 4 || text == 7 || text == 10 || text == 13){
            blackTexture.setBounds(0, 0, 360, 1920);
            auxText.setBounds(0, 710, 360, 500);
            auxButt.setPosition(100, 500);
        }else if(text == 2 || text == 5 || text == 8 || text == 11 || text == 14){
            blackTexture.setBounds(360, 0, 360, 1920);
            auxText.setBounds(360, 710, 360, 500);
            auxButt.setPosition(460, 500);
        }else if(text == 3 || text == 6 || text == 9 || text == 12 || text == 15){
            blackTexture.setBounds(720, 0, 360, 1920);
            auxText.setBounds(720, 710, 360, 500);
            auxButt.setPosition(820, 500);
        }
        blackTexture.setColor(0, 0, 0, 0.5f);
        blackTexture.setVisible(true);
        blackTexture.setZIndex(1000);
        auxButt.setZIndex(1000);
        auxButt.setVisible(true);
        for(int i = 0; i < 15; i++){
            if(i+1==text){
                auxText.setText(auxTexts[i]);
                auxText.setVisible(true);
                auxText.setZIndex(1000);
                auxButt.addListener(new InputListener(){
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                        super.touchDown(event, x, y, pointer, button);
                        caseToSolve = text;
                        Gdx.app.log("Entre", String.valueOf(caseToSolve));
                        return true;
                    }
                });
                break;
            }
        }
    }

    public void showRandCards(int maze){
        float cardX = 82.5f;
        float cardY = 1466;
        float sizeRand = 10;

        currentMaze = maze;
        sizeRand -= usedCards[currentMaze].size;

        cardShuffle.play();
        for(int i = 0; i < sizeRand; i++){
            randCards[i].setPosition(860, -250);
            randCards[i].setColor(1, 1, 1, 1);
            randCards[i].setDrawable(randCardDrawable);
            stage.addActor(randCards[i]);
            randCards[i].setVisible(true);
            randCards[i].addAction(Actions.sequence(Actions.parallel(Actions.moveTo(cardX, cardY, 0.7f), Actions.sizeTo(250, 350, 0.7f))));
            cardX += 316;
            if(cardX > 915){
                if(i != 8){
                    cardX = 82.5f;
                }else{
                    cardX = 415;
                }
                cardY -= 454;
            }
        }
    }

    public void showRandCard(final int card){
        Random rand = new Random();
        int[] posibleCards;
        int randC;
        if(usedCards[currentMaze].size>0){
            /*if(comodin){
                if(comodinCard == 1)
                    posibleCards = new int[9-usedCards[currentMaze].size];
                else if(comodinCard == 2)
                    posibleCards = new int[8-usedCards[currentMaze].size];
                else
                    posibleCards = new int[10-usedCards[currentMaze].size];
            }else{
                posibleCards = new int[10-usedCards[currentMaze].size];
            }*/
            posibleCards = new int[10-usedCards[currentMaze].size];
            int current = 0;
            for(int i = 0; i < 10; i++){
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
        currentCard = randCard;
        randCards[card].setZIndex(1000);
        usedCards[currentMaze].add(randCard);
        RunnableAction run = Actions.run(new Runnable(){
            @Override
            public void run(){
                randCards[card].setDrawable(cardsSkin.getDrawable(tarjetas[currentMaze][randCard]));
                if(comodin){
                    if(numCards==1){
                        card2.setDrawable(cardsSkin.getDrawable(tarjetas[currentMaze][randCard]));
                    }else{
                        card3.setDrawable(cardsSkin.getDrawable(tarjetas[currentMaze][randCard]));
                    }
                }else{
                    card1.setDrawable(cardsSkin.getDrawable(tarjetas[currentMaze][randCard]));
                }
            }
        });
        RunnableAction run2 = Actions.run(new Runnable(){
            @Override
            public void run(){
                closeBag.play();
                storeCard(card);
            }
        });
        randCards[card].addAction(Actions.sequence(run, Actions.parallel(Actions.moveTo(207.75f, 502.5f, 0.7f), Actions.sizeTo(664.5f, 915, 0.7f)), Actions.delay(0.5f), run2));
        menuCard.setZIndex(1000);
    }

    public void storeCard(final int cardI){
        final Image randCard = randCards[cardI];
        RunnableAction run = Actions.run(new Runnable(){
            @Override
            public void run(){
                randCard.setVisible(false);
                for(int i = 0; i < 10; i++){
                    if(i != cardI){
                        randCards[i].addAction(Actions.sequence(Actions.parallel(Actions.moveTo(860, -250, 0.8f), Actions.sizeTo(200, 250, 0.8f)), Actions.run(new Runnable(){
                            @Override
                            public void run(){
                                endAnim += 1;
                                if(endAnim==10-usedCards[currentMaze].size){
                                    showMaze = true;
                                    if(comodin){
                                        if(numCards==1){
                                            card2.setBounds(900, 10, 200, 250);
                                            card2.remove();
                                            //menuCard.addCard(card2);
                                        }else{
                                            card3.setBounds(900, 10, 200, 250);
                                            card3.remove();
                                            //menuCard.addCard(card3);
                                        }
                                    }else{
                                        card1.setBounds(900, 10, 200, 250);
                                        card1.remove();
                                        //menuCard.addCard(card1);

                                        if(tarjetas[currentMaze][currentCard].equals("+1") || tarjetas[currentMaze][currentCard].equals("+2")){
                                            comodin = true;
                                            if(tarjetas[currentMaze][currentCard].equals("+1")){
                                                comodinCard = 1;
                                                usedCards[currentMaze].add(9);
                                            }else if(tarjetas[currentMaze][currentCard].equals("+2")){
                                                comodinCard = 2;
                                                usedCards[currentMaze].add(8);
                                            }
                                            for(int i = 0; i < 5; i++){
                                                if(i != currentMaze){
                                                    usedCards[i].add(8);
                                                    usedCards[i].add(9);
                                                }
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
                                            nextCurrentScene();
                                        }
                                    }else{
                                        nextCurrentScene();
                                    }
                                }
                            }
                        })));
                    }
                }
            }
        });
        /*float cardX = 1710;
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
        }*/
        randCard.addAction(Actions.sequence(Actions.parallel(Actions.moveTo(1000, 1800, 0.8f), Actions.sizeTo(200, 250, 0.8f)), run));
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

    public void showEmergent(String help){
        emergentBox.textLabel.setText(help);
        emergentBox.setVisible2(true);
    }

    public void setData(){
        int size = scroll.ideaBoxes.size;
        if(size > 1){
            String[][] ideas = new String[size][2];
            for(int i = 0; i < size; i++){
                ideas[i] = new String[]{String.valueOf(currentMaze), scroll.ideaBoxes.get(i).label.getText().toString()};
            }
            dataUser.addIdeas(ideas);
            //game.addIdeasOnline("CUNY23", ideas);
        }else{
            String[] idea = new String[]{String.valueOf(currentMaze), scroll.ideaBoxes.get(0).label.getText().toString()};
            dataUser.addIdea(idea);
        }
        game.addIdeas();
    }

    public void hide(){
        stage.clear();
    }

    public void getHelps(String id){
        int helpI = 0;
        for(int i = 0; i < helps.length; i++){
            if(helps[i].id.equals(id)){
                helpsImages[helpI].setHelp(helps[i].text, helps[i].bounds);
                helpsImages[helpI].showHelp(true);
                helpI++;
            }
        }
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
        levelUser = Integer.valueOf(exp);
        level.setText(exp);
        //float newSize = (cant*100)/376;
        float newSize = (currentExp*376)/1500;
        levelBar2.setWidth(newSize);
        levelBar3.setX(levelBar2.getX()+levelBar2.getWidth());
    }
}