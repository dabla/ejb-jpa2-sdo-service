package nl.amis.sdo.jpa;


import commonj.sdo.helper.DataFactory;
import commonj.sdo.helper.TypeHelper;
import commonj.sdo.helper.XSDHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import nl.amis.sdo.jpa.entities.DepartmentsSDO;
import nl.amis.sdo.jpa.entities.EmployeesSDO;
import nl.amis.sdo.jpa.services.HrSessionEJB;
import nl.amis.sdo.jpa.services.HrSessionEJBBean;

import oracle.jbo.common.service.types.FindControl;
import oracle.jbo.common.service.types.FindCriteria;
import oracle.jbo.common.service.types.ViewCriteria;
import oracle.jbo.common.service.types.ViewCriteriaItem;
import oracle.jbo.common.service.types.ViewCriteriaRow;


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
    final List values = new ArrayList();
    values.add("2008-04-21T00:00:00.0Z");
    values.add("2012-08-22T00:00:00.0Z");
    
    final List<Object> item = new ArrayList<Object>();
    final ViewCriteriaItem viewCriteriaItem =
      (ViewCriteriaItem)DataFactory.INSTANCE.create(TypeHelper.INSTANCE.getType(ViewCriteriaItem.class));
    viewCriteriaItem.setConjunction("And");
    viewCriteriaItem.setUpperCaseCompare(true);
    viewCriteriaItem.setAttribute("hireDate");
    viewCriteriaItem.setOperator("between");
    viewCriteriaItem.setValue(values);
    item.add(viewCriteriaItem);
    
    findCriteria.setFilter((ViewCriteria)DataFactory.INSTANCE.create(TypeHelper.INSTANCE.getType(ViewCriteria.class)));
    findCriteria.getFilter().setGroup(new ArrayList(1));
    final ViewCriteriaRow viewCriteriaRow =
      (ViewCriteriaRow)DataFactory.INSTANCE.create(TypeHelper.INSTANCE.getType(ViewCriteriaRow.class));
    viewCriteriaRow.setConjunction("And");
    viewCriteriaRow.setUpperCaseCompare(true);
    viewCriteriaRow.setItem(item);
    findCriteria.getFilter().getGroup().add(viewCriteriaRow);
    
    final List<EmployeesSDO> employeesList = hrSessionEJB.findEmployeesSDO(findCriteria, findControl);
    
    System.out.println("employeesList: " + employeesList);
    
    for (EmployeesSDO employees :
         employeesList) {
        System.out.println("employees: " + employees);
    }
    
    final List values2 = new ArrayList();
    values2.add("100");
    values2.add("102");
    
    final ViewCriteriaItem viewCriteriaItem2 =
      (ViewCriteriaItem)DataFactory.INSTANCE.create(TypeHelper.INSTANCE.getType(ViewCriteriaItem.class));
    viewCriteriaItem2.setConjunction("And");
    viewCriteriaItem2.setUpperCaseCompare(true);
    viewCriteriaItem2.setAttribute("employeeId");
    viewCriteriaItem2.setOperator("in");
    viewCriteriaItem2.setValue(values2);

    viewCriteriaRow.getItem().set(0, viewCriteriaItem2);
    
    final List<EmployeesSDO> employeesList2 = hrSessionEJB.findEmployeesSDO(findCriteria, findControl);
        
        System.out.println("employeesList2: " + employeesList2);
        
        for (EmployeesSDO employees :
             employeesList2) {
            System.out.println("employees: " + employees);
        }
        
    final List values3 = new ArrayList();
    values3.add("100");
    
    final ViewCriteriaItem viewCriteriaItem3 =
      (ViewCriteriaItem)DataFactory.INSTANCE.create(TypeHelper.INSTANCE.getType(ViewCriteriaItem.class));
    viewCriteriaItem3.setConjunction("And");
    viewCriteriaItem3.setUpperCaseCompare(true);
    viewCriteriaItem3.setAttribute("employeeId");
    viewCriteriaItem3.setOperator("<=");
    viewCriteriaItem3.setValue(values3);

    viewCriteriaRow.getItem().set(0, viewCriteriaItem3);
    
    final List<EmployeesSDO> employeesList3 = hrSessionEJB.findEmployeesSDO(findCriteria, findControl);
        
        System.out.println("employeesList3: " + employeesList3);
        
        for (EmployeesSDO employees :
             employeesList3) {
            System.out.println("employees: " + employees);
        }
  }
}
