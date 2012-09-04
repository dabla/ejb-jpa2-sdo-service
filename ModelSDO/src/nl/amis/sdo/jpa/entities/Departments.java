package nl.amis.sdo.jpa.entities;

import commonj.sdo.helper.DataFactory;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name="DEPARTMENTS_SEQ",sequenceName="DEPARTMENTS_SEQ")
public class Departments implements BaseEntity<DepartmentsSDO>, Serializable {
    @SuppressWarnings("compatibility:-6447132931414648339")
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(generator="DEPARTMENTS_SEQ",strategy=GenerationType.SEQUENCE)
    @Column(name="DEPARTMENT_ID", nullable = false)
    private Long departmentId;

    @Column(name="DEPARTMENT_NAME", nullable = false, length = 30)
    private String departmentName;

    @Column(name="LOCATION_ID")
    private Long locationId;

    @ManyToOne
    @JoinColumn(name = "MANAGER_ID")
    @OneToMany(fetch = FetchType.EAGER)
    private Employees manager;

    @OneToMany(mappedBy = "departments", fetch = FetchType.EAGER)
    private List<Employees> employeesList;

    public Departments() {
    }

    public Object getId() {
        return getDepartmentId();
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }


    public List<Employees> getEmployeesList() {
        return employeesList;
    }

    public void setEmployeesList(List<Employees> employeesList) {
        this.employeesList = employeesList;
    }

    public Employees addEmployees(Employees employees) {
        getEmployeesList().add(employees);
        employees.setDepartments(this);
        return employees;
    }

    public Employees removeEmployees(Employees employees) {
        getEmployeesList().remove(employees);
        employees.setDepartments(null);
        return employees;
    }

    public void setManager(Employees manager) {
        this.manager = manager;
    }

    public Employees getManager() {
        return manager;
    }
    
    public DepartmentsSDO toDataObject() {
      final DepartmentsSDO departmentsSDO = ( DepartmentsSDO )DataFactory.INSTANCE.create(DepartmentsSDO.class);
      departmentsSDO.setDepartmentId(getDepartmentId());
      departmentsSDO.setDepartmentName(getDepartmentName());
      if (getEmployeesList() != null) {
          final List<EmployeesSDO> employeesList = new ArrayList<EmployeesSDO>(getEmployeesList().size());
          for (final Employees employee : getEmployeesList()) {
              employeesList.add(employee.toDataObject());
          }
          departmentsSDO.setEmployeesList(employeesList);
      }
      departmentsSDO.setLocationId(getLocationId());
      if (getManager() != null)
        departmentsSDO.setManager(getManager().toDataObject());
      return departmentsSDO;
    }
}
