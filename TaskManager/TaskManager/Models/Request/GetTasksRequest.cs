using System;

namespace TaskManager.Models.Request
{
    public class GetTasksRequest
    {
        public int userId { get; set; }
        public DateTime? StartTime { get; set; }
        public DateTime? EndTime { get; set; }
        public int? HandlerId { get; set; }
        public int? StatusId { get; set; }

    }
}
