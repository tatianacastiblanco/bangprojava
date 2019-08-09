package com.mygdx.bang;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;

public class IdeasScroll extends Table{
    ScrollPane scroll;
    Table ideasT;
    Array<IdeaBox> ideaBoxes;
    Texture ideaTxt;
    Label.LabelStyle labelStyle;
    TextField.TextFieldStyle textFieldStyle;
    IdeaTextArea textArea;
    MissionScreen lvl;
    IdeaBox current;

    IdeasScroll(MissionScreen level, Label.LabelStyle style){
        lvl = level;

        BitmapFont font = new BitmapFont();
        font.getData().setScale(5);

        ScrollPane.ScrollPaneStyle scrollStyle = new ScrollPane.ScrollPaneStyle();

        ideasT = new Table();

        scroll = new ScrollPane(ideasT, scrollStyle);
        scroll.setSize(300, 400);

        ideaTxt = lvl.assetManager.get("ideaBox.png", Texture.class);
        ideaBoxes = new Array<IdeaBox>();

        labelStyle = new Label.LabelStyle();
        labelStyle.font = style.font;
        labelStyle.fontColor = new Color(0.3176f, 0.0824f, 0.4666f, 1);

        textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.font = new BitmapFont(Gdx.files.internal("delius.fnt"));
        textFieldStyle.fontColor = new Color(0.3176f, 0.0824f, 0.4666f, 1);

        Texture ideaTxt2 = lvl.assetManager.get("ideaBox.png", Texture.class);
        textArea = new IdeaTextArea(ideaTxt2, textFieldStyle);

        this.add(scroll);
        setSize(956, 1300);
        this.align(Align.bottom);
    }

    public void addIdea(String idea){
        final IdeaBox ideaI = new IdeaBox(lvl, idea, ideaTxt, labelStyle);
        ideaI.content.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                Gdx.app.log("ShowIdea", "Entre");
                lvl.showEmergent("[#511577]¿Estás seguro de pasar a la siguiente fase con esta idea?");
                lvl.emergentBox.auxButt1.clearListeners();
                lvl.emergentBox.auxButt1.setDrawable(lvl.skinCharacters.getDrawable("aceptButt"));
                lvl.emergentBox.auxButt1.setVisible(true);
                lvl.emergentBox.auxButt1.addListener(new InputListener(){
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                        lvl.emergentBox.setVisible2(false);
                        Vector2 posItem = ideaI.content.localToStageCoordinates(new Vector2(0, 0));
                        ideaI.content.remove();
                        lvl.stage.addActor(ideaI.content);
                        ideaI.content.setPosition(posItem.x, posItem.y);
                        ideaI.content.setClip(true);
                        ideaI.label.setWrap(false);
                        ideaI.content.addAction(Actions.sequence(Actions.parallel(Actions.moveTo(1000, 1800, 0.8f), Actions.scaleTo(0, 0, 0.8f)), Actions.run(new Runnable(){
                            @Override
                            public void run(){
                                ideaI.content.setVisible(false);
                                lvl.menuCard.setSolucionText("[#511577]"+ideaI.label.getText().toString());
                                if(lvl.game.getSound())
                                    lvl.closeBag.play();
                                //lvl.nextCurrentScene();
                                if(lvl.showTutorials){
                                    lvl.menuCard.showWithArrow("Solucion");
                                    lvl.emergentBox.closeButt.addListener(new InputListener(){
                                        @Override
                                        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                                            if(lvl.menuCard.toNext){
                                                lvl.menuCard.toNext = false;
                                                lvl.emergentBox.closeButt.removeListener(this);
                                                lvl.nextCurrentScene();
                                            }
                                            return true;
                                        }
                                    });
                                }else{
                                    lvl.nextCurrentScene();
                                }
                            }
                        })));
                        return true;
                    }
                });
                return true;
            }
        });
        Image edit = new Image(lvl.assetManager.get("lapiz.png", Texture.class));
        edit.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                current = ideaI;
                String oldText = String.valueOf(ideaI.label.getText());
                showTextArea(lvl.stage);
                textArea.textField.setText(oldText);
                textArea.textField.setCursorPosition(oldText.length());
                return true;
            }
        });
        ideaBoxes.add(ideaI);
        ideasT.add(ideaI.content).size(847, ideaI.content.getHeight());
        ideasT.add(edit).size(77, 77).padLeft(32).expand().center();
        ideasT.row();
        ideasT.add().height(50);
        ideasT.row();
        scroll.addAction(Actions.run(new Runnable(){
            @Override
            public void run(){
                scroll.layout();
                scroll.setScrollPercentY(1);
                scroll.layout();
            }
        }));
    }

    public void showTextArea(Stage stage){
        textArea.setBounds(76, 1200, 928, 300);
        textArea.textField.setText("");
        textArea.butt1.setDrawable(lvl.skinCharacters.getDrawable("addButt"));
        textArea.butt1.clearListeners();
        textArea.butt1.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                hideTextArea();
                return true;
            }
        });
        textArea.blackTexture.setZIndex(1000);
        textArea.blackTexture.setVisible(true);
        textArea.setZIndex(1001);
        textArea.setVisible(true);
        Gdx.input.setOnscreenKeyboardVisible(true);
        stage.setKeyboardFocus(textArea.textField);
    }

    public void hideTextArea(){
        String text = textArea.textField.getText().trim();
        Gdx.input.setOnscreenKeyboardVisible(false);
        textArea.blackTexture.setVisible(false);
        textArea.setVisible(false);
        if(!text.isEmpty() && !text.equals(" ")){
            if(current != null){
                current.resizeIdea(text);
                ideasT.getCell(current.content).size(800, current.content.getHeight());
                ideasT.layout();
            }else{
                addIdea(text);
                if(lvl.game.getSound())
                    lvl.messageS.play(0.5f);
            }
        }
    }
}
