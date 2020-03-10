
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.DependencyInjection;

namespace TaskManager.Controllers
{
    public class BaseController: ControllerBase
    {
        public T GetService<T>() where T:class
        {
            return HttpContext.RequestServices.GetService<T>();
        }
    }
}
