using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.ChangeTracking;

namespace TaskManager.Models.Repositories
{
    public interface IBaseRepository<T, K> where T : class
    {
        T GetById(K key);
        EntityEntry<T> Create(T entity);
        EntityEntry<T> Update(T entity);
        EntityEntry<T> Delete(T entity);
    
    }
    public class BaseRepository<T, K> : IBaseRepository<T, K> where T : class
    {
        protected DbContext _context;
        protected DbSet<T> _dbSet;
        public BaseRepository(DbContext context)
        {
            _context = context;
            _dbSet = context.Set<T>();
        }

        public virtual EntityEntry<T> Create(T entity)
        {
            return _dbSet.Add(entity);
        }


        public EntityEntry<T> Update(T entity)
        {
            return _dbSet.Update(entity);
        }

        public virtual EntityEntry<T> Delete(T entity)
        {
            return _dbSet.Remove(entity);
        }

        public T GetById(K key)
        {
            return _dbSet.Find(key);
        }


      

    }
}
