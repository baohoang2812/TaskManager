using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.ChangeTracking;
using System.Linq;

namespace TaskManager.Models.Repositories
{
    public interface IBaseRepository<T, K> where T : class
    {
        T GetById(K key);
        IQueryable<T> GetAll();
        EntityEntry<T> Create(T entity);
        EntityEntry<T> Update(T entity);
        EntityEntry<T> Delete(T entity);
        EntityEntry<T> Delete(K key);
    
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

        public EntityEntry<T> Delete(K key)
        {
            var result = GetById(key);
            return result != null ? _dbSet.Remove(result) : null;
        }

        public IQueryable<T> GetAll()
        {
            return _context.Set<T>();
        }
    }
}
