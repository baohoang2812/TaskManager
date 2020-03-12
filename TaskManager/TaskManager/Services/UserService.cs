using AutoMapper;
using System.Collections.Generic;
using TaskManager.Models;
using TaskManager.Models.Repositories;
using TaskManager.ViewModels;

namespace TaskManager.Services
{
    public class UserService: BaseService
    {
        private readonly IUserRepository _userRepository;
        public UserService(IUnitOfWork unitOfWork, IMapper mapper): base(unitOfWork, mapper)
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

    }
}
