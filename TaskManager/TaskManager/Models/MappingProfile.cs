using AutoMapper;
using TaskManager.ViewModels;

namespace TaskManager.Models
{
    public class MappingProfile: Profile
    {
        public MappingProfile()
        {
            MapUser();
            MapTask();
        }

        public void MapUser()
        {
            CreateMap<User, UserViewModel>();
            CreateMap<Role, UserViewModel>().ForMember(dest => dest.RoleName, opts => opts.MapFrom(src => src.Name));
            CreateMap<UserCreateViewModel, User>()
                .ForMember(x => x.PasswordHash, options => options.MapFrom(x => x.Password));
            CreateMap<UserCreateViewModel, Group>();
            CreateMap<UserCreateViewModel, Role>();
            CreateMap<UserEditViewModel, User>();
        }

        public void MapTask()
        {
            CreateMap<Task, TaskViewModel>();
            CreateMap<TaskCreateViewModel, Task>();
            CreateMap<TaskEditViewModel, Task>();
        }
    }
}
