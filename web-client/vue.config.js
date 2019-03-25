module.exports = {
    publicPath: process.env.NODE_ENV === 'production'
        ? '/my-project/admin/'
        : '/',
    css: {
        loaderOptions: { // 向 CSS 相关的 loader 传递选项
            less: {
                modifyVars: {
                    'menu-dark-bg': '#001929',                          // 深色栏目背景色
                    'menu-dark-submenu-bg': '#000917',                  // 深色主题子栏目背景色
                    'primary-color': '#5a3b8a',                         // 全局主色
                    'link-color': '#1890ff',                            // 链接色
                    'success-color': '#52c41a',                         // 成功色
                    'warning-color': '#faad14',                         // 警告色
                    'error-color': '#f5222d',                           // 错误色
                    'font-size-base': '14px',                           // 主字号
                    'heading-color': 'rgba(0, 0, 0, .85)',              // 标题色
                    'text-color': 'rgba(0, 0, 0, .65)',                 // 主文本色
                    'text-color-secondary ': 'rgba(0, 0, 0, .45)',      // 次文本色
                    'disabled-color ': 'rgba(0, 0, 0, .25)',            // 失效色
                    'border-radius-base': '2px',                        // 组件/浮层圆角
                    'border-color-base': '#d9d9d9',                     // 边框色
                    'box-shadow-base': '0 2px 8px rgba(0, 0, 0, .15)',  // 浮层阴影
                },
                javascriptEnabled: true
            }
        }
    },
    pluginOptions: {
        ghPages: {
            message: 'Updates',
        },
    },
}