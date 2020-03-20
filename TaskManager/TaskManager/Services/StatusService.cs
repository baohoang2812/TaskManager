using AutoMapper;
using System.Collections.Generic;
using System.Linq;
using TaskManager.Models;
using TaskManager.Models.Repositories;

namespace TaskManager.Services
{
    public class StatusService : BaseService
    {
        private IStatusRepository _statusRepository;
        public StatusService(IUnitOfWork unitOfWork, IMapper mapper, IStatusRepository statusRepository) : base(unitOfWork, mapper)
        {
            _statusRepository = statusRepository;
        }

        public IList<Status> getAllStatus(string name)
        {
            var query = _statusRepository.GetAll();
            if (name != null)
            {
                query = query.Where(s => s.Name == name);
            }
            return query.Select(s => new Status
            {
                StatusId = s.StatusId,
                Name = s.Name
            }).ToList();
        }
    }
}
