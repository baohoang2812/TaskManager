using AutoMapper;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using NLog;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using TaskManager.Constants;
using TaskManager.Models;
using TaskManager.Models.Request;
using TaskManager.Services;
using TaskManager.ViewModels;

namespace TaskManager.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class GroupsController : BaseController
    {
        private readonly TaskManagerContext _context;
        private readonly Logger _logger = LogManager.GetCurrentClassLogger();

        public GroupsController(TaskManagerContext context, IMapper mapper, IUnitOfWork unitOfWork): base(unitOfWork, mapper)
        {
            _context = context;
        }

        // GET: api/Groups
        [HttpGet]
        public async Task<ActionResult<IEnumerable<Group>>> GetGroup()
        {
            return await _context.Group.ToListAsync();
        }

        // GET: api/Groups/5
        [HttpGet("{id}")]
        public async Task<ActionResult<Group>> GetGroup(int id)
        {
            var @group = await _context.Group.FindAsync(id);

            if (@group == null)
            {
                return NotFound();
            }

            return @group;
        }

        // PUT: api/Groups/5
        [HttpPut("{id}")]
        public async Task<IActionResult> PutGroup(int id, Group @group)
        {
            if (id != @group.GroupId)
            {
                return BadRequest();
            }

            _context.Entry(@group).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!GroupExists(id))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return NoContent();
        }

        // POST: api/Groups
        [HttpPost]
        public IActionResult PostGroup(CreateGroupRequest request)
        {
            try
            {
                var userService = GetService<UserService>();
                var user = userService.GetUserById(request.UserId);
                // check Group User is manager
                var manager = userService.GetUserById(request.ManagerId);
                if(user == null || user.RoleId != RoleName.ADMIN || manager.RoleId != RoleName.MANAGER)
                {
                    return Unauthorized(new ApiResult
                    {
                        Message = ResultMessage.Unauthorized
                    });
                }
                var service = GetService<GroupService>();
                var group = service.CreateGroup(request);
                var result = MapTo<GroupViewModel>(group);
                _unitOfWork.SaveChanges();
                return Created($"/api/Groups?id={group.GroupId}",new ApiResult
                {
                    Data = result,
                    Message = ResultMessage.Success
                });
            }catch(Exception e)
            {
                _logger.Error(e, e.Message);
                return Error(new ApiResult
                {
                    Message = ResultMessage.Error
                });
            }
        }

        // DELETE: api/Groups/5
        [HttpDelete("{id}")]
        public async Task<ActionResult<Group>> DeleteGroup(int id)
        {
            var @group = await _context.Group.FindAsync(id);
            if (@group == null)
            {
                return NotFound();
            }

            _context.Group.Remove(@group);
            await _context.SaveChangesAsync();

            return @group;
        }

        private bool GroupExists(int id)
        {
            return _context.Group.Any(e => e.GroupId == id);
        }
    }
}
