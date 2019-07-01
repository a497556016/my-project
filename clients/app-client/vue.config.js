module.exports = {
    outputDir: 'docs',
    publicPath: process.env.NODE_ENV === 'production' ? '/vant-demo/' : '/',
    css: {
        loaderOptions: {
            less: {
                modifyVars: {
                    red: '#f41414',
                    blue: '#245fae',
                    orange: '#f0741c',
                    green: '#245fae',
                    'text-color': '#111111'
                }
            }
        }
    }
};