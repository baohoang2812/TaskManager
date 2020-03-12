using AutoMapper;
using Microsoft.AspNetCore.Mvc;
using NLog;
using System;
using System.Linq;
using TaskManager.Models;
using TaskManager.Services;
using TaskManager.ViewModels;

namespace TaskManager.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class UsersController : BaseController
    {
        private readonly TaskManagerContext _context;
        private Logger _logger = LogManager.GetCurrentClassLogger();

        public UsersController(TaskManagerContext context, IUnitOfWork unitOfWork, IMapper mapper) : base(unitOfWork, mapper)
        {
            _context = context;
        }


        // GET: api/Users/5
        [HttpGet("{id}")]
        public IActionResult GetUser(int id)
        {
            try
            {
                var service = GetService<UserService>();
                var user = service.GetUserById(id);
                if (user == null)
                {
                    return NotFound(new ApiResult
                    {
                        Message = ResultMessage.NotFound
                    });
                }
                var result = MapTo<UserViewModel>(user);
                return Ok(new ApiResult
                {
                    Message = ResultMessage.Success,
                    Data = result
                });
            }
            catch (Exception e)
            {
                _logger.Error(e, e.Message);
                return Error(new ApiResult
                {
                    Message = ResultMessage.Error
                });
            }

        }

        // PUT: api/Users/5
        [HttpPut("{id}")]
        public IActionResult EditUser(int id, UserEditViewModel model)
        {
            try
            {

                var service = GetService<UserService>();
                var result = service.EditUser(id, model);
                _unitOfWork.SaveChanges();
                if (result == null)
                {
                    return BadRequest(new ApiResult
                    {
                        Message = ResultMessage.NotFound
                    });
                }
                return NoContent();
            }
            catch (Exception e)
            {
                _logger.Error(e, e.Message);
                return Error(new ApiResult
                {
                    Message = ResultMessage.Error
                });
            }
        }

        // POST: api/Users
        [HttpPost]
        public IActionResult CreateUser(UserCreateViewModel model)
        {
            try
            {
                var service = GetService<UserService>();
                var existedUser = service.Authenticate(model.Username, model.Password);
                if(existedUser != null)
                {
                    return BadRequest(new ApiResult
                    {
                        Message = ResultMessage.Existed
                    });
                }
                var user = service.CreateUser(model);
                _unitOfWork.SaveChanges();
                return Created($"/api/Users?id={user.UserId}", new ApiResult
                {
                    Data = user,
                    Message = ResultMessage.Success
                });
            }
            catch (Exception e)
            {
                _logger.Error(e, e.Message);
                return Error(new ApiResult
                {
                    Message = ResultMessage.Error
                });
            }

        }

        // DELETE: api/Users/5
        [HttpDelete("{id}")]
        public IActionResult DeleteUser(int id)
        {
            try
            {
                var service = GetService<UserService>();
                var user = service.DeleteUser(id);
                _unitOfWork.SaveChanges();
                if (user == null)
                {
                    return BadRequest(new ApiResult
                    {
                        Message = ResultMessage.NotFound
                    });
                }
                return NoContent();
            }
            catch(Exception e){
                _logger.Error(e, e.Message);
                return Error(new ApiResult
                {
                    Message = ResultMessage.Error
                });
            }
        }

        // POST: api/Users/login
        [Route("login")]
        [HttpPost]
        public IActionResult Login(LoginViewModel model)
        {
            try
            {
                var service = GetService<UserService>();
                var user = service.Authenticate(model.Username, model.Password);
                var result = MapTo<UserViewModel>(user);
                if (result == null)
                {
                    return NotFound();
                }
                return Ok(new ApiResult
                {
                    Data = result
                });
            }
            catch (Exception e)
            {
                _logger.Error(e, e.StackTrace);
                return BadRequest();
            }

        }
    }
}
