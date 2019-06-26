module.exports = {
    publicPath: process.env.NODE_ENV === 'production'
        ? '/my-project/'
        : '/',
    css: {
        loaderOptions: { // 向 CSS 相关的 loader 传递选项
            less: {
                modifyVars: {
                    'menu-dark-bg': '#001929',                          // 深色栏目背景色
                    'menu-dark-submenu-bg': '#000917',                  // 深色主题子栏目背景色
                    'primary-color': '#1076ca',                         // 全局主色
                    'link-color': '#433def',                            // 链接色
                    'success-color': '#02D173',                         // 成功色
                    'warning-color': '#FF9503',                         // 警告色
                    'error-color': '#FF4702',                           // 错误色
                    'font-size-base': '14px',                           // 主字号
                    'heading-color': '#17233d',                         // 标题色
                    'text-color': '#5c677b',                            // 主文本色
                    'text-color-secondary ': '#808695',                 // 次文本色
                    'disabled-color ': '#c5c8ce',                       // 失效色
                    'border-radius-base': '2px',                        // 组件/浮层圆角
                    'border-color-base': '#dcdee2',                     // 边框色
                    'box-shadow-base': '0 2px 8px rgba(0, 0, 0, .15)',  // 浮层阴影
                },
                javascriptEnabled: true
            }
        }
    },
    pluginOptions: {
        ghPages: {
            message: '更新',
        },
    },
}