using Newtonsoft.Json;
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
        public int StatusId { get; set; } 
        public DateTime CreatedTime { get; set; } = DateTime.Now;
        public string Creator { get; set; }
        public int? HandlerId { get; set; }
        public string ConfirmationImage { get; set; }
    }

    public class TaskCreateViewModel
    {
        [JsonProperty("name")]
        public string Name { get; set; }
        [JsonProperty("sourceId")]
        public int? SourceId { get; set; }
        [JsonProperty("description")]
        public string Description { get; set; }
        [JsonProperty("startTime")]
        public DateTime StartTime { get; set; } = DateTime.Now;
        [JsonProperty("endTime")]
        public DateTime? EndTime { get; set; }
        [JsonProperty("statusId")]
        public int? StatusId { get; set; } = 1;
        public DateTime CreatedTime { get; set; } = DateTime.Now;
        [JsonProperty("creator")]
        public string Creator { get; set; } = "Eden";
        [JsonProperty("handlerId")]
        public int? HandlerId { get; set; }
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
