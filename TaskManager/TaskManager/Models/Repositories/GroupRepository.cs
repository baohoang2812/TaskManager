using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace TaskManager.Models.Repositories
{
    public interface IGroupRepository:IBaseRepository<Group, int>
    {

    }
    public class GroupRepository: BaseRepository<Group, int>, IGroupRepository 
    {
        public GroupRepository(DbContext dbContext): base(dbContext) 
        {

        }
    }
}
