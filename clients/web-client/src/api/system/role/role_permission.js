import BaseService from "../../base";

class RolePermissionService extends BaseService{
    constructor(basePath){
        super(basePath);
    }

    assignPermissions(rolePermissions){
        return super.put('/assignPermissions', rolePermissions);
    }
}

export default new RolePermissionService('/account-server/role-permission');