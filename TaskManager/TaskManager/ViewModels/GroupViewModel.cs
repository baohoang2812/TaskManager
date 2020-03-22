using Newtonsoft.Json;
using System;
using System.Collections.Generic;

namespace TaskManager.ViewModels
{
    public class GroupViewModel
    {
        public int GroupId { get; set; }
        public int? ManagerId { get; set; }
        [JsonProperty("managerName")]
        public string ManagerFullName { get; set; }
        public string Name { get; set; }
        public DateTime? CreatedTime { get; set; }
        public string Description { get; set; }
        public IList<UserViewModel> GroupMember { get; set; }
    }
}
