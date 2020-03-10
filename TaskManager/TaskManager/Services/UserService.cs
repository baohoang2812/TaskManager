using TaskManager.Models;
using TaskManager.Models.Repositories;
using TaskManager.ViewModels;

namespace TaskManager.Services
{
    public class UserService: BaseService
    {
        private readonly IUserRepository _userRepository;
        public UserService(IUnitOfWork unitOfWork): base(unitOfWork)
        {
            _userRepository = _unitOfWork.GetService<IUserRepository>();
        }

        public User GetUserById(int id)
        {
            return _userRepository.GetById(id);
        }

        public User Authenticate(string username, string password)
        {
            return _userRepository.GetByUsernameAndPassword(username, password);
        }
    }
}
