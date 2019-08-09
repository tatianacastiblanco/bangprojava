package com.mygdx.bang;

import com.badlogic.gdx.utils.Array;

public class DataJson{
    private String user = "";
    private Array<String[]> ideas = new Array<String[]>();
    private Array<String[][]> cards = new Array<String[][]>();
    private float rank = -1;

    public void setUser(String _user) {
        
        user = _user;
    }

    public void setIdeas(){
        ideas = new Array<String[]>();
    }

    public void setRank(float _rank){
        rank = _rank;
    }

    public void setCards(Array<String[][]> _cards){
        cards = _cards;
    }

    public String getUser(){
        return user;
    }

    public Array<String[]> getIdeas(){
        return ideas;
    }

    public float getRank(){
        return rank;
    }

    public Array<String[][]> getCards(){
        return cards;
    }

    public void addIdea(String[] data){
        ideas.add(data);
    }

    public void addIdeas(String[][] data){
        for(int i = 0; i < data.length; i++)
            ideas.add(data[i]);
    }

    public void addCard(String[][] _data){
        cards.add(_data);
    }
}
