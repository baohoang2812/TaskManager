using Newtonsoft.Json;

namespace TaskManager.ViewModels
{
    public class ApiResult
    {
        [JsonProperty("data")]
        public object Data { get; set; }
        [JsonProperty("message")]
        public string Message { get; set; }
    }

    public static class ResultMessage
    {
        public const string NotFound = "Not Found";
        public const string Success = "Success";
        public const string Error = "Error";
        public const string Existed = "Existed";
        public const string Unauthorized = "Unauthorized";
        public const string NotSupported = "Not Supported";
    }

}
