package nl.amis.sdo.jpa.services;


import commonj.sdo.DataObject;
import commonj.sdo.helper.DataFactory;

import commonj.sdo.helper.XSDHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import java.util.logging.Level;

import javax.ejb.Stateless;


import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;


import javax.jws.WebMethod;
import javax.jws.WebParam;

import javax.jws.WebResult;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Order;
import javax.persistence.Query;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;

import javax.persistence.criteria.CriteriaQuery;

import javax.persistence.criteria.Root;

import javax.xml.rpc.ServiceException;

import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

import nl.amis.sdo.jpa.entities.Departments;
import nl.amis.sdo.jpa.entities.DepartmentsSDO;
import nl.amis.sdo.jpa.entities.Employees;
import nl.amis.sdo.jpa.entities.EmployeesSDO;

import oracle.jbo.common.sdo.SDOHelper;
import oracle.jbo.common.service.types.FindControl;
import oracle.jbo.common.service.types.ProcessControl;
import oracle.jbo.common.service.types.ProcessData;
import oracle.jbo.common.types.FindCriteria;

import oracle.webservices.annotations.PortableWebService;

@Stateless(name = "HrSessionEJB", mappedName = "EjbSdoService-HrSessionEJB")
@PortableWebService(serviceName = "HrSessionEJBBeanWS",
                    targetNamespace = "/nl.amis.sdo.jpa.services/",
                    portName = "HrSessionEJBBeanWSSoapHttpPort",
                    endpointInterface =
                    "nl.amis.sdo.jpa.services.HrSessionEJB")
public class HrSessionEJBBean extends AbstractService implements HrSessionEJB {
  @PersistenceContext(unitName = "ModelSDO")
  private EntityManager em;

  public HrSessionEJBBean() {
  }
  
  protected EntityManager getEntityManager() {
    return em;
  }

  public Object queryByRange(String jpqlStmt, int firstResult,
                             int maxResults) {
    Query query = em.createQuery(jpqlStmt);
    if (firstResult > 0) {
      query = query.setFirstResult(firstResult);
    }
    if (maxResults > 0) {
      query = query.setMaxResults(maxResults);
    }
    return query.getResultList();
  }

  static {
    synchronized (HrSessionEJBBean.class) {
      try {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        XSDHelper.INSTANCE.define(loader.getResourceAsStream("META-INF/wsdl/ServiceException.xsd"),
                                  "META-INF/wsdl/");
        XSDHelper.INSTANCE.define(loader.getResourceAsStream("META-INF/wsdl/BC4JService.xsd"),
                                  "META-INF/wsdl/");
        XSDHelper.INSTANCE.define(loader.getResourceAsStream("META-INF/wsdl/BC4JServiceCS.xsd"),
                                  "META-INF/wsdl/");
        XSDHelper.INSTANCE.define(loader.getResourceAsStream("nl/amis/sdo/jpa/entities/EmployeesSDO.xsd"),
                                  "nl/amis/sdo/jpa/entities/");
        XSDHelper.INSTANCE.define(loader.getResourceAsStream("nl/amis/sdo/jpa/entities/DepartmentsSDO.xsd"),
                                  "nl/amis/sdo/jpa/entities/");
        XSDHelper.INSTANCE.define(loader.getResourceAsStream("nl/amis/sdo/jpa/services/HrSessionEJBBeanWS.xsd"),
                                  "nl/amis/sdo/jpa/services/");
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
  }

  public DepartmentsSDO getDepartmentsSDO(Long employeeId) {
    return (DepartmentsSDO)get(Departments.class, employeeId);
  }

  public DepartmentsSDO createDepartmentsSDO(DepartmentsSDO departments) {
    return create(departments);
  }

  public DepartmentsSDO updateDepartmentsSDO(DepartmentsSDO departments) {
    return merge(Departments.class, departments);
  }

  public void deleteDepartmentsSDO(DepartmentsSDO departments) {
    delete(Departments.class, departments);
  }

  public DepartmentsSDO mergeDepartmentsSDO(DepartmentsSDO departments) {
    return merge(Departments.class, departments);
  }

  public List<DepartmentsSDO> findDepartmentsSDO(FindCriteria findCriteria,
                                              FindControl findControl) {
    return find(Departments.class, findCriteria, findControl);
  }

  public List<DepartmentsSDO> processDepartmentsSDO(String changeOperation,
                                                 List<DepartmentsSDO> departments,
                                                 ProcessControl processControl) {
    return process(Departments.class, changeOperation, departments);
  }

  public ProcessData processCSDepartmentsSDO(ProcessData processData,
                                          ProcessControl processControl) {
    return process(Departments.class, processData);
  }

  public EmployeesSDO getEmployeesSDO(Long employeeId) {
    System.out.println("getEmployees: " + employeeId);
    return (EmployeesSDO)get(Employees.class, employeeId);
  }

  public EmployeesSDO createEmployeesSDO(EmployeesSDO employees) {
    System.out.println("createEmployees: " + employees);
    return create(employees);
  }

  public EmployeesSDO updateEmployeesSDO(EmployeesSDO employees) {
    System.out.println("updateEmployees: " + employees);
    return merge(Employees.class, employees);
  }

  public void deleteEmployeesSDO(EmployeesSDO employees) {
    System.out.println("deleteEmployees: " + employees);
    delete(Employees.class, employees);
  }

  public EmployeesSDO mergeEmployeesSDO(EmployeesSDO employees) {
    return merge(Employees.class, employees);
  }

  public List<EmployeesSDO> findEmployeesSDO(FindCriteria findCriteria , 
    FindControl findControl) {
    return find(Employees.class, findCriteria, findControl);
  }

  public List<EmployeesSDO> processEmployeesSDO(String changeOperation,
                                             List<EmployeesSDO> employees,
                                             ProcessControl processControl) {
    return process(Employees.class, changeOperation, employees);
  }

  public ProcessData processCSEmployeesSDO(ProcessData processData,
                                        ProcessControl processControl) {
    return process(Employees.class, processData);
  }
}
