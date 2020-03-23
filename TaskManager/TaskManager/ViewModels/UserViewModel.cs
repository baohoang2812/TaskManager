using Newtonsoft.Json;
using System;

namespace TaskManager.ViewModels
{
    public class UserViewModel
    {
        [JsonProperty("userId")]
        public int UserId { get; set; }
        [JsonProperty("roleId")]
        public int? RoleId { get; set; }
        [JsonProperty("roleName")]
        public string RoleName { get; set; }
        [JsonProperty("fullName")]
        public string Fullname { get; set; }
        [JsonProperty("email")]
        public string Email { get; set; }
        [JsonProperty("groupId")]
        public int? GroupId { get; set; }
        [JsonProperty("phone")]
        public string Phone { get; set; }
        [JsonProperty("username")]
        public string Username { get; set; }
    }

    public class UserCreateViewModel
    {
        public string Username { get; set; }
        public string Password{ get; set; }
        public int? RoleId { get; set; } 
        public string Fullname { get; set; } 
        public string Email { get; set; }
        public string Phone { get; set; }
    }

    public class UserEditViewModel
    {
        public string Password { get; set; }
        public int? RoleId { get; set; }
        public string RoleName { get; set; }
        public string Fullname { get; set; }
        public string Email { get; set; }
        public int? GroupId { get; set; }
        public string Phone { get; set; }
        public DateTime? ModifyTime { get; set; } = DateTime.Now;
    }
}
