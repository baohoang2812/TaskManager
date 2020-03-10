using System;
using System.Collections.Generic;

namespace TaskManager.Models
{
    public partial class Group
    {
        public Group()
        {
            User = new HashSet<User>();
        }

        public int GroupId { get; set; }
        public int? ManagerId { get; set; }

        public virtual User Manager { get; set; }
        public virtual ICollection<User> User { get; set; }
    }
}
