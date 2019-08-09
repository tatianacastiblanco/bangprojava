package com.mygdx.bang;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;

public class ToolItem extends Stack{
    Image back;
    Label textLbl;
    MenuTools tools;
    MenuSlot slot;
    boolean onSlot = true;
    boolean moving = true;
    boolean editing = false;
    boolean rotating = false;
    float startX, startY;
    float[] points = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    Texture texture;
    float oldRotation = 0;
    float x2, y2, width2, height2;

    ToolItem(MenuTools _tools, MenuSlot _slot, Drawable d){
        //super(d);
        tools = _tools;
        slot = _slot;
        setTransform(true);

        back = new Image(d);
        this.addActor(back);

        textLbl = new Label("", tools.labelStyle);
        textLbl.setSize(280, 280);
        textLbl.setAlignment(Align.center);
        textLbl.setColor(1, 1, 1, 1);
        textLbl.setWrap(true);
        //this.addActor(textLbl);

        setSize(300, 300);

        addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                startX = x; startY = y;
                setSize(getWidth(), getHeight());
                if(onSlot){
                    tools.storyboard.setCurrentItem(ToolItem.this);
                    //tools.currentItem = ToolItem.this;
                    tools.disableScroll();
                    moving = true;
                    onSlot = false;
                    tools.stage.addActor(ToolItem.this);
                    Vector2 newCoord = slot.localToStageCoordinates(new Vector2(0, 0));
                    setPosition(newCoord.x, newCoord.y);
                    x2 = newCoord.x;
                    y2 = newCoord.y;
                    //slot.item = null;
                    setOrigin(getWidth()/2, getHeight()/2);
                    tools.ballon1.add(ToolItem.this);
                    //ToolItem newI = new ToolItem(tools, texture);
                    //slot.setItem(newI);
                    tools.reAsignItem(slot, back.getDrawable());
                }else{
                    tools.storyboard.setCurrentItem(ToolItem.this);
                    //tools.currentItem = ToolItem.this;
                    moving = true;
                    tools.showEdit();
                }
                return true;
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer){
                if(moving){
                    tools.activeTool = true;
                    float dragX = Gdx.input.getDeltaX();
                    float dragY = Gdx.input.getDeltaY();
                    setPosition(getX()+dragX, getY()-dragY);
                    x2 += dragX;
                    y2 -= dragY;
                    pointsRezise(getRotation());
                    tools.showEdit();
                }
                if(rotating){
                    rotate(x, y);
                    tools.activeTool = true;
                }
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                moving = false;
                tools.activeTool = false;
            }
        });
        x2 = getX();
        y2 = getY();
        width2 = getWidth();
        height2 = getHeight();
    }

    ToolItem(MenuTools _tools, String text){
        tools = _tools;
        setTransform(true);

        textLbl = new Label(text, tools.labelStyle);
        textLbl.setWidth(300);
        textLbl.setAlignment(Align.center);
        textLbl.setWrap(true);
        addActor(textLbl);

        setSize(300, 300);

        addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                setOrigin(getWidth()/2, getHeight()/2);
                tools.storyboard.setCurrentItem(ToolItem.this);
                //tools.currentItem = ToolItem.this;
                moving = true;
                tools.showEdit();
                return true;
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer){
                if(moving){
                    tools.activeTool = true;
                    float dragX = Gdx.input.getDeltaX();
                    float dragY = Gdx.input.getDeltaY();
                    setPosition(getX()+dragX, getY()-dragY);
                    x2 += dragX;
                    y2 -= dragY;
                    pointsRezise(getRotation());
                    tools.showEdit();
                }
                if(rotating){
                    tools.activeTool = true;
                    rotate(x, y);
                }
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                moving = false;
                tools.activeTool = false;
            }
        });
        x2 = getX();
        y2 = getY();
        width2 = getWidth();
        height2 = getHeight();
    }

    public void pointsRezise(float angle){
        float cos = MathUtils.cosDeg(angle);
        float sin = MathUtils.sinDeg(angle);
        float x3 = x2-40;
        float y3 = y2-40;
        float w = width2+40;
        float h = height2+40;
        float X0 = x3+(w/2);
        float Y0 = y3+(h/2);
        Gdx.app.log("Angle", "Angle"+angle+" X "+x3+" Y "+y3+" W "+w+" H "+h);
        points[0] = ((((x3-X0)*cos)-((y3-Y0)*sin))+X0);
        points[1] = ((((x3-X0)*sin)+((y3-Y0)*cos))+Y0);
        points[2] = ((((x3+(w/2)-X0)*cos)-((y3-Y0)*sin))+X0);
        points[3] = ((((x3+(w/2)-X0)*sin)+((y3-Y0)*cos))+Y0);
        points[4] = ((((x3+w-X0)*cos)-((y3-Y0)*sin))+X0);
        points[5] = ((((x3+w-X0)*sin)+((y3-Y0)*cos))+Y0);
        points[6] = ((((x3+w-X0)*cos)-((y3+(h/2)-Y0)*sin))+X0);
        points[7] = ((((x3+w-X0)*sin)+((y3+(h/2)-Y0)*cos))+Y0);
        points[8] = ((((x3+w-X0)*cos)-((y3+h-Y0)*sin))+X0);
        points[9] = ((((x3+w-X0)*sin)+((y3+h-Y0)*cos))+Y0);
        points[10] = ((((x3+(w/2)-X0)*cos)-((y3+h-Y0)*sin))+X0);
        points[11] = ((((x3+(w/2)-X0)*sin)+((y3+h-Y0)*cos))+Y0);
        points[12] = ((((x3-X0)*cos)-((y3+h-Y0)*sin))+X0);
        points[13] = ((((x3-X0)*sin)+((y3+h-Y0)*cos))+Y0);
        points[14] = ((((x3-X0)*cos)-((y3+(h/2)-Y0)*sin))+X0);
        points[15] = ((((x3-X0)*sin)+((y3+(h/2)-Y0)*cos))+Y0);
        x3 = x2;
        y3 = y2-40;
        w = width2;
        h = height2+90;
        X0 = x3+(w/2);
        Y0 = y3+(h/2);
        points[16] = ((((x3+(w/2)-115-X0)*cos)-((y3+h-Y0)*sin))+X0);
        points[17] = ((((x3+(w/2)-115-X0)*sin)+((y3+h-Y0)*cos))+Y0);
        points[18] = ((((x3+(w/2)+10-X0)*cos)-((y3+h-Y0)*sin))+X0);
        points[19] = ((((x3+(w/2)+10-X0)*sin)+((y3+h-Y0)*cos))+Y0);
        tools.showEdit();
    }

    public void setTexture(Texture _texture){
        texture = _texture;
        texture.getTextureData().prepare();
    }

    public void rotate(float x, float y){
        float X0 = (getX()+getWidth()/2);
        float Y0 = (getY()+getHeight()/2);
        setOrigin(getWidth()/2, getHeight()/2);
        float dx = Gdx.input.getX();
        float dy = (Gdx.input.getY());
        Vector2 newCoor = screenToLocalCoordinates(new Vector2(dx, dy));
        newCoor = localToStageCoordinates(newCoor);
        dx = newCoor.x;
        dy = newCoor.y;
        float angle = (float) Math.toDegrees(Math.atan2(dy-Y0, dx-X0));
        Gdx.app.log("Angle2", "Angle"+angle+" X "+dx+" Y "+dy);
        angle-=90;
        if(angle < 0){
            angle += 360;

        }
        Gdx.app.log("Angle2", " X2 "+Gdx.input.getX()+" Y2 "+Gdx.input.getY());
        textLbl.layout();
        setRotation(angle);
        pointsRezise(angle);
    }

    public void setTextLbl(String text){
        Table labelT = new Table();
        clearChildren();
        setRotation(0);
        setScale(1);
        textLbl = new Label(text, tools.labelStyle);
        textLbl.setWidth(280);
        textLbl.setAlignment(Align.center);
        textLbl.setWrap(true);
        labelT.add(textLbl).width(280).pad(50, 30, 70, 30).center().expand();
        textLbl.layout();
        float newH = textLbl.getGlyphLayout().height+140;
        labelT.setSize(340, newH);
        back.setSize(340, newH);
        back.pack();
        this.addActor(back);
        this.addActor(labelT);
        x2 = getX();
        y2 = getY();
        width2 = 340;
        height2 = newH;
        setSize(340, newH);
        setOrigin(Align.center);
        pointsRezise(0);
    }
}
