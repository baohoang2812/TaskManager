using System;
using System.Collections.Generic;

namespace TaskManager.Models
{
    public partial class User
    {
        public User()
        {
            Task = new HashSet<Task>();
        }

        public int UserId { get; set; }
        public string Username { get; set; }
        public string PasswordHash { get; set; }
        public int? RoleId { get; set; }
        public string Fullname { get; set; }
        public string Email { get; set; }
        public int? GroupId { get; set; }
        public string Phone { get; set; }
        public DateTime? ModifyTime { get; set; }

        public virtual Group Group { get; set; }
        public virtual Role Role { get; set; }
        public virtual ICollection<Task> Task { get; set; }
    }
}
