package com.mygdx.bang;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import javax.xml.soap.Text;

public class GetBangsScreen extends LevelScreen{
    Music backMusic;

    Image titleIdeas;

    Array<String[]> ideas;
    Array<String[][]> cards;
    ScrollPane scroll;
    Table ideasT, content;
    Array<Label> labels;

    Image[] filters;
    ScrollPane filterScroll;
    Table filterT;
    Image arrow1, arrow2;

    Label.LabelStyle labelStyle;
    Skin skin;
    private boolean nextScreen = false, nextScreen2 = true;
    private String nextScreenAdap;
    int currentIdea;

    GetBangsScreen(BANG _game, final MenuScreen menu){
        game = _game;
        camera = game.camera;
        camera.position.set(960, 540, 0);
        viewport = game.viewport;
        batch = game.batch;
        stage = game.stage;
        assetManager = game.assetManager;

        musicButt = menu.musicButt;
        soundButt = menu.soundButt;

        blackTexture = game.transitionText;
        background = menu.background;
        comet = menu.comet;
        asteroid = menu.asteroid;
        satelite = menu.satelite;
        alien = menu.alien;
        stars1 = menu.stars1;
        stars2 = menu.stars2;
        stars3 = menu.stars3;
        stars4 = menu.stars4;

        dataUser = game.dataUser;

        backMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/nylonStringGuitar.mp3"));
        backMusic.setVolume(0.5f);
        backMusic.setLooping(true);
        if(game.getMusic())
            backMusic.play();

        skinCharacters = new Skin();
        skinCharacters.add("hand", assetManager.get("hand.png", Texture.class));
        skinCharacters.add("aceptButt", assetManager.get("aceptButt.png", Texture.class));

        menuCard = new MenuCards(this);

        BitmapFont fontEmergent = new BitmapFont(Gdx.files.internal("delius.fnt"));
        fontEmergent.setColor(Color.WHITE);
        emergentBox = new EmergentBox(fontEmergent);
        emergentBox.setVisible2(false);

        ideas = dataUser.getIdeas();

        titleIdeas = new Image(assetManager.get("ideasTitle.png", Texture.class));
        titleIdeas.setPosition(319, 1603);

        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(0.9216f, 0.7686f, 0.3176f, 1);
        pixmap.fill();
        skin = new Skin();
        skin.add("backScroll", new Texture(pixmap));
        skin.add("arrow", assetManager.get("retomar/arrow.png", Texture.class));
        skin.add("ideaBox0", assetManager.get("retomar/ideaBox0.png", Texture.class));
        skin.add("ideaBox1", assetManager.get("retomar/ideaBox1.png", Texture.class));
        skin.add("ideaBox2", assetManager.get("retomar/ideaBox2.png", Texture.class));
        skin.add("ideaBox3", assetManager.get("retomar/ideaBox3.png", Texture.class));
        skin.add("ideaBox4", assetManager.get("retomar/ideaBox4.png", Texture.class));
        skin.add("filterOut", assetManager.get("retomar/filterOut.png", Texture.class));
        skin.add("filterBox", assetManager.get("retomar/filterBox.png", Texture.class));
        skin.add("filter1", assetManager.get("needs/iconN1.png", Texture.class));
        skin.add("filter2", assetManager.get("needs/iconN2.png", Texture.class));
        skin.add("filter3", assetManager.get("needs/iconN3.png", Texture.class));
        skin.add("filter4", assetManager.get("needs/iconN4.png", Texture.class));
        skin.add("filter5", assetManager.get("needs/iconN5.png", Texture.class));
        skin.add("filter6", assetManager.get("needs/iconN6.png", Texture.class));
        skin.add("filter7", assetManager.get("needs/iconN7.png", Texture.class));
        skin.add("filter8", assetManager.get("needs/iconN8.png", Texture.class));
        skin.add("filter9", assetManager.get("needs/iconN9.png", Texture.class));
        skin.add("filter10", assetManager.get("needs/iconN10.png", Texture.class));
        skin.add("filter11", assetManager.get("needs/iconN11.png", Texture.class));
        skin.add("filter12", assetManager.get("needs/iconN12.png", Texture.class));
        skin.add("filter13", assetManager.get("needs/iconN13.png", Texture.class));
        skin.add("filter14", assetManager.get("needs/iconN14.png", Texture.class));
        skin.add("filter15", assetManager.get("needs/iconN15.png", Texture.class));

        ideasT = new Table();
        labelStyle = new Label.LabelStyle();
        labelStyle.font = new BitmapFont(Gdx.files.internal("delius.fnt"));
        labelStyle.fontColor = Color.BLACK;

        ideas = new Array<String[]>();
        cards = new Array<String[][]>();
        for(int i = 0; i < dataUser.getIdeas().size; i++){
            ideas.add(dataUser.getIdeas().get(i));
            cards.add(dataUser.getCards().get(i));
        }

        labels = new Array<Label>();
        int indexColor = 0;
        for(int i = 0; i < ideas.size; i++){
            Image category = new Image(skin.getDrawable("filterBox"));
            Table categT = new Table();
            Image categFilter = new Image(skin.getDrawable("filter"+ideas.get(i)[1]));
            categT.setBackground(category.getDrawable());
            categFilter.setOrigin(Align.center);
            categFilter.setScale(0.7f);
            categT.add(categFilter).expand().center();
            categT.setSize(184, 184);
            categT.setTransform(true);
            Image backLabel = new Image(skin.getDrawable("ideaBox"+ideas.get(i)[0]));
            if(indexColor<4)
                indexColor++;
            else
                indexColor = 0;
            Label label = new Label(ideas.get(i)[2], labelStyle);
            label.setWidth(692);
            label.setWrap(true);
            label.layout();
            float newH = label.getGlyphLayout().height+160;
            backLabel.setBounds(0, 0, 772, newH);

            Table textStack = new Table();
            textStack.setBackground(backLabel.getDrawable());
            textStack.add(label).size(672, newH-140).pad(30, 47, 67, 0).expand();
            textStack.setSize(772, newH);
            final int finalI = i;
            textStack.addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                    showLoad("Mission");
                    currentMaze = Integer.parseInt(ideas.get(finalI)[0]);
                    currentNeed = Integer.parseInt(ideas.get(finalI)[1]);
                    if(!assetManager.isLoaded("nC"+currentNeed+".png", Texture.class)){
                        assetManager.load("nC" + currentNeed + ".png", Texture.class);
                    }
                    if(!assetManager.isLoaded("storyboard/tools/n"+currentNeed+"/obj5.png.png", Texture.class)){
                        assetManager.load("storyboard/tools/n"+currentNeed+"/obj1.png", Texture.class);
                        assetManager.load("storyboard/tools/n"+currentNeed+"/obj2.png", Texture.class);
                        assetManager.load("storyboard/tools/n"+currentNeed+"/obj3.png", Texture.class);
                        assetManager.load("storyboard/tools/n"+currentNeed+"/obj4.png", Texture.class);
                        assetManager.load("storyboard/tools/n"+currentNeed+"/obj5.png", Texture.class);
                    }
                    game.loadAssets("Storyboard");
                    currentIdea = finalI;
                    //menuCard.addCard();
                    return true;
                }
            });
            ideasT.add(categT).size(184, 184).expand().center();
            ideasT.add(textStack).size(772, newH);
            ideasT.row().padTop(10);
        }

        ScrollPane.ScrollPaneStyle scrollPaneStyle = new ScrollPane.ScrollPaneStyle();
        scroll = new ScrollPane(ideasT, scrollPaneStyle);

        content = new Table();
        content.add(scroll).top().expand();
        content.setBounds(71, 340, 956, 1242);

        arrow1 = new Image(skin.getDrawable("arrow"));
        arrow2 = new Image(skin.getDrawable("arrow"));
        arrow2.setOrigin(Align.center);
        arrow2.setRotation(180);

        Table filterContent = new Table();

        Image backFilter = new Image(skin.getDrawable("filterBox"));
        filters = new Image[15];
        for(int i = 0; i < 15; i++){
            filters[i] = new Image(skin.getDrawable("filter"+(i+1)));
        }

        Table firstFilter = new Table();
        firstFilter.setBackground(backFilter.getDrawable());
        firstFilter.add(new Image(skin.getDrawable("filterOut"))).center().expand();
        filterContent.add(firstFilter).size(282,  282).padLeft(18).center().expand();
        firstFilter.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                setFilter("remove");
                return true;
            }
        });
        for(int i = 0; i < filters.length; i++){
            Table newT = new Table();
            newT.setBackground(backFilter.getDrawable());
            newT.add(filters[i]).center().expand();
            if(i == filters.length-1)
                filterContent.add(newT).size(282,  282).padLeft(18).padRight(18).center().expand();
            else
                filterContent.add(newT).size(282,  282).padLeft(18).center().expand();
            final int finalI = i+1;
            newT.addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                    setFilter(String.valueOf(finalI));
                    return true;
                }
            });
        }

        filterScroll = new ScrollPane(filterContent, scrollPaneStyle);
        filterScroll.setSize(918, 320);

        filterT = new Table();
        filterT.add(arrow1).size(41, 59.5f).padLeft(40).expand().center();
        filterT.add(filterScroll).size(918, 320);//321 258
        filterT.add(arrow2).size(41, 59.5f).padRight(40).expand().center();
        filterT.setSize(1080, 320);
        filterT.setBackground(skin.getDrawable("backScroll"));
    }

    @Override
    public void render(float delta){
        Gdx.gl20.glClearColor(0, 0, 0, 1);
        Gdx.gl20.glClear(GL30.GL_COLOR_BUFFER_BIT | GL30.GL_DEPTH_BUFFER_BIT);

        /*batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.end();*/

        comet.update(delta);
        asteroid.update(delta);
        satelite.update(delta);
        alien.update(delta);

        if(nextScreen){
            game.setProgress();
            if(nextScreen2){
                if(assetManager.update()){
                    if(assetManager.getProgress() >= 1){
                        stage.addAction(Actions.sequence(Actions.delay(0.2f), Actions.run(new Runnable(){
                            @Override
                            public void run(){
                                backMusic.stop();
                                if(nextScreenAdap.equals("Menu")){
                                    game.setScreen(new MenuScreen(game, false));
                                }else if(nextScreenAdap.equals("Mission")){
                                    game.setScreen(new StoryboardScreen(GetBangsScreen.this, currentIdea));
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
        stage.addActor(titleIdeas);
        stage.addActor(content);
        stage.addActor(menuCard);
        stage.addActor(menuCard.blackTexture);
        stage.addActor(musicButt);
        musicButt.setVisible(false);
        musicButt.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                Gdx.app.log("Musica", "Entre");
                if(game.getMusic()){
                    game.setMusic(false);
                    backMusic.pause();
                }else{
                    game.setMusic(true);
                    backMusic.play();
                }
                return true;
            }
        });
        stage.addActor(soundButt);
        soundButt.setVisible(false);
        stage.addActor(emergentBox);
        stage.addActor(emergentBox.blackTexture);

        stage.addActor(filterT);

        stage.addActor(blackTexture);
        RunnableAction run = Actions.run(new Runnable() {
            @Override
            public void run(){
                blackTexture.setVisible(false);
            }
        });
        blackTexture.setVisible(true);
        blackTexture.addAction(Actions.sequence(Actions.alpha(1), Actions.alpha(0, 1), run));
    }

    @Override
    public void hide(){
        stage.clear();
        assetManager.unload("retomar/filterBox.png");
        assetManager.unload("retomar/ideaBox.png");
        assetManager.unload("retomar/ideaBox0.png");
        assetManager.unload("retomar/ideaBox1.png");
        assetManager.unload("retomar/ideaBox2.png");
        assetManager.unload("retomar/ideaBox3.png");
        assetManager.unload("retomar/ideaBox4.png");
        assetManager.unload("retomar/arrow.png");
        assetManager.unload("retomar/filterOut.png");
    }

    public void setFilter(String _filter){
        ideasT.clearChildren();
        Array<String[]> ideasTmp = new Array<String[]>();
        for(int i = 0; i < ideas.size; i++){
            String[] current = ideas.get(i);
            if(_filter.equals("remove")){
                ideasTmp.add(current);
                Image category = new Image(skin.getDrawable("filterBox"));
                Table categT = new Table();
                Image categFilter = new Image(skin.getDrawable("filter"+current[1]));
                categT.setBackground(category.getDrawable());
                categFilter.setOrigin(Align.center);
                categFilter.setScale(0.7f);
                categT.add(categFilter).expand().center();
                categT.setSize(184, 184);
                categT.setTransform(true);
                Image backLabel = new Image(skin.getDrawable("ideaBox"+current[0]));
                Label label = new Label(current[2], labelStyle);
                label.setWidth(692);
                label.setWrap(true);
                label.layout();
                float newH = label.getGlyphLayout().height+160;
                backLabel.setBounds(0, 0, 772, newH);

                Table textStack = new Table();
                textStack.setBackground(backLabel.getDrawable());
                textStack.add(label).size(672, newH-140).pad(30, 47, 67, 0).expand();
                textStack.setSize(772, newH);
                final int finalI = i;
                textStack.addListener(new InputListener(){
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                        showLoad("Mission");
                        currentMaze = Integer.parseInt(ideas.get(finalI)[0]);
                        currentNeed = Integer.parseInt(ideas.get(finalI)[1]);
                        if(!assetManager.isLoaded("nC"+currentNeed+".png", Texture.class)){
                            assetManager.load("nC" + currentNeed + ".png", Texture.class);
                        }
                        if(!assetManager.isLoaded("storyboard/tools/n"+currentNeed+"/obj5.png.png", Texture.class)){
                            assetManager.load("storyboard/tools/n"+currentNeed+"/obj1.png", Texture.class);
                            assetManager.load("storyboard/tools/n"+currentNeed+"/obj2.png", Texture.class);
                            assetManager.load("storyboard/tools/n"+currentNeed+"/obj3.png", Texture.class);
                            assetManager.load("storyboard/tools/n"+currentNeed+"/obj4.png", Texture.class);
                            assetManager.load("storyboard/tools/n"+currentNeed+"/obj5.png", Texture.class);
                        }
                        /*assetManager.load("nC"+currentNeed+".png", Texture.class);
                        assetManager.load("storyboard/tools/n"+currentNeed+"/obj1.png", Texture.class);
                        assetManager.load("storyboard/tools/n"+currentNeed+"/obj2.png", Texture.class);
                        assetManager.load("storyboard/tools/n"+currentNeed+"/obj3.png", Texture.class);
                        assetManager.load("storyboard/tools/n"+currentNeed+"/obj4.png", Texture.class);
                        assetManager.load("storyboard/tools/n"+currentNeed+"/obj5.png", Texture.class);*/
                        game.loadAssets("Storyboard");
                        currentIdea = finalI;
                        //menuCard.addCard();
                        return true;
                    }
                });
                ideasT.add(categT).size(184, 184).expand().center();
                ideasT.add(textStack).size(772, newH);
                ideasT.row().padTop(10);
            }else if(current[1].equals(_filter)){
                ideasTmp.add(current);
                Image category = new Image(skin.getDrawable("filterBox"));
                Table categT = new Table();
                Image categFilter = new Image(skin.getDrawable("filter"+current[1]));
                categT.setBackground(category.getDrawable());
                categFilter.setOrigin(Align.center);
                categFilter.setScale(0.7f);
                categT.add(categFilter).expand().center();
                categT.setSize(184, 184);
                categT.setTransform(true);
                Image backLabel = new Image(skin.getDrawable("ideaBox"+current[0]));
                Label label = new Label(current[2], labelStyle);
                label.setWidth(692);
                label.setWrap(true);
                label.layout();
                float newH = label.getGlyphLayout().height+160;
                backLabel.setBounds(0, 0, 772, newH);

                Table textStack = new Table();
                textStack.setBackground(backLabel.getDrawable());
                textStack.add(label).size(672, newH-140).pad(30, 47, 67, 0).expand();
                textStack.setSize(772, newH);
                final int finalI1 = i;
                textStack.setTouchable(Touchable.enabled);
                textStack.addListener(new ClickListener(){
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                        showLoad("Mission");
                        currentMaze = Integer.parseInt(ideas.get(finalI1)[0]);
                        currentNeed = Integer.parseInt(ideas.get(finalI1)[1]);
                        if(!assetManager.isLoaded("nC"+currentNeed+".png", Texture.class)){
                            assetManager.load("nC" + currentNeed + ".png", Texture.class);
                        }
                        if(!assetManager.isLoaded("storyboard/tools/n"+currentNeed+"/obj5.png.png", Texture.class)){
                            assetManager.load("storyboard/tools/n"+currentNeed+"/obj1.png", Texture.class);
                            assetManager.load("storyboard/tools/n"+currentNeed+"/obj2.png", Texture.class);
                            assetManager.load("storyboard/tools/n"+currentNeed+"/obj3.png", Texture.class);
                            assetManager.load("storyboard/tools/n"+currentNeed+"/obj4.png", Texture.class);
                            assetManager.load("storyboard/tools/n"+currentNeed+"/obj5.png", Texture.class);
                        }
                        /*assetManager.load("nC"+currentNeed+".png", Texture.class);
                        assetManager.load("storyboard/tools/n"+currentNeed+"/obj1.png", Texture.class);
                        assetManager.load("storyboard/tools/n"+currentNeed+"/obj2.png", Texture.class);
                        assetManager.load("storyboard/tools/n"+currentNeed+"/obj3.png", Texture.class);
                        assetManager.load("storyboard/tools/n"+currentNeed+"/obj4.png", Texture.class);
                        assetManager.load("storyboard/tools/n"+currentNeed+"/obj5.png", Texture.class);*/
                        game.loadAssets("Storyboard");
                        currentIdea = finalI1;
                        //menuCard.addCard();
                        return true;
                    }
                });
                ideasT.add(categT).size(184, 184).expand().center();
                ideasT.add(textStack).size(772, newH);
                ideasT.row().padTop(10);
            }
        }

        ideasT.align(Align.top);
        ScrollPane.ScrollPaneStyle scrollPaneStyle = new ScrollPane.ScrollPaneStyle();
        scroll = new ScrollPane(ideasT, scrollPaneStyle);

        content.clearChildren();
        content.add(scroll).top().expand();
        content.setBounds(71, 340, 956, 1242);
    }

    @Override
    public void showLoad(String asset){
        nextScreen = true;
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

}
