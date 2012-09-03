package nl.amis.sdo.jpa.services;

import commonj.sdo.DataObject;

import java.util.List;

import javax.ejb.Remote;


import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.soap.SOAPBinding;

import javax.xml.rpc.ServiceException;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

import nl.amis.sdo.jpa.entities.Departments;
import nl.amis.sdo.jpa.entities.DepartmentsSDO;
import nl.amis.sdo.jpa.entities.Employees;
import nl.amis.sdo.jpa.entities.EmployeesSDO;

import oracle.jbo.common.service.types.FindControl;
import oracle.jbo.common.service.types.ProcessData;
import oracle.jbo.common.types.FindCriteria;

import oracle.webservices.annotations.PortableWebService;
import oracle.webservices.annotations.SDODatabinding;

import weblogic.nodemanager.util.ProcessControl;


@Remote
@SOAPBinding(parameterStyle=SOAPBinding.ParameterStyle.WRAPPED, style=SOAPBinding.Style.DOCUMENT)
@PortableWebService(targetNamespace="/nl.amis.sdo.jpa.services/", name="HrSessionEJBBeanWS",
    wsdlLocation="nl/amis/sdo/jpa/services/HrSessionEJBBeanWS.wsdl")
@SDODatabinding(schemaLocation="nl/amis/sdo/jpa/services/HrSessionEJBBeanWS.xsd")
public interface HrSessionEJB {
    Object queryByRange(String jpqlStmt, int firstResult, int maxResults);
    
  @WebMethod(action="/nl.amis.sdo.jpa.services/getDepartments",
      operationName="getDepartments")
  @RequestWrapper(targetNamespace="/nl.amis.sdo.jpa.services/",
      localName="getDepartments")
  @ResponseWrapper(targetNamespace="/nl.amis.sdo.jpa.services/",
      localName="getDepartmentsResponse")
  @WebResult(name="result")
  DepartmentsSDO getDepartments(@WebParam(mode = WebParam.Mode.IN, name="employeeId")
      Long employeeId) throws RuntimeException;

  @WebMethod(action="/nl.amis.sdo.jpa.services/createDepartments",
      operationName="createDepartments")
  @RequestWrapper(targetNamespace="/nl.amis.sdo.jpa.services/",
      localName="createDepartments")
  @ResponseWrapper(targetNamespace="/nl.amis.sdo.jpa.services/",
      localName="createDepartmentsResponse")
  @WebResult(name="result")
  DepartmentsSDO createDepartments(@WebParam(mode = WebParam.Mode.IN, name="departments")
      DepartmentsSDO departments) throws RuntimeException;

  @WebMethod(action="/nl.amis.sdo.jpa.services/updateDepartments",
      operationName="updateDepartments")
  @RequestWrapper(targetNamespace="/nl.amis.sdo.jpa.services/",
      localName="updateDepartments")
  @ResponseWrapper(targetNamespace="/nl.amis.sdo.jpa.services/",
      localName="updateDepartmentsResponse")
  @WebResult(name="result")
  DepartmentsSDO updateDepartments(@WebParam(mode = WebParam.Mode.IN, name="departments")
      DepartmentsSDO departments) throws RuntimeException;

  @WebMethod(action="/nl.amis.sdo.jpa.services/deleteDepartments",
      operationName="deleteDepartments")
  @RequestWrapper(targetNamespace="/nl.amis.sdo.jpa.services/",
      localName="deleteDepartments")
  @ResponseWrapper(targetNamespace="/nl.amis.sdo.jpa.services/",
      localName="deleteDepartmentsResponse")
  void deleteDepartments(@WebParam(mode = WebParam.Mode.IN, name="departments")
      DepartmentsSDO departments) throws RuntimeException;

  @WebMethod(action="/nl.amis.sdo.jpa.services/mergeDepartments",
      operationName="mergeDepartments")
  @RequestWrapper(targetNamespace="/nl.amis.sdo.jpa.services/",
      localName="mergeDepartments")
  @ResponseWrapper(targetNamespace="/nl.amis.sdo.jpa.services/",
      localName="mergeDepartmentsResponse")
  @WebResult(name="result")
  DepartmentsSDO mergeDepartments(@WebParam(mode = WebParam.Mode.IN, name="departments")
      DepartmentsSDO departments) throws RuntimeException;
    
  @WebMethod(action="/nl.amis.sdo.jpa.services/findDepartments",
      operationName="findDepartments")
  @RequestWrapper(targetNamespace="/nl.amis.sdo.jpa.services/",
      localName="findDepartments")
  @ResponseWrapper(targetNamespace="/nl.amis.sdo.jpa.services/",
      localName="findDepartmentsResponse")
  @WebResult(name="result")
  List<DepartmentsSDO> findDepartments(@WebParam(mode = WebParam.Mode.IN,
          name="findCriteria")
      FindCriteria findCriteria, @WebParam(mode = WebParam.Mode.IN, name="findControl")
      FindControl findControl) throws RuntimeException;
  
  @WebMethod(action="/nl.amis.sdo.jpa.services/processDepartments",
      operationName="processDepartments")
  @RequestWrapper(targetNamespace="/nl.amis.sdo.jpa.services/",
      localName="processDepartments")
  @ResponseWrapper(targetNamespace="/nl.amis.sdo.jpa.services/",
      localName="processDepartmentsResponse")
  @WebResult(name="result")
  List<DepartmentsSDO> processDepartments(@WebParam(mode = WebParam.Mode.IN, name="changeOperation")
      String changeOperation, @WebParam(mode = WebParam.Mode.IN, name="departments")
      List<DepartmentsSDO> departments, @WebParam(mode = WebParam.Mode.IN, name="processControl")
      oracle.jbo.common.service.types.ProcessControl processControl) throws oracle.jbo.service.errors.ServiceException;

  @WebMethod(action="/nl.amis.sdo.jpa.services/processCSDepartments",
      operationName="processCSDepartments")
  @RequestWrapper(targetNamespace="/nl.amis.sdo.jpa.services/",
      localName="processCSDepartments")
  @ResponseWrapper(targetNamespace="/nl.amis.sdo.jpa.services/",
      localName="processCSDepartmentsResponse")
  @WebResult(name="result")
  ProcessData processCSDepartments(@WebParam(mode = WebParam.Mode.IN, name="processData")
      ProcessData processData, @WebParam(mode = WebParam.Mode.IN, name="processControl")
      oracle.jbo.common.service.types.ProcessControl processControl) throws oracle.jbo.service.errors.ServiceException;

  @WebMethod(action="/nl.amis.sdo.jpa.services/getEmployees",
      operationName="getEmployees")
  @RequestWrapper(targetNamespace="/nl.amis.sdo.jpa.services/",
      localName="getEmployees")
  @ResponseWrapper(targetNamespace="/nl.amis.sdo.jpa.services/",
      localName="getEmployeesResponse")
  @WebResult(name="result")
  EmployeesSDO getEmployees(@WebParam(mode = WebParam.Mode.IN, name="employeeId")
      Long employeeId) throws RuntimeException;

  @WebMethod(action="/nl.amis.sdo.jpa.services/createEmployees",
      operationName="createEmployees")
  @RequestWrapper(targetNamespace="/nl.amis.sdo.jpa.services/",
      localName="createEmployees")
  @ResponseWrapper(targetNamespace="/nl.amis.sdo.jpa.services/",
      localName="createEmployeesResponse")
  @WebResult(name="result")
  EmployeesSDO createEmployees(@WebParam(mode = WebParam.Mode.IN, name="employees")
      EmployeesSDO employees) throws RuntimeException;

  @WebMethod(action="/nl.amis.sdo.jpa.services/updateEmployees",
      operationName="updateEmployees")
  @RequestWrapper(targetNamespace="/nl.amis.sdo.jpa.services/",
      localName="updateEmployees")
  @ResponseWrapper(targetNamespace="/nl.amis.sdo.jpa.services/",
      localName="updateEmployeesResponse")
  @WebResult(name="result")
  EmployeesSDO updateEmployees(@WebParam(mode = WebParam.Mode.IN, name="employees")
      EmployeesSDO employees) throws RuntimeException;

  @WebMethod(action="/nl.amis.sdo.jpa.services/deleteEmployees",
      operationName="deleteEmployees")
  @RequestWrapper(targetNamespace="/nl.amis.sdo.jpa.services/",
      localName="deleteEmployees")
  @ResponseWrapper(targetNamespace="/nl.amis.sdo.jpa.services/",
      localName="deleteEmployeesResponse")
  void deleteEmployees(@WebParam(mode = WebParam.Mode.IN, name="employees")
      EmployeesSDO employees) throws RuntimeException;

  @WebMethod(action="/nl.amis.sdo.jpa.services/mergeEmployees",
      operationName="mergeEmployees")
  @RequestWrapper(targetNamespace="/nl.amis.sdo.jpa.services/",
      localName="mergeEmployees")
  @ResponseWrapper(targetNamespace="/nl.amis.sdo.jpa.services/",
      localName="mergeEmployeesResponse")
  @WebResult(name="result")
  EmployeesSDO mergeEmployees(@WebParam(mode = WebParam.Mode.IN, name="employees")
      EmployeesSDO employees) throws RuntimeException;
    
  @WebMethod(action="/nl.amis.sdo.jpa.services/findEmployees",
      operationName="findEmployees")
  @RequestWrapper(targetNamespace="/nl.amis.sdo.jpa.services/",
      localName="findEmployees")
  @ResponseWrapper(targetNamespace="/nl.amis.sdo.jpa.services/",
      localName="findEmployeesResponse")
  @WebResult(name="result")
  List<EmployeesSDO> findEmployees(@WebParam(mode = WebParam.Mode.IN,
          name="findCriteria")
      FindCriteria findCriteria, @WebParam(mode = WebParam.Mode.IN, name="findControl")
      FindControl findControl) throws RuntimeException;
  
  @WebMethod(action="/nl.amis.sdo.jpa.services/processEmployees",
      operationName="processEmployees")
  @RequestWrapper(targetNamespace="/nl.amis.sdo.jpa.services/",
      localName="processEmployees")
  @ResponseWrapper(targetNamespace="/nl.amis.sdo.jpa.services/",
      localName="processEmployeesResponse")
  @WebResult(name="result")
  List<EmployeesSDO> processEmployees(@WebParam(mode = WebParam.Mode.IN, name="changeOperation")
      String changeOperation, @WebParam(mode = WebParam.Mode.IN, name="employees")
      List<EmployeesSDO> employees, @WebParam(mode = WebParam.Mode.IN, name="processControl")
      oracle.jbo.common.service.types.ProcessControl processControl) throws oracle.jbo.service.errors.ServiceException;

  @WebMethod(action="/nl.amis.sdo.jpa.services/processCSEmployees",
      operationName="processCSEmployees")
  @RequestWrapper(targetNamespace="/nl.amis.sdo.jpa.services/",
      localName="processCSEmployees")
  @ResponseWrapper(targetNamespace="/nl.amis.sdo.jpa.services/",
      localName="processCSEmployeesResponse")
  @WebResult(name="result")
  ProcessData processCSEmployees(@WebParam(mode = WebParam.Mode.IN, name="processData")
      ProcessData processData, @WebParam(mode = WebParam.Mode.IN, name="processControl")
      oracle.jbo.common.service.types.ProcessControl processControl) throws oracle.jbo.service.errors.ServiceException;
}