using AutoMapper;
using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Swashbuckle.AspNetCore.Swagger;
using System.Collections.Generic;
using System.Linq;
using TaskManager.Models;
using TaskManager.Models.Repositories;
using TaskManager.Services;

namespace TaskManager
{
    public class Startup
    {
        public Startup(IConfiguration configuration)
        {
            Configuration = configuration;
        }

        public IConfiguration Configuration { get; }

        // This method gets called by the runtime. Use this method to add services to the container.
        public void ConfigureServices(IServiceCollection services)
        {
            services.AddMvc().SetCompatibilityVersion(CompatibilityVersion.Version_2_2);
            services.AddSwaggerGen(s =>
            {
                s.SwaggerDoc("v1", new Info
                {
                    Title = "Task Manager",
                    Description = "API Documentation of Task Manager",
                    TermsOfService = "None",
                    Version = "1.0.0"
                });
                s.AddSecurityDefinition("Bearer", new ApiKeyScheme
                {
                    In = "header",
                    Description = "Please enter into field the world 'Bearer' following by space and JWT",
                    Name = "Authorization",
                    Type = "apiKey"
                });
                s.AddSecurityRequirement(new Dictionary<string, IEnumerable<string>>
                {
                    {"Bearer", Enumerable.Empty<string>()}
                });
            });
            services.AddDbContext<TaskManagerContext>(options =>
            {
                options.UseSqlServer(Configuration.GetConnectionString("TaskManager"));
            });
            services.AddAutoMapper(typeof(Startup));
            ConfigureIoC(services);
        }

        // This method gets called by the runtime. Use this method to configure the HTTP request pipeline.
        public void Configure(IApplicationBuilder app, IHostingEnvironment env)
        {
            if (env.IsDevelopment())
            {
                app.UseDeveloperExceptionPage();
            }
            // Enable middleware to serve generated Swagger as a JSON endpoint.
            app.UseSwagger();
            // Enable middleware to serve swagger-ui (HTML, JS, CSS, etc.), 
            // specifying the Swagger JSON endpoint.
            app.UseSwaggerUI(c =>
            {
                c.SwaggerEndpoint("/swagger/v1/swagger.json", "Task Manager API V1");
                c.RoutePrefix = string.Empty;
            });
            app.UseStaticFiles();

            app.UseMvc();
        }

        public void ConfigureIoC(IServiceCollection services)
        {
            services.AddScoped<UserService>()
                .AddScoped<IUnitOfWork, UnitOfWork>()
                .AddScoped<DbContext, TaskManagerContext>()
                .AddScoped<IUserRepository, UserRepository>()
                .AddScoped<ITaskRepository, TaskRepository>()
                .AddScoped<IGroupRepository, GroupRepository>()
                .AddScoped<TaskService>()
                .AddScoped<GroupService>();
        }
    }
}
