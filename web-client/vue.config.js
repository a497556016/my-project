module.exports = {
    publicPath: process.env.NODE_ENV === 'production'
        ? '/my-project/admin/'
        : '/',
    css: {
        loaderOptions: { // 向 CSS 相关的 loader 传递选项
            less: {
                modifyVars: {
                    'primary-color': '#32a78c',
                    'success-color': '#30d14c',
                    'link-color': '#3f87bf',
                    'border-radius-base': '2px',
                    'menu-dark-bg': '#001929',
                    'menu-dark-submenu-bg': '#000917'
                },
                javascriptEnabled: true
            }
        }
    }
}