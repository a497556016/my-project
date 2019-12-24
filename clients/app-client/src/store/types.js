import * as account from "./account/types"
import * as home from "./home/types"
import * as menus from "./menus/types"
import * as relation from "./relation/types"


const moduleTypes = {
    account,
    home,
    menus,
    relation
}

for (let module in moduleTypes) {

    const types = moduleTypes[module];

    const newTypes = {
        value: module //取模块名称
    };

    for (let key in types) {
        const type = types[key];
        newTypes[key] = module + '/' + type; //取带命名空间的types
    }

    moduleTypes[module] = newTypes;
}

console.log('mapTypes', moduleTypes);

export default moduleTypes;