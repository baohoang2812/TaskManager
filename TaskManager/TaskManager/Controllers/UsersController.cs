using AutoMapper;
using Microsoft.AspNetCore.Mvc;
using NLog;
using System;
using System.Linq;
using TaskManager.Constants;
using TaskManager.Models;
using TaskManager.Models.Request;
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


        [HttpPost("all")]
        public IActionResult GetAllUser(GetUserRequest request)
        {
            try
            {
                var service = GetService<UserService>();
                var userList = service.GetAllUser(request);
                var result = MapToList<UserViewModel>(userList);
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
                var roleService = GetService<RoleService>();
                var role = roleService.GetRoleByName(model.RoleName);
                if(role != null)
                {
                    model.RoleId = role.RoleId;
                }
                else
                {
                    return BadRequest(new ApiResult
                    {
                        Message = ResultMessage.NotFound
                    });
                }
                    
                var result = service.EditUser(id, model);
                _unitOfWork.SaveChanges();
                if (result == null)
                {
                    return BadRequest(new ApiResult
                    {
                        Message = ResultMessage.NotFound
                    });
                }
                return Ok(new ApiResult
                {
                    Data = result,
                    Message = ResultMessage.Success,
                });
            }
            catch (Exception e)
            {
                _logger.Error(e, e.Message);
                return Error(new ApiResult
                {
                    Data = null,
                    Message = ResultMessage.Error
                }); ;
            }
        }

        // POST: api/Users
        [HttpPost]
        public IActionResult CreateUser(UserCreateViewModel model)
        {
            try
            {
                var service = GetService<UserService>();
                var roleService = GetService<RoleService>();
                var role = roleService.GetRoleByName(RoleName.USER);
                if(role != null)
                {
                    model.RoleId = role.RoleId;
                }
                else
                {
                    throw new Exception("Role not found");
                }
                var existedUser = service.GetUserByUsername(model.Username);
                if(existedUser != null)
                {
                    return BadRequest(new ApiResult
                    {
                        Message = "Username existed"
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
        public IActionResult Login(LoginRequest request)
        {
            try
            {
                var service = GetService<UserService>();
                var user = service.Authenticate(request.Username, request.Password);
                var result = MapTo<UserViewModel>(user);
                if (result == null)
                {
                    return NotFound(new ApiResult
                    {
                        Message = ResultMessage.NotSupported
                    });
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
