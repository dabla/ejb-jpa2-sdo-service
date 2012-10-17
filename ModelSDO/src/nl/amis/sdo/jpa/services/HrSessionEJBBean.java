package nl.amis.sdo.jpa.services;


import commonj.sdo.DataObject;
import commonj.sdo.helper.DataFactory;

import commonj.sdo.helper.XSDHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import java.util.logging.Level;

import javax.ejb.Remote;
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

import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

import nl.amis.sdo.jpa.entities.Departments;
import nl.amis.sdo.jpa.entities.DepartmentsSDO;
import nl.amis.sdo.jpa.entities.Employees;
import nl.amis.sdo.jpa.entities.EmployeesSDO;

import oracle.jbo.common.service.types.FindControl;
import oracle.jbo.common.service.types.FindCriteria;
import oracle.jbo.common.service.types.ProcessControl;
import oracle.jbo.common.service.types.ProcessData;
import oracle.jbo.service.errors.ServiceException;

import oracle.webservices.annotations.PortableWebService;

@Stateless(name = "HrSessionEJB", mappedName = "EjbSdoService-HrSessionEJB")
//@Remote(HrSessionEJB.class)
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

  public DepartmentsSDO getDepartmentsSDO(Long employeeId) throws ServiceException {
    return (DepartmentsSDO)get(Departments.class, employeeId);
  }

  public DepartmentsSDO createDepartmentsSDO(DepartmentsSDO departments) throws ServiceException {
    return create(departments);
  }

  public DepartmentsSDO updateDepartmentsSDO(DepartmentsSDO departments) throws ServiceException {
    return update(Departments.class, departments);
  }

  public void deleteDepartmentsSDO(DepartmentsSDO departments) throws ServiceException {
    delete(Departments.class, departments);
  }

  public DepartmentsSDO mergeDepartmentsSDO(DepartmentsSDO departments) throws ServiceException {
    return update(Departments.class, departments);
  }
  
  public Long countDepartmentsSDO(FindCriteria findCriteria,
                                              FindControl findControl) throws ServiceException {
    return count(Departments.class, findCriteria, findControl);
  }

  public List<DepartmentsSDO> findDepartmentsSDO(FindCriteria findCriteria,
                                              FindControl findControl) throws ServiceException {
    return find(Departments.class, findCriteria, findControl);
  }

  public List<DepartmentsSDO> processDepartmentsSDO(String changeOperation,
                                                 List<DepartmentsSDO> departments,
                                                 ProcessControl processControl) throws ServiceException {
    return process(Departments.class, changeOperation, departments);
  }

  public ProcessData processCSDepartmentsSDO(ProcessData processData,
                                          ProcessControl processControl) throws ServiceException {
    return process(Departments.class, processData);
  }

  public EmployeesSDO getEmployeesSDO(Long employeeId) throws ServiceException {
    System.out.println("getEmployees: " + employeeId);
    return (EmployeesSDO)get(Employees.class, employeeId);
  }

  public EmployeesSDO createEmployeesSDO(EmployeesSDO employees) throws ServiceException {
    System.out.println("createEmployees: " + employees);
    return create(employees);
  }

  public EmployeesSDO updateEmployeesSDO(EmployeesSDO employees) throws ServiceException {
    System.out.println("updateEmployees: " + employees);
    return update(Employees.class, employees);
  }

  public void deleteEmployeesSDO(EmployeesSDO employees) throws ServiceException {
    System.out.println("deleteEmployees: " + employees);
    delete(Employees.class, employees);
  }

  public EmployeesSDO mergeEmployeesSDO(EmployeesSDO employees) throws ServiceException {
    return update(Employees.class, employees);
  }
  
  public Long countEmployeesSDO(FindCriteria findCriteria,
                                              FindControl findControl) throws ServiceException {
    return count(Employees.class, findCriteria, findControl);
  }

  public List<EmployeesSDO> findEmployeesSDO(FindCriteria findCriteria , 
    FindControl findControl) throws ServiceException {
    return find(Employees.class, findCriteria, findControl);
  }

  public List<EmployeesSDO> processEmployeesSDO(String changeOperation,
                                             List<EmployeesSDO> employees,
                                             ProcessControl processControl) throws ServiceException {
    return process(Employees.class, changeOperation, employees);
  }

  public ProcessData processCSEmployeesSDO(ProcessData processData,
                                        ProcessControl processControl) throws ServiceException {
    return process(Employees.class, processData);
  }
}
