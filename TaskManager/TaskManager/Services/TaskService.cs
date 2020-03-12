using AutoMapper;
using System.Collections.Generic;
using System.Linq;
using TaskManager.Constants;
using TaskManager.Models;
using TaskManager.Models.Repositories;
using TaskManager.Models.Request;
using TaskManager.ViewModels;

namespace TaskManager.Services
{
    public class TaskService : BaseService
    {
        private readonly ITaskRepository _taskRepository;
        private readonly IUserRepository _userRepository;
        public TaskService(IUnitOfWork unitOfWork, ITaskRepository taskRepository, IMapper mapper, IUserRepository userRepository) : base(unitOfWork, mapper)
        {
            _taskRepository = taskRepository;
            _userRepository = userRepository;
        }
        public Task CreateTask(TaskCreateViewModel model)
        {
            return _taskRepository.Create(MapTo<Task>(model)).Entity;
        }

        public Task DeleteTask(int id)
        {
            return _taskRepository.Delete(id).Entity;
        }

        public Task GetById(int id)
        {
            return _taskRepository.GetById(id);
        }

        public Task EditTask(int id, TaskEditViewModel model)
        {
            var task = _taskRepository.GetById(id);
            return task != null ? _taskRepository.Update(Map(model, task)).Entity : null;
        }

        public IList<Task> GetAllTask(GetTasksRequest request, User user)
        {
            var query = _taskRepository.GetAllTask();
            if (request.StartTime != null)
            {
                query = query.Where(s => s.StartTime >= request.StartTime);
            }
            if (request.EndTime != null)
            {
                query = query.Where(s => s.EndTime <= request.EndTime);
            }
            if (request.StatusId != 0)
            {
                query = query.Where(s => s.StatusId == request.StatusId);
            }
            
            switch (user.RoleId)
            {
                case RoleName.ADMIN:
                    {
                        break;
                    }
                case RoleName.MANAGER:
                    {
                        if(user.GroupId != null)
                        {
                            var groupUsers = _userRepository.GetAll().Where(x => x.GroupId == user.GroupId).Select(e => e.UserId).ToList();
                            query.Where(x => groupUsers.Contains(x.HandlerId ?? 0));
                        }
                        break;
                    }
                case RoleName.USER:
                    {
                        query.Where(x => x.HandlerId == user.UserId);
                        break;
                    }
                default:
                    {
                        break;
                    }
            }
            return query.ToList();
        }
    }
}
