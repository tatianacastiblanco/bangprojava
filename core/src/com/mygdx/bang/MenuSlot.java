package com.mygdx.bang;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;

public class MenuSlot  extends Stack{
    ToolItem itemTool = null;
    CardItem itemT = null;
    Image item = null;
    Label itemL = null;
    String completeText = "";
    Table auxT;
    float[] sizeTable = {291, 198};

    MenuSlot(Texture texture){
        Image back = new Image(texture);
        this.add(back);
    }

    MenuSlot(){

    }

    public void setItem(CardItem _item){
        setLayoutEnabled(false);
        itemT = _item;
        add(itemT);
        itemT.setPosition(0, 0);
        itemT.needsLayout();
        itemT.pack();
    }

    public void setItem(Image _item){
        setLayoutEnabled(false);
        item = _item;
        add(item);
        item.setPosition(153-item.getWidth()/2, 113-item.getHeight()/2);
    }

    public void setItem(Label _item){
        itemL = _item;
        auxT.clearChildren();
        auxT.setSize(332, 168);
        auxT.add(_item).align(Align.topLeft).size(sizeTable[0], sizeTable[1]).padLeft(10).padBottom(30).expand();
    }

    public void setItem(ToolItem _item){
        itemTool = _item;
        add(itemTool);
    }

    public void setTable(){
        auxT = new Table();
        add(auxT);
    }
}
