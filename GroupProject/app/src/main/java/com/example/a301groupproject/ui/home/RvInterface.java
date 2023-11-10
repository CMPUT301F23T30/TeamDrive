package com.example.a301groupproject.ui.home;
/**
 * This interface defines a contract for handling item click events in a RecyclerView.
 */
public interface RvInterface {
    /**
     * Callback method triggered when an item within a RecyclerView is clicked.
     *
     * @param position The position of the clicked item in the RecyclerView.
     */
    void onItemClick(int position);
}
