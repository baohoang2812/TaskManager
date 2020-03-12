namespace TaskManager.Models.Request
{
    public class CreateGroupRequest
    {
        public int ManagerId { get; set; }
        public int UserId { get; set; }
    }
}
