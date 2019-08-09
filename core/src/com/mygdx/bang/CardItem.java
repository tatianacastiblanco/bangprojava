package com.mygdx.bang;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;

public class CardItem extends Table{
    int maze = 0;
    int cardNum = 0;
    boolean onSlot = false;
    Image head;
    Label label;
    Table content;

    CardItem(Drawable defaultD, Drawable defaultB, Label.LabelStyle labelStyle){
        super();

        head = new Image(defaultD);
        label = new Label("Carta1", labelStyle);
        label.setAlignment(Align.center);
        label.setWrap(true);
        label.setWidth(700);

        /*Table labelContent = new Table();
        labelContent.add(label).padLeft(40).center().expand();*/

        content = new Table();
        content.add(head).size(780, 142);
        content.row();
        content.add(label).padLeft(40).width(700).center().expand();

        add(content).size(780, 1222);
        setBounds(0, 0, 780, 1222);
        background(defaultB);
        setOrigin(0, 0);
        setTransform(true);
    }

    public void show(){

    }
}
