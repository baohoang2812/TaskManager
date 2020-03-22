using AutoMapper;
using Microsoft.EntityFrameworkCore;
using System.Collections.Generic;
using System.Linq;
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
        
        public List<Group> GetAllGroup()
        {
            return _groupRepository.GetAll().Include(x => x.Manager).Include(x => x.User).Select(x => new Group
            {
                Name = x.Name,
                Description = x.Description,
                CreatedTime = x.CreatedTime,
                Manager = x.Manager,
                ManagerId = x.ManagerId,
                GroupId = x.GroupId,
                User = x.User
            }).ToList();
        }

        public Group GetGroupById(int groupId)
        {
            return GetAllGroup().Where(x => x.GroupId == groupId).OrderBy(x => x.GroupId).FirstOrDefault();
        }
    }
}
