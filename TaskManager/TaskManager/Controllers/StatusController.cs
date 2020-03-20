using AutoMapper;
using Microsoft.AspNetCore.Mvc;
using NLog;
using System;
using TaskManager.Models;
using TaskManager.Services;
using TaskManager.ViewModels;

namespace TaskManager.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class StatusController : BaseController
    {
        public StatusController(IUnitOfWork unitOfWork, IMapper mapper): base(unitOfWork, mapper)
        {

        }
        private Logger _logger = LogManager.GetCurrentClassLogger();
        [HttpGet]
        public IActionResult GetStatus()
        {
            try
            {
                var statusService = GetService<StatusService>();
                var result = MapToList<StatusViewModel>(statusService.getAllStatus());
                return Ok(new ApiResult
                {
                    Message = ResultMessage.Success,
                    Data = result
                });
            }catch(Exception e)
            {
                _logger.Error(e, e.Message);
                return Error(new ApiResult
                {
                    Message = ResultMessage.Error
                });
            }
        }
    }
}