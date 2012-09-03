package nl.amis.sdo.jpa.entities;

public interface BaseEntity<T extends BaseDataObject> {
  public Object getId();
  public T toDataObject();
}