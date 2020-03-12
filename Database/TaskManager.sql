Drop Database IF Exists TaskManager
go

create database TaskManager
go 

use TaskManager
go
create table Task(
   TaskId int primary key Identity(1,1),
   [Name] nvarchar(200) not null,
   SourceId int, 
   [Description] nvarchar(max) not null,
   Report nvarchar(max),
   ManagerReview nvarchar(max),
   Mark int,
   ReviewedTime datetime, 
   StartTime datetime not null,
   EndTime datetime,
   StatusId int not null,
   CreatedTime datetime not null,
   Creator nvarchar(50) not null,
   HandlerId int,
   ConfirmationImage nvarchar(max), 
)

create table Status(
	StatusId int primary key Identity(1,1),
	Name nvarchar(256) not null
)

create table [User](
	UserId int primary key Identity(1,1),
	Username nvarchar(256) not null,
	PasswordHash nvarchar(max) not null,
	RoleId int not null, 
	Fullname nvarchar(max) not null,
	Email nvarchar(256) not null,
	GroupId int)


create table Role(
	RoleId int primary key Identity(1,1),
	Name nvarchar(256) not null 
)

create table [Group](
	GroupId int primary key Identity(1,1),
	ManagerId int,
)
