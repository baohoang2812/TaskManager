using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace TaskManager.Models.Request
{
    public class GetUserRequest
    {
        public int? GroupId { get; set; }
        public string Username { get; set; }
    }
}
