package com.mygdx.bang;

public class Scene{
    int id = -1;
    int especial = 0;
    int close = 0;
    String type, actor, text;
    String[][] texts;
    String[] toClose;

    Scene(int _id, String _type, int esp, int _close){
        id = _id;
        type = _type;
        especial = esp;
        close = _close;
    }

    public void setScene(String act, String _text){
        actor = act;
        text = _text;
    }

    public void setScene(String[][] _texts){
        texts = _texts;
    }

    public void setToClose(String[] close){
        toClose = close;
    }
}
