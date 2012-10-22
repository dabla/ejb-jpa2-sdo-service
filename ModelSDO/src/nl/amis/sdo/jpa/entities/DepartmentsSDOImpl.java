package nl.amis.sdo.jpa.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.persistence.sdo.SDODataObject;

public class DepartmentsSDOImpl extends SDODataObject implements DepartmentsSDO {

   public static final int START_PROPERTY_INDEX = 0;

   public static final int END_PROPERTY_INDEX = START_PROPERTY_INDEX + 4;

   public DepartmentsSDOImpl() {}

   public long getDepartmentId() {
      return getLong(START_PROPERTY_INDEX + 0);
   }

   public void setDepartmentId(long value) {
      set(START_PROPERTY_INDEX + 0 , value);
   }

   public java.lang.String getDepartmentName() {
      return getString(START_PROPERTY_INDEX + 1);
   }

   public void setDepartmentName(java.lang.String value) {
      set(START_PROPERTY_INDEX + 1 , value);
   }

   public java.util.List getEmployeesList() {
      return getList(START_PROPERTY_INDEX + 2);
   }

   public void setEmployeesList(java.util.List value) {
      set(START_PROPERTY_INDEX + 2 , value);
   }

   public long getLocationId() {
      return getLong(START_PROPERTY_INDEX + 3);
   }

   public void setLocationId(long value) {
      set(START_PROPERTY_INDEX + 3 , value);
   }

   public nl.amis.sdo.jpa.entities.EmployeesSDO getManager() {
      return (nl.amis.sdo.jpa.entities.EmployeesSDO)get(START_PROPERTY_INDEX + 4);
   }

   public void setManager(nl.amis.sdo.jpa.entities.EmployeesSDO value) {
      set(START_PROPERTY_INDEX + 4 , value);
   }

   public Departments toEntity() {
     final Departments departments = new Departments();
     departments.setDepartmentId(getDepartmentId());
     departments.setDepartmentName(getDepartmentName());
     if (getEmployeesList() != null) {
         final List<Employees> employeesList = new ArrayList<Employees>(getEmployeesList().size());
         for (final EmployeesSDO employee : (List<EmployeesSDO>)getEmployeesList()) {
             employeesList.add(employee.toEntity());
         }
         departments.setEmployeesList(employeesList);
     }
     departments.setLocationId(getLocationId());
     departments.setManager(getManager().toEntity());
     return departments;
   }

   public String toString() {
     final StringBuilder result = new StringBuilder(getClass().getName())
     .append("[departmentId=").append(getDepartmentId())
     .append(",departmentName=").append(getDepartmentName());
     if (getEmployeesList() != null) {
       result.append(",employeesList=").append(Arrays.toString(getEmployeesList().toArray()));
     }
     return result.append(",locationId=").append(getLocationId())
     .append(",manager=").append(getManager()).append("]").toString();
   }
}