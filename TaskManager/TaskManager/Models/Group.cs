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
        public string Name { get; set; }
        public DateTime? CreatedTime { get; set; }
        public string Description { get; set; }
        public DateTime? ModifyTime { get; set; }

        public virtual ICollection<User> User { get; set; }
    }
}
