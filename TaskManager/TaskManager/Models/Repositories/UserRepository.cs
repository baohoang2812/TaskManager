using Microsoft.EntityFrameworkCore;
using System.Linq;

namespace TaskManager.Models.Repositories
{
    public interface IUserRepository: IBaseRepository<User, int>
    {
        User GetByUsernameAndPassword(string username, string password);
    }
    public class UserRepository: BaseRepository<User, int>, IUserRepository
    {
        public UserRepository(DbContext dbContext): base(dbContext)
        {

        }

        public User GetByUsernameAndPassword(string username, string password)
        {
            // [TODO] - BHG Select column
            return _dbSet.FirstOrDefault<User>(x => x.Username == username && x.PasswordHash == password);
        }
    }
}
