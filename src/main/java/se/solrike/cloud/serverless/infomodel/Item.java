package se.solrike.cloud.serverless.infomodel;

import javax.persistence.Column;
import javax.persistence.Entity;

import se.solrike.cloud.serverless.common.AbstractEntity;

/**
 *
 */
@Entity
public class Item extends AbstractEntity {

  private static final long serialVersionUID = 1L;

  @Column(nullable = false, length = 255)
  private String itemDescription;

  public Item() {
  }

  public Item(String itemDescription) {
    this.itemDescription = itemDescription;
  }

  public String getItemDescription() {
    return itemDescription;
  }

  public void setItemDescription(String itemDescription) {
    this.itemDescription = itemDescription;
  }

  @Override
  public String toString() {
    return "Item [description=" + itemDescription + ", getId()=" + getId() + "]";
  }

}
