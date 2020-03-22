using Microsoft.EntityFrameworkCore;
using System.Linq;

namespace TaskManager.Models.Repositories
{
    public interface IRoleRepository : IBaseRepository<Role, int>
    {
        Role GetRoleByName(string name);
    }
    public class RoleRepository : BaseRepository<Role, int>, IRoleRepository
    {
        public RoleRepository(DbContext dbContext): base(dbContext)
        {

        }

        public Role GetRoleByName(string name)
        {
            return GetAll().Where(x => x.Name.Contains(name)).Select(x => new Role
            {
                Name = x.Name,
                RoleId = x.RoleId
            }).FirstOrDefault();
        }
    }
}
