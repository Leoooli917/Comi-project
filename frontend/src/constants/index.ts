// 权限值
export class Role {
  static RoleSuperAdmin = 100;
  static RoleAdmin = 90;
  static RoleUser = 80;

  static hanzi = (role: number) => {
    switch (role) {
      // 系统级别权限
      case Role.RoleSuperAdmin:
        return '系统超级管理员';
      case Role.RoleAdmin:
        return '系统管理员';
      case Role.RoleUser:
        return '系统用户';

    }
  };

  static upper = (role: number | undefined, targetRole: number) => {
    if (!role) return false;
    return role >= targetRole;
  };

  // 每个角色都大于等于目标角色
  static everyUpper = (roles: number[], targetRole: number) => {
    return roles.every((role) => role >= targetRole);
  }

  // 总有一个角色大于等于目标角色
  static someUpper = (roles: number[], targetRole: number) => {
    return roles.some((role) => role >= targetRole);
  }
}

export const CACHE_NAME = 'comi-user';
export const COOKIE_TOKEN = 'comi-token';


