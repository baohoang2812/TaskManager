using AutoMapper;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace TaskManager.ViewModels
{
    public interface IBaseViewModel<T>
    {
        void MapFromEntity(T entity);
        T MapToEntity();
    }
    public class BaseViewModel<T> : IBaseViewModel<T>
    {
        protected readonly IMapper _mapper;
        public BaseViewModel(IMapper mapper)
        {
            _mapper = mapper;
        }
        public void MapFromEntity(T entity)
        {
            _mapper.Map(entity, this);
        }

        public T MapToEntity()
        {
            return _mapper.Map<T>(this);
        }
    }
}
