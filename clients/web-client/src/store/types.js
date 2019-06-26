
//用户账户
import accountTypes from './modules/system/account/types'

//用户管理
import * as userTypes from './modules/system/user/types'
import * as userEditTypes from './modules/system/user/edit/types'
import * as userAddTypes from './modules/system/user/add/types'
//角色
import * as roleTypes from './modules/system/role/types'

//界面布局
import * as layoutTypes from './modules/layout/types'

//文件系统
import fileTypes from './modules/file/types'
//文件系统首页
import * as fileMainTypes from './modules/file/main/types'
//文件管理
import * as fileManageTypes from './modules/file/manage/types'

//表单
import * as formTypes from './modules/form/types'

/**
 * 导出模块类型
 * @param types 类型
 * @param m 模块名称
 * @private
 */
const _export = function (types, m) {

    const _export = {}

    for(let f in types){
        const fields = types[f];

        if(typeof fields === 'object') {
            const obj = {};
            for(let k in fields) {
                obj[k] = m + '/' + fields[k];
            }

            _export[f] = obj;
        }else {
            _export[f] = m + '/' + fields;
        }
    }

    console.log(_export);

    return _export;
};

export const account = _export(accountTypes, 'account');
export const user = _export(userTypes, 'user');
export const userEdit = _export(userEditTypes, 'userEdit');
export const userAdd = _export(userAddTypes, 'userAdd');
export const role = _export(roleTypes, 'role');

export const layout = _export(layoutTypes, 'layout');

export const file = _export(fileTypes, 'file');
export const fileMain = _export(fileMainTypes, 'fileMain');
export const fileManage = _export(fileManageTypes, 'fileManage');

export const form = _export(formTypes, 'form');


