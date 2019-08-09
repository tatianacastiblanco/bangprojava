package com.mygdx.bang;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.FocusListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;

import javax.tools.Tool;

public class MenuTools extends Table{
    StoryboardScreen storyboard;
    Stage stage;

    ToolItem currentItem;

    int scrolling = -1;
    ScrollPane scroll;
    Image scrollArrow1, scrollArrow2;
    Table content;
    Image closeButt;
    Array<MenuSlot> slots;

    boolean visible = false;

    Array<ToolItem> ballon1, ballon2, ballon3, charc1, charc2, charc3, charc4, charc5, charc6, square, rectangle, line, problem, answer;

    Label.LabelStyle labelStyle;

    String[] toolDrawables = {"balloon1", "balloon2", "balloon3", "charc1", "charc2", "charc3", "charc4", "charc5", "charc6", "square", "rectangle", "line", "problem", "answer", "balloon4", "balloon5", "balloon6", "balloon7", "balloon8", "balloon9"};

    Skin storySkin;
    boolean activeTool;

    MenuTools(StoryboardScreen _storyboard){
        storyboard = _storyboard;
        storySkin = storyboard.skinTools;

        //currentItem = storyboard.storyboard.currentItem;

        scrollArrow1 = new Image(storySkin.getDrawable("arrow"));
        scrollArrow1.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                scrolling = 2;
                //scroll.setScrollX(scroll.getScrollX()+10);
                //scrollArrow2.setRotation(scrollArrow2.getRotation()+10);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                scrolling = -1;
            }
        });
        scrollArrow2 = new Image(storySkin.getDrawable("arrow"));
        scrollArrow2.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                scrolling = 1;
                //scroll.setScrollX(scroll.getScrollX()-10);
                //scrollArrow2.setRotation(scrollArrow2.getRotation()+10);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                scrolling = -1;
            }
        });

        labelStyle = new Label.LabelStyle();
        labelStyle.font = new BitmapFont(Gdx.files.internal("delius.fnt"));
        labelStyle.font.getData().setScale(0.9f);
        labelStyle.fontColor = Color.BLACK;

        ballon1 = new Array<ToolItem>();
        ballon2 = new Array<ToolItem>();
        ballon3 = new Array<ToolItem>();
        charc1 = new Array<ToolItem>();
        charc2 = new Array<ToolItem>();
        charc3 = new Array<ToolItem>();
        charc4 = new Array<ToolItem>();
        charc5 = new Array<ToolItem>();
        charc6 = new Array<ToolItem>();
        square = new Array<ToolItem>();
        rectangle = new Array<ToolItem>();
        line = new Array<ToolItem>();
        problem = new Array<ToolItem>();
        answer = new Array<ToolItem>();

        //Texture textureSlot = new Texture("badlogic.jpg");

        closeButt = new Image(storySkin.getDrawable("toolsButt"));
        closeButt.setAlign(Align.center);
        closeButt.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                if(scroll.isVisible())
                    disableScroll();
                else
                    show();
                return true;
            }
        });

        content = new Table();
        slots = new Array<MenuSlot>(20);
        for(int i = 0; i < 24; i++){
            final MenuSlot slot = new MenuSlot();
            //final ToolItem item = new ToolItem(MenuTools.this, slot, storySkin.getDrawable(toolDrawables[i]));
            final ToolItem item;
            item = new ToolItem(MenuTools.this, slot, storySkin.getDrawable("default"+(i+1)));
            //item.setTexture(new Texture("balloon1.png"));
            //item.setTexture();
            //ballon1.add(item);
            slot.setItem(item);
            slots.add(slot);
            content.add(slot).size(300, 300);
            if(i == 11)
                content.row();
        }

        ScrollPane.ScrollPaneStyle scrollPaneStyle = new ScrollPane.ScrollPaneStyle();
        scrollPaneStyle.background = storySkin.getDrawable("backScroll");
        scroll = new ScrollPane(content, scrollPaneStyle);
        scroll.setVisible(false);

        setTransform(true);
        add(closeButt).size(281, 143).expandX().colspan(3).align(Align.center);
        row();
        add(scrollArrow2).size(100, 600);
        add(scroll).width(880);
        add(scrollArrow1).size(100, 600);

        scrollArrow1.setOrigin(50, 300);
        scrollArrow1.setRotation(180);

        setPosition(0, -600);
        setSize(1080, 743);
        bottom().left();
    }

    public void disableScroll(){
        scroll.cancel();
        scroll.setScrollingDisabled(true, true);
        MoveToAction move = new MoveToAction();
        move.setPosition(0, -600);
        move.setDuration(0.4f);
        RunnableAction run = new RunnableAction(){
            @Override
            public void run(){
                //tab.setDrawable(skin.getDrawable("show"));
                scroll.setVisible(false);
                visible = false;
            }
        };
        this.addAction(Actions.sequence(move, run));
    }

    public void show(){
        scroll.setVisible(true);
        scroll.setScrollingDisabled(false, false);
        scroll.layout();
        scroll.updateVisualScroll();
        MoveToAction move = new MoveToAction();
        move.setPosition(0, 0);
        move.setDuration(0.4f);
        RunnableAction run = new RunnableAction(){
            @Override
            public void run(){
                //tab.setDrawable(skin.getDrawable("close"));
                visible = true;
            }
        };
        this.addAction(Actions.sequence(move, run));
    }

    public void showEdit(){
        //storyboard.move.setPosition(storyboard.currentItem.getX()+storyboard.currentItem.getWidth()/2-115, storyboard.currentItem.getY()+storyboard.currentItem.getHeight()+10);
        storyboard.editT.setPosition(storyboard.currentItem.points[16], storyboard.currentItem.points[17]);
        storyboard.editT.setRotation(storyboard.currentItem.getRotation());
        storyboard.editT.setVisible(true);
        //storyboard.rotate.setPosition(storyboard.currentItem.getX()+storyboard.currentItem.getWidth()/2+10, storyboard.currentItem.getY()+storyboard.currentItem.getHeight()+10);
        storyboard.rotate.setPosition(storyboard.currentItem.points[18], storyboard.currentItem.points[19]);
        storyboard.rotate.setRotation(storyboard.currentItem.getRotation());
        storyboard.rotate.setVisible(true);
        //storyboard.sizePoint[0].setPosition(storyboard.currentItem.getX()-40, storyboard.currentItem.getY()-40);
        //storyboard.sizePoint[0].setPosition(storyboard.currentItem.newRectBounds[0]-40, storyboard.currentItem.newRectBounds[1]-40);
        storyboard.sizePoint[0].setPosition(storyboard.currentItem.points[0], storyboard.currentItem.points[1]);
        storyboard.sizePoint[0].setZIndex(1000);
        storyboard.sizePoint[0].setVisible(true);
        //storyboard.sizePoint[1].setPosition(storyboard.currentItem.getX()+storyboard.currentItem.getWidth()/2-20, storyboard.currentItem.getY()-40);
        //storyboard.sizePoint[1].setPosition(storyboard.currentItem.newRectBounds[0]+storyboard.currentItem.newRectBounds[2]/2-20, storyboard.currentItem.newRectBounds[1]-40);
        storyboard.sizePoint[1].setPosition(storyboard.currentItem.points[2], storyboard.currentItem.points[3]);
        storyboard.sizePoint[1].setZIndex(1000);
        storyboard.sizePoint[1].setVisible(true);
        //storyboard.sizePoint[2].setPosition(storyboard.currentItem.getX()+storyboard.currentItem.getWidth()+40, storyboard.currentItem.getY()-40);
        //storyboard.sizePoint[2].setPosition(storyboard.currentItem.newRectBounds[0]+storyboard.currentItem.newRectBounds[2], storyboard.currentItem.newRectBounds[1]-40);
        storyboard.sizePoint[2].setPosition(storyboard.currentItem.points[4], storyboard.currentItem.points[5]);
        storyboard.sizePoint[2].setZIndex(1000);
        storyboard.sizePoint[2].setVisible(true);
        //storyboard.sizePoint[3].setPosition(storyboard.currentItem.getX()+storyboard.currentItem.getWidth()+40, storyboard.currentItem.getY()+storyboard.currentItem.getHeight()/2-20);
        //storyboard.sizePoint[3].setPosition(storyboard.currentItem.newRectBounds[0]+storyboard.currentItem.newRectBounds[2], storyboard.currentItem.newRectBounds[1]+storyboard.currentItem.newRectBounds[3]/2-20);
        storyboard.sizePoint[3].setPosition(storyboard.currentItem.points[6], storyboard.currentItem.points[7]);
        storyboard.sizePoint[3].setZIndex(1000);
        storyboard.sizePoint[3].setVisible(true);
        //storyboard.sizePoint[4].setPosition(storyboard.currentItem.getX()+storyboard.currentItem.getWidth()+40, storyboard.currentItem.getY()+storyboard.currentItem.getHeight()+40);
        //storyboard.sizePoint[4].setPosition(storyboard.currentItem.newRectBounds[0]+storyboard.currentItem.newRectBounds[2], storyboard.currentItem.newRectBounds[1]+storyboard.currentItem.newRectBounds[3]);
        storyboard.sizePoint[4].setPosition(storyboard.currentItem.points[8], storyboard.currentItem.points[9]);
        storyboard.sizePoint[4].setZIndex(1000);
        storyboard.sizePoint[4].setVisible(true);
        //storyboard.sizePoint[5].setPosition(storyboard.currentItem.getX()+storyboard.currentItem.getWidth()/2-20, storyboard.currentItem.getY()+storyboard.currentItem.getHeight()+40);
        //storyboard.sizePoint[5].setPosition(storyboard.currentItem.newRectBounds[0]+storyboard.currentItem.newRectBounds[2]/2-20, storyboard.currentItem.newRectBounds[1]+storyboard.currentItem.newRectBounds[3]);
        storyboard.sizePoint[5].setPosition(storyboard.currentItem.points[10], storyboard.currentItem.points[11]);
        storyboard.sizePoint[5].setZIndex(1000);
        storyboard.sizePoint[5].setVisible(true);
        //storyboard.sizePoint[6].setPosition(storyboard.currentItem.getX()-40, storyboard.currentItem.getY()+storyboard.currentItem.getHeight()+40);
        //storyboard.sizePoint[6].setPosition(storyboard.currentItem.newRectBounds[0]-40, storyboard.currentItem.newRectBounds[1]+storyboard.currentItem.newRectBounds[3]);
        storyboard.sizePoint[6].setPosition(storyboard.currentItem.points[12], storyboard.currentItem.points[13]);
        storyboard.sizePoint[6].setZIndex(1000);
        storyboard.sizePoint[6].setVisible(true);
        //storyboard.sizePoint[7].setPosition(storyboard.currentItem.getX()-40, storyboard.currentItem.getY()+storyboard.currentItem.getHeight()/2-20);
        //storyboard.sizePoint[7].setPosition(storyboard.currentItem.newRectBounds[0]-40, storyboard.currentItem.newRectBounds[1]+storyboard.currentItem.newRectBounds[3]/2-20);
        storyboard.sizePoint[7].setPosition(storyboard.currentItem.points[14], storyboard.currentItem.points[15]);
        storyboard.sizePoint[7].setZIndex(1000);
        storyboard.sizePoint[7].setVisible(true);
    }

    public void reAsignItem(MenuSlot slot, Drawable d){
        ToolItem toolItem = new ToolItem(this, slot, d);
        slot.item = null;
        slot.setItem(toolItem);
    }

    public void scrollTo(){
        float currentScrollX = scroll.getScrollX();
        if(scrolling == 1)
            scroll.setScrollX(currentScrollX-30);
        if(scrolling == 2)
            scroll.setScrollX(currentScrollX+30);
    }
}
