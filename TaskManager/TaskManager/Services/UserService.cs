using AutoMapper;
using Microsoft.EntityFrameworkCore;
using System.Collections.Generic;
using System.Linq;
using TaskManager.Constants;
using TaskManager.Models;
using TaskManager.Models.Repositories;
using TaskManager.Models.Request;
using TaskManager.ViewModels;

namespace TaskManager.Services
{
    public class UserService : BaseService
    {
        private readonly IUserRepository _userRepository;
        public UserService(IUnitOfWork unitOfWork, IMapper mapper) : base(unitOfWork, mapper)
        {
            _userRepository = _unitOfWork.GetService<IUserRepository>();
        }

        public User GetUserById(int id)
        {
            return _userRepository.GetUserById(id);
        }

        public User Authenticate(string username, string password)
        {
            return _userRepository.GetByUsernameAndPassword(username, password);
        }

        public User CreateUser(UserCreateViewModel model)
        {
            var user = MapTo<User>(model);
            return _userRepository.Create(user).Entity;
        }

        public User EditUser(int id, UserEditViewModel model)
        {
            var user = _userRepository.GetById(id);
            return user != null ? _userRepository.Update(Map<User>(model, user)).Entity : null;
        }

        public User DeleteUser(int id)
        {
            return _userRepository.Delete(id).Entity;
        }

        public List<User> GetAllUser(GetUserRequest request)
        {
            var query = _userRepository.GetAll().Include(s => s.Role).Select(s => new User
            {
                Fullname = s.Fullname,
                Email = s.Email,
                UserId = s.UserId,
                Role = s.Role,
                GroupId = s.GroupId,
                Phone = s.Phone,
                Username = s.Username
            });
            if (request.GroupId != null)
            {
                query = query.Where(s => s.GroupId == request.GroupId);
            }
            return query.ToList();
        }

        public User GetUserByUsername(string username)
        {
            return GetAllUser(new GetUserRequest()).Where(s => s.Username == username).FirstOrDefault();
        }

    }
}
