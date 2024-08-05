package org.example.model;

public class Item {

  private int id;

  public final int getId() {
    return id;
  }

  public final void setId(final int id) {
    this.id = id;
  }

  private String name;

  public final void setName(final String name) {
    this.name = name;
  }

  public final String getName() {
    return name;
  }

  private double cost;

  public final void setCost(final double cost) {
    this.cost = cost;
  }

  public final double getCost() {
    return cost;
  }

  private String type;

  public final void setType(final String type) {
    this.type = type;
  }

  public final String getType() {
    return type;
  }

  private double tax;

  public final double getTax() {
    return tax;
  }

  public final void setTax(final double tax) {
    this.tax = tax;
  }

  @Override
  public final String toString() {
    return "Item{" + "id=" + id + ", name='" + name + '\'' + ", cost=" + cost + ", type='" + type
        + '\'' + ", tax=" + tax + '}';
  }
}
