package com.mygdx.bang;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;

import java.util.Random;

import javax.smartcardio.Card;

public class MenuCards extends Table{
    Image blackTexture;
    Image menuButt, problem;
    Label problema, solucion;
    Table items;
    Array<MenuSlot> slots;
    Image arrow;
    String[] helps = {
            "Lorem ipsum dolor sit amet consectetur adipiscing elit magna sociis habitant luctus posuere eu accumsan, ullamcorper "+
            "ultricies laoreet hendrerit condimentum etiam orci neque nostra integer penatibus himenaeos. Dignissim penatibus aptent commodo ut nisi congue sollicitudin velit, pellentesque turpis rutrum dapibus pretium mauris scelerisque.",
            "ultricies laoreet hendrerit condimentum etiam orci neque nostra integer penatibus himenaeos. Dignissim penatibus aptent commodo ut nisi congue sollicitudin velit, pellentesque turpis rutrum dapibus pretium mauris scelerisque."+
            "Lorem ipsum dolor sit amet consectetur adipiscing elit magna sociis habitant luctus posuere eu accumsan, ullamcorper "
    };

    boolean toNext = false;
    boolean block = false;
    int currentTutorial = -1;

    Array<CardItem> cards;

    LevelScreen level;

    MenuCards(LevelScreen lvl){
        level = lvl;
        Pixmap p = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        p.setColor(Color.BLACK);
        p.fill();
        blackTexture = new Image(new Texture(p));
        blackTexture.setColor(0, 0, 0, 0.5f);
        blackTexture.setBounds(0, 0, 1080, 1920);
        blackTexture.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                if(!block)
                    close();
                return true;
            }
        });
        blackTexture.setVisible(false);

        arrow = new Image(level.skinCharacters.getDrawable("hand"));
        arrow.setSize(200, 100);
        arrow.setVisible(false);

        menuButt = new Image(new Texture("menuButt.png"));
        menuButt.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                if(!block){
                    if(items.isVisible())
                        close();
                    else
                        show();
                }
                return true;
            }
        });

        cards = new Array<CardItem>();

        items = new Table();
        items.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture("menuBack.png"))));

        slots = new Array<MenuSlot>(6);
        Texture t1 = new Texture("bangButt.png");
        Texture t2 = new Texture("textSlot.png");
        Texture t3 = new Texture("cardSlot.png");
        Texture[] texturasBack = {t1, t2, t2, t3, t3, t3};
        float[][] backSizes = {{356, 288}, {332, 198}, {332, 198}, {172, 230}, {172, 230}, {172, 230}};
        float[] paddings = {82, 70, 17, 17, 15, 15};
        for(int i = 0; i < texturasBack.length; i++){
            MenuSlot slot;
            if(i < 3){
                slot = new MenuSlot(texturasBack[i]);
            }else{
                slot = new MenuSlot(texturasBack[i]);
            }
            slots.add(slot);
            if(i != 5){
                items.add(slot).size(backSizes[i][0], backSizes[i][1]).padTop(paddings[i]).expandX();
                items.row();
            }else{
                items.add(slot).size(backSizes[i][0], backSizes[i][1]).padTop(paddings[i]).align(Align.top).expand();
            }
        }

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        BitmapFont f = new BitmapFont(Gdx.files.internal("delius.fnt"));
        f.getData().setLineHeight(f.getData().lineHeight-5);
        f.getData().padTop = 70;
        f.getData().markupEnabled = true;
        labelStyle.font = f;
        labelStyle.fontColor = Color.WHITE;
        problema = new Label("", labelStyle);
        problema.setFontScale(0.9f);
        problema.setAlignment(Align.top);
        problema.setWrap(true);
        solucion = new Label("", labelStyle);
        solucion.setFontScale(0.9f);
        solucion.setAlignment(Align.top);
        solucion.setWrap(true);

        slots.get(0).addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                if(!block){
                    level.showEmergent("[#511577]¿Estás seguro de querer volver al menú?");
                    level.emergentBox.auxButt1.setVisible(true);
                    level.emergentBox.auxButt1.clearListeners();
                    level.emergentBox.auxButt1.setDrawable(level.skinCharacters.getDrawable("aceptButt"));
                    level.emergentBox.auxButt1.addListener(new InputListener(){
                        @Override
                        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                            level.emergentBox.auxButt1.setVisible(false);
                            level.emergentBox.setVisible(false);
                            blackTexture.setColor(0, 0, 0, 0);
                            blackTexture.setZIndex(1000);
                            blackTexture.setVisible(true);
                            blackTexture.addAction(Actions.sequence(Actions.fadeIn(1), Actions.run(new Runnable(){
                                @Override
                                public void run(){
                                    level.showLoad("Menu");
                                    //level.game.setScreen(new MenuScreen(level.game));
                                }
                            })));
                            return true;
                        }
                    });
                }
                return true;
            }
        });
        slots.get(1).setTable();
        //slots.get(1).setItem(problema);
        slots.get(1).addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                if(!block || currentTutorial == 1){
                    if(!slots.get(1).completeText.equals("")){
                        level.showEmergent(slots.get(1).completeText);
                    }
                }
                return true;
            }
        });
        slots.get(2).setTable();
        slots.get(2).setItem(solucion);
        slots.get(2).addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                if(!block || currentTutorial == 2) {
                    if (!slots.get(2).completeText.equals("")){
                        level.showEmergent(slots.get(2).completeText);
                    }
                }
                return true;
            }
        });

        add(menuButt).size(160, 156).align(Align.right).expand().padBottom(37);
        row();
        add(items).size(481, 1656);
        setPosition(568, 39);
        setSize(481, 1812);
        items.setVisible(false);
    }

    public void show(){
        blackTexture.setColor(0, 0, 0, 0.5f);
        blackTexture.setVisible(true);
        blackTexture.setZIndex(1000);
        items.setVisible(true);
        level.musicButt.setVisible(true);
        level.musicButt.setZIndex(1000);
        level.soundButt.setVisible(true);
        level.soundButt.setZIndex(1000);
        setZIndex(1000);
    }

    public void close(){
        blackTexture.setVisible(false);
        items.setVisible(false);
        level.musicButt.setVisible(false);
        level.soundButt.setVisible(false);
    }

    public void addCard(CardItem item){
        if(cards.size != 0){
            if(cards.size == 1){
                slots.get(4).setItem(item);
            }else{
                slots.get(5).setItem(item);
            }
        }else{
            slots.get(3).setItem(item);
        }
        item.getColor().a = 1;
        item.setVisible(true);
        cards.add(item);
    }

    public void getRandHelp(){
        Random rand = new Random();
        int randText = rand.nextInt(2);
        level.showEmergent(helps[randText]);
    }

    public void setProblemaText(String text, Drawable _problem){
        slots.get(1).completeText = text;
        if(text.length()>35){
            text = text.substring(0, 35);
            text += "...";
        }
        String newText = text.replace("[#511577]", "[#ffffff]");
        //problema.setText(newText);
        problem = new Image(_problem);
        slots.get(1).setItem(problem);
    }

    public void setSolucionText(String text){
        slots.get(2).completeText = text;
        if(text.length()>35){
            text = text.substring(0, 35);
            text += "...";
        }
        String newText = text.replace("[#511577]", "[#ffffff]");
        solucion.setText(newText);
    }

    public void showWithArrow(String item){
        block = true;
        show();
        if(item.equals("Card1")){
            currentTutorial = 3;
            final MenuSlot currentItem = slots.get(3);
            Vector2 itemPos = slots.get(3).localToStageCoordinates(new Vector2(0, 0));
            Vector2 arrowPos = new Vector2( (itemPos.x-arrow.getWidth())-80, (itemPos.y+slots.get(3).getHeight()/2)-arrow.getHeight()/2);
            arrow.setPosition(arrowPos.x, arrowPos.y);
            arrow.setZIndex(1001);
            arrow.setVisible(true);
            arrow.addAction(Actions.forever(Actions.sequence(Actions.moveTo(arrowPos.x+70, arrowPos.y, 0.8f), Actions.moveTo(arrowPos.x-70, arrowPos.y, 0.8f))));
            final InputListener nextScreen = new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                    toNext = true;
                    currentItem.removeListener(this);
                    return true;
                }
            };
            currentItem.addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                    Gdx.app.log("Enrte", "Entre");
                    block = false;
                    arrow.setVisible(false);
                    arrow.clearActions();
                    currentItem.removeListener(this);
                    //currentItem.addListener(nextScreen);
                    toNext = true;
                    return true;
                }
            });
        }else if(item.equals("Problematica")){
            currentTutorial = 1;
            final MenuSlot currentItem = slots.get(1);
            Vector2 itemPos = currentItem.localToStageCoordinates(new Vector2(0, 0));
            Vector2 arrowPos = new Vector2( (itemPos.x-arrow.getWidth())-80, (itemPos.y+currentItem.getHeight()/2)-arrow.getHeight()/2);
            arrow.setPosition(arrowPos.x, arrowPos.y);
            arrow.setZIndex(1001);
            arrow.setVisible(true);
            arrow.clearActions();
            arrow.addAction(Actions.forever(Actions.sequence(Actions.moveTo(arrowPos.x+70, arrowPos.y, 0.8f), Actions.moveTo(arrowPos.x-70, arrowPos.y, 0.8f))));
            currentItem.addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                    block = false;
                    arrow.setVisible(false);
                    arrow.clearActions();
                    currentItem.removeListener(this);
                    toNext = true;
                    return true;
                }
            });
        }else if(item.equals("Solucion")){
            currentTutorial = 2;
            final MenuSlot currentItem = slots.get(2);
            Vector2 itemPos = currentItem.localToStageCoordinates(new Vector2(0, 0));
            Vector2 arrowPos = new Vector2( (itemPos.x-arrow.getWidth())-80, (itemPos.y+currentItem.getHeight()/2)-arrow.getHeight()/2);
            arrow.setPosition(arrowPos.x, arrowPos.y);
            arrow.setZIndex(1001);
            arrow.setVisible(true);
            arrow.clearActions();
            arrow.addAction(Actions.forever(Actions.sequence(Actions.moveTo(arrowPos.x+70, arrowPos.y, 0.8f), Actions.moveTo(arrowPos.x-70, arrowPos.y, 0.8f))));
            currentItem.addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                    block = false;
                    arrow.setVisible(false);
                    currentItem.removeListener(this);
                    toNext = true;
                    return true;
                }
            });
        }
    }
}
