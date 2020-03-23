using System;

namespace TaskManager.Models.Request
{
    public class CreateGroupRequest
    {
        public int UserId { get; set; }
        public string Description { get; set; }
        public DateTime? CreatedTime { get; set; }
        public string Name { get; set; }
    }
}
