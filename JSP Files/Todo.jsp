<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!doctype html>
<html lang="en">
<head>
	<title>Todo</title>
	<!-- Latest compiled and minified CSS -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">	
	<%-- <link rel="stylesheet" href="https://bootswatch.com/paper/bootstrap.min.css"> --%>
</head>
<body>
<div class="container">

	<!-- Page Header -->
	<div class="page-header">
		<h2>
			Todo
			<small>
				<span id="remaining">${remaining}</span>
				of
				<span id="total">${fn:length(todos)}</span>
				remaining
				<small>
					[<a href="Archive">Archive</a>]
				</small>
			</small>
		</h2>
	</div>

	<form action="Todo" method="post">
		<div class="row">
			<div class="col-xs-9 col-sm-10">
				<div class="form-group">
					<label class="sr-only" for="description">Todo Description</label>
					<input type="text" class="form-control" id="description" name="description" placeholder="Enter todo description">
				</div>
				<c:if test="${not empty error}">
					<p class="well-sm bg-danger">${error}</p>
				</c:if>
			</div>
			<div class="col-xs-3 col-sm-2">
				<button type="submit" class="btn btn-success btn-block">Add Todo</button>
			</div>
		</div>
	</form>

	<c:if test="${empty todos}">
		<div class="jumbotron">
			<h1>Uh-Oh! <small>There are no tasks to display.</small></h1>
		</div>
	</c:if>

	<c:if test="${not empty todos}">
	<table class="table table-hover table-striped table-bordered">
		<thead>
			<tr>
				<th>Task</th>
				<th>Action</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${todos}" var="todo">
				<tr>					
					<td>
						<c:choose>
							<c:when test="${todo.notDone}">
								${todo.description}
							</c:when>
							<c:otherwise>
								<s>${todo.description}</s>
							</c:otherwise>
						</c:choose>						
					</td>
					<td>
					
						<%-- Construct the Update URL --%>
						<c:url value="Update" var="updateUrl">
						   <c:param name="id" value="${todo.id}"/>
						   <c:param name="state" value="${todo.oppositeState}"/>
						</c:url>
						
						<c:choose>
							<c:when test="${todo.notDone}">
								<%--<a class="btn btn-primary btn-xs" href="Update?id=${todo.id}&state=${todo.oppositeState}">Mark as Done</a>--%>
								<a class="btn btn-primary btn-xs" href="${updateUrl}">Mark as Done</a>
							</c:when>
							<c:otherwise>
								<%--<a class="btn btn-default btn-xs" href="Update?id=${todo.id}&state=${todo.oppositeState}">Mark as Not Done</a> --%>
								<a class="btn btn-default btn-xs" href="${updateUrl}">Mark as Not Done</a>
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</c:if>
	
	
	<div class="page-header">
		<h2>Archived 
			<small>
				<span id="totalArchived">${fn:length(archived)} todo(s)</span> 
				have been archived.
			</small></h2>
	</div>
		
	<c:if test="${empty archived}">
		<div class="well">
			<h3 class="text-center">No Items have been archived.</h3>
		</div>
	</c:if>

	<c:if test="${not empty archived}">
	
	<table class="table table-hover table-striped table-bordered">
		<thead>
			<tr>
				<th>Task</th>
				<th>Action</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${archived}" var="todo">
				<tr>					
					<td>
						<c:choose>
							<c:when test="${todo.notDone}">
								${todo.description}
							</c:when>
							<c:otherwise><s>${todo.description}</s></c:otherwise>
						</c:choose>						
					</td>
					<td>
					
						<c:url value="Update" var="updateUrl">
						   <c:param name="id" value="${todo.id}"/>
						   <c:param name="state" value="${todo.oppositeState}"/>
						</c:url>
						
						<a class="btn btn-default btn-xs" href="${updateUrl}">Mark as Not Done</a>
						
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</c:if>
	
</div>

</body>
</html>