package nl.amis.sdo.jpa.services;

import java.util.List;

import javax.ejb.Remote;

import javax.jws.WebMethod;

import nl.amis.sdo.jpa.entities.BaseDataObject;
import nl.amis.sdo.jpa.entities.BaseEntity;

import oracle.jbo.common.service.types.FindControl;
import oracle.jbo.common.service.types.FindCriteria;


@Remote
public interface Service {
  @WebMethod(exclude=true)
  public <S extends BaseDataObject<T>, T extends BaseEntity<S>> S get(final Class<T> implementation, final Object id);
  @WebMethod(exclude=true)
  public <S extends BaseDataObject<T>, T extends BaseEntity<S>> S create(final S dataObject);
  @WebMethod(exclude=true)
  public <S extends BaseDataObject<T>, T extends BaseEntity<S>> S update(final Class<T> implementation, final S dataObject);
  @WebMethod(exclude=true)
  public <S extends BaseDataObject<T>, T extends BaseEntity<S>> void delete(final Class<T> implementation, final S dataObject);
  @WebMethod(exclude=true)
  public <S extends BaseDataObject<T>, T extends BaseEntity<S>> Long count(final Class<T> implementation, final FindCriteria findCriteria /* JSR-227 API - BC4J Service Runtime */, final FindControl findControl /* BC4J Service Runtime */);
  @WebMethod(exclude=true)
  public <S extends BaseDataObject<T>, T extends BaseEntity<S>> List<S> find(final Class<T> implementation, final FindCriteria findCriteria /* JSR-227 API - BC4J Service Runtime */, final FindControl findControl /* BC4J Service Runtime */);  
}