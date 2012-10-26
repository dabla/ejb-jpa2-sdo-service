package nl.amis.sdo.jpa.services;

import java.util.List;

import javax.ejb.Remote;

import javax.jws.WebMethod;

import oracle.jbo.common.service.types.FindControl;
import oracle.jbo.common.service.types.FindCriteria;


@Remote
public interface Service {
  @WebMethod(exclude=true)
  public <S> S get(final Class<S> implementation, final Object id);
  @WebMethod(exclude=true)
  public <S> S create(final S dataObject);
  @WebMethod(exclude=true)
  public <S> S update(final S dataObject);
  @WebMethod(exclude=true)
  public <S> void delete(final S dataObject);
  @WebMethod(exclude=true)
  public <S> Long count(final Class<S> implementation, final FindCriteria findCriteria /* JSR-227 API - BC4J Service Runtime */, final FindControl findControl /* BC4J Service Runtime */);
  @WebMethod(exclude=true)
  public <S> List<S> find(final Class<S> implementation, final FindCriteria findCriteria /* JSR-227 API - BC4J Service Runtime */, final FindControl findControl /* BC4J Service Runtime */);  
}