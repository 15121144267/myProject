package com.dispatching.feima.entity;

import java.util.List;


public interface IExpandable<T> {
    boolean isExpanded();
    void setExpanded(boolean expanded);
    List<T> getSubItems();
    int getLevel();
}
