package com.mygdx.bang;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

public class IdeaBox extends Stack{
    MissionScreen levelScreen;
    Label.LabelStyle style;
    Table content;
    Image ideaI;
    Label label;

    IdeaBox(final MissionScreen level, String _text, Texture t, Label.LabelStyle labelStyle){
        levelScreen = level;
        style = labelStyle;

        ideaI = new Image(t);

        label = new Label(_text, style);
        label.setFontScale(1.2f);
        label.setWidth(720);
        label.setWrap(true);
        label.setAlignment(Align.center);
        label.invalidate();
        label.pack();

        float newH = label.getHeight();
        ideaI.setSize(800, newH+100);

        content = new Table();
        content.add(label).size(710, newH).align(Align.topLeft).pad(30, 35, 0, 0).grow();
        content.setSize(800, newH+100);
        content.setBackground(new TextureRegionDrawable(new TextureRegion(t)));
        content.setTransform(true);

        content.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                //levelScreen.currentIdea = label.getText().toString();
                return true;
            }
        });

        Gdx.app.log("SizeS", "height"+ideaI.getHeight());
    }

    public void resizeIdea(String text){
        label = new Label(text, style);
        label.setFontScale(1.2f);
        label.setWidth(720);
        label.setWrap(true);
        label.setAlignment(Align.center);
        label.invalidate();
        label.pack();

        content.clearChildren();

        float newH = label.getHeight();
        ideaI.setSize(800, newH+100);

        content.add(label).size(710, newH).align(Align.topLeft).pad(30, 35, 0, 0).grow();
        content.setSize(800, newH+100);
        content.layout();

        Gdx.app.log("SizeS", "height"+ideaI.getHeight());
    }
}
