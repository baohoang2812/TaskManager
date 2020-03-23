using System;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata;

namespace TaskManager.Models
{
    public partial class TaskManagerContext : DbContext
    {
        public TaskManagerContext()
        {
        }

        public TaskManagerContext(DbContextOptions<TaskManagerContext> options)
            : base(options)
        {
        }

        public virtual DbSet<Group> Group { get; set; }
        public virtual DbSet<Role> Role { get; set; }
        public virtual DbSet<Status> Status { get; set; }
        public virtual DbSet<Task> Task { get; set; }
        public virtual DbSet<User> User { get; set; }

        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
            if (!optionsBuilder.IsConfigured)
            {
#warning To protect potentially sensitive information in your connection string, you should move it out of source code. See http://go.microsoft.com/fwlink/?LinkId=723263 for guidance on storing connection strings.
                optionsBuilder.UseSqlServer("Server=.\\sqlexpress;Database=TaskManager;Trusted_Connection=false;User Id=sa;Password=123456;");
            }
        }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.HasAnnotation("ProductVersion", "2.2.4-servicing-10062");

            modelBuilder.Entity<Group>(entity =>
            {
                entity.Property(e => e.CreatedTime).HasColumnType("datetime");

                entity.Property(e => e.Description).HasMaxLength(200);

                entity.Property(e => e.ModifyTime).HasColumnType("datetime");

                entity.Property(e => e.Name)
                    .IsRequired()
                    .HasMaxLength(50);
            });

            modelBuilder.Entity<Role>(entity =>
            {
                entity.Property(e => e.Name)
                    .IsRequired()
                    .HasMaxLength(256);
            });

            modelBuilder.Entity<Status>(entity =>
            {
                entity.Property(e => e.Name)
                    .IsRequired()
                    .HasMaxLength(256);
            });

            modelBuilder.Entity<Task>(entity =>
            {
                entity.Property(e => e.CreatedTime).HasColumnType("datetime");

                entity.Property(e => e.Creator)
                    .IsRequired()
                    .HasMaxLength(50);

                entity.Property(e => e.Description).IsRequired();

                entity.Property(e => e.EndTime).HasColumnType("datetime");

                entity.Property(e => e.ModifyTime).HasColumnType("datetime");

                entity.Property(e => e.Name)
                    .IsRequired()
                    .HasMaxLength(200);

                entity.Property(e => e.ReviewedTime).HasColumnType("datetime");

                entity.Property(e => e.StartTime).HasColumnType("datetime");

                entity.HasOne(d => d.Handler)
                    .WithMany(p => p.Task)
                    .HasForeignKey(d => d.HandlerId)
                    .HasConstraintName("FK_Task_User");

                entity.HasOne(d => d.Source)
                    .WithMany(p => p.InverseSource)
                    .HasForeignKey(d => d.SourceId)
                    .HasConstraintName("FK_Task_Task");

                entity.HasOne(d => d.Status)
                    .WithMany(p => p.Task)
                    .HasForeignKey(d => d.StatusId)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("FK_Task_Status");
            });

            modelBuilder.Entity<User>(entity =>
            {
                entity.HasIndex(e => e.Username)
                    .HasName("UQ_User")
                    .IsUnique();

                entity.Property(e => e.Email).HasMaxLength(256);

                entity.Property(e => e.Fullname).IsRequired();

                entity.Property(e => e.ModifyTime).HasColumnType("datetime");

                entity.Property(e => e.PasswordHash).IsRequired();

                entity.Property(e => e.Phone).HasMaxLength(15);

                entity.Property(e => e.Username)
                    .IsRequired()
                    .HasMaxLength(256);

                entity.HasOne(d => d.Group)
                    .WithMany(p => p.User)
                    .HasForeignKey(d => d.GroupId)
                    .HasConstraintName("FK_User_Group");

                entity.HasOne(d => d.Role)
                    .WithMany(p => p.User)
                    .HasForeignKey(d => d.RoleId)
                    .HasConstraintName("FK_User_Role");
            });
        }
    }
}
