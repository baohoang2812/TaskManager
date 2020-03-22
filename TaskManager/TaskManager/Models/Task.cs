using System;
using System.Collections.Generic;

namespace TaskManager.Models
{
    public partial class Task
    {
        public Task()
        {
            InverseSource = new HashSet<Task>();
        }

        public int TaskId { get; set; }
        public string Name { get; set; }
        public int? SourceId { get; set; }
        public string Description { get; set; }
        public string Report { get; set; }
        public string ManagerReview { get; set; }
        public int? Mark { get; set; }
        public DateTime? ReviewedTime { get; set; }
        public DateTime? StartTime { get; set; }
        public DateTime? EndTime { get; set; }
        public int StatusId { get; set; }
        public DateTime CreatedTime { get; set; }
        public string Creator { get; set; }
        public int? HandlerId { get; set; }
        public string ConfirmationImage { get; set; }
        public DateTime? ModifyTime { get; set; }

        public virtual User Handler { get; set; }
        public virtual Task Source { get; set; }
        public virtual Status Status { get; set; }
        public virtual ICollection<Task> InverseSource { get; set; }
    }
}
