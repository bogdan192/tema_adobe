package com.adobe.dps.sample.model;

import java.io.Serializable;

public class Page<T> implements Serializable {

  private static final long serialVersionUID = -3525788375547596219L;
  private T items;

  public Page() {
  }

  public Page(T items) {
    this.items = items;
  }

  public T getItems() {
    return items;
  }

  public void setItems(T items) {
    this.items = items;
  }
}
