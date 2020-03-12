using AutoMapper;
using TaskManager.Models;
using TaskManager.Models.Repositories;
using TaskManager.Models.Request;
using TaskManager.ViewModels;

namespace TaskManager.Services
{
    public class GroupService: BaseService
    {
        private readonly IGroupRepository _groupRepository;
        public GroupService(IGroupRepository groupRepository, IUnitOfWork unitOfWork, IMapper mapper): base(unitOfWork, mapper)
        {
            _groupRepository = groupRepository;
        }
        public Group CreateGroup(CreateGroupRequest request)
        {
            return _groupRepository.Create(MapTo<Group>(request)).Entity;
        }
    }
}
