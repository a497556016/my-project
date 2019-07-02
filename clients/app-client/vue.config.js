module.exports = {
    outputDir: 'docs',
    publicPath: process.env.NODE_ENV === 'production' ? '/vant-demo/' : '/',
    css: {
        loaderOptions: {
            less: {
                modifyVars: {
                    red: '#f41414',
                    blue: '#4e5e97', //active color
                    orange: '#f0741c',
                    green: '#334151', //primary color
                    'text-color': '#001226'
                }
            }
        }
    }
};