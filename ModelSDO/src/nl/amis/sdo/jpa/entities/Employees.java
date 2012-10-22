package nl.amis.sdo.jpa.entities;

import commonj.sdo.helper.DataFactory;

import java.io.Serializable;

import java.util.Date;


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


import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import javax.xml.bind.annotation.XmlTransient;

@Entity
@SequenceGenerator(name="EMPLOYEES_SEQ",sequenceName="EMPLOYEES_SEQ")
public class Employees implements BaseEntity<EmployeesSDO>, Serializable {
    @SuppressWarnings("compatibility:3441819862910628280")
    private static final long serialVersionUID = 1L;
    @Column(name="COMMISSION_PCT")
    private Double commissionPct;
    @Column(nullable = false, unique = true, length = 25)
    private String email;
    @Id
    @GeneratedValue(generator="EMPLOYEES_SEQ",strategy=GenerationType.SEQUENCE)
    @Column(name="EMPLOYEE_ID", nullable = false)
    private Long employeeId;
    @Column(name="FIRST_NAME", length = 20)
    private String firstName;
    @Column(name="HIRE_DATE", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date hireDate;
    @Column(name="JOB_ID", nullable = false, length = 10)
    private String jobId;
    @Column(name="LAST_NAME", nullable = false, length = 25)
    private String lastName;
    @Column(name="PHONE_NUMBER", length = 20)
    private String phoneNumber;
    private Double salary;

    @Column(name = "MANAGER_ID")
    private Long managerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DEPARTMENT_ID")
    private Departments departments;

    public Employees() {
    }


    public Double getCommissionPct() {
        return commissionPct;
    }

    public void setCommissionPct(Double commissionPct) {
        this.commissionPct = commissionPct;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

  public Object getId() {
      return getEmployeeId();
  }
  
    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }



    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }


    @XmlTransient
    public Departments getDepartments() {
        return departments;
    }

    public void setDepartments(Departments departments) {
        this.departments = departments;
    }

    public void setManagerId(Long managerId) {
        this.managerId = managerId;
    }

    public Long getManagerId() {
        return managerId;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public Date getHireDate() {
        return hireDate;
    }
    
    public EmployeesSDO toDataObject() {
          final EmployeesSDO employeesSDO = ( EmployeesSDO )DataFactory.INSTANCE.create(EmployeesSDO.class);
          
          if (getCommissionPct() != null ) {
            employeesSDO.setCommissionPct(getCommissionPct());
          }

          employeesSDO.setEmail(getEmail());
          employeesSDO.setEmployeeId(getEmployeeId());
          employeesSDO.setFirstName(getFirstName());
          employeesSDO.setHireDate(getHireDate());
          employeesSDO.setJobId(getJobId());
          employeesSDO.setLastName(getLastName());

          if (getManagerId() != null ) {
            employeesSDO.setManagerId(getManagerId());
          }

          employeesSDO.setPhoneNumber(getPhoneNumber());
          employeesSDO.setSalary(getSalary());
          return employeesSDO;
    }
}