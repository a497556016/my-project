import BaseService from "../../base";

class RoleService extends BaseService{
    constructor(basePath){
        super(basePath);
    }


}

export default new RoleService('/account-server/role');