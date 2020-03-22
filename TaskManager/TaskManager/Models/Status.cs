using System;
using System.Collections.Generic;

namespace TaskManager.Models
{
    public partial class Status
    {
        public Status()
        {
            Task = new HashSet<Task>();
        }

        public int StatusId { get; set; }
        public string Name { get; set; }

        public virtual ICollection<Task> Task { get; set; }
    }
}
