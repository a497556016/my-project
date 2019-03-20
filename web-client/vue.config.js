module.exports = {
    css: {
        loaderOptions: { // 向 CSS 相关的 loader 传递选项
            less: {
                modifyVars: {
                    'primary-color': '#4a318a',
                    'link-color': '#4a318a',
                    'border-radius-base': '2px',
                    'menu-dark-bg': '#001529',
                    'menu-dark-submenu-bg': '#000c1 7'
                },
                javascriptEnabled: true
            }
        }
    }
}