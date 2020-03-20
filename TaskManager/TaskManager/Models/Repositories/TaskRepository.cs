using Microsoft.EntityFrameworkCore;
using System.Linq;

namespace TaskManager.Models.Repositories
{
    public interface ITaskRepository: IBaseRepository<Task, int>
    {
        IQueryable<Task> GetAllTask();
        Task GetTaskById(int id);
    }
    public class TaskRepository : BaseRepository<Task, int>, ITaskRepository
    {
        public TaskRepository(DbContext dbContext): base(dbContext)
        {

        }

        public IQueryable<Task> GetAllTask()
        {
            return GetAll().Include(x => x.Handler).Include(x => x.Status).Select(x => new Task
            {
                TaskId = x.TaskId,
                Name = x.Name,
                Description = x.Description,
                EndTime = x.EndTime,
                Status = x.Status
            });
        }
        public Task GetTaskById(int id)
        {
            return GetAll().Include(x => x.Status).Include(x => x.Handler).Where(x => x.TaskId == id).FirstOrDefault();
        }
    }
}
