using Microsoft.EntityFrameworkCore;
using System.Linq;

namespace TaskManager.Models.Repositories
{
    public interface ITaskRepository: IBaseRepository<Task, int>
    {
        IQueryable<Task> GetAllTask();
    }
    public class TaskRepository : BaseRepository<Task, int>, ITaskRepository
    {
        public TaskRepository(DbContext dbContext): base(dbContext)
        {

        }

        public IQueryable<Task> GetAllTask()
        {
            return GetAll().Include(x => x.Handler).Select(x => new Task
            {
                TaskId = x.TaskId,
                Name = x.Name,
                Status = x.Status,
                CreatedTime = x.CreatedTime,
                Description = x.Description,
            });
        }
    }
}
