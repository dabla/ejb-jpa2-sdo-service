package nl.amis.sdo.jpa;

import commonj.sdo.helper.DataFactory;
import commonj.sdo.helper.TypeHelper;
import commonj.sdo.helper.XSDHelper;

import java.util.Hashtable;

import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import nl.amis.sdo.jpa.entities.Departments;
import nl.amis.sdo.jpa.entities.DepartmentsSDO;
import nl.amis.sdo.jpa.entities.EmployeesSDO;
import nl.amis.sdo.jpa.services.HrSessionEJB;

import oracle.jbo.common.service.types.FindControl;
import oracle.jbo.common.service.types.FindCriteria;
import oracle.jbo.common.service.types.FindControlImpl;
import oracle.jbo.common.service.types.FindCriteriaImpl;

import org.eclipse.persistence.sdo.SDOType;
import org.eclipse.persistence.sdo.helper.jaxb.JAXBHelperContext;

public class HrSessionEJBClientSDO {
    public HrSessionEJBClientSDO() {
    }

    public static void main(String[] args) throws Exception {
        try {
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

            final Context context = getInitialContext();
          System.out.println("context = " +
                             context);
            HrSessionEJB hrSessionEJB =
                (HrSessionEJB)context.lookup("EjbSdoService-HrSessionEJB#nl.amis.sdo.jpa.services.HrSessionEJB");
          System.out.println("hrSessionEJB = " +
                             hrSessionEJB);
            
          final FindCriteria findCriteria = (FindCriteria)DataFactory.INSTANCE.create(TypeHelper.INSTANCE.getType(FindCriteria.class));
          findCriteria.setFetchStart(0);
          findCriteria.setFetchSize(-1);
          findCriteria.setExcludeAttribute(false);
          
          final FindControl findControl = (FindControl)DataFactory.INSTANCE.create(TypeHelper.INSTANCE.getType(FindControl.class));
          findControl.setRetrieveAllTranslations(false);
          
          // here we use generic count method
          final Long countDepartments = hrSessionEJB.count(Departments.class, findCriteria,findControl);
          
          System.out.println("countDepartments: " + countDepartments);
          
            for (DepartmentsSDO departments :
                 (List<DepartmentsSDO>)hrSessionEJB.findDepartmentsSDO(findCriteria,findControl)) {
                printDepartments(departments);
            }
          
          // here we use entity specific count method
          final Long countEmployees = hrSessionEJB.countEmployeesSDO(findCriteria,findControl);
          
          System.out.println("countEmployees: " + countEmployees);
          
          for (EmployeesSDO employees :
               (List<EmployeesSDO>)hrSessionEJB.findEmployeesSDO(findCriteria,findControl)) {
              System.out.println("employees: " + employees);
          }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void printDepartments(DepartmentsSDO departments) {
        System.out.println("departmentId = " + departments.getDepartmentId());
        System.out.println("departmentName = " +
                           departments.getDepartmentName());
        System.out.println("locationId = " + departments.getLocationId());
        System.out.println("employeesList = " +
                           departments.getEmployeesList());
        System.out.println("manager = " + departments.getManager());
    }


    private static Context getInitialContext() throws NamingException {
        Hashtable env = new Hashtable();
        // WebLogic Server 10.x connection details
        env.put(Context.INITIAL_CONTEXT_FACTORY,
                "weblogic.jndi.WLInitialContextFactory");
        env.put(Context.PROVIDER_URL, "t3://pc100016989:7001");
        return new InitialContext(env);
    }

}
