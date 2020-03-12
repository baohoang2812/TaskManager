
using AutoMapper;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.DependencyInjection;
using System.Collections.Generic;
using System.Net;
using TaskManager.Models;

namespace TaskManager.Controllers
{
    public class BaseController: ControllerBase
    {
        protected readonly IUnitOfWork _unitOfWork;
        protected readonly IMapper _mapper;
        public BaseController(IUnitOfWork unitOfWork, IMapper mapper)
        {
            _unitOfWork = unitOfWork;
            _mapper = mapper;
        }
        public T GetService<T>() where T:class
        {
            return HttpContext.RequestServices.GetService<T>();
        }

        protected IActionResult Error<T>(T obj)
        {
            return StatusCode((int)HttpStatusCode.InternalServerError, obj);
        }

        public T MapTo<T>(object entity) where T : class
        {
            return _mapper.Map<T>(entity);
        }

        public IList<T> MapToList<T>(object obj)
        {
            return _mapper.Map<IList<T>>(obj);
        }
    }
}
