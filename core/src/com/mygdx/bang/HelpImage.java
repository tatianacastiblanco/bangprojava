package com.mygdx.bang;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;

public class HelpImage{
    Image help;
    Table textBox;
    Image background;
    Label label;
    Label.LabelStyle labelStyle;
    boolean show = false;

    HelpImage(Texture helpT, Texture back, Label.LabelStyle _labelStyle){
        //labelStyle = _labelStyle;
        labelStyle = new Label.LabelStyle();
        labelStyle.font = new BitmapFont(Gdx.files.internal("delius.fnt"));
        labelStyle.fontColor = Color.BLACK;
        labelStyle.font.getData().setScale(0.9f);
        help = new Image(helpT);
        help.setOrigin(45, 45);
        help.getColor().a = 0.5f;
        help.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                show();
                return true;
            }
        });

        background = new Image(back);
        background.setColor(1, 1, 1, 0.7f);

        textBox = new Table();
        textBox.setBackground(background.getDrawable());
        textBox.setVisible(false);
    }

    public void setHelp(String text, float[] bounds){
        //background.setSize(bounds[4], bounds[5]);
        help.setPosition(bounds[0], bounds[1]);
        textBox.clearChildren();
        label = new Label(text, labelStyle);
        //label.setFontScale(0.8f);
        label.setAlignment(Align.center);
        //label.setColor(Color.BLACK);
        label.setWrap(true);
        label.setWidth(bounds[4]-50);
        label.layout();
        Gdx.app.log("setHelp", String.valueOf(label.getGlyphLayout().width)+"valor"+(bounds[4]-50));
        textBox.add(label).size(bounds[4]-50, bounds[5]-50).center().expand();
        textBox.setBounds(bounds[2], bounds[3], bounds[4], bounds[5]);
        textBox.setZIndex(1000);
        help.setZIndex(1001);
    }

    public void show(){
        if(show){
            textBox.setVisible(false);
            show = false;
        }else{
            textBox.setZIndex(1000);
            textBox.setVisible(true);
            show = true;
        }
    }

    public void add(Stage stage){
        stage.addActor(help);
        stage.addActor(textBox);
    }

    public void setText(String text){
        textBox.clearChildren();
        label = new Label(text, labelStyle);
        label.setAlignment(Align.center);
        label.setColor(Color.BLACK);
        label.setWidth(textBox.getWidth()-50);
        label.setWrap(true);
        label.pack();
        textBox.add(label).size(textBox.getWidth()-50, textBox.getHeight()-50).center().expand();
    }

    public void showHelp(boolean setShow){
        if(setShow){
            help.setZIndex(1000);
            help.setVisible(true);
        }else{
            help.setVisible(false);
            show = true;
            show();
        }
    }

    public void resetListener(){
        help.clearListeners();
        help.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                show();
                return true;
            }
        });
    }
}
