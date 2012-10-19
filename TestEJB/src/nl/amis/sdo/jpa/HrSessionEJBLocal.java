package nl.amis.sdo.jpa;

import commonj.sdo.helper.DataFactory;
import commonj.sdo.helper.TypeHelper;

import commonj.sdo.helper.XSDHelper;

import java.util.List;

import javax.persistence.EntityManager;

import javax.persistence.Persistence;

import nl.amis.sdo.jpa.entities.Departments;
import nl.amis.sdo.jpa.entities.DepartmentsSDOImpl;
import nl.amis.sdo.jpa.entities.EmployeesSDO;
import nl.amis.sdo.jpa.services.HrSessionEJB;
import nl.amis.sdo.jpa.services.HrSessionEJBBean;

import oracle.jbo.common.service.types.FindControl;
import oracle.jbo.common.service.types.FindCriteria;

public class HrSessionEJBLocal {
  private static final class HrSessionEJBBeanLocal extends HrSessionEJBBean {
    @Override
    protected EntityManager getEntityManager() {
      return Persistence.createEntityManagerFactory("ModelSDOLocal").createEntityManager();
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
    
    final Long countDepartments = hrSessionEJB.count(Departments.class, findCriteria, findControl);
    
    System.out.println("countDepartments: " + countDepartments);
    
      /*for (DepartmentsSDO departments :
           (List<DepartmentsSDO>)hrSessionEJB.find(DepartmentsSDOImpl.class, findCriteria, findControl)) {
          printDepartments(departments);
      }*/
    
    // here we use entity specific count method, which is also exposed and callable through SOAP
    final Long countEmployees = hrSessionEJB.countEmployeesSDO(findCriteria, findControl);
    
    System.out.println("countEmployees: " + countEmployees);
    
    for (EmployeesSDO employees :
         (List<EmployeesSDO>)hrSessionEJB.findEmployeesSDO(findCriteria, findControl)) {
        System.out.println("employees: " + employees);
    }
  }
}
