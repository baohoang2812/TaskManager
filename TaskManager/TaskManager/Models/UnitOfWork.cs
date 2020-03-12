using Microsoft.EntityFrameworkCore;
using System;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.EntityFrameworkCore.Storage;
using System.Threading.Tasks;
using System.Threading;

namespace TaskManager.Models
{
    public interface IUnitOfWork
    {
        T GetService<T>();
        int SaveChanges();
        Task<int> SaveChangesAsync(CancellationToken cancellationToken = default(CancellationToken));
        IDbContextTransaction BeginTransaction();
    }
    public class UnitOfWork: IUnitOfWork
    {
        private DbContext _context;
        private IServiceProvider _serviceProvider;
        public UnitOfWork(IServiceProvider serviceProvider ,DbContext context)
        {
            _serviceProvider = serviceProvider;
            _context = context;
        }

        public IDbContextTransaction BeginTransaction()
        {
            return _context.Database.BeginTransaction();
        }

        public void CommitTransaction()
        {
            _context.Database.CommitTransaction();
        }

        public T GetService<T>()
        {
            return _serviceProvider.GetService<T>();
        }

        public int SaveChanges()
        {
            return _context.SaveChanges();
        }

        public async Task<int> SaveChangesAsync(CancellationToken cancellationToken = default(CancellationToken))
        {
            return await _context.SaveChangesAsync(cancellationToken);
        }
    }
}
