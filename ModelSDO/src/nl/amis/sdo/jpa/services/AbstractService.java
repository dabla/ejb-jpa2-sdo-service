package nl.amis.sdo.jpa.services;

import commonj.sdo.ChangeSummary;
import commonj.sdo.DataObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.TransactionAttribute;

import javax.ejb.TransactionAttributeType;

import javax.jws.WebParam;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nl.amis.sdo.jpa.entities.BaseDataObject;
import nl.amis.sdo.jpa.entities.BaseEntity;
import nl.amis.sdo.jpa.entities.Employees;
import nl.amis.sdo.jpa.entities.EmployeesSDO;

import oracle.jbo.common.service.types.FindControl;
import oracle.jbo.common.service.types.ProcessControl;
import oracle.jbo.common.service.types.ProcessData;
import oracle.jbo.common.service.types.SortAttribute;
import oracle.jbo.common.service.types.ViewCriteriaItem;
import oracle.jbo.common.service.types.ViewCriteriaRow;
import oracle.jbo.common.types.FindCriteria;

import org.eclipse.persistence.sdo.helper.ListWrapper;

public abstract class AbstractService {
  protected static final Logger logger = Logger.getLogger(AbstractService.class.getName());
  
  public AbstractService() {
    super();
  }
  
  protected abstract EntityManager getEntityManager();
  
  protected <S extends BaseDataObject<T>, T extends BaseEntity<S>> S get(final Class<T> implementation, final Object id) {
    logger.log(Level.FINEST, "implementation: {0}", implementation);
    logger.log(Level.FINEST, "id: {0}", id);
    final T entity = getEntityManager().find(implementation, id);
    
    if (entity != null) {
      return entity.toDataObject();
    }
    
    return null;
  }
  
  @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
  protected <S extends BaseDataObject<T>, T extends BaseEntity<S>> S create(final S dataObject) {
    logger.log(Level.FINEST, "dataObject: {0}", dataObject);
    System.out.println("dataObject: " + dataObject);
    final T entity = dataObject.toEntity();
    System.out.println("entity: " + entity);
    getEntityManager().persist(entity);
    getEntityManager().flush();  // http://stackoverflow.com/questions/7206891/is-calling-persist-flush-and-refresh-in-one-method-to-persist-an-entity-th
    getEntityManager().refresh(entity);
    // now the entity has an id
    System.out.println("entity: " + entity);
    return entity.toDataObject();
  }
  
  @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
  public <S extends BaseDataObject<T>, T extends BaseEntity<S>> S merge(final Class<T> implementation, final S dataObject) {
    logger.log(Level.FINEST, "implementation: {0}", implementation);
    logger.log(Level.FINEST, "dataObject: {0}", dataObject);
    if (dataObject != null) {
      return getEntityManager().merge(dataObject.toEntity()).toDataObject();
    }
    return null;
  }
  
  @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
  protected <S extends BaseDataObject<T>, T extends BaseEntity<S>> void delete(final Class<T> implementation, final S dataObject) {
    logger.log(Level.FINEST, "implementation: {0}", implementation);
    logger.log(Level.FINEST, "dataObject: {0}", dataObject);
    if (dataObject != null) {
      getEntityManager().remove(getEntityManager().find(implementation, dataObject.toEntity().getId()));
    }
  }
    
    protected <S extends BaseDataObject<T>, T extends BaseEntity<S>> ProcessData process(final Class<T> implementation, final ProcessData request) {
      //return process(implementation, processControl.g, request.getValue());
      final List<S> dataObjects = request.getValue();
      
      for (int index = 0; index < dataObjects.size(); index++) {
        final ChangeSummary changeSummary = request.getChangeSummary();
        final DataObject dataObject = (DataObject)dataObjects.get(index);
        
        if (changeSummary.isCreated(dataObject)) {
          dataObjects.set(index, create(dataObjects.get(index)));
        }
        else if (changeSummary.isModified(dataObject)) {
          dataObjects.set(index, merge(implementation, dataObjects.get(index)));
        }
        else if (changeSummary.isDeleted(dataObject)) {
          delete(implementation, dataObjects.get(index));
        }
      }
      
      return request;
    }
  
     /*protected <S extends BaseDataObject<T>, T extends BaseEntity<S>> List<S> process(final Class<T> implementation, final String operation, final List<S> dataObjects) {
      final String[] operations = new String[dataObjects.size()];
      Arrays.fill(operations, operation);
      return process(implementation, operations, dataObjects);
     }*/
    
  protected <S extends BaseDataObject<T>, T extends BaseEntity<S>> List<S> process(final Class<T> implementation, final String operation, final List<S> dataObjects) {
    if (dataObjects != null) {
        for (int index = 0; index < dataObjects.size(); index++) {
          logger.log(Level.FINEST, "operations[{0}]: {1}", new Object[]{index,operation});
          
          if ("Create".equals(operation)) {
            dataObjects.set(index, create(dataObjects.get(index)));
          }
          else if ("Merge".equals(operation) || "Update".equals(operation)) {
            dataObjects.set(index, merge(implementation, dataObjects.get(index)));
          }
          else if ("Delete".equals(operation)) {
            delete(implementation, dataObjects.get(index));
          }
        }
    }
    
    return dataObjects;
  }
  
  protected <S extends BaseDataObject<T>, T extends BaseEntity<S>> Long count(final Class<T> implementation, final FindCriteria findCriteria /* JSR-227 API - BC4J Service Runtime */, final FindControl findControl /* BC4J Service Runtime */) throws RuntimeException {
    final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
    final CriteriaQuery<Long> cq = cb.createQuery(Long.class);
    final Root<T> root = cq.from(implementation);
    cq.select(cb.count(root));
    appendToQueryBuilder(cb, cq, root, findCriteria);
    return getEntityManager().createQuery(cq).getSingleResult();
  }
  
  protected <S extends BaseDataObject<T>, T extends BaseEntity<S>> List<S> find(final Class<T> implementation, final FindCriteria findCriteria /* JSR-227 API - BC4J Service Runtime */, final FindControl findControl /* BC4J Service Runtime */) throws RuntimeException {
    final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
    final CriteriaQuery<T> cq = cb.createQuery(implementation);
    final Root<T> root = cq.from(implementation);
    cq.select(root);
    appendToQueryBuilder(cb, cq, root, findCriteria);
    
    final TypedQuery<T> tq = getEntityManager().createQuery(cq);
    
    logger.log(Level.FINEST, "fetchStart: {0}", findCriteria.getFetchStart());
      
    if (findCriteria.getFetchStart() > -1) {
      tq.setFirstResult(findCriteria.getFetchStart());
    }
    
    logger.log(Level.FINEST, "fetchSize: {0}", findCriteria.getFetchSize());
    
    if (findCriteria.getFetchSize() > 0) {
      tq.setMaxResults(findCriteria.getFetchSize());
    }

    final List<T> resultList = tq.getResultList();
    
    logger.log(Level.FINEST, "resultList: {0}", resultList);
    
    if (resultList != null) {
      final List<S> resultListSDO = new ArrayList<S>(resultList.size());
      
      for (final T entity : resultList) {
        logger.log(Level.FINEST, "entity: {0}", entity);
        logger.log(Level.FINEST, "dataObject: {0}", entity.toDataObject());
        resultListSDO.add(entity.toDataObject());
      }
      
      System.out.println("resultListSDO: " + resultListSDO);
      logger.log(Level.FINEST, "resultListSDO: {0}", resultListSDO);
      
      return resultListSDO;
    }
    
    return null;
  }
  
  protected <T> void appendToQueryBuilder(final CriteriaBuilder cb, final CriteriaQuery cq, final Root<T> root, final FindCriteria findCriteria) {
    logger.log(Level.FINEST, "findCriteria: {0}", findCriteria);

    if (findCriteria != null) {
      final List<Order> order = buildOrderBy(cb, cq, root, findCriteria);
      
      logger.log(Level.FINEST, "order: {0}", order);
      
      if (order != null) {
        cq.orderBy(order);
      }
      
      final Predicate predicate = buildWhere(cb, cq, root, findCriteria);
      
      logger.log(Level.FINEST, "predicate: {0}", predicate);

      if (predicate != null) {
        cq.where(predicate);
      }
    }
  }
  
  protected <T> List<Order> buildOrderBy(final CriteriaBuilder cb, final CriteriaQuery cq, final Root<T> root, final FindCriteria findCriteria) {
    logger.log(Level.FINEST, "sortOrder: {0}", findCriteria.getSortOrder());
    
    if (findCriteria.getSortOrder() != null) {
      final List<SortAttribute> sortAttributes = findCriteria.getSortOrder().getSortAttribute();
      
      logger.log(Level.FINEST, "sortAttributes: {0}", sortAttributes);

      if (sortAttributes != null) {
        final List<Order> orders = new ArrayList<Order>(sortAttributes.size());
        
        for (final SortAttribute sortAttribute : sortAttributes) {
          logger.log(Level.FINEST, "sortAttribute: {0}", sortAttribute);
          logger.log(Level.FINEST, "descending: {0}", sortAttribute.getDescending());
          logger.log(Level.FINEST, "name: {0}", sortAttribute.getName());

          orders.add(sortAttribute.getDescending() ? cb.desc(root.get(camelize(sortAttribute.getName()))) : cb.asc(root.get(camelize(sortAttribute.getName()))));
        }
        
        return orders;
      }
    }
    
    return null;
  }
  
  protected <T> Predicate buildWhere(final CriteriaBuilder cb, final CriteriaQuery cq, final Root<T> root, final FindCriteria findCriteria) {
    logger.log(Level.FINEST, "filter: {0}", findCriteria.getFilter());
    
    if (findCriteria.getFilter() != null) {
      final List<ViewCriteriaRow> groups = findCriteria.getFilter().getGroup();
      
      logger.log(Level.FINEST, "groups: {0}", groups);

      if (groups != null) {
        final List<Predicate> wherePredicates = new ArrayList<Predicate>();
        
        for (final ViewCriteriaRow group : groups) {
          logger.log(Level.FINEST, "group: {0}", group);
          
          final List<ViewCriteriaItem> items = group.getItem();
          
          logger.log(Level.FINEST, "items: {0}", items);
          
          if (items != null) {
            for (final ViewCriteriaItem item : items) {
              logger.log(Level.FINEST, "item: {0}", item);
              logger.log(Level.FINEST, "upperCaseCompare: {0}", item.getUpperCaseCompare());
              logger.log(Level.FINEST, "attribute: {0}", item.getAttribute());
              logger.log(Level.FINEST, "operator: {0}", item.getOperator());
              logger.log(Level.FINEST, "list value: {0}", item.getValue());
              
              if ((item.getValue() != null) && !item.getValue().isEmpty()) {
                logger.log(Level.FINEST, "first value: {0}", item.getValue().get(0));
                
                if ("=".equals(item.getOperator())) {
                  wherePredicates.add(cb.equal(root.get(camelize(item.getAttribute())), item.getValue().get(0)));
                }
                else if ("<>".equals(item.getOperator())) {
                  wherePredicates.add(cb.notEqual(root.get(camelize(item.getAttribute())), item.getValue().get(0)));
                }
                else if ("like".equals(item.getOperator())) {
                  final String value = new StringBuilder().append(item.getValue().get(0)).append("%").toString();
                  logger.log(Level.FINEST, "string value: {0}", value);
                  final Path<String> path = root.get(camelize(item.getAttribute()));
                  
                  if (item.getUpperCaseCompare()) {
                    wherePredicates.add(cb.like(cb.upper(path), value.toUpperCase()));
                  }
                  else {
                    wherePredicates.add(cb.like(cb.lower(path), value.toLowerCase()));
                  }
                }
              }
            }
          }
        } 
       return cb.and(wherePredicates.toArray(new Predicate[0]));
      }
    }
    
    return null;
  }
  
  // http://stackoverflow.com/questions/4052840/most-efficient-way-to-make-the-first-character-of-a-string-lower-case
  protected String camelize(final String value) {
    if (value != null) {
      return Character.toLowerCase(value.charAt(0)) + (value.length() > 1 ? value.substring(1) : "");
    }
    
    return null;
  }
}
