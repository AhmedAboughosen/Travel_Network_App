package com.example.socialnetworkfortravellers.eventBus;

public class DeletePostEvent {

    private int mIndex;

    public DeletePostEvent(int index) {
        this.mIndex = index;
    }

    public int getIndex() {
        return mIndex;
    }


}
