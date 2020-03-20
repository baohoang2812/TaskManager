using Newtonsoft.Json;

namespace TaskManager.ViewModels
{
    public class StatusViewModel
    {
        [JsonProperty("id")]
        public int StatusId { get; set; }
        [JsonProperty("name")]
        public string Name { get; set; }
    }
}
