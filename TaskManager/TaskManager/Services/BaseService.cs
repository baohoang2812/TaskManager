using AutoMapper;
using TaskManager.Models;

namespace TaskManager.Services
{
    public class BaseService
    {
        protected readonly IUnitOfWork _unitOfWork;
        protected readonly IMapper _mapper;
        public BaseService(IUnitOfWork unitOfWork, IMapper mapper)
        {
            _unitOfWork = unitOfWork;
            _mapper = mapper;
        }

        public T MapTo<T>(object src)
        {
            return _mapper.Map<T>(src);
        }

        public T Map<T>(object src, T dest)
        {
            return _mapper.Map(src, dest);
        }
    }
}
