using Microsoft.EntityFrameworkCore;

namespace TaskManager.Models.Repositories
{
    public interface IStatusRepository: IBaseRepository<Status, int>
    {

    }
    public class StatusRepository: BaseRepository<Status, int >, IStatusRepository
    {
        public StatusRepository(DbContext dbContext) : base(dbContext)
        {

        }
    }
}
