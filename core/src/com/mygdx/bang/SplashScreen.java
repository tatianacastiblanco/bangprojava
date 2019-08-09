package com.mygdx.bang;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class SplashScreen extends ScreenAdapter{
    BANG game;
    OrthographicCamera camera;
    FitViewport viewport;
    SpriteBatch batch;
    Stage stage;

    Image asteroid, asteroid2, star, whiteBack;

    int begin = 0;

    float deltaTime = 0;

    AssetManager assetManager;

    Image comet;
    Animation<TextureRegion> cometAnim;
    float animTime;


    SplashScreen(BANG _game){
        game = _game;
        game.batch = new SpriteBatch();
        batch = game.batch;
        game.camera = new OrthographicCamera(1080, 1920);
        camera = game.camera;
        game.viewport = new FitViewport(1080, 1920, camera);
        viewport = game.viewport;
        game.stageCamera = new OrthographicCamera(1080, 1920);
        game.stage = new Stage(new FitViewport(1080, 1920, game.stageCamera));
        stage = game.stage;

        game.assetManager = new AssetManager();
        assetManager = game.assetManager;

        game.backLoad = new Image(new Texture("levelBar1.png"));
        game.backLoad.setBounds(140, 836.4f, 800, 247.22f);
        game.backLoad.setColor(Color.GRAY);
        game.barLoad = new Image(new Texture("levelBar2.png"));
        game.barLoad.setBounds(238.9f, 931.6f, 0, 66);
        game.barLoad.setColor(Color.GRAY);
        game.endBarLoad = new Image(new Texture("levelBar3.png"));
        game.endBarLoad.setBounds(238.9f, 931.6f, 23, 66);
        game.endBarLoad.setColor(Color.GRAY);

        /*asteroid = new Image(new Texture("asteroid.png"));
        asteroid.setBounds(-400, game.height/2-125, 400, 250);
        asteroid2 = new Image(new Texture("asteroid.png"));
        asteroid2.setBounds(game.width, game.height/2-125, 400, 250);*/
        star = new Image(new Texture("starShine.png"));
        star.setOrigin(14, 14.5f);
        star.setBounds(game.width/2-25, game.height/2-25, 50, 50);
        star.setColor(1, 1, 1, 0);
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        game.transitionText = new Image(new Texture(pixmap));
        whiteBack = game.transitionText;
        whiteBack.setBounds(0, 0, 1080, 1920);
        whiteBack.setColor(1, 1, 1, 0);

        /*stage.addActor(asteroid);
        stage.addActor(asteroid2);*/
        stage.addActor(star);
        stage.addActor(whiteBack);

        game.loadAssets("Splash");
        game.loadAssets("Menu");
    }

    @Override
    public void render(float delta){
        Gdx.gl20.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        /*if(deltaTime < 2)
            deltaTime += delta;
        else if(begin == 0)
            begin = 1;
        if(begin == 1){
            final RunnableAction run2 = Actions.run(new Runnable(){
                @Override
                public void run(){
                    //game.setScreen(new MenuScreen(game));
                    begin = 3;
                    stage.addActor(game.backLoad);
                    stage.addActor(game.barLoad);
                    stage.addActor(game.endBarLoad);
                }
            });
            RunnableAction run = Actions.run(new Runnable(){
                @Override
                public void run(){
                    star.addAction(Actions.parallel(Actions.alpha(1f, 4), Actions.sizeTo(1920, 1920, 4), Actions.moveTo(-420, 0, 4)));
                    asteroid.addAction(Actions.moveTo(game.width/2-200, game.height/2-125, 4));
                    asteroid2.addAction(Actions.moveTo(game.width/2-200, game.height/2-125, 4));
                    whiteBack.addAction(Actions.sequence(Actions.delay(0.5f), Actions.fadeIn(3.5f), run2));
                }
            });
            asteroid.addAction(Actions.moveTo(game.width/2-400, game.height/2-125, 2));
            asteroid2.addAction(Actions.sequence(Actions.moveTo(game.width/2, game.height/2-125, 2), run));
            begin = 2;
        }

        if(begin == 3 && assetManager.update()){
            stage.addAction(Actions.sequence(Actions.delay(0.2f), Actions.run(new Runnable() {
                @Override
                public void run() {
                    game.setScreen(new MenuScreen(game));
                    //game.setScreen(new TutorialScreen(game));
                }
            })));
        }*/
        if(assetManager.isLoaded("splash/cometSheet58.png")){
            anim(delta);
        }
        assetManager.update();
        setProgress();
        Gdx.app.log("Cargando", String.valueOf(assetManager.getProgress()));

        stage.act(delta);
        stage.draw();
    }

    public void show(){
        stage.addActor(game.backLoad);
        stage.addActor(game.barLoad);
        stage.addActor(game.endBarLoad);
    }

    @Override
    public void hide(){
        stage.clear();
        for(int i = 1; i <= 58; i++){
            assetManager.unload("splash/cometSheet"+i+".png");
        }
    }

    public void anim(float _delta){
        if(begin==0){
            /*Texture texture = assetManager.get("splash/cometSheet.png", Texture.class);
            TextureRegion[][] temp = TextureRegion.split(texture, texture.getWidth() / 10, texture.getHeight() / 6);*/
            TextureRegion[] frames = new TextureRegion[58];
            int index = 0;
            /*for (int i = 0; i < 6; i++){
                for (int j = 0; j < 10; j++){
                    frames[index++] = temp[i][j];
                }
            }*/
            for(int i = 1; i <= 58; i++){
                frames[i-1] = new TextureRegion(assetManager.get("splash/cometSheet"+i+".png", Texture.class));
            }
            cometAnim = new Animation<TextureRegion>(0.05f, frames);
            cometAnim.setPlayMode(Animation.PlayMode.NORMAL);
            comet = new Image(assetManager.get("splash/cometSheet1.png", Texture.class));
            comet.setPosition(0, 264);
            stage.addActor(comet);
            game.backLoad.setVisible(false);
            game.barLoad.setVisible(false);
            game.endBarLoad.setVisible(false);
            //game.backLoad
            begin = 1;
        }else if(begin == 1){
            if(cometAnim.isAnimationFinished(animTime) && assetManager.isLoaded("noButt.png")){
                stage.addAction(Actions.run(new Runnable(){
                    @Override
                    public void run(){
                        game.setScreen(new MenuScreen(game, true));
                    }
                }));
            }else{
                comet.setDrawable(new TextureRegionDrawable(cometAnim.getKeyFrame(animTime)));
                if(cometAnim.getKeyFrameIndex(animTime) == 13){
                    star.setZIndex(1000);
                    whiteBack.setZIndex(1001);
                    RunnableAction run = Actions.run(new Runnable(){
                        @Override
                        public void run(){
                            star.addAction(Actions.parallel(Actions.alpha(1f, 2f), Actions.sizeTo(1920, 1920, 2f), Actions.moveTo(-420, 0, 2f)));
                            whiteBack.addAction(Actions.sequence(Actions.delay(0.2f), Actions.fadeIn(1.8f)));
                        }
                    });
                    stage.addAction(run);
                }
                animTime += _delta;
            }
        }
    }

    public void setProgress(){
        float currentProg = assetManager.getProgress();
        float newSize = (currentProg*577.35f);
        game.barLoad.setWidth(newSize);
        game.endBarLoad.setX(game.barLoad.getX()+game.barLoad.getWidth());
    }
}
