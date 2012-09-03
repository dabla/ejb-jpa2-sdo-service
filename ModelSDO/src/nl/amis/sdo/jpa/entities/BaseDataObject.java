package nl.amis.sdo.jpa.entities;

public interface BaseDataObject<T> {
  public T toEntity();
}
