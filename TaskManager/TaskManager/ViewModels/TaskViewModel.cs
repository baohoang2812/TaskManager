using System;

namespace TaskManager.ViewModels
{
    public class TaskViewModel
    {
        public int TaskId { get; set; }
        public string Name { get; set; }
        public int? SourceId { get; set; }
        public string Description { get; set; }
        public string Report { get; set; }
        public string ManagerReview { get; set; }
        public int? Mark { get; set; }
        public DateTime? ReviewedTime { get; set; }
        public DateTime StartTime { get; set; } 
        public DateTime? EndTime { get; set; } 
        public int? StatusId { get; set; } 
        public DateTime CreatedTime { get; set; } = DateTime.Now;
        public string Creator { get; set; }
        public int? HandlerId { get; set; }
        public string ConfirmationImage { get; set; }
    }

    public class TaskCreateViewModel
    {
        public string Name { get; set; }
        public int? SourceId { get; set; }
        public string Description { get; set; }
        public string Report { get; set; }
        public string ManagerReview { get; set; }
        public int? Mark { get; set; }
        public DateTime? ReviewedTime { get; set; }
        public DateTime StartTime { get; set; } = DateTime.Now;
        public DateTime? EndTime { get; set; }
        public int? StatusId { get; set; }
        public DateTime CreatedTime { get; set; }
        public string Creator { get; set; }
        public int? HandlerId { get; set; }
        public string ConfirmationImage { get; set; }
    }
    public class TaskEditViewModel
    {
        public string Name { get; set; }
        public int? SourceId { get; set; }
        public string Description { get; set; }
        public string Report { get; set; }
        public string ManagerReview { get; set; }
        public int? Mark { get; set; }
        public DateTime? ReviewedTime { get; set; }
        public DateTime StartTime { get; set; }
        public DateTime? EndTime { get; set; }
        public int? StatusId { get; set; }
        public DateTime CreatedTime { get; set; }
        public string Creator { get; set; }
        public int? HandlerId { get; set; }
        public string ConfirmationImage { get; set; }
    }
}
