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
            MapStatus();
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
            CreateMap<Task, TaskViewModel>()
                .ForMember(dest => dest.HandlerName, opts => opts.MapFrom(src => src.Handler.Fullname))
                .ForMember(dest => dest.Comment, opts => opts.MapFrom(src => src.ManagerReview));
            CreateMap<TaskCreateViewModel, Task>();
            CreateMap<TaskEditViewModel, Task>();
            //CreateMap<Status, TaskViewModel>().ForMember(dest => dest.StatusName, opts => opts.MapFrom(src => src.Name));
        }

        public void MapStatus()
        {
            CreateMap<Status, StatusViewModel>();
        }
    }
}
