<%-- 
    Document   : users
    Created on : 26-Jul-2022, 4:32:33 PM
    Author     : Jashan Gill
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>My users</title>
    </head>
    <body>
            <form action="users" method="POST">
                
                <label for="e">Email</label>
                <input type="email" name="email" id="e" value=""><br>
                
                <label for="f">First Name</label>
                <input type="text" name="firstName" id="f" value=""><br>
                
                <label for="l">Last Name</label>
                <input type="text" name="lastName" id="l" value=""><br>
                
                <label for="p">Password</label>
                <input type="password" name="password" id="p" value=""><br>
                
                <label for="role">Role</label>
                <select name="role"><br>
                    <option value="2">Regular User</option>
                    <option value="1">System Admin</option>
                    <option value="3">Company Admin</option>
                </select>
                <input type="hidden" name="action" value="add">
                <input type="submit" value="Create">
            </form>

            <table>
                <tr>
                  <th>Email</th>
                  <th>First Name</th>
                  <th>Last Name</th>
                  <th>Role</th>
                  <th>Active</th>
                  <th>Edit</th>
                  <th>Delete</th>
                </tr>             
                <c:forEach items="${users}" var="user">
                    <tr>
                    <td>${user.email}</td>
                    <td>${user.firstName}</td>
                    <td>${user.lastName}</td>
                    <td>${user.role}</td>
                    <td>
                        <c:if test="${user.active eq true}">Active</c:if>
                        <c:if test="${user.active ne true}">Inactive</c:if>
                    </td>
                    <td><a href="users?act=edit&amp;email=${user.email}">edit</a><br></td>
                    <td><a href="users?act=del&amp;email=${user.email}">delete</a><br></td>
                    </tr>
                </c:forEach>
            </table>

            <h2>Edit user</h2>
            <form action="users" method="post">
                 <label for="firstName">First Name</label>
                <input type="text" name="firstName" id="firstName" value="${user.firstName}"><br>
                
                 <label for="lastName">Last Name</label>
                <input type="text" name="lastName" id="lastName" value="${user.lastName}"><br>
                
                <label for="role">Role</label>
                <select name="role" id="role" default="${user.role}"><br>
                    <option value="2"
                      <c:if test="${user.role eq '2'}">selected</c:if>       
                            >Regular User</option>
                    <option value="1"
                      <c:if test="${user.role eq '1'}">selected</c:if>   
                            >System Admin</option>
                    <option value="3"
                      <c:if test="${user.role eq '3'}">selected</c:if>         
                            >Company Admin</option>
                </select>
                <label for="a">Active</label>
                <input type="checkbox" name="active" id="a"
                   <c:if test="${user.active}">checked</c:if>    
                   value="true">Yes</input><br>
                <input type="hidden" name="action" value="edit">
                <input type="submit" value="Save">
            </form>
        <p>
            <c:if test="${message eq 'create'}">user created</c:if>
            <c:if test="${message eq 'update'}">user updated</c:if>
            <c:if test="${message eq 'delete'}">user deleted</c:if>
            <c:if test="${message eq 'error'}">Sorry, something went wrong.</c:if>
            </p>
    </body> 
</html>
