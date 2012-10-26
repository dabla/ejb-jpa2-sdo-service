package nl.amis.sdo.jpa;


import commonj.sdo.helper.DataFactory;
import commonj.sdo.helper.TypeHelper;
import commonj.sdo.helper.XSDHelper;

import java.util.Arrays;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import nl.amis.sdo.jpa.entities.DepartmentsSDO;
import nl.amis.sdo.jpa.entities.EmployeesSDO;
import nl.amis.sdo.jpa.services.HrSessionEJB;
import nl.amis.sdo.jpa.services.HrSessionEJBBean;

import oracle.jbo.common.service.types.FindControl;
import oracle.jbo.common.service.types.FindCriteria;


public class HrSessionEJBLocal {
  private static final class HrSessionEJBBeanLocal extends HrSessionEJBBean {
    @Override
    protected EntityManager getEntityManager() {
      final EntityManager entityManager = Persistence.createEntityManagerFactory("ModelSDOLocal").createEntityManager();
      entityManager.getTransaction().begin();
      return entityManager;
    }
  }
  
  public static void main(String[] args) throws Exception {
    ClassLoader loader =
        Thread.currentThread().getContextClassLoader();
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
    
    final FindCriteria findCriteria = (FindCriteria)DataFactory.INSTANCE.create(TypeHelper.INSTANCE.getType(FindCriteria.class));
    findCriteria.setFetchStart(0);
    findCriteria.setFetchSize(-1);
    findCriteria.setExcludeAttribute(false);
    
    final FindControl findControl = (FindControl)DataFactory.INSTANCE.create(TypeHelper.INSTANCE.getType(FindControl.class));
    findControl.setRetrieveAllTranslations(false);
    final HrSessionEJB hrSessionEJB = new HrSessionEJBBeanLocal();
    
    System.out.println("classes: " + Arrays.toString(DepartmentsSDO.class.getPackage().getClass().getClasses()));
    
    final Long countDepartments = hrSessionEJB.count(DepartmentsSDO.class, findCriteria, findControl);
    
    System.out.println("countDepartments: " + countDepartments);
    
    for (DepartmentsSDO departments :
           hrSessionEJB.find(DepartmentsSDO.class, findCriteria, findControl)) {
             System.out.println("departments: " + departments);
           }
    
    // here we use entity specific count method, which is also exposed and callable through SOAP
    final Long countEmployees = hrSessionEJB.countEmployeesSDO(findCriteria, findControl);
    
    System.out.println("countEmployees: " + countEmployees);
    
    for (EmployeesSDO employees :
         hrSessionEJB.findEmployeesSDO(findCriteria, findControl)) {
        System.out.println("employees: " + employees);
    }
    
    final EmployeesSDO employeesSDO = (EmployeesSDO)DataFactory.INSTANCE.create(TypeHelper.INSTANCE.getType(EmployeesSDO.class));
    employeesSDO.setEmail("dablomatique@gmail.com");
    employeesSDO.setFirstName("David");
    employeesSDO.setHireDate(new Date());
    employeesSDO.setLastName("Blain");
    employeesSDO.setManagerId(103);
    employeesSDO.setPhoneNumber("590.423.4569");
    employeesSDO.setSalary(10000);
    
    System.out.println("employeesSDO: " + employeesSDO);
    
    /*EmployeesSDO employee = hrSessionEJB.create(employeesSDO);
    System.out.println("employee: " + employee);
    System.out.println("employeeId: " + employee.getEmployeeId());
    employee = hrSessionEJB.get(EmployeesSDO.class, employee.getEmployeeId());
    
    employee.setEmail("info@dabla.be");
    
    System.out.println("employee: " + hrSessionEJB.update(employee));
    
    Thread.sleep(60000);
    
    hrSessionEJB.delete(employee);*/
  }
}
