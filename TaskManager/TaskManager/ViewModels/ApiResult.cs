using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace TaskManager.ViewModels
{
    public class ApiResult
    {
        [JsonProperty("code")]
        public ResultCode? Code { get; set; }
        [JsonProperty("data")]
        public object Data { get; set; }
        [JsonProperty("message")]
        public string Message { get; set; }
    }
    
    public enum ResultCode
    {
        [Display(Name = "Not Found")]
        NotFound,
        [Display(Name = "Unauthorized")]
        Unauthorized,
        [Display(Name = "Unsupported")]
        Unsupported,
        [Display(Name = "Unknown Error")]
        UnknownError,
        [Display(Name = "Fail Validation")]
        FailValidation,
        [Display(Name = "Success")]
        Succsess,

    }
}
