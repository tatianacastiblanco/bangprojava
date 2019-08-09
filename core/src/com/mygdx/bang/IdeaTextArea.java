package com.mygdx.bang;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;

public class IdeaTextArea extends Table{
    Image ideaI, blackTexture;
    Table content;
    TextArea textField;
    TextField.TextFieldStyle style;
    int currentNumLines = 3;
    Image butt1, butt2;
    float heightTA = 280;

    IdeaTextArea(Texture t, TextField.TextFieldStyle textFieldStyle){
        Pixmap p = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        p.setColor(0, 0, 0, 0.7f);
        p.fill();
        blackTexture = new Image(new Texture(p));
        blackTexture.setBounds(0, 0, 1080, 1920);
        blackTexture.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                setBounds(76, 1200, 928, heightTA);
                textField.setText("");
                blackTexture.setVisible(false);
                setVisible(false);
                Gdx.input.setOnscreenKeyboardVisible(false);
                return true;
            }
        });
        blackTexture.setVisible(false);

        content = new Table();
        ideaI = new Image(t);
        ideaI.setSize(928, heightTA);
        style = new TextField.TextFieldStyle();
        style.font = new BitmapFont(Gdx.files.internal("delius.fnt"));
        style.font.getData().setScale(1.2f);
        style.fontColor = new Color(0.3176f, 0.0824f, 0.4666f, 1);

        textField = new TextArea("", style);
        textField.setWidth(900);
        //textField.setHeight(300);
        textField.setAlignment(Align.center);
        //textField.setMaxLength(50);
        textField.setFocusTraversal(false);
        textField.getStyle().font.getData().padLeft = 120;
        textField.getStyle().font.getData().padRight = 20;
        textField.setMaxLength(300);
        textField.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor){
                update();
            }
        });
        textField.setTextFieldListener(new TextField.TextFieldListener(){
            @Override
            public void keyTyped(TextField textField, char key){
                if((key == '\r' || key == '\n')){
                    textField.next(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT));
                }
                //update();
            }
        });
        textField.setTextFieldFilter(new TextField.TextFieldFilter(){
            @Override
            public boolean acceptChar(TextField textField2, char c){
                if(textField.getText().equals(" ") && (c == 62 || c == '\n' || c == '\r')){
                    Gdx.app.log("Accept", "Entre");
                    return false;
                }
                return true;
            }
        });

        content.setBackground(ideaI.getDrawable());
        content.add(textField).pad(20, 30, 10, 20).size(928, heightTA);

        butt1 = new Image();
        butt2 = new Image();

        add(content);
        row();
        add(butt1).size(431, 134).center().expandX();
        setVisible(false);
    }

    public void update(){
        Gdx.app.log("Update", String.valueOf(textField.getLines()));
        if(textField.getLines()>3){
            String text = textField.getText().trim();
            if(currentNumLines < textField.getLines()){
                /*String text = textField.getText().trim();
                heightTA += textField.getStyle().font.getLineHeight();
                ideaI.setHeight(heightTA);
                textField.remove();
                textField = new TextArea(text, style);
                textField.setMaxLength(300);
                textField.setWidth(928);
                textField.setHeight(heightTA);
                textField.setAlignment(Align.center);
                textField.setFocusTraversal(false);
                textField.addListener(new ChangeListener(){
                    @Override
                    public void changed(ChangeEvent event, Actor actor){
                        update();
                    }
                });
                currentNumLines++;
                content.clearChildren();
                content.add(textField).pad(20, 40, 10, 30).size(928, heightTA);
                clearChildren();
                add(content);
                row();
                add(butt1).size(200, 100).center().expandX();
                getStage().setKeyboardFocus(textField);
                textField.setCursorPosition(textField.getText().length());*/
                heightTA += textField.getStyle().font.getLineHeight();
                currentNumLines++;
            }else if(currentNumLines > textField.getLines()){
                /*ideaI.setHeight(ideaI.getHeight()-textField.getStyle().font.getLineHeight());
                textField.setHeight(textField.getHeight()-textField.getStyle().font.getLineHeight());
                textField.layout();
                currentNumLines--;*/
                heightTA -= textField.getStyle().font.getLineHeight();
                currentNumLines--;
            }
            ideaI.setHeight(heightTA);
            textField.remove();
            textField = new TextArea(text, style);
            textField.setMaxLength(300);
            textField.setWidth(928);
            textField.setHeight(heightTA);
            textField.setAlignment(Align.center);
            textField.setFocusTraversal(false);
            textField.addListener(new ChangeListener(){
                @Override
                public void changed(ChangeEvent event, Actor actor){
                    update();
                }
            });
            content.clearChildren();
            content.add(textField).pad(20, 40, 10, 30).size(928, heightTA);
            clearChildren();
            add(content);
            row();
            add(butt1).size(200, 100).center().expandX();
            getStage().setKeyboardFocus(textField);
            textField.setCursorPosition(textField.getText().length());
        }
    }
}

