package com.mygdx.bang;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class Ship extends Table{
    Table shipT;
    Image shipI;
    Image fire;
    TextureRegionDrawable[] regions;
    Animation<TextureRegionDrawable> anim;
    float animTime = 0;

    Ship(Drawable drawable, TextureRegionDrawable[] _regions){
        super();
        regions = _regions;
        anim = new Animation<TextureRegionDrawable>(0.2f, _regions);
        anim.setPlayMode(Animation.PlayMode.LOOP);
        shipI = new Image(drawable);
        fire = new Image(_regions[0]);
        add(shipI).size(244, 398);
        row();
        add(fire).size(110, 161).center().expand();
        setSize(244, 559);
        setTransform(true);
    }

    public void update(float delta){
        fire.setDrawable(anim.getKeyFrame(animTime));
        pack();
        animTime += delta;
    }
}
