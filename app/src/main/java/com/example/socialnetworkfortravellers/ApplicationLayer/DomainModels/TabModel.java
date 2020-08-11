package com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels;

public class TabModel {


    private int indexofTab;
    private String NameofTab;

    public TabModel(String nameofTab , int indexofTab)
    {
        this.NameofTab = nameofTab;
        this.indexofTab = indexofTab;
    }


    public int getIndexofTab() {
        return indexofTab;
    }

    public void setIndexofTab(int indexofTab) {
        this.indexofTab = indexofTab;
    }

    public String getNameofTab() {
        return NameofTab;
    }

    public void setNameofTab(String nameofTab) {
        NameofTab = nameofTab;
    }
}
