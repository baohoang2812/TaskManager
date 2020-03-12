using AutoMapper;
using TaskManager.Constants;

namespace TaskManager.ViewModels
{
    public class UserViewModel
    {
        public int UserId { get; set; }
        public int? RoleId { get; set; }
        public string Fullname { get; set; }
        public string Email { get; set; }
        public int? GroupId { get; set; }
    }

    public class LoginViewModel
    {
        public string Username { get; set; }
        public string Password { get; set; }
    }

    public class UserCreateViewModel
    {
        public string Username { get; set; }
        public string Password{ get; set; }
        public int? RoleId { get; set; } = RoleName.USER;
        public string Fullname { get; set; } 
        public string Email { get; set; }
        public int? GroupId { get; set; }
    }

    public class UserEditViewModel
    {
        public string Password { get; set; }
        public int? RoleId { get; set; }
        public string RoleName { get; set; }
        public string Fullname { get; set; }
        public string Email { get; set; }
        public int? GroupId { get; set; }
    }
}
