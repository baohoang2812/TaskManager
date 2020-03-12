using AutoMapper;
using Microsoft.AspNetCore.Hosting;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using NLog;
using System;
using System.Collections.Generic;
using System.IO;
using TaskManager.Models;
using TaskManager.Models.Request;
using TaskManager.Services;
using TaskManager.ViewModels;

namespace TaskManager.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class TasksController : BaseController
    {
        private Logger _logger = LogManager.GetCurrentClassLogger();
        public static IHostingEnvironment _hostingEnvironment;
        public TasksController(IUnitOfWork unitOfWork, IMapper mapper, IHostingEnvironment hostingEnvironment): base(unitOfWork, mapper)
        {
            _hostingEnvironment = hostingEnvironment;
        }

        // GET: api/Tasks
        [HttpPost]
        [Route("all")]
        public IActionResult GetTask([FromBody] GetTasksRequest request)
        {
            try
            {
                var userService = GetService<UserService>();
                var user = userService.GetUserById(request.userId);
                if(user == null)
                {
                    return Unauthorized(new ApiResult
                    {
                        Message = ResultMessage.Unauthorized,
                    });
                }
                var taskService = GetService<TaskService>();
                var taskList =taskService.GetAllTask(request, user);
                var result = MapToList<TaskViewModel>(taskList);
                return Ok(new ApiResult
                {
                    Data = result,
                    Message = ResultMessage.Success
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

        // GET: api/Tasks/5
        [HttpGet("{id}")]
        public IActionResult GetTask(int id)
        {
            try
            {
                var service = GetService<TaskService>();
                var task = service.GetById(id);
                if (task == null)
                {
                    return NotFound(new ApiResult
                    {
                        Message = ResultMessage.NotFound
                    });
                }
                var result = MapTo<TaskViewModel>(task);
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

        // PUT: api/Tasks/5
        [HttpPut("{id}")]
        public IActionResult EditTask(int id, TaskEditViewModel model)
        {
            try
            {
                var service = GetService<TaskService>();
                var task = service.EditTask(id, model);
                var result = MapTo<TaskViewModel>(task);
                if(task == null)
                {
                    return NotFound(new ApiResult
                    {
                        Message = ResultMessage.NotFound,
                    });
                }
                _unitOfWork.SaveChanges();
                return NoContent();

            }
            catch (Exception e) 
            {
                _logger.Error(e, e.Message);
                Error(new ApiResult
                {
                    Message = ResultMessage.Error,
                });
            }

            return NoContent();
        }

        // POST: api/Tasks
        [HttpPost]
        public IActionResult CreateTask(TaskCreateViewModel model)
        {
            try
            {
                var service = GetService<TaskService>();
                var task = service.CreateTask(model);
                var result = MapTo<TaskViewModel>(task);
                _unitOfWork.SaveChanges();
                return Created($"/api/Tasks?id={task.TaskId}", new ApiResult
                {
                    Data = result,
                    Message = ResultMessage.Success
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

        // DELETE: api/Tasks/5
        [HttpDelete("{id}")]
        public IActionResult DeleteTask(int id)
        {
            try
            {
                var service = GetService<TaskService>();
                var task = service.DeleteTask(id);
                _unitOfWork.SaveChanges();
                if (task == null)
                {
                    return NotFound(new ApiResult
                    {
                        Message = ResultMessage.NotFound
                    });
                }
                return NoContent();
            }
            catch(Exception e)
            {
                _logger.Error(e, e.Message);
                return Error(new ApiResult
                {
                    Message = ResultMessage.Error
                });
            }
            

        }

        // POST: api/Tasks
        [HttpPost]
        [Route("upload")]
        public IActionResult UploadConfirmationImage(IFormFile file)
        {
            try
            {
                if (file.Length > 0)
                {
                    if (!Directory.Exists(_hostingEnvironment.WebRootPath + "\\Upload\\"))
                    {
                        Directory.CreateDirectory(_hostingEnvironment.WebRootPath + "\\Upload\\");
                    }
                    using (FileStream fileStream = System.IO.File.Create(_hostingEnvironment.WebRootPath + "\\Upload\\" + file.FileName))
                    {
                        file.CopyTo(fileStream);
                        fileStream.Flush();
                        return CreatedAtAction("UploadConfirmationImage", new ApiResult
                        {
                            Message = "\\Upload\\"+file.FileName
                        });
                    }
                }
                return BadRequest(new ApiResult
                {
                    Message = ResultMessage.NotSupported
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

    }
}
