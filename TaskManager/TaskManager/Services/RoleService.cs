using AutoMapper;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using TaskManager.Models;
using TaskManager.Models.Repositories;

namespace TaskManager.Services
{
    public class RoleService : BaseService
    {
        private IRoleRepository _roleRepository; 
        public RoleService(IUnitOfWork unitOfWork, IMapper mapper, IRoleRepository roleRepository) : base(unitOfWork, mapper)
        {
            _roleRepository = roleRepository;
        }

        public Role GetRoleByName(string name)
        {
           return _roleRepository.GetRoleByName(name);
        }
    }
}
