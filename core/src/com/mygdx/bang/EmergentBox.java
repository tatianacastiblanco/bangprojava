package com.mygdx.bang;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;

public class EmergentBox extends Table{
    Image blackTexture, closeButt, auxButt1, auxButt2;
    Texture boxBack;
    Table box;
    Label textLabel;

    EmergentBox(BitmapFont font){
        Pixmap p = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        p.setColor(0, 0, 0, 0.7f);
        p.fill();
        blackTexture = new Image(new Texture(p));
        blackTexture.setBounds(0, 0, 1080, 1920);
        //font = new BitmapFont(Gdx.files.internal("delius.png"));
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, new Color(1, 1, 1, 1));
        font.getData().markupEnabled = true;
        textLabel = new Label("jksdfaksdflasdhflas", labelStyle);
        textLabel.setAlignment(Align.center);
        textLabel.setFontScale(1.2f);
        textLabel.setWrap(true);
        closeButt = new Image(new Texture("closeEmergent.png"));
        closeButt.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                EmergentBox.this.setVisible(false);
                auxButt1.setVisible(false);
                blackTexture.setVisible(false);
                return true;
            }
        });
        auxButt1 = new Image();
        auxButt1.setVisible(false);
        auxButt2 = new Image();
        auxButt2.setVisible(false);
        boxBack = new Texture("emergente.png");

        box = new Table();
        Skin s = new Skin();
        s.add("box", boxBack);

        box.background(s.getDrawable("box"));
        box.add(textLabel).width(800).padLeft(30).center().expand();

        add(closeButt).size(127, 128).expandX().right().colspan(2);
        row();
        add(box).size(800, 600).colspan(2);
        row();
        add(auxButt1).size(250, 150).center().expand();
        this.setSize(800, 878);
        setPosition(120, 700);
    }

    public void setVisible2(boolean visible){
        if(!visible)
            auxButt1.setVisible(false);
        closeButt.setVisible(visible);
        blackTexture.setZIndex(1000);
        setZIndex(1001);
        blackTexture.setVisible(visible);
        setVisible(visible);
    }

    public void showButt2(){
        getCell(closeButt).size(127, 128).expandX().right().colspan(2);
        getCell(box).size(800, 600).colspan(2);
        getCell(auxButt1).size(331, 134).right().padRight(30).expand();
        add(auxButt2).size(331, 134).left().padLeft(30).expand();
    }

    public void hideButt2(){
        clearChildren();
        add(closeButt).size(127, 128).expandX().right().colspan(2);
        row();
        add(box).size(800, 600).colspan(2);
        row();
        add(auxButt1).size(431, 134).center().expand();
    }

    public void setText(String text){
        add(closeButt).size(127, 128).expandX().right().colspan(2);
        row();
        add(box).size(800, 600).colspan(2);
        row();
        add(auxButt1).size(250, 150).center().expand();
        this.setSize(800, 878);
        setPosition(120, 700);
        textLabel.setText(text);
    }
}
