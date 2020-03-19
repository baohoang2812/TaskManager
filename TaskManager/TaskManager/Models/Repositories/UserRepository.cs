using Microsoft.EntityFrameworkCore;
using System.Linq;

namespace TaskManager.Models.Repositories
{
    public interface IUserRepository: IBaseRepository<User, int>
    {
        User GetByUsernameAndPassword(string username, string password);
        User GetUserById(int id);
    }
    public class UserRepository: BaseRepository<User, int>, IUserRepository
    {
        public UserRepository(DbContext dbContext): base(dbContext)
        {

        }

        public User GetByUsernameAndPassword(string username, string password)
        {
            // [TODO] - BHG Select column
            return _dbSet.Include(x => x.Role).Where(x => x.Username == username && x.PasswordHash == password)
                .Select(x => new User
            {
                Fullname = x.Fullname,
                Email = x.Email,
                UserId =x.UserId,
                GroupId = x.GroupId,
                Role = x.Role
            }).FirstOrDefault();
        }

        public User GetUserById(int id)
        {
            return _dbSet.Where(x => x.UserId == id).Include(x => x.Role).Include(x => x.Group).Select(x => new User
            {
                UserId = x.UserId,
                GroupId = x.GroupId,
                RoleId = x.RoleId,
                Fullname = x.Fullname,
                Email = x.Email,
                Role = x.Role
            }).FirstOrDefault();
        }
    }
}
