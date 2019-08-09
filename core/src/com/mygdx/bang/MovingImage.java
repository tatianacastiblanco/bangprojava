package com.mygdx.bang;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;

public class MovingImage extends Image{
    BANG game;

    Array<Texture> textures;
    TextureRegion[] frames;
    Animation<TextureRegion> animation;
    float animTime;
    int type;
    int dir = 0;
    float time = 0;
    float vel;
    float randTime = 0;
    Sound sound;

    MovingImage(BANG g, Texture texture, int _type, float v, int cols, int rows){
        TextureRegionDrawable textureR = new TextureRegionDrawable(new TextureRegion(texture));
        if(_type == 0){
            TextureRegion[][] tmp = TextureRegion.split(texture, texture.getWidth()/cols, texture.getHeight()/rows);
            frames = new TextureRegion[cols*rows];
            int index = 0;
            for (int i = 0; i < rows; i++){
                for (int j = 0; j < cols; j++){
                    frames[index++] = tmp[i][j];
                }
            }
            animation = new Animation<TextureRegion>(0.2f, frames);
            animation.setPlayMode(Animation.PlayMode.LOOP);
            textureR = new TextureRegionDrawable(animation.getKeyFrame(animTime));
            setSize(500, 500);
        }

        game = g;

        type = _type;
        setPosition(-150, -1000);
        vel = v;
        randTime = MathUtils.random(4, 7);

        setDrawable(textureR);
    }

    public void update(float delta){
        float x = getX();
        float y = getY();
        float w = getWidth();
        float h = getHeight();
        if(type == 0){
            setSize(450, 450);
            animTime += delta;
            TextureRegion currentFrame = animation.getKeyFrame(animTime);
            TextureRegionDrawable t = new TextureRegionDrawable(currentFrame);
            setDrawable(t);
            if(x+w<0 || x>1080 || y+h<0 || y>1920){
                if(time <= 0){
                    float rand = MathUtils.random(0, 4);
                    float rand2;
                    if(rand == 0){
                        rand2 = MathUtils.random(300, 1900);
                        setPosition(-w, rand2);
                        dir = 0;
                        setRotation(0);
                    }else if(rand == 1){
                        rand2 = MathUtils.random(300, 1900);
                        setPosition(1080, rand2);
                        dir = 1;
                        setRotation(270);
                    }else if(rand == 2){
                        rand2 = MathUtils.random(-w, 1080);
                        setPosition(rand2, 1920);
                        if(rand2 >540){
                            dir = 1;
                            setRotation(270);
                        }else{
                            dir = 0;
                            setRotation(0);
                        }
                    }else{
                        rand2 = MathUtils.random(-w, 1080);
                        setPosition(rand2, 1920);
                        if(rand2 >540){
                            dir = 1;
                            setRotation(270);
                        }else{
                            dir = 0;
                            setRotation(0);
                        }
                    }
                    time = 0.5f;
                }else{
                    time -= delta;
                }
            }else{
                y -= (vel*delta);
                if(dir == 0){
                    x += (vel*delta);
                }else{
                    x -= (vel*delta);
                }
                setPosition(x, y);
            }
        }else if(type == 1){
            if(randTime != -1){
                if(time >= randTime){
                    float rand = MathUtils.random(0, 1820);
                    float rand2 = MathUtils.random(0, 2);
                    randTime = -1;
                    RunnableAction run = Actions.run(new Runnable(){
                        @Override
                        public void run(){
                            randTime = MathUtils.random(4, 7);
                        }
                    });
                    this.setY(rand);
                    if(rand2 == 0){
                        dir = 0;
                        this.addAction(Actions.sequence(Actions.delay(1), Actions.moveTo(1080+w, rand, 2), run));
                    }else{
                        dir = 1;
                        this.addAction(Actions.sequence(Actions.delay(1), Actions.moveTo(-w, rand, 1), run));
                    }
                    time = 0;
                    if(game.soundOn)
                        sound.play(0.1f);
                }else{
                    time += delta;
                }
            }
        }else{
            if(x+w<0 || x>1080){
                float rand = MathUtils.random(0, 1820);
                float rand2 = MathUtils.random(0, 2);
                if(rand2 == 0){
                    dir = 0;
                    this.setPosition(-100, rand);
                }else{
                    dir = 1;
                    this.setPosition(1080, rand);
                }
            }else{
                if(dir == 0){
                    x += (vel*delta);
                }else{
                    x -= (vel*delta);
                }
                setPosition(x, y);
            }
        }
    }

    public void setSound(Sound _sound){
        sound = _sound;
    }
}
