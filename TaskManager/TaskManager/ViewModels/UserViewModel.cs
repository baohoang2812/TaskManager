using Newtonsoft.Json;
using System.ComponentModel.DataAnnotations;

namespace TaskManager.ViewModels
{
    public class UserViewModel
    {
        public string Username { get; set; }
        public string Password { get; set; }
    }
}
