import Vue from 'vue'

/**
 * 根据路由配置生成菜单栏目
 */
const buildMenuFromRoutes = function (routes) {

    const menus = [], menuTabs = [];

    function computeFromRoutes(menus, routes){
        routes.forEach(route => {
            if(route.meta){
                // console.log(route.component)

                let menu = {
                    id: route.name,
                    title: route.meta.title,
                    component: route.name,
                    icon: route.meta.icon,
                    hide: route.meta.hideInMenu
                    // component: route.component
                };

                if(route.meta.showInFirst){
                    menu.noCloseable = true;
                    menuTabs.push(menu);
                }

                if(route.children && route.children.length > 0){
                    menu.children = [];
                    computeFromRoutes(menu.children, route.children);
                }

                menus.push(menu);

                //全局注册栏目页面组件
                if(route.name && route.component) {
                    Vue.component(route.name, route.component);
                }

            }else{
                if(route.children && route.children.length > 0){
                    computeFromRoutes(menus, route.children);
                }
            }
        })
    }

    computeFromRoutes(menus, routes);

    return {
        menus,
        menuTabs
    };
}

/**
 * 获取表单的数据同时验证
 * @param form
 * @returns {Promise<any>}
 */
const getValuesWithValid = function (form) {
    return new Promise((resolve, reject) => {
        form.validateFields({force: true}, (err, values) => {
            if(!err) {
                resolve(values);
            }else{
                reject(err);
            }
        })
    })
}

export default {
    buildMenuFromRoutes,
    getValuesWithValid
}