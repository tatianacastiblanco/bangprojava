package com.mygdx.bang;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class TutorialScreen extends LevelScreen implements InputProcessor{
    BANG game;
    SpriteBatch batch;
    OrthographicCamera camera;
    AssetManager assetManager;

    boolean next = false;
    boolean toRight;
    FrameBuffer oldBuff, nextBuff;
    Stage stage;
    Image tutorial1, tutorial2, endButt;
    boolean active = true;
    Vector2 touchPos = new Vector2(0, 0);
    Vector2 touchPos2 = new Vector2(0, 0);
    int currentImg = 0;
    int maxTut = 4;
    Skin tutorialSkin;
    boolean nextScreen = false, nextScreen2 = true;

    MissionScreen mission;
    private String nextScreenAdap;
    Image backButt2, nextButt2;

    TutorialScreen(BANG _game, MissionScreen level){
        game = _game;
        mission = level;
        assetManager = game.assetManager;

        camera = new OrthographicCamera(1080, 1920);
        camera.position.set(540, 960, 0);
        batch = new SpriteBatch();

        currentScene = level.currentScene;
        currentMaze = level.currentMaze;
        currentNeed = level.currentNeed;

        stage = game.stage;

        blackTexture = game.transitionText;

        background = level.background;
        comet = level.comet;
        asteroid = level.asteroid;
        satelite = level.satelite;
        alien = level.alien;
        stars1 = level.stars1;
        stars2 = level.stars2;
        stars3 = level.stars3;
        stars4 = level.stars4;

        tutorialSkin = new Skin();
        tutorialSkin.add("beginButt", new Texture("tutoriales/begin.png"), Texture.class);
        tutorialSkin.add("nextButt", new Texture("nextButt.png"), Texture.class);
        tutorialSkin.add("Tut0", new Texture("tutoriales/1.png"), Texture.class);
        tutorialSkin.add("Tut1", new Texture("tutoriales/2.png"), Texture.class);
        tutorialSkin.add("Tut2", new Texture("tutoriales/3.png"), Texture.class);
        tutorialSkin.add("Tut3", new Texture("tutoriales/4.png"), Texture.class);
        tutorialSkin.add("Tut4", new Texture("tutoriales/5.png"), Texture.class);

        oldBuff = new FrameBuffer(Pixmap.Format.RGBA8888, 1080, 1920, false);
        nextBuff = new FrameBuffer(Pixmap.Format.RGBA8888, 1080, 1920, false);

        tutorial1 = new Image(tutorialSkin.get("Tut0", Texture.class));
        tutorial1.setPosition(0, 0);
        tutorial2 = new Image(tutorialSkin.get("Tut1", Texture.class));
        tutorial2.setPosition(1080, 0);

        backButt2 = new Image(tutorialSkin.getDrawable("nextButt"));
        backButt2.setOrigin(155, 133.5f);
        backButt2.setRotation(180);
        backButt2.setPosition(20, 26);
        backButt2.setVisible(false);
        backButt2.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                backImg();
                return true;
            }
        });
        nextButt2 = new Image(tutorialSkin.getDrawable("nextButt"));
        nextButt2.setPosition(800, 26);
        nextButt2.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                nextImg();
                return true;
            }
        });
        endButt = new Image(tutorialSkin.getDrawable("beginButt"));
        endButt.setPosition(654, 58);
        endButt.setVisible(false);
        endButt.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                showLoad("Storyboard");
                return true;
            }
        });
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(stage);
        inputMultiplexer.addProcessor(this);
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0.0f,	0.0f,	0.0f,	1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        /*batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(tutorialSkin.get("Tut1", Texture.class), 100, 100);
        batch.end();
        if(next){
            if(oldX==1080){
                oldBuff.bind();
                Gdx.gl.glClearColor(0.0f,	0.0f,	0.0f,	1.0f);
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                oldBuff.begin();
                batch.begin();
                batch.draw(tutorial1, 0, 0);
                batch.end();
                oldBuff.end();
                nextBuff.bind();
                Gdx.gl.glClearColor(0.0f,	0.0f,	0.0f,	1.0f);
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                nextBuff.begin();
                batch.begin();
                batch.draw(tutorial2, 0, 0);
                batch.end();
                nextBuff.end();
                nextBuff.unbind();
                oldX -= 0.0001f;
            }else{
                oldX -= 0.5f;
                nextX -= 0.5f;
                batch.begin();
                tutorial1 = oldBuff.getColorBufferTexture();
                TextureRegion t = new TextureRegion(oldBuff.getColorBufferTexture());
                t.flip(false, true);
                batch.draw(t, oldX, 0);
                TextureRegion t2 = new TextureRegion(nextBuff.getColorBufferTexture());
                t2.flip(false, true);
                batch.draw(t2, nextX, 0);
                batch.end();
            }
        }else{
            if(time > 0){
                time -= delta;
                nextImg();
            }*/
            /*batch.begin();
            batch.draw(tutorialSkin.get("badlogic", Texture.class), 100, 100);
            batch.end();*/
        /*}*/

        if(nextScreen){
            game.setProgress();
            if(nextScreen2){
                if(assetManager.update()){
                    if(assetManager.getProgress() >= 1){
                        stage.addAction(Actions.sequence(Actions.delay(0.2f), Actions.run(new Runnable(){
                            @Override
                            public void run(){
                                if(nextScreenAdap.equals("Storyboard"))
                                    game.setScreen(new StoryboardScreen(mission));
                                else if(nextScreenAdap.equals("Menu"))
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

        stage.addActor(tutorial1);
        stage.addActor(tutorial2);
        stage.addActor(backButt2);
        stage.addActor(nextButt2);
        stage.addActor(endButt);
        stage.addActor(blackTexture);
        blackTexture.setVisible(false);
    }

    public void nextImg(){
        if(currentImg < maxTut){
            active = false;
            next = true;
            toRight = true;
            tutorial1.setDrawable(tutorialSkin.getDrawable("Tut"+currentImg));
            if(currentImg == 0){
                backButt2.setVisible(true);
            }
            currentImg++;
            if(currentImg == maxTut){
                nextButt2.setVisible(false);
                endButt.setVisible(true);
            }
            tutorial2.setDrawable(tutorialSkin.getDrawable("Tut"+currentImg));
            tutorial1.addAction(Actions.sequence(Actions.moveTo(0, 0), Actions.moveTo(-1080, 0, 0.3f), Actions.run(new Runnable() {
                @Override
                public void run(){
                    active = true;
                }
            })));
            tutorial2.addAction(Actions.sequence(Actions.moveTo(1080, 0), Actions.moveTo(0, 0, 0.3f)));
        }
    }

    public void backImg(){
        if(currentImg > 0){
            if(currentImg==maxTut){
                endButt.setVisible(false);
                nextButt2.setVisible(true);
            }else if(currentImg == 1){
                backButt2.setVisible(false);
            }
            active = false;
            toRight = false;
            tutorial2.setDrawable(tutorialSkin.getDrawable("Tut"+currentImg));
            currentImg--;
            tutorial1.setDrawable(tutorialSkin.getDrawable("Tut"+currentImg));
            tutorial1.addAction(Actions.sequence(Actions.moveTo(-1080, 0), Actions.moveTo(0, 0, 0.3f), Actions.run(new Runnable() {
                @Override
                public void run(){
                    active = true;
                }
            })));
            tutorial2.addAction(Actions.sequence(Actions.moveTo(0, 0), Actions.moveTo(1080, 0, 0.3f)));
        }
    }

    @Override
    public void showLoad(String asset){
        nextScreen = true;
        nextScreenAdap = asset;
        assetManager.load("nC"+currentNeed+".png", Texture.class);
        assetManager.load("storyboard/tools/n"+currentNeed+"/obj1.png", Texture.class);
        assetManager.load("storyboard/tools/n"+currentNeed+"/obj2.png", Texture.class);
        assetManager.load("storyboard/tools/n"+currentNeed+"/obj3.png", Texture.class);
        assetManager.load("storyboard/tools/n"+currentNeed+"/obj4.png", Texture.class);
        assetManager.load("storyboard/tools/n"+currentNeed+"/obj5.png", Texture.class);
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
        if(mission.showTutorials)
            mission.currentScene = 50;
        else
            mission.currentScene = 26;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button){
        touchPos.x = screenX;
        touchPos.y = screenY;
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button){
        if(active){
            Gdx.app.log("Entre", "SIP");
            float deltaPosX = touchPos.x-touchPos2.x;
            if(deltaPosX<0){
                Gdx.app.log("Entre", "Next");
                backImg();
            }else if(deltaPosX>0){
                Gdx.app.log("Entre", "Back");
                nextImg();
            }
        }
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer){
        touchPos2.x = screenX;
        touchPos2.y = screenY;
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
